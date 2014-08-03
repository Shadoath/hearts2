package game.shad.tempus.hearts;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.util.Log;

public class Deck {
	public static final String TAG = "Hearts--Deck";

	public ArrayList<Card> deck;
	/**
	 * Low Card Point Value 
	 * Cards 2-7
	 * Max points 21
	 */
	public int lowCPVClubs = 0;
	/**
	 * High Card Point Value 
	 * Cards 9-14
	 * Max points 21
	 */
	public int highCPVClubs = 0;
	public int totalClubs = 0;
	
	/**
	 * Low Card Point Value 
	 * Cards 2-7
	 * Max points 21
	 */
	public int lowCPVDiamonds = 0;
	/**
	 * High Card Point Value 
	 * Cards 9-14
	 * Max points 21
	 */
	public int highCPVDiamonds = 0;
	public int totalDiamonds = 0;
	
	
	/**
	 * Low Card Point Value 
	 * Cards 2-7
	 * Max points 21
	 */
	public int lowCPVSpades = 0;
	/**
	 * High Card Point Value 
	 * Cards 9-14
	 * Max points 21
	 */
	public int highCPVSpades = 0;
	public int totalSpades = 0;

	
	/**
	 * Low Card Point Value 
	 * Cards 2-7
	 * Max points 21
	 */
	public int lowCPVHearts = 0;
	/**
	 * High Card Point Value 
	 * Cards 9-14
	 * Max points 21
	 */
	public int highCPVHearts = 0;
	public int totalHearts = 0;

	private Card majorCard = null;
	public Deck(){
		deck = new ArrayList<Card>();
	}
	
	
	/**
	 * Adds a card to this deck in a ordered fashion.
	 * TEST ME
	 * @param card
	 */
	public void addCard(Card card){
		addCardValue(card.getSuit(), card.getValue()-8);
		deck.add(card);
	}
	
	private void addCardValue(int suit, int cpv){
		switch(suit){
		case Game.CLUBS:
			if(cpv>0){
				lowCPVClubs+=cpv;
			}
			else{
				highCPVClubs-=cpv;
			}
			totalClubs++;
			break;
		case Game.DIAMONDS:
			if(cpv>0){
				lowCPVDiamonds+=cpv;
			}
			else{
				highCPVDiamonds-=cpv;
			}
			totalDiamonds++;
			break;
		case Game.SPADES:
			if(cpv>0){
				lowCPVSpades+=cpv;
			}
			else{
				highCPVSpades-=cpv;
			}
			totalSpades++;
			break;
		case Game.HEARTS:
			if(cpv>0){
				lowCPVHearts+=cpv;
			}
			else{
				highCPVHearts-=cpv;
			}
			totalHearts++;
			break;
		}		
	}
	
	/**
	 * Adds a card to this deck in a ordered fashion.
	 * TEST ME
	 * @param card
	 */
	public void removeCard(Card card){
		Log.d(TAG, "Removing card from Deck. Card = S:"+card.getSuit()+" V:"+card.getValue());
		if(deck.size()>0){
			removeCardValue(card.getSuit(), card.getValue()-8);
			this.deck.remove(card);
		}
		else{
			Log.d(TAG, "Deck has no cards");
		}
	}
	
	private void removeCardValue(int suit, int cpv){
		switch(suit){
		case Game.CLUBS:
			if(cpv>0){
				lowCPVClubs-=cpv;
			}
			else{
				highCPVClubs+=cpv;
			}
			totalClubs--;
			break;
		case Game.DIAMONDS:
			if(cpv>0){
				lowCPVDiamonds-=cpv;
			}
			else{
				highCPVDiamonds+=cpv;
			}
			totalDiamonds--;
			break;
		case Game.SPADES:
			if(cpv>0){
				lowCPVSpades-=cpv;
			}
			else{
				highCPVSpades+=cpv;
			}
			totalSpades--;
			break;
		case Game.HEARTS:
			if(cpv>0){
				lowCPVHearts-=cpv;
			}
			else{
				highCPVHearts+=cpv;
			}
			totalHearts--;
			break;
		}		
	}	
	
	public Card getCardBelow(Card card, Card nextBest){
		int value =card.getValue();
		Log.d(TAG, "Looking for card below Value:"+value);
		for(int i=deck.size()-1; i>=0;i--){
			Card c =deck.get(i);
			if(c.getValue()<value){
				Log.d(TAG, "Found! Card="+c.name);
				return c;
			}
			nextBest = c;
		}
		Log.d(TAG, "No card Found! NextBest="+nextBest.name);
		return nextBest;		
	}
	
