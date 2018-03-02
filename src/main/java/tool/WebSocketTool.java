package tool;

import java.io.IOException;
import java.util.Map;

import com.websocket.AbstractWebSocket;
import com.websocket.BaseWebSocket;

public class WebSocketTool {
	public static void closePreSocketAndAdd(Class<?> clazz,Map socketMap,AbstractWebSocket webSocket) throws IOException{
		synchronized(clazz){
			AbstractWebSocket abstractWebSocket=(AbstractWebSocket) socketMap.get(webSocket.getUsername());
			if(abstractWebSocket!=null){
				abstractWebSocket.close();
			}
			socketMap.put(webSocket.getUsername(),webSocket);
		}
	}
	public static void closePreSocketAndAdd(Class<?> clazz,Map socketMap,BaseWebSocket webSocket) throws IOException{
		synchronized(clazz){
			BaseWebSocket baseWebSocket=(BaseWebSocket) socketMap.get(webSocket.getUsername());
			if(baseWebSocket!=null){
				baseWebSocket.close();
			}
			socketMap.put(webSocket.getUsername(),webSocket);
		}
	}
}
