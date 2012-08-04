package game.shad.tempus.hearts;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("ParserError")
public class Player extends HeartsActivity{
	ArrayList<card> hand;
	public ArrayList<ArrayList<card>> takenHands = new ArrayList<ArrayList<card>>();
	public int seat=0; //position
	public String realName = "";
	public int colorInt = 0;
	public int state = 0;   //using 1-4
	public Player curPlayer;
	public int score = 0;
	public int totalScore=0;
	public int passTo = 0;
	ArrayList<card> clubs = new ArrayList<card>();     //0
	int highClub = 0;
	int lowClub = 0;
	ArrayList<card> diamonds = new ArrayList<card>();  //1
	int highDiamonds = 0;
	int lowDiamonds = 0;
	ArrayList<card> spades = new ArrayList<card>();	   //2
	int highSpades = 0;
	int lowSpades = 0;
	ArrayList<card> hearts = new ArrayList<card>();    //
	int highHearts = 0;
	int lowHearts = 0;
	public boolean winner = false;
	public boolean voidClubs;
	public boolean voidDiamonds;
	public boolean voidHearts;
	public boolean voidSpades;
	public String clubsString="";
	public String diamondsString="";
	public String heartsString="";
	public String spadesString="";
	public Button clubsButton;
	public Button diamondsButton;
	public Button spadesButton;
	public Button heartsButton;
	
	
	public LayoutInflater factory;

	public View textEntryView;

	public Player(ArrayList<card> hand1, int score, int seat, String name, int color){
		this.hand = hand1;
		this.score = score;
		this.seat = seat;
		this.realName = name;
		this.colorInt = color;
		//this.deck = new Deck();
		/*
		switch(seat){
		case 1:
			clubsButton = (Button)  findViewById(R.id.p1clubsButton);
			diamondsButton = (Button)  findViewById(R.id.p1diamondsButton);
			spadesButton = (Button)  findViewById(R.id.p1spadesButton);
			heartsButton = (Button)  findViewById(R.id.p1heartsButton);
			break;
		case 2:
			clubsButton = (Button)  findViewById(R.id.p2clubsButton);
			diamondsButton = (Button)  findViewById(R.id.p2diamondsButton);
			spadesButton = (Button)  findViewById(R.id.p2spadesButton);
			heartsButton = (Button)  findViewById(R.id.p2heartsButton);
			break;
		case 3:
			clubsButton = (Button)  findViewById(R.id.p3clubsButton);
			diamondsButton = (Button)  findViewById(R.id.p3diamondsButton);
			spadesButton = (Button)  findViewById(R.id.p3spadesButton);
			heartsButton = (Button)  findViewById(R.id.p3heartsButton);
			break;
		case 4:
			clubsButton = (Button)  findViewById(R.id.p4clubsButton);
			diamondsButton = (Button)  findViewById(R.id.p4diamondsButton);
			spadesButton = (Button)  findViewById(R.id.p4spadesButton);
			heartsButton = (Button)  findViewById(R.id.p4heartsButton);
			break;
		
		}
		*/
		

	}
	/**Checks if void in said suit.....Should be redone.
	 * this is not called from HeartsActivity but is an internal method.
	 * @param i the suit value.
	 * @return
	 */
	public boolean checkVoid(int i){
		switch(i){
		case 0:
			if(voidClubs)
				return voidClubs;
			break;
		case 1:
			if(voidDiamonds)
				return voidDiamonds;
			break;
		case 2:
			if(voidSpades)
				return voidSpades;
			break;
		case 3: 
			if(voidHearts)
				return voidHearts;
			break;
		}
		return false;
	}
	

	
	public card go(int round, ArrayList<card> pile , Player p){
		card nextCard;
		int nextCardSuit;
		curPlayer=p;
		print("player.go ="+curPlayer.getRealName(), 91);
		if(pile.size()==0){
			return playLow(0); //get two of clubs	
			//TODO start on different suits.  aka go for voids.
		}
		int suit=pile.get(0).getSuit();
		if(checkVoid(suit)){
			print("void--PlayHigh("+suit+")", 93);
			if(suit<3){
				//playing next suit
				suit++;
			}
			else{  //suit is too high try for clubs
				//No real problems with sending the wrong suit the call for play high and play low are recursive
				suit=0;
			}
			
			return playHigh(suit);
			//TODO do all code for void crap

		}
		print("state ="+state, 34);
		switch (state){  //REPLACED ps with STATE, removed call for it in constructor
		case 1:{
			switch (round){
			case 0:
			case 1: 
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				print("round 0-7 --PlayLow("+suit+")", 110);
				return playLow(suit);
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
				print("round 8-12 --PlayLow("+suit+")", 117);
				return playLow(suit);
			}
		}
		case 2:

			switch (round){
			case 0:
			case 1: 
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				print("round 0-7 --PlayLow("+suit+")", 133);
				return playLow(suit);
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
				print("round 8-12 --PlayLow("+suit+")", 140);
				return playLow(suit);
			}
		case 3:		
			switch (round){
			case 0:
			case 1: 
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				print("round 8-12 --PlayLow("+suit+")", 154);
				return playLow(suit);
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
				print("round 0-7 --PlayLow("+suit+")", 161);
				return playLow(suit);
			}
		case 4:
			if(checkForPoints(pile)){
				print("Play low--POINTS!!", 177);
				playLow(suit);
			}
			else
				print("Play high--no points", 177);
				playHigh(suit);

			switch (round){
			case 0:
			case 1: 
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				print("round 0-7 --PlayHigh("+suit+")", 175);
				return playLow(suit);
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
				print("round 8-12 --PlayHigh("+suit+")", 183);
				return playHigh(suit);
			}
			
		
		
		}
		print("Out of all Loops !!SHOULD NOT HAPPEN!!--PlayHigh("+suit+")", 193);

		return playHigh(suit);

		
	}

