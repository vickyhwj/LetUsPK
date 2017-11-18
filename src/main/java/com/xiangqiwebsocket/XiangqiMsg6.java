package com.xiangqiwebsocket;

import org.springframework.stereotype.Repository;

import com.websocket.MsgStrategy;

import po.XiangqiGameState;
import net.sf.json.JSONObject;
//�ܾ��ս
@Repository
public class XiangqiMsg6 implements MsgStrategy{

	public void dealMsg(JSONObject jsonObject,String username,String gameMapKey) {
		// TODO Auto-generated method stub
		String to=jsonObject.getString("to");
		JSONObject comResult = new JSONObject();
        comResult.element("type", 61);
        comResult.element("from", username);
        try{
			WebSocketXiangqi webSocketTest=WebSocketXiangqi.socketMap.get(to);
			webSocketTest.sendMessage(comResult.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
