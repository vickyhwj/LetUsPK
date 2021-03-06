package com.firegame.dao.impl;

import org.springframework.stereotype.Repository;

import com.firegame.dao.UserDao;

import po.User;
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{
	public UserDaoImpl(){
		super.setNs("com.firegame.mapper.UserMapper");
	}

	@Override
	public void updateWinAddOne(String userid) {
		// TODO Auto-generated method stub
		super.getSqlSession().update(super.getNs()+".updateWinAddOne", userid);
		
	}

	@Override
	public void updateFaillAddOne(String userid) {
		// TODO Auto-generated method stub
		super.getSqlSession().update(super.getNs()+".updateFaillAddOne", userid);

	}
}
