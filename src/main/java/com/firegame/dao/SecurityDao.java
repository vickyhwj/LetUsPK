package com.firegame.dao;

import java.util.List;
import java.util.Map;

import po.Authority;
import po.Role;
import po.UserRoles;
import po.Userrole;

public interface SecurityDao {
	public List<Authority> findAllAuthority();
	public UserRoles findUserRolesByUserId(String userid);
	public List<Map> findUserRolesMapByUserId(String userid);
	public List<Role> findAllRoles();
	public int updateUserRoleIsRun(Map map);
	public int insertUserRole(Userrole userrole);
	public void removeUser(String userid);
	public void removeRole(String roleid);
	public void removeAuth(Long id);
	public void insertRole(Role role);
	public void insertAuth(Authority authority);
}
