package game.shad.tempus.hearts;



import android.util.Log;

public class PlayerDeck extends SuperDeck{
	public static final String TAG = "Hearts--PlayerDeck";
	public boolean hasQueen;
	public PlayerDeck(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get the card that is just below the card sent for a value.
	 * Use to keep low cards longer.
	 * @param cardToBeat Card to be lower than
	 * @param lastPlayer if we are the last player of the trick, if true and we have no cards lower just play highest card.
	 */
	public Card getDeckCardBelow(Card cardToBeat, boolean lastPlayer, Card startCard){
		Card bestPick = null;
		switch (cardToBeat.getSuit()){
		case 0:
			bestPick = clubCards.getCardBelow(cardToBeat, startCard);
			break;
		case 1:
			bestPick = diamondCards.getCardBelow(cardToBeat, startCard);
			break;
		case 2:
			bestPick = spadeCards.getCardBelow(cardToBeat, startCard);
			break;
		case 3:
			bestPick = heartCards.getCardBelow(cardToBeat, startCard);
			break;
		}
		if(bestPick.getSuit()==-1){
			Log.d(TAG, "No card found, Deck was empty.  Returning 3 of clubs");
			Log.d(TAG, "Bad call to get CardBelow!!");
			bestPick.setSuit(0);
			bestPick.setValue(3);
			return bestPick;
		}
		if(bestPick.getValue()>cardToBeat.getValue()){
			Log.d(TAG, "!Card is above!");
			if(lastPlayer){
				Log.d(TAG, "Last Player, getting highest Card of Suit(");
				Card highCard = null;
				switch(cardToBeat.getSuit()){
					case 0:
						highCard = clubCards.getHighCard();
						break;
					case 1:
						highCard = diamondCards.getHighCard();
						break;
					case 2:
						highCard = spadeCards.getHighCard();
						break;
					case 3:
						highCard = heartCards.getHighCard();
						break;
					}
				if (highCard!=null){
					Log.d(TAG, "highcard Found");
					bestPick=highCard;
				}
			}
			else{
				Log.d(TAG, "Not last Player");
			}
		}
		Log.d(TAG, "Playing Best Pick");
		return bestPick;
		
	}

	/**
	 * Check deck to see if that suit is out of cards
	 * @param suit 0=clubs 1=diamonds 2=spades 3=hearts
	 * @return true if out of cards.
	 */
	public boolean checkVoid(int suit){
		switch(suit){
		case 0:
			if(clubCards.getSize()==0){
				return true;
			}
		break;
			case 1:
			if(spadeCards.getSize()==0){
				return true;
			}
		break;
		case 2:
			if(diamondCards.getSize()==0){
				return  true;
			}
		break;
		case 3:
			if(heartCards.getSize()==0){
				return true;
			}
		break;
		}
		return false;
		
	}
	
	/**
	 * Finds the Queen of spades
	 * Sets Card queenOfSpades to the queen for later use with GetQueenOfSpades
	 * REMOVES queen from normal Deck.
	 * returns true if found
	 * 
	 * @return
	 */
	public boolean checkForQueen(){
		for (int i=0;i<spadeCards.getSize();i++){//Should only need to check the first two the rest is overkill.
			if(this.spadeCards.getCard(i).getValue()==(12)){
				hasQueen = true;
				return true;
			}
		}
		hasQueen = false;
		return false;
	}
	/**
	 * Gets queen from deck.
	 * Removes Card from Deck
	 * Must run checkForQueen first
	 * @return
	 */
	public Card getQueenOfSpades(){
		Card Queen = null;
		for (int i=0;i<spadeCards.getSize();i++){//Should only need to check the first two the rest is overkill.
			if(this.spadeCards.getCard(i).getValue()==(12)){
				Queen= this.spadeCards.removeCardAtIndex(i);
			}
		
		
		}
		if (Queen==null){
			Log.d(TAG, "NO QUEEN FOUND!!");
			return new Card(3, 0, game);
		}
		return Queen;
		
}
	
	/**
	 * Finds the two of Clubs 
	 * Sets Card twoOfClubs to the two for later use with getTwoOfClubs

	 * returns true if found
	 * 
	 * @return
	 */
	public boolean checkForTwo(){
		for (int i=0;i<clubCards.getSize();i++){//Should only need to check the first two the rest is overkill.
			if(this.clubCards.getCard(i).getValue()==(2)){
				return true;
			}
		}
			return false;
	}
	
	public Card getTwoOfClubs(){
		Card Two = null;
		for (int i=0;i<clubCards.getSize();i++){//Should only need to check the first two the rest is overkill.
			if(this.clubCards.getCard(i).getValue()==(2)){
				Two = this.clubCards.getCard(i);
			}
		}
		if (Two==null){
			Log.d(TAG, "twoOfClubs Null, Run check first!");
			return new Card(3, 0, game);
		}
		return Two;
	}
	
	
		
}
