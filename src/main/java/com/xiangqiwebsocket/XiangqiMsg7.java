package com.xiangqiwebsocket;

import org.springframework.stereotype.Repository;

import com.websocket.MsgStrategy;

import po.XiangqiGameState;
import net.sf.json.JSONObject;
//ͬ对方同意对战了
@Repository
public class XiangqiMsg7 implements MsgStrategy{
//	String gameMapKey;

	public void dealMsg(JSONObject jsonObject,String username,String gameMapKey) {
		// TODO Auto-generated method stub
		
		String to=jsonObject.getString("to");
		JSONObject comResult = new JSONObject();
        comResult.element("type", 71);
        comResult.element("from", username);
        try{
			WebSocketXiangqi webSocketTest=WebSocketXiangqi.socketMap.get(to);
			webSocketTest.sendMessage(comResult.toString());
			XiangqiGameState gameState=new XiangqiGameState(username,to,username);
			
			
			
			
			String key=username.compareTo(to)<0? username+" "+to:to+" "+username;
		//	gameMapKey=key;
			
			WebSocketXiangqi.gameMap.put(key, gameState);
			WebSocketXiangqi w1=WebSocketXiangqi.socketMap.get(username),w2=WebSocketXiangqi.socketMap.get(to);
			w1.setGameMapKey(key);
			w2.setGameMapKey(key);
			JSONObject gameObj=new JSONObject();
			gameObj.element("state", gameState.getState());
			gameObj.element("A", gameState.getA());
			gameObj.element("B", gameState.getB());
			gameObj.element("type", 8);
			gameObj.element("turn", gameState.getTurn());
			System.out.println(gameObj.toString());
			w1.sendMessage(gameObj.toString());
			w2.sendMessage(gameObj.toString());
			gameMapKey=key;
			createTimer(gameState, w1, w2,gameMapKey);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	// 计时器
		public void createTimer(XiangqiGameState gameState, WebSocketXiangqi w1,
				WebSocketXiangqi w2,String gameMapKey) {
		
		
			gameState.setNow(XiangqiTimer.TIME_SECOND);
									
			JSONObject timerObj=new JSONObject();
			timerObj.element("type",33 );
			timerObj.element("second", XiangqiTimer.TIME_SECOND);
			
			try {
				w1.sendMessage(timerObj.toString());
				w2.sendMessage(timerObj.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

}
