package game.shad.tempus.hearts;
import i.win.sky.*;
import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

public class HeartsActivity extends Activity {
	//private Random mRnd = new Random();
	public Card[] deck=new Card[52];
	public Player p1;
	public Player p2;
	public Player p3;
	public Player p4;
	public Card[] pile;
	public int pileI=0;
	public int playState=0;
	public playState game = new playState();
	public GridLayout topGrid;
    public EditText et1;
    public boolean playing=true;
    public int round=0;
    public int count=0;
    public int players=4;
    public EditText tCount;
    public TextView output1;
    public TextView output2;

    //Booleans for setting game states

	//Custom Buttons for Player one and two
    Button curColorButtonP1;
    Button curColorButtonP2;
    //Button used as a global place holder in function fixSides()
	Button fixSidesButton;
	int size;
	int hsize;
	int wsize;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //p1 =new Player();
        setContentView(R.layout.main);
        chooseType();
        
    }
	protected void firststart(int i) {
		makeDeck();
		//shuffle
		//getName TODO
        deal();
        //findtwo
        
        //
        start();

	
	}
	
    
    private void start(){ 	
    	//p1.set
        setContentView(R.layout.table);
		if(round<3){
			trade();	//ignoring for now.
		}
		playState++;
		while(playing){
			game.play(); 
		}
		
		
	}
    private void layCard(Card card) {
		topGrid=(GridLayout) findViewById(R.id.table);
		
		// TODO Auto-generated method stub
		
	}
	//could be removed if it can be taken care of int the card class
    public void drawCard(Canvas g, Card card, int x, int y) {
    	   int cx;    // x-coord of upper left corner of the card inside cardsImage
    	   int cy;    // y-coord of upper left corner of the card inside cardsImage
    	   if (card == null) {
    	      cy = 4*123;   // coords for a face-down card.
    	      cx = 2*79;
    	   }
    	   else {
    	      cx = (card.getValue())*79;
    	      switch (card.getSuit()) {
    	      case Card.CLUBS:    
    	         cy = 0; 
    	         break;
    	      case Card.DIAMONDS: 
    	         cy = 123; 
    	         break;
    	      case Card.HEARTS:   
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

	private Player findTwo() {
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



	private void deal() {
    	Card[] hand1 = new Card[14];
    	Card[] hand2 = new Card[14];
    	Card[] hand3 = new Card[14];
    	Card[] hand4 = new Card[14];
 
		for(int i=0;i<13;i++){//could be buggy
			int j=i;
			hand1[i]=deck[j];
			hand2[i]=deck[1+j];
			hand3[i]=deck[2+j];
			hand4[i]=deck[3+j];
		}
		p1 = new Player(hand1, 0, 1, "Player 1"); //Set name Later or get it sooner?
		p2 = new Player(hand2, 0, 2, "Player 2");
		p3 = new Player(hand3, 0, 3, "Player 3");
		p4 = new Player(hand4, 0, 4, "Player 4");
		
		}
		
	



	private void makeDeck() {
    	
		for(int suit=0;suit<4;suit++){			
			for(int value=0;value<13;value++){
				int x=0; //to be determined
				int y=0; //to be determined
				Card cd = new Card(x, y, value, suit);
				deck[suit*13+value]=cd;
			}

		}
		
	}



	public void chooseType(){

		topGrid=(GridLayout) findViewById(R.id.grid1);
        et1=(EditText) findViewById(R.id.et1);
		//One player Button
        Button single=new Button(this);
		single.setText("1 Player");
		topGrid.addView(single, 0);
		single.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {
	        	firststart(1);
        		topGrid.removeAllViews();
        		et1.setText("Single Player");

        	}	
	    });
		//Two player Button
		Button twoPlay=new Button(this);
		twoPlay.setText("2 Player");
		topGrid.addView(twoPlay, 1);
		twoPlay.setOnClickListener(new OnClickListener() {  
	        public void onClick(View v) {
	        	firststart(2);
        		topGrid.removeAllViews();
        		et1.setText("Chill out! Try some one player first.");
	        }
	    });
    }

	public class playState {
		int curState =0;
		
		public int getState() {
			return curState;
		}
		public void setState(int c) {
			curState = c;
		}
		
		public void play(){
			switch (curState){
				case 1:
					play1();
					break;		
				case 2:
					play2();
					break;
				case 3:
					play3();
					break;
				case 4:
					play4();
					break;
			}
					
		}
		
		
		
		private void play4() {

			
		}
		private void play3() {

			
		}
		private void play2() {
			
			
		}
		private void play1() {
			Player CurPlayer=findTwo();
			//layCard((CurPlayer.playCard(2, Card.CLUBS)));
			//pile[pileI++]=CurPlayer.playCard(2, Card.CLUBS);
			playState++;
		}


    
	} 

	
}