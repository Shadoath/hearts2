package game.shad.tempus.hearts;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class HeartsActivity extends Activity{
	
	//current issues
	//the pickup pile method needs to correctly pick who wins.  using when it was played needs aditional playstate check to confirm seat.
	//playing someone elses cards....needs testing
	// when doing the Play2clubs can play Ace instead due to getting the first item in array
	//BOTS ARE CHEATING AND NOT PLAYING CARDS WHEN THEY SHOULD!!
	//
	public String name;
	public card[] deck=new card[53];
	public int[] cardArrayRes= new int[52];
	public card[] deckCards=new card[4];
    public ArrayList<card> pile=new ArrayList<card>();
    public ArrayList<ArrayList<card>> roundHands= new ArrayList<ArrayList<card>>(); 
	public Player p1;
	public Player p2;
	public Player p3;
	public Player p4;
	public Player curPlayer;
	public card cardToPlay;
	public Intent gameIntent;
	//public card[] pile = new card[4];
	public int pileI=0;

	public int selectedCard=0;
	public int selectedCardSuit=-1;
	public int selectedCardPlace=0;
	
    public EditText et1;
    //Booleans for setting game states
    public boolean playing = false;  //initialized to false but set true during check for 2
    public boolean heartsBroken;
    public boolean restart = false;
    public boolean voidHelper;
    
    public int round=0;
    public int count=0;
    public int players=4;
    public int difficulty=1;
    
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
    
    public Button p2Main;
    public Button p3Main;
    public Button p4Main;
    
    public Button p1ClubsB;
    public Button p1DiamondsB;
    public Button p1SpadesB;
    public Button p1HeartsB;
    
    public Button p2ClubsB;
    public Button p2DiamondsB;
    public Button p2SpadesB;
    public Button p2HeartsB;
    
    public Button p3ClubsB;
    public Button p3DiamondsB;
    public Button p3SpadesB;
    public Button p3HeartsB;
    
    public Button p4ClubsB;
    public Button p4DiamondsB;
    public Button p4SpadesB;
    public Button p4HeartsB;
    
    public TextView p1tvScore;
    public TextView p2tvScore;
    public TextView p3tvScore;
    public TextView p4tvScore;
    
    public EditText tableCard1;
    public EditText tableCard2;
    public EditText tableCard3;
    public EditText tableCard4;
    
    public LinearLayout p1TR;
    public TableRow p2TR;
    public TableRow p3TR;
    public TableRow p4TR;

    public TextView bottomText;
    public TextView bottomText2;
    
    private GestureDetector gestures;


    //Button used as a global place holder in function fixSides()
	Button fixSidesButton;
	public String eol = System.getProperty("line.separator");
	int size;
	int hsize;
	int wsize;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent.g
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameIntent = getIntent();
        
        
        
        
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SurfaceView r = (SurfaceView) findViewById(R.id.tableView);
        //setContentView(new heartsPanel(this));
        
        //p1 =new Player();

        setContentView(new heartsPanel(this));
        firstStart(); 
        
    }
   
    @Override
    public void onStart(){
    	print("onStart Called", 135);
    	super.onStart();
    	firstStart();
    	//code to go here.
    	Bundle b = gameIntent.getExtras();
        this.name=(String) b.get("name").toString().trim();
        this.voidHelper = (Boolean) b.get("voidHelper");
        print(this.name, 148);
        if (this.name.equalsIgnoreCase("Your name")){
        	this.name = "You";
        }
        this.voidHelper = (boolean) b.getBoolean("voidHelper");
        this.difficulty =  (Integer) b.get("diff");
        print("difficulty= "+difficulty, 167);
        this.restart = (Boolean) b.get("restart");
        if(restart){
        	print("restarting-onStart", 171);
	    	makeDeck();
			shuffle();
	        deal();
	        p1.sortHand();
	        p2.sortHand();
	        p3.sortHand();
	        p4.sortHand();
	
	        trade();  //ha ha more like set who to trade too.
	        //TODO let play select cards and trade.
	        voidCheck();
	    	//Should check for stuff
	        start();
        }
        else{
        	print("Game restarted", 174);
        }
		
    	
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
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.hint:
	            print("Hint asked for", 250);
				Toast.makeText(HeartsActivity.this, "Press Next",  Toast.LENGTH_SHORT).show();

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

        p2Main = (Button) findViewById(R.id.p2Main);
        p3Main = (Button) findViewById(R.id.p3Main);
        p4Main = (Button) findViewById(R.id.p4Main);

	
    	
        p1ClubsB =(Button)findViewById(R.id.p1clubsButton);
        p1DiamondsB=(Button)findViewById(R.id.p1diamondsButton);
        p1SpadesB=(Button)findViewById(R.id.p1spadesButton);
        p1HeartsB=(Button)findViewById(R.id.p1heartsButton);
        
        p2ClubsB =(Button)findViewById(R.id.p2clubsButton);
        p2DiamondsB=(Button)findViewById(R.id.p2diamondsButton);
        p2SpadesB=(Button)findViewById(R.id.p2spadesButton);
        p2HeartsB=(Button)findViewById(R.id.p2heartsButton);
        
        p3ClubsB =(Button)findViewById(R.id.p3clubsButton);
        p3DiamondsB=(Button)findViewById(R.id.p3diamondsButton);
        p3SpadesB=(Button)findViewById(R.id.p3spadesButton);
        p3HeartsB=(Button)findViewById(R.id.p3heartsButton);
        
	    p2Main.setBackgroundColor(Color.RED);
	    p3Main.setBackgroundColor(Color.BLUE);
	    p4Main.setBackgroundColor(Color.YELLOW);
	    
       /// p4ClubsB =(Button)findViewById(R.id.p4clubsButton);
       // p4DiamondsB=(Button)findViewById(R.id.p4diamondsButton);
       // p4SpadesB=(Button)findViewById(R.id.p4spadesButton);
       // p4HeartsB=(Button)findViewById(R.id.p4heartsButton);
        
        tableCard1 = (EditText)findViewById(R.id.card1);
        tableCard2 = (EditText)findViewById(R.id.card2);
        tableCard3 = (EditText)findViewById(R.id.card3);
        tableCard4 = (EditText)findViewById(R.id.card4);

        
        p1TR= (LinearLayout) findViewById(R.id.player1);
        p2TR= (TableRow) findViewById(R.id.player2);
        p3TR= (TableRow) findViewById(R.id.player3);
        p4TR= (TableRow) findViewById(R.id.player4);

        clubsPlayed = (TextView) findViewById(R.id.clubsPlayed);
        diamondsPlayed = (TextView) findViewById(R.id.diamondsPlayed);
        spadesPlayed = (TextView) findViewById(R.id.spadesPlayed);
        heartsPlayed = (TextView) findViewById(R.id.heartsPlayed);
        
        getCardResources();

	}
	 
    public void start(){ 	
    	print("start()", 344);
    	//should not need to make deck, just shuffle the old one.
		shuffle();
        deal();
        p1.sortHand();
        p2.sortHand();
        p3.sortHand();
        p4.sortHand();

        trade();  //ha ha more like set who to trade too.
        voidCheck();
        checkForTwoMethod();
        displayCards(p1);
	
	}
    /**
     * creates 52 cards 13 of each suit.
     */
    public void makeDeck() { 
    	print("make Deck", 400);
    	if(deck[0]!=null){
    		deck=new card[53];
    	}
		for(int suit=0;suit<4;suit++){			
			for(int value=1;value<14;value++){

				card cd = new card( value, suit, null);
				deck[suit*13+value]=cd;
			}

		}
		
	}
	/**
	 * WOULD NOT BET LIFE ON THIS SHUFFLE!!!
	 * Probably a very uneven shuffle.
	 */
	public void shuffle(){
		print("shuffle", 417);
		int x=0;
		int z=50;//times to loop the deck and 'randomly' switch cards.
		int r = 51;
		boolean stop=false;
		card[] deck2= new card[52];
		while(x<z){
			x++;
			int j=0;
			int a=(int) (Math.random()*r);
			for(int i=52; i>0&&!stop; i--){
				int loop=0;
				while(deck[a]==null&&!stop){
					loop++;
					a=(int) (Math.random()*r);
					if(loop>15&&!stop){ //careful on this Set loop to 15 from 10.
						r = 0;
						for(int q=0; q<deck.length;q++){//Random math not finding cards, Empty rest and start again.
							if(deck[q]!=null){
								deck2[j]=deck[q];
								r++;
								j++;
							}
							
							
						}
						stop=true;
						
						
					}
				}
				if(!stop){
					deck2[j]=deck[a];
					deck[a]=null;			
					j++;
				}
			}
			
			deck=deck2;
			
			//print("suffle"+x, 249);
		}
	}
