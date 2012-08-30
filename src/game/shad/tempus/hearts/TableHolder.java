package game.shad.tempus.hearts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

public class TableHolder extends View
{

    private Deck deck;
    private Card Card;
    private int screenWidth;
    private int screenHeight;
    private int position=0;
    private Context mContext;
    private boolean full=false;
    //Holds players deck, class to call for updates about deck and drawing the deck
   
    public TableHolder(Context context, int sW, int sH){
        super(context);
        this.mContext=context;
        this.screenWidth = sW;
        this.screenHeight = sH;
        this.deck = new Deck();
        addBlankCards();
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
    

    public void addCard(Card c){
    	this.deck.removeCardAtIndex(0);
    	this.deck.addCard(c);
    }
    public void removeAll(){
    	this.deck.clearALL();
    }
    
    public void addBlankCards(){
    	this.position=0;
        this.deck.addCard(new Card(0,0, mContext));
        this.deck.addCard(new Card(0,0, mContext));
        this.deck.addCard(new Card(0,0, mContext));
        this.deck.addCard(new Card(0,0, mContext));
      
    }
    
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        System.out.println("painting, deck size= "+deck.getSize());
        full=false;
        int cardWidth=(screenWidth/4);
        for (int i=0;i<deck.getSize();i++){
    		Card c=deck.getCard(i);
    		c.resizeBitmap(cardWidth, screenHeight);
    		c.setCoords(cardWidth*(i), 0, cardWidth+cardWidth*(i), screenHeight);
    		c.draw(canvas);
        	
        }
    }
    
    
    public void updateDeck(Deck deck){
        this.deck = deck;
        this.refreshDrawableState();
    }
    
    public void updateCurrentCard(Card Card){
        this.Card = Card;
    }

	public Rect getBounds() {
		return new Rect(0, 0, this.screenWidth, this.screenHeight);

	}  
    

}
