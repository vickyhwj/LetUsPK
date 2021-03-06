package com.userwebsocket;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.MessageDriven;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import org.springframework.web.context.ContextLoader;

import po.Message;

import com.firegame.service.MessageService;
import com.firegame.service.RelationshipService;
import com.websocket.MsgStrategy;
import com.websocket.WebSocket;
import com.xiangqiwebsocket.WebSocketMsg;

@ServerEndpoint("/websocketMsg")
public class WebSocketUser implements WebSocket {
	MessageService messageService = (MessageService) ContextLoader
			.getCurrentWebApplicationContext().getBean("messageService");
	RelationshipService relationshipService = (RelationshipService) ContextLoader
			.getCurrentWebApplicationContext().getBean("relationshipService");
	String username;
	public static ConcurrentHashMap<String, WebSocketUser> socketMap = new ConcurrentHashMap<String, WebSocketUser>();
	private Session session;

	@Override
	@OnOpen
	public void onOpen(Session session) throws IOException {
		// TODO Auto-generated method stub
		this.session = session;
		Map<String, List<String>> map = session.getRequestParameterMap();
		List<String> list = map.get("username");
		this.username = list.get(0);
		WebSocketUser presocket=socketMap.get(this.username);
		if(presocket!=null) presocket.session.close();
		socketMap.put(this.username, this);
		
		System.out.println("在线人数："+socketMap.size());
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		socketMap.remove(username);
		System.out.println("在线人数："+socketMap.size());
	}

	@Override
	@OnMessage
	public void onMessage(String message, Session session)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		JSONObject jsonObject=JSONObject.fromObject(message);
		int type=jsonObject.getInt("type");
		try{
			MsgStrategy msgStrategy=(MsgStrategy) Class.forName("com.userwebsocket.UserMsg"+type).newInstance();
			msgStrategy.dealMsg(jsonObject, username, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	
		
	
	
		System.out.println(message);
	}

	@Override
	@OnError
	public void onError(Session session, Throwable error) {
		// TODO Auto-generated method stub
		System.out.println("error");
        error.printStackTrace();
	}

	@Override
	public void sendMessage(String message) throws IOException {
		// TODO Auto-generated method stub
		this.session.getBasicRemote().sendText(message);

	}

	@Override
	public void sendMsg(String msg) {
		// TODO Auto-generated method stub

	}

}
