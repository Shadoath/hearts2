package game.shad.tempus.hearts;

public class playState {
	int curState =0;
	
	public int getState() {
		return curState;
	}
	public void setState(int c) {
		curState = c;
	}
	
	public void play(){
		switch (curState){
			case 1:
				play1();
				break;		
			case 2:
				play2();
				break;
			case 3:
				play3();
				break;
			case 4:
				play4();
				break;
		}
				
	}
	
	
	
	private void play4() {

		
	}
	private void play3() {

		
	}
	private void play2() {

		
	}
	private void play1() {
		
		
	}
	
	
	
	
	
}