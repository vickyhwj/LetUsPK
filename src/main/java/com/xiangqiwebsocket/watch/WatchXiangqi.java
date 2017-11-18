package com.xiangqiwebsocket.watch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;
import po.XiangqiGameState;

import com.websocket.WebSocket;
import com.xiangqiwebsocket.WebSocketXiangqi;
@ServerEndpoint("/WatchXiangqi")
public class WatchXiangqi implements WebSocket{
	private Session session;
	private String gameMapKey;
	public static ConcurrentHashMap<String, CopyOnWriteArraySet<WatchXiangqi>> watchMap=new ConcurrentHashMap<>();
	public CopyOnWriteArraySet<WatchXiangqi> set;
	@OnOpen
	public void onOpen(Session session) throws Exception {
		// TODO Auto-generated method stub
		this.session=session;
		Map<String,List<String>> map= session.getRequestParameterMap();
		List<String> pl= map.get("key");
		gameMapKey=pl.get(0).replaceAll("@", " ");
		synchronized(WatchXiangqi.class){
			CopyOnWriteArraySet<WatchXiangqi> watSet= watchMap.get(gameMapKey);
			if(watSet==null){
				watSet=new CopyOnWriteArraySet<>();
				watchMap.put(gameMapKey, watSet);
			}
			watSet.add(this);
			this.set=watSet;
		}
		XiangqiGameState gameState= WebSocketXiangqi.gameMap.get(gameMapKey);
		if(gameState!=null){
			JSONObject jsonObject=new JSONObject();
			jsonObject.element("A", gameState.getA());
			jsonObject.element("B", gameState.getB());
			jsonObject.element("state",gameState.getState() );
			jsonObject.element("turn", gameState.getTurn());
			sendMessage(jsonObject.toString());
		}
		System.out.println("watchMapsize:"+watchMap.size());

	}

	@OnClose
	public void onClose() {
		// TODO Auto-generated method stub
		out();
		System.out.println("watchMapsize:"+watchMap.size());
		
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
		out();
		System.out.println("watchMapsize:"+watchMap.size());

	}

	@Override
	public void sendMessage(String message) throws IOException {
		// TODO Auto-generated method stub
		session.getBasicRemote().sendText(message);
		
	}

	@Override
	public void sendMsg(String msg) {
		// TODO Auto-generated method stub
		for(WatchXiangqi watchXiangqi:set)
			try {
				watchXiangqi.sendMessage(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public void out(){
		synchronized (WatchXiangqi.class) {
			if(set.isEmpty()) return;
			set.remove(this);			
			if(set.isEmpty()){	
				watchMap.remove(gameMapKey);
			}
		}
	}

}
