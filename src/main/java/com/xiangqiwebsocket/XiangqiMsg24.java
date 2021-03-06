package com.xiangqiwebsocket;

import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.ContextLoader;

import com.websocket.MsgStrategy;

import po.XiangqiGameState;
import po.XiangqiMove;
import net.sf.json.JSONObject;
//同意悔棋
@Repository
public class XiangqiMsg24 implements MsgStrategy{

	public void dealMsg(JSONObject jsonObject,String username,String gameMapKey) {
		// TODO Auto-generated method stub
		try {
			int kind=jsonObject.getInt("kind");
			String to=jsonObject.getString("to");
			if(kind==1){
				int ju=jsonObject.getInt("ju");
				XiangqiGameState gameState=WebSocketXiangqi.gameMap.get(gameMapKey);
				gameState.recovery(ju-1);
				
				WebSocketXiangqi w1=WebSocketXiangqi.socketMap.get(username);
	    		WebSocketXiangqi w2=WebSocketXiangqi.socketMap.get(to);
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
				WebSocketXiangqi socketTest=WebSocketXiangqi.socketMap.get(to);
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
