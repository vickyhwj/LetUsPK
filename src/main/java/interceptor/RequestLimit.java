package interceptor;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RequestLimit implements HandlerInterceptor {
	public static ConcurrentHashMap<String, Long> ipTimeMap = new ConcurrentHashMap<String, Long>();
	static int TIME_SPAN = 15 * 1000;
	static {
		
		new Thread(new Runnable() {
			
			public void run() {
				while (true) {
					long now = System.currentTimeMillis();
					Set<Entry<String, Long>> set = ipTimeMap.entrySet();
					for (Entry<String, Long> entry : set) {
						if (now - entry.getValue() > TIME_SPAN) {
							ipTimeMap.remove(entry.getKey());
						}
					}
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("ipsize:=" + ipTimeMap.size());
				}
			}
		},"cleanIpMap").start();
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String ip = request.getRemoteAddr();
		Long preTime = ipTimeMap.get(ip);
		System.out.println("ipMap.size=" + ipTimeMap.size());
		if (preTime == null) {
			ipTimeMap.put(ip, System.currentTimeMillis());
			response.getWriter().print("limit");
			return true;
		}
		Long now = System.currentTimeMillis();
		if (now - preTime < TIME_SPAN) {
			response.sendError(503,"error");
			return false;
		}
		ipTimeMap.put(ip, System.currentTimeMillis());

		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
