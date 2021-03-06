package com.firegame.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.firegame.dao.GameStateDao;
import com.firegame.dao.UserDao;

import po.GameState;
import po.User;
@Repository("gameStateDao")
public class GameStateDaoImpl extends BaseDaoImpl<GameState> implements GameStateDao{
	public GameStateDaoImpl(){
		super.setNs("com.firegame.mapper.GameStateMapper");
	}
}
