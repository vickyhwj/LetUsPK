package com.userwebsocket;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.MessageDriven;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
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
import com.websocket.BaseWebSocket;
import com.websocket.GetHttpSessionConfigurator;
import com.websocket.MsgStrategy;
import com.websocket.WebSocket;
import com.xiangqiwebsocket.WebSocketMsg;

import exception.MyRuntimeException;

@ServerEndpoint(value="/websocketMsg",configurator=GetHttpSessionConfigurator.class)
public class WebSocketUser extends BaseWebSocket {
	MessageService messageService = (MessageService) ContextLoader
			.getCurrentWebApplicationContext().getBean("messageService");
	RelationshipService relationshipService = (RelationshipService) ContextLoader
			.getCurrentWebApplicationContext().getBean("relationshipService");
	public static ConcurrentHashMap<String, WebSocketUser> socketMap = new ConcurrentHashMap<String, WebSocketUser>();
	

/*	@Override
	@OnOpen
	public void onOpen(Session session,EndpointConfig config) throws Exception {
		// TODO Auto-generated method stub
	
		super.onOpen(session, config);
		System.out.println("在线人数："+socketMap.size());
	}

	@Override
	@OnClose
	public void onClose() {
		// TODO Auto-generated method stub
		super.onClose();
		System.out.println("在线人数："+socketMap.size());

	}*/

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
		super.onError(session, error);
		System.out.println("在线人数："+socketMap.size());

	}

	

	@Override
	public void sendMsg(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map getSocketMap() {
		// TODO Auto-generated method stub
		return this.socketMap;
	}

}
