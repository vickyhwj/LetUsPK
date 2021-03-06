package com.firegame.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiangqiwebsocket.WebSocketXiangqi;

@Controller
public class WatchController extends BaseController{
	@RequestMapping("/WatchXiangqi")
	public void WatchXiangqi(String key,HttpServletResponse response) throws IOException{
		key=key.replaceAll("@", " ");
		if(!WebSocketXiangqi.gameMap.containsKey(key))
			response.getWriter().print("no");
		else response.getWriter().print("yes");
	}
}
