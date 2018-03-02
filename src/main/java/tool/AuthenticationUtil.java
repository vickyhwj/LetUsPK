package tool;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;

public class AuthenticationUtil {
	private AuthenticationUtil(){
		
	}
	public static User getUser(){
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	public static void expireLastUser(SessionRegistry sessionRegistry,User user){
		final List<SessionInformation> list=sessionRegistry.getAllSessions(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),false);
		for(final SessionInformation si:list){
		    si.expireNow();
		}
		
	}
	public static void expireLastUser(SessionRegistry sessionRegistry){
		expireLastUser(sessionRegistry, getUser());
	}
	public static void registerNewSession(){
		
	}
}
