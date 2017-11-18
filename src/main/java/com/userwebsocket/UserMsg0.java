package com.userwebsocket;

import org.springframework.web.context.ContextLoader;

import net.sf.json.JSONObject;

import com.firegame.service.MessageService;
import com.websocket.MsgStrategy;
//已读
public class UserMsg0 implements MsgStrategy{
	MessageService messageService=(MessageService) ContextLoader
			.getCurrentWebApplicationContext().getBean("messageService");
	@Override
	public void dealMsg(JSONObject msg, String username, String gameMapKey) {
		// TODO Auto-generated method stub
		int msgId=msg.getInt("msgId");
		messageService.isRead(msgId,1);
	}

}
