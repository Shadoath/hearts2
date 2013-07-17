package game.shad.tempus.hearts;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
//TODO jack check to not give away so easy.		Also Not give away high diamonds on trade.
@SuppressLint("ParserError")
public class OldPlayer {
	public static final String TAG = "Hearts--Player--";
	private MainActivity main;
	private Game game;
	ArrayList<Card> hand;
    public ArrayList<Card> cardsToTrade=new ArrayList<Card>();

	private OldDeck deck =new OldDeck();
	public int seat=0; //position
	public String realName = "";
	public String shortName = "";
	public int colorInt = 0;
	public int AISmarts = 1;	//1 dumb, 2 normal, 3 smart, 11 dog
	public int state = 0;   //using 1-4
	public int score = 0;
	public int totalScore=0;
	public int passTo = 0;
	public OldDeck clubs = new OldDeck();     //0
	int highClub = 0;
	int lowClub = 0;
	public OldDeck diamonds = new OldDeck();  //1
	int highDiamonds = 0;
	int lowDiamonds = 0;
	public OldDeck spades = new OldDeck();	   //2
	int highSpades = 0;
	int lowSpades = 0;
	public OldDeck hearts = new OldDeck();    //3
	int highHearts = 0;
	int lowHearts = 0;
	boolean winner = false;
	boolean voidClubs;
	boolean voidDiamonds;
	boolean voidHearts;
	boolean voidSpades;
	boolean hasQueen=false;
	boolean playingVoid=false;
	boolean tradingCardsRemoved=false;
	
	public boolean claimedDeck = false;
	public boolean sneakPeak = false;
	
	private int IQ;	//Then just a simple slider to change it and easy greater than if statments to do bot AI.
	//This will let the IQ be changed in game. just Put a slider around the AI portrait.
	public LayoutInflater factory;

	public View textEntryView;
	public Card twoOfClubs = null;
	
	public OldPlayer(MainActivity main, Game game, OldDeck deck, int AISmarts, int seat, String name, int color){
		this.main =main;
		this.game = game;
		this.deck = deck;
		this.AISmarts = AISmarts;
		this.seat = seat;
		setShortName();
		this.realName = name;
		this.colorInt = color;
		sortHandFromDeck();
		claimedDeck=false;	//Set after sort hand so that after trading cards can be claimed.
		//TODO add playerholder link here.
		
	}
	/**
	 * Used when Saving game data, for consistancy.
	 */
	public void setShortName(){
		switch(seat){
		case 1:
			shortName="P1";
			break;
		case 2:
			shortName="P2";
			break;
		case 3:
			shortName="P3";
			break;
			
		case 4:
			shortName="P4";
			break;
		}
		Log.d(TAG, "shortName="+this.shortName);
		
	}
	
	/**
	 * Basic call to get a card from the player.
	 * Runs a simple switch on AISmarts to run correct go.
	 * @param round Current round in the game.
	 * @param trick the current trick.
	 * @return Card to play.
	 */
	public Card go(int round, Trick trick){
		switch (AISmarts){
		case 1: 
			return goDumb(round, trick);
		case 2:
			return goNormal(round, trick);
		case 3:
			return goNormal(round, trick);

		case 11:
			return goNormal(round, trick);
		}
		return goNormal(round, trick);

	}
	
