package aop;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

import net.sf.json.JSONObject;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import po.XiangqiGameState;

import com.xiangqiwebsocket.WebSocketXiangqi;
import com.xiangqiwebsocket.watch.WatchXiangqi;



//@Aspect
public class LoggerAOP implements MethodInterceptor{
	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		// TODO Auto-generated method stub
		
		
		return around(arg0);
	}
	public Object around(MethodInvocation joinPoint) throws Throwable{
		long start=System.currentTimeMillis();
		Object result=joinPoint.proceed();
		Object[] args=joinPoint.getArguments();
		JSONObject jsonObject=getXiangqiGameState((String) args[1]);
		System.out.println(jsonObject);
		
		System.out.println(joinPoint.getMethod().getName()+"timeuse:"+(System.currentTimeMillis()-start));
		return result;
		
	}
	public JSONObject getXiangqiGameState(String username){
		WebSocketXiangqi webSocket=WebSocketXiangqi.socketMap.get(username);
		String key=webSocket.getGameMapKey();
		XiangqiGameState gameState= WebSocketXiangqi.gameMap.get(key);
		JSONObject jsonObject=new JSONObject();
		jsonObject.element("A", gameState.getA());
		jsonObject.element("B", gameState.getB());
		jsonObject.element("state",gameState.getState() );
		jsonObject.element("turn", gameState.getTurn());
		CopyOnWriteArraySet<WatchXiangqi> set= WatchXiangqi.watchMap.get(key);
		if(set!=null){
			Iterator<WatchXiangqi> iterator= set.iterator();
			while (iterator.hasNext()) {
				WatchXiangqi watchXiangqi = (WatchXiangqi) iterator.next();
				try {
					watchXiangqi.sendMessage(jsonObject.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}	
		return jsonObject;
	}
	 
}