	/**
	 * 
	 * @param suit
	 * @return the lowest card of a given suit.
	 */
	public card playLow(int suit){
		card nextCard = null;
		switch(suit){
		case 0:
			if(clubs.size()==0){
				return playLow(++suit);
			}
			nextCard=clubs.get(0);
			clubs.remove(0);
			return nextCard;
		case 1:
			if(diamonds.size()==0){
				return playLow(++suit);
			}
			nextCard=diamonds.get(0);
			diamonds.remove(0);
			return nextCard;
		case 2:
			if(spades.size()==0){
				return playLow(++suit);
			}
			nextCard=spades.get(0);
			spades.remove(0);
			return nextCard;
		case 3: 
			if(hearts.size()==0){
				return playLow(0);
			}
			nextCard=hearts.get(0);
			hearts.remove(0);
			return nextCard;
		case 4:
			print("something Broken", 238);		
			break;
		}		
		
		return nextCard;
	}
	public card playHigh(int suit){
		card nextCard;
		switch(suit){
		case 0:
			if(clubs.size()==0){  //if suit is empty just recall this method with a higher suit.
				return playHigh(++suit);
			}
			nextCard=clubs.get(clubs.size()-1);
			clubs.remove(clubs.size()-1);
			return nextCard;
		case 1:
			if(diamonds.size()==0){
				return playHigh(++suit);
			}
			nextCard=diamonds.get(diamonds.size()-1);
			diamonds.remove(diamonds.size()-1);
			return nextCard;
		case 2:
			if(spades.size()==0){
				return playHigh(++suit);
			}
			nextCard=spades.get(spades.size()-1);
			spades.remove(spades.size()-1);
			return nextCard;
		case 3: 
			if(hearts.size()==0){
				return playHigh(0);
			}
			nextCard=hearts.get(hearts.size()-1);
			hearts.remove(hearts.size()-1);
			return nextCard;
		}		
		return null;
	}
	

	public void setRealName(String name){
		realName = name;
	}
	
	public String getRealName(){
		return realName;
	}
	
	public void setColor(int c){
		colorInt=c;
	}

	public int getColor(){
		return colorInt;
	}
	public void setSeat(int s) {
		seat=s;
	}
	public int getSeat() {
		return seat;
	}
	public void setValue(int c) {
		seat = c;
	}
	public int getState() {
		return state;
	}
	public void setState(int c) {
		state = c;
	}
	
	public void addDeckItem(ArrayList<card> h){
		takenHands.add(h);		//deck.
	}
	
