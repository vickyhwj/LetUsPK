package com.firegame.service;

import java.util.List;

import po.Authority;
import po.Role;
import po.User;
import po.UserRoles;
import po.Userrole;

public interface ManagerService {

	UserRoles findUserRoleByUserId(String userId);
	
	List<Role> loadAllRoles();
	
	void giveUserRole(String userid,String roleid);
	
	void removeUserRole(String userid,String roleid);
	
	void removeUser(String userid);
	
	List<Authority> loadAllAuthoritys();

	void removeAuthById(long id);

	void removeRole(String roleid);

	void addRole(String roleid, String description);

	void addAuthe(String url, String roleid);

}
