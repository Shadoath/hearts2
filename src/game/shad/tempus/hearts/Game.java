package game.shad.tempus.hearts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;


public class Game extends Activity{
	
	//current issues
	//the pickup pile method needs to correctly pick who wins.  using when it was played needs aditional playstate check to confirm seat.
	//playing someone elses cards....needs testing
	// when doing the Play2clubs can play Ace instead due to getting the first item in array
	//BOTS ARE CHEATING AND NOT PLAYING CARDS WHEN THEY SHOULD!!
	//Points are not being kept...p1+p2 do odd things
	//
	public String eol = System.getProperty("line.separator");
	public String name;
	public Deck deck=new Deck();
	public int[] cardArrayRes= new int[52];
	public Card[] deckCards=new Card[4];
    public ArrayList<Card> pile=new ArrayList<Card>();
    public ArrayList<ArrayList<Card>> roundHands= new ArrayList<ArrayList<Card>>(); 
	public Player p1;
	public Player p2;
	public Player p3;
	public Player p4;
	public Player curPlayer;
	public Card cardToPlay;
	public Intent gameIntent;
	public Canvas canvas;
	public Paint paint;
	//public card[] pile = new card[4];
	public int pileI=0;

	public int selectedCard=0;
	public int selectedCardSuit=-1;
	public int selectedCardPlace=0;
	int clubsPlayedInt=0;
	int diamondsPlayedInt=0;
	int spadesPlayedInt=0;
	int heartsPlayedInt=0;
	
	
    public EditText et1;
    //Booleans for setting game states
    public boolean playing = false;  //initialized to false but set true during check for 2
    public boolean heartsBroken;
    public boolean restart = false;
    public boolean voidHelper;
    public boolean playerHelper;
    public boolean newRound=false;
    
    public int round=1;
    public int count=0;
    public int players=4;
    public int difficulty=1;
	int size;
	int screenWidth;
    int screenHeight;
	private int shuffleType;
	int playerHelperInt=0;
   
	
    public EditText tCount;
    public TextView output1;
    //Player 1
    
    public TextView p1Clubs;
    public TextView p1Diamonds;
    public TextView p1Spades;
    public TextView p1Hearts;
    
    public TextView clubsPlayed;
    public TextView diamondsPlayed;
    public TextView spadesPlayed;
    public TextView heartsPlayed;
    public TextView roundView;
	TextView totalPlayed;
    
    public Button p1ClubsB;
    public Button p1DiamondsB;
    public Button p1SpadesB;
    public Button p1HeartsB;
    
    public TextView p1tvScore;
    public TextView p2tvScore;
    public TextView p3tvScore;
    public TextView p4tvScore;
        
    public LinearLayout p1TR;

    public TextView bottomText;
    public TextView bottomText2;
    
    private GestureDetector gestures;
    LinearLayout bottomLayout;
    LinearLayout tableHolderLayout;
    LinearLayout tableLayout;
    //LinearLayout topLayout;
    DeckHolder cardViewDH;
    TableHolder tableViewDH;

    //Button used as a global place holder in function fixSides()
	Button fixSidesButton;

	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //hide keyboard :
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        gameIntent = getIntent();        
        Bundle b = gameIntent.getExtras();
        this.name=(String) b.get("name").toString().trim();
        this.voidHelper = (Boolean) b.get("voidHelper");
        this.playerHelper = (Boolean) b.get("playerHelper");
        print("player helper is "+playerHelper, 145);
        print(this.name, 148);
        if (this.name.equalsIgnoreCase("Your name")||this.name.equals("")){
        	this.name = "You";
        }
        this.difficulty =  (Integer) b.get("diff");
        this.shuffleType =  (Integer) b.get("shuffle");
        print("shuffle type= "+shuffleType, 152);

        print("difficulty= "+difficulty, 154);
               
