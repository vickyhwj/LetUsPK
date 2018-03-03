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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
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
import tool.AuthenticationUtil;
import tool.BQueue;


//@Controller
public class UserController extends BaseController{
	@Autowired
	SessionRegistry sessionRegistry;
	
	@RequestMapping("/loginxiangqi")
	public String loginxiangqi(HttpServletResponse response,HttpSession session,HttpServletRequest request) throws IOException{
		request.setAttribute("username", AuthenticationUtil.getUser().getUsername());
		request.setAttribute("list",relationshipService.selectUserListbyUserA(AuthenticationUtil.getUser().getUsername()));
		return "xiangqi/xiangqi.jsp";
	}
	
	@RequestMapping("/loginwuziqi")
	public String loginwuziqi(String username,HttpServletResponse response,HttpSession session,HttpServletRequest request) throws IOException{
		request.setAttribute("username", AuthenticationUtil.getUser().getUsername());
		request.setAttribute("list",relationshipService.selectUserListbyUserA(AuthenticationUtil.getUser().getUsername()));
		return "wuziqi/wuziqi.jsp";
	}
	@RequestMapping("/getUserListByUserId")
	public void getUserListByUserId(HttpServletRequest request,HttpServletResponse response,String userId,int index,int len) throws IOException{
		Page<User> page=new Page<>();
		
		ArrayList<User> list= userService.selectUserListbyUserid_page("%"+userId+"%",index,len,page);
		JSONObject jsonObject=new JSONObject();
		jsonObject.element("userlist", list);
		jsonObject.element("sum", page.getTotalPage());
		jsonObject.element("now", index);
		response.getWriter().print(jsonObject.toString());
	}
	@RequestMapping("/getUserRank")
	public void getUserRank(HttpServletResponse response,String userId,int index,int len) throws IOException{
		ArrayList<User> list= userService.selectUserRank(index ,len, true);
		JSONObject jsonObject=new JSONObject();
		jsonObject.element("userlist", list);
		response.getWriter().print(jsonObject.toString());
	}
	@RequestMapping("/getFridendListJSON")
	public void getFridendListJSON(HttpServletResponse response) throws IOException{
		ArrayList<User> list=relationshipService.selectUserListbyUserA(AuthenticationUtil.getUser().getUsername());
		JSONObject jsonObject=new JSONObject();
		jsonObject.element("userids", list);
		response.getWriter().print(jsonObject.toString());
	}
	
	@RequestMapping("/deleteRelationship")
	public void deleteRelationship(String userA,String userB,HttpServletResponse response) throws IOException{
		relationshipService.deleteRelationship(userA, userB);
		response.getWriter().print("ok");
	}

	
	
	@RequestMapping("/getLoginUserDetail")
	public void getUserDetail(HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, IOException{
		org.springframework.security.core.userdetails.User user=AuthenticationUtil.getUser();
		UserCustom userCustom=userService.selectUserMessageByUserId(user.getUsername());
		JSONObject jsonObject=new JSONObject();
		jsonObject.element("status", "ok");
		ArrayList<User> friends= relationshipService.selectUserListbyUserA(user.getUsername());
		jsonObject.element("userCustom", userCustom);
		jsonObject.element("friendlist",friends);
		response.getWriter().print(jsonObject.toString());
	}
	
	
	@RequestMapping("/userIndex")
	public String loginIndex(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		org.springframework.security.core.userdetails.User user=AuthenticationUtil.getUser();
		AuthenticationUtil.expireLastUser(sessionRegistry);
		sessionRegistry.registerNewSession(session.getId(),AuthenticationUtil.getUser());
		session.setAttribute("userId", user.getUsername());
		return "userIndex.jsp";
		
	}
	
	
	@RequestMapping("/getRecords")
	public void getRecords(HttpServletResponse response,HttpSession session,String userid,String type) throws IOException{
		if("xiangqi".equals(type)){
			String key=WebSocketXiangqi.socketMap.get(userid).getGameMapKey();
			XiangqiGameState gameState= WebSocketXiangqi.gameMap.get(key);
			int size=gameState.getPlayRecord().getList().size();
			response.getWriter().print(size);
		}
		else if("wuziqi".equals(type)){
			String key=WebSocketWuziqi.socketMap.get(userid).getGameMapKey();
			WuziqiGameState gameState= WebSocketWuziqi.gameMap.get(key);
			int size=gameState.getPlayRecord().getList().size();
			response.getWriter().print(size);
		}
	}
	@RequestMapping("/outLogin")
	public void outLogin(HttpServletRequest request,HttpServletResponse response){
		
	}
	
	@RequestMapping("/register")
	public void register(String username,String password,HttpServletResponse response) throws IOException{
		if(userService.register(username, password)){
			managerService.giveUserRole(username, "ROLE_USER");
			response.getWriter().print("succ");
		}
		else 
			response.getWriter().print("error");

	}
	
}
