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
import tool.BQueue;


@Controller
public class UserController extends BaseController{
	@Autowired
	SessionRegistry sessionRegistry;
	
	@RequestMapping("/loginxiangqi")
	public String loginxiangqi(String username,HttpServletResponse response,HttpSession session,HttpServletRequest request) throws IOException{
		request.setAttribute("username", username);
		request.setAttribute("list",relationshipService.selectUserListbyUserA(username));
		return "xiangqi/xiangqi.jsp";
	}
	@RequestMapping("/login")
	public String login(String username,HttpServletResponse response,HttpSession session,HttpServletRequest request) throws IOException{
		request.setAttribute("username", username);
		request.setAttribute("list",relationshipService.selectUserListbyUserA(username));
		return "index1.jsp";
	}
	@RequestMapping("/mobilelogin")
	public String mobilelogin(String username,HttpServletResponse response,HttpSession session,HttpServletRequest request) throws IOException{
		request.setAttribute("username", username);
		request.setAttribute("list",relationshipService.selectUserListbyUserA(username));
		return "mobilepk1.jsp";
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
	@RequestMapping("/loginByUserid")
	public String login1(String username,String password,HttpServletResponse response,HttpSession session,HttpServletRequest request) throws IOException, IllegalAccessException, InvocationTargetException{
		UserCustom userCustom=userService.selectUserMessageByUserId(username);
		
		if(userCustom!=null){
			System.out.print(userCustom);
			if(!userCustom.getPassword().equals(password)) return null;
			request.setAttribute("user", userCustom);
			request.setAttribute("messages",new JSONObject().element("messages", userCustom.getMessages()).toString());
			
		}
		return "userIndex.jsp";
	}
	@RequestMapping("/getFridendListJSON")
	public void getFridendListJSON(String userA,HttpServletResponse response) throws IOException{
		ArrayList<User> list=relationshipService.selectUserListbyUserA(userA);
		JSONObject jsonObject=new JSONObject();
		jsonObject.element("userids", list);
		response.getWriter().print(jsonObject.toString());
	}
	
	@RequestMapping("/deleteRelationship")
	public void deleteRelationship(String userA,String userB,HttpServletResponse response) throws IOException{
		relationshipService.deleteRelationship(userA, userB);
		response.getWriter().print("ok");
	}
	@RequestMapping("/testQueue")
	public void testQueue(HttpServletRequest request,HttpServletResponse response) throws InterruptedException, IOException{
		String out=BQueue.getQueue().take();
		response.getWriter().print(out);
	}
	@RequestMapping("/testPutQueue")
	public void testQueue2(HttpServletRequest request,HttpServletResponse response) throws InterruptedException, IOException{
		BQueue.getQueue().put("A");;
		response.getWriter().print("ok");
	}
	@RequestMapping("/xielou")
	public void testxielou(HttpServletResponse response) throws IOException{
		Xielou xielou=new Xielou();
		GameState[] gameStates=new GameState[1000];
		for(int i=0;i<gameStates.length;++i) gameStates[i]=new GameState();
		xielou.setGameStates(gameStates);
		Xielou.getList().add(xielou);
		response.getWriter().print("ok");
	}
	
	@RequestMapping("/getFriendListJson")
	public void getFriendListJson(String username,HttpServletResponse response) throws IOException{
		ArrayList<User> user=relationshipService.selectUserListbyUserA(username);
		JSONObject jsonObject=new JSONObject();
		
		ArrayList<String> list=new ArrayList<String>();
		for(User u:user){
			list.add(u.getUserid());
		}
		jsonObject.element("friendlist",list);
		response.getWriter().print(jsonObject.toString());
	}
	@RequestMapping("/loginByJson")
	public void loginByJson(String username,String password,HttpServletResponse response) throws IOException, IllegalAccessException, InvocationTargetException{
		UserCustom userCustom=userService.selectUserMessageByUserId(username);
		ArrayList<User> list = null;
		JSONObject jsonObject=new JSONObject();
		if(userCustom==null){
			list=userService.selectUserListbyUserid(username);
			if(list.size()==0||!password.equals(list.get(0).getPassword())){
				jsonObject.element("status", "error");
				response.getWriter().print(jsonObject);
				return;
			}
			userCustom=new UserCustom();
			userCustom.setUserid(list.get(0).getUserid());
			userCustom.setPassword(list.get(0).getPassword());
			userCustom.setWin(list.get(0).getWin());
			userCustom.setFail(list.get(0).getFail());
			userCustom.setMessages(new ArrayList<Message>());
			
		}
		
		jsonObject.element("status", "ok");
		ArrayList<User> user= relationshipService.selectUserListbyUserA(username);
		


		jsonObject.element("userCustom", userCustom);
		jsonObject.element("friendlist",user);
		response.getWriter().print(jsonObject.toString());
	}
	@RequestMapping("/userIndex")
	public String login1(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User user=(org.springframework.security.core.userdetails.User)authentication.getPrincipal();
		User myuser=null;
		if((myuser=userService.login(user.getUsername(), user.getPassword()))!=null){
				request.getSession().setAttribute("userid", myuser.getUserid());
				request.setAttribute("user", myuser);
				final List<SessionInformation> list=sessionRegistry.getAllSessions(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),false);
				for(final SessionInformation si:list){
				    si.expireNow();
				}
				sessionRegistry.registerNewSession(session.getId(),SecurityContextHolder.getContext().getAuthentication().getPrincipal() );
				session.setAttribute("userId", myuser.getUserid());
				return "userIndex1.jsp";
			}
			return null;
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
	@RequestMapping("/who")
	public void who(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.getWriter().print(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}
	
}
