package com.firegame.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import redis.clients.jedis.Jedis;
import cache.JedisPoolUtils;
import cache.JedisPoolUtils.WorkRunnable;

import com.firegame.service.RegisterWork;
import com.firegame.service.impl.User2ServiceImpl;
import com.firegame.service.impl.UserServiceImpl;

@Controller
public class User2Controller extends UserController{

	@Override
	@RequestMapping("/register")
	public void register(String username,String password,HttpServletResponse response) throws IOException{
		((User2ServiceImpl)userService).registerTemp(username, password);
		response.getWriter().print("verify java mail");
		
	}
	@RequestMapping("/verify")
	public void verify(final String urlKey,HttpServletResponse response) throws IOException{
		List list=JedisPoolUtils.work(new WorkRunnable<List>() {

			@Override
			public List run(Jedis jedis) {
				// TODO Auto-generated method stub
				List list=jedis.hmget(urlKey, RegisterWork.USERNAME,RegisterWork.PASSWORD);
				return list;
			}
		});
		if(list==null||list.size()==0){
			response.getWriter().print("error");
		}else{
			userService.register((String)list.get(0), (String)list.get(1));
			managerService.giveUserRole((String)list.get(0), "ROLE_USER");
			response.getWriter().print("succ");
		}
	}
	
}
