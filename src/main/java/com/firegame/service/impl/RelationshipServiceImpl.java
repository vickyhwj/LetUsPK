package com.firegame.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.firegame.service.RelationshipService;

import po.Relationship;
import po.User;

@Service("relationshipService")
public class RelationshipServiceImpl extends BaseServiceImpl implements RelationshipService {
	@Override
	public ArrayList<User> selectUserListbyUserA(String userA){
		
		 List list= relationshipDao.selectUserListbyUserA(userA);
		 return (ArrayList<User>) list;
	}
	@Override
	public void insertRelationship(String userA,String userB){
		HashMap<String, String> map=new HashMap<>();
		if(userA.compareTo(userB)>0){
			String temp=userA;
			userA=userB;
			userB=temp;
		}
		map.put("userA",userA);
		map.put("userB",userB);
		if(relationshipDao.find(map).size()==0){
			Relationship relationship=new Relationship();
			relationship.setUserA(userA);
			relationship.setUserB(userB);
			relationshipDao.insert(relationship);
		

		}
	}
	@Override
	public void deleteRelationship(String userA ,String userB){
		if(userA.compareTo(userB)>0){
			String temp=userA;
			userA=userB;
			userB=temp;
		}
		HashMap<String, String> map=new HashMap<>();
		map.put("userA",userA);
		map.put("userB",userB);
		relationshipDao.deleteRelationshipByAB(map);
	}
}
