package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.web.context.ContextLoader;

import po.Authority;


import seccurity.tool.AntUrlPathMatcher;

import com.firegame.dao.SecurityDao;
import com.firegame.service.UserService;

public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private SecurityDao securityDao;
	
	private final seccurity.tool.UrlMatcher urlMatcher = new AntUrlPathMatcher();
    // key:url value:角色
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    // tomcat启动时实例化一次
    public MyInvocationSecurityMetadataSource(SecurityDao securityDao) {
    	this.securityDao=securityDao;
        loadResourceDefine();
    }

    // tomcat开启时加载一次，加载所有url和权限（或角色）的对应关系
    private void loadResourceDefine() {
    
    	resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
    	List<Authority> list=securityDao.findAllAuthority();
    	for(Authority authority:list){
    		Collection<ConfigAttribute> collection=resourceMap.get(authority.getUrl());
    		if(collection==null){
    			collection=new ArrayList<>();
    			resourceMap.put(authority.getUrl(), collection);
    		}
    		ConfigAttribute cano = new SecurityConfig(authority.getRole().getRoleid());
			collection.add(cano);
    		
    	}
    	System.out.println(resourceMap);
    }

    // 参数是要访问的url，返回这个url对于的所有权限（或角色）
    @Override
    public Collection<ConfigAttribute> getAttributes(final Object object) throws IllegalArgumentException {
        // 将参数转为url
        final String url = ((FilterInvocation) object).getRequestUrl().split("[?]")[0];
      
        final Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            final String resURL = ite.next();
            if (urlMatcher.pathMatchesUrl(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return true;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
