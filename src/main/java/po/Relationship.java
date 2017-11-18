package po;

import java.io.Serializable;

public class Relationship implements Serializable{
	String userA;
	String userB;
	public String getUserA() {
		return userA;
	}
	public void setUserA(String userA) {
		this.userA = userA;
	}
	public String getUserB() {
		return userB;
	}
	public void setUserB(String userB) {
		this.userB = userB;
	}
	

}
