package game.shad.tempus.hearts;

import java.util.ArrayList;
import java.util.Collections;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
//TODO jack check to not give away so easy.		Also Not give away high diamonds on trade.
@SuppressLint("ParserError")
public class Player {
	public static final String TAG = "Hearts--Player--";
	private MainActivity main;
	private Game game;
	ArrayList<Card> hand;
    public ArrayList<Card> cardsToTrade=new ArrayList<Card>();

//	private PlayerDeck playerDeck;
	private Deck playerDeck;
	private ArrayList<Trick> takenTricks;
	public int seat=0; //position
	public String realName = "";
	public String shortName = "";
	public int colorInt = 0;
	public int AISmarts = 1;	//1 dumb, 2 normal, 3 smart, 11 dog
	public int state = 0;   //using 1-4
	public int score = 0;
	public int highScore=0;
	public int passTo = 0;
	
	boolean winner = false;
	boolean voidClubs = true;
	boolean voidDiamonds = true;
	boolean voidHearts = true;
	boolean voidSpades = true;
	boolean hasQueen=false;
	boolean playingVoid=false;
	boolean tradingCardsRemoved=false;
	
	public boolean sneakPeak = false;
	
	private int IQ;	//Then just a simple slider to change it and easy greater than if statments to do bot AI.
	//This will let the IQ be changed in game. just Pust a slider around the AI portrait.
	public LayoutInflater factory;

	public View textEntryView;
	private Context context;
	
	public Player(Context context, MainActivity main, Game game, ArrayList<Card> hand1, int AISmarts, int seat, String name, int color){
		this.context =context;
		this.main =main;
		this.game = game;
//		this.playerDeck = new PlayerDeck(game, this);	//may be obsolete
		this.playerDeck = new Deck();
		this.playerDeck.addNewCards(hand1);
		this.AISmarts = AISmarts;
		this.seat = seat;
		setShortName();
		this.realName = name;
		this.colorInt = color;
		takenTricks=new ArrayList<Trick>();		
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
			if(game.heartsBroken)
				return playerDeck.playLowSimple(playerDeck.getLowestCPVSuit());//lowest CPV 2013-02-10
			else
				return playLowDontBreakHearts();
		}
		int startSuit=trick.getCard(0).getSuit();
		Log.d(TAG+this.getRealName(), "Start suit is "+startSuit);
		if(checkVoid(startSuit)){//This is the void CODE
			Log.d(TAG+this.getRealName(), "Void!");
			if(hasQueen){
				Log.d(TAG+this.getRealName(), "hasQueen = true");
				if(game.round!=1){	//Don't play on first round.
					hasQueen=false;
					return playerDeck.getQueenOfSpades();
				}
			}
			else{
				Log.d(TAG+this.getRealName(), "hasQueen = false");
			}
			Log.d(TAG+this.getRealName(), "void--PlayHigh(getLargestSuit)");
			if(game.round==1){
				//TODO fix chance to play queen on first round if play high spades
				return playHighDontBreakHearts(); //Play a high card but no points!
			}
			return playerDeck.playHighSimple(playerDeck.getLargestSuit());
		}
		else{
			Log.d(TAG+this.getRealName(), "Not Void!");
		}
		boolean trickHasPoints = trick.hasPoints();
		boolean trickNegativePoints = trick.hasNegativePoints();
		switch (state){  //Set at the start of each round. Basically says where in the rotation does this player sit.
		//No case 1: that is for picking start card.  Taken care of above.
		case 2:
			Log.d(TAG+this.getRealName(), "Seat 2!");

			switch (round){
			case 1: 
				return playerDeck.playHighSimple(0);
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				Log.d(TAG+this.getRealName(), "round 2-7 --PlayLow("+startSuit+")");
				return playerDeck.playLowSimple(startSuit);
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:			
			case 13:

				Log.d(TAG+this.getRealName(), "round 8-13 --PlayLow("+startSuit+")");
				return playerDeck.playLowSimple(startSuit);
			}
		case 3:
			Log.d(TAG+this.getRealName(), "Seat 3!");
			switch (round){
			case 1: 
				return playerDeck.playHighSimple(0);
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
					return playerDeck.playHighSimple(startSuit);
//					return playerDeck.getDeckCardBelow(trick.getHighCard(), false, bestPick);
				}
				if(trickNegativePoints){
					Log.d(TAG+this.getRealName(), "Jack in trick, play high.");
					return playerDeck.playHighSimple(startSuit);
				}
				else {
					return playerDeck.playHighSimple(startSuit);
//					Log.d(TAG+this.getRealName(), "Checking for a card below "+bestPick.name);
//					return playerDeck.getDeckCardBelow(trick.getHighCard(), false, bestPick);
				}
			}
		case 4:
			if(round==1){
				return playerDeck.playHighSimple(0);
			}
			Log.d(TAG+this.getRealName(), "Seat 4!");
			if(trickHasPoints){
				Log.d(TAG+this.getRealName(), "Play below highest in pile --POINTS in pile!!");
				return playerDeck.playHighSimple(startSuit);
//				Log.d(TAG+this.getRealName(), "Checking for a card below "+trick.getHighCard());
//				return playerDeck.getDeckCardBelow(trick.getHighCard(), true, bestPick);			
			}
			if(trickNegativePoints){
				Log.d(TAG+this.getRealName(), "Jack in trick, play high.");
				return playerDeck.playHighSimple(startSuit);
			}
			else if(startSuit==2 && hasQueen){
				Log.d(TAG+this.getRealName(), "Have the queen and it is spades, check for higher Spades");
				if(checkForCardsHigher(trick.TrickToDeck(), 12)){
					return playerDeck.getQueenOfSpades();
				}
			}
			Log.d(TAG+this.getRealName(), "No points take it with highest card in suit.");
			return playerDeck.playHighSimple(startSuit);
			
		}
		Log.d(TAG+this.getRealName(), "++++++Out of all Loops !!SHOULD NOT HAPPEN!!--PlayHigh("+startSuit+")");
		Log.d(TAG+this.getRealName(), "++++++retuning 3 0 to not crash CODE!");

		Toast.makeText(main, "OUT OF CARDS!!", Toast.LENGTH_SHORT).show();
		Card oddBall = new Card(3, 0, game, context);
