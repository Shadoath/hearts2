package game.shad.tempus.hearts;

import java.util.ArrayList;
import java.util.Arrays;

import game.shad.tempus.hearts.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    
    public TextView playStateView;
    public TextView roundView;

    public Button p1ClubsB;
    public Button p1DiamondsB;
    public Button p1SpadesB;
    public Button p1HeartsB;
    public EditText tableCard1;
    public EditText tableCard2;
    public EditText tableCard3;
    public EditText tableCard4;
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
    public void print(String i, int j){
	    System.out.println(i+"  "+j);
	
	}
    public void firstStart() {
		p1Clubs = (TextView) findViewById(R.id.p1Clubs);
    	p1Diamonds = (TextView) findViewById(R.id.p1Diamonds);
    	p1Spades = (TextView) findViewById(R.id.p1Spades);
    	p1Hearts = (TextView) findViewById(R.id.p1Hearts);
    	
    	playStateView = (TextView) findViewById(R.id.playStateView);
    	roundView = (TextView) findViewById(R.id.roundView);

    	
        p1ClubsB =(Button)findViewById(R.id.p1clubsButton);
        p1DiamondsB=(Button)findViewById(R.id.p1clubsButton);
        p1SpadesB=(Button)findViewById(R.id.p1clubsButton);
        p1HeartsB=(Button)findViewById(R.id.p1clubsButton);
        
        tableCard1 = (EditText)findViewById(R.id.card1);
        tableCard2 = (EditText)findViewById(R.id.card2);
        tableCard3 = (EditText)findViewById(R.id.card3);
        tableCard4 = (EditText)findViewById(R.id.card4);
        selectedCardTextBox=(TextView)findViewById(R.id.selectedCardTextBox);
		Thegame = new playState();
		makeDeck();
		shuffle();
        deal();
        p1.sortHand();
        p2.sortHand();
        p3.sortHand();
        p4.sortHand();
        displayCards(p4);
        displayCards(p3);
        displayCards(p2);
        displayCards(p1);
        //p1.sortHighLow();
        trade();
        checkForTwoMethod();
        //print("start player is "+curPlayer.getName(), 71);

        //Arrays.sort(p1.gethand());
       //Arrays.sort(p2.gethand());
        //Arrays.sort(p3.gethand());
       // Arrays.sort(p4.gethand());
        
	
	}
	
	public String arraytoString(int[] theArray){
		String s="";
		for(int i=0; i<theArray.length;i++){
			if(theArray[i]!=0){
				s+=theArray[i]+", ";
			}
		}
		if(s!=""){
			s=s.substring(0, s.length()-2);
		}
		return s;
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
	 * lots of diamonds to p1
	 */
	public void shuffle(){

		int x=0;
		int z=50;
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
					if(loop>10&&!stop){
						r = 0;
						for(int q=0; q<deck.length;q++){
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
			
			print("suffle"+x, 174);
		}
	}
/**
 	* Create the and deals them out and then finds the two
	 * NEEDS SHUFFLE.
	 * */
	private void deal() {
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
		
		p1 = new Player(hand1, 0, 1, "YOU"); 
		p2 = new Player(hand2, 0, 2, "Bot 2");
		p3 = new Player(hand3, 0, 3, "Bot 3");
		p4 = new Player(hand4, 0, 4, "Bot 4");
		
		
	
		
	}
	public void checkForTwoMethod(){
		if(p1.checkForTwo()){
			curPlayer=p1;
			pile.add(p1.getClubs().get(0));
			p1.getClubs().remove(0);
			playState++;
			displayCards(p1);
			tableCard1.setText(selectedCardSuit+"-"+selectedCard);
			selectedCardTextBox.setText("You played the 2");
			print("game started by"+curPlayer.getrealName(), 489);

			playing=true;
		}
		else if(p2.checkForTwo()){
			print("p2 plays 2 of clubs", 273);
			curPlayer=p2;
			playTwoOfClubs();
			}
		else if(p3.checkForTwo()){
			print("p3 plays 2 of clubs", 278);

			curPlayer=p3;
			playTwoOfClubs();
		}
		else if(p4.checkForTwo()){
			print("p4 plays 2 of clubs", 284);
			curPlayer=p4;
			playTwoOfClubs();
		}
		print("current player="+curPlayer.getrealName(), 260);
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
	//euphoria 1:18
	public void onPlayCardPressed(View v){
		if(cardToPlay!=null&&playing){ //make sure we have a card selected and we have not already played.
			pile.add(cardToPlay);			
			playing=true;
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
			switch(playState){
				case 0:{
					tableCard1.setText(selectedCardSuit+"-"+selectedCard);
					break;
				}
				case 1:{
					tableCard2.setText(selectedCardSuit+"-"+selectedCard);
					break;
				}
				case 2:{
					tableCard3.setText(selectedCardSuit+"-"+selectedCard);
					break;
				}
				case 3:{
					tableCard4.setText(selectedCardSuit+"-"+selectedCard);
					break;
				}		
			}
			playState++;
			if(pile.size()==4){
				Toast.makeText(HeartsActivity.this, "Last card",  Toast.LENGTH_SHORT).show();
				pickUpHand(pile);
				
			}
			selectedCardPlace = 0;
			selectedCardSuit = -1;
			selectedCard = 0;
			displayCards(p1);
			cardToPlay=null;
			selectedCardTextBox.append("You played", 0, 0);
			curPlayer=nextPlayer(curPlayer);	//this is the error line

		}
		else{
			Toast.makeText(HeartsActivity.this, "Not your turn",  Toast.LENGTH_SHORT).show();

		}
		

	}
	
	
	public void onPlayStatePPPressed(View v){
		//ADMIN COMMAND!!!!!!;
		playState++;
		
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
		
		if(c.size()==0){
			p.setClubsVoid(true);
		}
		if(d.size()==0){
			p.setSpadesVoid(true);
		}
		if(s.size()==0){
			p.setDiamondsVoid(true);
		}
		if(h.size()==0){
			p.setHeartsVoid(true);
		}
		
		//should create switch here to display for correct player not just these.
		p1Clubs.setText(clubs);
		p1Diamonds.setText(diamonds);
		p1Spades.setText(spades);
		p1Hearts.setText(hearts);
		
	}

	public void onHintPressed(View v){
	    tableCard1.setText("");
	    tableCard2.setText("");;
	    tableCard3.setText("");;
	    tableCard4.setText("");;
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
			print("Player 1 trying to be player", 445);
			Toast.makeText(HeartsActivity.this, "Trying to play P1",  Toast.LENGTH_SHORT).show();
		}
		else
			GO();
		
	}
	
	public void GO(){
		print("GO() playState="+playState, 461);
		playStateView.setText("playState = "+playState);
		roundView.setText("Round= "+round+" Player="+curPlayer.getrealName());
		card r=(curPlayer.go(playState, round, pile, curPlayer));
		String rs=r.getSuit()+"-"+ r.getValue();
		pile.add(r);
		if(curPlayer.getSeat()==4&&pile.size()==3){
			playing=true;
			Toast.makeText(HeartsActivity.this, "Please play card",  Toast.LENGTH_SHORT).show();
		}
		else{
			switch(playState){
			case 0:
				curPlayer=nextPlayer(curPlayer);	//this is the error line
				tableCard1.setText(rs);
				break;
			case 1:
				curPlayer=nextPlayer(curPlayer);	//this is the error line
				tableCard2.setText(rs);
				break;
			case 2:
				curPlayer=nextPlayer(curPlayer);	//this is the error line
				tableCard3.setText(rs);
				break;
			case 3:
				tableCard4.setText(rs);
				if(pile.size()==4){
					pickUpHand(pile);
				}
				else{
					curPlayer=nextPlayer(curPlayer);	//this is the error line
				}
				break;
			case 4:
				playState=-1;
				print("playstate=0", 474);
				Toast.makeText(HeartsActivity.this, "playState=0",  Toast.LENGTH_SHORT).show();
	
				break;
			}
		}
		if(pile.size()!=0){
			playState++;
		}
	}
	
	public void playTwoOfClubs(){
		card r=(curPlayer.getClubs().get(0));
		if(r.getValue()==1){  //has the one and the two of clubs
			r=(curPlayer.getClubs().get(1));
			curPlayer.getClubs().remove(1);
		}
		else{
			curPlayer.getClubs().remove(0);
		}
		print("game started by"+curPlayer.getrealName(), 489);
		String rs=r.getSuit()+"-"+ r.getValue();
		pile.add(r);
		tableCard1.setText(rs);
		playState++;
		if(curPlayer.getSeat()==3){
			playing=true;
			selectedCardTextBox.setText("Your Turn");
		}
	}
	public void pickUpHand(ArrayList<card> p){
		print("pickUpHand()", 515);
		roundHands.add(p);
		int high = 0;
		int highSeat  = 0;
		int points = 0;
		int firstSuit=p.get(0).getSuit();
		for(int i=0;i<p.size();i++){		
			int curSuit=p.get(i).getSuit();
			int curCard =p.get(i).getValue();
			if(high<curCard){
				if(firstSuit==curSuit){  //does not work unless p1 starts.
					high=curCard;
					highSeat=p.get(i).getOwner().getSeat();//Seats are 1-4 unlike ALL ELSE but case statement is 0-3
				}
			}
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
		switch(highSeat){
		case 0:
			curPlayer=p1;
			curPlayer.addToScore(points);
			Toast.makeText(HeartsActivity.this, "p1 won, points="+points,  Toast.LENGTH_SHORT).show();
			playing=true;
			selectedCardTextBox.setText("playing="+playing);
			break;
		case 1:
			curPlayer=p2;
			curPlayer.addToScore(points);
			Toast.makeText(HeartsActivity.this, "p2 won, points="+points,  Toast.LENGTH_SHORT).show();
			break;
		case 2:
			curPlayer=p3;
			curPlayer.addToScore(points);
			Toast.makeText(HeartsActivity.this, "p3 won, points="+points,  Toast.LENGTH_SHORT).show();
			break;
		case 3: 
			curPlayer=p4;
			curPlayer.addToScore(points);
			Toast.makeText(HeartsActivity.this, "p4 won, points="+points,  Toast.LENGTH_SHORT).show();
			break;
		}	
		pile.clear();
		curPlayer.addDeckItem(p);
		playState=0;  //may be redundant....may be very important
		round++;  	  //ROUND INCREMENTER!!!
		Toast.makeText(HeartsActivity.this, "board will clear in 5",  Toast.LENGTH_SHORT).show();

		
	}
	
	public Player nextPlayer(Player p){
		switch(p.getSeat()){
			case 1:
				return p2;		
			case 2:
				return p3;		
			case 3:
				return p4;		
			case 4:
				return p1;
		}
		return null;
	}

	public void onp1ClubsButtonPressed(View v){
		//p1ClubsB.findFocus();
		//String c=(String) p1Clubs.getText();
		
		ArrayList<card> cArray=p1.getClubs();
		
		if(cArray.size()==0){
			return;
		}
		
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
		cardToPlay=cArray.get(selectedCardPlace);
		selectedCardTextBox.setText("suit="+selectedCardSuit+"  "+selectedCard);
	}
	public void onp1DiamondsButtonPressed(View v){
		//p1ClubsB.findFocus();
		String c=(String) p1Diamonds.getText();
		ArrayList<card> cArray=p1.getDiamonds();
		if(cArray.size()==0){
			return;
		}
		if(selectedCard==0){
			selectedCard=cArray.get(selectedCardPlace).getValue();
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
		cardToPlay=cArray.get(selectedCardPlace);
		selectedCardTextBox.setText("suit="+selectedCardSuit+"  "+selectedCard);
	}
	public void onp1SpadesButtonPressed(View v){
		//p1ClubsB.findFocus();
		String c=(String) p1Spades.getText();
		ArrayList<card> cArray=p1.getSpades();
		if(cArray.size()==0){
			return;
		}
		if(selectedCard==0){
			selectedCard=cArray.get(selectedCardPlace).getValue();
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
		cardToPlay=cArray.get(selectedCardPlace);
		selectedCardTextBox.setText("suit="+selectedCardSuit+"  "+selectedCard);
	}
	public void onp1HeartsButtonPressed(View v){
		//p1ClubsB.findFocus();
		String c=(String) p1Hearts.getText();
		ArrayList<card> cArray=p1.getHearts();
		if(cArray.size()==0){
			return;
		}
		if(selectedCard==0){
			selectedCard=cArray.get(selectedCardPlace).getValue();
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
		cardToPlay=cArray.get(selectedCardPlace);
		selectedCardTextBox.setText("suit="+selectedCardSuit+"  "+selectedCard);
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

    
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	Log.d(TAG, "return request");
    	print("return", 107);
    	super.onActivityResult(requestCode, resultCode, data);
    	Log.d(TAG, "result Code "+resultCode);
    	if(resultCode==1){
	     	String extraData=data.getStringExtra("picture");
    	}
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

	public void onPopPressed(View v){
		print("pop...", 144);
	}
	
	public void onPassPressed(View v){
		print("passing...", 148);
	}
	
*/
    
	

	
