package com.firegame.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import pagination.Page;
import po.User;
import po.UserCustom;


public interface UserService  {
	

	boolean register(String username, String password);

	ArrayList<User> selectUserRank(Integer index, Integer len, Boolean desc);

	UserCustom selectUserMessageByUserId(String userId)
			throws IllegalAccessException, InvocationTargetException;

	ArrayList<User> selectUserListbyUserid_page(String userid, int index,
			int len, Page<User> page);

	ArrayList<User> selectUserListbyUserid(String userid);

	User login(String userid, String password);

}
