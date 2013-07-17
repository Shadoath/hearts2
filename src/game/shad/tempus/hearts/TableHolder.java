package game.shad.tempus.hearts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class TableHolder extends SurfaceView implements Callback, OnTouchListener{
	public static final String TAG = "Hearts--TableHolder";

	
    private OldDeck deck;
    private Card Card;
    private Card firstCardTouched;
    private int screenWidth;
    private int screenHeight;
    private int position=0;
	private SurfaceHolder surfaceHolder;
	private int initialX = 0, initialY = 0;	//the first point in a swipe or touch gesture
	private float t1x, t1y, t2x, t2y; // the initial coordinates for touch 1 and touch 2 when the player begins to pinch-zoom
    private Context mContext;
	private Game game;

    private boolean full=false;
	public boolean initialized = false; //made true on surfaceCreated()

    //Holds players deck, class to call for updates about deck and drawing the deck
   
    public TableHolder(Context context, Game game, int sW, int sH){
    	super(context);
        this.mContext=context;
        this.game = game;

        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        this.screenWidth = sW;
        this.screenHeight = sH;
        this.deck = new OldDeck();
        Log.d(TAG, "ScreenWidth="+screenWidth);
        Log.d(TAG, "screenHeight="+screenHeight);
        addBlankCards();
    }
    
    
    //protected void onMeasure(int width, int height){
    //    setMeasuredDimension(measureWidth(width),measureHeight(height));
    //}

	
    public Card getCard(int i){
        return this.deck.getCard(i);
    }
    
    public void addDeck(OldDeck deck){
        this.deck = deck;
    }
    

    public void addCard(Card c){
    	Log.d(TAG, "CARD added to table, "+c.name);
    	this.deck.removeCardAtIndex(position);
    	this.deck.addCardAtIndex(position, c);
    	this.position++;
    	postInvalidate();
    }
    public void removeAll(){
    	this.deck.clearALL();
    }
    
    public void addBlankCards(){
    	this.deck.clearALL();
    	this.position=0;
    	int i = 0;
    	while(i  < 4){
	        this.deck.addCard(new Card(1, 0, game));
	        i++;
    	}
        
      
    }
    @Override
    protected void onDraw(Canvas canvas){
    	super.onDraw(canvas);
//       Log.d(TAG, "painting, deck size= "+deck.getSize());
        full=false;
        int cardWidth=(int) (screenWidth/4);
        for (int i=0;i<this.deck.getSize();i++){
    		Card c=this.deck.getCard(i);
    		c.resizeBitmap(cardWidth, screenHeight);
    		c.setCoords(cardWidth*(i), 0, cardWidth+cardWidth*(i), screenHeight);
    		c.draw(canvas);
        	
        }
    }
    
    
    public void updateDeck(OldDeck deck){
        this.deck = deck;
    }
    
    public void updateCurrentCard(Card Card){
        this.Card = Card;
    }

	public Rect getBounds() {
		return new Rect(0, 0, this.screenWidth, this.screenHeight);

	}  
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surface Created");
		initialized = true;
		setOnTouchListener(this);
		game.updateTH();
			
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surface Destroyed");
		initialized = false;
			
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
					for(Card card : this.deck.getDeck()){
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
		return true;
	}

    

}