	/**
	 * Picks the next card from Player's suit
	 * @param round
	 * @param trick
	 * @return
	 */
	public Card goDumb(int round, Trick trick){
		Log.d(TAG+this.getRealName(), "player.go ="+this.realName);
		
		if(trick.getSize()==0){	
			Log.d(TAG+this.getRealName(), "First Card of Pile");
			return playLow(getLargestSuit(), 0);
		}
		
		int startSuit=trick.getCard(0).getSuit();
		Log.d(TAG+this.getRealName(), "Start suit is "+startSuit);
		if(checkVoid(startSuit)){//This is the void CODE
			if(hasQueen){
				Log.d(TAG+this.getRealName(), "Queencheck = true");
				if(game.round!=1){
					return getQueen();
				}
			}
			else{
				Log.d(TAG+this.getRealName(), "hasQueen = false");
			}
			Log.d(TAG+this.getRealName(), "void--PlayHigh(getLargestSuit)");
			if(game.round==1){
				playHighDontBreakHearts(); //Play a high card but no points!
			}
			return playHighSimple(getLargestSuit(), 0);
		}
		boolean trickHasPoints = trick.hasPoints();
		boolean trickNegativePoints = trick.hasNegativePoints();
		switch (state){  //Set at the start of each round. Basically says where in the rotation does this player sit.
		//No case 1: that is for picking start card.  Taken care of above.
		case 2:
			Log.d(TAG+this.getRealName(), "Seat 2!");

			switch (round){
			case 1: 
				return playHighSimple(0, 0);
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				Log.d(TAG+this.getRealName(), "round 2-7 --PlayLow("+startSuit+")");
				return playLowSimple(startSuit, 0);
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:			
			case 13:

				Log.d(TAG+this.getRealName(), "round 8-13 --PlayLow("+startSuit+")");
				return playLowSimple(startSuit, 0);
			}
		case 3:
			Log.d(TAG+this.getRealName(), "Seat 3!");
			switch (round){
			case 1: 
				return playHighSimple(0, 0);
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
				if(trickHasPoints){
					Log.d(TAG+this.getRealName(), "Play low/CardBelow--POINTS in pile!!");
					return getDeckCardBelow(trick.getHighCard(), false);
				}
				if(trickNegativePoints)
					playHighSimple(startSuit, 0);
				else {
					return getDeckCardBelow(trick.getHighCard(), false);
				}
			}
		case 4:
			if(round==1){
				playHighSimple(0, 0);
			}
			Log.d(TAG+this.getRealName(), "Seat 4!");
			if(trickHasPoints){
				Log.d(TAG+this.getRealName(), "Play below highest in pile --POINTS in pile!!");
				return getDeckCardBelow(trick.getHighCard(), true);
			}
			if(trickNegativePoints){
				playHighSimple(startSuit, 0);
			}
			else if(startSuit==2 && hasQueen){
				if(checkForCardsHigher(trick.TrickToDeck(), 12)){
					return getQueen();
				}
			}
			//take high
			return playHighSimple(startSuit, 0);
		}
			
		Log.d(TAG+this.getRealName(), "++++++Out of all Loops !!SHOULD NOT HAPPEN!!--PlayHigh("+startSuit+")");
		Log.d(TAG+this.getRealName(), "++++++retuning 3 0 to not crash CODE!");

		Toast.makeText(main, "OUT OF CARDS!!", Toast.LENGTH_SHORT).show();
		Card oddBall = new Card(3, 0, game);
//		oddBall.setOwner(this);
		return oddBall;
	}
	
	public Card goNormal(int round, Trick trick){
		Log.d(TAG+this.getRealName(), "player.go ="+this.realName);
		
		if(trick.getSize()==0){	
			Log.d(TAG+this.getRealName(), "First Card of Pile");
			//TODO add more AI smarts here
			return playLow(getLargestSuit(), 0);
		}
		
		int startSuit=trick.getCard(0).getSuit();
		Log.d(TAG+this.getRealName(), "Start suit is "+startSuit);
		if(checkVoid(startSuit)){//This is the void CODE
			if(hasQueen){
				Log.d(TAG+this.getRealName(), "Queencheck = true");
				if(game.round!=1){
					return getQueen();
				}
			}
			else{
				Log.d(TAG+this.getRealName(), "hasQueen = false");
				checkForQueen();
				if(hasQueen){//This SHOULD  be redundant.
					Log.d(TAG+this.getRealName(), "Double  checkForQueen = true");
					return getQueen();
				}
			}
			Log.d(TAG+this.getRealName(), "void--PlayHigh(getLargestSuit)");
			if(game.round==1){
				playHighDontBreakHearts(); //Play a high card but no points!
			}
			return playHighSimple(getLargestSuit(), 0);
			//TODO better code for voids
		}
		boolean trickHasPoints = trick.hasPoints();
		boolean trickNegativePoints = trick.hasNegativePoints();
		switch (state){  //Set at the start of each round. Basically says where in the rotation does this player sit.
		//No case 1: that is for picking start card.  Taken care of above.
		case 2:
			Log.d(TAG+this.getRealName(), "Seat 2!");

			switch (round){
			case 1: 
				return playHighSimple(0, 0);
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				Log.d(TAG+this.getRealName(), "round 0-7 --PlayLow("+startSuit+")");
				return playLowSimple(startSuit, 0);
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:			
			case 13:

				Log.d(TAG+this.getRealName(), "round 8-12 --PlayLow("+startSuit+")");
				return playLowSimple(startSuit, 0);
			}
		case 3:
			Log.d(TAG+this.getRealName(), "Seat 3!");
			switch (round){
			case 1: 
				return playHighSimple(0, 0);
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
				if(trickHasPoints){
					Log.d(TAG+this.getRealName(), "Play low/CardBelow--POINTS in pile!!");
					return getDeckCardBelow(trick.getHighCard(), false);
				}
				if(trickNegativePoints)
					playHighSimple(startSuit, 0);
				else {
					
					return getDeckCardBelow(trick.getHighCard(), false);
				}
				}
		case 4:
			if(round==1){
				playHighSimple(0, 0);
			}
			Log.d(TAG+this.getRealName(), "Seat 4!");
			if(trickHasPoints){
				Log.d(TAG+this.getRealName(), "Play below highest in pile --POINTS in pile!!");
				return getDeckCardBelow(trick.getHighCard(), true);
			}
			else if(startSuit==2 && hasQueen){
				if(checkForCardsHigher(trick.TrickToDeck(), 12)){//TODO build method into Trick class
					return getQueen();
				}
				else
					return playLowSimple(startSuit, 0);
			}
			//take high
			return playHighSimple(startSuit, 0);
		}
			
		Log.d(TAG+this.getRealName(), "++++++Out of all Loops !!SHOULD NOT HAPPEN!!--PlayHigh("+startSuit+")");
		Log.d(TAG+this.getRealName(), "++++++retuning 3 0 to not crash CODE!");

		Toast.makeText(main, "OUT OF CARDS!!", Toast.LENGTH_SHORT).show();
		Card oddBall = new Card(3, 0, game);
//		oddBall.setOwner(this);
		return oddBall;
	}
	