	public ArrayList<ArrayList<card>> getDeck(){
		return takenHands;
	}
	
	public void addToScore(int score){
		this.score += score;
	}
	public void addToTotalScore(int i){
		totalScore+=i;
	}
	public int getScore(){
		return score;
	}
	
	public ArrayList<card> gethand() {
		return hand;
	}
	public void sethand(ArrayList<card> c) {
		hand=c;
	}
	public int getPass() {
		return passTo;
	}
	public void setPass(int passTo) {
		this.passTo=passTo;
	}
    
	public void setClubs(ArrayList<card> c){
		this.clubs=c;
	}
	public void setDiamonds(ArrayList<card> d){
		this.diamonds=d;
	}
	public void setSpades(ArrayList<card> s){
		this.spades=s;
	}
	public void setHearts(ArrayList<card> h){
		this.hearts=h;
	}
	
	public void setClubsVoid(boolean c){
		voidClubs=c;
		//Is there was easy mode On show that they are void to help Player.
	    //Button p4ClubsB =(Button)findViewById(R.id.p4clubsButton);
	    //p4ClubsB.setText("c=VOID");
	}
	public void setDiamondsVoid(boolean d){
		voidDiamonds=d;
	}
	public void setSpadesVoid(boolean s){
		voidSpades=s;
		
	    //Button p4SpadesB =(Button)findViewById(R.id.p4spadesButton);
	    //p4SpadesB.setText("c=VOID");
	}
	public void setHeartsVoid(boolean h){
		voidHearts=h;
	}
	public ArrayList<card> getClubs(){
		return clubs;
	}
	public ArrayList<card> getDiamonds(){
		return diamonds;
	}
	public ArrayList<card> getSpades(){
		return spades;
	}
	public ArrayList<card> getHearts(){
		return hearts;
	}
	public boolean checkForTwo(){
		int i;
		for (i=0;i<13;i++){
			if(hand.get(i).getValue()==(2)&&hand.get(i).getSuit()==(0)){	
				return true;				
			}
		}
		return false;
	}
	
