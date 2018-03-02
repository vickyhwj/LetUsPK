package com.wuziqiwebsocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import po.WuziqiGameState;

import com.websocket.GameWebSocket;
import com.websocket.GetHttpSessionConfigurator;
import com.websocket.MsgStrategy;

@ServerEndpoint(value="/websocket",configurator=GetHttpSessionConfigurator.class)
public class WebSocketWuziqi extends GameWebSocket{
	
	public static ConcurrentHashMap<String, WebSocketWuziqi> socketMap = new ConcurrentHashMap<String, WebSocketWuziqi>();
	public static ConcurrentHashMap<String, WuziqiGameState> gameMap = new ConcurrentHashMap<String, WuziqiGameState>();


	@Override
	public void loadLastGameState() throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = new Criteria();

		WuziqiGameState gameState = (WuziqiGameState) mongoTemplate.findOne(
				new Query(new Criteria().orOperator(
						Criteria.where("A").is(username), Criteria.where("B")
								.is(username))), WuziqiGameState.class);
		if (gameState != null) {

			JSONObject jsonObject = new JSONObject();
			jsonObject.element("turn", gameState.getTurn());
			jsonObject.element("state", gameState.getState());
			jsonObject.element("type", 8);
			jsonObject.element("A", gameState.getA());
			jsonObject.element("B", gameState.getB());
			String A = jsonObject.getString("A");
			String B = jsonObject.getString("B");
			String key = A.compareTo(B) < 0 ? A + " " + B : B + " " + A;
			gameMapKey = key;
			if (gameMap.containsKey(gameMapKey))
				throw new Exception();
			String opp = A.equals(username) ? B : A;
			if (!socketMap.containsKey(opp))
				return;
			gameMap.put(key, gameState);
			try {
				WebSocketWuziqi w1 = socketMap.get(A);
				w1.sendMessage(jsonObject.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				WebSocketWuziqi w1 = socketMap.get(B);
				w1.sendMessage(jsonObject.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
	}

	@Override
	public void out() {
		if (gameMapKey == null)
			return;
		WuziqiGameState gameState = gameMap.get(gameMapKey);
		if (gameState == null)
			return;
		synchronized (gameState) {

			if (gameMap.get(gameMapKey) == null)
				return;
			if (gameState == null)
				return;
			mongoTemplate.upsert(
					new Query(new Criteria().orOperator(
							Criteria.where("A").is(username),
							Criteria.where("B").is(username))),
					new Update().set("A", gameState.getA())
							.set("B", gameState.getB())
							.set("state", gameState.getState())
							.set("turn", gameState.getTurn())
							.set("playRecord", gameState.getPlayRecord()),
					gameState.getClass());
			// mongoTemplate.insert(gameState);

			gameMap.remove(gameMapKey);
			if (gameState.getA().equals(username)) {
				try {
					boolean isOpen = socketMap.get(gameState.getB())
							.getSession().isOpen();
					if (isOpen)
						socketMap.get(gameState.getB()).getSession().close();
					socketMap.remove(gameState.getB());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					boolean isOpen = socketMap.get(gameState.getA())
							.getSession().isOpen();
					if (isOpen)
						socketMap.get(gameState.getA()).getSession().close();
					socketMap.remove(gameState.getA());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	
	@Override
	@OnMessage
	public void onMessage(String message, Session session)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSONObject.fromObject(message);
		int type = jsonObject.getInt("type");
		// ����Է�
		MsgStrategy msgStrategy = (MsgStrategy) Class.forName(
				"com.wuziqiwebsocket.WuziqiMsg" + type).newInstance();
		msgStrategy.dealMsg(jsonObject, username, gameMapKey);
		System.out.println("���Կͻ��˵���Ϣ:" + message);
		System.out.println(Thread.currentThread().getName());
		
	}

	@Override
	public Map getSocketMap() {
		// TODO Auto-generated method stub
		return socketMap;
	}

	@Override
	public Map getGameMap() {
		// TODO Auto-generated method stub
		return gameMap;
	}

}