	/**
	 * Recursive method to get the lowest card of a suit OR roll-over to the next suit.
	 * @param suit we want to get
	 * @param count How many times playHigh is called; only used if called in error and there are no cards left.
	 * When count>6 returns a new card with a red back. used to get out of a endless loop.
	 * @return Card to play
	 */
	public Card playLow(int suit, int count){
		Log.d(TAG+this.getRealName(), "Play Low");
		Card nextCard = null;

		if(count>6){
			Log.d(TAG+this.getRealName(), "PlayLow is Out of cards!!!");
			return new Card(3, 0, game);	//Red back card
		}
		switch(suit){
			case 0:
				if(clubs.getSize()==0){
					Log.d(TAG+this.getRealName(), "out of Clubs");
	
					return playLow(++suit, ++count);
				}
				Log.d(TAG+this.getRealName(), "Playing Club");
				nextCard = this.clubs.getCard(0);
				return nextCard;
			case 1:
				if(diamonds.getSize()==0){
					Log.d(TAG+this.getRealName(), "out of Diamonds");
	
					return playLow(++suit, ++count);
				}
				Log.d(TAG+this.getRealName(), "Playing Diamond");
				nextCard=diamonds.getCard(0);
				return nextCard;
			case 2:
				if(spades.getSize()==0){			
					Log.d(TAG+this.getRealName(), "out of Spades");
					return playLow(++suit, ++count);
				}
				Log.d(TAG+this.getRealName(), "Playing Spade");
	
				nextCard=spades.getCard(0);
				if(spades.getSize()>1 && nextCard.getValue()==12){	//This should fix dropping queen on self
					Log.d(TAG+this.getRealName(), "play low, trying to playing queen");
					nextCard=spades.getCard(1);
					return nextCard;
				}
				return nextCard;
			case 3: 
				if(hearts.getSize()==0){
					Log.d(TAG+this.getRealName(), "out of Hearts");
					return playLow(0, ++count);
				}
				else if(game.heartsBroken||game.trading){
					Log.d(TAG+this.getRealName(), "Playing Hearts");
					nextCard=hearts.getCard(0);
					this.playingVoid=false;
					return nextCard;
				}
				else{
					Log.d(TAG+this.getRealName(), "hearts not broken!");
					return playLow(0, ++count);
				}
			case 4:
				Log.d(TAG+this.getRealName(), "Error no suit 4!");
			}	
		Log.d(TAG+this.getRealName(), "count="+count);
		
		return new Card(3, 0, game);
	}
	
	/**
	 * Recursive method to get the highest card of a suit OR roll-over to the next suit.
	 * @param suit we want to get
	 * @param count How many times playHigh is called; only used if called in error and there are no cards left.
	 * When count>6 returns a new card with a red back. used to get out of a endless loop.
	 * @return Card to play
	 */
	public Card playHigh(int suit, int count){
		Log.d(TAG+this.getRealName(), "Play High");
		Card nextCard = null;
		if(count>6){
			Log.d(TAG+this.getRealName(), "Out of cards!!!");
			return new Card(3, 0, game); //Red back card
		}
		switch(suit){
		case 0:
			if(clubs.getSize()==0){  //if suit is empty just recall this method with a higher suit.
				Log.d(TAG+this.getRealName(), "out of Clubs");
				return playHigh(++suit, ++count);
			}
			else{
				Log.d(TAG+this.getRealName(), "Playing Club");
				nextCard = clubs.getCard(clubs.getSize()-1);
				Log.d(TAG+this.getRealName(), nextCard.toString());
				return nextCard;
			}	
		case 1:
			if(diamonds.getSize()==0){
				Log.d(TAG+this.getRealName(), "out of Diamonds");
				return playHigh(++suit, ++count);
			}
			else{
				Log.d(TAG+this.getRealName(), "Playing Diamond");
				nextCard=diamonds.getCard(diamonds.getSize()-1);
				Log.d(TAG+this.getRealName(), nextCard.toString());
				return nextCard;
			}
		case 2:
			if(spades.getSize()==0){
				Log.d(TAG+this.getRealName(), "out of Spades");
				return playHigh(++suit, ++count);
			}else{
				Log.d(TAG+this.getRealName(), "Playing Spade");
				nextCard=spades.getCard(spades.getSize()-1);
				if(spades.getSize()>1 && nextCard.getValue()==12 && !game.trading){	//This should fix dropping queen on self
					Log.d(TAG+this.getRealName(), "play high, trying to playing queen");
					nextCard=spades.getCard(spades.getSize()-2);
					return nextCard;
				}
				spades.removeCardAtIndex(spades.getSize()-1);
				Log.d(TAG+this.getRealName(), nextCard.toString());
				return nextCard;
			}
		case 3: 
			if(hearts.getSize()==0){
				Log.d(TAG+this.getRealName(), "out of Hearts");
				return playHigh(0, ++count);
			}else if(game.heartsBroken||playingVoid||game.trading){
				Log.d(TAG+this.getRealName(), "Playing heart");
				nextCard=hearts.getCard(hearts.getSize()-1);
				Log.d(TAG+this.getRealName(), nextCard.toString());
				this.playingVoid=false;
				return nextCard;
			}
			else{
				Log.d(TAG+this.getRealName(), "hearts not broken!");
				playHigh(0, ++count);
			}
		}
		return nextCard;
	}