//		oddBall.setOwner(this);
		return oddBall;
		
	}
	
	public Card goNormal(int round, Trick trick){
		Log.d(TAG+this.getRealName(), "Normal player.go ="+this.realName);		
		if(trick.getSize()==0){	
			Log.d(TAG+this.getRealName(), "First Card of Pile");
			//TODO add more AI smarts here
			if(game.heartsBroken)
				return playerDeck.playLowSimple(playerDeck.getLowestCPVSuit());//CPV 2013-02-10
			else
				return playLowDontBreakHearts();
//			return playerDeck.getWorstCPVCard(playerDeck.getWorstCPVSuit());
		}
		
		int startSuit=trick.getCard(0).getSuit();
		Log.d(TAG+this.getRealName(), "Start suit is "+startSuit);
		if(checkVoid(startSuit)){//This is the void CODE
			if(hasQueen){
				Log.d(TAG+this.getRealName(), "Queencheck = true");
				if(game.round!=1){
					hasQueen=false;
					return playerDeck.getQueenOfSpades();
				}
			}
			else{
				Log.d(TAG+this.getRealName(), "hasQueen = false");
				checkForQueen();
				if(hasQueen){//This SHOULD  be redundant.
					Log.d(TAG+this.getRealName(), "Double  checkForQueen = true");
					return playerDeck.getQueenOfSpades();
				}
			}
			Log.d(TAG+this.getRealName(), "void--PlayHigh(getLargestSuit)");
			if(game.round==1){
				return playHighDontBreakHearts(); //Play a high card but no points!
			}
			return playerDeck.playHighSimple(playerDeck.getLargestSuit());
			//TODO better code for voids
		}
		boolean trickHasPoints = trick.hasPoints();
		boolean trickHasNegativePoints = trick.hasNegativePoints();
		switch (state){  //Set at the start of each round. Basically says where in the rotation does this player sit.
		//No case 1: that is for picking start card.  Taken care of above.
		case 2:
			Log.d(TAG+this.getRealName(), "Seat 2!");

			switch (round){
			case 1: 
				return playerDeck.playHighSimple(0);
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				Log.d(TAG+this.getRealName(), "round 0-7 --PlayLow("+startSuit+")");
				return playerDeck.playLowSimple(startSuit);
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:			
			case 13:

				Log.d(TAG+this.getRealName(), "round 8-12 --PlayLow("+startSuit+")");
				return playerDeck.playLowSimple(startSuit);
			}
		case 3:
			Log.d(TAG+this.getRealName(), "Seat 3!");
			switch (round){
			case 1: 
				return playerDeck.playHighSimple(0);
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
					return playerDeck.playLowSimple(startSuit);	
				}
				if(trickHasNegativePoints){
					Log.d(TAG+this.getRealName(), "Jack in trick, play high.");
					return playerDeck.playHighSimple(startSuit);
				}
				else {					
					Card bestPick = playerDeck.playHighSimple(startSuit);
					Log.d(TAG+this.getRealName(), "Checking for a card below "+bestPick.name);
					return playerDeck.playHighSimple(startSuit);
				}
			}
		case 4:
			if(round==1){
				return playerDeck.playHighSimple(0);
			}
			Log.d(TAG+this.getRealName(), "Seat 4!");
			if(trickHasPoints){
				Log.d(TAG+this.getRealName(), "Play low/CardBelow--POINTS in pile!!");
				return playerDeck.playLowSimple(startSuit);		
			}
			if(trickHasNegativePoints){
				Log.d(TAG+this.getRealName(), "Jack in trick, play high.");
				return playerDeck.playHighSimple(startSuit);
			}
			else if(startSuit==2 && hasQueen){
				if(checkForCardsHigher(trick.TrickToDeck(), 12)){//TODO build method into Trick class
					return playerDeck.getQueenOfSpades();
				}
				else if(playerDeck.playLowSimple(startSuit).getValue()==12 && playerDeck.totalCardsFromSuit(startSuit)>1){//Dont play the queen on yourself.
					return playerDeck.playHighSimple(startSuit);
				}
				return playerDeck.playLowSimple(startSuit);
			}
			//take high
			return playerDeck.playHighSimple(startSuit);
		}
		
		Log.d(TAG+this.getRealName(), "++++++Out of all Loops !!SHOULD NOT HAPPEN!!--PlayHigh("+startSuit+")");
		Log.d(TAG+this.getRealName(), "++++++retuning 3 0 to not crash CODE!");

		Toast.makeText(main, "OUT OF CARDS!!", Toast.LENGTH_SHORT).show();
		Card oddBall = new Card(3, 0, game, context);
