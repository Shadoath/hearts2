package game.shad.tempus.hearts;

import game.shad.tempus.hearts.GameThread.AutoRunState;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

	//TODO swap button to take someone elses place.  Only once a round.
public class Game extends Activity {
	public static final String TAG = "Hearts--Game";
	public static final int pointsTillEndGame = 50;
    
	private Context context;
    private File path;

	//current issues
	private MainActivity main;
	public String eol = System.getProperty("line.separator");
	public String name;
	
	private Deck cardDeck=new Deck();
	public Trick tableTrick=new Trick();
	
    public Player p1;
	public Player p2;
	public Player p3;
	public Player p4;
	public Player curPlayer;
	
	public Card cardToPlay;
	public Canvas canvas = new Canvas();
	private ArrayList<TrickStats> roundWinnerAndPoints= new ArrayList<TrickStats>(); 
	public String roundCardString = "";

	
    //Booleans for setting game states
    public boolean playing 			= false;  //initialized to false but set true during check for 2
    public boolean heartsBroken 	= false;
    public boolean restart 			= false;
    public boolean gameOver 		= false; 
    public boolean cardCounterB		= false;
    public boolean newRound			= false;
    public boolean trading 			= false; 
	public boolean justPickedUpPile = false;
	public boolean jackFound 		= false;	//TODO use this 
	public boolean queenFound 		= false;	//TODO use this 

	private boolean initialized 	= false; //made true on surfaceCreated()
	private boolean jackFoundP1 	= false;
	private boolean jackFoundP2 	= false;
	private boolean jackFoundP3 	= false;
	private boolean jackFoundP4 	= false;
	private boolean portaitMode 	= false;
	
	public int playerHelperInt=0;
	public int playerHelperIntTotal=0;
    public int round=1;
	public int selectedCard=0;
	public int selectedCardSuit=-1;
    public int roundScore = 0;

	
	private int clubsPlayedInt=0;
	private int diamondsPlayedInt=0;
	private int spadesPlayedInt=0;
	private int heartsPlayedInt=0;
    private int roundScoreP1 = 0;
    private int roundScoreP2 = 0;
    private int roundScoreP3 = 0;
    private int roundScoreP4 = 0;
    private int session=1;
    private int gameHands=1;
    private int difficulty=1;
    private int shuffleType;
	private int screenWidth;
    private int screenHeight;

	
	private Canvas tableHolderCanvas = null;
	private Canvas p1HolderCanvas = null;
	private Canvas p2HolderCanvas = null;
	private Canvas p3HolderCanvas = null;
	private Canvas p4HolderCanvas = null;

    private LinearLayout bottomLayout;
    private HorizontalScrollView deckHolderLayout;
    private LinearLayout tableHolderLayout;
    private LinearLayout topLayout;
    private LinearLayout middleLayout;
    private LinearLayout tableRightView;
    private LinearLayout bottomTVlayout;

    
    private Button playCard;
    
    public DeckHolder deckHolder;
    public SlidingDeckHolder slidingDeckHolder;
    private TableHolder tableHolder;
    
    private PlayerHolder p1Holder;
    private PlayerHolder p2Holder;
    private PlayerHolder p3Holder;
    private PlayerHolder p4Holder;
    
    private LinearLayout.LayoutParams tableHolderlayoutParams;
    private LinearLayout.LayoutParams deckHolderlayoutParams;
    private LinearLayout.LayoutParams slidingDeckHolderlayoutParams;
    private TextView clubsPlayed;
    private TextView diamondsPlayed;
    private TextView spadesPlayed;
    private TextView heartsPlayed;
    private TextView roundView;
    private TextView bottomText;
    private TextView bottomText2;
    
    private RectF rect = new RectF(); // public rect to be passed to drawable objects for drawing bitmaps
    private Paint paint = new Paint(); // public paint to be passed to drawable objects
	private Toast myToast;
   
		
	
    public Game(Bundle gameBundle, Context context, MainActivity main) {
        Bundle b = gameBundle;
        this.context = context;
        this.main = main;
        this.name=(String) b.get("name").toString().trim();
        this.cardCounterB = (Boolean) b.get("cardCounter");
        this.screenHeight = (int) b.getInt("height");
        this.screenWidth = (int) b.getInt("width");
        
        Log.d(TAG, "Text in player name box is "+this.name);
        if (this.name.equalsIgnoreCase("Your name")||this.name.equals("")){
        	this.name = "You";
        }
        this.difficulty =  (Integer) b.get("diff");
        this.shuffleType =  (Integer) b.get("shuffle");
        Log.d(TAG, "shuffle type= "+shuffleType);

        Log.d(TAG, "difficulty= "+difficulty);
        portaitMode = b.getBoolean("portaitMode", true);
        this.restart = (Boolean) b.get("restart");
//        createBlankHand();
        myToast  = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        setupSaveSettings();
        findViewsById();
        

	}
    
	private void setupSaveSettings(){
    	switch(2){
    	case 1:
        	path = main.getCacheDir();
        	break;
    	case 2:
        	path = main.getFilesDir();
        	break;
    	case 3:
        	path = main.getExternalCacheDir();
        	break;
        
    	}
    	Log.d(TAG, "settings Saved");
    }
	
	public void heartsMe(){ 
		//TODO create a loading screen event.
		Log.d(TAG, "start()");
		if(restart){		//Not sure on this line.
			makeDeck();
			this.restart = false;
		}
    	shuffle();	
        dealing();
        voidCheckAllPlayers();	//Run this before doing and player logic 
        queenCheckAllPlayers();	//Fist of two calls during a 13 trick game.
        if(session!=4){//Trading
        	playCard.setText("Trade Cards");
        	playCard.setEnabled(false);
        	trading=true;
	        bottomText.setText("Choose three cards to trade");
	        bottomText2.setText("Trading..."+eol);
        }
        else{	//NO trading  
        	playCard.setText("Play Card");
        	playCard.setEnabled(true);
        	checkForTwoMethod();	//This more or less starts the game.
        }
       
        update();
	}
		
	/**
	 * creates 52 cards 13 of each suit.
	 */
	public void makeDeck() { 
		Log.d(TAG, "make Deck");
		this.cardDeck = new Deck();

		for(int suit=0;suit<4;suit++){			
			for(int value=2;value<15;value++){
				Card cd = new Card(value, suit, this);
				cardDeck.addCard(cd);
			}
	
		}
		
	}
	
