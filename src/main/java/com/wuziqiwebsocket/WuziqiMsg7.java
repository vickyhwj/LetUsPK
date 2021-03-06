package com.wuziqiwebsocket;

import po.GameState;
import po.WuziqiGameState;
import net.sf.json.JSONObject;

import com.websocket.MsgStrategy;
//同意对战
public class WuziqiMsg7 implements MsgStrategy{

	@Override
	public void dealMsg(JSONObject msg, String username, String gameMapKey) {
		// TODO Auto-generated method stub
		String to=msg.getString("to");
		JSONObject comResult = new JSONObject();
        comResult.element("type", 71);
        comResult.element("from", username);
        try{
			WebSocketWuziqi webSocketTest=WebSocketWuziqi.socketMap.get(to);
			webSocketTest.sendMessage(comResult.toString());
			WuziqiGameState gameState=new WuziqiGameState();
			String[][] state=gameState.getState();
			for(int i=0;i<state.length;++i)
				for(int j=0;j<state[i].length;++j){
					state[i][j]="o";
				}
			gameState.setA(username);
			gameState.setB(to);
			gameState.setTurn(username);
			
			String key=username.compareTo(to)<0? username+" "+to:to+" "+username;
			gameMapKey=key;
			
			WebSocketWuziqi.gameMap.put(key, gameState);
			WebSocketWuziqi w1=WebSocketWuziqi.socketMap.get(username),w2=WebSocketWuziqi.socketMap.get(to);
			w1.setGameMapKey(gameMapKey);
			w2.setGameMapKey(gameMapKey);
			JSONObject gameObj=new JSONObject();
			gameObj.element("state", gameState.getState());
			gameObj.element("A", gameState.getA());
			gameObj.element("B", gameState.getB());
			gameObj.element("type", 8);
			gameObj.element("turn", gameState.getTurn());
			System.out.println(gameObj.toString());
			w1.sendMessage(gameObj.toString());
			w2.sendMessage(gameObj.toString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
