package com.firegame.controller;

import java.util.ArrayList;

import mongodb.dao.TestDao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import po.GameState;



public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext-*.xml");
		TestDao testDao=(TestDao) ac.getBean("testDao");
		testDao.insertBook();
		GameState gameState=testDao.getBook();
		System.out.print(gameState);
	}

}
