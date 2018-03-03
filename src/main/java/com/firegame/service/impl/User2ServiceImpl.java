package com.firegame.service.impl;

import java.util.HashMap;
import java.util.Map;

import mail.JavaMail;
import mail.MailObj;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import tool.AsynUtils;
import tool.MD5Utils;
import cache.JedisPoolUtils;
import cache.JedisPoolUtils.WorkRunnable;

import com.firegame.service.RegisterWork;
import com.firegame.service.impl.UserServiceImpl;
@Repository("userService")
public class User2ServiceImpl extends UserServiceImpl {
	JavaMail javaMail=mail.JavaMailSender.INSTANCE;
	String registerUrl="http://localhost:8080/ssmws/verify";

	
	public boolean registerTemp(final String username, final String password) {
		// TODO Auto-generated method stub
	
		final String urlKey=JedisPoolUtils.work(new WorkRunnable<String>() {

			@Override
			public String run(Jedis jedis) {
				// TODO Auto-generated method stub
				String key=MD5Utils.md5(RegisterWork.REG_PRE+username);
				Map<String, String> map=new HashMap<String,String>();
				map.put(RegisterWork.USERNAME, username);
				map.put(RegisterWork.PASSWORD, password);
				jedis.hmset(key, map);
				jedis.expire(key, 100);
				return key;
			}
		});
		AsynUtils.executor.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				MailObj mailObj=new MailObj();
				mailObj.setTo(username);
				mailObj.setSubject("register");
				mailObj.setText(registerUrl+"?urlKey="+urlKey);
				try {
					javaMail.sendMail(mailObj);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		return true;
	}

	
}