//		oddBall.setOwner(this);
		return oddBall;
	}
	
	public void goSmart(){
		int round = game.round;
		Trick trick = game.tableTrick;
		
	}
	

	
	/**
	 * Get rid of bad cards but don't play hearts
	 * @param startSuit
	 * @return
	 */
	private Card playHighDontBreakHearts(){
		Log.d(TAG+this.getRealName(), "PlayHigh Dont Break Hearts");
		switch(playerDeck.getLargestSuit()){
		case 0:
			return playerDeck.playHighSimple(0);
		case 1:
			return playerDeck.playHighSimple(1);
		case 2:
			return playerDeck.playHighSimple(2);
		case 3:
			Log.d(TAG+this.getRealName(), "Trying to play hearts, Attempting next best... playLow(clubs).");
			return playerDeck.playHighSimple(0);
		default:
			Log.d(TAG+this.getRealName(), "out of loop, playing  clubs.");
			return playerDeck.playHighSimple(0);
		}	
	}
	
	/**
	 * Use this to start a round with largest suit deck
	 * Helps even hand.
	 * @param startSuit
	 * @return playLow(getLagerstSuit());
	 */
	private Card playLowDontBreakHearts(){
		Log.d(TAG+this.getRealName(), "PlayLow Don't Break Hearts");
		switch(playerDeck.getHeighestCPVSuit()){//Changed to CPV from largest 2013-02-10
		case 0:
			return playerDeck.playLowSimple(Game.CLUBS);
		case 1:
			return playerDeck.playLowSimple(Game.DIAMONDS);
		case 2:
			return playerDeck.playLowSimple(Game.SPADES);
		case 3:
			Log.d(TAG+this.getRealName(), "Trying to play hearts on void, Attempting next best... playLow(clubs).");
			return playerDeck.playLowSimple(Game.HEARTS);
		default:
			Log.d(TAG+this.getRealName(), "out of loop, playing  clubs.");
			return playerDeck.playLowSimple(Game.CLUBS);
		}
	}
	
	private Card playLargestSuit(){
		Log.d(TAG+this.getRealName(), "Play largest suit");
		switch(playerDeck.getLargestSuit()){
			case 0:
				return playerDeck.playHighSimple(Game.CLUBS);
			case 1:
				return playerDeck.playHighSimple(Game.DIAMONDS);
			case 2:
				return playerDeck.playHighSimple(Game.SPADES);
			case 3:
				if(game.heartsBroken){
					return playerDeck.playHighSimple(Game.HEARTS);
				}
				else
				{
					Log.d(TAG+this.getRealName(), "Trying to play hearts, Game.heartsBroken is false... playLow(clubs).");
					return playerDeck.playHighSimple(Game.CLUBS);
				}
			default:
				Log.d(TAG+this.getRealName(), "out of loop");
				return playerDeck.playHighSimple(Game.CLUBS);
	
		}
	}
			
	
	
	//TODO dont play last card of a suit
	//TODO Play queen if there is a higher club.
	/**
	 * Get the card that is just below the card sent for a value.
	 * Use to keep low cards longer.
	 * @param c Card to be lower than
	 * @param lastPlayer if we are the last player of the trick, if true and we have no cards lower just play highest card.
	 */