	/**
	 * WOULD NOT BET LIFE ON THIS SHUFFLE!!!
	 * Probably a very uneven shuffle.
	 */
	public void shuffle(){
		Log.d(TAG, "shuffle");
		Log.d(TAG, "Deck size="+this.cardDeck.getSize());
		
			if(shuffleType==1){
				Log.d(TAG, "shuffle type 1");
	
				//New shuffle going in 8/16
				int x = 0;
				int z = 50;
				
				Deck deck2 = new Deck();
				Deck deck3 = new Deck();
				Deck deck4 = new Deck();
				deck2.addAllCards(cardDeck);
				int j=0;
				int a=(int)  (Math.random()*7)+1;
				int dLength=0;;
				boolean stop = false;
				while(x<z&&!stop){
					x++;
					dLength=deck2.getSize();
					while(dLength>0){
						if(dLength<=a){
							Log.d(TAG, "STOP, too Small. "+dLength);
							deck3.addAllCards(deck2);
							deck2.clearALL();
							a=-1;	//must be negative or it will go into the while loop
							stop=true;
							//maybe just break and drop all cards.
						}		
						//print ("a="+a, 449);
						while(a>=0){
							deck3.addCard(deck2.getCard(a));
							deck2.removeCardAtIndex(a);
							a--;	//goes to -1, but thats ok
							j++;	//seemed like it adds an extra but a>=0 is why.
						}
						
						deck4.addAllCards(deck3);
						deck3.clearALL();
						
						a=(int) (Math.random()*7)+1;
						dLength=deck2.getSize();
						//Log.d(TAG, "deck2= "+dLength, 461);
						//Log.d(TAG, "deck3= "+deck3.getSize(), 462);
						//Log.d(TAG, "deck4= "+deck4.getSize(), 463);
					}
					//deck2.clearALL();  should be empty
					stop=false;
					deck2.addAllCards(deck4);  //dont use = it makes them clones of each other
					deck4.clearALL();
					Log.d(TAG, "x="+x+"  z="+z);
					
				}
				Log.d(TAG, "j is "+j);
				cardDeck.clearALL();
				cardDeck.addAllCards(deck2);
			}
			else if(shuffleType==2){	//this shuffle may not work after changing everything to Deck...
				Log.d(TAG, "shuffle type 2");
				int x=0;
				int z=50;//times to loop the deck and 'randomly' switch cards.
				int r = 51;
				boolean stop=false;
				Deck deck2 = new Deck();
		
				while(x<z){
					x++;
					int j=0;
					int a=(int) (Math.random()*r);
					for(int i=52; i>0&&!stop; i--){
						int loop=0;
						while(cardDeck.getCard(a)!=null&&!stop){
							loop++;
							a=(int) (Math.random()*r);
							if(loop>15&&!stop){ //careful on this Set loop to 15 from 10.
								r = 0;
								for(int q=0; q<cardDeck.getSize();q++){//Random math not finding cards, Empty rest and start again.
									if(cardDeck.getCard(q)!=null){
										deck2.addCardAtIndex(q, cardDeck.getCard(q));
										r++;
										j++;
									}
									
									
								}
								stop=true;
								
								
							}
						}
						if(!stop){
							deck2.addCardAtIndex(a, cardDeck.getCard(a));
							cardDeck.addCardAtIndex(a, null);			
							j++;
						}
					}
					
					cardDeck=deck2;
					
					
					
				}
			}
			else{
				//TODO finish.
				Log.d(TAG, "shuffle type 3");
				int x = 0;
				int z = 50;
				int total =0;
				ArrayList<Deck> decks= new ArrayList<Deck>();
				Deck deck2 = new Deck();
				deck2.addAllCards(cardDeck);
				while(x<z){
					decks=(splitDeck(deck2));
					Log.d(TAG, "the deck is "+decks.size());
					deck2.clearALL();
					deck2 = (mixOutIn(decks));
					decks.clear();
					x++;
				}	
			}
		}
	
	/**
	 * Takes a Deck and returns an array of decks, 11 of them 5 per array and 2 in the last one.
	 * @param a The Deck to be split
	 * @return	ArrayList<Deck>(size = 11)
	 */
	private ArrayList<Deck> splitDeck(Deck a) {
		ArrayList<Deck> decks = new ArrayList<Deck>();
		for(int i =0; i<10;i++){
			Deck deck2 = new Deck();
	
			int t=0;
			while(t<5){
				deck2.addCard(a.getCard(t));
				t++;
			}
			t--;
			while(t>=0){
				a.removeCardAtIndex(t);
				t--;
			}
			decks.add(deck2);
			Log.d(TAG, "deck size="+ deck2.getSize());

		}
		Log.d(TAG, "final cards size="+ a.getSize());
		decks.add(a);
		return decks;
	}
	
	
	/**
	 * Mix an ArrayList<Deck> into 
	 * @param a
	 * @return
	 */
	private Deck mixOutIn(ArrayList<Deck> a){
		int size = a.size();
		boolean extra=false;
		if(size%2==1){
			size--;
			extra=true;
		}
		Deck d= new Deck();
		for(int i =0;i<size/2;i++){
			d.addAllCards(mixDecks(a.get(i), a.get(size-i-1)));
		}
		d.addAllCards(a.get(size/2));
		
		return d;
	}
	
	public Deck mixDecks(Deck a, Deck b){
		for(int i=0; i<a.getSize();i++){
			b.addCardAtIndex(i+i, a.getCard(i));
		}
		return b;
	}
	
	
	
	/**
	* Create the and deals them out and then finds the two
	* NEEDS SHUFFLE.
	*/
	public void dealing() {
		Log.d(TAG, "dealing");
		Log.d(TAG, "Deck size="+this.cardDeck.getSize());
		Deck hand1 = new Deck();
		Deck hand2 = new Deck();
		Deck hand3 = new Deck();
		Deck hand4 = new Deck();
		for(int i=0;i<cardDeck.getSize();i+=4){
			int d1=i;
			int d2=i+1;
			int d3=i+2;
			int d4=i+3;
			hand1.addCard(cardDeck.getCard(d1));
			hand2.addCard(cardDeck.getCard(d2));
			hand3.addCard(cardDeck.getCard(d3));
			hand4.addCard(cardDeck.getCard(d4));
			
			
		}
		if(p1==null){//first Start 
			Log.d(TAG, "Creating new players and giving each person a hand.");
			int color1 = Color.parseColor("#FF7711");
			int color2 = Color.rgb(0, 50 , 200);
			p1 = new Player(main, this, hand1, 0, 1, name, Color.GREEN); 
			p2 = new Player(main, this, hand2, 0, 2, "(P2)", color1);
			p3 = new Player(main, this, hand3, 0, 3, "(P3)", color2);
			p4 = new Player(main, this, hand4, 0, 4, "(P4)", Color.RED);
			slidingDeckHolder.addDeck(hand1);
			
			createPlayerViews();
			updatePlayerInfo();
		}
		else {//else new round and New cards are dealt
			Log.d(TAG, "new round being delt");
			p1.sethand(hand1);
			p2.sethand(hand2);
			p3.sethand(hand3);
			p4.sethand(hand4);
			slidingDeckHolder.addDeck(p1.getDeck());
			logPlayerPoints();
		}			
		
		
	}
	
	/**
	 * Called when player has picked all his cards.
	 */
	public void tradeCards(){
		p2.getCardsToTrade();
		p3.getCardsToTrade();
		p4.getCardsToTrade();
		switch (session){
		case 1:	
			//Trade Left p4->p3->p2->p1
			p4.addCardsToDeck(p1.cardsToTrade);
			
			p3.addCardsToDeck(p4.cardsToTrade);
			
			p2.addCardsToDeck(p3.cardsToTrade);
			
			p1.addCardsToDeck(p2.cardsToTrade);
			updateBottomTextWithTradingCards(false, p2.cardsToTrade);
		break;
		case 2:
			//Trade right p1->p2->p3->p4
			p2.addCardsToDeck(p1.cardsToTrade);
			
			p1.addCardsToDeck(p4.cardsToTrade);
			updateBottomTextWithTradingCards(false, p4.cardsToTrade);
			
			p4.addCardsToDeck(p3.cardsToTrade);
			
			p3.addCardsToDeck(p2.cardsToTrade);
		break;
		case 3:
			//across p2 to p4
			p4.addCardsToDeck(p2.cardsToTrade);
			
			p2.addCardsToDeck(p4.cardsToTrade);
			
			//across p1 to p3
			p1.addCardsToDeck(p3.cardsToTrade);
			updateBottomTextWithTradingCards(false, p3.cardsToTrade);
			
			p3.addCardsToDeck(p1.cardsToTrade);
		break;
		case 4:
			Log.d(TAG, "Error there should not be trading now!");
		break;
		}
		if(!p1.tradingCardsRemoved){
			p1.removeCardsFromDeck(p1.cardsToTrade);
			p1.tradingCardsRemoved=false;
		}
		p1.cardsToTrade.clear();
		p2.cardsToTrade.clear();
		p3.cardsToTrade.clear();
		p4.cardsToTrade.clear();

		p1.sortHandFromDeck();
		p2.sortHandFromDeck();
		p3.sortHandFromDeck();
		p4.sortHandFromDeck();
        voidCheckAllPlayers();	//Run this before doing player logic 
		this.trading=false;
		slidingDeckHolder.addDeck(p1.getDeck());
		playCard.setEnabled(true);
		checkForTwoMethod();
		update();

	}
	
