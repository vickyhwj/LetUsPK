package com.websocket;

import po.XiangqiMove;

import com.xiangqiwebsocket.PlayRecord;

public interface Recorder<T> {
	public void addRecord(T move);
	public void recovery(int index);
}
