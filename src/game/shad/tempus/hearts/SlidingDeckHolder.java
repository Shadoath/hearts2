package game.shad.tempus.hearts;

import java.util.ArrayList;
import java.util.Iterator;

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

	
    private ArrayList<Card> deck;
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
       	
       	this.deck = new ArrayList<Card>();
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
        return this.deck.get(i);
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
	public void addDeck(ArrayList<Card> deck){
    	removeAllViews();
		cardViewSelected= new ArrayList<CardView>();
		Iterator<Card> it = deck.iterator();
		while(it.hasNext()){
			Card card = it.next();
			Log.d(TAG, "Card added to sliding deckholder="+card.name);
			CardView cView= new CardView(mContext, card, params);
			cView.setTag(card.name);

			cView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
					String t = v.getTag().toString();
					CardView cv = (CardView) v;
					Log.d(TAG, "tag="+t);
					game.slidingDeckViewTouched(cv.getCard());
					
				}
			});
			addView(cView);
		}
		
//			cView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					CardView cv = (CardView) v;
//					String t = cv.getTag().toString();
//					Log.d(TAG, "tag="+t);
//					Iterator<Card> it = self.deck.iterator();
//					while(it.hasNext()){
//						Card c = it.next();
//						Log.d(TAG, "card Tag="+c.name);
//						if(c.name.equals(t)){
//							game.slidingDeckViewTouched(c);
//							int cardsSelected = tradingViews.size();
//
//							if(game.trading){//Pick up to three cards
//								if(cardsSelected>0){
//									int cardInt = 0;
//					    			for(View view : tradingViews){
//										if(view.equals(cv)){//This one was picked, now remove it.
//											tradingViews.get(cardInt).setSelected(false);
//											tradingViews.remove(cardInt);	//Possible CME...
//											Log.d(TAG, "deselected a card ");
//											cv.resetCardImage();
//											return;
//										}
//					    				cardInt++;
//
//									}
//
//								}//did not unselect a card so we must be picking it...
//								cv.setSelected(true);
//								cv.setCardSelected(true);
//								if(cardsSelected<3){//Less than three selected so select this one.
//									tradingViews.add(cv);
//								}
//								else {//unselect the first one and add the new one.
//									tradingViews.get(0).setSelected(false);
//									tradingViews.remove(0);
//									tradingViews.add(cv);
//								}
//								cv.resetCardImage();
//								return;
//							}//Not trading lets see if the card picked is ok to play...
//							if(game.checkPlayability(c, game.p1)){
//								if(viewSelected==null){
//									cv.setSelected(true);
//									cv.resetCardImage();
//									viewSelected=(CardView) cv;
//								}
//								else{
//									viewSelected.setSelected(false);
//									cv.setSelected(true);
//									viewSelected=(CardView) cv;
//									
//								}
//								cv.resetCardImage();
//
//							}
//							
//						}
//					}
//
//				}
//
//			});

    }
 
    
    public void swipeRight(){
    	if(getPosition()<deck.size()+1)
    		setPosition(getPosition() + 1);
    	else
    		setPosition(0);
    }
    public void swipeLeft(){
    	if(getPosition()>=1)
    		setPosition(getPosition() - 1);
    	else{
    		setPosition(deck.size()-1);
    	}

    }
    public void addCard(Card c){
    	this.deck.add(c);
    }
    
    public void replaceCard(Card c){
    	this.deck.remove(c);
    	this.deck.add(c);
    }
    public void removeAll(){
		this.cardViewSelected= new ArrayList<CardView>();
    }
            
    public void setSelectedCard(Card c){
    	int count=getChildCount();
    	int i=0;
    	CardView toHighLightView = null;
    	while(i<count){
	    	if(c.name.equals(getChildAt(i).getTag())){
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
    	
    public ArrayList<Card> getDeck(){
        return this.deck;
    }
    



	

}
