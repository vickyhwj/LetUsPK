package com.firegame.service;

import java.util.ArrayList;

import po.User;

public interface RelationshipService {

	ArrayList<User> selectUserListbyUserA(String userA);

	void deleteRelationship(String userA, String userB);

	void insertRelationship(String userA, String userB);

}
