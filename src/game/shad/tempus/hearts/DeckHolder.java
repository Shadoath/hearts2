package game.shad.tempus.hearts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;

public class DeckHolder extends SurfaceView  implements Callback, OnTouchListener
{
	private static final String TAG = "Hearts--DeckHolder";
	private float xDistance, yDistance, lastX, lastY;
    private static final int SWIPE_MIN_DISTANCE = 5;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;

	
    private Deck deck;
    private Card Card;
    private Card firstCardTouched;
    private int screenWidth;
    private int screenHeight;
    private int position=0;
    private boolean full=false;
	public boolean initialized = false; //made true on surfaceCreated()
	private boolean doneTouching = false;
	private SurfaceHolder surfaceHolder;

	private Context mContext;
	private Game game;
	private int initialX = 0, initialY = 0;	//the first point in a swipe or touch gesture
	private float t1x, t1y, t2x, t2y; // the initial coordinates for touch 1 and touch 2 when the player begins to pinch-zoom
    //Holds players deck, class to call for updates about deck and drawing the deck

    public DeckHolder(Context context, Game game, int sW, int sH){
        super(context);
        this.mContext=context;
        this.game = game;
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
	    this.screenWidth = sW;
        this.screenHeight = sH;
        this.deck = new Deck();
        addBlankCards();
        

    }

    
    public void addBlankCards(){
    	this.deck.clearALL();
    	this.position=0;
    	int i = 0;
    	while(i  < 12){
	        this.deck.addCard(new Card(0,1, game));
	        i++;
    	}
    }
    
    public Card getCard(int i){
        return this.deck.getCard(i);
    }
    
    public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	public Rect getBounds(){
		return new Rect(0, this.screenHeight, this.screenWidth, this.screenHeight);
	}

	/**
	 * Clears the deck then adds a new one.
	 * @param deck: new deck to be drawn in this view.
	 */
	public void addDeck(Deck deck){
		this.deck.clearALL();
        this.deck = deck;
    }
 
    
    public void swipeRight(){
    	if(getPosition()<deck.getSize()+1)
    		setPosition(getPosition() + 1);
    	else
    		setPosition(0);
    }
    public void swipeLeft(){
    	if(getPosition()>=1)
    		setPosition(getPosition() - 1);
    	else{
    		setPosition(deck.getSize()-1);
    	}

    }
    public void addCard(Card c){
    	this.deck.addCard(c);
    }
    public void removeAll(){
    	this.deck.clearALL();
    }
    
    @Override
    protected void onDraw(Canvas canvas){
    	//TODO cards that were just traded light up for 5 secs.
    	//TODO need to redraw cards with correct images/symbol.
        super.onDraw(canvas);
        Log.d(TAG, "onDraw DH");
        setInView(false);
        if(deck.getSize()<8){
        	setPosition(0);
        }
        full=false;
        canvas.drawColor(Color.BLACK);
        int displayed=0;
       	int cardWidth=screenWidth/7;
       	int i=getPosition();
       	int loop=0;
       	while(!full){
        	if(displayed>7){
        		//j+=60;
        		//i2=8;
        		full=true;
        	}
        	if(!full){
        		if(i>=deck.getSize()&&deck.getSize()>7){
        			loop=i;
        			i=0;
        		}
        		if(i>=deck.getSize()){
        			break;
        		}
        		if(i<0){
        			i=deck.getSize()+i;
        		}
        		
        		Card c=deck.getCard(i);
        		c.inView=true;
        		c.resizeBitmap(cardWidth, screenHeight);
        		c.setCoords(cardWidth*((i+loop)-getPosition()), 0, cardWidth+cardWidth*((i+loop)-getPosition()), screenHeight);
        		c.draw(canvas);
           		i++;
           		displayed++;
        	}
        }
       	clearCardCoords();
       	
    }
    
    private void setInView(boolean b){
    	for(Card c :deck.getDeck()){
    		c.inView=b;
    	}
    }
    
    private void clearCardCoords(){
    	for(Card c :deck.getDeck()){
    		if(!c.inView)
    			c.setCoords(0, 0, 0, 0);
    	}
   
    }
    public void updateDeck(Deck deck){
        this.deck = deck;
        refreshDrawableState();
    }
    
    public Deck getDeck(){
        return this.deck;
    }
    
    public void updateCurrentCard(Card Card){
        this.Card = Card;
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
		this.deck = game.p1.getDeck();
		game.updateDH();
			
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surface Created");
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
		    			game.deckViewTouched(initialX, initialY);  //record first touch location
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
