package game.shad.tempus.hearts;

import game.shad.tempus.hearts.*;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.TextView;

public class playState  extends  HeartsActivity {
		int curState;
		int result;
		
		
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			Intent intent= getIntent();
			//Bundle mBundle=intent.getBundleExtra("ps");
			result=intent.getIntExtra("re", result++);
			//curState=mBundle.getInt("ps", curState++);
			print("new playstate Line=", 21);
			curState= intent.getIntExtra("ps", curState);
			
		}
		public void RTS(){
			  Intent returnPic=new Intent();
			  setResult(result, returnPic);
			  finish();
		}
		
		public int getState() {
			return curState;
		}
		public void setState(int c) {
			curState = c;
		}
		
		public void nextState(int curState){
			switch (curState){
				case 1:					
					//play1();
					break;		
				case 2:
					///nextPlayer();
					//play2();
					break;
				case 3:
					//play3();
					break;
				case 4:
					//play4();
					break;
			}
					
		}

			
		

/*
		public int[] getHighLow(Deck d){
			Card[] curpile=pile;
			//curpile.getCards();
			
			
			return null;
		}
		
		
			private void play4() {
	
				curState=1;
			}
			private void play3() {
				/*
				check round
				if void(
					1--Play high Club->spade->diamond
					2-6--Get rid of stuff //hearts get dropped  //get seccond void
					7-13--  all worst cards  //second void
				check void
				/
				curState++;
			}
			//Second person to play a card
			//aka JUST AFTER THE FIRST PERSON
			private void play2() {
				Card[] curpile=pile;
				Card f=curpile[1];
				Card s=curpile[2];
				TextView TV5 = (TextView) findViewById(R.id.textView5);
				//TextView topMidTV = (TextView) findViewById(R.id.topMidText);
				EditText botET = (EditText) findViewById(R.id.et1);
				//int[] HLS = curpile.getHighLowSuit();
				//topMidTV.setText("High " + HLS[0]);
				//botTV.setText("Low " + HLS[1]);
				//TV5.setText("Suit " + HLS[2]);
				int curSuit = f.getSuit();
				switch (curSuit){
					case 0:{
						if(curPlayer.getDeck().voidClubs){
							if(round==1){
								if(!curPlayer.getDeck().voidClubs){
									curPlayer.getDeck().getCards();
									}
								
								}
							else if(round<6&&!heartsBroken){
								//play queen
								//play highest non heart in deck
								
							}
							else if(round>=6){
								//play highest in deck
									
							}
	
						}
						else{//not void
							//play low
						}
					}
					case 1:{
								
					}
					case 2:{
						
					}
						
					case 3:{
						
					
					
				}
			}

			//if(curpile.getFirst().getValue()==curpile.getSecond().getValue()
			//		&&curpile.getFirst()){
			}
			
		
		private void play1() {
			print("play1", 133);
			if (round==0){
				pile[pileI]=curPlayer.playCard(2, Card.CLUBS);  //null SOMEWHERE!!!!
				RTS();
			}
			//layCard((CurPlayer.playCard(2, Card.CLUBS)));
		}

*/
	
	
	
	
	
}