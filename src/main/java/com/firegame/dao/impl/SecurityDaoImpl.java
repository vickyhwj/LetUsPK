package com.firegame.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.firegame.dao.BaseDao;
import com.firegame.dao.SecurityDao;

import po.Authority;
import po.Role;
import po.User;
import po.UserRoles;
import po.Userrole;
@Repository("securityDao")
public class SecurityDaoImpl extends SqlSessionDaoSupport implements SecurityDao{
	String ns="com.firegame.mapper.SecurityMapper";
	@Autowired
	//mybatis-spring 1.0无需此方法；mybatis-spring1.2必须注入。
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	@Override
	public List<Authority> findAllAuthority(){
		List<Authority> list=this.getSqlSession().selectList(ns+".findAllAuthority");
		return list;
	}
	@Override
	public UserRoles findUserRolesByUserId(String userid) {
		// TODO Auto-generated method stub
		UserRoles userRoles=this.getSqlSession().selectOne(ns+".findUserRolesByUserId",userid);
		return userRoles;
	}
	@Override
	public List<Map> findUserRolesMapByUserId(String userid) {
		// TODO Auto-generated method stub
		List<Map> map= this.getSqlSession().selectList(ns+".findUserRolesMapByUserId",userid);
		return map;
	}
	@Override
	public List<Role> findAllRoles() {
		// TODO Auto-generated method stub
		List<Role> list= this.getSqlSession().selectList(ns+".findAllRoles");
		return list;
	}
	@Override
	public int updateUserRoleIsRun(Map map) {
		// TODO Auto-generated method stub
		Integer re=this.getSqlSession().update(ns+".updateUserRoleIsRun",map);
		return re;
	}
	@Override
	public int insertUserRole(Userrole userrole) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(ns+".insertUserRole", userrole);
	
	}
	@Override
	public void removeUser(String userid) {
		// TODO Auto-generated method stub
		this.getSqlSession().selectOne(ns+".removeUser", userid);
		
	}
	@Override
	public void removeRole(String roleid) {
		// TODO Auto-generated method stub
		this.getSqlSession().selectOne(ns+".removeRole",roleid);
	}
	@Override
	public void removeAuth(Long id) {
		// TODO Auto-generated method stub
		this.getSqlSession().delete(ns+".removeAuth",id);
		
	}
	@Override
	public void insertRole(Role role) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(ns+".insertRole",role);
		
	}
	@Override
	public void insertAuth(Authority authority) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(ns+".insertAuth", authority);
	}
	
	
	
}
