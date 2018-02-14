package com.wuziqiwebsocket;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.context.ContextLoader;

import com.firegame.dao.RelationshipDao;
import com.firegame.dao.UserDao;
import com.google.gson.Gson;
import com.websocket.AbstractWebSocket;
import com.websocket.GetHttpSessionConfigurator;
import com.websocket.MsgStrategy;
import com.websocket.WebSocket;
import com.xiangqiwebsocket.WebSocketXiangqi;

import exception.MyRuntimeException;
import po.GameState;
import po.GameStateToString;
import po.User;
import po.WuziqiGameState;
import po.XiangqiGameState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ServerEndpoint ע����һ�����ε�ע�⣬��Ĺ�����Ҫ�ǽ�Ŀǰ���ඨ���һ��websocket��������,
 *                 ע���ֵ�������ڼ����û����ӵ��ն˷���URL��ַ,�ͻ��˿���ͨ�����URL�����ӵ�
 *                 WebSocket��������
 * @author uptop
 */
@ServerEndpoint(value="/websocket",configurator=GetHttpSessionConfigurator.class)
public class WebSocketWuziqi extends AbstractWebSocket {
	
	public static ConcurrentHashMap<String, WebSocketWuziqi> socketMap = new ConcurrentHashMap<String, WebSocketWuziqi>();
	public static ConcurrentHashMap<String, WuziqiGameState> gameMap = new ConcurrentHashMap<String, WuziqiGameState>();

	private Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	
	@OnOpen
	public void onOpen(Session session,EndpointConfig config) throws Exception {

		this.session = session;
	
		 HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
	     this.username=(String) httpSession.getAttribute("userId");
	      if(username==null)
	    	  throw new MyRuntimeException(MyRuntimeException.USERNOTEQUAL);

		onOpenTemple();
	  
		System.out.println("�������Ӽ��룡��ǰ��������Ϊ" + socketMap.size());
		System.out.println("�������Ӽ��룡��ǰgameState" + gameMap.size());

	}

	/**
	 * ���ӹرյ��õķ���
	 */
	@OnClose
	public void onClose() {
		onCloseTemple();;
			System.out.println("��һ���ӹرգ���ǰ��������Ϊ" + socketMap.size());
			System.out.println("�������Ӽ��룡��ǰgameState" + gameMap.size());
		
	}

	/**
	 * �յ��ͻ�����Ϣ����õķ���
	 * 
	 * @param message
	 *            �ͻ��˷��͹�������Ϣ
	 * @param session
	 *            ��ѡ�Ĳ���
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@OnMessage
	public void onMessage(String message, Session session)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		JSONObject jsonObject = JSONObject.fromObject(message);
		int type = jsonObject.getInt("type");
		// ����Է�
		MsgStrategy msgStrategy = (MsgStrategy) Class.forName(
				"com.wuziqiwebsocket.WuziqiMsg" + type).newInstance();
		msgStrategy.dealMsg(jsonObject, username, gameMapKey);
		System.out.println("���Կͻ��˵���Ϣ:" + message);
		System.out.println(Thread.currentThread().getName());

		// Ⱥ����Ϣ
		/*
		 * for (WebSocketTest item : webSocketSet) { try {
		 * item.sendMessage(message); } catch (IOException e) {
		 * e.printStackTrace(); continue; } }
		 */
	}

	/**
	 * �������ʱ����
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		try {
			socketMap.remove(username);
			System.out.println("�������");
			out();
			error.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	/**
	 * ������������漸��������һ��û����ע�⣬�Ǹ���Լ���Ҫ��ӵķ�����
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);

	}

	public void sendMsg(String msg) {
		Set<Entry<String, WebSocketWuziqi>> set = socketMap.entrySet();
		for (Entry<String, WebSocketWuziqi> entry : set) {
			try {
				entry.getValue().sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		session.close();
	}

	@Override
	public Map getSocketMap() {
		// TODO Auto-generated method stub
		return socketMap;
	}

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
	public Map getGameMap() {
		// TODO Auto-generated method stub
		return gameMap;
	}

}
