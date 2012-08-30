package game.shad.tempus.hearts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class DeckHolder extends View
{

    private Deck deck;
    private Card Card;
    private int screenWidth;
    private int screenHeight;
    private int position=0;
    private boolean full=false;
    //Holds players deck, class to call for updates about deck and drawing the deck
   
    public DeckHolder(Context context, int sW, int sH){
        super(context);
        this.screenWidth = sW;
        this.screenHeight = sH;
        this.deck = new Deck();
    }
    
    //protected void onMeasure(int width, int height){
    //    setMeasuredDimension(measureWidth(width),measureHeight(height));
    //}
    
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
		return new Rect(0, this.screenHeight, this.screenWidth, this.screenHeight*2);
	}

	public void addDeck(Deck deck){
        this.deck = deck;
    }
    
    public void swipeLeft(){
    	if(getPosition()<deck.getSize()-3)
    		setPosition(getPosition() + 3);
    	else
    		setPosition(0);
    	invalidate();
    }
    public void swipeRight(){
    	if(getPosition()>=3)
    		setPosition(getPosition() - 3);
    	else{
    		setPosition(deck.getSize()-4);
    	}
    	invalidate();

    }
    public void addCard(Card c){
    	this.deck.addCard(c);
    }
    public void removeAll(){
    	this.deck.clearALL();
    }
    
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        full=false;
        int displayed=0;
        //TODO resize cards 
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
        		Card c=deck.getCard(i);
        		c.resizeBitmap(cardWidth, screenHeight);
        		c.setCoords(cardWidth*((i+loop)-getPosition()), 0, cardWidth+cardWidth*((i+loop)-getPosition()), screenHeight);
        		c.draw(canvas);
           		i++;
           		displayed++;
        	}
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
    

}
