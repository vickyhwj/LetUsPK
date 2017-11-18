package com.xiangqiwebsocket;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoader;

import com.websocket.MsgStrategy;

import po.XiangqiGameState;
import po.XiangqiMove;
import net.sf.json.JSONObject;
//��һ��
@Repository
public class XiangqiMsg11 implements MsgStrategy{
	@Override
	public void dealMsg(JSONObject jsonObject,String username,String gameMapKey) {
		// TODO Auto-generated method stub
		try {
			String to=jsonObject.getString("to");
    		WebSocketXiangqi.gameMap.remove(username+" "+to);
    		WebSocketXiangqi.gameMap.remove(to+" "+username);
    		WebSocketXiangqi socketTest=WebSocketXiangqi.socketMap.get(to);
    		jsonObject.element("from", username);
    		System.out.print("buwan"+jsonObject.toString());
			socketTest.sendMessage(jsonObject.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
