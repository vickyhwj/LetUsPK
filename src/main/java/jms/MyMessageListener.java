package jms;

import javax.jms.Message;
import javax.jms.MessageListener;

public class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		System.out.println("==========================");
		System.out.println(message);
		System.out.println("==========================");
		
	}

}
