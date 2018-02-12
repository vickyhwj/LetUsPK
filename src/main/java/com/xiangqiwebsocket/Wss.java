package com.xiangqiwebsocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.websocket.WebSocket;
@ServerEndpoint("/wss")
public class Wss implements WebSocket{

	@OnOpen
	public void onOpen(Session session) {
		// TODO Auto-generated method stub
		 Map<String,List<String>> map=session.getRequestParameterMap();
	      List<String> list=map.get("username");
	      System.out.println(list.get(0));
	}

	@OnClose
	public void onClose() {
		// TODO Auto-generated method stub
		
	}

	@OnMessage
	public void onMessage(String message, Session session)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@OnError
	public void onError(Session session, Throwable error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String message) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMsg(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpen(Session session, EndpointConfig config) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