	public int getLowCPV(int suit){
		switch(suit){
			case Game.CLUBS:
				 return lowCPVClubs;			
			case Game.DIAMONDS:
				return lowCPVDiamonds;
			case Game.SPADES:
				return lowCPVSpades;			
			case Game.HEARTS:
				return lowCPVHearts;
			default:
				return 0;
		}				
	}

	public int getHighCPV(int suit){
		switch(suit){
		case Game.CLUBS:
			 return highCPVClubs;			
		case Game.DIAMONDS:
			return highCPVDiamonds;
		case Game.SPADES:
			return highCPVSpades;			
		case Game.HEARTS:
			return highCPVHearts;
		default:
			return 0;
		}
	}	
	
	public int getTotalCPV(int suit){
		switch(suit){
		case Game.CLUBS:
			 return lowCPVClubs+highCPVClubs;			
		case Game.DIAMONDS:
			return lowCPVDiamonds+highCPVDiamonds;
		case Game.SPADES:
			return lowCPVSpades+highCPVSpades;			
		case Game.HEARTS:
			return lowCPVHearts+highCPVHearts;
		default:
			return 0;
		}
	}
	
	public ArrayList<Card> getArrayListDeck(){
		return this.deck;
	}
	
	public void addDeckCards(Deck cards){
		for(int i = 0; i<cards.getSize();i++){
			addCard(cards.getCard(i));
		}
		sortCards();
	}
	
	public void addNewCards(ArrayList<Card> cards){		
		for(Card c : cards){
			addCard(c);
		}
		sortCards();
	}
	
	public void removeCards(ArrayList<Card> cards){
		for(Card c : cards){
			removeCard(c);
		}
	}

	public Card removeCardAtIndex(int i){
		return this.deck.remove(i);
	}
	/**
	 * clears the deck and sets CPV values to 0
	 * does NOT reset Singlesuit.
	 */
	public void clear(){
		lowCPVClubs  = 0;
		highCPVClubs = 0;
		lowCPVDiamonds  = 0;
		highCPVDiamonds = 0;
		lowCPVSpades  = 0;
		highCPVSpades = 0;
		lowCPVHearts  = 0;
		highCPVHearts = 0;		
		this.deck.clear();		
	}

	public int getIndex(Card card){
		return this.deck.indexOf(card);
	}
	
	public int getSize(){
		return this.deck.size();
	}
	
