package com.websocket;

import net.sf.json.JSONObject;

public interface MsgStrategy {
	public void dealMsg(JSONObject msg,String username,String gameMapKey);
}
