package game.shad.tempus.hearts;

import java.util.ArrayList;

import android.util.Log;


public class Trick{
	public static final String TAG = "Hearts--Trick";

	public int round = 0;
	public int points;
	public int startSuit=0;
	public Card highCard;
	public int highCardValue=0;
	public boolean hasQueen =false;
	public boolean hasJack =false;
	
	private ArrayList<Card> trick;
	
	public Trick(int round){
		this.round=round;
		trick = new ArrayList<Card>();
	}
	
	public Deck TrickToDeck(){
		Deck deck= new Deck();
		deck.setSingleSuit(false);
		deck.setDeck(trick);
		return deck;
	}
		
	public void addCard(Card c){
		int newCardValue=c.getValue();
		int newCardSuit=c.getSuit();
		switch (trick.size()){
		case 0:
			trick.add(c);
			startSuit = c.getSuit();
			highCardValue = c.getValue();
			highCard = c;
			break;
			
		case 1:
			trick.add(c);
			if(startSuit==newCardSuit){
				if(highCardValue<newCardValue){
					highCardValue=newCardValue;
					highCard=c;
				}
			}
			break;
		case 2:
			trick.add(c);
			if(startSuit==newCardSuit){
				if(highCardValue<newCardValue){
					highCardValue=newCardValue;
					highCard=c;
				}
			}
			break;
		case 3:
			trick.add(c);
			if(startSuit==newCardSuit){
				if(highCardValue<newCardValue){
					highCardValue=newCardValue;
					highCard=c;
				}
			}
			break;
		}
		if(newCardSuit==1){
			if(newCardValue==11){
				Log.d(TAG, "Jack of Diamonds added to trick!");
				hasJack=true;
				points-=10;
			}
		}
		else if(newCardSuit==2){
			if(newCardValue==12){
				Log.d(TAG, "Queen of Spades added to trick!");
				hasQueen=true;
				points+=13;
			}
			
		}
		else if(newCardSuit==3){
			Log.d(TAG, c.name+" added to trick!");
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
		hasQueen=false;
		
	}
	
	public ArrayList<Card> getTrick(){
		return trick;
	}
	
	
	
}
