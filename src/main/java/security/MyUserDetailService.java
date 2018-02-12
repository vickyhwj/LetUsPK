package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import po.Role;
import po.UserRoles;

import com.firegame.dao.SecurityDao;

public class MyUserDetailService implements UserDetailsService {
	@Autowired
	UserCache userCache;
	@Autowired
	SecurityDao securityDao;

	public UserCache getUserCache() {
		return userCache;
	}

	public void setUserCache(final UserCache userCache) {
		this.userCache = userCache;
	}

	// 登陆验证时，通过username获取用户的所有权限信息，
	// 并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException, DataAccessException {
	//	List<Map> map=securityDao.findUserRolesMapByUserId(username);
		UserDetails userDetails = null;
		if (userCache != null)
			userDetails=userCache.getUserFromCache(username);
		if (userDetails == null) {
			final Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			final UserRoles userRoles = securityDao
					.findUserRolesByUserId(username);
			for (final Role role : userRoles.getRoles()) {
				final GrantedAuthorityImpl auth = new GrantedAuthorityImpl(
						role.getRolename());
				auths.add(auth);

			}
			userDetails = new User(username, userRoles.getPassword(), true,
					true, true, true, auths);
			if (userCache != null)
				userCache.putUserInCache(userDetails);
		}
		return userDetails;
	}
}
