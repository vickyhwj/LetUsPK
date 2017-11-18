package cache;

import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.ContextLoader;

public class RedisCache implements Cache{
	
    private  String id="usercache"; // cache instance id
   
    private RedisTemplate redisTemplate; 
   

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private static final long EXPIRE_TIME_IN_MINUTES = 30; // redis过期时间
    public RedisCache(String id){
    	this.id=id;
    }

	@Override
	public void clear() {
		// TODO Auto-generated method stub
	//	redisTemplate=(RedisTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("redisTemplate"); 

		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection arg0) throws DataAccessException {
				// TODO Auto-generated method stub
				arg0.flushDb();
				System.out.println("removeall");
				return null;
			}
		});
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public Object getObject(Object arg0) {
		// TODO Auto-generated method stub
		redisTemplate=(RedisTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("redisTemplate"); 
		Object obj=redisTemplate.opsForValue().get(arg0);
		System.out.println("key="+arg0);
		System.out.println("value="+obj);
		return obj;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void putObject(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		redisTemplate=(RedisTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("redisTemplate"); 
		redisTemplate.opsForValue().set(arg0, arg1);
		
	}

	@Override
	public Object removeObject(Object arg0) {
		// TODO Auto-generated method stub
		redisTemplate=(RedisTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("redisTemplate"); 
		redisTemplate.delete(arg0);
		System.out.println("removekey="+arg0);
		return arg0;
	}

}
