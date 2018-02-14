package com.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import net.sf.json.JSONObject;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.context.ContextLoader;

import po.User;
import tool.WebSocketTool;

import com.firegame.dao.RelationshipDao;
import com.firegame.dao.UserDao;
import com.xiangqiwebsocket.WebSocketXiangqi;

public abstract class AbstractWebSocket {
	protected RelationshipDao relationshipDao = (RelationshipDao) ContextLoader
			.getCurrentWebApplicationContext().getBean("relationshipDao");
	protected UserDao userDao = (UserDao) ContextLoader.getCurrentWebApplicationContext()
			.getBean("userDao");
	protected MongoTemplate mongoTemplate = (MongoTemplate) ContextLoader
			.getCurrentWebApplicationContext().getBean("mongoTemplate");
	protected String username;
	protected String gameMapKey;
	

	
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getGameMapKey() {
		return gameMapKey;
	}



	public void setGameMapKey(String gameMapKey) {
		this.gameMapKey = gameMapKey;
	}



	public abstract void onOpen(Session session, EndpointConfig config) throws Exception;


	
	public abstract void onClose();

	
	public abstract void onMessage(String message, Session session)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException;
	
	public abstract void onError(Session session, Throwable error);

	
	public abstract void sendMessage(String message) throws IOException ;

	
	public abstract void sendMsg(String msg) ;
	
	public abstract void close() throws IOException;
	
	private ArrayList<User> readFriendOnline(Map socketMap){
		ArrayList<User> userList = (ArrayList<User>) relationshipDao
				.selectUserListbyUserA(username);
		ArrayList<User> friendOnlineList = new ArrayList<User>();
		for (User user : userList) {
			if (socketMap.containsKey(user.getUserid())) {
				friendOnlineList.add(user);
			}
		}
		return friendOnlineList;
		
	}
	private void sendOnlineFriendMsg(ArrayList<User> friendOnlineList){
		JSONObject result = new JSONObject();
		result.element("type", 2);
		result.element("list", friendOnlineList);
		System.out.print(result.toString());
		try {
			sendMessage(result.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void sendFriendOnOpen(ArrayList<User> friendOnlineList) throws IOException{
		JSONObject comResult = new JSONObject();
		comResult.element("type", 1);
		comResult.element("from", username);
		for (User user : friendOnlineList) {
			if (getSocketMap().containsKey(user.getUserid())) {
				AbstractWebSocket socketTest = (AbstractWebSocket) getSocketMap().get(user.getUserid());
				try{
					socketTest.sendMessage(comResult.toString());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

	}
	public abstract Map getSocketMap();
	public void onOpenTemple() throws IOException{
		   WebSocketTool.closePreSocketAndAdd(WebSocketXiangqi.class, getSocketMap(), this);
			
			ArrayList<User> userList = readFriendOnline(getSocketMap());
			sendOnlineFriendMsg(userList);
			sendFriendOnOpen(userList);
			
			try {
				loadLastGameState();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	private void tellFriendOnClose(){
		JSONObject comResult = new JSONObject();
		comResult.element("type", 3);
		comResult.element("from", username);
		ArrayList<User> userList = (ArrayList<User>) relationshipDao
				.selectUserListbyUserA(username);
		for (User user : userList) {
			try {
				AbstractWebSocket socketTest = (AbstractWebSocket) getSocketMap().get(user
						.getUserid());
				socketTest.sendMessage(comResult.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void onCloseTemple(){
		tellFriendOnClose();
		out();
		getSocketMap().remove(username);
	}
	public abstract void out();
	public abstract void loadLastGameState() throws Exception;
	
	public abstract Map getGameMap();
	public abstract Session getSession();

}
