package com.wuziqiwebsocket;

import net.sf.json.JSONObject;

import com.websocket.MsgStrategy;
//邀请对方
public class WuziqiMsg4 implements MsgStrategy{

	@Override
	public void dealMsg(JSONObject msg, String username, String gameMapKey) {
		// TODO Auto-generated method stub
		String to=msg.getString("to");
		JSONObject comResult = new JSONObject();
        comResult.element("type", 5);
        comResult.element("from", username);
		try{
			WebSocketWuziqi webSocketTest=WebSocketWuziqi.socketMap.get(to);
			webSocketTest.sendMessage(comResult.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