	/**
	 * Called from HeartsMe();
	 * Finds who has the 2 of clubs and forces them to play it
	 * Sets the states for the next round
	 * Then advances the curPlayer
	 * Checks all players for queen of Spades
	 */
	public void checkForTwoMethod(){
		Log.d(TAG, "checkForTwoMethod() called");
		if(p1.checkForTwo()){
			Log.d(TAG, "p1 played the 2 of clubs");
			curPlayer=p1;
			setState(1);
			playTwoOfClubs();
	
		}
		else if(p2.checkForTwo()){
			Log.d(TAG, "p2 plays 2 of clubs");
			curPlayer=p2;
			setState(2);
			playTwoOfClubs();
			}
		else if(p3.checkForTwo()){
			Log.d(TAG, "p3 plays 2 of clubs");
			curPlayer=p3;
			setState(3);
			playTwoOfClubs();
		}
		else if(p4.checkForTwo()){
			Log.d(TAG, "p4 plays 2 of clubs");
			curPlayer=p4;
			setState(4);
			playTwoOfClubs();
		}
		else{
			Log.d(TAG, "The game has already started.");
		}
		Log.d(TAG, " 2 check done--curPlayer="+curPlayer.getRealName());
        voidCheckAllPlayers();	//Run this before doing and player logic 
        queenCheckAllPlayers();	//This should be the only place this needs to be run.
		}
	      
	/**
	 * Sets the first round of the game going
	 * Plays two and then sets next CurPlayer
	 */
	public void playTwoOfClubs(){
		tableTrick.clearALL();
		clearTableCards();
		this.justPickedUpPile=false;
		Card nextCard = curPlayer.twoOfClubs;
		nextCard.setOwner(curPlayer);
		curPlayer.removeCardFromDeck(nextCard);
		curPlayer.updateSuitsFast();
		if(curPlayer==p1){
			slidingDeckHolder.addDeck(p1.getDeck());
		}
		Log.d(TAG, "Game started by "+curPlayer.getRealName());
		tableTrick.addCard(nextCard);
		tableHolder.addCard(nextCard);
		bottomText2.append("Round Started!"+eol+curPlayer.getRealName()+" had the "+ nextCard.name+eol);
		nextPlayer();	
		Log.d(TAG, "0101010--0101010--0101010--0101010--0101010");
	}
	
	/**
	 * Checks for voids in suits for all players.
	 * This gets called at the end of each trick and at the start of the game.
	 * Maybe work in a set void to true when the player plays the last card.
	 */
	public void voidCheckAllPlayers(){
	    p1.checkForVoids();
	    p2.checkForVoids();     
	    p3.checkForVoids();
	    p4.checkForVoids();
	}

	/**
	 * Checks for the Queen in all players hands.
	 * Sets  the boolean hasQueen=true/false for later round logic
	 */
	public void queenCheckAllPlayers(){
	    p1.checkForQueen();
	    p2.checkForQueen();     
	    p3.checkForQueen();
	    p4.checkForQueen();
	}	
	
	/**
	 * When player hits the Play/next Button or when game thread is continuing the Game
	 * Checks trading  // Only for round 1 but 
	 * If on auto run cards will be picked for you.
	 */
	public void GO(){
		Log.d(TAG,  "!!!GO!!!  round="+round);
			if(trading){//
				if(p1.cardsToTrade.size()!=3){
					p1.getCardsToTrade();
				}
				tradeCards();
				return;
			}
			if(curPlayer!=null){
				if(justPickedUpPile){//
					tableTrick.clearALL();
					clearTableCards();
					this.justPickedUpPile=false;
				}
				if(curPlayer!=p1||cardToPlay==null){// If null then it is a AUTO play.  This is also GO for BOTS.
					Log.d(TAG, "normal GO()");
					Card nextCard=(curPlayer.go(round, tableTrick));
					Log.d(TAG, "###"+curPlayer.realName);
					Log.d(TAG, "###"+curPlayer.realName+" choose the "+nextCard.name+". Pile size="+tableTrick.getSize() );
					playCard(nextCard);
				}
				else {
					Log.d(TAG, "USER SELECTED CARD PLAY ----CardToPlay GO()");
					if(checkPlayability(cardToPlay)){
						cardToPlay.setTouched(false);
						Log.d(TAG, "###"+curPlayer.realName+" played a "+cardToPlay.name+". Pile size="+tableTrick.getSize() );
						playCard(cardToPlay);
						cardToPlay=null;
					}
					else{
						Log.d(TAG, "Trying to cheat!!");
						if(cardToPlay!=null){
							cardToPlay.setTouched(false);
							cardToPlay=null;
						}
					}
				}
			}
			else
				Log.d(TAG, "Game.GO failed, Curplayer was null");
	}
	
	/**
	 * Plays the selected card and advances the curPlayer
	 * @param p seat of person who is playing a card
	 * @param rs the string to be set to the layout.
	 */
	public void playCard(Card nextCard){
		nextCard.setOwner(curPlayer);
		tableTrick.addCard(nextCard);

		Log.d(TAG, "Playing card="+nextCard.name);
		tableHolder.addCard(nextCard);		
		playCardBottomText(nextCard);
		curPlayer.removeCardFromDeck(nextCard);

		curPlayer.updateSuitsFast();
		if(curPlayer==p1){
			slidingDeckHolder.addDeck(p1.getDeck());
		}
		if(tableTrick.getSize()>=4){
			pickUpHand();//This sets the next player
		}
		else{
			nextPlayer();	
		}
		update();
	}
	
