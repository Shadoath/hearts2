package game.shad.tempus.hearts;


import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Canvas;

public class Hand extends HeartsActivity{
	Player handWinner=null;
	
	private ArrayList<card> hand;
	
	public Hand(){
		hand = new ArrayList<card>();
		
	}
	public void newhand(ArrayList<card> nCard){
		hand=nCard;
	}


	public void addCard(card card){
		this.hand.add(card);
	}
	
	public void removeCard(card card){
		this.hand.remove(card);
	}
	
	public int getIndex(card card){
		return this.hand.indexOf(card);
	}
	
	public int getSize(){
		return this.hand.size();
	}
	public Object getCards(){
		return  hand;
	}
	
	public card getCard(int index){
		return hand.get(index);
	}
	
	public card getFirst(){
		return hand.get(0);
	}
	public card getSecond(){
		return hand.get(1);
	}
	
	public card getThird(){
		return hand.get(2);
	}
	
	public card getFourth(){  //Dumb code never called
		return hand.get(3);
	}
	/*
	public void sortHand(){
		for(int i=0;i<hand.size();i++){
			int suit = hand.get(i).getSuit();
			int value = hand.get(i).getValue();
			int c=0;
			int d=0;
			int h=0;
			int s=0;
			switch(suit){
				case 0:{
					clubs[c]=value;
					c++;
				}
				case 1:{
					diamonds[d]=value;
					d++;		
				}
				case 2:{
					hearts[h]=value;
					h++;
				}
					
				case 3:{
					spades[s]=value;
					s++;
				}
			}
			if(c==0){
				voidClubs=true;			
			}
			if(d==0){
				voidDiamonds=true;			
			}
			if(h==0){
				voidHearts=true;			
			}
			if(s==0){
				voidSpades=true;			
			}
		}
	}	
	*/
	public int[] getHighLowSuit(){
		int high=0;
		int low =0;
		int suit=0;
		int[] HLS=new int[3];
		
		if(hand.size()>1){
			suit = hand.get(0).getSuit();
			for(int i=0;i<hand.size();i++){
				int curSuit=hand.get(i).getSuit();
				int curCard =hand.get(i).getValue();
				if(high<curCard){
					if(suit==curSuit){
						high=curCard;
					}
					if(suit!=curSuit){
						//Not a valid high
					}
				}
				if (low>curCard){
					if(suit==curSuit){
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
	
	public void draw(Canvas canvas) {
		for(int i = 0; i < hand.size(); i++){
			getCard(i).draw(canvas);
		}
	}
	/*
	private Card[] hand;
	
	public hand(Card[] cards){
		hand = cards;
	}

	public int getLength(){
		return hand.length;
	}
	
	public Card[] getCards(){
		return  hand;
	}
	
	public void sortHighLow(){
		if(!voidClubs){
			Arrays.sort(clubs);
		}
		if(!voidDiamonds){
			Arrays.sort(diamonds);
		}
		if(!voidHearts){
			Arrays.sort(hearts);
		}
		if(!voidSpades){
			Arrays.sort(spades);
		}
	}
	//used when getting Cards from the pile on table.
	public Card getFirst(){
		return hand[0];
	}
	//used when getting Cards from the pile on table.
	public Card getSecond(){
		return hand[1];
	}
	//used when getting Cards from the pile on table.
	public Card getThird(){
		return hand[2];
	}
	
	public void setFirst(Card c){
		hand[0]=c;
	}	
	public void setSecond(Card c){
		hand[1]=c;
	}	
	public void setThird(Card c){
		hand[2]=c;
	}
	public void setForth(Card c){
		hand[3]=c;
	}
	
	public void sortHand(){
		for(int i=0;i<hand.length;i++){
			int suit = hand[i].getSuit();
			int value = hand[i].getValue();
			int c=0;
			int d=0;
			int h=0;
			int s=0;
			switch(suit){
				case 0:{
					if(value<=lowClub){
						lowClub=value;
					}
					if(value>highClub){
						highClub=value;
					}
					clubsString+=value+" ";
					clubs[c]=value;
					c++;
				}
				case 1:{
					if(value<=lowDiamonds){
						lowDiamonds=value;
					}
					if(value>highDiamonds){
						highDiamonds=value;
					}
					diamondsString+=value+" ";

					diamonds[d]=value;
					d++;		
				}
				case 2:{
					if(value<=lowHearts){
						lowHearts=value;
					}
					if(value>highHearts){
						highHearts=value;
					}
					heartsString+=value+" ";

					hearts[h]=value;
					h++;
				}
					
				case 3:{
					if(value<=lowSpades){
						lowSpades=value;
					}
					if(value>highSpades){
						highSpades=value;
					}
					spadesString+=value+" ";

					spades[s]=value;
					s++;
				}
			}
			if(c==0){
				voidClubs=true;			
			}
			if(d==0){
				voidDiamonds=true;			
			}
			if(h==0){
				voidHearts=true;			
			}
			if(s==0){
				voidSpades=true;			
			}
		}
		sortHighLow();
	}


	

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
					if(suit==curSuit){
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
	
	public void draw(Canvas canvas) {
		for(int i = 0; i < hand.size(); i++){
			getCard(i).draw(canvas);
		}
	}
	*/
	
}
