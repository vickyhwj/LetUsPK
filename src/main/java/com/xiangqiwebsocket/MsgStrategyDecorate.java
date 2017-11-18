package com.xiangqiwebsocket;

import com.websocket.MsgStrategy;

import net.sf.json.JSONObject;

public class MsgStrategyDecorate implements MsgStrategy{
	MsgStrategy msgStrategy;
	public MsgStrategyDecorate(MsgStrategy msgStrategy){
		this.msgStrategy=msgStrategy;
	}
	public void dealMsg(JSONObject msg,String username,String gameMapKey){
		msgStrategy.dealMsg(msg, username, gameMapKey);
	}
}
