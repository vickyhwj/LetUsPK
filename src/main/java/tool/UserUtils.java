package tool;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
	private UserUtils(){
		
	}
	public static boolean isOwn(String name){
		String username=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return name.equals(username);
	}

}