	public Card getCard(int index){
		return this.deck.get(index);
	}
	/**
	 * Sets a new deck without attempting to order.
	 * @param deck
	 */
	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}
		

	public void addCardAtIndex(int position,Card c) {
		deck.add(position, c);
	}

	/**
	 * Get highest card of suit
	 * @param suit
	 * @param count
	 * @return
	 */
	public Card playHighSimple(int suit) {
		Card c = null;
		int orderValueHigh = suit*13 + 14; //Ace
		int orderValueLow = suit*13 + 2;  //Two
		Log.d(TAG, "playHighSimple="+suit+", orderValueHigh="+orderValueHigh+", orderValueLow="+orderValueLow);
		for(int i = deck.size()-1; i>0; i--){
			c = deck.get(i);
			Log.d(TAG, "c.orderValue="+c.orderValue);
			if(c.orderValue <= orderValueHigh && c.orderValue >= orderValueLow){
				Log.d(TAG, "FOUND c.orderValue="+c.orderValue);
				return c;
			}
		}
		Log.d(TAG, "playHighSimple card= null, deck.size="+deck.size());
		return deck.get(0);
		//TODO get high of other suit.
	}
	
	/**
	 * Get Lowest card of suit
	 * @param suit
	 * @param count
	 * @return
	 */
	public Card playLowSimple(int suit) {
		Card c = null;
		int orderValueHigh = suit*13 + 14; //Ace
		int orderValueLow = suit*13 + 2;  //Two
		for(int i = 0; i<deck.size(); i++){
			c = deck.get(i);
			if(c.orderValue>orderValueLow && c.orderValue<orderValueHigh){
				//return first card found of this suit
				return c;
			}
		}
		//TODO get high of other suit.
		return null;
	}

	/**
	 * Check should have run we have queen  
	 * @return the Queen of spades
	 */
	public Card getQueenOfSpades() {
		for(int i = 0; i<deck.size(); i++){
			if(deck.get(i).orderValue ==38){
				return deck.get(i);
			}
		}
		Log.d(TAG, "Error: getQueenOfSpades() no Queen found.");
		return null;
	}


	public int getLargestSuit() {
		int largestSuit = Game.CLUBS;
		int suitCards = 0;
		if(suitCards > totalClubs){
			suitCards = totalClubs;
			largestSuit = Game.CLUBS;
		}
		if(suitCards > totalDiamonds){
			suitCards = totalDiamonds;
			largestSuit = Game.DIAMONDS;
		}
		if(suitCards > totalSpades){
			suitCards = totalSpades;
			largestSuit = Game.SPADES;
		}
		if(suitCards > totalHearts){
			suitCards = totalHearts;
			largestSuit = Game.HEARTS;
		}
		Log.d(TAG, "getLargestSuit="+largestSuit);
		return largestSuit;
	}


	public boolean checkForTwo() {
		for(Card c : deck){
			if(c.orderValue == 2)
				return true;
		}
		return false;
	}


	public Card getTwoOfClubs() {
		for(Card c : deck){
			if(c.orderValue == 2)
				return c;
		}
		return null;
	}


	public boolean checkForQueen() {
		for(Card c : deck){
			if(c.orderValue == 38)
				return true;
		}
		return false;
	}

	public boolean checkVoid(int suit) {
		Log.d(TAG, "CheckingVoid");
		switch(suit){
			case Game.CLUBS:
				if(totalClubs<=0){
					return true;
				}
				 return false;			
			case Game.DIAMONDS:
				if(totalDiamonds<=0){
					return true;
				}
				 return false;	
			 case Game.SPADES:
				 if(totalSpades<=0){
						return true;
					}
					 return false;				
			case Game.HEARTS:
				if(totalHearts<=0){
					return true;
				}
				 return false;	
			default:
				return false;
		}			
	}

	/**
	 * Gets what suit has the Highest Card value
	 * @return
	 */
	public int getHeighestCPVSuit() {
		//TODO <= less than or equal to
		int largestSuit = Game.CLUBS;
		int suitCPV = 0;
		if(suitCPV < highCPVClubs){
			suitCPV =highCPVClubs;
			largestSuit = Game.CLUBS;
		}
		if(suitCPV < highCPVDiamonds){
			suitCPV =highCPVDiamonds;
			largestSuit = Game.DIAMONDS;
		}
		if(suitCPV < highCPVSpades){
			suitCPV =highCPVSpades;
			largestSuit = Game.SPADES;
		}
		if(suitCPV < highCPVHearts){
			suitCPV =highCPVHearts;
			largestSuit = Game.HEARTS;
		}
		Log.d(TAG, "getHeighestCPVSuit="+largestSuit);		
		return largestSuit;
	}	
	
	public int getLowestCPVSuit() {
		int largestSuit = Game.CLUBS;
		int suitCPV = 0;
		if(suitCPV < lowCPVClubs){
			suitCPV =lowCPVClubs;
			largestSuit = Game.CLUBS;
		}
		if(suitCPV < lowCPVDiamonds){
			suitCPV =lowCPVDiamonds;
			largestSuit = Game.DIAMONDS;
		}
		if(suitCPV < lowCPVSpades){
			suitCPV =lowCPVSpades;
			largestSuit = Game.SPADES;
		}
		if(suitCPV < lowCPVHearts){
			suitCPV =lowCPVHearts;
			largestSuit = Game.HEARTS;
		}
		return largestSuit;
	}

	public Card getDeckCardBelow(Card highCard, boolean b, Card bestPick) {
		int highCardValue = highCard.orderValue;
		int orderValueLow = highCard.getSuit()*13 + 2;  //Two

		Card c = null;
		for(int i = deck.size()-1; i<=0; i--){
			c = deck.get(i);
			if(c.orderValue<highCardValue && c.orderValue>orderValueLow){
				//return first card found of this suit
				return c;
			}
		}
		Log.d(TAG, "Error getDeckCardBelow. No card found.");
		//TODO get other card.
		//For now just return card given
		return highCard;
	}
	
	public int totalCardsFromSuit(int suit){
		switch(suit){
			case Game.CLUBS:
				return totalClubs;
			case Game.DIAMONDS:
				return totalDiamonds;
			 case Game.SPADES:
				 return totalSpades;
			case Game.HEARTS:
				return totalHearts;
			default:
				return 0;
		}			
		
	}
	
	/**
	 * Sort all the cards by cardValue
	 */
	public void sortCards(){
		Collections.sort(deck, new CardComparator() );
	}
	

class CardComparator implements Comparator<Card> {
    @Override
    public int compare(Card a, Card b) {
        return a.orderValue < b.orderValue ? -1 : a.orderValue == b.orderValue ? 0 : 1;
    }
}

	
}
