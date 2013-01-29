package game.shad.tempus.hearts;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SlidingDeckHolder extends LinearLayout   
{
	private static final String TAG = "Hearts--DeckHolder";
	private float xDistance, yDistance, lastX, lastY;
    private static final int SWIPE_MIN_DISTANCE = 5;
    private static final int SWIPE_THRESHOLD_VELOCITY = 300;

	
    private Deck deck;
    private ArrayList<CardView> cardViewSelected;
    private ArrayList<CardView> tradingViews;
    private CardView viewSelected;
    private CardView cardImageView;
    private Card Card;
    private Card firstCardTouched;
    private LinearLayout.LayoutParams params;
    private int screenWidth;
    private int screenHeight;
    private int position=0;
    private int cardWidth=0;

	private SlidingDeckHolder self;
	private Context mContext;
	private Game game;
	private int initialX = 0, initialY = 0;	//the first point in a swipe or touch gesture
	private float t1x, t1y, t2x, t2y; // the initial coordinates for touch 1 and touch 2 when the player begins to pinch-zoom
    //Holds players deck, class to call for updates about deck and drawing the deck

    public SlidingDeckHolder(Context context, Game game, int sW, int sH){
        super(context);
        this.mContext=context;
        self=this;
        this.game = game;
	    this.screenWidth = sW;
        this.screenHeight = sH;
       	cardWidth=screenWidth/7;
       	params = new LinearLayout.LayoutParams(cardWidth, screenHeight);
       	params.setMargins(0, 0, 0, 0);
       	
       	this.deck = new Deck();
        
        cardViewSelected= new ArrayList<CardView>();
        tradingViews= new ArrayList<CardView>();
        //addBlankCards();
        //this.addCardViews(cardView);


    }
        
    public void addBlankCards(){
    	this.deck.clear();
    	int i = 0;
    	while(i  < 12){
    		ImageView cView= new ImageView(mContext);
			cView.setMaxHeight(screenHeight-10);
			cView.setMaxWidth(cardWidth-10);
			cView.setVisibility(View.VISIBLE);
			cView.setLayoutParams(params);
			cView.setImageBitmap(game.BlueBack);
			cView.setPadding(10,10,10,10);
			addView(cView);
	        i++;
    	}
    }
    
    public void addCardViews(ArrayList<View> cards){
    	for(View v: cards){
    		this.addView(v);
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
    	removeAllViews();
		cardViewSelected= new ArrayList<CardView>();
		for(Card c: deck.getDeck()){
			CardView cView= new CardView(mContext, c, params);
			cView.setTag(c.toString());
			cView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CardView cv = (CardView) v;
					String t = cv.getTag().toString();
					Log.d(TAG, "tag="+t);
					for(Card c: self.deck.getDeck()){
						Log.d(TAG, "card Tag="+c.toString());
						if(c.toString().equals(t)){
							game.slidingDeckViewTouched(c);
							boolean done = false;
							if(game.trading){//Pick up to three cards
								if(tradingViews.size()>0){
									int cardInt = 0;
					    			for(View view : tradingViews){
										if(view.equals(cv)){
											tradingViews.get(cardInt).setBackgroundColor(Color.BLACK);
											tradingViews.get(cardInt).setCardSelected(false);
											
											tradingViews.remove(cardInt);
											done=true;	//Already picked that one. Now it is unselected.
											break;
										}
					    				cardInt++;

									}
					    			if(done){
					    				Log.d(TAG, "deselected a card ");
										return;
					    			}
								}
								cv.setSelected(true);
								cv.setBackgroundColor(Color.YELLOW);
								int size = tradingViews.size();
								if(size<3){//Nothing picked yet
									tradingViews.add(cv);
									
								}
								else {
									tradingViews.get(0).setBackgroundColor(Color.BLACK);
									tradingViews.get(0).setSelected(false);
									tradingViews.get(0).setCardSelected(false);
									tradingViews.remove(0);
									tradingViews.add(cv);
								}

								return;
							}
							if(game.checkPlayability(c)){
								if(viewSelected==null){
									cv.setSelected(true);
									cv.setCardSelected(true);
									cv.setBackgroundColor(Color.YELLOW);
									viewSelected=(CardView) cv;
								}
								else{
									viewSelected.setSelected(false);
									viewSelected.setCardSelected(false);
									
									viewSelected.setBackgroundColor(Color.BLACK);
									cv.setBackgroundColor(Color.YELLOW);
									cv.setSelected(true);
									cv.setCardSelected(true);
									viewSelected=(CardView) cv;
									
								}
							}
							
						}
					}
				}
			});
			addView(cView);
		}
		this.deck=deck;
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
    
    public void replaceCard(Card c){
    	this.deck.removeCard(c);
    	this.deck.addCard(c);
    }
    public void removeAll(){
		this.cardViewSelected= new ArrayList<CardView>();
    }
            
    public void setSelectedCard(Card c){
    	int count=getChildCount();
    	int i=0;
    	CardView toHighLightView = null;
    	while(i<count){
	    	if(c.toString().equals(getChildAt(i).getTag())){
	    		toHighLightView=(CardView) getChildAt(i);
	    		Log.d(TAG, "view found, Setting background yellow");
	    		break;
	    	}
	    	i++;
		}
    	if(toHighLightView==null){
    		Log.d(TAG, "no View found for card!");
    		return;
    	}
    	
    	if(viewSelected==null){
    		toHighLightView.setSelected(true);
    		toHighLightView.setCardSelected(true);
    		toHighLightView.setBackgroundColor(Color.YELLOW);
			viewSelected=(CardView) toHighLightView;
		}
		else{
			viewSelected.setSelected(false);
			viewSelected.setCardSelected(false);
			viewSelected.setBackgroundColor(Color.BLACK);
			toHighLightView.setBackgroundColor(Color.YELLOW);
			toHighLightView.setSelected(true);
			toHighLightView.setCardSelected(true);
			viewSelected=(CardView) toHighLightView;
			
		}
    }
    	
    public Deck getDeck(){
        return this.deck;
    }
    



	

}
