package com.firegame.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.stereotype.Service;

import po.Authority;
import po.Role;
import po.User;
import po.UserRoles;
import po.Userrole;
import cache.JedisPoolUtils;

import com.firegame.service.ManagerService;
@Service("managerService")
public class ManagerServiceImpl extends BaseServiceImpl implements ManagerService{

	@Override
	public UserRoles findUserRoleByUserId(String userId) {
		// TODO Auto-generated method stub
		UserRoles userroles=securityDao.findUserRolesByUserId(userId);
		if(userroles==null){
			userroles=new UserRoles();
			User user=userDao.get(userId);
			userroles.setFail(user.getFail());
			userroles.setWin(user.getWin());
			userroles.setUserid(user.getUserid());
			userroles.setPassword(user.getPassword());
		}
		return userroles;
	}

	@Override
	public List<Role> loadAllRoles() {
		// TODO Auto-generated method stub
		System.out.println(userCache);
		return securityDao.findAllRoles();
		
	}

	@Override
	public void giveUserRole(String userid, String roleid) {
		// TODO Auto-generated method stub
		Map map=new HashMap<>();
		map.put("isrun", 1);
		map.put("userid", userid);
		map.put("roleid", roleid);
		int re=securityDao.updateUserRoleIsRun(map);
		if(re==0){
			Userrole userrole=new Userrole();
			userrole.setRoleid(roleid);
			userrole.setUserid(userid);
			userrole.setIsrun(1);
			securityDao.insertUserRole(userrole);
		}
		userCache.removeUserFromCache(userid);
		
	}

	@Override
	public void removeUserRole(String userid, String roleid) {
		// TODO Auto-generated method stub
		Map map=new HashMap<>();
		map.put("isrun", 0);
		map.put("userid", userid);
		map.put("roleid", roleid);
		int re=securityDao.updateUserRoleIsRun(map);
		userCache.removeUserFromCache(userid);
		
		
	}

	@Override
	public void removeUser(String userid) {
		// TODO Auto-generated method stub
		securityDao.removeUser(userid);
		userCache.removeUserFromCache(userid);
		
	}

	@Override
	public List<Authority> loadAllAuthoritys() {
		// TODO Auto-generated method stub
		return securityDao.findAllAuthority();
		
	}

	@Override
	public void removeAuthById(long id) {
		// TODO Auto-generated method stub
		securityDao.removeAuth(id);
		securityMetadataSource.loadResourceDefine();
		
		
	}

	@Override
	public void removeRole(String roleid) {
		// TODO Auto-generated method stub
		securityDao.removeRole(roleid);
		securityMetadataSource.loadResourceDefine();
		JedisPoolUtils.flushDB();
	}

	@Override
	public void addRole(String roleid, String description) {
		// TODO Auto-generated method stub
		Role role=new Role();
		role.setRoleid(roleid);
		role.setDescription(description);
		securityDao.insertRole(role);
		
	}

	@Override
	public void addAuthe(String url, String roleid) {
		// TODO Auto-generated method stub
		Authority authority=new Authority();
		authority.setUrl(url);
		Role role=new Role();
		role.setRoleid(roleid);
		authority.setRole(role);
		securityDao.insertAuth(authority);
		securityMetadataSource.loadResourceDefine();

		
	}

	
	

}