	/**
	 * Gets the table cards, totals points and sends to round winner
	 * Does a check on round 14 to re deal hands.
	 */
	public void pickUpHand(){
		Log.d(TAG, "Picking up hand.");
		
		justPickedUpPile=true;
		playing=false;	//Is set back to true later if trick is won.
		boolean jackFound = false;
		int high = 0;
		int highSeat  = 0;
		int points = 0;
		String roundString = "Round="+round+"\n";
		int firstSuit=tableTrick.getCard(0).getSuit();
		for(int i=0;i<tableTrick.getSize();i++){		// maybe start at the back of the pile...yet it does not really matter
			Card currentCard=tableTrick.getCard(i);
			int curSuit=currentCard.getSuit();
			int curCard =currentCard.getValue();
			if(firstSuit==curSuit){  	
				if(high<curCard){ //equals so that p1 can take the lead.  But should never be equal
					high=curCard;
					highSeat=currentCard.getOwner().getSeat();//Seats are 1-4 
				}
			}
			if(curSuit==0){
//				clubsPlayedString+=currentCard.cardToString()+"\n";
				clubsPlayedInt++;
			}
			
			else if(curSuit==1){ //Diamonds  check for jack.
//				diamondsPlayedString+=currentCard.cardToString()+"\n";
				diamondsPlayedInt++;
				if(curCard==11){
					jackFound = true;
					this.jackFound = true;
					points-=10;
					Toast.makeText(context, "Jack - 10 points",  Toast.LENGTH_SHORT).show();

				}
			}
			else if(curSuit==2){//Spades   check for queen
//				spadesPlayedString+=currentCard.cardToString()+"\n";
				spadesPlayedInt++;
				if(curCard==12){
					heartsBroken=true;
					queenFound  =true;
					points+=13;
					Toast.makeText(context, "Queen + 13 points",  Toast.LENGTH_SHORT).show();

				}
			}
			else if(curSuit==3){//heart--add points				
//				heartsPlayedString+=currentCard.cardToString()+"\n";
				heartsPlayedInt++;
				heartsBroken=true;
				points++;
			}
			Log.d(TAG, "pick up hand cards checked="+i);
			roundString += currentCard.name+", ";

		}
		roundCardString+=roundString+"\n";
		
		if(cardCounterB){
			clubsPlayed.setText("C="+clubsPlayedInt);
			diamondsPlayed.setText("D="+diamondsPlayedInt);
			spadesPlayed.setText("S="+spadesPlayedInt);
			heartsPlayed.setText("H="+heartsPlayedInt);
			int total=clubsPlayedInt+diamondsPlayedInt+spadesPlayedInt+heartsPlayedInt;
		}
		resetPlayerHolderCards();
		switch(highSeat){
		case 1:
			curPlayer=p1;
			p1Holder.addBlankCard(0);
			p1.addToScore(points);
			roundScoreP1+=points;
			playing = true;
			playCard.setText("Play Card");
			bottomText2.append("You won!  Points= "+points+eol);
			bottomText2.append("Choose a starting card."+eol);
			if(jackFound)
			    jackFoundP1 = true;
			break;
		case 2:
			curPlayer=p2;
			p2Holder.addBlankCard(0);
			p2.addToScore(points);
			roundScoreP2+=points;
			playCard.setText("Next");
			bottomText2.append(eol+"P2 won, points= "+points+eol);
			if(jackFound)
			    jackFoundP2 = true;
			break;
		case 3:
			curPlayer=p3;
			p3Holder.addBlankCard(0);
			p3.addToScore(points);
			roundScoreP3+=points;
			playCard.setText("Next");
			bottomText2.append(eol+"P3 won, points= "+points+eol);
			if(jackFound)
			    jackFoundP3 = true;
			break;
		case 4: 
			curPlayer=p4;
			p4Holder.addBlankCard(0);
			p4.addToScore(points);
			roundScoreP4+=points;
			playCard.setText("Next");
			bottomText2.append(eol+"P4 won, points= "+points+eol);
			if(jackFound)
			    jackFoundP4 = true;
			break;
		}	
		Log.d(TAG, "pickUpHand() for "+curPlayer.getRealName());
		roundScore+=points;
		roundWinnerAndPoints.add(new TrickStats(points, curPlayer, tableTrick));  
		
		if(cardToPlay!=null){
			cardToPlay.setTouched(false);
			cardToPlay=null;
		}
		if(endGameCheck()){
			gameOver();
			return;
		}
		round++;	
		roundView.setText("Round="+round);
		if(round==14){//Done playing need new cards.'
			Log.d(TAG, "round is 14 checking moon for holes");
			session++;	//Used to tell when to trade and who to
			if(session > 5){
				session = 1;
			}
			gameHands++;
			round=1;  	//ROUND RESET!!!
			
			if(!shootingForTheMoon()){//false we missed need a new round/or we hit the moon but nobody lost.  True if game winnings shot.
				Log.d(TAG, "New Round needed, games is not over yet.");
				newRound();
			}

		}
		else{
			setState(highSeat);		//Used by AI for picking next card.
			voidCheckAllPlayers();
			
		}
		main.handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if(justPickedUpPile){
					clearTableCards();			
					update();					
					tableTrick.clearALL();
					justPickedUpPile=false;
				}
			}
		}, main.gt.getAutoRunTimeinMilli()/2);
		//;

				
	}
	
	/**
	 * Called after pick up hand if game is not over
	 * Resets round variables and calls heartsMe()
	 * 
	 */
	private void newRound(){
		Log.d(TAG, "New round");
		playCard.setEnabled(false);	//This should fix hitting next to fast and causing a GO() that will crash game.  Re-enabled in HeartsMe()
		newRound=true;
		resetRoundVars();
		heartsMe();

	}

	
	/**
	 * Checks total scores for the round to see if anyone shot the moon
	 * 
	 * If true adds 26 points to everyone else and takes the 26 points away from self.
	 * 
	 * @return True if hit AND game is over
	 * Else false and game continues.
	 * OPTION if game is over due to shooting the moon just minus 26 points from players score
	 * OR give option to choose.
	 */
	private boolean shootingForTheMoon(){
		boolean hit = false;
		Log.d(TAG, "Shooting for the Moon");
		Log.d(TAG, "p1.score="+p1.score);
		Log.d(TAG, "p2.score="+p2.score);
		Log.d(TAG, "p3.score="+p3.score);
		Log.d(TAG, "p4.score="+p4.score);
		Log.d(TAG, "BLAM!!!!!");
		if(roundScoreP1==26||roundScoreP1==16&&jackFoundP1){
			bottomText2.setText("You Shot the MOON!");
			bottomText2.append("Plus 26 points to EVERYONE ELSE!");
			p1.score-=26;
			p2.score+=26;
			p3.score+=26;
			p4.score+=26;
			hit = true;
		}		
		if(roundScoreP2==26||roundScoreP2==16&&jackFoundP2){
			bottomText2.setText("(P2) Shot the MOON!");
			bottomText2.append("Plus 26 points to EVERYONE ELSE!");
			p1.score+=26;
			p2.score-=26;
			p3.score+=26;
			p4.score+=26;
			hit = true;
		}		
		if(roundScoreP3==26||roundScoreP3==16&&jackFoundP3){
			bottomText2.setText("(P3) Shot the MOON!");
			bottomText2.append("Plus 26 points to EVERYONE ELSE!");
			p1.score+=26;
			p2.score+=26;
			p3.score-=26;
			p4.score+=26;
			hit = true;
		}		
		if(roundScoreP4==26||roundScoreP4==16&&jackFoundP4){
			bottomText2.setText("(P4) Shot the MOON!");
			bottomText2.append("Plus 26 points to EVERYONE ELSE!");
			p1.score+=26;
			p2.score+=26;
			p3.score+=26;
			p4.score-=26;
			hit = true;
		}
		if (!hit){
			Log.d(TAG, "Miss!");

			
			return false;
		}
		else
			Log.d(TAG, "Direct Hit!");
			if(endGameCheck()){
				bottomText2.append("GAME ENDING SHOT!");
				Log.d(TAG, "game ending shot");
				gameOver();
				return true;
			}
//			roundWinnerAndPoints.add(new TrickStats(points, curPlayer, tableTrick));  
		return false;
	}
	
	
	/**
	 * Check to see if anyone broke the point limit till end of game
	 * @return	true if game is over.
	 */
	public boolean endGameCheck(){
		if(p1.getScore()>=pointsTillEndGame){
			Log.d(TAG, "Player 1 LOOSES");
			
			return true;
		}
		if(p2.getScore()>=pointsTillEndGame){
			Log.d(TAG, "Player 2 LOOSES");
			return true;

		}
		if(p3.getScore()>=pointsTillEndGame){
			Log.d(TAG, "Player 3 LOOSES");
			return true;

		}
		if(p4.getScore()>=pointsTillEndGame){
			Log.d(TAG, "Player 4 LOOSES");
			return true;

		}
		return false;  //Nobody has too many points
		//TODO End game mode, find winner, show scores, 
	}
	
	/**
	 * Called when endGameCheck returns true, Stops the game and sets winner.
	 */
	private void gameOver(){
		gameOver=true;
		winnerCheck();
		if(main.gt.fullAutoRun)
			main.gt.fullAutoRun=!main.gt.fullAutoRun;
		main.gt.autoRunState.compareAndSet(AutoRunState.RUNNING,  AutoRunState.PAUSED);
		playCard.setText("Game Saved");
		playCard.setEnabled(false);
		bottomText.setText(curPlayer.getRealName()+" WON THE GAME!!!");
		bottomText2.append("Press menu, then Exit to play again.");
		Toast.makeText(context, curPlayer.getRealName() + " Won the GAME!!",  Toast.LENGTH_LONG).show();
		saveGameStats(curPlayer.getRealName());

	}
	
	/**
	 * Finds lowest scoring player and set winner to true.
	 * Does less than or equal to...should be just less than or tie.
	 */
	public void winnerCheck(){//this prob needs some rework
		Log.d(TAG, "winnerCheck()");
		int scorep1 = p1.getScore();
		int scorep2 = p2.getScore();
		int scorep3 = p3.getScore();
		int scorep4 = p4.getScore();

		if(scorep1<=scorep2){
			if(scorep1<=scorep3){
				if(scorep1<=scorep4){
					Log.d(TAG, "YOU WON");
					p1.winner=true;
//					saveGameStats(p1.getRealName());
					curPlayer=p1;
					return;
				}
				else{
					Log.d(TAG, "P4 WON-2");
					p4.winner=true;
//					saveGameStats(p4.getRealName());
					curPlayer=p4;
					return;
				}
			}
			else if(scorep3<=scorep4){
				Log.d(TAG, "P3 WON");
				p3.winner=true;
//				saveGameStats(p3.getRealName());
				curPlayer=p3;
				return;
			}

		}
		else if(scorep2<=scorep3){
			if(scorep2<=scorep4){
				Log.d(TAG, "P2 WON");
				p2.winner=true;
//				saveGameStats(p2.getRealName());
				curPlayer=p2;
				return;
			}
			else{
				Log.d(TAG, "P4 WON-3");
				p4.winner=true;
//				saveGameStats(p4.getRealName());
				curPlayer=p4;
				return;
				
			}
		}
		else if(scorep3<=scorep4){
				Log.d(TAG, "P3 WON");
				p3.winner=true;
//				saveGameStats(p3.getRealName());
				curPlayer=p3;

				return;
			}
			else{
				Log.d(TAG, "P4 WON-4");
				p4.winner=true;
//				saveGameStats(p4.getRealName());
				curPlayer=p4;
				return ;
				
			}
	}
		
	
	