	/**
	 * Called after a hasQueen = true
	 * @return Queen of spades
	 * OR 
	 * if not in deck returns NULL
	 */
	public Card getQueen(){
	    Card card = null;
		for(Card c : spades.getDeck()){
			if(c.getValue()==12){
				card = c;
				
				Log.d(TAG+this.getRealName(), "PLAYING queen from deck for "+this.getRealName());
				hasQueen = false;
				break;
			}
		}
//		spades.removeCard(card);	//remove it from the suit deck
		if(card==null){
			Log.d(TAG+this.getRealName(), "Returning Null, no Queen in DECK!  Should have done a QueenCheck");
		}
		return card;
	}
	/**
	 * Get rid of bad cards but don't play hearts
	 * @param startSuit
	 * @return
	 */
	private Card playHighDontBreakHearts(){
		Log.d(TAG+this.getRealName(), "voidPlayHighHearts");
		switch(getLargestSuit()){
		case 0:
			return playHigh(0, 0);
		case 1:
			return playHigh(1, 0);
		case 2:
			return playHigh(2, 0);
		case 3:
			Log.d(TAG+this.getRealName(), "Trying to play hearts on void, Attempting next best... playLow(clubs).");
			return playLow(0, 0);
		default:
			Log.d(TAG+this.getRealName(), "out of loop, playing  clubs.");
			return playHigh(0, 0);


		}
		
		
		
	}
	/**
	 * Use this to start a round with largest suit deck
	 * Helps even hand.
	 * @param startSuit
	 * @return playLow(getLagerstSuit());
	 */
	private Card playLowDontBreakHearts(){
		Log.d(TAG+this.getRealName(), "voidPlayHighHearts");
		switch(getLargestSuit()){
		case 0:
			return playLow(0, 0);
		case 1:
			return playLow(1, 0);
		case 2:
			return playLow(2, 0);
		case 3:
			Log.d(TAG+this.getRealName(), "Trying to play hearts on void, Attempting next best... playLow(clubs).");
			return playLow(0, 0);
		default:
			Log.d(TAG+this.getRealName(), "out of loop, playing  clubs.");
			return playHigh(0, 0);

		}
	
	}
	
