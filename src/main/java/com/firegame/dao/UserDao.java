package com.firegame.dao;

import po.User;

public interface UserDao extends BaseDao<User>{
	void  updateWinAddOne(String userid);
	void updateFaillAddOne(String userid);
	
}