///////////////////////////////////////////////////////////////////////////////GAMEVIEW ////////////////////////////////////
    public void findViewsById() {
    	Log.d(TAG, "firstStart()");
    	roundView = (TextView) main.findViewById(R.id.roundView);
    	bottomText = (TextView) main.findViewById(R.id.bottomTV);
    	bottomText2 = (TextView) main.findViewById(R.id.bottomTV2);
	
        clubsPlayed = (TextView) main.findViewById(R.id.clubsPlayed);
        diamondsPlayed = (TextView) main.findViewById(R.id.diamondsPlayed);
        spadesPlayed = (TextView) main.findViewById(R.id.spadesPlayed);
        heartsPlayed = (TextView) main.findViewById(R.id.heartsPlayed);
        
        deckHolderLayout = (HorizontalScrollView) main.findViewById(R.id.DeckHolderLayout);
        tableHolderLayout = (LinearLayout) main.findViewById(R.id.TableHolderLayout);
        topLayout =  (LinearLayout) main.findViewById(R.id.topLayout);
//        bottomTVlayout =  (LinearLayout) main.findViewById(R.id.bottomTVlayout);
//        middleLayout  =  (LinearLayout) main.findViewById(R.id.MiddleLayout);
//        tableRightView  =  (LinearLayout) main.findViewById(R.id.TableRightView);
        
    	playCard = (Button) main.findViewById(R.id.playCard);
//    	leftButton = (Button) main.findViewById(R.id.left);
//    	rightButton = (Button) main.findViewById(R.id.right);
    	if(portaitMode){
    		playCard.setWidth(screenWidth/4);
    	}

    	else{
    		playCard.setWidth(screenWidth/4);

    	}
    	//    		rightButton.setWidth(screenWidth/4);
//    	leftButton.setWidth(screenWidth/4);
    	
   
    	findCardBitmaps();
	}
	
	public void createDeckTableViews(){
		Log.d(TAG, "CreateViews");

//    	deckHolder = new DeckHolder(context, this, screenWidth, screenHeight/8);
//        deckHolderlayoutParams = new LinearLayout.LayoutParams(screenWidth, screenHeight/8);
		slidingDeckHolder = new SlidingDeckHolder(context, this, screenWidth, screenHeight/8);
        slidingDeckHolderlayoutParams = new LinearLayout.LayoutParams(screenWidth, screenHeight/8);

        tableHolder = new TableHolder(context, this, (int) (screenWidth*.75), (int) (screenHeight/6));
        tableHolderlayoutParams = new LinearLayout.LayoutParams((int) (screenWidth*.75), (int) (screenHeight/6));

//        deckHolder.setLayoutParams(deckHolderlayoutParams);
        slidingDeckHolder.setLayoutParams(slidingDeckHolderlayoutParams);
        tableHolder.setLayoutParams(tableHolderlayoutParams);

//        deckHolderLayout.addView(this.deckHolder);
        deckHolderLayout.addView(slidingDeckHolder);
        tableHolderLayout.addView(this.tableHolder);
        tableHolderLayout.setLayoutParams(tableHolderlayoutParams);
        
      

        this.initialized=true;
    }
	
	/**
	 * Creates the Views for the players.
	 */
	public void createPlayerViews(){
		LayoutParams playerLayout = new LinearLayout.LayoutParams((int) (screenWidth*.25), screenHeight/10);
		//New code to add player info
		p1Holder = new PlayerHolder(context, main, this, (int) (screenWidth*.25), screenHeight/10, p1);
	    p2Holder = new PlayerHolder(context, main, this, (int) (screenWidth*.25), screenHeight/10, p2);
	    p3Holder = new PlayerHolder(context, main, this, (int) (screenWidth*.25), screenHeight/10, p3);
	    p4Holder = new PlayerHolder(context, main, this, (int) (screenWidth*.25), screenHeight/10, p4);
        p1Holder.setLayoutParams(playerLayout);
        p2Holder.setLayoutParams(playerLayout);
        p3Holder.setLayoutParams(playerLayout);
        p4Holder.setLayoutParams(playerLayout);
        topLayout.addView(this.p1Holder);
        topLayout.addView(this.p2Holder);
        topLayout.addView(this.p3Holder);
        topLayout.addView(this.p4Holder);
	}
	 
	////////////////////////////////////////Start Updates//////////////////////////////////////////////////////////////////////
	/**
	 * Call to refresh the screen.
	 */
	public synchronized void update() {
		if(this.initialized){
			//updateDH();
			slidingDeckHolder.invalidate();
			updateTH();
			updatePH();
			updateTable();
			//p1.updateDeckFromSuits();
			//deckHolder.addDeck(p1.getDeck());
		}
		else{
			Log.d(TAG, "not Initialized");
		}
	}
	
	public synchronized void updateDH(){
//		if (!deckHolder.initialized){
//			Log.d(TAG, "DeckHolder not initialized");
//		}
//		else{
//			//deckHolder.addDeck(p1.getDeck());
//			deckHolder.updateDeck(p1.getDeck());
//
////			deckHolder.invalidate();
////			deckHolder.draw();
//			deckHolderCanvas = deckHolder.getHolder().lockCanvas();
//			deckHolder.onDraw(deckHolderCanvas);
//			deckHolder.getHolder().unlockCanvasAndPost(deckHolderCanvas);
//			
//		}
	}

	public synchronized void updateTH(){
		if (!tableHolder.initialized){
			Log.d(TAG, "TableHolder not initialized");
		}
		else{
			tableHolderCanvas = tableHolder.getHolder().lockCanvas();
			tableHolder.onDraw(tableHolderCanvas);
			tableHolder.getHolder().unlockCanvasAndPost(tableHolderCanvas);
			}
	}
	
	public void updatePH(){
		if (!p1Holder.initialized){
			Log.d(TAG, "p1HolderCanvas not initialized");
		}
		else{
			p1Holder.invalidate();
			p1HolderCanvas = p1Holder.getHolder().lockCanvas();
			p1Holder.onDraw(p1HolderCanvas);
			p1Holder.getHolder().unlockCanvasAndPost(p1HolderCanvas);
			}
		if (!p2Holder.initialized){
			Log.d(TAG, "p2HolderCanvas not initialized");
		}
		else{
			p2Holder.invalidate();
			p2HolderCanvas = p2Holder.getHolder().lockCanvas();
			p2Holder.onDraw(p2HolderCanvas);
			p2Holder.getHolder().unlockCanvasAndPost(p2HolderCanvas);
			}
		if (!p3Holder.initialized){
			Log.d(TAG, "p3HolderCanvas not initialized");
		}
		else{
			p3HolderCanvas = p3Holder.getHolder().lockCanvas();
			p3Holder.onDraw(p3HolderCanvas);
			p3Holder.getHolder().unlockCanvasAndPost(p3HolderCanvas);
			}
		if (!p4Holder.initialized){
			Log.d(TAG, "p4HolderCanvas not initialized");
		}
		else{
			p4HolderCanvas = p4Holder.getHolder().lockCanvas();
			p4Holder.onDraw(p4HolderCanvas);
			p4Holder.getHolder().unlockCanvasAndPost(p4HolderCanvas);
			}
	}
	
	public void updatePlayerInfo(){
		p1Holder.invalidate();
		p2Holder.invalidate();
		p3Holder.invalidate();
		p4Holder.invalidate();
	}
	
	private void updateTable() {

     	roundView.invalidate();
    	bottomText.invalidate();
    	bottomText2.invalidate();
	    	
        clubsPlayed.invalidate();
        diamondsPlayed.invalidate();
        spadesPlayed.invalidate();
        heartsPlayed.invalidate();

	}
	
	public void UserUpdate(){
			Log.d(TAG,  "User Update");
			displayPlayerCards(p1);
			updateTable();
		}
	//////////////////////////////////////Card/ DeckHolder/ Table holder methods//////////////////////////////////////////////////////////////	
	
	/**
	 * Outputs to the Log a list of all the player and their points.
	 */
	public void logPlayerPoints(){
		Log.d(TAG, "P1 points="+p1.getScore());
		Log.d(TAG, "P2 points="+p2.getScore());
		Log.d(TAG, "P3 points="+p3.getScore());
		Log.d(TAG, "P4 points="+p4.getScore());
	}

	public void playCardBottomText(Card nextCard){
		//TODO set text to scroll to bottom.
		if(tableTrick.getSize()==1){	//first card was just laid down.
			if(round==1){
				if(session==4)
					bottomText2.setText("No Trading Round!"+eol+curPlayer.getRealName()+" had the "+ nextCard.name+eol);
				else
					bottomText2.append(curPlayer.getRealName()+" played  "+ nextCard.name+eol);
			}
				bottomText2.setText(curPlayer.getRealName()+" played  "+ nextCard.name+eol);
		}
		else
			bottomText2.append(curPlayer.getRealName()+" played  "+ nextCard.name+eol);
	}
	
	/**
	 * @param p the player who's deck is about to be printed
	 */
	public String displayPlayerCards(Player p){ 
		Log.d(TAG, "displayCards for "+p.getRealName());
		Deck c = p.getClubs();
		Deck d = p.getDiamonds();
		Deck s = p.getSpades();
		Deck h = p.getHearts();
		int deckSize = p.getDeck().getSize();
		int totalSize=c.getSize()+d.getSize()+s.getSize()+h.getSize();
		String clubs = "";
		String diamonds = "";
		String spades = "";
		String hearts = "";
		for(int i=0; i<c.getSize();i++){
			clubs+=c.getCard(i).getValue()+", ";
		}
		for(int i=0; i<d.getSize();i++){
			diamonds+=d.getCard(i).getValue()+", ";
		}
		for(int i=0; i<s.getSize();i++){
			spades+=s.getCard(i).getValue()+", ";
		}
		for(int i=0; i<h.getSize();i++){
			hearts+=h.getCard(i).getValue()+", ";
		}
		Log.d(TAG, "clubs=" + clubs);
		Log.d(TAG, "diamonds=" + diamonds);
		Log.d(TAG, "spades=" + spades);
		Log.d(TAG, "hearts=" + hearts);
		Log.d(TAG, "Size=" + totalSize+" Deck Size ="+deckSize);
		String sDeck= "The deck of "+p.getRealName()+"\n";
		sDeck+="clubs=" + clubs+"\n";
		sDeck+="diamonds=" + diamonds+"\n";
		sDeck+="spades=" + spades+"\n";
		sDeck+="hearts=" + hearts+"\n";
		return sDeck;
		//TODO update gameView in thread.
	}
	
	/**
	 * @param deck the Deck of cards to be returned in a string list.
	 */
	public String displayDeckCards(Deck deck){ 
		Log.d(TAG, "displayTable cards");
		String sDeck= "";
		for(int i=0; i<deck.getSize();i++){
			sDeck += (1+i)+". "+deck.getCard(i).getOwner().getRealName()+" : "+deck.getCard(i).name+"\n";
		}
		return sDeck;
		//TODO update gameView in thread.
	}
	
	public DeckHolder getDeckHolder() {
		return deckHolder;
	}

	public TableHolder getTableHolder() {
		return tableHolder;
	}

	///////////////////////////////////////////////////////////////////////VOID methods////////////////////////////////////////////////////
	
	/**
	 * Takes the curPlayer and updates to the nextPlayer.
	 * 
	 */
	public void nextPlayer(){
		main.gt.updateLastTime();
		if(curPlayer==null){
			Log.d(TAG, "CurPlayer not set");
			return;
		}
		switch(curPlayer.getSeat()){
			case 1:
				playCard.setText("Next");
				p1Holder.addBlankCard(3);
				p2Holder.addBlankCard(0);
				this.curPlayer = this.p2;		
				break;
			case 2:
				playCard.setText("Next");
				p2Holder.addBlankCard(3);
				p3Holder.addBlankCard(0);
				this.curPlayer = this.p3;		
				break;
			case 3:
				playCard.setText("Next");
				p3Holder.addBlankCard(3);
				p4Holder.addBlankCard(0);
				this.curPlayer = this.p4;	
				break;
			case 4:
				playing = true;
				playCard.setText("Play Card");
				p4Holder.addBlankCard(3);
				p1Holder.addBlankCard(0);
				this.curPlayer = this.p1;
				break;
		}
		bottomText.setText("Current player is "+curPlayer.getRealName());

	}
	
	public void resetPlayerHolderCards(){
		p1Holder.addBlankCard(1);
		p2Holder.addBlankCard(1);
		p3Holder.addBlankCard(1);
		p4Holder.addBlankCard(1);
	}
	
	/**
     * Resets all the Variables used in each round
     * Called after pickUpHand && round ==14
     */
	private void resetRoundVars(){
		heartsBroken=false;
		playing = false;
		if(cardToPlay!=null){
			cardToPlay.setTouched(false);
			cardToPlay=null;
		}
		clubsPlayedInt =	0;
		diamondsPlayedInt =	0;
		spadesPlayedInt =	0;
		heartsPlayedInt =	0;
		roundCardString =	"";
		roundScore =	0;
		roundScoreP1 = 	0;
		roundScoreP2 = 	0;
		roundScoreP3 = 	0;
		roundScoreP4 = 	0;
		jackFoundP1 = false;
		jackFoundP2 = false;
		jackFoundP3 = false;
		jackFoundP4 = false;		
		jackFound 	= false;
		queenFound	= false;
		p1.claimedDeck= false;
		p2.claimedDeck= false;
		p3.claimedDeck= false;
		p4.claimedDeck= false;
		roundView.setText("Round="+round);
		if(cardCounterB){
			clubsPlayed.setText("C="+clubsPlayedInt);
			diamondsPlayed.setText("D="+diamondsPlayedInt);
			spadesPlayed.setText("S="+spadesPlayedInt);
			heartsPlayed.setText("H="+heartsPlayedInt);
		}
		resetPlayerHolderCards();
		update();
	}
	
	/**
	 * Used to restart the game.
	 * Call heartsMe() after this.
	 */
	public void restartGame(){
	    resetRoundVars();
		restart=true;
		p1 = null;
		p2 = null;
		p3 = null;
		p4 = null;
	    round=1;
	    session=1;
		clearTableCards();

	    
	}
	
	/**
	 * Sets all the player's states for the next round.
	 * @param startSeat the player who will start the round.
	 */
	private void setState(int startSeat){
		switch (startSeat){
		case 1:
			p1.setState(1);
			p2.setState(2);
			p3.setState(3);
			p4.setState(4);
			break;
		case 2:
			p1.setState(4);
			p2.setState(1);
			p3.setState(2);
			p4.setState(3);
			break;
		case 3:
			p1.setState(3);
			p2.setState(4);
			p3.setState(1);
			p4.setState(2);
			break;
		case 4:
			p1.setState(2);
			p2.setState(3);
			p3.setState(4);
			p4.setState(1);
			break;
		}
	}
	
	/**
	 * Clears and then adds blank cards to the tableHolder
	 */
  	public void clearTableCards(){
		tableHolder.removeAll();
		tableHolder.addBlankCards();
	    
	}

  	
  	///////////////////////////////////////////////////////////////////////Save Game//////////////////////////////////////////////////////////////////////////////
  	/**
	 * Saves the Trick history to a JSON
	 * @param winner  Not being used....later implementation
	 */
	public void saveGameStats(String winner) {
		//TODO better way to keep track of file scores.
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(main);
		int wonCount = preferences.getInt("wonInt", 0);
		wonCount++;
	    if(wonCount>10){//Only Ten save files.
	    	//TODO fill empty then overwrite.
	    	wonCount=1;
	    }
	    String als = writeTextFile();
	    try {
			saveStringArray("winner"+wonCount, als);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	
	    	
	    	
	    	
//	    JSONObject jsonData = writeJSON();
//	    Log.d(TAG, jsonData.length()+"");
//		Log.d(TAG, jsonData.toString());
//		if(path==null){
//    		setupSaveSettings();
//    	}
//
//	    try {
//			saveToSD("winner"+wonCount, jsonData);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	    SharedPreferences.Editor editor = preferences.edit();
	    editor.putInt("wonInt", wonCount);
	    editor.commit();

	    
	}
	
    /**
     * Saves the Coords to the SD card.
     * @param v
     * @throws FileNotFoundException
     */
    public void saveToSD(String fileName, JSONObject data) throws FileNotFoundException{
    	Log.d(TAG, "Saving new save file to\n"+path+fileName+".txt");
    	File file = new File(path, fileName+".txt");
		    	
    	if(!path.exists()){
    		path.mkdirs();
    	}
	    if(path.canWrite()){
	    	try {
	    		FileOutputStream logWriter = new FileOutputStream(file);
	    		BufferedOutputStream out = new BufferedOutputStream(logWriter);
	    		if(gameOver){
	    			try {
						data.put("Winner", curPlayer.getRealName().toString());
						data.put("Score", curPlayer.getScore());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//	    			String winner = curPlayer.getRealName()+" Winner score="+curPlayer.getScore();
//	    			out.write(winner.getBytes());
	    		}
	    		out.write(data.toString().getBytes());

	            out.flush();
	            out.close();   
	            logWriter.close();
		    	Toast.makeText(context, "Saved "+fileName, Toast.LENGTH_SHORT).show();

	        }
	    	catch (IOException e) {	
				e.printStackTrace();
				bottomText.setText("File failed to write");
	    	}
	    	
    	}
    	else{
    		//cant write
    		Toast.makeText(context, "Not Writable", Toast.LENGTH_SHORT).show();

    	}
        
    }
        
    /**
	 * Takes the list of tricks played and writes that to a JSON
	 * @return Json as a String.
	 */
	public JSONObject writeJSON() {
		Log.d(TAG, "writeJSON()");
		JSONObject object = new JSONObject();
		  try {
			  int trickCounter=1;
			  int hand=1;
			  for(int i=0; i<roundWinnerAndPoints.size();i++){
				  
				  if(trickCounter==14){
					  hand++;
					  trickCounter=1;
				  }
				  String title="Hand="+hand+" Trick="+trickCounter;
				  object.put(title, roundWinnerAndPoints.get(i).jsonWinnerString);
				  trickCounter++;
			  }
		  } catch (JSONException e) {
		    e.printStackTrace();
		  }
		  
		  return object;
	}
	
	public String writeTextFile() {
		Log.d(TAG, "writeJSON()");
		String tricks = "";
			  int trickCounter=1;
			  int hand=1;
			  for(int i=0; i<roundWinnerAndPoints.size();i++){
				  
				  if(trickCounter==14){
					  hand++;
					  trickCounter=1;
				  }
				  String trick="Hand="+hand+" Trick="+trickCounter+":"+roundWinnerAndPoints.get(i).jsonWinnerString+"$";
				  tricks+=trick;
				  trickCounter++;
			  }
		  
		  return tricks;
	}
	/**
	 * Saves the Coords to the SD card.
     * @param v
     * @throws FileNotFoundException
     */
    public void saveStringArray(String fileName, String data) throws FileNotFoundException{
    	Log.d(TAG, "Saving new save file to\n"+path+fileName+".txt");
    	File file = new File(path, fileName+".txt");
		    	
    	if(!path.exists()){
    		path.mkdirs();
    	}
	    if(path.canWrite()){
	    	try {
	    		FileOutputStream logWriter = new FileOutputStream(file);
	    		BufferedOutputStream out = new BufferedOutputStream(logWriter);
	    		if(gameOver){
						data+="Winner=" + curPlayer.getRealName().toString();
						data+="Score=" + curPlayer.getScore();
//	    			String winner = curPlayer.getRealName()+" Winner score="+curPlayer.getScore();
//	    			out.write(winner.getBytes());
	    		}
	    		out.write(data.toString().getBytes());

	            out.flush();
	            out.close();   
	            logWriter.close();
		    	Toast.makeText(context, "Saved "+fileName, Toast.LENGTH_SHORT).show();

	        }
	    	catch (IOException e) {	
				e.printStackTrace();
				bottomText.setText("File failed to write");
	    	}
	    	
    	}
    	else{
    		//cant write
    		Toast.makeText(context, "Not Writable", Toast.LENGTH_SHORT).show();

    	}
        
    }
  	///////////////////////////////////////////////////////////////////////Touch Events///////////////////////////////////////////////////////////////////////////
	public void deckViewTouched(int x, int y) {
		boolean done =false;
		for(Card c :deckHolder.getDeck().getDeck()){
	    	if(c.getBounds().contains(x, y)){
	    		if(trading){//Pick up to three cards
	    			if(p1.cardsToTrade.size()>0){
	    				int cardInt = 0;
		    			for(Card card : p1.cardsToTrade){
	    					if(card.equals(c)){
	    	    				p1.cardsToTrade.get(cardInt).setTouched(false);
	    	    				p1.cardsToTrade.remove(cardInt);
	    						done=true;	//Already picked that one. Now it is unselected.
	    						break;
							}
		    				cardInt++;

	    				}
		    			if(done){
		    				Log.d(TAG, "deselected a card ");
		    				updateBottomTextWithTradingCards(true, p1.cardsToTrade);
    						playCard.setEnabled(false);
		    				continue;
		    			}
	    			}
    				c.setTouched(true);
    				int size = p1.cardsToTrade.size();
        			if(size<3){//Nothing picked yet
        				p1.cardsToTrade.add(c);
	    				updateBottomTextWithTradingCards(true, p1.cardsToTrade);
	    				if(p1.cardsToTrade.size()==3){
	    					playCard.setEnabled(true);
	    				}
	    				break;
	    			}
	    			else {
	    				
	    				p1.cardsToTrade.get(0).setTouched(false);
	    				p1.cardsToTrade.remove(0);
	    				p1.cardsToTrade.add(c);
	    				updateBottomTextWithTradingCards(true, p1.cardsToTrade);
	    				break;
	    			}
	    			
	    		}
	    		//Not Trading, just check to see if it can be played
	    		else if (checkPlayability(c)){	//Select a card to play.
		    			if(cardToPlay==null){//Nothing picked yet
		    				cardToPlay=c;
		    				cardToPlay.setTouched(true);
		    				myToast.setText("You picked the "+c.name);
		    				myToast.show();
		    				break;
		    			}
		    			else{
		    				cardToPlay.setTouched(false);
		    				cardToPlay=c;
		    				cardToPlay.setTouched(true);
		    				myToast.setText("You picked the "+c.name);
		    				myToast.show();

		    				break;
		    			}
		    		}
	    	}
    	}

	}
	
	public void slidingDeckViewTouched(Card c) {
		boolean done =false;
   		if(trading){//Pick up to three cards
			if(p1.cardsToTrade.size()>0){
				int cardInt = 0;
    			for(Card card : p1.cardsToTrade){
					if(card.equals(c)){
	    				p1.cardsToTrade.get(cardInt).setTouched(false);
	    				p1.cardsToTrade.remove(cardInt);
						done=true;	//Already picked that one. Now it is unselected.
						break;
					}
    				cardInt++;

				}
    			if(done){
    				Log.d(TAG, "deselected a card ");
    				updateBottomTextWithTradingCards(true, p1.cardsToTrade);
					playCard.setEnabled(false);
					return;
    			}
			}
			c.setTouched(true);
			int size = p1.cardsToTrade.size();
			if(size<3){//Nothing picked yet
				p1.cardsToTrade.add(c);
				updateBottomTextWithTradingCards(true, p1.cardsToTrade);
				if(p1.cardsToTrade.size()==3){
					playCard.setEnabled(true);
				}
			}
			else {
				
				p1.cardsToTrade.get(0).setTouched(false);
				p1.cardsToTrade.remove(0);
				p1.cardsToTrade.add(c);
				updateBottomTextWithTradingCards(true, p1.cardsToTrade);
			}

			
		}
		//Not Trading, just check to see if it can be played
		else if (checkPlayability(c)){	//Select a card to play.
			if(cardToPlay==null){//Nothing picked yet
				cardToPlay=c;
				cardToPlay.setTouched(true);
				myToast.setText("You picked the "+c.name);
				myToast.show();
				
			}
			else{
				cardToPlay.setTouched(false);
				cardToPlay=c;
				cardToPlay.setTouched(true);
				myToast.setText("You picked the "+c.name);
				myToast.show();

				
			}
			
		}
		updateDH();

	}
    	

	
	/**
	 * 
	 * @param sentRecieved true if picking cards false if received them
	 * @param cards
	 */
	public void updateBottomTextWithTradingCards(boolean picking, ArrayList<Card> cards){
		if(picking){
			switch (session){
			case 1:
				bottomText2.setText("Cards picked to trade left:"+eol);
				for(Card c : cards)
					bottomText2.append(c.name+eol);
				break;
			case 2:
				bottomText2.setText("Cards picked to trade right:"+eol);
				for(Card c : cards)
					bottomText2.append(c.name+eol);
				break;
			case 3:
				bottomText2.setText("Cards picked to trade across:"+eol);
				for(Card c : cards)
					bottomText2.append(c.name+eol);
				break;
			default:
				Log.d(TAG, "Traiding on round 4. BAD PLAN!");
				break;
			}
		}
		else{		
			bottomText2.setText("Cards given to you:"+eol);
			for(Card c : cards)
				bottomText2.append(c.name+eol);
			bottomText2.append(eol);
		}
		

	}
    	
	public void tableViewTouched(int x, int y){
		String tableInfo = displayDeckCards(tableTrick.TrickToDeck());
		main.displayTableInfo(tableInfo);
	}
	
	
	/**
	 * Checks to see if the card is playable
	 * @param c card to be checked.
	 * @return
	 */
	public boolean checkPlayability(Card c){
		if(justPickedUpPile){
			if(c.getSuit()==3){
				if(heartsBroken){//Playing a heart
					return true;
				}
				else if (p1.voidClubs&&p1.voidDiamonds&&p1.voidSpades){	//Play heart if that all you got.
					return true;
				}else{
					myToast.cancel();
					myToast.setText("Hearts have not been broken.");
					myToast.show();
					return false;
				}
			}
			return true;//we are playing the first card of the pile.
		}
		if(tableTrick.getSize()>=1){
			int startSuit = tableTrick.getCard(0).getSuit();
			if(c.getSuit()==startSuit){	//Good to play card of same suit
				return true;
				
			}
			else if(p1.checkVoid(startSuit)){	//If we are void still ok to play.
				return true;
			}
			else{
				Log.d(TAG, "Trying to play "+c.name);
				myToast.setText("Not a Valid Choice");
				myToast.show();
				return false;
			}
		}
		else if(c.getSuit()==3){
			if(heartsBroken){//Playing a heart
				return true;
			}
			else if (p1.voidClubs&&p1.voidDiamonds&&p1.voidSpades){	//Play heart if that all you got.
				return true;
			}else{
				myToast.cancel();
				myToast.setText("Hearts have not been broken.");
				myToast.show();
				return false;
			}
		}
		return true;//we are playing the first card of the pile.
	}
			
	public void findCardBitmaps(){
		GreenBack = BitmapFactory.decodeResource(main.getResources(), R.drawable.green_back);
		BlueBack = BitmapFactory.decodeResource(main.getResources(), R.drawable.blue_back);
		BlackBack = BitmapFactory.decodeResource(main.getResources(), R.drawable.black_back);
		RedBack = BitmapFactory.decodeResource(main.getResources(), R.drawable.red_back);
		CardBack = BitmapFactory.decodeResource(main.getResources(), R.drawable.cardback);

		ClubsTwo = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs2);
		ClubsThree = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs3);
		ClubsFour = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs4);
		ClubsFive = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs5);
		ClubsSix = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs6);
		ClubsSeven = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs7);
		ClubsEight = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs8);
		ClubsNine = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs9);
		ClubsTen = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs10);
		ClubsJack = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs_jack);
		ClubsQueen = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs_queen);
		ClubsKing = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs_king);
		ClubsAce = BitmapFactory.decodeResource(main.getResources(), R.drawable.clubs_ace);

		DiamondsTwo = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds2);
		DiamondsThree = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds3);
		DiamondsFour = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds4);
		DiamondsFive = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds5);
		DiamondsSix = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds6);
		DiamondsSeven = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds7);
		DiamondsEight = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds8);
		DiamondsNine = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds9);
		DiamondsTen = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds10);
		DiamondsJack = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds_jack);
		DiamondsQueen = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds_queen);
		DiamondsKing = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds_king);
		DiamondsAce = BitmapFactory.decodeResource(main.getResources(), R.drawable.diamonds_ace);

		SpadesTwo = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade2);
		SpadesThree = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade3);
		SpadesFour = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade4);
		SpadesFive = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade5);
		SpadesSix = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade6);
		SpadesSeven = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade7);
		SpadesEight = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade8);
		SpadesNine = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade9);
		SpadesTen = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade10);
		SpadesJack = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade_jack);
		SpadesQueen = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade_queen);
		SpadesKing = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade_king);
		SpadesAce = BitmapFactory.decodeResource(main.getResources(), R.drawable.spade_ace);
		

		HeartsTwo = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts2);
		HeartsThree = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts3);
		HeartsFour = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts4);
		HeartsFive = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts5);
		HeartsSix = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts6);
		HeartsSeven = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts7);
		HeartsEight = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts8);
		HeartsNine = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts9);
		HeartsTen = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts10);
		HeartsJack = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts_jack);
		HeartsQueen = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts_queen);
		HeartsKing = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts_king);
		HeartsAce = BitmapFactory.decodeResource(main.getResources(), R.drawable.hearts_ace);
	}
