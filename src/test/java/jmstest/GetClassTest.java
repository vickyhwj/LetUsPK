package jmstest;
class Peo{
	public void getMyClass(){
		System.out.println(getClass());
	}
}
class Stu extends Peo{
	
}
interface If{
	public void say();
}
 abstract class MyIf implements If{
	
}
public class GetClassTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Peo stu=new Stu();
		stu.getMyClass();

	}

}
