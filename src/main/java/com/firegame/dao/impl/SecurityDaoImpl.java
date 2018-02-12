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
	
	
	
}
