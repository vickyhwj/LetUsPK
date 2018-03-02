package com.firegame.controller;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pagination.Page;
import po.Authority;
import po.Role;
import po.User;
import po.UserRoles;
import po.Userrole;
import net.sf.json.JSONObject;

import com.google.gson.JsonObject;
@Controller
public class ManagerController extends BaseController{
	@RequestMapping("/loadUserAllDetailByUserId")
	public void loadUserAllDetailByUserId(String userId,HttpServletResponse response) throws IOException{
		UserRoles userRoles=managerService.findUserRoleByUserId(userId);
		List<User> friemdList=relationshipService.selectUserListbyUserA(userId);
		JSONObject jsonObject=new JSONObject();
		jsonObject.element("userRoles", userRoles);
		jsonObject.element("friendList", friemdList);
		response.getWriter().print(jsonObject.toString());
	}
	
	@RequestMapping("/ManagerUser")
	public String getAllRole(String userId,HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<Role> list=managerService.loadAllRoles();
		request.setAttribute("roles", list);
		return "manager/user.jsp";
	}
	
	@RequestMapping("/giveUserRole")
	public void giveUserRole(String userid,String roleid,HttpServletResponse response){
		managerService.giveUserRole(userid, roleid);
	}
	
	@RequestMapping("/removeUserRole")
	public void removeUserRole(String userid,String roleid,HttpServletResponse response){
		managerService.removeUserRole(userid, roleid);
	
	}
	
	@RequestMapping("/removeUser")
	public void removeUser(String userid,HttpServletResponse response) throws IOException{
		managerService.removeUser(userid);
		response.sendRedirect("ManagerUser");
	}
	private Set getRequestUrlByClass(Class<?> clazz){
		Set set=new HashSet<>();
		for(Method method: clazz.getMethods()){
			for(Annotation annotation:method.getAnnotations()){
				if(annotation.annotationType().equals(RequestMapping.class))
					for(String v:((RequestMapping) annotation).value())
						set.add(v);
			}
		}
		return set;
	}
	
	@RequestMapping("/ManagerAuth")
	public String loadAuthRole(HttpServletRequest request,HttpServletResponse response){
		List<Role> roles=managerService.loadAllRoles();
		List<Authority> auths=managerService.loadAllAuthoritys();
		request.setAttribute("roles", roles);
		request.setAttribute("auths", auths);
		Set set=new HashSet<>();
		set.addAll(getRequestUrlByClass(ManagerController.class));
		set.addAll(getRequestUrlByClass(UserController.class));
		set.addAll(getRequestUrlByClass(GameStateController.class));
		request.setAttribute("urls", set);
		return "manager/auth.jsp";
	}
	@RequestMapping("/delAuth")
	public void removeAuth(long id ,HttpServletResponse response) throws IOException{
		managerService.removeAuthById(id);
		response.sendRedirect("ManagerAuth");
	}
	@RequestMapping("/delRole")
	public void removeRole(String roleid ,HttpServletResponse response) throws IOException{
		managerService.removeRole(roleid);
		response.sendRedirect("ManagerAuth");
	}
	@RequestMapping("/addRole")
	public void addRole(String roleid ,String description,HttpServletResponse response) throws IOException{
		managerService.addRole(roleid,description);
		response.sendRedirect("ManagerAuth");
	}
	@RequestMapping("/addAuth")
	public void addAuth(String url ,String roleid,HttpServletResponse response) throws IOException{
		String[] u=url.split(",");
		for(String uu:u)
			if(!"".equals(uu))
				managerService.addAuthe(uu,roleid);
		response.sendRedirect("ManagerAuth");
	}
}
