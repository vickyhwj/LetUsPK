package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

import redis.clients.jedis.Jedis;
import cache.JedisPoolUtils;
import cache.JedisPoolUtils.WorkRunnable;
import cache.RedisCachePre;

public class RedisUserCache implements UserCache{
	
	
	@Override
	public UserDetails getUserFromCache(final String username) {
		// TODO Auto-generated method stub
		List<String> list= JedisPoolUtils.work(new WorkRunnable<List<String>>() {

			@Override
			public List<String> run(Jedis jedis) {
				// TODO Auto-generated method stub
				List<String> list=jedis.lrange(RedisCachePre.USERDETAIL+username,0, -1);
				return list;
			}
		});
		if(list==null||list.isEmpty()) return null;
		String password=(String) list.get(0);
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		
		for(int i=1;i<list.size();++i){
			auths.add(new GrantedAuthorityImpl((String) list.get(i)));
		}
			
		User user=new User(username, password, auths);
			
		return user;
	}

	@Override
	public void putUserInCache(final UserDetails user) {
		// TODO Auto-generated method stub
		JedisPoolUtils.work(new WorkRunnable<Void>() {

			@Override
			public Void run(Jedis jedis) {
				// TODO Auto-generated method stub
				String key=RedisCachePre.USERDETAIL+user.getUsername();
				jedis.rpush(key, user.getPassword());
				for(GrantedAuthority authority:user.getAuthorities()){
					jedis.rpush(key,authority.getAuthority());
				}
				return null;
			}
		});
		
		
	}

	@Override
	public void removeUserFromCache(String username) {
		// TODO Auto-generated method stub
		
	}

}
