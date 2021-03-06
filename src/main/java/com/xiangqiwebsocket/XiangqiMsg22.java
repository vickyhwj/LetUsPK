package com.xiangqiwebsocket;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoader;

import com.websocket.MsgStrategy;

import po.XiangqiGameState;
import po.XiangqiMove;
import net.sf.json.JSONObject;
//悔棋请求
@Repository
public class XiangqiMsg22 implements MsgStrategy{

	public void dealMsg(JSONObject jsonObject,String username,String gameMapKey) {
		// TODO Auto-generated method stub
		try {
			String to=jsonObject.getString("to");
    		WebSocketXiangqi socketTest=WebSocketXiangqi.socketMap.get(to);
    		JSONObject msg=new JSONObject();
    		msg.element("from", username);
    		msg.element("to", to);
    		msg.element("type", 23);
    		msg.element("ju", jsonObject.getInt("ju"));
			socketTest.sendMessage(msg.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
