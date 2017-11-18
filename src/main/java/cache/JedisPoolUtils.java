package cache;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;





import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

import net.sf.json.JSONObject;
import po.XiangqiGameState;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {

	private static String IP = "192.168.58.132";
	private static int port = 6379;
	private static volatile JedisPool jedisPool = null;

	private JedisPoolUtils() {

	}

	public static JedisPool getJedisPool() {
		if (jedisPool == null) {
			synchronized (JedisPoolUtils.class) {
				if (jedisPool == null) {

					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxIdle(32);
					config.setMaxWaitMillis(100 * 1000);
					config.setTestOnBorrow(true);
					jedisPool = new JedisPool(config, IP, port);
				}
			}
			
		}
		return jedisPool;
	}
	public static void  released(Jedis jedis){
		if(null!=jedis){
			jedisPool.returnResourceObject(jedis);
		}
	}
	public static void main(String[] args) throws InterruptedException {
		
		final AtomicInteger count=new AtomicInteger(0);
		ExecutorService pool=Executors.newFixedThreadPool(1);
		long start=System.currentTimeMillis();
			for(int i=1;i<=9999999;++i){
				pool.submit(new WorkThread(count, i));
			}
		
		pool.shutdown();
		pool.awaitTermination(100000, TimeUnit.SECONDS);
		System.out.println(System.currentTimeMillis()-start);
		System.out.println(count.get());
	}
}
class WorkThread implements Runnable{
		AtomicInteger count;
		int i;
		static ConcurrentHashMap<String, Object> map=new ConcurrentHashMap<String, Object>();
	
		public WorkThread(AtomicInteger count, int i) {
			super();
			this.count = count;
			this.i = i;
		}


		public void run() {
			
			Jedis jedis=JedisPoolUtils.getJedisPool().getResource();
			XiangqiGameState xiangqiGameState=new XiangqiGameState("1", "2", "1");
			JSONObject jsonObject=JSONObject.fromObject(xiangqiGameState);
			jedis.set("obj"+1, jsonObject.toString());
			String os=jedis.get("obj"+1);
	//		map.put("obj"+1,xiangqiGameState );
			Gson  gson=new Gson();
			XiangqiGameState x1=gson.fromJson(os, XiangqiGameState.class);
			System.out.println(x1);
			JSONObject jsonObject2=JSONObject.fromObject(x1);
			jedis.set("obj"+1, jsonObject2.toString());
			count.incrementAndGet();
			JedisPoolUtils.released(jedis);
			System.out.println(i);
		
	}
	
}