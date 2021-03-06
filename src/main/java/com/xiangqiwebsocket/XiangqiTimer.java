package com.xiangqiwebsocket;

import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import com.xiangqiwebsocket.WebSocketXiangqi;

import po.AbstractGameState;
import po.XiangqiGameState;
import po.XiangqiMove;

public class XiangqiTimer implements Runnable {
	public static int TIME_SECOND = 20;
	ExecutorService executorService=Executors.newCachedThreadPool();

	
	
	public void work(XiangqiGameState xiangqiGameState) {
		// TODO Auto-generated method stub
		xiangqiGameState.setNow(TIME_SECOND);
		xiangqiGameState = (XiangqiGameState) xiangqiGameState;
		String turn = xiangqiGameState.getTurn();
		if (turn.equals(xiangqiGameState.getA()))
			xiangqiGameState.setTurn(xiangqiGameState.getB());
		else
			xiangqiGameState.setTurn(xiangqiGameState.getA());
		WebSocketXiangqi w1 = WebSocketXiangqi.socketMap.get(xiangqiGameState
				.getB());
		WebSocketXiangqi w2 = WebSocketXiangqi.socketMap.get(xiangqiGameState
				.getA());

		JSONObject gameObj = new JSONObject();
		gameObj.element("state", xiangqiGameState.getState());
		gameObj.element("A", xiangqiGameState.getA());
		gameObj.element("B", xiangqiGameState.getB());
		gameObj.element("type", 8);
		gameObj.element("turn", xiangqiGameState.getTurn());
		System.out.println(gameObj.toString());
		try {
			w1.sendMessage(gameObj.toString());
			w2.sendMessage(gameObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		JSONObject timerObj = new JSONObject();
		timerObj.element("type", 33);
		timerObj.element("second", XiangqiTimer.TIME_SECOND);

		try {
			w1.sendMessage(timerObj.toString());
			w2.sendMessage(timerObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		try {
			while (true) {
				Set<Entry<String, XiangqiGameState>> set= WebSocketXiangqi.gameMap.entrySet();
				for(Entry<String, XiangqiGameState> entry:set){
					final XiangqiGameState xiangqiGameState=entry.getValue();
					if(xiangqiGameState.getNow()!=null){
						xiangqiGameState.setNow(xiangqiGameState.getNow()-1);
						if(xiangqiGameState.getNow()<0){
							executorService.submit(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									work(xiangqiGameState);
								}
							});
						}
					}
				}
				Thread.sleep(1000);
				
				
					
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("timer is end");
			e.printStackTrace();
		}

	}
	

}