	public void checkForVoids(){ 	
		if(clubs.size()==0){
			setClubsVoid(true);
		}
		if(spades.size()==0){
			setSpadesVoid(true);
		}
		if(diamonds.size()==0){
			setDiamondsVoid(true);
		}
		if(hearts.size()==0){
			setHeartsVoid(true);
		}
		
		//could this game be used to teach people hearts?
		
		if(voidHelper){
			clubsButton.setText("c="+clubs.size());
			clubsButton.setBackgroundColor(Color.MAGENTA);
			diamondsButton.setText("d="+diamonds.size());
			diamondsButton.setBackgroundColor(Color.MAGENTA);
			spadesButton.setText("s="+spades.size());
			spadesButton.setBackgroundColor(Color.MAGENTA);
			heartsButton.setText("h="+hearts.size());
			heartsButton.setBackgroundColor(Color.MAGENTA);
		}


	}
	public void sortHand() {
		ArrayList<card> thand=hand;
		int z=0;
		while(hand.size()>z){
			hand.get(z).setOwner(this);//had some issues this fixed them.
			z++;
		}
		int hc = 0;  //high club
        int hd = 0;  //high diamond
        int hs = 0;  //high spade
        int hh = 0;  //high heart
		int clubsCounter = 0;
		int diamondsCounter = 0;
		int spadesCounter = 0;
		int heartsCounter = 0;
		int cc2 = 0; //keep track of counters when rearranging deck
		int dc2 = 0;
		int sc2 = 0;
		int hc2 = 0;
        ArrayList<card> c = new ArrayList<card>();  
        ArrayList<card> d = new ArrayList<card>();
        ArrayList<card> s = new ArrayList<card>();
        ArrayList<card> h = new ArrayList<card>();
        for(int i=0;i<thand.size();i++){
        	int ncard=thand.get(i).getValue();
        	switch(thand.get(i).getSuit()){
	        	case 0:	
	        		cc2=clubsCounter;
	        		if(hc>ncard){	        			
	        			while(clubsCounter>0&&ncard<c.get(clubsCounter-1).getValue()){
	        				clubsCounter--;
	        			}     					

	        		}
	        		c.add(clubsCounter, thand.get(i));	 
	        		hc=c.get(c.size()-1).getValue(); 
	        		clubsCounter=cc2;
	        		clubsCounter++;

	        		break;
		    	case 1:

		    		dc2=diamondsCounter;
	        		if(hd>ncard){	   
	        			while(diamondsCounter>0&&ncard<d.get(diamondsCounter-1).getValue()){
	        				diamondsCounter--;
	        			}     					

	        		}        			  
	        		d.add(diamondsCounter, thand.get(i));	
	        		hd=d.get(d.size()-1).getValue();
	        		diamondsCounter=dc2;
	        		diamondsCounter++;

		    		break;
		    	case 2:

	        		sc2=spadesCounter;
	        		if(hs>ncard){	        			
	        			while(spadesCounter>0&&ncard<s.get(spadesCounter-1).getValue()){
	        				spadesCounter--;
	        			}     					

	        		}			  
	        		s.add(spadesCounter, thand.get(i));	
	        		hs=s.get(s.size()-1).getValue();
	        		spadesCounter=sc2;
	        		spadesCounter++;

		    		break;
		    	case 3:

		    		hc2=heartsCounter;
	        		if(hh>ncard){	   
	        			while(heartsCounter>0&&ncard<h.get(heartsCounter-1).getValue()){
	        				heartsCounter--;
	        			}     					

	        		} 			  
	        		h.add(heartsCounter, thand.get(i));	
	        		hh=h.get(h.size()-1).getValue();
	        		heartsCounter=hc2;
	        		heartsCounter++;

		    		break;

        	}
	    	//print(i+" ", 358); for debug
	    	

        }
        	
        setClubs(c);
        setDiamonds(d);
        setSpades(s);
        setHearts(h);
        if(seat==6){
        	

        	p1Clubs = (TextView) textEntryView.findViewById(R.id.p1Clubs);
        	p1Diamonds = (TextView) textEntryView.findViewById(R.id.p1Diamonds);
        	p1Spades = (TextView) textEntryView.findViewById(R.id.p1Spades);
        	p1Hearts = (TextView) textEntryView.findViewById(R.id.p1Hearts);
        	p1Clubs = (TextView) findViewById(R.id.p1Clubs);
        	p1Diamonds = (TextView) findViewById(R.id.p1Diamonds);
        	p1Spades = (TextView) findViewById(R.id.p1Spades);
        	p1Hearts = (TextView) findViewById(R.id.p1Hearts);
	    	//p1Clubs.setText(arraytoString(c2));
	    	//p1Diamonds.setText(arraytoString(d2));
	    	//p1Spades.setText(arraytoString(s2));
	    	//p1Hearts.setText(arraytoString(h2));
        }
}

	public boolean checkForPoints(ArrayList<card> c) {
		int points=0;
		for(int i=0; i<c.size();i++){
			int curCard = c.get(i).getValue();
			int curSuit = c.get(i).getSuit();
			if(curSuit==1){ //Diamonds  check for jack.
				if(curCard==11){
					points-=10;

				}
			}
			if(curSuit==2){//Spades   check for queen
				if(curCard==12){
					points+=13;

				}
			}
			if(curSuit==3){//heart--add points
				points++;
			}

			
		}
		if(points>0){
			return true;
		}
		return false;
	}
	/*

	
/*
	public int[] getHighLowSuit(){
		int high=0;
		int low =0;
		int suit=0;
		int[] HLS=new int[3];
		
		if(hand.length>1){
			suit = hand[0].getSuit();
			for(int i=0;i<hand.length;i++){
				int curSuit=hand[i].getSuit();
				int curCard =hand[i].getValue();
				if(high<curCard){
					if(suit==curSuit){
						high=curCard;
					}
					if(suit!=curSuit){
						//Not a valid high
					}
				}
				if (low>curCard){
					aif(suit==curSuit){
						low=curCard;
					}
					if(suit!=curSuit){
						//Not a valid low
					}

				}
			}
			HLS[0]=high;
			HLS[1]=low;
			HLS[2]=suit;
			
			return HLS;		
		}
		else
		return null;
	}
*/
}
