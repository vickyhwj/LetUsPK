package com.wuziqiwebsocket;

import org.springframework.web.context.ContextLoader;

import po.GameState;
import po.WuziqiGameState;
import po.WuziqiMove;
import po.XiangqiMove;
import net.sf.json.JSONObject;

import com.firegame.dao.UserDao;
import com.websocket.MsgStrategy;

//拒绝对战
public class WuziqiMsg9 implements MsgStrategy{
	UserDao userDao= (UserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");

	@Override
	public void dealMsg(JSONObject msg, String username, String gameMapKey) {
		// TODO Auto-generated method stub
		try{
			String to=msg.getString("to");
    		int x=msg.getInt("x");
    		int y=msg.getInt("y");
    		String key;
    		if(username.compareTo(to)<0) key=username+" "+to;
    		else key=to+" "+username;
    		
    		WuziqiGameState gameState=WebSocketWuziqi.gameMap.get(key);
    		gameState.changeTurn();
    		WebSocketWuziqi w1=WebSocketWuziqi.socketMap.get(username),w2=WebSocketWuziqi.socketMap.get(to);
			JSONObject gameObj=new JSONObject();
			WuziqiMove move=new WuziqiMove();
			move.setX(x);
			move.setY(y);
			move.setUserid(username);
			if(gameState.play(username, move)){
				gameObj.element("winner", username);
				userDao.updateWinAddOne(username);
				if(username.equals(gameState.getA()))
					userDao.updateFaillAddOne(gameState.getB());
				else
					userDao.updateFaillAddOne(gameState.getA());
			}
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
