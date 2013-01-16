package game.shad.tempus.hearts;


import java.util.ArrayList;

import android.R.integer;
import android.util.Log;

public class SuperDeck {
	public static final String TAG = "Hearts--Deck";

	private ArrayList<Card> clubsCards;
	private ArrayList<Card> diamondCards;
	private ArrayList<Card> spadeCards;
	private ArrayList<Card> heartCards;
	
	private int clubsDuckPoints = 0;
	private int diamondsDuckPoints = 0;
	private int spadesDuckPoints = 0;
	private int heartsDuckPoints = 0;
	
	private int clubsJumpPoints = 0;
	private int diamondsJumpPoints = 0;
	private int spadesJumpPoints = 0;
	private int heartsJumpPoints = 0;
	
	public SuperDeck(){
		clubsCards = new ArrayList<Card>();
		diamondCards = new ArrayList<Card>();
		spadeCards = new ArrayList<Card>();
		heartCards = new ArrayList<Card>();
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
		for(int i=0; i<diamondCards.size();i++){
			if(diamondCards.get(i).getValue()==11){
				points-=10;
				break;

			}
		}
		for(int i=0; i<spadeCards.size();i++){
			if(spadeCards.get(i).getValue()==12){
				points+=13;
				break;
			}
			
		}
		for(int i=0; i<heartCards.size();i++){
				points++;
		}
		Log.d(TAG, "Points found= "+points);
		return points;
	}

	
	public void addNewDeck(ArrayList<Card> deck){
		//TODO
	}

	public void addCard(Card card){
		switch(card.getSuit()){
		case 0:	
			
			break;
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		}
	}
	public void addCardToClubs(Card card){
		int cardPointValue = card.getValue()-8;
		if(cardPointValue<0){
			clubsDuckPoints+=cardPointValue;
			
		}
		if(cardPointValue>0){
			clubsJumpPoints+=cardPointValue;
		}
		
	}

	
	public ArrayList<Card> getDeck(){
		return this.deck;
	}
	
	public void addAllCards(SuperDeck cards){
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
	public void updateDeck(SuperDeck deck2){
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
