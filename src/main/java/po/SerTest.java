package po;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerTest {

	public  static void write() throws IOException {
		// TODO Auto-generated method stub
		GameState gameState=new GameState();
		char[][] a={{'1','2'},{'3','4'}};
		gameState.state=a;
		gameState.A="vicky";
		gameState.B="jenny";
		gameState.turn="vicky";
		 FileOutputStream fileOut =
		         new FileOutputStream("H:/gamestate.ser");
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(gameState);
		         out.close();
		         fileOut.close();
		         System.out.printf("Serialized data is saved in /tmp/employee.ser");

	}
	public  static void read() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		GameState gameState=null;
		FileInputStream fileIn = new FileInputStream("H:/gamestate.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        gameState=(GameState) in.readObject();
        System.out.print(gameState);
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		write();
		read();
	}

}
