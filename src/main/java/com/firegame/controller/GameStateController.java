package com.firegame.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;

import com.wuziqiwebsocket.WebSocketWuziqi;
import com.xiangqiwebsocket.WebSocketXiangqi;

import pagination.Page;
import po.GameState;
import po.Message;
import po.User;
import po.UserCustom;
import po.WuziqiGameState;
import po.XiangqiGameState;
import po.Xielou;
import tool.BQueue;


@Controller
public class GameStateController extends BaseController{
	
	@RequestMapping("/removeXiangqiGameState")
	public void removeXiangqiGameState(HttpServletResponse response,HttpSession session,String userid) throws IOException{
		gameStateService.removeXiangqiGameState(userid);
		
		response.getWriter().print("ok");
	}
	@RequestMapping("/removeWuziqiGameState")
	public void removeWuziqiGameState(HttpServletResponse response,HttpSession session,String userid) throws IOException{
		gameStateService.removeWuziqiGameState(userid);
		
		response.getWriter().print("ok");
	}
	@RequestMapping("/getXiangqiKeyList")
	public void getXiangqiKeyList(HttpServletResponse response,int page,int len) throws IOException{
		int index=(page-1)*len;
		MongoTemplate mongoTemplate=(MongoTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("mongoTemplate");
		JSONObject resobj=new JSONObject();
		List<JSONObject> list= mongoTemplate.find(new Query().skip(index).limit(len), net.sf.json.JSONObject.class, "xiangqigamekey");
		resobj.element("list", list);
		long size=mongoTemplate.count(new Query(),"xiangqigamekey" );
		resobj.element("sum", (size-1)/len+1);
		resobj.element("now", page);
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");  
		response.getWriter().print(resobj);
	}
	
}
