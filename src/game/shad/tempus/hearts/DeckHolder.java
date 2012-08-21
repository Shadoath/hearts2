package game.shad.tempus.hearts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
    
    public void addDeck(Deck deck){
        this.deck = deck;
    }
    
    public void swipeLeft(){
    	if(position<=deck.getSize()+3)
    		position+=3;
    	this.refreshDrawableState();
    }
    public void swipeRight(){
    	if(position>=3)
    		position-=3;
    	this.refreshDrawableState();

    }
    
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        full=false;
        int displayed=0;
        int cardWidth=screenWidth/7;
        for (int i=position;i<deck.getSize();i++, displayed++){
        	if(i>=7+position){
        		//j+=60;
        		//i2=8;
        		full=true;
        	}
        	if(i==deck.getSize()&&deck.getSize()>displayed){
        		System.out.println("onDraw DH");
        	}
        	if(!full){
        		Card c=deck.getCard(i);
        		c.resizeBitmap(cardWidth, screenHeight);
        		c.setCoords(cardWidth*(i-position), 0, cardWidth+cardWidth*(i-position), screenHeight);
        		c.draw(canvas);
        	}
        }
    }
    
    
    public void updateDeck(Deck deck){
        this.deck = deck;
        this.refreshDrawableState();
    }
    
    public void updateCurrentCard(Card Card){
        this.Card = Card;
    }  
    

}
