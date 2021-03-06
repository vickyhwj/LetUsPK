package com.firegame.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.firegame.dao.RelationshipDao;
import com.mysql.fabric.xmlrpc.base.Array;

import po.Relationship;
import po.User;
@Repository("relationshipDao")
public class RelationshipDaoImpl extends BaseDaoImpl<Relationship> implements RelationshipDao {
	public RelationshipDaoImpl(){
		super.setNs("com.firgame.mapper.RelationshipMapper");
	}

	@Override
	public void deleteRelationshipByAB(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		super.getSqlSession().delete(super.getNs() + ".deleteRelationshipByAB", map);
		
	}

	@Override
	public List selectUserListbyUserA(String userA) {
		// TODO Auto-generated method stub
		List list= super.getSqlSession().selectList(super.getNs() + ".selectUserListbyUserA", userA);
		
		return  list;
	}
}
