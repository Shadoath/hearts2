package game.shad.tempus.hearts;


import java.util.ArrayList;

import android.R.integer;
import android.util.Log;

/**
 * Super deck to keep track of all the cards in four decks and then extend to other clases/
 * @author Sky
 *
 */
public abstract class SuperDeck {
	public static final String TAG = "Hearts--SuperDeck";

	public Deck clubCards;
	public Deck diamondCards;
	public Deck spadeCards;
	public Deck heartCards;

	public Game game;
	
	
	public SuperDeck(Game game){
		this.game = game;
		clubCards = new Deck();
		diamondCards = new Deck();
		spadeCards = new Deck();
		heartCards = new Deck();
	}
	
	/**
	 * Does no Checks just Plays low suit.
	 * Recursive method to get the lowest card of a suit OR roll-over to the next suit.
	 * @param suit we want to get
	 * @param count How many times playHigh is called; only used if called in error and there are no cards left.
	 * When count>6 returns a new card with a red back. used to get out of a endless loop.
	 * @return Card to play
	 */
	public Card playLowSimple(int suit, int count){
		Log.d(TAG, "Play Low Simple");
		if(count>6){
			Log.d(TAG, "PlayLow is Out of cards!!!");
			return new Card(3, 0, game);	//Red back card
		}
		switch(suit){
			case 0:
				if(clubCards.getSize()==0){
					Log.d(TAG, "out of Clubs");
					return playLowSimple(++suit, ++count);
				}
				Log.d(TAG, "Playing Club");
				return clubCards.getCard(0);
			case 1:
				if(diamondCards.getSize()==0){
					Log.d(TAG, "out of Diamonds");
	
					return playLowSimple(++suit, ++count);
				}
				Log.d(TAG, "Playing Diamond");
				return diamondCards.getCard(0);
			case 2:
				if(spadeCards.getSize()==0){			
					Log.d(TAG, "out of Spades");
					return playLowSimple(++suit, ++count);
				}
				Log.d(TAG, "Playing Spade");
				return spadeCards.getCard(0);
	
			case 3: 
				if(heartCards.getSize()==0){
					Log.d(TAG, "out of Hearts");
					return playLowSimple(0, ++count);
				}
				Log.d(TAG, "Playing Hearts");
				return heartCards.getCard(0);
			case 4:
				Log.d(TAG, "Error no suit 4!");
			}	
		Log.d(TAG, "count="+count);
		
		return new Card(3, 0, game);
	}
		
	/**
	 * Recursive method to get the highest card of a suit OR roll-over to the next suit.
	 * @param suit we want to get
	 * @param count How many times playHigh is called; only used if called in error and there are no cards left.
	 * When count>6 returns a new card with a red back. used to get out of a endless loop.
	 * @return Card to play
	 */
	public Card playHighSimple(int suit, int count){
		Log.d(TAG, "Play HighSimple");
		Card nextCard = null;
		if(count>6){
			Log.d(TAG, "Out of cards!!!");
			return new Card(3, 0, game); //Red back card
		}
		switch(suit){
		case 0:
			if(clubCards.getSize()==0){  //if suit is empty just recall this method with a higher suit.
				Log.d(TAG, "out of Clubs");
				return playHighSimple(++suit, ++count);
			}
			else{
				Log.d(TAG, "Playing Club");
				return clubCards.getCard(clubCards.getSize()-1);
			}	
		case 1:
			if(diamondCards.getSize()==0){
				Log.d(TAG, "out of Diamonds");
				return playHighSimple(++suit, ++count);
			}
			else{
				Log.d(TAG, "Playing Diamond");
				return diamondCards.getCard(diamondCards.getSize()-1);
			}
		case 2:
			if(spadeCards.getSize()==0){
				Log.d(TAG, "out of Spades");
				return playHighSimple(++suit, ++count);
			}else{
				Log.d(TAG, "Playing Spade");
				return spadeCards.getCard(spadeCards.getSize()-1);
			}
		case 3: 
			if(heartCards.getSize()==0){
				Log.d(TAG, "out of Hearts");
				return playHighSimple(0, ++count);
			}
			Log.d(TAG, "Playing heart");
			return heartCards.getCard(heartCards.getSize()-1);
		
		case 4:
			Log.d(TAG, "Error should not be case 4");
		}
		return nextCard;
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
		for(int i=0; i<diamondCards.getSize();i++){
			if(diamondCards.getCard(i).getValue()==11){
				points-=10;
				break;

			}
		}
		for(int i=0; i<spadeCards.getSize();i++){
			if(spadeCards.getCard(i).getValue()==12){
				points+=13;
				break;
			}
			
		}
		for(int i=0; i<heartCards.getSize();i++){
				points++;
		}
		Log.d(TAG, "Points found= "+points);
		return points;
	}