//////////////////////////BITMAPS
public Bitmap GreenBack;
public Bitmap BlueBack;
public Bitmap BlackBack;
public Bitmap RedBack;
public Bitmap CardBack;

public Bitmap ClubsTwo;
public Bitmap ClubsThree;
public Bitmap ClubsFour;
public Bitmap ClubsFive;
public Bitmap ClubsSix;
public Bitmap ClubsSeven;
public Bitmap ClubsEight;
public Bitmap ClubsNine;
public Bitmap ClubsTen;
public Bitmap ClubsJack;
public Bitmap ClubsQueen;
public Bitmap ClubsKing;
public Bitmap ClubsAce;

public Bitmap DiamondsTwo;
public Bitmap DiamondsThree;
public Bitmap DiamondsFour;
public Bitmap DiamondsFive;
public Bitmap DiamondsSix;
public Bitmap DiamondsSeven;
public Bitmap DiamondsEight;
public Bitmap DiamondsNine;
public Bitmap DiamondsTen;
public Bitmap DiamondsJack;
public Bitmap DiamondsQueen;
public Bitmap DiamondsKing;
public Bitmap DiamondsAce;

public Bitmap SpadesTwo;
public Bitmap SpadesThree;
public Bitmap SpadesFour;
public Bitmap SpadesFive;
public Bitmap SpadesSix;
public Bitmap SpadesSeven;
public Bitmap SpadesEight;
public Bitmap SpadesNine;
public Bitmap SpadesTen;
public Bitmap SpadesJack;
public Bitmap SpadesQueen;
public Bitmap SpadesKing;
public Bitmap SpadesAce;

public Bitmap HeartsTwo;
public Bitmap HeartsThree;
public Bitmap HeartsFour;
public Bitmap HeartsFive;
public Bitmap HeartsSix;
public Bitmap HeartsSeven;
public Bitmap HeartsEight;
public Bitmap HeartsNine;
public Bitmap HeartsTen;
public Bitmap HeartsJack;
public Bitmap HeartsQueen;
public Bitmap HeartsKing;
public Bitmap HeartsAce;

}
