package com.xiangqiwebsocket;

import java.util.ArrayList;

import po.XiangqiGameState;
import po.XiangqiMove;

public class PlayRecord {
	private ArrayList<XiangqiMove> list=new ArrayList<XiangqiMove>();

	public ArrayList<XiangqiMove> getList() {
		return list;
	}
	
	public void addRecord(XiangqiMove move) {
		this.list.add(move);
	}
	public void recovery(int index,XiangqiGameState gameState){
		String[][] state=gameState.getState();
		ArrayList<XiangqiMove> list=this.list;
		if(index>list.size()-1) return;
		for(int i=list.size()-1;i>=index;--i){
			XiangqiMove move=list.get(i);
			state[move.getStartx()][move.getStarty()]=state[move.getEndx()][move.getEndy()];
			state[move.getEndx()][move.getEndy()]=move.getEat();
			gameState.setTurn(move.getUserid());
		//	if(i!=index)
				list.remove(i);
		}
	}
}
