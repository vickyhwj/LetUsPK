package exception;

public class MyRuntimeException extends RuntimeException{
	public final static String USERNOTEQUAL="user not equal"; 
	public MyRuntimeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
