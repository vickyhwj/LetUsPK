package com.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import net.sf.json.JSONObject;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.context.ContextLoader;

import po.User;
import tool.WebSocketTool;

import com.firegame.dao.RelationshipDao;
import com.firegame.dao.UserDao;
import com.xiangqiwebsocket.WebSocketXiangqi;

public abstract class GameWebSocket extends BaseWebSocket{
	protected RelationshipDao relationshipDao = (RelationshipDao) ContextLoader
			.getCurrentWebApplicationContext().getBean("relationshipDao");
	protected UserDao userDao = (UserDao) ContextLoader.getCurrentWebApplicationContext()
			.getBean("userDao");
	protected MongoTemplate mongoTemplate = (MongoTemplate) ContextLoader
			.getCurrentWebApplicationContext().getBean("mongoTemplate");
	protected String gameMapKey;
	public String getGameMapKey() {
		return gameMapKey;
	}



	public void setGameMapKey(String gameMapKey) {
		this.gameMapKey = gameMapKey;
	}



	@Override
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) throws Exception {
		// TODO Auto-generated method stub
		super.onOpen(session, config);
		onOpenTemple();
		System.out.println(getClass().toString()+":"+ getSocketMap().size());
		System.out.println(getClass().toString() +"_gameMap:"+ getGameMap().size());
	}



	@Override
	@OnClose
	public void onClose() {
		// TODO Auto-generated method stub
		onCloseTemple();
		super.onClose();
		System.out.println(getClass().toString()+":"+ getSocketMap().size());
		System.out.println(getClass().toString() +"_gameMap:"+ getGameMap().size());
	}



	@Override
	@OnError
	public void onError(Session session, Throwable error) {
		// TODO Auto-generated method stub
		try {
			System.out.println("onError");
			out();
			error.printStackTrace();
			super.onError(session, error);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(getClass().toString()+":"+ getSocketMap().size());
		System.out.println(getClass().toString() +"_gameMap:"+ getGameMap().size());
	}
	
	
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
	public void onOpenTemple() throws IOException{
			
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
				BaseWebSocket socketTest = (BaseWebSocket) getSocketMap().get(user.getUserid());
				try{
					socketTest.sendMessage(comResult.toString());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

	}
	
	public void onCloseTemple(){
		tellFriendOnClose();
		out();
		
	}
	private void tellFriendOnClose(){
		JSONObject comResult = new JSONObject();
		comResult.element("type", 3);
		comResult.element("from", username);
		ArrayList<User> userList = (ArrayList<User>) relationshipDao
				.selectUserListbyUserA(username);
		for (User user : userList) {
			try {
				BaseWebSocket socketTest = (BaseWebSocket) getSocketMap().get(user
						.getUserid());
				socketTest.sendMessage(comResult.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public abstract void loadLastGameState() throws Exception;
	public abstract void out();



	public abstract Map getGameMap();
	
}
