package com.websocket;

import java.io.IOException;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.apache.commons.logging.impl.AvalonLogger;

public interface WebSocket {
	
	public abstract void onOpen(Session session, EndpointConfig config)
			throws Exception;

	
	public abstract void onClose();

	
	public abstract void onMessage(String message, Session session)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException;

	
	public abstract void onError(Session session, Throwable error);

	public void sendMessage(String message) throws IOException;

	// 广播
	public void sendMsg(String msg);
}
