package tool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import po.User;
import po.UserCustom;

public class CopyUtils {
	public static void copy(Object from,Object to,ArrayList<String> list) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
	/*	Field[] fields=from.getClass().getDeclaredFields();
		Field[] fields2=to.getClass().getSuperclass().getDeclaredFields();*/
/*	for(int i=0;i<fields2.length;++i)
		System.out.println(fields2[i]);*/
	/*	for(int i=0;i<fields.length;++i)
			fields2[i].set(to, fields[i].get(from));
		*/
		BeanUtils.copyProperties(to, from);
	}
	public static void main(String[] agrs) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		User user=new User();
		user.setUserid("111");
		user.setFail(10);
		UserCustom custom=new UserCustom();
		copy(user, custom, null);
		System.out.println(custom.getUserid());
	}
}
