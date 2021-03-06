package po;

import java.util.HashMap;
import java.util.Timer;

import com.xiangqiwebsocket.XiangqiTimer;

public abstract  class AbstractGameState<T> {
	
	Integer now;
	public  abstract boolean  play(String user,T t);
	public  abstract  boolean check(String user,T t);
	public Integer getNow() {
		return now;
	}
	public void setNow(Integer now) {
		this.now = now;
	}
	
}