//		return deck.getDeckCardBelow(c, lastPlayer, bestPick);
			
	/////////////////////////ODD  METHODS/////////////////////////////////////////////////////////////////
			
	/**
	 * Checks to see if there is a higher card is in the pile of cards.
	 * The first suit MUST be the same as the card to check.
	 * Good to check if an Ace or King of spades has been played thus the queen can be played 
	 * Or to take the Jack check for higher Diamonds
	 * @param deck
	 * @param cardToCheck
	 * @return
	 */
	public boolean checkForCardsHigher(Deck deck, int valueToCheck){
		Log.d(TAG+this.getRealName(), "Checking for higher cards than " + valueToCheck + " for "+this.getRealName());
		int suitToCheck = deck.getCard(0).getSuit();
		for(Card c :deck.getArrayListDeck()){
			if(c.getSuit()==suitToCheck){
				if(c.getValue()>valueToCheck){
					Log.d(TAG+this.getRealName(), "Found "+c.name);
					return true;
				}
			}
		}
		return false;
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
		Log.d(TAG+this.getRealName(), "Deck Size is="+this.playerDeck.getSize());
		if(playerDeck.checkForTwo()){
			return true;				
		}
		return false;
	}
	
	public Card getTwoOfClubs(){
		return playerDeck.getTwoOfClubs();
	}
	
			
	/**
	 * Checks for Queen of Spades
	 * Sets hasQueen boolean.
	 */
	public void checkForQueen(){
		this.hasQueen = playerDeck.checkForQueen();
		Log.d(TAG, "checkForQueen = "+hasQueen);
	}
	/**
	 * checks all the suits for void and sets Suit Booleans
	 */
	public void checkForVoids(){ 	
		
		
		
		Log.d(TAG+this.getRealName(), "checkForVoids for "+this.realName);
		Log.d(TAG+this.getRealName(), "playerDeck= "+playerDeck.deck.toString());
		voidClubs = playerDeck.checkVoid(0);
		voidDiamonds = playerDeck.checkVoid(1);
		voidSpades= playerDeck.checkVoid(2);
		voidHearts = playerDeck.checkVoid(3);
		if(voidClubs&&voidDiamonds&&voidSpades&&voidHearts){
			Log.d(TAG+this.getRealName(), "!!ERROR!! No Cards left for "+this.realName);

		}
	}
	
	/**Checks if void in said suit
	 * @param suit the suit value.
	 * @return true if void in that suit.
	 */	
	public boolean checkVoid(int suit){;
		return playerDeck.checkVoid(suit);
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
			worstCards.add(playerDeck.getQueenOfSpades());
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
		Log.d(TAG+this.getRealName(), "Finding a Trading card.");
		Card c = null;
		switch(playerDeck.getHeighestCPVSuit()){
			case 0:
				c = playerDeck.playHighSimple(Game.CLUBS);
				break;
			case 1:
				//Diamonds-- Low are good
				c = playerDeck.playLowSimple(Game.DIAMONDS);
				break;
			case 2:
				c = playerDeck.playHighSimple(Game.SPADES);
				break;
			case 3:
				c = playerDeck.playHighSimple(Game.HEARTS);
				break;
		}
		if(c == null){
			Log.d(TAG+this.getRealName()+"-getTradingCard", "null, attempting to get card at 0");
			c=playerDeck.getCard(0);			
		}
		c.setTouched(false);
		playerDeck.removeCard(c);
		Log.d(TAG+this.getRealName()+"-getTradingCard", "CardPicked ===="+c.name);
		return c;
				
	}
	
	public Card getFirstCard(){
		Log.d(TAG, "Get first Card deck.size="+playerDeck.getSize());
		return playerDeck.removeCardAtIndex(0);
		
	}
	
	/**
	 * Adds cards to this.deck
	 * @param cards to be Added
	 * Should update deck/suits after this
	 */
	public void addCardsToDeck(ArrayList<Card> cards){
		Log.d(TAG+this.getRealName(), "Cards to be added for "+this.getRealName());
		this.playerDeck.deck.addAll(cards);
	}
	
	/**
	 * Goes through each card setting Touched to false and removing it from the deck
	 * @param cards to be removed
	 * Should update deck/suits after this
	 */
	public void removeCardsFromDeck(ArrayList<Card> cards){
		Log.d(TAG+this.getRealName(), "Cards to be Removed for "+this.getRealName());
		this.playerDeck.removeCards(cards);
	}
		
	/////////////////////////////////////////////////////////Simple SET GET CODE//////////////////
	
	public void addTakenTrick(Trick tt){
		takenTricks.add(tt);
	}
	
	public ArrayList<Trick> returnTakenTricks(){
		return takenTricks;
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
	/**
	 * Clears the deck and sets a new one.
	 * @param deck
	 */
	public void setDeck(Deck deck){
		playerDeck.clear();
		playerDeck.deck.addAll(deck.deck);
	}
	public Deck getDeck(){
		return playerDeck;
	}
	
	/**
	 * Clears the deck and sets a new one.
	 * @param deck
	 */
	public void setArrayListDeck(ArrayList<Card> deck){
		playerDeck.clear();
		playerDeck.deck.addAll(deck);
	}

	public void removeCardFromDeck(Card card){
		this.playerDeck.removeCard(card);
	}
	
	public void addToScore(int score){
		Log.d(TAG+this.getRealName(), "Adding to score ="+score);
		this.score += score;
	}
	public void addToHighScore(int points){
		highScore+=points;
	}
	public int getScore(){
		return score;
	}

	public int getPass() {
		return passTo;
	}
	public void setPass(int passTo) {
		this.passTo=passTo;
	}
	public ArrayList<Card> getArrayListDeck() {
		return playerDeck.deck;
	}
	
	public void sortPlayerDeck(){
		playerDeck.sortCards();
	}
    
		
}
