package com.userwebsocket;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.web.context.ContextLoader;

import po.Message;
import net.sf.json.JSONObject;

import com.firegame.service.MessageService;
import com.firegame.service.RelationshipService;
import com.websocket.MsgStrategy;
import com.xiangqiwebsocket.WebSocketMsg;
//接受
public class UserMsg2 implements MsgStrategy{
	MessageService messageService=(MessageService) ContextLoader
			.getCurrentWebApplicationContext().getBean("messageService");
	RelationshipService relationshipService=(RelationshipService) ContextLoader.getCurrentWebApplicationContext().getBean("relationshipService");

	@Override
	public void dealMsg(JSONObject msg, String username, String gameMapKey) {
		// TODO Auto-generated method stub
		String to=msg.getString("to");
		int msgId=msg.getInt("msgId");
		messageService.isRead(msgId,1);
		relationshipService.insertRelationship(username, to);
		WebSocketUser socketMsg2=WebSocketUser.socketMap.get(username);
		JSONObject jsonObject2=new JSONObject();
		jsonObject2.element("type", 22);
    	Message mm= messageService.insertMessage(username, to, "", 2, 0,new Timestamp(new Date().getTime()));
    	WebSocketUser socketMsg=WebSocketUser.socketMap.get(to);
		msg.element("to", to);
		msg.element("msgId", mm.getMsgId());
		msg.element("type", 2);
		msg.element("from", username);
		msg.element("content","" );
		msg.element("createDate", mm.getCreateDate());
		
		try {
			socketMsg2.sendMessage(jsonObject2.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			socketMsg.sendMessage(msg.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
