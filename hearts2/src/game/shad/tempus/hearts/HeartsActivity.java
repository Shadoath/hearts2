package game.shad.tempus.hearts;

import java.util.ArrayList;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class HeartsActivity extends Activity {
	
	//current issues
	//the pickup pile method needs to correctly pick who wins.  using when it was played needs aditional playstate check to confirm seat.
	//playing someone elses cards....needs testing
	// when doing the Play2clubs can play Ace instead due to getting the first item in array
	//
	public String name;
	public card[] deck=new card[53];
	public card[] deckCards=new card[4];
    public ArrayList<card> pile=new ArrayList<card>();
    public ArrayList<ArrayList<card>> roundHands= new ArrayList<ArrayList<card>>(); 
	public Player p1;
	public Player p2;
	public Player p3;
	public Player p4;
	public Player curPlayer;
	public card cardToPlay;
	//public card[] pile = new card[4];
	public int pileI=0;
	public int playState=0;
	public playState Thegame;
	public int selectedCard=0;
	public int selectedCardSuit=-1;
	public int selectedCardPlace=0;
	
    public EditText et1;
    public TextView selectedCardTextBox;
    //Booleans for setting game states
    public boolean playing=false;  //initialized to false but set true during check for 2
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
    
    public TextView roundView;
    
    public Button p2Main;
    public Button p3Main;
    public Button p4Main;
    
    public Button p1ClubsB;
    public Button p1DiamondsB;
    public Button p1SpadesB;
    public Button p1HeartsB;
    
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
    

    //Button used as a global place holder in function fixSides()
	Button fixSidesButton;
	int size;
	int hsize;
	int wsize;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.table);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(new heartsPanel(this));
        
        //p1 =new Player();
        
        setContentView(R.layout.table);
        firstStart();
        
    }
   

	@Override
	public void onResume(){
		super.onResume();
		bottomText.setText("curPlayer is "+curPlayer.getRealName());
		if(pile.size()>=1){
			bottomText2.setText(""+pile.get(pile.size()-1).getOwner().getRealName()+" played last");
			
			}
		else{
			bottomText2.setText(curPlayer.getRealName()+" needs to start");
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
     * Should only be called from OnCreate(); and OnRestart();
     */

    public void firstStart() {
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

        p2Main =(Button)findViewById(R.id.p2Main);
        p3Main =(Button)findViewById(R.id.p3Main);
        p4Main =(Button)findViewById(R.id.p4Main);

	    p2Main.setBackgroundColor(Color.RED);
	    p3Main.setBackgroundColor(Color.BLUE);
	    p4Main.setBackgroundColor(Color.YELLOW);

    	
    	
        p1ClubsB =(Button)findViewById(R.id.p1clubsButton);
        p1DiamondsB=(Button)findViewById(R.id.p1clubsButton);
        p1SpadesB=(Button)findViewById(R.id.p1clubsButton);
        p1HeartsB=(Button)findViewById(R.id.p1clubsButton);
        
        tableCard1 = (EditText)findViewById(R.id.card1);
        tableCard2 = (EditText)findViewById(R.id.card2);
        tableCard3 = (EditText)findViewById(R.id.card3);
        tableCard4 = (EditText)findViewById(R.id.card4);
        selectedCardTextBox=(TextView)findViewById(R.id.selectedCardTextBox);
        
        p1TR= (LinearLayout) findViewById(R.id.player1);
        p2TR= (TableRow) findViewById(R.id.player2);
        p3TR= (TableRow) findViewById(R.id.player3);
        p4TR= (TableRow) findViewById(R.id.player4);

        //this could be a method to create a new hand.
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
        checkForTwoMethod();
        displayCards(p1);

	}
	 
    public void start(){ 	
    	//p1.set;
    	print("cp", 89);

	
	}
    /**
     * creates 52 cards 13 of each suit.
     */
    public void makeDeck() {    	
		for(int suit=0;suit<4;suit++){			
			for(int value=1;value<14;value++){
				int x=0; //to be determined
				int y=0; //to be determined
				card cd = new card(x, y, value, suit, null, Thegame);
				deck[suit*13+value]=cd;
			}

		}
		
	}
	/**
	 * WOULD NOT BET LIFE ON THIS SHUFFLE!!!
	 * Probably a very uneven shuffle.
	 */
	public void shuffle(){

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
			
			print("suffle"+x, 249);
		}
	}
/**
 	* Create the and deals them out and then finds the two
	 * NEEDS SHUFFLE.
	 * */
	public void deal() {
		print("dealing", 283);
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
		
		if(p1==null){//first Start
			p1 = new Player(hand1, 0, 1, "YOU"); 
			p2 = new Player(hand2, 0, 2, "Bot 2");
			p3 = new Player(hand3, 0, 3, "Bot 3");
			p4 = new Player(hand4, 0, 4, "Bot 4");
			//p2.setColor(Color.RED);
		}
		else{//else new round and New cards are dealt
			p1.sethand(hand1);
			p2.sethand(hand2);
			p3.sethand(hand3);
			p4.sethand(hand4);

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
			tableCard1.setText(selectedCardSuit+"-"+selectedCard);
			selectedCardTextBox.setText("You played the 2");
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
		print("current player="+curPlayer.getRealName(), 349);
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
			switch(p1.getState()){
				case 1:{
					clearTableCards();
					tableCard1.setText(selectedCardSuit+"-"+selectedCard);
					break;
				}
				case 2:{
					tableCard2.setText(selectedCardSuit+"-"+selectedCard);
					break;
				}
				case 3:{
					tableCard3.setText(selectedCardSuit+"-"+selectedCard);
					break;
				}
				case 4:{
					tableCard4.setText(selectedCardSuit+"-"+selectedCard);
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
			
			selectedCardPlace = 0;
			selectedCardSuit = -1;
			selectedCard = 0;
			displayCards(p1);
			cardToPlay=null;
			selectedCardTextBox.setText(selectedCardSuit+" "+ selectedCard);
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
	    tableCard1.setText("");
	    tableCard2.setText("");
	    tableCard3.setText("");
	    tableCard4.setText("");
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
		print("GO()",  581);
		bottomText2.setText(curPlayer.getRealName()+" just Played");
		roundView.setText("Round= "+round);
		card r=(curPlayer.go(round, pile, curPlayer));
		String rs=r.getSuit()+"-"+ r.getValue();
		pile.add(r);
		if(curPlayer.getSeat()==4&&pile.size()==3){
			if(playing){
				print("redundant call to set playing=true", 589);
			}
			playing=true;
			Toast.makeText(HeartsActivity.this, "Please play card",  Toast.LENGTH_SHORT).show();
		}
		setCardText(curPlayer.getState(), rs);//ADVANCES THE CUR PLAYER
	}
	
	/**
	 * Plays the selected card and advances the curPlayer
	 * @param p seat of person who is playing a card
	 * @param rs the string to be set to the layout.
	 */
	public void setCardText(int p, String rs){
		switch(p){//is is player state 1-4
		case 1:
			clearTableCards();
			curPlayer=nextPlayer(curPlayer);	//this is the error line
			
			tableCard1.setText(rs);
			break;
			
		case 2:
			curPlayer=nextPlayer(curPlayer);	//this is the error line
			tableCard2.setText(rs);
			break;
		case 3:
			curPlayer=nextPlayer(curPlayer);	//this is the error line
			tableCard3.setText(rs);
			break;
		case 4:
			tableCard4.setText(rs);
			if(pile.size()==4){
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
		String rs=r.getSuit()+"-"+ r.getValue();
		pile.add(r);
		bottomText2.setText(curPlayer.getRealName()+" played the 2 of Clubs");
		setCardText(curPlayer.getState(), rs);
		//playState++; no longer used
		if(curPlayer.getSeat()==1){
			playing=true;
			if(playing){
				print("redundant call to set playing=true", 663);
			}
			selectedCardTextBox.setText("Your Turn");
		}
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
			curPlayer=p1;
			curPlayer.addToScore(points);
	        p1tvScore.setText("P1 has "+curPlayer.getScore()+" points");

			//Toast.makeText(HeartsActivity.this, "p1 won, points="+points,  Toast.LENGTH_SHORT).show();
			playing=true;
			bottomText2.setText("You won, points="+points);

			selectedCardTextBox.setText("Pick a Start Card");
			break;
		case 2:
			curPlayer=p2;
			curPlayer.addToScore(points);
	        p2tvScore.setText("P2 has "+curPlayer.getScore()+" points");

			bottomText2.setText("p2 won, points="+points);
			//Toast.makeText(HeartsActivity.this, "p2 won, points="+points,  Toast.LENGTH_SHORT).show();
			break;
		case 3:
			curPlayer=p3;
			curPlayer.addToScore(points);
	        p3tvScore.setText("P3 has "+curPlayer.getScore()+" points");

			bottomText2.setText("p3 won, points="+points);
			//Toast.makeText(HeartsActivity.this, "p3 won, points="+points,  Toast.LENGTH_SHORT).show();
			break;
		case 4: 
			curPlayer=p4;
			curPlayer.addToScore(points);
	        p4tvScore.setText("P4 has "+curPlayer.getScore()+" points");
			bottomText2.setText("p4 won, points="+points);
			//Toast.makeText(HeartsActivity.this, "p4 won, points="+points,  Toast.LENGTH_SHORT).show();
			break;
		}	
		print("pickUpHand() for "+curPlayer.getRealName(), 770);
		curPlayer.addDeckItem(pile);


		pile.clear();
		setPlayState(curPlayer);  //for next round.


		count++;	//cards left counter
		if(count==13){//Done playing need new cards.
			Toast.makeText(HeartsActivity.this, "New round!",  Toast.LENGTH_SHORT).show();

			count=0;
			round++;  	//ROUND INCREMENTER!!!
			//TODO Point check see if anyone has 100 yet 
			shuffle();
	        deal();
	        p1.addToTotalScore(p1.getScore());
	        p1.sortHand();
	        p2.sortHand();
	        p3.sortHand();
	        p4.sortHand();
	        trade();  //ha ha more like set who to trade too.
	        voidCheck();
	        checkForTwoMethod();
	        displayCards(p1);

			
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
			p1TR.setBackgroundColor(C);
				playing=true;
				return p1;
		}
		return null;
	}

	public boolean canPlaySuit(Player p, int i){
		switch (i){
		case 0:
			return(p.voidClubs);
		case 2:
			return (p.voidDiamonds);
		case 3:
			return (p.voidSpades);
		case 4:
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
		selectedCardTextBox.setText("suit="+selectedCardSuit+"  "+selectedCard);
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
		selectedCardTextBox.setText("suit="+selectedCardSuit+"  "+selectedCard);
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

			}
		}
		else{//we are playing the first card of the pile.
			if(selectedCard==0){
				selectedCard=cArray.get(selectedCardPlace).getValue();
				//pile.add((cArray.get(selectedCardPlace)));
				selectedCardSuit=2;
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
					selectedCardSuit=2;
					selectedCardPlace=0;
				}
			}
		}
		cardToPlay=cArray.get(selectedCardPlace);
		selectedCardTextBox.setText("suit="+selectedCardSuit+"  "+selectedCard);
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
		selectedCardTextBox.setText("suit="+selectedCardSuit+"  "+selectedCard);
	}
	public void cardToString(){
		
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
        //p1 =new Player();
        
        setContentView(R.layout.table);
        firstStart();
        /*
        if(!playing){
        	setContentView(R.layout.main);
        }
        /

        
    }
    protected void firstStart() {
		
		Thegame=new playState();
		makeDeck();
		//shuffle
        deal();
        print("start player is "+curPlayer.getName(), 71);

        Arrays.sort(p1.gethand());
        Arrays.sort(p2.gethand());
        Arrays.sort(p3.gethand());
        Arrays.sort(p4.gethand());
        

        playing=true;
        p1.gethand();
        
        //p1.getDeck().sortHand();
        start();

	
	}
	
    
    private void start(){ 	
    	//p1.setsky
    	print("cp", 89);
        p1Clubs =(TextView)findViewById(R.id.p1clubsButton);
        p1Diamonds=(TextView)findViewById(R.id.p1clubsButton);
        p1Spades=(TextView)findViewById(R.id.p1clubsButton);
        p1Hearts=(TextView)findViewById(R.id.p1clubsButton);
        p1Clubs.setText(p1.getDeck().clubsString.toString());
        
        
        
        //p1Diamonds
        //p1Spades=
        //p1Hearts
		//if(round<3){
		//	trade();	//ignoring for now.
		//}
	
	}
    
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	Log.d(TAG, "return request");
    	print("return", 107);
    	super.onActivityResult(requestCode, resultCode, data);
    	Log.d(TAG, "result Code "+resultCode);
    	if(resultCode==1){
	     	String extraData=data.getStringExtra("picture");
    	}
    }
    
    private void laycard(card card) {
		//topGrid=(LinearLayout) findViewById(R.id.table);
		
		// TODO Auto-generated method stub
		
	}
    public void onNextButtonPressed(View v){
    	playState++;
    	Intent nextIntent  =new Intent(this, game.shad.tempus.hearts.playState.class);
    	nextIntent.putExtra("ps", playState);
    	nextIntent.putExtra("re", count);
    	startActivityForResult(nextIntent, count);
    	//Thegame.nextState(playState);
        // startActivity(nextIntent);
    	
    	count++;
    }
    
    
	//could be removed if it can be taken care of int the card class
    public void drawcard(Canvas g, card card, int x, int y) {
    	   int cx;    // x-coord of upper left corner of the card inside cardsImage
    	   int cy;    // y-coord of upper left corner of the card inside cardsImage
    	   if (card == null) {
    	      cy = 4*123;   // coords for a face-down card.
    	      cx = 2*79;
    	   }
    	   else {
    	      cx = (card.getValue())*79;
    	      switch (card.getSuit()) {
    	      case card.CLUBS:    
    	         cy = 0; 
    	         break;
    	      case card.DIAMONDS: 
    	         cy = 123; 
    	         break;
    	      case card.HEARTS:   
    	         cy = 2*123; 
    	         break;
    	      default:  // spades   
    	         cy = 3*123; 
    	         break;
    	      }
    	   }
    	   //Bitmap  bitmap = Bitmap.createBitmap( topGrid.getWidth(), topGrid.getHeight(), Bitmap.Config.ARGB_8888);
    	   //Canvas canvas = new Canvas(bitmap);
    	   //view.draw(canvas); 
    	   //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos); 
    	   //g.drawImage(Images,x,y,x+79,y+123,cx,cy,cx+79,cy+123,this);
    	}
    /**
     * Find the player who has the 2 of Clubs
     * Return them and set states for other players
     * @return
     
	public void onPopPressed(View v){
		print("pop...", 144);
	}
	
	public void onPassPressed(View v){
		print("passing...", 148);
	}
	protected Player findTwo() {
		if(p1.checkForTwo()){
			p1.state.setState(1);
			p2.state.setState(2);
			p3.state.setState(3);
			p4.state.setState(4);
			return p1;
		}
		else if(p2.checkForTwo()){
			p1.state.setState(4);
			p2.state.setState(1);
			p3.state.setState(2);
			p4.state.setState(3);
			return p2;
		}
		else if(p3.checkForTwo()){
			p1.state.setState(3);
			p2.state.setState(4);
			p3.state.setState(1);
			p4.state.setState(2);
			return p3;
		}
		else if(p4.checkForTwo()){
			p1.state.setState(2);
			p2.state.setState(3);
			p3.state.setState(4);
			p4.state.setState(1);
			return p4;
		}
		
		return null;
		
		
	}



	private void trade() {
		
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
		}
		
		
	}


/**
 * Create the and deals them out and then finds the two
 * NEEDS SHUFFLE.
 
	private void deal() {
    	card[] hand1 = new card[14];
    	card[] hand2 = new card[14];
    	card[] hand3 = new card[14];
    	card[] hand4 = new card[14];
 
		for(int i=0;i<13;i++){//could be buggy
			int d1=i;
			int d2=13+i;
			int d3=26+i;
			int d4=39+i;
			hand1[i]=deck[d1];
			hand2[i]=deck[d2];
			hand3[i]=deck[d3];
			hand4[i]=deck[d4];
		}
		
		p1 = new Player(hand1, 0, 1, name); 
		p2 = new Player(hand2, 0, 2, "Bot 2");
		p3 = new Player(hand3, 0, 3, "Bot 3");
		p4 = new Player(hand4, 0, 4, "Bot 4");
		
		
	
		if(p1.checkForTwo()){
			curPlayer=p1;
		}
		else if(p2.checkForTwo()){
			curPlayer=p2;
		}
		else if(p3.checkForTwo()){
			curPlayer=p3;
		}
		else if(p4.checkForTwo()){
			curPlayer=p4;
		}
		}
		


	private void makeDeck() {
    	
		for(int suit=0;suit<4;suit++){			
			for(int value=0;value<13;value++){
				int x=0; //to be determined
				int y=0; //to be determined
				card cd = new card(x, y, value, suit);
				deck[suit*13+value]=cd;
			}

		}
		
	}



	public void onStartPressed(View v){
		EditText et = (EditText) findViewById(R.id.name);
		name=et.getText().toString();
		print(name, 287);
		RadioGroup rg = (RadioGroup) findViewById(R.id.difficulty);
		RadioButton easy = (RadioButton) findViewById(R.id.easy);
		RadioButton medium = (RadioButton) findViewById(R.id.medium);
		RadioButton hard = (RadioButton) findViewById(R.id.hard);
		if(easy.isChecked()){
			difficulty=1;
		}
		else if(medium.isChecked()){
			difficulty=2;
		}
		else if(hard.isChecked()){
			difficulty=3;
		}	
		System.out.println("the game difficulty is set to "+difficulty);
		firstStart();
	}

	
		

*/
    
	

	