        this.restart = (Boolean) b.get("restart");
        setContentView(R.layout.table);
        
	}
    
    @Override
    public void onStart(){
    	print("onStart Called", 135);
    	super.onStart();

    	firstStart();
    	
    }

	@Override
	public void onResume(){
		print("onResume called", 192);
        //setContentView(R.layout.table);
		//firstStart();  //always a good plan to call this
        //this could be a method to create a new hand.
		//deal();
        //checkForTwoMethod();
        //displayCards(p1);
		super.onResume();
        gestures = new GestureDetector(this, new Gestures(this));
        

		/*
		bottomText.setText("curPlayer is "+curPlayer.getRealName());
		if(pile.size()>=1){
			bottomText2.setText(""+pile.get(pile.size()-1).getOwner().getRealName()+" played last");
			
			}
		else{
			bottomText2.setText(curPlayer.getRealName()+" needs to start");
		}
		*/
		
		
	}

	@Override
	public void onPause(){
		print("on pause", 190);
		super.onPause();
		
		/*
		Bundle b;
		Parcelable p;
		p1.
		b.putParcelableArray("players", )
		ParseObject gameScore = new ParseObject("GameScore");
		gameScore.put("score", 1337);
		gameScore.put("playerName", "Sean Plott");
		gameScore.put("cheatMode", false);
		try {
		    gameScore.save();
		} catch (ParseException e) {
		    // e.getMessage() will have information on the error.
		}
		Test for crash fix from start up.
		*/
		
	}
	
	
	public void onRestoreInstanceState(Bundle savedInstanceState){
		print("onRestoreInstanceState", 212);
		restart=false;
		
		
		
		super.onRestoreInstanceState(savedInstanceState);
		
	}
	@Override
    public void onRestart(){
    	print("onRestart()", 214);
    	restart=true;
    	//TODO set ALL points back to 0.
    	super.onRestart();
    	
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mainmenu, menu);
	    return true;
	}
	
	public boolean onTouchEvent(MotionEvent e){
        return gestures.onTouchEvent(e);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.hint:
	            print("Hint asked for", 250);
				Toast.makeText(Game.this, "Press Next",  Toast.LENGTH_SHORT).show();

	            return true;
	        case R.id.settings:
	            print("Settings asked for", 253);
	            MenuInflater inflater = getMenuInflater();
	            Menu m = null;
	            inflater.inflate(R.menu.preferences, m);
	            return true;
	        case R.id.restart:
	        	print("restart pressed", 258);
	        	restart = true;
	        	
	        	clearALL();
	        	start();
	            print("Restart game", 175);
	            //TODO GAME variables reset.

	            return true;
	        case R.id.exit:
	        	restart=true;
	            print("Exit", 180);
	            finish();
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	/**
	 * quick print method for debugging
	 * @param i the String to be printed
	 * @param j the line number where the print is being called from.
	 */
    public void print(String i, int j){
	    System.out.println(i+"  "+j);
	
	}
    
    /**
     * Basically declares all the views into  objects.
     * Should only be called from OnCreate(); and OnRestart();
     * 
     */
    public void firstStart() {
    	print("firstStart()", 315);
    	p1Clubs = (TextView) findViewById(R.id.p1Clubs);
    	p1Diamonds = (TextView) findViewById(R.id.p1Diamonds);
    	p1Spades = (TextView) findViewById(R.id.p1Spades);
    	p1Hearts = (TextView) findViewById(R.id.p1Hearts);
    	
        p1tvScore = (TextView) findViewById(R.id.p1tvScore);
        p2tvScore = (TextView) findViewById(R.id.p2tvScore);
        p3tvScore = (TextView) findViewById(R.id.p3tvScore);
        p4tvScore = (TextView) findViewById(R.id.p4tvScore);

        
    	roundView = (TextView) findViewById(R.id.roundView);
    	bottomText = (TextView) findViewById(R.id.bottomTV);
    	bottomText2 = (TextView) findViewById(R.id.bottomTV2);

	
    	
        p1ClubsB =(Button)findViewById(R.id.p1clubsButton);
        p1DiamondsB=(Button)findViewById(R.id.p1diamondsButton);
        p1SpadesB=(Button)findViewById(R.id.p1spadesButton);
        p1HeartsB=(Button)findViewById(R.id.p1heartsButton);
        
                
        p1TR= (LinearLayout) findViewById(R.id.player1);

        clubsPlayed = (TextView) findViewById(R.id.clubsPlayed);
        diamondsPlayed = (TextView) findViewById(R.id.diamondsPlayed);
        spadesPlayed = (TextView) findViewById(R.id.spadesPlayed);
        heartsPlayed = (TextView) findViewById(R.id.heartsPlayed);
        totalPlayed = (TextView) findViewById(R.id.totalPlayed);
        bottomLayout = (LinearLayout) findViewById(R.id.bottomLayout);
        tableHolderLayout = (LinearLayout) findViewById(R.id.tableHolderLayout);
        tableLayout = (LinearLayout) findViewById(R.id.tableLayout);

        //topLayout = (LinearLayout) findViewById(R.id.topLayout); 

        Display display = getWindowManager().getDefaultDisplay(); 
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();

        getCardResources();
        start();
	}
	 
    public void start(){ 	
    	print("start()", 344);
    	//should not need to make deck, just shuffle the old one.
        if(restart){
        	//TODO reset all points
			print("restarting-onStart", 348);
        	makeDeck();
			shuffle();
	        deal();
	        //This needs to be done later trade();  //ha ha more like set who to trade too.
	        //TODO let play select cards and trade.
	        voidCheck();

	        
	        createViews();
	        checkForTwoMethod();//This more or less starts the game.
	        displayCards(p1);
        }
    
        else{
        	print("Normal Start", 386);
        	//New round keep playing
			makeDeck();
        	shuffle();	
	        deal();
	        //TODO trade()
	        voidCheck();
	        createViews();
	        
	        checkForTwoMethod();//This more or less starts the game.
	        displayCards(p1);
        }
      //DECKHOLDER
        
	}
    
    public void createViews(){
    	this.cardViewDH = new DeckHolder(this.getApplicationContext(), screenWidth, screenHeight/8);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth, screenHeight/8);

        this.tableViewDH = new TableHolder(this.getApplicationContext(), (int) (screenWidth*.7), screenHeight/8);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams((int) (screenWidth*.7), screenHeight/8);

        cardViewDH.setLayoutParams(layoutParams);
        tableViewDH.setLayoutParams(layoutParams2);
     
        cardViewDH.addDeck(p1.getDeck());
        tableHolderLayout.addView(cardViewDH);
        tableLayout.addView(tableViewDH);
    }
    
    
    /**
     * creates 52 cards 13 of each suit.
     */
    public void makeDeck() { 
    	print("make Deck", 404);
    	deck.clearALL();
		for(int suit=0;suit<4;suit++){			
			for(int value=1;value<14;value++){
				Card cd = new Card( value, suit, this.getApplicationContext());
				deck.addCard(cd);
			}

		}
		
	}
	/**
	 * WOULD NOT BET LIFE ON THIS SHUFFLE!!!
	 * Probably a very uneven shuffle.
	 */
	public void shuffle(){
		print("shuffle", 424);
			if(shuffleType==1){
				print("shuffle type 1", 475);

				//New shuffle going in 8/16
				int x = 0;
				int z = 50;
				int total =0;
				
				Deck deck2 = new Deck();
				Deck deck3 = new Deck();
				Deck deck4 = new Deck();
				deck2.addAllCards(this.deck);
				int j=0;
				int a=(int)  (Math.random()*7)+1;
				int dLength=0;;
				boolean stop = false;
				while(x<z&&!stop){
					x++;
					dLength=deck2.getSize();
					while(dLength>0){
						if(dLength<=a){
							print("STOP, too Small. "+dLength, 443);
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
						//print("deck2= "+dLength, 461);
						//print("deck3= "+deck3.getSize(), 462);
						//print("deck4= "+deck4.getSize(), 463);
					}
					//deck2.clearALL();  should be empty
					stop=false;
					deck2.addAllCards(deck4);  //dont use = it makes them clones of each other
					deck4.clearALL();
					print("x="+x+"  z="+z, 467);
					
				}
				print("j is "+j, 470);
				deck.clearALL();
				deck.addAllCards(deck2);
			}
			else if(shuffleType==2){	//this shuffle may not work after changing everything to Deck...
				print("shuffle type 2", 475);
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
						while(deck.getCard(a)!=null&&!stop){
							loop++;
							a=(int) (Math.random()*r);
							if(loop>15&&!stop){ //careful on this Set loop to 15 from 10.
								r = 0;
								for(int q=0; q<deck.getSize();q++){//Random math not finding cards, Empty rest and start again.
									if(deck.getCard(q)!=null){
										deck2.addCardAtIndex(q, deck.getCard(q));
										r++;
										j++;
									}
									
									
								}
								stop=true;
								
								
							}
						}
						if(!stop){
							deck2.addCardAtIndex(a, deck.getCard(a));
							deck.addCardAtIndex(a, null);			
							j++;
						}
					}
					
					deck=deck2;
					
					
					
				}
			}
			else{
				//TODO finish.
				print("shuffle type 3", 475);
				int x = 0;
				int z = 50;
				int total =0;
				ArrayList<Deck> decks= new ArrayList<Deck>();
				Deck deck2 = new Deck();
				deck2.addAllCards(this.deck);
				while(x<z){
					decks.addAll(splitDeck(deck2));
					print("the deck is "+decks.size(), 552);
					deck2.clearALL();
					deck2.addAllCards(mixOutIn(decks));
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
		}
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
	
	public ArrayList<Card> addLowHigh(ArrayList<Card> fromDeck){
		ArrayList<Card> toDeck= new ArrayList<Card>();
		for(int i=0;i<fromDeck.size();i++){
			toDeck.add(fromDeck.get(i));
		}
		return toDeck;
	}
	
	public ArrayList<Card> addHighLow(ArrayList<Card> fromDeck){
		ArrayList<Card> toDeck= new ArrayList<Card>();
		for(int i=fromDeck.size()-1;i>=0;i--){
			toDeck.add(fromDeck.get(i));
		}
		return toDeck;
	}
	
	
/**
 	* Create the and deals them out and then finds the two
	 * NEEDS SHUFFLE.
	 * */
	public void deal() {
		print("dealing", 560);
		Deck hand1 = new Deck();
		Deck hand2 = new Deck();
		Deck hand3 = new Deck();
		Deck hand4 = new Deck();
		for(int i=0;i<deck.getSize();i+=4){
			int d1=i;
			int d2=i+1;
			int d3=i+2;
			int d4=i+3;
			hand1.addCard(deck.getCard(d1));
			hand2.addCard(deck.getCard(d2));
			hand3.addCard(deck.getCard(d3));
			hand4.addCard(deck.getCard(d4));
			
			
		}
		if(p1==null||restart){//first Start  or restart called
			print("New hands dealt.", 483);
			int b = Color.rgb(0, 50 , 200);
			p1 = new Player(hand1, 0, 1, name, Color.WHITE); 
			p2 = new Player(hand2, 0, 2, "Chuck  (P2)", Color.RED);
			p3 = new Player(hand3, 0, 3, "Skippy  (P3)", b);
			p4 = new Player(hand4, 0, 4, "Jeff  (P4)", Color.YELLOW);
			//p2.setColor(Color.RED);
		}
		else {//else new round and New cards are dealt
			print("new round being delt", 563);
			p1.sethand(hand1);
			p2.sethand(hand2);
			p3.sethand(hand3);
			p4.sethand(hand4);
		}			
		
		//TODO if resume code 
		/* New Deal to work the proper way.
		ArrayList<card> hand1 = new ArrayList<card>();
		ArrayList<card> hand2 = new ArrayList<card>();
		ArrayList<card> hand3 = new ArrayList<card>();
		ArrayList<card> hand4 = new ArrayList<card>();
 
		for(int i=0;i<13;i++){//could be buggy; also could be shot for shuffling like this.
			int d1=i;
			int d2=13+i;
			int d3=26+i;
			int d4=39+i;
			hand1.add(deck[d1]);
			hand2.add(deck[d2]);
			hand3.add(deck[d3]);
			hand4.add(deck[d4]);
		}
		if(p1==null||restart){//first Start  or restart called
			print("New hands dealt.", 483);
			int b = Color.rgb(0, 50 , 200);
			p1 = new Player(hand1, 0, 1, name, Color.WHITE); 
			p2 = new Player(hand2, 0, 2, "Chuck  (P2)", Color.RED);
			p3 = new Player(hand3, 0, 3, "Skippy  (P3)", b);
			p4 = new Player(hand4, 0, 4, "Jeff  (P4)", Color.YELLOW);
			//p2.setColor(Color.RED);
		}
		else if(count==0){//else new round and New cards are dealt
			print("new round being delt", 492);
			p1.sethand(hand1);
			p2.sethand(hand2);
			p3.sethand(hand3);
			p4.sethand(hand4);
		}
		else{
			print("IMPORTANT STUFF", 494);
			//we are loading an old game.....
			//TODO more code for setting it up right?
			
		}	
	*/
		
	}
	
	
	/**
	 * Call on round 0;
	 * Finds who has the 2 of clubs and forces them to play it
	 * Sets the states for the next round
	 * Then advances the curPlayer
	 */
	public void checkForTwoMethod(){
		print("checkForTwoMethod() called", 638);
		if(p1.checkForTwo()){
			print("p1 plays 2 of clubs", 305);
			curPlayer=p1;
			//tableCard1.setText(selectedCardSuit+"-"+selectedCard);  //TODO remove this line
			bottomText.setText("You played the 2");
			//playing=true;
			//New code to set up each round play state then not modify till next round.
			p1.setState(1);
			p2.setState(2);
			p3.setState(3);
			p4.setState(4);
			playTwoOfClubs();
			displayCards(p1);


		}
		else if(p2.checkForTwo()){
			print("p2 plays 2 of clubs", 320);
			bottomText.setText("p2 plays 2 of clubs");
			curPlayer=p2;
			p1.setState(4);
			p2.setState(1);
			p3.setState(2);
			p4.setState(3);
			playTwoOfClubs();
			}
		else if(p3.checkForTwo()){
			print("p3 plays 2 of clubs", 330);
			bottomText.setText("p3 plays 2 of clubs");
			curPlayer=p3;
			p1.setState(3);
			p2.setState(4);
			p3.setState(1);
			p4.setState(2);
			playTwoOfClubs();
		}
		else if(p4.checkForTwo()){
			print("p4 plays 2 of clubs", 340);
			bottomText.setText("p4 plays 2 of clubs");
			curPlayer=p4;
			p1.setState(2);
			p2.setState(3);
			p3.setState(4);
			p4.setState(1);
			playTwoOfClubs();
		}
		else{
			print("The game has already started.", 465);
		}
	print(" 2 check done--curPlayer="+curPlayer.getRealName(), 438);
	}
	
	
	public void  setPlayState(Player p) {
		print("setting Player state on "+p.getSeat(), 354);
		switch(p.getSeat()){
		case 1:
			
			this.p1.setState(1);
			this.p2.setState(2);
			this.p3.setState(3);
			this.p4.setState(4);
			break;
		case 2:
			this.p1.setState(4);
			this.p2.setState(1);
			this.p3.setState(2);
			this.p4.setState(3);
			break;
		case 3:
			this.p1.setState(3);
			this.p2.setState(4);
			this.p3.setState(1);
			this.p4.setState(2);
			break;		
		case 4:
			this.p1.setState(2);
			this.p2.setState(3);
			this.p3.setState(4);
			this.p4.setState(1);
			break;
		}
		
		
	}
	
	public void voidCheck(){
        this.p1.checkForVoids();
        this.p2.checkForVoids();     
        this.p3.checkForVoids();
        this.p4.checkForVoids();
	}
	public void setPass() {
		
		switch (round%4){
		case 1:
			p1.setPass(2);
			p2.setPass(3);
			p3.setPass(4);
			p4.setPass(1);
			break;
		case 2:
			p1.setPass(4);
			p2.setPass(1);
			p3.setPass(2);
			p4.setPass(3);
			break;
		case 3:
			p1.setPass(3);
			p2.setPass(4);
			p3.setPass(1);
			p4.setPass(2);
			break;
		case 4:
			p1.setPass(0);
			p2.setPass(0);
			p3.setPass(0);
			p4.setPass(0);
			break;
		case 5://round is out of bounds should be 1
			p1.setPass(2);
			p2.setPass(3);
			p3.setPass(4);
			p4.setPass(1);
			round=1;
			break;
			
		}
		
		
	}
	//euphoria 1:18
	
	public void onPlayCardPressed(View v){
		if(cardToPlay!=null&&playing){ //make sure we have a card selected and we have not already played.
			pile.add(cardToPlay);			
			playing=false;
			switch(cardToPlay.getSuit()){
				case 0:{
					
					p1.getClubs().removeCardAtIndex(selectedCardPlace);
					break;
				}
				case 1:{
					p1.getDiamonds().removeCardAtIndex(selectedCardPlace);
					break;
				}
				case 2:{
					p1.getSpades().removeCardAtIndex(selectedCardPlace);
					break;
				}
				case 3:{
					p1.getHearts().removeCardAtIndex(selectedCardPlace);
					break;
				}
				//TODO reset the deckHolder view
			}
				
			//String rs=cardToPlay.name.replaceFirst(" of ", eol);
			if(pile.size()==0){
				clearTableCards();
			}
			tableViewDH.addCard(cardToPlay);

			if(pile.size()==4){
				//Toast.makeText(HeartsActivity.this, "Last card",  Toast.LENGTH_SHORT).show();
				pickUpHand();			
			}
			else{
				curPlayer=nextPlayer(curPlayer);
			}
			bottomText.setText("You played the "+cardToPlay.name);

			selectedCardPlace = 0;
			selectedCardSuit = -1;
			selectedCard = 0;
			displayCards(p1);
			cardToPlay=null;
		}
		else{
			Toast.makeText(Game.this, "Not your turn",  Toast.LENGTH_SHORT).show();

		}
		p1.updateDeck();
		cardViewDH.updateDeck(p1.getDeck());
		cardViewDH.invalidate();
		tableViewDH.invalidate();
		tableViewDH.refreshDrawableState();
		cardViewDH.refreshDrawableState();
		

	}
 	
	
	public void displayCards(Player p){ 
		Deck c = p.getClubs();
		Deck d = p.getDiamonds();
		Deck s = p.getSpades();
		Deck h = p.getHearts();
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
	
		switch(p.getSeat()){
			case 1:
				p1Clubs.setText(clubs);
				p1Diamonds.setText(diamonds);
				p1Spades.setText(spades);
				p1Hearts.setText(hearts);
		        p1tvScore.setText("P1 has "+p1.getScore()+" points");
				break;
			case 2:
		       // p2tvScore.setText("P2 has "+p2.getScore()+" points");
		        break;
				//TODO
			case 3:
		       // p3tvScore.setText("P3 has "+p3.getScore()+" points");
		        break;
				//TODO	
			case 4:
		        //p4tvScore.setText("P4 has "+p4.getScore()+" points");
		        break;
				//TODO
		}

		
	}

	public void onClearPressed(View v){
		print("onClearPressed", 782);
		clearTableCards();
	}

	public void clearTableCards(){
		//
		tableViewDH.removeAll();
		tableViewDH.addBlankCards();
		/*
	    tableCard1.setText("1");
	    tableCard2.setText("2");
	    tableCard3.setText("3");
	    tableCard4.setText("4");
	    tableCard1.setBackgroundColor(Color.LTGRAY);
	    tableCard2.setBackgroundColor(Color.LTGRAY);
	    tableCard3.setBackgroundColor(Color.LTGRAY);
	    tableCard4.setBackgroundColor(Color.LTGRAY);
	*/
	    
	}
	public void clearALL(){
		clearTableCards();
		p1 = null;
		p2 = null;
		p3 = null;
		p4 = null;
		playing = false;
		heartsBroken=false;
	    round=1;
	    count=0;
		
	}
	
	public void onMenuPressed(View v){
		super.onBackPressed();
	}
	public void onExitPressed(View v){
		finish();
	}
	public void onDebugButtonPressed(View v){
		showHand(p1);
		showHand(p2);
		showHand(p3);
		showHand(p4);
		Toast.makeText(Game.this, "Printed Hands",  Toast.LENGTH_SHORT).show();
		p1.updateDeck();
		cardViewDH.updateDeck(p1.getDeck());
		cardViewDH.invalidate();
		tableViewDH.invalidate();
		tableViewDH.refreshDrawableState();
		cardViewDH.refreshDrawableState();
		

	}
	public void onNextButtonPressed(View v){
		if(playing){
			
			if(playerHelper&&playerHelperInt>0){
				playerHelperInt=0;
				GO();
				
			}
			else{
				playerHelperInt++;
				Toast.makeText(Game.this, "Please play card",  Toast.LENGTH_SHORT).show();
			}
		}
		else{
			GO();
		}

	}
	
	
	public void onSwipeLeftPressed(View v){
		this.cardViewDH.swipeLeft();
		Toast.makeText(Game.this, "position is "+cardViewDH.getPosition(),  Toast.LENGTH_SHORT).show();

	}
	
	public void onSwipeRightPressed(View v){
		this.cardViewDH.swipeRight();
		Toast.makeText(Game.this, "position is "+cardViewDH.getPosition(),  Toast.LENGTH_SHORT).show();
		

	}
	
	public void GO(){
		print("GO()",  619);
		roundView.setText("Round= "+round);
		Card r=(curPlayer.go(round, pile, curPlayer));
		String rs=r.name.replaceFirst(" of ", eol);
		print (rs, 832);
		pile.add(r);
		if(curPlayer.getSeat()==4&&pile.size()!=4){
			playing=true;
			//Toast.makeText(HeartsActivity.this, "Please play card",  Toast.LENGTH_SHORT).show();//  Should not be necessary.
		}
		else{
			playing=false;//added so that player helper does not call as many toast
		}
		bottomText2.setText(curPlayer.getRealName()+" played the "+ r.name);
		curPlayer.updateDeck();
		setCardText(curPlayer, rs, r);//ADVANCES THE CUR PLAYER
		cardViewDH.invalidate();
		tableViewDH.invalidate();

	}
	
	/**
	 * Plays the selected card and advances the curPlayer
	 * @param p seat of person who is playing a card
	 * @param rs the string to be set to the layout.
	 */
	public void setCardText(Player P, String rs, Card r){
		
		int p=P.getState();  //could just call curPlayer but this makes sense in my head
		switch(p){//is is player state 1-4
		case 1:
			clearTableCards();
			tableViewDH.addCard(r);
			curPlayer=nextPlayer(curPlayer);	//this is the error line
			break;
			
		case 2:
			tableViewDH.addCard(r);

			//tableCard2.setBackgroundResource(r.getRid());
			curPlayer=nextPlayer(curPlayer);	//this is the error line

			break;
		case 3:
			tableViewDH.addCard(r);

			//tableCard3.setBackgroundResource(r.getRid());
			curPlayer=nextPlayer(curPlayer);	//this is the error line
			break;
		case 4:
			tableViewDH.addCard(r);

			//tableCard4.setBackgroundResource(r.getRid());
			if(pile.size()>=4){
				print("picking up hand", 652);
				pickUpHand();
			}
			else{
				print("PLAYER TURN", 624);
				curPlayer=nextPlayer(curPlayer);	//this is the error line
				if(playing){
					print("redundant call to set playing=true", 626);
				}
				playing=true;
			}
			break;
		case 5:
			//playState=-1; commented out
			print("OUT OF BOUNDS!!! playstate=5", 637);
			Toast.makeText(Game.this, "playState=0",  Toast.LENGTH_SHORT).show();
			break;
		}
		tableViewDH.refreshDrawableState();
		bottomText2.append(eol + "Current player is "+curPlayer.getRealName());
	}
	
	/**
	 * Sets the first round of the game going
	 * Plays two and then sets next CurPlayer
	 */
	public void playTwoOfClubs(){
		Card r=(curPlayer.getClubs().getCard(0));
		if(r.getValue()!=2){  //has the one and the two of clubs
			r=(curPlayer.getClubs().getCard(1));
			curPlayer.getClubs().removeCardAtIndex(1);//Thus only remove 2
		}
		else{
			curPlayer.getClubs().removeCardAtIndex(0); //Does not have Ace thus remove place one.
		}
		print("game started by "+curPlayer.getRealName(), 653);
		String rs=r.name.replaceFirst(" of ", eol);
		pile.add(r);
		bottomText2.setText(curPlayer.getRealName()+" played the 2 of Clubs");
		setCardText(curPlayer, null, r);//This advances the curPlayer.
		//test CODE TODO
		//canvas.drawBitmap(bitmap, x - (bitmap.getWidth() /2), y - (bitmap.getHeight() /2), null); 
		//
		//Rect rect;
		//rect= new Rect(10, 10, 120, 80);
		if(curPlayer.getSeat()==1){
			playing=true;
			bottomText.setText("Your Turn");
		}
		
		
		
		
		print("0101010", 959);
	}
	
	public void getCardResources(){
		cardArrayRes[0] = R.drawable.clubs_ace;
		cardArrayRes[2] = R.drawable.clubs2;
		cardArrayRes[3] = R.drawable.clubs3;
		cardArrayRes[4] = R.drawable.clubs4;
		cardArrayRes[5] = R.drawable.clubs5;
        //arr[1] = R.drawable.icon;
	}
	
	public void pickUpHand(){
		roundHands.add(pile);
		int high = 0;
		int highSeat  = 0;
		int points = 0;
		int firstSuit=pile.get(0).getSuit();
		for(int i=0;i<pile.size();i++){		// maybe start at the back of the pile...yet it does not really matter
			pile.get(i).setPlayed(true);	//just added Not sdfused yet but could be used to check what cards have been played on crash.
			int curSuit=pile.get(i).getSuit();
			int curCard =pile.get(i).getValue();
			if(firstSuit==curSuit){  	
				if(high<=curCard){ //equals so that p1 can take the lead.
					high=curCard;
					highSeat=pile.get(i).getOwner().getSeat();//Seats are 1-4 
				}
			}
			if(curSuit==0){
				clubsPlayedInt++;
			}
			
			else if(curSuit==1){ //Diamonds  check for jack.
				diamondsPlayedInt++;
				if(curCard==11){
					points-=10;
					Toast.makeText(Game.this, "Jack - 10",  Toast.LENGTH_SHORT).show();

				}
			}
			else if(curSuit==2){//Spades   check for queen
				spadesPlayedInt++;

				if(curCard==12){
					heartsBroken=true;
					points+=13;
					Toast.makeText(Game.this, "Queen + 13",  Toast.LENGTH_SHORT).show();

				}
			}
			else if(curSuit==3){//heart--add points
				heartsPlayedInt++;
				heartsBroken=true;
				points++;
			}
				
		}
		clubsPlayed.setText("clubs = "+clubsPlayedInt);
		diamondsPlayed.setText("diamonds = "+diamondsPlayedInt);
		spadesPlayed.setText("spades = "+spadesPlayedInt);
		heartsPlayed.setText("hearts = "+heartsPlayedInt);
		int total=clubsPlayedInt+diamondsPlayedInt+spadesPlayedInt+heartsPlayedInt;
		totalPlayed.setText("total= "+total);
		switch(highSeat){
		case 1:
			this.curPlayer=this.p1;
			this.p1.addToScore(points);
	        p1tvScore.setText("P1 has "+p1.getScore()+" points");
			playing=true;
			bottomText2.setText("You won, points="+points);
			bottomText2.append(eol+"Pick a Start Card");
			
			break;
		case 2:
			this.curPlayer=this.p2;
			this.p2.addToScore(points);
	        p2tvScore.setText("P2 has "+p2.getScore()+" points");
			bottomText2.append(eol+"P2 won, points="+points);
			
			break;
		case 3:
			this.curPlayer=this.p3;
			this.p3.addToScore(points);
	        p3tvScore.setText("P3 has "+p3.getScore()+" points");
			bottomText2.append(eol+"P3 won, points="+points);
			
			break;
		case 4: 
			this.curPlayer=this.p4;
			this.p4.addToScore(points);
	        p4tvScore.setText("P4 has "+p4.getScore()+" points");
			bottomText2.append(eol+"P4 won, taking="+points+" points");
			break;
		}	
		print("pickUpHand() for "+curPlayer.getRealName(), 770);
		curPlayer.addDeckItem(pile);
		pile.clear();
		setPlayState(curPlayer);  //for next round.


		round++;	//cards left counter
		if(round==14){//Done playing need new cards.
			Toast.makeText(Game.this, "New round!",  Toast.LENGTH_SHORT).show();
			count++;
			round=0;  	//ROUND INCREMENTER!!!
			if(endGameCheck()){
				curPlayer = winnerCheck();
				bottomText.setText(curPlayer.getRealName()+" won the game!");
				bottomText2.setText("Press menu, then restart to play again");

			}
			else{
				print("New round", 982);
				restart=false;
				start();
			}
			
		}
		
		//clears on start of next round....usually
		//Toast.makeText(HeartsActivity.this, "Press Clear to clear Board",  Toast.LENGTH_SHORT).show();
		
		/*  Kept getting ViewRoot called from wrong thread exception
		Timer t=new Timer();
		t.schedule(new TimerTask(){
			public void run(){
				EditText tableCard12 = (EditText)findViewById(R.id.card1);
				EditText  tableCard22 = (EditText)findViewById(R.id.card2);
				EditText tableCard32 = (EditText)findViewById(R.id.card3);
				EditText  tableCard42 = (EditText)findViewById(R.id.card4);
			    tableCard12.setText("");
			    tableCard22.setText("");
			    tableCard32.setText("");
			    tableCard42.setText("");
			}
		}, 5000);
		*/

		
	}
	/**
	 * Finds lowest scoring player and sets winner to true.
	 * Does less than or equal to...should be just less than or tie.
	 */
	public Player winnerCheck(){//this prob needs some rework
		int scorep1 = p1.getScore();
		int scorep2 = p2.getScore();
		int scorep3 = p3.getScore();
		int scorep4 = p4.getScore();


		if(scorep1<=scorep2){
			if(scorep1<=scorep3){
				if(scorep1<=scorep4){
					print("YOU WON", 995);
					p1.winner=true;
					return p1;
				}
				else{
					print("P4 WON", 995);
					p4.winner=true;
					return p4;
				}
			}
			else if(scorep3<=scorep4){
				print("P3 WON", 995);
				p3.winner=true;
				return p3;
			}

		}
		else if(scorep2<=scorep3){
			if(scorep2<=scorep4){
				print("P2 WON", 995);
				p2.winner=true;
				return p2;
			}
			else{
				print("P4 WON", 995);
				p4.winner=true;
				return p4;
				
			}
		}
		else if(scorep3<=scorep4){
				print("P3 WON", 995);
				p3.winner=true;
				return p3;
			}
			else{
				print("P4 WON", 995);
				p4.winner=true;
				return p4;
				
			}
		print("returning null on winner", 1040);
		return null;
		}
		
	public boolean endGameCheck(){
		if(p1.getScore()>=100){
			print("Player 1 LOOSES", 912);
			return true;
		}
		if(p2.getScore()>=100){
			print("Player 2 LOOSES", 912);
			return true;

		}
		if(p3.getScore()>=100){
			print("Player 3 LOOSES", 912);
			return true;

		}
		if(p4.getScore()>=100){
			print("Player 4 LOOSES", 912);
			return true;

		}
		return false;  //Nobody has too many points
		//TODO End game mode, find winner, show scores, 
	}
	

	public Player nextPlayer(Player p){

		switch(p.getSeat()){
			case 1:
				return this.p2;		
			case 2:
				return this.p3;		
			case 3:
				return this.p4;		
			case 4:
				playing=true;
				return this.p1;
		}
		return null;
	}
	public void onp1ClubsButtonPressed(View v){
		
		//DO NOT PUT STUFF ABOVE THIS  this check should happen first.
		Deck cArray=p1.getClubs();
		if(cArray.getSize()==0){
			return;
		}
		if(pile.size()>=1){
			int startSuit = pile.get(0).getSuit();
			if(startSuit==0||curPlayer.checkVoid(startSuit)||round==13){//error fix...||round==13
				if(selectedCard==0){
					selectedCard=cArray.getCard(selectedCardPlace).getValue();
					//pile.add((cArray.get(selectedCardPlace)));
					selectedCardSuit=0;
				}
				else{
					if(selectedCardSuit==0){
						selectedCardPlace++;
						if(selectedCardPlace>=cArray.getSize()){
							selectedCardPlace=0;
							selectedCard=cArray.getCard(selectedCardPlace).getValue();
						}
						else{
							selectedCard=cArray.getCard(selectedCardPlace).getValue();
						}
					}
					else{
						selectedCard=cArray.getCard(0).getValue();
						selectedCardSuit=0;
						selectedCardPlace=0;
					}
				}
			}
			else{
				print("Trying to play wrong suit", 1309);
				Toast.makeText(Game.this, "Not a Valid Choice",  Toast.LENGTH_SHORT).show();
				return;
			}
		}
		else{//we are playing the first card of the pile.
			if(selectedCard==0){
				selectedCard=cArray.getCard(selectedCardPlace).getValue();
				//pile.add((cArray.get(selectedCardPlace)));
				selectedCardSuit=0;
			}
			else{
				if(selectedCardSuit==0){
					selectedCardPlace++;
					if(selectedCardPlace>=cArray.getSize()){
						selectedCardPlace=0;
						selectedCard=cArray.getCard(selectedCardPlace).getValue();
					}
					else{
						selectedCard=cArray.getCard(selectedCardPlace).getValue();
					}
				}
				else{
					selectedCard=cArray.getCard(0).getValue();
					selectedCardSuit=0;
					selectedCardPlace=0;
				}
			}
		}
		cardToPlay=cArray.getCard(selectedCardPlace);
		bottomText.setText(cardToPlay.name);
	}
	public void onp1DiamondsButtonPressed(View v){
		//DO NOT PUT STUFF ABOVE THIS  this check should happen first.
		Deck cArray=p1.getDiamonds();
		if(cArray.getSize()==0){
			return;
		}
		voidCheck();
		if(pile.size()>=1){
			int startSuit = pile.get(0).getSuit();
			if(startSuit==1||curPlayer.checkVoid(startSuit)||round==13){
				if(selectedCard==0){
					selectedCard=cArray.getCard(selectedCardPlace).getValue();
					//pile.add((cArray.get(selectedCardPlace)));
					selectedCardSuit=1;
				}
				else{
					if(selectedCardSuit==1){
						selectedCardPlace++;
						if(selectedCardPlace>=cArray.getSize()){
							selectedCardPlace=0;
							selectedCard=cArray.getCard(selectedCardPlace).getValue();
						}
						else{
							selectedCard=cArray.getCard(selectedCardPlace).getValue();
						}
					}
					else{
						selectedCard=cArray.getCard(0).getValue();
						selectedCardSuit=1;
						selectedCardPlace=0;
					}
				}
			}
			else{
				print("Trying to play wrong suit", 915);
				Toast.makeText(Game.this, "Not a Valid Choice",  Toast.LENGTH_SHORT).show();
				return;

			}
		}
		else{//we are playing the first card of the pile.
			if(selectedCard==0){
				selectedCard=cArray.getCard(selectedCardPlace).getValue();
				//pile.add((cArray.get(selectedCardPlace)));
				selectedCardSuit=1;
			}
			else{
				if(selectedCardSuit==1){
					selectedCardPlace++;
					if(selectedCardPlace>=cArray.getSize()){
						selectedCardPlace=0;
						selectedCard=cArray.getCard(selectedCardPlace).getValue();
					}
					else{
						selectedCard=cArray.getCard(selectedCardPlace).getValue();
					}
				}
				else{
					selectedCard=cArray.getCard(0).getValue();
					selectedCardSuit=1;
					selectedCardPlace=0;
				}
			}
		}
		cardToPlay=cArray.getCard(selectedCardPlace);
		bottomText.setText(cardToPlay.name);
	}
	public void onp1SpadesButtonPressed(View v){

		Deck cArray=p1.getSpades();
		if(cArray.getSize()==0){
			return;
		}
		voidCheck();
		if(pile.size()>=1){
			int startSuit = pile.get(0).getSuit();

			if(startSuit==2||curPlayer.checkVoid(startSuit)||round==13){
				if(selectedCard==0){
					selectedCard=cArray.getCard(selectedCardPlace).getValue();
					//pile.add((cArray.get(selectedCardPlace)));
					selectedCardSuit=2;
				}
				else{
					if(selectedCardSuit==2){
						selectedCardPlace++;
						if(selectedCardPlace>=cArray.getSize()){
							selectedCardPlace=0;
							selectedCard=cArray.getCard(selectedCardPlace).getValue();
						}
						else{
							selectedCard=cArray.getCard(selectedCardPlace).getValue();
						}
					}
					else{
						selectedCard=cArray.getCard(0).getValue();
						selectedCardSuit=2;
						selectedCardPlace=0;
					}
				}
			}
			else{
				print("Trying to play wrong suit", 980);
				Toast.makeText(Game.this, "Not a Valid Choice",  Toast.LENGTH_SHORT).show();
				return;


			}
		}
		else{//we are playing the first card of the pile.
			if(selectedCard==0){
				selectedCard=cArray.getCard(selectedCardPlace).getValue();
				//pile.add((cArray.get(selectedCardPlace)));
				selectedCardSuit=2;
			}
			else{
				if(selectedCardSuit==2){
					selectedCardPlace++;
					if(selectedCardPlace>=cArray.getSize()){
						selectedCardPlace=0;
						selectedCard=cArray.getCard(selectedCardPlace).getValue();
					}
					else{
						selectedCard=cArray.getCard(selectedCardPlace).getValue();
					}
				}
				else{
					selectedCard=cArray.getCard(0).getValue();
					selectedCardSuit=2;
					selectedCardPlace=0;
				}
			}
		}
		cardToPlay=cArray.getCard(selectedCardPlace);
		bottomText.setText(cardToPlay.name);
	}
	public void onp1HeartsButtonPressed(View v){

		Deck cArray=p1.getHearts();
		if(cArray.getSize()==0){
			return;
		}
		voidCheck();

		if(pile.size()>=1){
			int startSuit = pile.get(0).getSuit();

			if(startSuit==3||curPlayer.checkVoid(startSuit)||round==13){
				if(selectedCard==0){
					selectedCard=cArray.getCard(selectedCardPlace).getValue();
					//pile.add((cArray.get(selectedCardPlace)));
					selectedCardSuit=3;
				}
				else{
					if(selectedCardSuit==3){
						selectedCardPlace++;
						if(selectedCardPlace>=cArray.getSize()){
							selectedCardPlace=0;
							selectedCard=cArray.getCard(selectedCardPlace).getValue();
						}
						else{
							selectedCard=cArray.getCard(selectedCardPlace).getValue();
						}
					}
					else{
						print("CODE CHECK ### CODE CHECK", 1089);
						selectedCard=cArray.getCard(0).getValue();
						selectedCardSuit=3;
						selectedCardPlace=0;
					}
				}
			}
			else{
				print("Trying to play wrong suit", 1045);
				Toast.makeText(Game.this, "Not a Valid Choice",  Toast.LENGTH_SHORT).show();
				return;

			}

		}
		else{
			if(!heartsBroken){
				print("Hearts not broken", 1101);
				Toast.makeText(Game.this, "Hearts not broken",  Toast.LENGTH_SHORT).show();

				return;
			}//we are playing the first card of the pile.
			if(selectedCard==0){
				selectedCard=cArray.getCard(selectedCardPlace).getValue();
				//pile.add((cArray.get(selectedCardPlace)));
				selectedCardSuit=3;
			}
			else{
				if(selectedCardSuit==3){
					selectedCardPlace++;
					if(selectedCardPlace>=cArray.getSize()){
						selectedCardPlace=0;
						selectedCard=cArray.getCard(selectedCardPlace).getValue();
					}
					else{
						selectedCard=cArray.getCard(selectedCardPlace).getValue();
					}
				}
				else{
					selectedCard=cArray.getCard(0).getValue();
					selectedCardSuit=3;
					selectedCardPlace=0;
				}
			}
		}		
		cardToPlay=cArray.getCard(selectedCardPlace);
		bottomText.setText(cardToPlay.name);
	}
	public void showHand(Player p){
		Deck hand = p.gethand();
		String wholeHand=p.getRealName()+" ";
		for(int i=0;i<hand.getSize();i++){
			wholeHand+=hand.getCard(i).name+", ";
		}
		print(wholeHand, 1467);
		print("total="+hand.getSize(), 1580);
	}
	public DeckHolder getDeckHolder() {
		// TODO Auto-generated method stub
		return cardViewDH;
	}
	public TableHolder getTableHolder() {
		// TODO Auto-generated method stub
		return tableViewDH;
	}
}


