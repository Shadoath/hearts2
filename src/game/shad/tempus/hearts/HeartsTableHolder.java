package game.shad.tempus.hearts;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HeartsTableHolder extends LinearLayout implements OnTouchListener{
	public static final String TAG = "Hearts--TableHolder";
	
    private ArrayList<Card> tableCards;
    private Card Card;
    private Card firstCardTouched;
    private int screenWidth;
    private int screenHeight;
    private int position=0;
	private SurfaceHolder surfaceHolder;
	private int initialX = 0, initialY = 0;	//the first point in a swipe or touch gesture
	private float t1x, t1y, t2x, t2y; // the initial coordinates for touch 1 and touch 2 when the player begins to pinch-zoom
    private Context context;
	private Game game;
	private Paint paint = new Paint();

    private boolean full=false;
	public boolean initialized = false; //made true on surfaceCreated()
    private LinearLayout.LayoutParams tableParams;
    private LinearLayout.LayoutParams cardParams;
    private int cardWidth=0;
    private int cardHeight=0;

    //Holds players deck, class to call for updates about deck and drawing the deck
   
    public HeartsTableHolder(Context context, Game game, int sW, int sH){
    	super(context);
        this.context=context;
        this.game = game;
        this.screenWidth = sW;
        this.screenHeight = sH;
		paint.setStyle(Style.FILL);
        Log.d(TAG, "Table Holder w="+sW+" h="+sH);
        this.tableCards = new ArrayList<Card>();
        cardWidth=screenWidth/4;
        cardHeight=(int) (screenHeight/1.1);
       	tableParams = new LinearLayout.LayoutParams(screenWidth, screenHeight);
       	cardParams = new LinearLayout.LayoutParams(cardWidth, cardHeight);
       	cardParams.setMargins(5, 5, 5, 5);

       	tableParams.setMargins(0, 0, 0, 0);
        Log.d(TAG, "ScreenWidth="+screenWidth);
        Log.d(TAG, "screenHeight="+screenHeight);
        addBlankCards();
    }
    
    public Card getCard(int i){
        return this.tableCards.get(i);
    }

    public void addCard(Card c){
    	Log.d(TAG, "CARD added to table, "+c.name);
    	Log.d(TAG, "Table card count ="+tableCards.size());
    	Log.d(TAG, "getTouchables ="+getTouchables().size());

//    	this.tableCards.removeCardAtIndex(position);
//    	this.tableCards.addCardAtIndex(position, c);
//    	this.position++;
//    	postInvalidate();
//    	CardView cView= new CardView(context, card, params);

    	
    	CardView cView= new CardView(context, c, cardParams);

		cView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				String t = v.getTag().toString();
				CardView cv = (CardView) v;
				Log.d(TAG, "tag="+t);
				game.slidingDeckViewTouched(cv);
				
			}
		});
		addView(cView);
    }
    
    public void removeAll(){
    	Log.d(TAG, "removeAll");
    	this.tableCards.clear();
    	removeAllViews();

    }
    
    public void addBlankCards(){
//    	this.tableCards.clear();;
//    	this.position=0;
//    	int i = 0;
//    	while(i  < 4){
//	        this.tableCards.addCard(new Card(1, 0, game, context));
//	        i++;
//    	}     
      Log.d(TAG, "addBlankCards() ");

    	removeAllViews();
    	this.tableCards.clear();
    	int i = 0;
    	while(i  < 4){
    		Card card = new Card(1, 0, game, context);
    		CardView cView= new CardView(context, card, cardParams);		
			addView(cView);
	        i++;
    	}   	
    }

	/**
	 * Clears the deck then adds a new one.
	 * @param deck: new deck to be drawn in this view.
	 */
	private void setDeck(ArrayList<Card> deck){
    	removeAllViews();
		Iterator<Card> it = deck.iterator();
		while(it.hasNext()){
			Card card = it.next();
			Log.d(TAG, "Card added to table ="+card.name);
			CardView cView= new CardView(context, card, cardParams);
			cView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
					String t = v.getTag().toString();
					CardView cv = (CardView) v;
					Log.d(TAG, "tag="+t);
					game.slidingDeckViewTouched(cv);					
				}
			});
			addView(cView);
		}
	}		
    
    public void updateCurrentCard(Card Card){
        this.Card = Card;
    }

	public Rect getBounds() {
		return new Rect(0, 0, this.screenWidth, this.screenHeight);
	}	
	
	private int touchID = -1; // the int identifier of the main touch, or -1 if there is no main touch
	@Override
	public boolean onTouch(View v, MotionEvent e) {
		Log.d(TAG, "Touching at x="+e.getX()+", y="+e.getY());
		if (touchID == -1) { //means first touch on the screen
			if (e.getActionMasked() == MotionEvent.ACTION_DOWN || e.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {//pointer down is the second touch/ first finger, second touch
				touchID = e.getActionIndex();	//Tack the first touch finger.
				t1x = (e.getX(touchID));
				t1y = (e.getY(touchID));
				initialX =  (int) t1x;
				initialY =  (int) t1y;
				if(firstCardTouched==null){
					for(Card card : this.tableCards){
				    	if(card.getBounds().contains(initialX, initialY)){
				    		firstCardTouched=card;  //record first touch location
				    	}
					}
				}				
				if (e.getPointerCount() == 1){	//if only one finger down, send touch event to game.
					//game.deckViewTouched((int)e.getX(), (int)e.getY());
					//touch(e.getX(), e.getY());	//Get a chip from the chip rack
				}
				else {	//there are two fingers touching, assume that player is zooming initialize the zoom variables.
					t2x = (e.getX(touchID+1));
					t2y = (e.getY(touchID+1));
					Log.d(TAG, "Touching Second Finger at x="+t2x+", y="+t2y);
				}
			}
		} else {	//first finger has touched screen
			if (e.getAction() == MotionEvent.ACTION_MOVE) {
				if (e.getPointerCount() == 2) {  //two fingers down,  zoom the game
				//zoom(e);
				}
				//Updates the chipheld position... or it should.
				//move(e.getX(touchID), e.getY(touchID));
			} 
		}
		if (e.getActionMasked() == MotionEvent.ACTION_UP || e.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
			Log.d(TAG, "Untouch at x="+e.getX()+", y="+e.getY());
			if (e.getActionIndex() == touchID) {
				touchID = -1;
				if(firstCardTouched!=null){
			    	if(firstCardTouched.getBounds().contains(initialX, initialY)){
		    			Log.d(TAG, "Player tapped a CARD");
		    			game.tableViewTouched(initialX, initialY);  //record first touch location
		    			firstCardTouched=null;		    		
			    	}				    	
				}
			}
			firstCardTouched=null;
			//unTouch(e.getX(touchID), e.getY(touchID), initialX, initialY);		
		} 
		//game.deckViewTouched((int)e.getX(), (int)e.getY());
		initialized = true;
		return true;
	}
}
