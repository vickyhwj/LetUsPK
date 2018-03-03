package mail;

import java.util.ArrayList;

public class MailObj {
	String from;
	String to;
	String password;
	String subject;
	String text;
	ArrayList<String> FilePath;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ArrayList<String> getFilePath() {
		return FilePath;
	}
	public void setFilePath(ArrayList<String> filePath) {
		FilePath = filePath;
	} 
	
	
}
