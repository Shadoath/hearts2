package game.shad.tempus.hearts;


import java.util.ArrayList;

import android.util.Log;

public class Deck {
	public static final String TAG = "Hearts--Deck";

	private ArrayList<Card> deck;
	/**
	 * Low Card Point Value 
	 * Cards 2-7
	 * Max points 21
	 */
	private int lowCPV = 0;
	/**
	 * High Card Point Value 
	 * Cards 9-14
	 * Max points 21
	 */
	private int highCPV = 0;
	private int deckSuit = 4;
	private boolean singleSuit = true;
	
	public Deck(){
		deck = new ArrayList<Card>();
	}
	
	/**
	 * Adds a card to this deck in a ordered fashion.
	 * TEST ME
	 * @param card
	 */
	public void addCard(Card card){
		int cardValue =card.getValue();
		int cardSuit =card.getSuit();
		int cardPointValue = card.getValue()-8;
		if(!singleSuit){
			Log.d(TAG, "Single suit is false adding card to deck without order.");
			deck.add(card);
			return;
		}
		if(deckSuit==4){//not set
			Log.d(TAG, "Setting DeckSuit!");
			deckSuit=cardSuit;
		}
		else if(deckSuit!=cardSuit){
			Log.d(TAG, "!!!!!Trying to add Card S:"+cardSuit+" V:"+cardValue+" which is different than original deckSuit="+deckSuit);
			return;


		}
		Log.d(TAG, "Adding card to Deck. Card = Suit:"+cardSuit+" Value:"+cardValue);
		boolean added=false;
		if(deck.size()>0){
			if(cardPointValue<=0){//Start from bottom of deck
				Log.d(TAG, "Going from bottom of the deck. CPV="+cardPointValue);
				lowCPV += cardPointValue;
				for(int i=0; i<deck.size();i++){
					int dv = deck.get(i).getValue();//waist of a variable but good to debug
					if(dv>cardValue){
						Log.d(TAG, "Adding at i="+i);
						this.deck.add(i, card);
						return;
					}
				}
			}
			else{//start from top of deck
				Log.d(TAG, "Going from top of the deck. CPV="+cardPointValue);
				highCPV += cardPointValue;
				for(int i=deck.size()-1; i>=0;i--){
					int dv = deck.get(i).getValue();
					if(dv>cardValue){
						continue;
						
					}
					if(dv==cardValue){
						Log.d(TAG, "deckValue==CardValue, adding card at i="+i);
						Log.d(TAG, "This should only occur when singleSingle suit is false");
						deck.add(i, card);
						return;
					}
					if(dv<cardValue){
						Log.d(TAG, "i="+i);
						deck.add(i, card);
						Log.d(TAG, "deckValue<CardValue, adding card at i="+i);
						return;
					}
							
					
					
//					if(deck.get(i).getValue()<cardValue){
//						Log.d(TAG, "Adding at i="+i);
//						this.deck.add(i+1, card);
//						added=true;
//						break;
//					}
				}
			}
		}
		if(!added){
			Log.d(TAG, "Adding card to end of deck");
			this.deck.add(card);
		}
	}
	
	/**
	 * Adds a card to this deck in a ordered fashion.
	 * TEST ME
	 * @param card
	 */
	public void removeCard(Card card){
		int cardValue =card.getValue();
		int cardPointValue = card.getValue()-8;
		Log.d(TAG, "Removing card from Deck. Card = S:"+card.getSuit()+" V:"+cardValue);
		if(deck.size()>0){
			if(cardPointValue<0){//Start from bottom of deck
				Log.d(TAG, "lowCPV="+cardPointValue);
				lowCPV -= cardPointValue;
				this.deck.remove(card);
			}
			
			else{//start from top of deck
				Log.d(TAG, "highCPV="+cardPointValue);
				highCPV -= cardPointValue;
				this.deck.remove(card);
			}
		}
		else{
			Log.d(TAG, "Deck has no cards");
		}
	}
	
	public Card getLowCard(){
		Log.d(TAG, "Get Low Card");
		if(deck.size()>0)
			return deck.get(0);
		else
			Log.d(TAG, "No Cards left");
			return null;
	}
	public Card getHighCard(){
		Log.d(TAG, "Get High Card");
		if(deck.size()>0)
			return deck.get(deck.size()-1);
		else
			Log.d(TAG, "No Cards left");
			return null;
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
	
	public int getLowCPV(){
		return lowCPV;
	}

	public int getHighCPV(){
		return highCPV;
	}
	
	public int getTotalCPV(){
		return lowCPV+highCPV;
	}
	
	public ArrayList<Card> getArrayListDeck(){
		return this.deck;
	}
	
	public void addDeckCards(Deck cards){
		for(int i = 0; i<cards.getSize();i++){
			addCard(cards.getCard(i));
		}
	}
	
	public void addCards(ArrayList<Card> cards){
		for(Card c : cards){
			addCard(c);
		}
	}
	
	public void removeCards(ArrayList<Card> cards){
		for(Card c : cards){
			removeCard(c);
		}
	}

	public void removeCardAtIndex(int i){
		this.deck.remove(i);
	}
	/**
	 * clears the deck and sets CPV values to 0
	 * does NOT reset Singlesuit.
	 */
	public void clear(){
		lowCPV  = 0;
		highCPV = 0;
		deckSuit= 4;
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
	
	public void setSingleSuit(boolean b){
		singleSuit = b;
	}
	public boolean getSingleSuit(){
		return singleSuit;
	}
}
