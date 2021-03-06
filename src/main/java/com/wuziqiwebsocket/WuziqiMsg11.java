package com.wuziqiwebsocket;

import java.io.IOException;

import org.springframework.web.context.ContextLoader;

import po.GameState;
import po.WuziqiGameState;
import po.WuziqiMove;
import po.XiangqiMove;
import net.sf.json.JSONObject;

import com.firegame.dao.UserDao;
import com.websocket.MsgStrategy;
//拒绝对战
public class WuziqiMsg11 implements MsgStrategy{
	UserDao userDao= (UserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");

	@Override
	public void dealMsg(JSONObject msg, String username, String gameMapKey) {
		// TODO Auto-generated method stub
		try {
			String to=msg.getString("to");
    		WebSocketWuziqi.gameMap.remove(username+" "+to);
    		WebSocketWuziqi.gameMap.remove(to+" "+username);
    		WebSocketWuziqi socketTest=WebSocketWuziqi.socketMap.get(to);
    		msg.element("from", username);
    		System.out.print("buwan"+msg.toString());
			socketTest.sendMessage(msg.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
