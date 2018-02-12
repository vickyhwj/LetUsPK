package com.firegame.dao;

import java.util.List;
import java.util.Map;

import po.Authority;
import po.UserRoles;

public interface SecurityDao {
	public List<Authority> findAllAuthority();
	public UserRoles findUserRolesByUserId(String userid);
	public List<Map> findUserRolesMapByUserId(String userid);
}