	public void addCard(Card card){
		switch(card.getSuit()){
		case 0:	
			clubCards.addCard(card);
			break;
		case 1:
			diamondCards.addCard(card);
			break;
		case 2:
			spadeCards.addCard(card);
			break;
		case 3:
			heartCards.addCard(card);
			break;
		}
	}
	
	public void removeCard(Card card){
		switch(card.getSuit()){
		case 0:	
			clubCards.removeCard(card);
			break;
		case 1:
			diamondCards.removeCard(card);
			break;
		case 2:
			spadeCards.removeCard(card);
			break;
		case 3:
			heartCards.removeCard(card);
			break;
		}
	}
	
	public void addAllCards(Deck cards){
		for(int i = 0; i<cards.getSize();i++){
			addCard(cards.getCard(i));
		}
	}
	public void removeAllCards(Deck cards){
		for(int i = 0; i<cards.getSize();i++){
			removeCard(cards.getCard(i));
		}
	}
	
	public void addCards(ArrayList<Card> cards){
		for(Card c : cards){
			Log.d(TAG, "card added="+c.name);
			addCard(c);
		}
	}
	public void removeCards(ArrayList<Card> cards){
		for(Card c : cards){
			Log.d(TAG, "card removed="+c.name);
			removeCard(c);
		}
	}
	
	/**
	 * Finds the largest suit deck in hand
	 * @return int as suit; 
	 * 0=clubs
	 * 1=diamonds
	 * 2=spades
	 * 3=hearts
	 */
	public int getLargestSuit(){
		Log.d(TAG, "getLargestSuit");
		int c = this.clubCards.getSize();
		int d = this.diamondCards.getSize();
		int s = this.spadeCards.getSize();
		int h = this.heartCards.getSize();
		int[] test = {c, d, s, h};
		int position = 0;
		int high = 0;
		for(int i=0; i<test.length;i++){
			if(test[i]>high){
				position = i;
				high = test[i];
			}

		}
		Log.d(TAG, "Largest suit position is ="+position);
		return position;
			
	}
	
	/**
	 * Returns the whole SuperDeck as a Deck
	 */
	public Deck getFullDeck(){
		Deck fullDeck = new Deck();
		fullDeck.setSingleSuit(false);
		fullDeck.addCards(clubCards.getArrayListDeck());
		fullDeck.addCards(diamondCards.getArrayListDeck());
		fullDeck.addCards(spadeCards.getArrayListDeck());
		fullDeck.addCards(heartCards.getArrayListDeck());
		return fullDeck;
	}
	
	/**
	 * Returns the whole SuperDeck as a ArrayList<Card>
	 */
	public ArrayList<Card> getDeck(){
		ArrayList<Card> fullDeck = new ArrayList<Card>();
		fullDeck.addAll(clubCards.getArrayListDeck());
		fullDeck.addAll(diamondCards.getArrayListDeck());
		fullDeck.addAll(spadeCards.getArrayListDeck());
		fullDeck.addAll(heartCards.getArrayListDeck());
		return fullDeck;
	}
	
