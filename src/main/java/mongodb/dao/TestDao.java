package mongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import po.GameState;
@Component
public class TestDao {
	@Autowired
	MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void insertBook(){
		GameState gameState=new GameState();
		char[][] state={
				{'1','2'},
				{'0','0'}
		};
		gameState.setState(state);
		gameState.setA("aa");
		gameState.setB("bb");
		gameState.setTurn("you");
		mongoTemplate.insert(gameState);
		
		
	}
	public GameState getBook(){
		
		Query query=new Query(Criteria.where("turn").in("you"));
		
	//	query.with(new Sort(Sort.Direction.DESC, "_id"));
		//query.addCriteria(Criteria.where("A").is("aa"));
		GameState gameState=mongoTemplate.findOne(query,GameState.class);
		return gameState;
	}
}
