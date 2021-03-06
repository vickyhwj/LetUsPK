package po;

import java.util.ArrayList;

import com.xiangqiwebsocket.PlayRecord;
import com.xiangqiwebsocket.XiangqiTimer;

public class XiangqiGameState extends AbstractGameState<XiangqiMove>{
	public XiangqiGameState() {
		
	}
	
	public XiangqiGameState( PlayRecord playRecord, String[][] state, String a,
			String b, String turn,Integer now) {
		super();
		this.playRecord = playRecord;
		this.state = state;
		A = a;
		B = b;
		this.turn = turn;
		super.now=now;
	}

	PlayRecord playRecord=new PlayRecord();
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
	


	String[][] state;
	String A,B,turn;
	
	
	

	public XiangqiGameState( String a, String b, String trun) {
		super();
		this.state = new String[10][9];
		state[0][0] = "ju";
		state[0][1] = "ma";
		state[0][2] = "xiang";
		state[0][3] = "shi";
		state[0][4] = "jiang";
		state[0][8] = "ju";
		state[0][7] = "ma";
		state[0][6] = "xiang";
		state[0][5] = "shi";
		state[2][1] = "pao";
		state[2][7] = "pao";
		state[3][0] = "bing";
		state[3][2] = "bing";
		state[3][4] = "bing";
		state[3][6] = "bing";
		state[3][8] = "bing";

		state[9][0] = "r_ju";
		state[9][1] = "r_ma";
		state[9][2] = "r_xiang";
		state[9][3] = "r_shi";
		state[9][4] = "r_jiang";
		state[9][8] = "r_ju";
		state[9][7] = "r_ma";
		state[9][6] = "r_xiang";
		state[9][5] = "r_shi";
		state[7][1] = "r_pao";
		state[7][7] = "r_pao";
		state[6][0] = "r_bing";
		state[6][2] = "r_bing";
		state[6][4] = "r_bing";
		state[6][6] = "r_bing";
		state[6][8] = "r_bing";

		A = a;
		B = b;
		this.turn = trun;
	}

	@Override
	public boolean play(String user, XiangqiMove t) {
		// TODO Auto-generated method stub
		
		int startx=t.getStartx();
		int starty=t.getStarty();
		int endx=t.getEndx();
		int endy=t.getEndy();
		t.setEat(state[endx][endy]);
		addRecord(t);
		String temp=state[startx][starty];
		state[startx][starty]=null;
		String temp2=state[endx][endy];
		state[endx][endy]=temp;
		
		turn = turn .equals( A)? B:A;
		
		if("jiang".equals(temp2)||"r_jiang".equals(temp2)) return true;
		return false;
	}

	public String[][] getState() {
		return state;
	}

	public void setState(String[][] state) {
		this.state = state;
	}

	@Override
	public boolean check(String user, XiangqiMove t) {
		// TODO Auto-generated method stub
		return true;
	}
	public void addRecord(XiangqiMove move){
		playRecord.addRecord(move);
	}
	public PlayRecord getPlayRecord() {
		return playRecord;
	}

	public void setPlayRecord(PlayRecord playRecord) {
		this.playRecord=playRecord;
	}

	public void recovery(int index){
		/*ArrayList<XiangqiMove> list=playRecord.getList();
		if(index>list.size()-1) return;
		for(int i=list.size()-1;i>=index;--i){
			XiangqiMove move=list.get(i);
			state[move.getStartx()][move.getStarty()]=state[move.getEndx()][move.getEndy()];
			state[move.getEndx()][move.getEndy()]=move.getEat();
			turn=move.getUserid();
		//	if(i!=index)
				list.remove(i);
		}*/
		playRecord.recovery(index, this);
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		System.out.println(this+":has been released");
	}
	
}