	/**
	 * Finds the worst Card Point Value (CPV) suit
	 * @return
	 */
	public int getWorstCPVSuit(){
		Log.d(TAG, "find worst CPV Suit");
		int c = this.clubCards.getTotalCPV();
		int d = this.diamondCards.getTotalCPV();
		int s = this.spadeCards.getTotalCPV();
		int h = this.heartCards.getTotalCPV();
		int[] test = {c, d, s, h};
		int position = 0;
		int high = 0;
		for(int i=0; i<test.length;i++){
			if(test[i]>high){
				position = i;
				high = test[i];
			}

		}
		Log.d(TAG, "Largest CPV suit is ="+position);
		return position;
			
	}
	
	/**
	 * Finds the worst Card Point Value (CPV) suit
	 * TODO NEEDS TESTING
	 * @return
	 */
	public int getLowestCPVSuit(){
		Log.d(TAG, "find worst CPV Suit");
		int c = this.clubCards.getTotalCPV();
		int d = this.diamondCards.getTotalCPV();
		int s = this.spadeCards.getTotalCPV();
		int h = this.heartCards.getTotalCPV();
		int[] test = {c, d, s, h};
		int position = 0;
		int low = 0;
		for(int i=0; i<test.length;i++){
			if(test[i]<low){
				position = i;
				low = test[i];
			}
		}
		Log.d(TAG, "Lowest CPV suit is ="+position);
		return position;
			
	}
	public Card getWorstCPVCard(int suit){
		Log.d(TAG, "find worst CPV card in suit="+suit);
		Card worstCard=null;
		switch(suit){
			case 0:	
				worstCard = clubCards.getCard(clubCards.getSize()-1);
				break;
			case 1:
				//Never give a high diamond
				worstCard = diamondCards.getCard(0);
				break;
			case 2:
				worstCard = spadeCards.getCard(spadeCards.getSize()-1);
				break;
			case 3:
				worstCard = heartCards.getCard(heartCards.getSize()-1);
				break;
		}
		Log.d(TAG, "Worst CPV card is ="+worstCard.name);
		return worstCard;
			
	}

	public int getTotalCPV(int suit){
		int total=0;
		switch (suit){
		case 0:
			total = clubCards.getTotalCPV();
			break;
		case 1:
			total = diamondCards.getTotalCPV();
			break;
		case 2:
			total = spadeCards.getTotalCPV();
			break;
		case 3:
			total = heartCards.getTotalCPV();
			break;
		}
	return total;
	}
	public int getLowCPV(int suit){
		int total=0;
		switch (suit){
		case 0:
			total = clubCards.getLowCPV();
			break;
		case 1:
			total = diamondCards.getLowCPV();
			break;
		case 2:
			total = spadeCards.getLowCPV();
			break;
		case 3:
			total = heartCards.getLowCPV();
			break;
		}
	return total;
	}
	public int getHighCPV(int suit){
		int total=0;
		switch (suit){
		case 0:
			total = clubCards.getHighCPV();
			break;
		case 1:
			total = diamondCards.getHighCPV();
			break;
		case 2:
			total = spadeCards.getHighCPV();
			break;
		case 3:
			total = heartCards.getHighCPV();
			break;
		}
	return total;
	}
	
	public void clear(){
		this.clubCards.clear();
		this.diamondCards.clear();
		this.spadeCards.clear();
		this.heartCards.clear();
	}
				
	public int getSize(){
		return this.clubCards.getSize()+this.diamondCards.getSize()+this.spadeCards.getSize()+this.heartCards.getSize();
	}
	
	public Deck getClubs(){
		return clubCards;
	}
	public Deck getDiamonds(){
		return diamondCards;
	}
	public Deck getSpades(){
		return spadeCards;
	}
	public Deck getHearts(){
		return heartCards;
	}
	
		
}
