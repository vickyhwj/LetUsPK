package com.firegame.service.impl;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserCache;

import security.MyInvocationSecurityMetadataSource;

import com.firegame.dao.GameStateDao;
import com.firegame.dao.MessageDao;
import com.firegame.dao.RelationshipDao;
import com.firegame.dao.SecurityDao;
import com.firegame.dao.UserDao;

public class BaseServiceImpl {
	@Resource
	UserDao userDao;
	@Resource
	GameStateDao gameStateDao;
	@Resource
	RelationshipDao relationshipDao;
	@Resource
	MessageDao messageDao;
	@Resource
	SecurityDao securityDao;
	@Resource
	UserCache userCache;
	@Resource
	MyInvocationSecurityMetadataSource securityMetadataSource;
}