/**
 	* Create the and deals them out and then finds the two
	 * NEEDS SHUFFLE.
	 * */
	public void deal() {
		print("dealing", 465);
		//TODO if resume code 
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
			
		
		
		
	
		
	}
	
	
	/**
	 * Call on round 0;
	 * Finds who has the 2 of clubs and forces them to play it
	 * Sets the states for the next round
	 * Then advances the curPlayer
	 */
	public void checkForTwoMethod(){
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
	
	public void voidCheck(){
        p1.checkForVoids();
        p2.checkForVoids();     
        p3.checkForVoids();
        p4.checkForVoids();
	}
	public void trade() {
		
		switch (round){
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
					p1.getClubs().remove(selectedCardPlace);
					break;
				}
				case 1:{
					p1.getDiamonds().remove(selectedCardPlace);
					break;
				}
				case 2:{
					p1.getSpades().remove(selectedCardPlace);
					break;
				}
				case 3:{
					p1.getHearts().remove(selectedCardPlace);
					break;
				}
			}
			String rs=cardToString(cardToPlay).replaceFirst(" of ", eol);
			switch(p1.getState()){
				case 1:{
					clearTableCards();
					tableCard1.setText(rs);
			        tableCard1.setBackgroundColor(p1.colorInt);
					break;
				}
				case 2:{
					tableCard2.setText(rs);
			        tableCard2.setBackgroundColor(p1.colorInt);
					break;
				}
				case 3:{
					tableCard3.setText(rs);
			        tableCard3.setBackgroundColor(p1.colorInt);
					break;
				}
				case 4:{
					tableCard4.setText(rs);
			        tableCard4.setBackgroundColor(p1.colorInt);
					break;
				}		
			}
			if(pile.size()==4){
				//Toast.makeText(HeartsActivity.this, "Last card",  Toast.LENGTH_SHORT).show();
				pickUpHand();			
			}
			else{
				curPlayer=nextPlayer(curPlayer);
			}
			bottomText.setText("You played the "+cardToString(cardToPlay));

			selectedCardPlace = 0;
			selectedCardSuit = -1;
			selectedCard = 0;
			displayCards(p1);
			cardToPlay=null;
		}
		else{
			Toast.makeText(HeartsActivity.this, "Not your turn",  Toast.LENGTH_SHORT).show();

		}
		

	}
 	
	
	public void displayCards(Player p){ 
		ArrayList<card> c = p.getClubs();
		ArrayList<card> d = p.getDiamonds();
		ArrayList<card> s = p.getSpades();
		ArrayList<card> h = p.getHearts();
		String clubs = "";
		String diamonds = "";
		String spades = "";
		String hearts = "";
		for(int i=0; i<c.size();i++){
			clubs+=c.get(i).getValue()+", ";
		}
		for(int i=0; i<d.size();i++){
			diamonds+=d.get(i).getValue()+", ";
		}
		for(int i=0; i<s.size();i++){
			spades+=s.get(i).getValue()+", ";
		}
		for(int i=0; i<h.size();i++){
			hearts+=h.get(i).getValue()+", ";
		}
	
		switch(p.getSeat()){
			case 1:
				p1Clubs.setText(clubs);
				p1Diamonds.setText(diamonds);
				p1Spades.setText(spades);
				p1Hearts.setText(hearts);
		        p1tvScore.setText("P1 has "+curPlayer.getScore()+" points");

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

	public void onHintPressed(View v){
		clearTableCards();
	}

	public void clearTableCards(){
	    tableCard1.setText("1");
	    tableCard2.setText("2");
	    tableCard3.setText("3");
	    tableCard4.setText("4");
	    tableCard1.setBackgroundColor(Color.LTGRAY);
	    tableCard2.setBackgroundColor(Color.LTGRAY);
	    tableCard3.setBackgroundColor(Color.LTGRAY);
	    tableCard4.setBackgroundColor(Color.LTGRAY);

	    
	}
	public void clearALL(){
		clearTableCards();
		p1 = null;
		p2 = null;
		p3 = null;
		p4 = null;
		playing = false;
		heartsBroken=false;
	    round=0;
	    count=0;
		tableCard1.setBackgroundColor(curPlayer.colorInt);
		p1TR.setBackgroundColor(Color.BLACK);
		p2TR.setBackgroundColor(Color.BLACK);
		p3TR.setBackgroundColor(Color.BLACK);
		p4TR.setBackgroundColor(Color.BLACK);
		
	}
	
	public void onMenuPressed(View v){
		super.onBackPressed();
	}
	
	public void onExitPressed(View v){
		finish();
	}
	public void onBackButtonPressed(View v){
		playing=!playing;
		Toast.makeText(HeartsActivity.this, "playing is now"+playing,  Toast.LENGTH_SHORT).show();

	}
	public void onNextButtonPressed(View v){
		if(playing){
			Toast.makeText(HeartsActivity.this, "Please play card",  Toast.LENGTH_SHORT).show();
		}
		else if(curPlayer==p1){
			print("Player 1 trying to be player", 570);
			//Toast.makeText(HeartsActivity.this, "Trying to play P1",  Toast.LENGTH_SHORT).show();
		}
		else
			GO();
		
	}
	
	
	
	public void GO(){
		print("GO()",  619);
		roundView.setText("Round= "+round);
		card r=(curPlayer.go(round, pile, curPlayer));
		String rs=cardToString(r).replaceFirst(" of ", eol);
		print (rs, 832);
		pile.add(r);
		if(curPlayer.getSeat()==4&&pile.size()!=4){
			playing=true;
			//Toast.makeText(HeartsActivity.this, "Please play card",  Toast.LENGTH_SHORT).show();//  Should not be necessary.
		}
		bottomText2.setText(curPlayer.getRealName()+" played the "+ cardToString(r));
		setCardText(curPlayer, rs);//ADVANCES THE CUR PLAYER

	}
	
	/**
	 * Plays the selected card and advances the curPlayer
	 * @param p seat of person who is playing a card
	 * @param rs the string to be set to the layout.
	 */
	public void setCardText(Player P, String rs){
		int p=P.getState();  //could just call curPlayer but this makes sense in my head
		switch(p){//is is player state 1-4
		case 1:
			clearTableCards();
			
			tableCard1.setBackgroundColor(curPlayer.colorInt);
			tableCard1.setText(rs);
			curPlayer=nextPlayer(curPlayer);	//this is the error line

			break;
			
		case 2:
			tableCard2.setBackgroundColor(curPlayer.colorInt);
			tableCard2.setText(rs);
			curPlayer=nextPlayer(curPlayer);	//this is the error line

			break;
		case 3:
			tableCard3.setBackgroundColor(curPlayer.colorInt);
			tableCard3.setText(rs);
			curPlayer=nextPlayer(curPlayer);	//this is the error line
			break;
		case 4:
			tableCard4.setText(rs);
			tableCard4.setBackgroundColor(curPlayer.colorInt);
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
			print("OUT OF BOUNDS!!! playstate=4", 637);
			Toast.makeText(HeartsActivity.this, "playState=0",  Toast.LENGTH_SHORT).show();
			break;
		}
		bottomText.setText("current Player is "+curPlayer.getRealName());
	}
	
	/**
	 * Sets the first round of the game going
	 * Plays two and then sets next CurPlayer
	 */
	public void playTwoOfClubs(){
		card r=(curPlayer.getClubs().get(0));
		if(r.getValue()!=2){  //has the one and the two of clubs
			r=(curPlayer.getClubs().get(1));
			curPlayer.getClubs().remove(1);//Thus only remove 2
		}
		else{
			curPlayer.getClubs().remove(0); //Does not have Ace thus remove place one.
		}
		print("game started by"+curPlayer.getRealName(), 653);
		String rs=cardToString(r).replaceFirst(" of ", eol);
		pile.add(r);
		bottomText2.setText(curPlayer.getRealName()+" played the 2 of Clubs");
		setCardText(curPlayer, null);//This advances the curPlayer.
		//test CODE TODO
		//canvas.drawBitmap(bitmap, x - (bitmap.getWidth() /2), y - (bitmap.getHeight() /2), null); 
		
		tableCard1.setBackgroundResource(cardArrayRes[0]);
		tableCard1.setMaxHeight(123);
		tableCard1.setMaxWidth(75);
		tableCard1.setHeight(123);
		tableCard1.setWidth(75);
		Rect rect;
		rect= new Rect(10, 10, 120, 80);
		Drawable d=tableCard1.getBackground();
		String dInt= ""+d.getBounds().top+ d.getBounds().right+ d.getBounds().left+ d.getBounds().bottom;
		print (dInt, 943);
		//Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
		// Scale it to 50 x 50
		// Set your new, scaled drawable "d"
		d.setBounds(rect);
		dInt= ""+d.getBounds().top+ d.getBounds().right+ d.getBounds().left+ d.getBounds().bottom;
		print (dInt, 949);
		tableCard1.setBackgroundDrawable(d);
		if(curPlayer.getSeat()==1){
			playing=true;
			bottomText.setText("Your Turn");
		}
		
		
		
		
		print("0101010", 959);
	}
	
	public void getCardResources(){
		cardArrayRes[0] = R.drawable.club2front;
		cardArrayRes[2] = R.drawable.club2front;
		cardArrayRes[3] = R.drawable.club3front;
		cardArrayRes[4] = R.drawable.club2front;
		cardArrayRes[5] = R.drawable.club2front;
        //arr[1] = R.drawable.icon;
	}
	
	
	
	public void pickUpHand(){
		p1TR.setBackgroundColor(Color.BLACK);
		p2TR.setBackgroundColor(Color.BLACK);
		p3TR.setBackgroundColor(Color.BLACK);
		p4TR.setBackgroundColor(Color.BLACK);
		
		roundHands.add(pile);
		int high = 0;
		int highSeat  = 0;
		int points = 0;
		int firstSuit=pile.get(0).getSuit();
		for(int i=0;i<pile.size();i++){		// maybe start at the back of the pile...yet it does not really matter
			pile.get(i).setPlayed(true);	//just added Not used yet but could be used to check what cards have been played on crash.
			int curSuit=pile.get(i).getSuit();
			int curCard =pile.get(i).getValue();
			if(high<curCard){
				if(firstSuit==curSuit){  //does not work unless p1 starts.
					high=curCard;
					highSeat=pile.get(i).getOwner().getSeat();//Seats are 1-4 
				}
			}
			if(curSuit==1){ //Diamonds  check for jack.
				if(curCard==11){
					points-=10;
					Toast.makeText(HeartsActivity.this, "Jack-="+10,  Toast.LENGTH_SHORT).show();

				}
			}
			if(curSuit==2){//Spades   check for queen
				if(curCard==12){
					heartsBroken=true;
					points+=13;
					Toast.makeText(HeartsActivity.this, "Queen+="+13,  Toast.LENGTH_SHORT).show();

				}
			}
			if(curSuit==3){//heart--add points
				heartsBroken=true;
				points++;
			}
				
		}
		switch(highSeat){
		case 1:
			this.curPlayer=this.p1;
			curPlayer.addToScore(points);
	        p1tvScore.setText("P1 has "+curPlayer.getScore()+" points");
			playing=true;
			bottomText2.setText("You won, points="+points);
			bottomText.setText("Pick a Start Card");

			break;
		case 2:
			this.curPlayer=this.p2;
			curPlayer.addToScore(points);
	        p2tvScore.setText("P2 has "+curPlayer.getScore()+" points");
			bottomText2.setText("p2 won, points="+points);
			
			break;
		case 3:
			this.curPlayer=this.p3;
			curPlayer.addToScore(points);
	        p3tvScore.setText("P3 has "+curPlayer.getScore()+" points");
			bottomText2.setText("p3 won, points="+points);
			
			break;
		case 4: 
			this.curPlayer=this.p4;
			curPlayer.addToScore(points);
	        p4tvScore.setText("P4 has "+curPlayer.getScore()+" points");
			bottomText2.setText("p4 won, taking="+points+" points");

			break;
		}	
		print("pickUpHand() for "+curPlayer.getRealName(), 770);
		curPlayer.addDeckItem(pile);
		tableCard1.setBackgroundColor(curPlayer.getColor());
	    tableCard2.setBackgroundColor(curPlayer.getColor());
	    tableCard3.setBackgroundColor(curPlayer.getColor());
	    tableCard4.setBackgroundColor(curPlayer.getColor());
		pile.clear();
		setPlayState(curPlayer);  //for next round.


		count++;	//cards left counter
		if(count==13){//Done playing need new cards.
			Toast.makeText(HeartsActivity.this, "New round!",  Toast.LENGTH_SHORT).show();
			count=0;
			round++;  	//ROUND INCREMENTER!!!
			if(endGameCheck()){
				curPlayer = winnerCheck();
				bottomText.setText(curPlayer.getRealName()+" won the game!");
				bottomText2.setText("Press menu, then restart to play again");

			}
			else{
				print("New round", 982);
				shuffle();
		        deal();
		        p1.sortHand();
		        p2.sortHand();
		        p3.sortHand();
		        p4.sortHand();
		        trade();  //ha ha more like set who to trade too.
		        voidCheck();
		        checkForTwoMethod();
		        displayCards(p1);
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
				
				p1TR.setBackgroundColor(Color.BLACK);
				p2TR.setBackgroundColor(Color.GREEN);
				return p2;		
			case 2:
				p2TR.setBackgroundColor(Color.BLACK);
				p3TR.setBackgroundColor(Color.GREEN);

				return p3;		
			case 3:
				p3TR.setBackgroundColor(Color.BLACK);
				p4TR.setBackgroundColor(Color.GREEN);

				return p4;		
			case 4:
				p4TR.setBackgroundColor(Color.BLACK);
			int C=-132936;
			
			p1TR.setBackgroundColor(669900);
				playing=true;
				return p1;
		}
		return null;
	}

	public boolean canPlaySuit(Player p, int i){
		switch (i){
		case 0:
			return(p.voidClubs);
		case 1:
			return (p.voidDiamonds);
		case 2:
			return (p.voidSpades);
		case 3:
			return(p.voidHearts);
		}
		return false;
	}
	
	public void onp1ClubsButtonPressed(View v){
		
		//DO NOT PUT STUFF ABOVE THIS  this check should happen first.
		ArrayList<card> cArray=p1.getClubs();
		if(cArray.size()==0){
			return;
		}
		
		if(pile.size()>=1){
			int startSuit = pile.get(0).getSuit();
			if(startSuit==0||canPlaySuit(curPlayer, startSuit)){
				if(selectedCard==0){
					selectedCard=cArray.get(selectedCardPlace).getValue();
					//pile.add((cArray.get(selectedCardPlace)));
					selectedCardSuit=0;
				}
				else{
					if(selectedCardSuit==0){
						selectedCardPlace++;
						if(selectedCardPlace>=cArray.size()){
							selectedCardPlace=0;
							selectedCard=cArray.get(selectedCardPlace).getValue();
						}
						else{
							selectedCard=cArray.get(selectedCardPlace).getValue();
						}
					}
					else{
						selectedCard=cArray.get(0).getValue();
						selectedCardSuit=0;
						selectedCardPlace=0;
					}
				}
			}
			else{
				print("Trying to play wrong suit", 849);
				Toast.makeText(HeartsActivity.this, "Not a Valid Choice",  Toast.LENGTH_SHORT).show();
				return;

			}
		}
		else{//we are playing the first card of the pile.
			if(selectedCard==0){
				selectedCard=cArray.get(selectedCardPlace).getValue();
				//pile.add((cArray.get(selectedCardPlace)));
				selectedCardSuit=0;
			}
			else{
				if(selectedCardSuit==0){
					selectedCardPlace++;
					if(selectedCardPlace>=cArray.size()){
						selectedCardPlace=0;
						selectedCard=cArray.get(selectedCardPlace).getValue();
					}
					else{
						selectedCard=cArray.get(selectedCardPlace).getValue();
					}
				}
				else{
					selectedCard=cArray.get(0).getValue();
					selectedCardSuit=0;
					selectedCardPlace=0;
				}
			}
		}
		cardToPlay=cArray.get(selectedCardPlace);
		bottomText.setText(cardToString(cardToPlay));
	}
	
	public void onp1DiamondsButtonPressed(View v){
		//DO NOT PUT STUFF ABOVE THIS  this check should happen first.
		ArrayList<card> cArray=p1.getDiamonds();
		if(cArray.size()==0){
			return;
		}
		voidCheck();
		if(pile.size()>=1){
			int startSuit = pile.get(0).getSuit();
			if(startSuit==1||canPlaySuit(curPlayer, startSuit)){
				if(selectedCard==0){
					selectedCard=cArray.get(selectedCardPlace).getValue();
					//pile.add((cArray.get(selectedCardPlace)));
					selectedCardSuit=1;
				}
				else{
					if(selectedCardSuit==1){
						selectedCardPlace++;
						if(selectedCardPlace>=cArray.size()){
							selectedCardPlace=0;
							selectedCard=cArray.get(selectedCardPlace).getValue();
						}
						else{
							selectedCard=cArray.get(selectedCardPlace).getValue();
						}
					}
					else{
						selectedCard=cArray.get(0).getValue();
						selectedCardSuit=1;
						selectedCardPlace=0;
					}
				}
			}
			else{
				print("Trying to play wrong suit", 915);
				Toast.makeText(HeartsActivity.this, "Not a Valid Choice",  Toast.LENGTH_SHORT).show();
				return;

			}
		}
		else{//we are playing the first card of the pile.
			if(selectedCard==0){
				selectedCard=cArray.get(selectedCardPlace).getValue();
				//pile.add((cArray.get(selectedCardPlace)));
				selectedCardSuit=1;
			}
			else{
				if(selectedCardSuit==1){
					selectedCardPlace++;
					if(selectedCardPlace>=cArray.size()){
						selectedCardPlace=0;
						selectedCard=cArray.get(selectedCardPlace).getValue();
					}
					else{
						selectedCard=cArray.get(selectedCardPlace).getValue();
					}
				}
				else{
					selectedCard=cArray.get(0).getValue();
					selectedCardSuit=1;
					selectedCardPlace=0;
				}
			}
		}
		cardToPlay=cArray.get(selectedCardPlace);
		bottomText.setText(cardToString(cardToPlay));
	}
	public void onp1SpadesButtonPressed(View v){

		ArrayList<card> cArray=p1.getSpades();
		if(cArray.size()==0){
			return;
		}
		voidCheck();
		if(pile.size()>=1){
			int startSuit = pile.get(0).getSuit();

			if(startSuit==2||canPlaySuit(curPlayer, startSuit)){
				if(selectedCard==0){
					selectedCard=cArray.get(selectedCardPlace).getValue();
					//pile.add((cArray.get(selectedCardPlace)));
					selectedCardSuit=2;
				}
				else{
					if(selectedCardSuit==2){
						selectedCardPlace++;
						if(selectedCardPlace>=cArray.size()){
							selectedCardPlace=0;
							selectedCard=cArray.get(selectedCardPlace).getValue();
						}
						else{
							selectedCard=cArray.get(selectedCardPlace).getValue();
						}
					}
					else{
						selectedCard=cArray.get(0).getValue();
						selectedCardSuit=2;
						selectedCardPlace=0;
					}
				}
			}
			else{
				print("Trying to play wrong suit", 980);
				Toast.makeText(HeartsActivity.this, "Not a Valid Choice",  Toast.LENGTH_SHORT).show();
				return;


			}
		}
		else{//we are playing the first card of the pile.
			if(selectedCard==0){
				selectedCard=cArray.get(selectedCardPlace).getValue();
				//pile.add((cArray.get(selectedCardPlace)));
				selectedCardSuit=2;
			}
			else{
				if(selectedCardSuit==2){
					selectedCardPlace++;
					if(selectedCardPlace>=cArray.size()){
						selectedCardPlace=0;
						selectedCard=cArray.get(selectedCardPlace).getValue();
					}
					else{
						selectedCard=cArray.get(selectedCardPlace).getValue();
					}
				}
				else{
					selectedCard=cArray.get(0).getValue();
					selectedCardSuit=2;
					selectedCardPlace=0;
				}
			}
		}
		cardToPlay=cArray.get(selectedCardPlace);
		bottomText.setText(cardToString(cardToPlay));
	}
	public void onp1HeartsButtonPressed(View v){

		ArrayList<card> cArray=p1.getHearts();
		if(cArray.size()==0){
			return;
		}
		voidCheck();

		if(pile.size()>=1){
			int startSuit = pile.get(0).getSuit();

			if(startSuit==3||canPlaySuit(curPlayer, startSuit)){
				if(selectedCard==0){
					selectedCard=cArray.get(selectedCardPlace).getValue();
					//pile.add((cArray.get(selectedCardPlace)));
					selectedCardSuit=3;
				}
				else{
					if(selectedCardSuit==3){
						selectedCardPlace++;
						if(selectedCardPlace>=cArray.size()){
							selectedCardPlace=0;
							selectedCard=cArray.get(selectedCardPlace).getValue();
						}
						else{
							selectedCard=cArray.get(selectedCardPlace).getValue();
						}
					}
					else{
						print("CODE CHECK ### CODE CHECK", 1089);
						selectedCard=cArray.get(0).getValue();
						selectedCardSuit=3;
						selectedCardPlace=0;
					}
				}
			}
			else{
				print("Trying to play wrong suit", 1045);
				Toast.makeText(HeartsActivity.this, "Not a Valid Choice",  Toast.LENGTH_SHORT).show();
				return;

			}

		}
		else{
			if(!heartsBroken){
				print("Hearts not broken", 1101);
				Toast.makeText(HeartsActivity.this, "Hearts not broken",  Toast.LENGTH_SHORT).show();

				return;
			}//we are playing the first card of the pile.
			if(selectedCard==0){
				selectedCard=cArray.get(selectedCardPlace).getValue();
				//pile.add((cArray.get(selectedCardPlace)));
				selectedCardSuit=3;
			}
			else{
				if(selectedCardSuit==3){
					selectedCardPlace++;
					if(selectedCardPlace>=cArray.size()){
						selectedCardPlace=0;
						selectedCard=cArray.get(selectedCardPlace).getValue();
					}
					else{
						selectedCard=cArray.get(selectedCardPlace).getValue();
					}
				}
				else{
					selectedCard=cArray.get(0).getValue();
					selectedCardSuit=3;
					selectedCardPlace=0;
				}
			}
		}
		
		cardToPlay=cArray.get(selectedCardPlace);
		bottomText.setText(cardToString(cardToPlay));
	}
	public String cardToString(card c){
		int value = c.getValue();
		String sValue = "";
		
		int suit = c.getSuit();
		String sSuit="";
		switch(suit){
		case 0:
			sSuit="Clubs";
			break;
		case 1:
			sSuit="Diamonds";
			break;
		case 2:
			sSuit="Spades";
			break;
		case 3: 
			sSuit="Hearts";
			break;
		}
		
		switch (value){
			case 1:
				sValue="Ace";
				break;
			case 2: 
				sValue="Two";
				break;
			case 3:
				sValue="Three";
				break;
			case 4:
				sValue="Four";
				break;
			case 5:
				sValue="Five";
				break;
			case 6:
				sValue="Six";
				break;
			case 7:
				sValue="Seven";
				break;
			case 8:
				sValue="Eight";
				break;
			case 9:
				sValue="Nine";
				break;
			case 10:
				sValue="Ten";
				break;
			case 11:
				sValue="Jack";
				break;
			case 12:
				sValue="Queen";
				break;
			case 13:
				sValue="King";
				break;
			}
		return sValue+" of "+sSuit;
		
	}





}







/**
public class HeartsActivity extends Activity {
	private static final String TAG = "Hearts";

	//private Random mRnd = new Random();
	public card[] deck=new card[52];
	public String name;
	public Player p1;
	public Player p2;
	public Player p3;
	public Player p4;
	public Player curPlayer;
	public card[] pile = new card[4];
	public int pileI=0;
	public int playState=0;
	public playState Thegame;
	public LinearLayout topGrid;
    public EditText et1;
    public boolean playing=false;
    public boolean heartsBroken;
    public int round=0;
    public int count=0;
    public int players=4;
    public int difficulty=1;
    public EditText tCount;
    public TextView output1;
    //Player 1
    public TextView p1Clubs;
    public TextView p1Diamonds;
    public TextView p1Spades;
    public TextView p1Hearts;


    
    //Booleans for setting game states

	//Custom Buttons for Player one and two
    Button curColorButtonP1;
    Button curColorButtonP2;
    //Button used as a global place holder in function fixSides()
	Button fixSidesButton;
	int size;
	int hsize;
	int wsize;
    public void print(String i, int j){
	    System.out.println(i+"  "+j);
	
	}
	///** Called when the activity is first created. */
/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//Intent menuIntent  =new Intent(this, game.shad.tempus.hearts.mainMenu.class);
		//startActivity(menuIntent);


    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	Log.d(TAG, "return request");
    	print("return", 107);
    	super.onActivityResult(requestCode, resultCode, data);
    	Log.d(TAG, "result Code "+resultCode);
    	if(resultCode==1){
	     	String extraData=data.getStringExtra("picture");
    	}
    }
    
   


	

*/
    
	

	
