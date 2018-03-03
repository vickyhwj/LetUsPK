package mail;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.MailSSLSocketFactory;

public class JavaMailSender implements JavaMail {
	public static JavaMailSender INSTANCE = new JavaMailSender();
	private String host;
	private String from;
	private String password;

	private JavaMailSender() {
		try {
			Properties pop = new Properties();
			InputStream is = getClass().getResourceAsStream(
					"/javamail.properties");
			pop.load(is);
			host = pop.getProperty("host");
			from = pop.getProperty("username");
			password = pop.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean sendMail(MailObj mailObj) throws Exception {
		// TODO Auto-generated method stub
		String from1 = from;
		String password1 = password;
		if (mailObj.getFrom() != null)
			from1 = mailObj.getFrom();
		if (mailObj.getPassword() != null)
			password1 = mailObj.getPassword();

		Properties properties = System.getProperties();

		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);

		properties.put("mail.smtp.auth", "true");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		final String authFrom=from1;
		final String authPassword=password1;
		// 获取默认session对象
		Session session = Session.getDefaultInstance(properties,
				new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(authFrom, authPassword); // 发件人邮件用户名、密码
					}
				});

		try {
			// 创建默认的 MimeMessage 对象
			MimeMessage message = new MimeMessage(session);

			// Set From: 头部头字段
			message.setFrom(new InternetAddress(authFrom));

			// Set To: 头部头字段
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mailObj.getTo()));

			// Set Subject: 头部头字段
			message.setSubject(mailObj.getSubject());
			// 创建多重消息
			Multipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = null;
			if (mailObj.getText() != null) {
				// 创建消息部分
				messageBodyPart = new MimeBodyPart();

				// 消息
				messageBodyPart.setText(mailObj.getText());

				// 设置文本消息部分
				multipart.addBodyPart(messageBodyPart);
			}

			// 附件部分
			if (mailObj.getFilePath() != null)
				for (String filepath : mailObj.getFilePath()) {
					messageBodyPart = new MimeBodyPart();
					String filename = filepath;
					DataSource source = new FileDataSource(filename);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(filename);
					multipart.addBodyPart(messageBodyPart);
				}
			// 发送完整消息
			message.setContent(multipart);

			// 发送消息
			Transport.send(message);
			System.out.println("Sent message successfully....from runoob.com");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
		return false;
	}

}
