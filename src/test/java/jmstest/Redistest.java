package jmstest;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import cache.JedisPoolUtils;
import cache.JedisPoolUtils.WorkRunnable;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Redistest {

	public static void main(String[] args) throws InterruptedException {	
		int threadCount=500000;
		CountDownLatch latch=new CountDownLatch(threadCount);
		JedisPoolUtils.work(new WorkRunnable<Void>() {

			@Override
			public Void run(Jedis jedis) {
				// TODO Auto-generated method stub
				jedis.del("a","order");
				jedis.set("a", "1000");
				return null;
			}
		});
		long start=System.currentTimeMillis();
		for(int i=1;i<=threadCount;++i){
			new RedisServiceThread(latch,i).start();
		}
		latch.await();
		System.out.println("time"+(System.currentTimeMillis()-start));
	}

}
class RedisServiceThread extends Thread{
	static String listName="order";
	CountDownLatch latch;
	int threadName;
	
	public RedisServiceThread(CountDownLatch latch, int threadName) {
		super();
		this.latch = latch;
		this.threadName = threadName;
	}

	public void run(){
		final String goodId="a"; 
		JedisPoolUtils.work(new WorkRunnable<Void>() {

			@Override
			public Void run(Jedis jedis) {
				// TODO Auto-generated method stub
				if(Integer.valueOf(jedis.get("a"))<=0) return null;
				if(JedisPoolUtils.lock("lock:"+goodId, (long) -1, 10, jedis)){
					String count=jedis.get(goodId);
					if(Integer.valueOf(count)>0){
						jedis.decr(goodId);
					}
					
					JedisPoolUtils.unlock("lock:"+goodId, jedis);
					if(Integer.valueOf(count)>0){
						jedis.rpush(listName, Thread.currentThread().getName()+"||"+goodId);
		//				System.out.println(threadName+":success");
					}
				}
			//	else
		//			System.out.println(threadName+":fail");
				return null;
			}
		});
		latch.countDown();
	}
}
