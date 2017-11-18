package com.xiangqiwebsocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import org.springframework.web.context.ContextLoader;

import com.firegame.service.MessageService;
import com.firegame.service.RelationshipService;

import po.GameState;
import po.Message;
import po.Relationship;
import po.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @ServerEndpoint ע����һ�����ε�ע�⣬��Ĺ�����Ҫ�ǽ�Ŀǰ���ඨ���һ��websocket��������,
 * ע���ֵ�������ڼ����û����ӵ��ն˷���URL��ַ,�ͻ��˿���ͨ�����URL�����ӵ�WebSocket��������
 * @author uptop
 */
@ServerEndpoint("/websocketMsg1")
public class WebSocketMsg {
	MessageService messageService=(MessageService) ContextLoader.getCurrentWebApplicationContext().getBean("messageService"); 
	RelationshipService relationshipService=(RelationshipService) ContextLoader.getCurrentWebApplicationContext().getBean("relationshipService");
	String username;
	//��̬������������¼��ǰ����������Ӧ�ð�����Ƴ��̰߳�ȫ�ġ�
    private static int onlineCount = 0;

    public static ConcurrentHashMap<String,WebSocketMsg> socketMap=new ConcurrentHashMap<String, WebSocketMsg>();
    //��ĳ���ͻ��˵����ӻỰ����Ҫͨ��������ͻ��˷������
    private Session session;

    /**
     * ���ӽ����ɹ����õķ���
     *
     * @param session ��ѡ�Ĳ���sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ��������ͻ��˷������
     */
    @OnOpen 
    public void onOpen(Session session) {
        this.session = session;
        Map<String,List<String>> map=session.getRequestParameterMap();
        List<String> list=map.get("username");
        this.username=list.get(0);
        socketMap.put(this.username, this);
       
       
        
           
        addOnlineCount();           //�������1
        System.out.println("�������Ӽ��룡��ǰ��������Ϊ" + getOnlineCount());
    }

    /**
     * ���ӹرյ��õķ���
     */
    @OnClose
    public void onClose() {
    	
      
        socketMap.remove(username);
        subOnlineCount();           //�������1
        System.out.println("��һ���ӹرգ���ǰ��������Ϊ" + getOnlineCount());
    }

    /**
     * �յ��ͻ�����Ϣ����õķ���
     *
     * @param message �ͻ��˷��͹�������Ϣ
     * @param session ��ѡ�Ĳ���
     * @throws IOException 
     */
    @OnMessage
    public void onMessage(String message, Session session)  {
    	System.out.print(message);
    	JSONObject jsonObject=new JSONObject().fromObject(message);
    	JSONObject msg=new JSONObject();
    	String tt=null;
    	int type=jsonObject.getInt("type");
    	if(type==0){
    		int msgId=jsonObject.getInt("msgId");
    		messageService.isRead(msgId,1);
    		return;
    	}
    	String to=jsonObject.getString("to");
    	Message mm= messageService.insertMessage(username, to, "", type, 0,new Timestamp(new Date().getTime()));
    	WebSocketMsg socketMsg=socketMap.get(to);
		msg.element("to", to);
		msg.element("msgId", mm.getMsgId());
		msg.element("type", type);
		msg.element("from", username);
		msg.element("content","" );
		msg.element("createDate", mm.getCreateDate());
		
	
		
		if(type==2){
			relationshipService.insertRelationship(username, to);
			WebSocketMsg socketMsg2=socketMap.get(username);
			JSONObject jsonObject2=new JSONObject();
			jsonObject2.element("type", 22);
			
			
			try {
				socketMsg2.sendMessage(jsonObject2.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try{
			socketMsg.sendMessage(msg.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
    	System.out.println(Thread.currentThread().getName());
    }

    /**
     * �������ʱ����
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("�������");
        error.printStackTrace();
    }

    /**
     * ������������漸��������һ��û����ע�⣬�Ǹ���Լ���Ҫ��ӵķ�����
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketMsg.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketMsg.onlineCount--;
    }


    public void sendMsg(String msg) {
       
    }


}
