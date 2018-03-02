package com.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.wuziqiwebsocket.WebSocketWuziqi;
import com.xiangqiwebsocket.WebSocketXiangqi;

import exception.MyRuntimeException;
import tool.AuthenticationUtil;
import tool.WebSocketTool;

public abstract class BaseWebSocket implements WebSocket{
	protected Session session;
	protected String username;
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) throws Exception {
		// TODO Auto-generated method stub
		//不能用 SecurityContextHolder.getContext().getAuthentication().getPrincipal()
		 HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
	     this.username=(String) httpSession.getAttribute("userId");
	      if(username==null)
	    	  throw new MyRuntimeException(MyRuntimeException.USERNOTEQUAL);

		this.session=session;
		WebSocketTool.closePreSocketAndAdd(getClass(), getSocketMap(), this);
		
		System.out.println(getClass().toString()+":"+getSocketMap().size());

		
	}

	@OnClose
	public void onClose() {
		// TODO Auto-generated method stub
		
		synchronized (this.getClass()) {
			if(this == getSocketMap().get(username)){
				getSocketMap().remove(username);
			}
		}
		System.out.println(getClass().toString()+":"+getSocketMap().size());

		
		
	}

	
	public abstract void onMessage(String message, Session session)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException ;

	@OnError
	public void onError(Session session, Throwable error) {
		synchronized (this.getClass()) {
			if(this == getSocketMap().get(username)){
				getSocketMap().remove(username);
			}
		}
		System.out.println(getClass().toString()+":"+getSocketMap().size());

	}

	
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);

	}

	
	public void sendMsg(String msg) {
		// TODO Auto-generated method stub
		Set<Entry<String, WebSocketWuziqi>> set = getSocketMap().entrySet();
		for (Entry<String, WebSocketWuziqi> entry : set) {
			try {
				entry.getValue().sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public abstract Map getSocketMap();


	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	public Session getSession(){
		return session;
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		if(this.session.isOpen())
			this.session.close();
		
	}
	

}
