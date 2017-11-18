package com.wuziqiwebsocket;

import java.io.IOException;

import org.springframework.web.context.ContextLoader;

import com.websocket.MsgStrategy;

import po.WuziqiGameState;
import po.XiangqiGameState;
import po.XiangqiMove;
import net.sf.json.JSONObject;
//��һ��
public class WuziqiMsg24 implements MsgStrategy{

	public void dealMsg(JSONObject jsonObject,String username,String gameMapKey) {
		// TODO Auto-generated method stub
		try {
			int kind=jsonObject.getInt("kind");
			String to=jsonObject.getString("to");
			if(kind==1){
				int ju=jsonObject.getInt("ju");
				WuziqiGameState gameState=WebSocketWuziqi.gameMap.get(gameMapKey);
				gameState.recovery(ju-1);
				
				WebSocketWuziqi w1=WebSocketWuziqi.socketMap.get(username);
				WebSocketWuziqi w2=WebSocketWuziqi.socketMap.get(to);
	    		JSONObject msg=new JSONObject();
	    		msg.element("from", username);
	    		msg.element("to", to);
	    		msg.element("type", 25);
	    		msg.element("content",username+"ͬ����");
	    		
	    		JSONObject gameObj=new JSONObject();
	    		gameObj.element("state", gameState.getState());
				gameObj.element("A", gameState.getA());
				gameObj.element("B", gameState.getB());
				gameObj.element("type", 8);
				gameObj.element("turn", gameState.getTurn());
				System.out.println(gameObj.toString());
				w1.sendMessage(gameObj.toString());
				w2.sendMessage(gameObj.toString());
	    		
				w2.sendMessage(msg.toString());
			}else{
				WebSocketWuziqi socketTest=WebSocketWuziqi.socketMap.get(to);
	    		JSONObject msg=new JSONObject();
	    		msg.element("from", username);
	    		msg.element("to", to);
	    		msg.element("type", 25);
	    		msg.element("content",username+"�ܾ���");
				socketTest.sendMessage(msg.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
