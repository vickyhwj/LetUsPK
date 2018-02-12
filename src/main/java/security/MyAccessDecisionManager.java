package security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MyAccessDecisionManager implements AccessDecisionManager {

	// 检查用户是否够权限访问资源
	// 参数authentication是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息
	// 参数object是url
	// 参数configAttributes所需的权限
	@Override
	public void decide(final Authentication authentication,
			final Object object,
			final Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}

		final Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			final ConfigAttribute ca = ite.next();
			final String needRole = ((SecurityConfig) ca).getAttribute();
			for (final GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.equals(ga.getAuthority())) {

					return;
				}
			}
		}
		// 注意：执行这里，后台是会抛异常的，但是界面会跳转到所配的access-denied-page页面
		throw new AccessDeniedException("no right");
	}

	@Override
	public boolean supports(final ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}
}
