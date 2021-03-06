package po;

import java.io.Serializable;
import java.util.ArrayList;

import com.websocket.Recorder;
import com.wuziqiwebsocket.PlayRecord;

public class WuziqiGameState extends AbstractGameState<WuziqiMove> implements Serializable,Recorder<WuziqiMove>{
	PlayRecord playRecord=new PlayRecord();
 	String[][] state=new String[20][20]; 
	String A;
	String B;
	String turn;
	public WuziqiGameState(){
		
	}
	public WuziqiGameState(String[][] state, String a, String b, String turn) {
		super();
		this.state = state;
		A = a;
		B = b;
		this.turn = turn;
	}
	public String[][] getState() {
		return state;
	}
	public void setState(String[][] state) {
		this.state = state;
	}
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	public String getTurn() {
		return turn;
	}
	public void setTurn(String turn) {
		this.turn = turn;
	}
	public void changeTurn(){
		if(turn.equals(A)){
			turn=B;
		}
		else turn=A;
	}
	public boolean play(String username,WuziqiMove move){
		int x=move.getX(),y=move.getY();
		if(x==-1) return false;
		state[x][y]="b";
		if(username.equals(A))
			state[x][y]="a";
		addRecord(move);
		//��
		int h=1;
		for(int i=1;x-i>=0&&state[x-i][y]==state[x][y];++i){
			++h;
			if(h>=5) return true;
		}
		for(int i=1;x+i<state.length&&state[x+i][y]==state[x][y];++i){
			++h;
			if(h>=5) return true;
		}
		//��
		h=1;
		for(int i=1;y-i>=0&&state[x][y-i]==state[x][y];++i){
			++h;
			if(h>=5) return true;
		}
		for(int i=1;y+i<state.length&&state[x][y+i]==state[x][y];++i){
			++h;
			if(h>=5) return true;
		}
		//45
		h=1;
		for(int i=1;x-i>=0&&y-i>=0&&state[x-i][y-i]==state[x][y];++i){
			++h;
			if(h>=5) return true;
		}
		for(int i=1;x+i<state.length&&y+i<state.length&&state[x+i][y+i]==state[x][y];++i){
			++h;
			if(h>=5) return true;
		}
		//135
		h=1;
		for(int i=1;x-i>=0&&y+i<state.length&&state[x-i][y+i]==state[x][y];++i){
			++h;
			if(h>=5) return true;
		}
		for(int i=1;x+i<state.length&&y-i>=0&&state[x+i][y-i]==state[x][y];++i){
			++h;
			if(h>=5) return true;
		}
		return false;
			
	}
	
	public PlayRecord getPlayRecord() {
		return playRecord;
	}
	public void setPlayRecord(PlayRecord playRecord) {
		this.playRecord = playRecord;
	}
	@Override
	public boolean check(String user, WuziqiMove t) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void addRecord(WuziqiMove move) {
		// TODO Auto-generated method stub
		this.playRecord.getList().add(move);
		
	}
	@Override
	public void recovery(int index) {
		// TODO Auto-generated method stub
		ArrayList<WuziqiMove> list=this.playRecord.getList();
		for(int i=list.size()-1;i>=index;--i){
			WuziqiMove move=list.get(i);
			this.state[move.getX()][move.getY()]="o";
			this.turn=move.getUserid();
			list.remove(i);
		}
		
	}
	
	

}
