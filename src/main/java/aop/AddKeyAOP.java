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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.context.ContextLoader;

import po.XiangqiGameState;

import com.xiangqiwebsocket.WebSocketXiangqi;
import com.xiangqiwebsocket.watch.WatchXiangqi;



//@Aspect
public class AddKeyAOP implements MethodInterceptor{
	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		// TODO Auto-generated method stub
		
		
		return around(arg0);
	}
	public Object around(MethodInvocation joinPoint) throws Throwable{
		long start=System.currentTimeMillis();
		Object result=joinPoint.proceed();
		Object[] args=joinPoint.getArguments();
		MongoTemplate mongoTemplate=(MongoTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("mongoTemplate");
		WebSocketXiangqi socket=WebSocketXiangqi.socketMap.get(args[1]);
		String key=socket.getGameMapKey();
		mongoTemplate.insert(new JSONObject().element("key", key.replaceAll(" ", "-")),"xiangqigamekey");

		
		System.out.println(joinPoint.getMethod().getName()+"timeuse:"+(System.currentTimeMillis()-start));
		return result;
		
	}
	
	 
}
