package com.firegame.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import po.Relationship;
import po.User;

public interface RelationshipDao extends BaseDao<Relationship>{

	void deleteRelationshipByAB(HashMap<String, String> map);
	List selectUserListbyUserA(String userA);
}
