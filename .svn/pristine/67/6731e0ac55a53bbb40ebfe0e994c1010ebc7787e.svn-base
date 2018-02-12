package service;

import java.util.ArrayList;






import po.Relationship;
import po.User;
import mapper.RelationshipMapper;

public class RelationshipService {
	RelationshipMapper relationshipMapper;

	public void setRelationshipMapper(RelationshipMapper relationshipMapper) {
		this.relationshipMapper = relationshipMapper;
	}
	public ArrayList<User> selectUserListbyUserA(String userA){
		return (ArrayList<User>) relationshipMapper.selectUserListbyUserA(userA);
	}
	public void insertRelationship(String userA,String userB){
		Relationship relationship=new Relationship();
		relationship.setUserA(userA);
		relationship.setUserB(userB);
		if(relationshipMapper.selectRelationshipByAB(relationship)==null){
			relationshipMapper.insertRelationship(relationship);
			
			Relationship relationship2=new Relationship();
			relationship2.setUserA(userB);
			relationship2.setUserB(userA);
			relationshipMapper.insertRelationship(relationship2);

		}
	}
	public void deleteRelationship(String userA ,String userB){
		Relationship relationship=new Relationship();
		relationship.setUserA(userA);
		relationship.setUserB(userB);
		relationshipMapper.deleteRelationship(relationship);
		Relationship relationship1=new Relationship();
		relationship1.setUserA(userB);
		relationship1.setUserB(userA);
		relationshipMapper.deleteRelationship(relationship1);
	}
}
