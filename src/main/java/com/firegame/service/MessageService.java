package com.firegame.service;

import java.sql.Timestamp;

import po.Message;

public interface MessageService {

	Message insertMessage(String from, String to, String content, int type,
			int isRead, Timestamp createDate);

	void isRead(int msgId,int isRead);

}
