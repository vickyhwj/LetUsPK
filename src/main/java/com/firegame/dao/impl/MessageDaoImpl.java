package com.firegame.dao.impl;

import org.springframework.stereotype.Repository;

import com.firegame.dao.MessageDao;

import po.Message;
@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao{
	public MessageDaoImpl(){
		super.setNs("com.firegame.mapper.MessageMapper");
	}

	

	
}
