package com.firegame.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import pagination.Page;
import po.Message;
import po.User;
import po.UserCustom;
import po.UserVo;
import po.XiangqiGameState;

import com.firegame.dao.MessageDao;
import com.firegame.dao.UserDao;
import com.firegame.service.UserService;
import com.xiangqiwebsocket.WebSocketXiangqi;
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	
	@Override
	public User login(String userid,String password){
		User user=userDao.get(userid);
		if(user==null||!password.equals(user.getPassword())) return null;
		return user;
	}
	@Override
	public ArrayList<User> selectUserListbyUserid(String userid){
		HashMap<String, Object> map=new HashMap<>();
		map.put("userid", userid);
		return (ArrayList<User>) userDao.find(map);
	}
//	@Secured("ROLE_FIND")
	@Override
	public ArrayList<User> selectUserListbyUserid_page(String userid,int index,int len,Page<User> page){
		
		
		HashMap<String, Object> map=new HashMap<>();
		map.put("userid", userid);
		page.setParams(map);
		page.setPageSize(len);
		page.setPageNo(index);
		return (ArrayList<User>) userDao.findPage(page);
	}
	@Override
	public UserCustom selectUserMessageByUserId(String userId) throws IllegalAccessException, InvocationTargetException{
		
		User user=userDao.get(userId);
		HashMap<String, Object> map=new HashMap<>();
		map.put("to", userId);
		map.put("isRead", 0);
		UserCustom userCustom=new UserCustom();
		BeanUtils.copyProperties(userCustom, user);
		userCustom.setMessages((ArrayList<Message>) messageDao.find(map));
		return userCustom;
	}
	@Override
	public ArrayList<User> selectUserRank(Integer index,Integer len,Boolean desc){
		Page<User> page=new Page<>();
		page.setPageNo(1);
		page.setPageSize(len);
		HashMap<String, Object> map=new HashMap<>();
		map.put("desc", desc);
		page.setParams(map);
		ArrayList<User> list= (ArrayList<User>) userDao.findPage(page);
		return list;
	}
	@Override
	public boolean register(String username,String password){
		if(username==null||"".equals(username)) return false;
		if(password==null||"".equals(password)) return false;
		User user=new User();
		user.setUserid(username);
		user.setPassword(password);
		try{
			userDao.insert(user);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
}
