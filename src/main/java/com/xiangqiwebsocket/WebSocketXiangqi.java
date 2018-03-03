package com.xiangqiwebsocket;

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
import com.mongodb.BasicDBObject;
import com.websocket.AbstractWebSocket;
import com.websocket.GameWebSocket;
import com.websocket.GetHttpSessionConfigurator;
import com.websocket.MsgStrategy;
import com.websocket.WebSocket;

import exception.MyRuntimeException;
import po.GameState;
import po.GameStateToString;
import po.User;
import po.XiangqiGameState;
import po.XiangqiMove;
import tool.UserUtils;
import tool.WebSocketTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocketxiangqi", configurator = GetHttpSessionConfigurator.class)
public class WebSocketXiangqi extends GameWebSocket {

	static Thread timer;
	static {
		timer = new Thread(new XiangqiTimer(), "xiangqiTimer");
		timer.start();
	}

	public static ConcurrentHashMap<String, WebSocketXiangqi> socketMap = new ConcurrentHashMap<String, WebSocketXiangqi>();
	public static ConcurrentHashMap<String, XiangqiGameState> gameMap = new ConcurrentHashMap<String, XiangqiGameState>();


	

	// 计时器
	public void createTimer(XiangqiGameState gameState, WebSocketXiangqi w1,
			WebSocketXiangqi w2) {

		JSONObject timerObj = new JSONObject();
		timerObj.element("type", 33);
		timerObj.element("second", gameState.getNow());

		try {
			w1.sendMessage(timerObj.toString());
			w2.sendMessage(timerObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
	@OnMessage
	public void onMessage(String message, Session session)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		try {
			JSONObject jsonObject = JSONObject.fromObject(message);
			int type = jsonObject.getInt("type");
			// ����Է�
			/*
			 * MsgStrategy msgStrategy = (MsgStrategy) Class.forName(
			 * "com.xiangqiwebsocket.XiangqiMsg" + type).newInstance();
			 */

			MsgStrategy msgStrategy = (MsgStrategy) ContextLoader
					.getCurrentWebApplicationContext().getBean(
							"xiangqiMsg" + type);
			msgStrategy.dealMsg(jsonObject, username, gameMapKey);

			System.out.println("���Կͻ��˵���Ϣ:" + message);
			System.out.println(Thread.currentThread().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//
		/*
		 * for (WebSocketTest item : webSocketSet) { try {
		 * item.sendMessage(message); } catch (IOException e) {
		 * e.printStackTrace(); continue; } }
		 */
	}


	@Override
	public void out() {

		if (gameMapKey == null)
			return;
		XiangqiGameState gameState = gameMap.get(gameMapKey);
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
							.set("now", gameState.getNow())
							.set("playRecord", gameState.getPlayRecord()),
					gameState.getClass());
			// mongoTemplate.insert(gameState);
			mongoTemplate
					.remove(new Query(Criteria.where("key").is(
							gameMapKey.replaceAll(" ", "-"))), "xiangqigamekey");
			gameMap.remove(gameMapKey);
			if (gameState.getA().equals(username)) {
				try {
					boolean isOpen = socketMap.get(gameState.getB())
							.getSession().isOpen();
					// 会死锁
					/*
					 * if (isOpen)
					 * socketMap.get(gameState.getB()).getSession().close();
					 * socketMap.remove(gameState.getB());
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					boolean isOpen = socketMap.get(gameState.getA())
							.getSession().isOpen();
					// 会死锁
					/*
					 * if (isOpen)
					 * socketMap.get(gameState.getA()).getSession().close();
					 * socketMap.remove(gameState.getA());
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

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

		XiangqiGameState gameState = (XiangqiGameState) mongoTemplate.findOne(
				new Query(new Criteria().orOperator(
						Criteria.where("A").is(username), Criteria.where("B")
								.is(username))), XiangqiGameState.class);
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
				throw new Exception("gameMapIsLive");
			String opp = A.equals(username) ? B : A;
			if (socketMap.containsKey(opp)) {

				XiangqiGameState lastGame = gameMap.get(key);
				/*
				 * if (lastGame != null) { lastGame.cancelTimer(); //
				 * 防止之前的线程还在运行内存泄漏 }
				 */
				gameMap.put(key, gameState);
				mongoTemplate.insert(
						new JSONObject().element("key",
								key.replaceAll(" ", "-")), "xiangqigamekey");
				WebSocketXiangqi w1 = null, w2 = null;
				try {
					w1 = socketMap.get(A);
					w1.sendMessage(jsonObject.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					w2 = socketMap.get(B);
					w2.sendMessage(jsonObject.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}

				createTimer(gameState, w1, w2);

			}

		}

	}

	@Override
	public Map getGameMap() {
		// TODO Auto-generated method stub
		return gameMap;
	}

}
