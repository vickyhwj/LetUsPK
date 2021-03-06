package com.xiangqiwebsocket;

import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.TimerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoader;

import com.firegame.dao.UserDao;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.websocket.MsgStrategy;

import po.AbstractGameState;
import po.XiangqiGameState;
import po.XiangqiMove;
import net.sf.json.JSONObject;

//下一步
@Repository
public class XiangqiMsg9 implements MsgStrategy {
	@Autowired
	UserDao userDao;

	public void dealMsg(JSONObject jsonObject, String username,
			String gameMapKey) {
		// TODO Auto-generated method stub
		try {
			String to = jsonObject.getString("to");

			String key;
			if (username.compareTo(to) < 0)
				key = username + " " + to;
			else
				key = to + " " + username;

			XiangqiGameState gameState = WebSocketXiangqi.gameMap.get(key);

			WebSocketXiangqi w1 = WebSocketXiangqi.socketMap.get(username), w2 = WebSocketXiangqi.socketMap
					.get(to);
			JSONObject gameObj = new JSONObject();
			w2.setGameMapKey(key);

			int startx = jsonObject.getInt("startx");
			int starty = jsonObject.getInt("starty");
			int endx = jsonObject.getInt("endx");
			int endy = jsonObject.getInt("endy");
			XiangqiMove xiangqiMove = new XiangqiMove();
			xiangqiMove.setUserid(username);
			xiangqiMove.setStartx(startx);
			xiangqiMove.setStarty(starty);
			xiangqiMove.setEndx(endx);
			xiangqiMove.setEndy(endy);

			if (gameState.play(username, xiangqiMove) == true) {
				gameObj.element("winner", username);
				userDao.updateWinAddOne(username);
				if (username.equals(gameState.getA()))
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
			
			createTimer(gameState, w1, w2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 计时器
	public void createTimer(XiangqiGameState gameState, WebSocketXiangqi w1,
			WebSocketXiangqi w2) {
	
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
