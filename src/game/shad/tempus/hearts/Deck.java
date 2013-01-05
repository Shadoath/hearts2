package game.shad.tempus.hearts;


import java.util.ArrayList;

import android.util.Log;

public class Deck {
	public static final String TAG = "Hearts--Deck";

	private ArrayList<Card> deck;
	public Deck(){
		deck = new ArrayList<Card>();
	}
	
	/**
	 * Use on a deck to see if it contains points
	 * Does not check for the jack of Diamonds.
	 * @param deck
	 * @return true if has points
	 */
	public int checkForPoints() {
		Log.d(TAG, "checkForPoints ");
		int points=0;
		for(int i=0; i<deck.size();i++){
			int curCard = deck.get(i).getValue();
			int curSuit = deck.get(i).getSuit();
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
		Log.d(TAG, "Points found= "+points);
		return points;
	}

	
	public void createDeck(ArrayList<Card> deck){
		this.deck = deck;
	}

	public void addCard(Card card){
		this.deck.add(card);
	}

	public ArrayList<Card> getDeck(){
		return this.deck;
	}
	
	public void addAllCards(Deck cards){
		for(int i = 0; i<cards.getSize();i++){
			this.deck.add(cards.getCard(i));
		}
	}
	
	public void addCards(ArrayList<Card> cards){
		for(Card c : cards){
//			Log.d(TAG, "card added="+c.toString());
			this.deck.add(c);
		}
	}
	
	public void removeCards(ArrayList<Card> cards){
		for(Card c : cards){
			Log.d(TAG, "card removed="+c.toString());
			c.setTouched(false);
			this.deck.remove(c);
		}
	}
	public void removeCard(Card card){
		this.deck.remove(card);
	}

	public void removeCardAtIndex(int i){
		this.deck.remove(i);
	}
	
	public void addCardAtIndex(int index, Card card) {
		this.deck.add(index, card);
		
	}
	public void clearALL(){
		this.deck.clear();
	}
	/**
	 * Clears the deck then updates it with new cards.
	 * @param deck2
	 */
	public void updateDeck(Deck deck2){
		this.deck.clear();
		for(int i=0;i<deck.size();i++){
			this.deck.add(deck2.getCard(i));
		}
	}
	public int getIndex(Card card){
		
		return this.deck.indexOf(card);
	}
	
	public int getSize(){
		return this.deck.size();
	}
	
	//returns a card at an index
	public Card getCard(int index){
		return this.deck.get(index);
	}
	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}
	

	//////////////////////////////////////////////////added from game class

	
	public void addLowHigh(ArrayList<Card> fromDeck){
		for(int i=0;i<fromDeck.size();i++){
			deck.add(fromDeck.get(i));
		}
	}
	
	public void addHighLow(ArrayList<Card> fromDeck){
		for(int i=fromDeck.size()-1;i>=0;i--){
			deck.add(fromDeck.get(i));
		}
		
	}
	

	
	
}
