package com.firegame.service.impl;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import po.WuziqiGameState;
import po.XiangqiGameState;

import com.firegame.dao.GameStateDao;
import com.firegame.service.GameStateService;
import com.wuziqiwebsocket.WebSocketWuziqi;
import com.xiangqiwebsocket.WebSocketXiangqi;
@Service("gameStateService")
public class GameStateServiceImpl extends BaseServiceImpl implements GameStateService{
	@Override
	public void removeXiangqiGameState(String userid){
		WebSocketXiangqi webSocket= WebSocketXiangqi.socketMap.get(userid);
		String key=webSocket.getGameMapKey();
		webSocket.gameMap.remove(key);
		MongoTemplate mongoTemplate=(MongoTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("mongoTemplate");
		mongoTemplate.remove(new Query(new Criteria().orOperator(Criteria.where("A").is(userid),Criteria.where("B").is(userid))), XiangqiGameState.class,"xiangqiGameState");
	}
	@Override
	public void removeWuziqiGameState(String userid) {
		// TODO Auto-generated method stub
		WebSocketWuziqi webSocket= WebSocketWuziqi.socketMap.get(userid);
		String key=webSocket.getGameMapKey();
		webSocket.gameMap.remove(key);
		MongoTemplate mongoTemplate=(MongoTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("mongoTemplate");
		mongoTemplate.remove(new Query(new Criteria().orOperator(Criteria.where("A").is(userid),Criteria.where("B").is(userid))), WuziqiGameState.class,"wuziqiGameState");
	}
}
