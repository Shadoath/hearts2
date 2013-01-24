package game.shad.tempus.hearts;

import java.util.ArrayList;

import android.util.Log;


public class Trick{
	public static final String TAG = "Hearts--Trick";

	public int points;
	public int startSuit=0;
	public Card highCard;
	public int highCardValue=0;
	
	private ArrayList<Card> trick;
	
	public Trick(){
		trick = new ArrayList<Card>();
	}
	
	public oldDeck TrickToDeck(){
		oldDeck deck= new oldDeck();
		deck.setDeck(trick);
		return deck;
	}
	
	public void addCard(Card c){
		int newCardValue=c.getValue();
		int newCardSuit=c.getSuit();
		switch (trick.size()){
		
		case 0:
			trick.add(c);
			startSuit=c.getSuit();
			highCardValue=c.getValue();
			highCard=c;
			break;
			
		case 1:
			trick.add(c);
			if(startSuit==newCardSuit){
				if(highCardValue<newCardValue)
					highCardValue=newCardValue;
					highCard=c;
			}
			break;
		case 2:
			trick.add(c);
			if(startSuit==newCardSuit){
				if(highCardValue<newCardValue)
					highCardValue=newCardValue;
					highCard=c;
			}
			break;
		case 3:
			trick.add(c);
			if(startSuit==newCardSuit){
				if(highCardValue<newCardValue)
					highCardValue=newCardValue;
					highCard=c;
			}
		}
		if(newCardSuit==1){
			if(newCardValue==11)
				points-=10;
		}
		else if(newCardSuit==2){
			if(newCardValue==12)
				points+=13;
			
		}
		else if(newCardSuit==3){
			points++;
		}

		
	}
	public boolean hasPoints(){
		return points>0;
	}
	public boolean hasNegativePoints(){
		return points<0;
	}
	public Card getCard(int i){
		return trick.get(i);
	}
	
	public Card getHighCard(){
		return highCard;
	}
	
	public Card getFirstCard(){
	    	    
		if(trick.size()==0){
			Log.d(TAG, "No cards in the Trick");
			return null;
		}
	    return trick.get(0); 
	}
			
	public Card getSecondCard(){
		if(trick.size()<2){
			Log.d(TAG, "Not enough cards in the Trick");
			return null;
		}
	    return trick.get(0); 
	}
			
	public Card getThirdCard(){
			if(trick.size()<3){
				Log.d(TAG, "Not enough cards in the Trick");
				return null;
			}
		    return trick.get(0); 
	        }
		
	public int getSize(){
		return trick.size();
	}
	public void clearALL(){
		trick.clear();
		points=0;
		startSuit=0;
		highCard=null;
		highCardValue=0;
		
	}
	
	public ArrayList<Card> getTrick(){
		return trick;
	}
	
	
	
}
