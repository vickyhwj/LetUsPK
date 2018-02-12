package service;

import java.sql.Timestamp;
import java.util.ArrayList;








import po.Message;
import po.User;
import mapper.MessageMapper;
import mapper.RelationshipMapper;

public class MessageService {
	MessageMapper messageMapper;

	public void setMessageMapper(MessageMapper messageMapper) {
		this.messageMapper = messageMapper;
	}
	public Message insertMessage(String from,String to,String content,int type,int isRead,Timestamp createDate){
		Message message=new Message();
		message.setFrom(from);
		message.setTo(to);
		message.setContent(content);
		message.setType(type);
		message.setIsRead(isRead);
		message.setCreateDate(createDate);
		messageMapper.insertMessage(message);
		return message;
	}
	public void isRead(int msgId){
		Message message=new Message();
		message.setIsRead(1);
		message.setMsgId(msgId);
		messageMapper.updataMessage(message);
	}
}
