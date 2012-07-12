package game.backup;

import game.shad.tempus.hearts.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeartsActivity extends Activity {
	
	
	//public Card[] deck=new Card[52];
	public String name;
	public Player p1;
	public Player p2;
	public Player p3;
	public Player p4;
	public Player curPlayer;
	//public Card[] pile = new Card[4];
	public int pileI=0;
	public int playState=0;
	public playState Thegame;
	
    public EditText et1;
    //Booleans for setting game states
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
    }
    public void print(String i, int j){
	    System.out.println(i+"  "+j);
	
	}
}




/**
public class HeartsActivity extends Activity {
	private static final String TAG = "Hearts";

	//private Random mRnd = new Random();
	public Card[] deck=new Card[52];
	public String name;
	public Player p1;
	public Player p2;
	public Player p3;
	public Player p4;
	public Player curPlayer;
	public Card[] pile = new Card[4];
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
    
    private void layCard(Card card) {
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
    	Card[] hand1 = new Card[14];
    	Card[] hand2 = new Card[14];
    	Card[] hand3 = new Card[14];
    	Card[] hand4 = new Card[14];
 
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
				Card cd = new Card(x, y, value, suit);
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
    
	

	
