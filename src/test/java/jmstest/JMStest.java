package jmstest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JMStest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext-jms.xml");
		JmsTemplate jmsTemplate=(JmsTemplate) ac.getBean("jmsTemplate");
		long start=System.currentTimeMillis();
		for(int i=0;i<=10000;++i){
			final int j=i;
			jmsTemplate.send(new MessageCreator() {
				
				@Override
				public Message createMessage(Session s) throws JMSException {
					// TODO Auto-generated method stub
					StringBuffer sb=new StringBuffer();
					for(int c=1;c<=100;++c)
						sb.append("ten leng feng ");
					Message message=s.createTextMessage("spring msg:"+j+sb.toString());
					return message;
				}
			});
		}
		System.out.println(System.currentTimeMillis()-start);
	}

}
