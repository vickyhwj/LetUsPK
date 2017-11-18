package com.firegame.service.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import po.Message;

import com.firegame.service.MessageService;
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl implements MessageService{
	@Override
	public Message insertMessage(String from,String to,String content,int type,int isRead,Timestamp createDate){
		Message message=new Message();
		message.setFrom(from);
		message.setTo(to);
		message.setContent(content);
		message.setType(type);
		message.setIsRead(isRead);
		message.setCreateDate(createDate);
		messageDao.insert(message);
		return message;
	}
	@Override
	public void isRead(int msgId,int isRead){
		Message message=new Message();
		message.setIsRead(1);
		message.setMsgId(msgId);
		messageDao.update(message);
	}
}