	private Card playLargestSuitAndPlay(){
		Log.d(TAG+this.getRealName(), "voidPlayHighHearts");
		switch(getLargestSuit()){
			case 0:
				return playHigh(0, 0);
			case 1:
				return playHigh(1, 0);
			case 2:
				return playHigh(2, 0);
			case 3:
				return playHigh(3, 0);
			default:
				Log.d(TAG+this.getRealName(), "out of loop");
				return playHigh(0, 0);
	
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
	private synchronized int getLargestSuit(){
		Log.d(TAG+this.getRealName(), "getLargestSuit");
		int c = this.clubs.getSize();
		int d = this.diamonds.getSize();
		int s = this.spades.getSize();
		int h = this.hearts.getSize();
		int[] test = {c, d, s, h};
		int position = 0;
		int high = 0;
		for(int i=0; i<test.length;i++){
			if(test[i]>high){
				position = i;
				high = test[i];
			}

		}
		Log.d(TAG+this.getRealName(), "Largest suit position is ="+position);
		return position;
			
	}
	//TODO dont play last card of a suit
	//TODO Play queen if there is a higher club.
	/**
	 * Get the card that is just below the card sent for a value.
	 * Use to keep low cards longer.
	 * @param c Card to be lower than
	 * @param lastPlayer if we are the last player of the trick, if true and we have no cards lower just play highest card.
	 */
	private Card getDeckCardBelow(Card c, boolean lastPlayer){
		Log.d(TAG+this.getRealName(), "Checking for a card below "+c.toString());
		int top = c.getValue();
		Card bestPick = new Card(0, -1, game);
		switch (c.getSuit()){
		case 0:
			for(Card card: clubs.getDeck()){
				if(card.getValue()<top){
					if(card.getValue()>bestPick.getValue()){
						bestPick=card;
					}
				}
			}
			break;
		case 1:
			for(Card card: diamonds.getDeck()){
				if(card.getValue()<top){
					if(card.getValue()>bestPick.getValue()){
						bestPick=card;
					}
				}
			}
			break;
		case 2:
			for(Card card: spades.getDeck()){
				if(card.getValue()<top){
					if(card.getValue()>bestPick.getValue()){
						bestPick=card;
					}
				}
			}
			break;
		case 3:
			for(Card card: hearts.getDeck()){
				if(card.getValue()<top){
					if(card.getValue()>bestPick.getValue()){
						bestPick=card;
					}
				}
			}
			break;
			
		}
		if(bestPick.getSuit()==-1)
			if(lastPlayer){
				Log.d(TAG+this.getRealName(), "No card found, Last Card in trick sooo, play high and save lower cards for later");
				return playHigh(c.getSuit(), 0);
			}
			else{
				Log.d(TAG+this.getRealName(), "No card found, Playing next best");
				return playLow(c.getSuit(), 0);
			}
		return bestPick;
		
	}
	
	
	
	/////////////////////////ODD  METHODS/////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * Checks to see if there is a higher card is in the pile of cards.
	 * The first suit MUST be the same as the card to check.
	 * @param cards
	 * @param cardToCheck
	 * @return
	 */
	public boolean checkForCardsHigher(Deck cards, int valueToCheck){
		Log.d(TAG+this.getRealName(), "Checking for higher cards than " + valueToCheck + " for "+this.getRealName());
		int suitToCheck = cards.getCard(0).getSuit();
		for(Card c :cards.getArrayListDeck()){
			if(c.getSuit()==suitToCheck){
				if(c.getValue()>valueToCheck){
					return true;
				}
			}
		}
		return false;
	}
	
	public Card getLargestCardInTrick(Deck cards){
		Card highestCard = cards.getCard(0);
		int startValue= highestCard.getValue();
		int startSuit = highestCard.getSuit();
		
		for(Card c :cards.getArrayListDeck()){
			if(c.getSuit()==startSuit){
				if(c.getValue()>startValue){
					startValue = c.getValue();
					highestCard = c;
				}
			}
		}
		return highestCard;
	
	}
	
	
	/**
	 * Called from onCreate of Player OR when getting a new Deck
	 * ASSUMES a new deck was just set to this.deck! 
	 * assigns all cards to the player
	 * then sorts them into suits
	 * and updates the DeckHolder.
	 * Claims Cards for owner.
	 */
	public void sortHandFromDeck() {
		Log.d(TAG+this.getRealName(), "Sorting hand from deck");
		sortSuitsFromDeck();	
        updateDeckFromSuits();	//to rearrange deck nicely
	}
	
	/**
	 * Sets the clubs/diamonds/spades/hearts from this.deck
	 * Also sorts the suit decks.
	 */
	public void sortSuitsFromDeck(){
		OldDeck thand = new OldDeck();
		thand.addAllCards(deck);
		Log.d(TAG+this.getRealName(), "sorting hand for "+this.realName);
		Log.d(TAG+this.getRealName(), "deck size is "+thand.getSize());
		int hc = 0;  //high club
        int hd = 0;  //high diamond
        int hs = 0;  //high spade
        int hh = 0;  //high heart/
		int clubsCounter = 0;
		int diamondsCounter = 0;
		int spadesCounter = 0;
		int heartsCounter = 0;
		int cc2 = 0; //(clubs counter 2) keep track of counters when rearranging deck
		int dc2 = 0;
		int sc2 = 0;
		int hc2 = 0;
        OldDeck c = new OldDeck();  
        OldDeck d = new OldDeck();
        OldDeck s = new OldDeck();
        OldDeck h = new OldDeck();
        for(int i=0;i<thand.getSize();i++){
        	int ncard=thand.getCard(i).getValue();
        	switch(thand.getCard(i).getSuit()){
	        	case 0:	
	        		cc2=clubsCounter;
	        		if(hc>ncard){	        			
	        			while(clubsCounter>0&&ncard<c.getCard(clubsCounter-1).getValue()){
	        				clubsCounter--;
	        			}     					

	        		}
	        		c.addCardAtIndex(clubsCounter, thand.getCard(i));	 
	        		hc=c.getCard(c.getSize()-1).getValue(); 
	        		clubsCounter=cc2;
	        		clubsCounter++;

	        		break;
		    	case 1:

		    		dc2=diamondsCounter;
	        		if(hd>ncard){	   
	        			while(diamondsCounter>0&&ncard<d.getCard(diamondsCounter-1).getValue()){
	        				diamondsCounter--;
	        			}     					

	        		}        			  
	        		d.addCardAtIndex(diamondsCounter, thand.getCard(i));	
	        		hd=d.getCard(d.getSize()-1).getValue();
	        		diamondsCounter=dc2;
	        		diamondsCounter++;

		    		break;
		    	case 2:

	        		sc2=spadesCounter;
	        		if(hs>ncard){	        			
	        			while(spadesCounter>0&&ncard<s.getCard(spadesCounter-1).getValue()){
	        				spadesCounter--;
	        			}     					

	        		}			  
	        		s.addCardAtIndex(spadesCounter, thand.getCard(i));	
	        		hs=s.getCard(s.getSize()-1).getValue();
	        		spadesCounter=sc2;
	        		spadesCounter++;

		    		break;
		    	case 3:

		    		hc2=heartsCounter;
	        		if(hh>ncard){	   
	        			while(heartsCounter>0&&ncard<h.getCard(heartsCounter-1).getValue()){
	        				heartsCounter--;
	        			}     					

	        		} 			  
	        		h.addCardAtIndex(heartsCounter, thand.getCard(i));	
	        		hh=h.getCard(h.getSize()-1).getValue();
	        		heartsCounter=hc2;
	        		heartsCounter++;
		    		break;
        	}
        }
        setClubs(c);
        setDiamonds(d);
        setSpades(s);
        setHearts(h);
	}
	
	/**
	 * Clears deck and replaces it with a new Deck from the suit arrays.
	 */
	public void updateDeckFromSuits(){
		Log.d(TAG+this.getRealName(), "updateDeckfromSuits for "+this.realName);
		this.deck.clearALL();
        OldDeck newDeck = new  OldDeck();
        newDeck.addAllCards(this.clubs);
        newDeck.addAllCards(this.diamonds);
        newDeck.addAllCards(this.spades);
        newDeck.addAllCards(this.hearts);
        this.deck.addAllCards(newDeck);
		Log.d(TAG+this.getRealName(), "deck size= "+deck.getSize());

        
	}
	 
	/**
	 * Updates deck with new cards then calls sortSuitsFromDeck()
	 * @param OldDeck of cards
	 */
	public void updateDeckCards(OldDeck cards){
		Log.d(TAG+this.getRealName(), "updateDeckCards for "+this.realName);
		deck.clearALL();
		for(int i = 0; i<cards.getSize();i++){
			this.deck.addCard(cards.getCard(i));
		}
		sortHandFromDeck();
	}
	
	/**
	 * Fast update of Suit decks from this.deck
	 * This method is called after updateDeckCards(Deck)
	 *  
	 */
	public synchronized void updateSuitsFast(){
		Log.d(TAG+this.getRealName(), "updateSuits for "+this.realName);
		clubs.clearALL();
		diamonds.clearALL();
		spades.clearALL();
		hearts.clearALL();
		for(Card card : deck.getDeck()){
			switch(card.getSuit()){
			case Card.CLUBS:
				clubs.addCard(card);
				break;
			case Card.DIAMONDS:
				diamonds.addCard(card);
				break;
			case Card.SPADES:
				spades.addCard(card);
				break;
			case Card.HEARTS:
				hearts.addCard(card);
				break;
			}
		}

	}
	
	/**
	 * Use on a deck to see if it contains points
	 * Does not check for the jack of Diamonds.
	 * @param c
	 * @return true if has points
	 */
	public int checkForPoints(ArrayList<Card> c) {
		Log.d(TAG+this.getRealName(), "checkForPoints for "+this.realName);
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
		Log.d(TAG+this.getRealName(), "Points found= "+points);
		return points;
	}
	//////////////////////////////////////////Check for ******//////////////////////////////////////
	/**
	 * 
	 * @return true if player has Two of Clubs in deck
	 */
	public boolean checkForTwo(){
		Log.d(TAG+this.getRealName(), "checkForTwo for "+this.realName);
		Log.d(TAG+this.getRealName(), "Deck Size is="+this.deck.getSize());
		for (int i=0;i<3;i++){//Should only need to check the first two the rest is overkill.
			if(this.deck.getCard(i).getValue()==(2)&&this.deck.getCard(i).getSuit()==(0)){	
				twoOfClubs = this.deck.getCard(i);
				return true;				
			}
		}
		return false;
	}
	
	/**
	 * Call this to check size of suits and set boolean voids.
	 */
	public void checkForVoids(){ 	
		Log.d(TAG+this.getRealName(), "checkForVoids for "+this.realName);
		int empty =0;
		if(clubs.getSize()==0){
			empty++;
			this.voidClubs=true;
		}
		else
			this.voidClubs=false;

		if(spades.getSize()==0){
			empty++;
			this.voidSpades = true;
		}
		else
			this.voidSpades = false;

		if(diamonds.getSize()==0){
			empty++;
			this.voidDiamonds = true;
		}
		else
			this.voidDiamonds = false;

		if(hearts.getSize()==0){
			empty++;
			this.voidHearts = true;
		}
		else
			this.voidHearts = false;
		if(empty==4){
			Log.d(TAG+this.getRealName(), "The ship is SINKING!!, we are out of cards");
		}
		

	}
		
	/**
	 * Checks for Queen of Spades
	 * Sets hasQueen boolean.
	 */
	public void checkForQueen(){
		Log.d(TAG+this.getRealName(), "QueenCheck for "+this.realName);
		for(Card c: spades.getDeck()){
			if(c.getValue()==12){
				this.hasQueen = true;
			}
		}
		this.hasQueen = false;
	}
	
	/**Checks if void in said suit.....Should be redone.
	 * Does not Check size just checks BOOLEAN
	 * @param i the suit value.
	 * @return true if void in that suit.
	 */	
	public boolean checkVoid(int i){;
	switch(i){
		case 0:
			return voidClubs;
		case 1:
			return voidDiamonds;
		case 2:
			return voidSpades;
		case 3: 
			return voidHearts;
		}
		return false;
	}
	
	/////////////////////////////////////Trading Methods///////////////////////////////////////////////////////
	
	
	
	/**
	 * Grabs three cards from the biggest suits.  And queen if we have it.
	 * clears cards to trade to make sure extras dont get traded.
	 */
	public void getCardsToTrade(){
		this.cardsToTrade.clear();
		Log.d(TAG+this.getRealName()+"-Trading", "Finding Cards to trade for "+this.getRealName());
		ArrayList<Card> worstCards = new ArrayList<Card>();
		if(hasQueen){
			Log.d(TAG+this.getRealName()+"-Trading", "Trading the Queen");
			worstCards.add(getQueen());
		}
		else{
			Log.d(TAG+this.getRealName()+"-Trading", "no Queen");			
			worstCards.add(getTradingCard());

		}
		worstCards.add(getTradingCard());
		worstCards.add(getTradingCard());
		tradingCardsRemoved=true;
		this.cardsToTrade.addAll(worstCards);
	}
	
	/**
	 * get bad card.
	 * REMOVES CARDS FROM DECK
	 * @return
	 */
	private synchronized Card getTradingCard(){
		Log.d(TAG+this.getRealName(), "");
		Card c = null;
		switch(getLargestSuit()){
			case 0:
				c = playHigh(0, 0);
				this.clubs.removeCard(c);
				break;
			case 1:
				c = playHigh(1, 0);
				this.diamonds.removeCard(c);
				break;
			case 2:
				c = playHigh(2, 0);
				this.spades.removeCard(c);
				break;
			case 3:
				c = playHigh(3, 0);
				this.hearts.removeCard(c);
				break;
		}
		c.setTouched(false);
		this.deck.removeCard(c);
		Log.d(TAG+this.getRealName()+"-Trading", "Trading the Queen");

		Log.d(TAG+this.getRealName()+"-Trading", "CardPicked ===="+c.toString());
		return c;
				
	}
	
	/**
	 * Adds cards to this.deck
	 * @param cards to be Added
	 * Should update deck/suits after this
	 */
	public void addCardsToDeck(ArrayList<Card> cards){
		Log.d(TAG+this.getRealName(), "Cards to be added for "+this.getRealName());
		this.deck.addCards(cards);
	}
	
	/**
	 * Goes through each card setting Touched to false and removing it from the deck
	 * @param cards to be removed
	 * Should update deck/suits after this
	 */
	public void removeCardsFromDeck(ArrayList<Card> cards){
		Log.d(TAG+this.getRealName(), "Cards to be Removed for "+this.getRealName());
		this.deck.removeCards(cards);
	}
	
	
	/////////////////////////////////////////////////////////Simple SET GET CODE//////////////////
	
	/**
	 * Does no Checks just Plays low suit.
	 * Recursive method to get the lowest card of a suit OR roll-over to the next suit.
	 * @param suit we want to get
	 * @param count How many times playHigh is called; only used if called in error and there are no cards left.
	 * When count>6 returns a new card with a red back. used to get out of a endless loop.
	 * @return Card to play
	 */
	public Card playLowSimple(int suit, int count){
		Log.d(TAG+this.getRealName(), "Play LowSimple");
		Card nextCard = null;

		if(count>6){
			Log.d(TAG+this.getRealName(), "PlayLow is Out of cards!!!");
			return new Card(3, 0, game);	//Red back card
		}
		switch(suit){
			case 0:
				if(clubs.getSize()==0){
					Log.d(TAG+this.getRealName(), "out of Clubs");
					return playLow(++suit, ++count);
				}
				Log.d(TAG+this.getRealName(), "Playing Club");
				nextCard = this.clubs.getCard(0);
				return nextCard;
			case 1:
				if(diamonds.getSize()==0){
					Log.d(TAG+this.getRealName(), "out of Diamonds");
	
					return playLow(++suit, ++count);
				}
				Log.d(TAG+this.getRealName(), "Playing Diamond");
				nextCard=diamonds.getCard(0);
				return nextCard;
			case 2:
				if(spades.getSize()==0){			
					Log.d(TAG+this.getRealName(), "out of Spades");
					return playLow(++suit, ++count);
				}
				Log.d(TAG+this.getRealName(), "Playing Spade");
				nextCard=spades.getCard(0);
				return nextCard;
	
			case 3: 
				if(hearts.getSize()==0){
					Log.d(TAG+this.getRealName(), "out of Hearts");
					return playLow(0, ++count);
				}
				Log.d(TAG+this.getRealName(), "Playing Hearts");
					nextCard=hearts.getCard(0);
					return nextCard;
			case 4:
				Log.d(TAG+this.getRealName(), "Error no suit 4!");
			}	
		Log.d(TAG+this.getRealName(), "count="+count);
		
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
		Log.d(TAG+this.getRealName(), "Play HighSimple");
		Card nextCard = null;
		if(count>6){
			Log.d(TAG+this.getRealName(), "Out of cards!!!");
			return new Card(3, 0, game); //Red back card
		}
		switch(suit){
		case 0:
			if(clubs.getSize()==0){  //if suit is empty just recall this method with a higher suit.
				Log.d(TAG+this.getRealName(), "out of Clubs");
				return playHigh(++suit, ++count);
			}
			else{
				Log.d(TAG+this.getRealName(), "Playing Club");
				nextCard = clubs.getCard(clubs.getSize()-1);
				return nextCard;
			}	
		case 1:
			if(diamonds.getSize()==0){
				Log.d(TAG+this.getRealName(), "out of Diamonds");
				return playHigh(++suit, ++count);
			}
			else{
				Log.d(TAG+this.getRealName(), "Playing Diamond");
				nextCard=diamonds.getCard(diamonds.getSize()-1);
				return nextCard;
			}
		case 2:
			if(spades.getSize()==0){
				Log.d(TAG+this.getRealName(), "out of Spades");
				return playHigh(++suit, ++count);
			}else{
				Log.d(TAG+this.getRealName(), "Playing Spade");
				nextCard=spades.getCard(spades.getSize()-1);
				return nextCard;
			}
		case 3: 
			if(hearts.getSize()==0){
				Log.d(TAG+this.getRealName(), "out of Hearts");
				return playHigh(0, ++count);
			}
			Log.d(TAG+this.getRealName(), "Playing heart");
			nextCard=hearts.getCard(hearts.getSize()-1);
			Log.d(TAG+this.getRealName(), nextCard.toString());
			return nextCard;
			
		case 4:
			Log.d(TAG+this.getRealName(), "Error should not be case 4");
		}
		return nextCard;
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
	public void setAISmarts(int s) {
		AISmarts=s;
	}
	public int getAISmarts() {
		return AISmarts;
	}
	public int getState() {
		return state;
	}
	public void setState(int c) {
		state = c;
	}
		
	public OldDeck getDeck(){
		return this.deck;
	}
	public void removeCardFromDeck(Card card){
		this.deck.removeCard(card);
	}
	
	public void addToScore(int score){
		Log.d(TAG+this.getRealName(), "Adding to score ="+score);
		this.score += score;
	}
	public void addToTotalScore(int i){
		totalScore+=i;
	}
	public int getScore(){
		return score;
	}

	/**Updates the players Deck with a new one.
	 * Then Sorts the Ha
	 * 
	 * @param deck1
	 */
	public void sethand(OldDeck deck1) {
		this.deck = deck1;
		sortHandFromDeck();
	}
	public int getPass() {
		return passTo;
	}
	public void setPass(int passTo) {
		this.passTo=passTo;
	}
    
	/**
	 * Clears the deck then sets it
	 * Out puts log of how many cards were in the new deck
	 * @param c Deck to be added
	 */
	public void setClubs(OldDeck c){
		this.clubs.clearALL();
		this.clubs.addAllCards(c);
		Log.d(TAG+this.getRealName(), "clubs.size="+clubs.getSize());
	}
	/**
	 * Clears the deck then sets it
	 * Out puts log of how many cards were in the new deck
	 * @param d Deck to be added
	 */
	public void setDiamonds(OldDeck d){
		this.diamonds.clearALL();
		this.diamonds.addAllCards(d);
		Log.d(TAG+this.getRealName(), "Diamonds.size="+diamonds.getSize());
	}
	/**
	 * Clears the deck then sets it
	 * Out puts log of how many cards were in the new deck
	 * @param s Deck to be added
	 */
	public void setSpades(OldDeck s){
		this.spades.clearALL();
		this.spades.addAllCards(s);
		Log.d(TAG+this.getRealName(), "Spades.size="+spades.getSize());
	}
	/**
	 * Clears the deck then sets it
	 * Out puts log of how many cards were in the new deck
	 * @param h Deck to be added
	 */
	public void setHearts(OldDeck h){
		this.hearts.clearALL();
		this.hearts.addAllCards(h);
		Log.d(TAG+this.getRealName(), "Hearts.size="+hearts.getSize());
	}
	
	public OldDeck getClubs(){
		return clubs;
	}
	public OldDeck getDiamonds(){
		return diamonds;
	}
	public OldDeck getSpades(){
		return spades;
	}
	public OldDeck getHearts(){
		return hearts;
	}
	
}
