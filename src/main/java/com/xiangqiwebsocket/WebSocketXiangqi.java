package com.xiangqiwebsocket;

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
import com.websocket.MsgStrategy;
import com.websocket.WebSocket;

import po.GameState;
import po.GameStateToString;
import po.User;
import po.XiangqiGameState;
import po.XiangqiMove;

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

/**
 * @ServerEndpoint ע����һ�����ε�ע�⣬��Ĺ�����Ҫ�ǽ�Ŀǰ���ඨ���һ��websocket��������,
 *                 ע���ֵ�������ڼ����û����ӵ��ն˷���URL��ַ,�ͻ��˿���ͨ�����URL�����ӵ�
 *                 WebSocket��������
 * @author uptop
 */
@ServerEndpoint("/websocketxiangqi")
public class WebSocketXiangqi implements WebSocket {
	RelationshipDao relationshipDao = (RelationshipDao) ContextLoader
			.getCurrentWebApplicationContext().getBean("relationshipDao");
	UserDao userDao = (UserDao) ContextLoader.getCurrentWebApplicationContext()
			.getBean("userDao");
	MongoTemplate mongoTemplate = (MongoTemplate) ContextLoader
			.getCurrentWebApplicationContext().getBean("mongoTemplate");
	String username, gameMapKey;
	static Thread timer;
	static{
		timer=new Thread(new XiangqiTimer(),"xiangqiTimer");
		timer.start();
	}
	public String getGameMapKey() {
		return gameMapKey;
	}

	public void setGameMapKey(String gameMapKey) {
		this.gameMapKey = gameMapKey;
	}

	// concurrent����̰߳�ȫSet���������ÿ���ͻ��˶�Ӧ��MyWebSocket������Ҫʵ�ַ�����뵥һ�ͻ���ͨ�ŵĻ�������ʹ��Map����ţ�����Key����Ϊ�û���ʶ
	public static ConcurrentHashMap<String, WebSocketXiangqi> socketMap = new ConcurrentHashMap<String, WebSocketXiangqi>();
	public static ConcurrentHashMap<String, XiangqiGameState> gameMap = new ConcurrentHashMap<String, XiangqiGameState>();

	// ��ĳ���ͻ��˵����ӻỰ����Ҫͨ��������ͻ��˷������
	private Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * ���ӽ����ɹ����õķ���
	 * 
	 * @param session
	 *            ��ѡ�Ĳ���sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ��������ͻ��˷������
	 * @throws Exception
	 */
	@OnOpen
	public void onOpen(Session session) throws Exception {
		this.session = session;
		Map<String, List<String>> map = session.getRequestParameterMap();
		List<String> list = map.get("username");
		this.username = list.get(0);

		synchronized (WebSocketXiangqi.class) {
			WebSocketXiangqi presocket = socketMap.get(this.username);
			
			if (presocket != null) {
				presocket.getSession().close();
			}
			
			socketMap.put(this.username, this);

		}
		
		
		ArrayList<User> userList = (ArrayList<User>) relationshipDao
				.selectUserListbyUserA(username);
		ArrayList<String> friendOnlineList = new ArrayList<String>();
		for (User user : userList) {
			if (socketMap.containsKey(user.getUserid())) {
				friendOnlineList.add(user.getUserid());
			}
		}
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
		// ֪ͨ����
		JSONObject comResult = new JSONObject();
		comResult.element("type", 1);
		comResult.element("from", username);
		for (User user : userList) {
			if (socketMap.containsKey(user.getUserid())) {
				WebSocketXiangqi socketTest = socketMap.get(user.getUserid());
				socketTest.sendMsg(comResult.toString());
			}
		}

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
			/*	if (lastGame != null) {
					lastGame.cancelTimer(); // 防止之前的线程还在运行内存泄漏
				}*/
				gameMap.put(key, gameState);
				mongoTemplate.insert(new JSONObject().element("key", key.replaceAll(" ", "@")),"xiangqigamekey");
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
		System.out.println("�������Ӽ��룡��ǰ��������Ϊ" + socketMap.size());
		System.out.println("�������Ӽ��룡��ǰgameState" + gameMap.size());

	}

	// 计时器
	public void createTimer(XiangqiGameState gameState, WebSocketXiangqi w1,
			WebSocketXiangqi w2) {

		JSONObject timerObj = new JSONObject();
		timerObj.element("type", 33);
		timerObj.element("second",
				 gameState.getNow());

		try {
			w1.sendMessage(timerObj.toString());
			w2.sendMessage(timerObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ���ӹرյ��õķ���
	 */
	@OnClose
	public void onClose() {
		try {
			JSONObject comResult = new JSONObject();
			comResult.element("type", 3);
			comResult.element("from", username);
			ArrayList<User> userList = (ArrayList<User>) relationshipDao
					.selectUserListbyUserA(username);
			for (User user : userList) {
				try {
					WebSocketXiangqi socketTest = socketMap.get(user
							.getUserid());
					if (socketTest != null && socketTest.getSession().isOpen())
						socketTest.sendMessage(comResult.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			out();
			socketMap.remove(username);
			System.out.println("��һ���ӹرգ���ǰ��������Ϊ" + socketMap.size());
			System.out.println("�������Ӽ��룡��ǰgameState" + gameMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		try{
		JSONObject jsonObject = JSONObject.fromObject(message);
		int type = jsonObject.getInt("type");
		// ����Է�
		/*MsgStrategy msgStrategy = (MsgStrategy) Class.forName(
				"com.xiangqiwebsocket.XiangqiMsg" + type).newInstance();*/
	
		MsgStrategy msgStrategy=(MsgStrategy) ContextLoader.getCurrentWebApplicationContext().getBean("xiangqiMsg"+type);
		msgStrategy.dealMsg(jsonObject, username, gameMapKey);
		
		System.out.println("���Կͻ��˵���Ϣ:" + message);
		System.out.println(Thread.currentThread().getName());
		}catch(Exception e){
			e.printStackTrace();
		}

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
			mongoTemplate.remove(new Query(Criteria.where("key").is(gameMapKey.replaceAll(" ", "@"))), "xiangqigamekey");
			gameMap.remove(gameMapKey);
			if (gameState.getA().equals(username)) {
				try {
					boolean isOpen = socketMap.get(gameState.getB())
							.getSession().isOpen();
					//会死锁
					/*if (isOpen)
						socketMap.get(gameState.getB()).getSession().close();
					socketMap.remove(gameState.getB());*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					boolean isOpen = socketMap.get(gameState.getA())
							.getSession().isOpen();
					//会死锁
					/*if (isOpen)
						socketMap.get(gameState.getA()).getSession().close();
					socketMap.remove(gameState.getA());*/
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
		Set<Entry<String, WebSocketXiangqi>> set = socketMap.entrySet();
		for (Entry<String, WebSocketXiangqi> entry : set) {
			try {
				entry.getValue().sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
