package com.userwebsocket;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.web.context.ContextLoader;

import po.Message;
import net.sf.json.JSONObject;

import com.firegame.service.MessageService;
import com.websocket.MsgStrategy;
import com.xiangqiwebsocket.WebSocketMsg;
//邀请
public class UserMsg1 implements MsgStrategy{
	MessageService messageService=(MessageService) ContextLoader
			.getCurrentWebApplicationContext().getBean("messageService");
	@Override
	public void dealMsg(JSONObject msg, String username, String gameMapKey) {
		// TODO Auto-generated method stub
		String to=msg.getString("to");
		Message mm= messageService.insertMessage(username, to, "", msg.getInt("type"), 0,new Timestamp(new Date().getTime()));
    	WebSocketUser socketMsg=WebSocketUser.socketMap.get(to);
		msg.element("to", to);
		msg.element("msgId", mm.getMsgId());
		msg.element("type", msg.getInt("type"));
		msg.element("from", username);
		msg.element("content","" );
		msg.element("createDate", mm.getCreateDate());
		try{
			socketMsg.sendMessage(msg.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
