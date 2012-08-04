package game.shad.tempus.hearts;


import java.util.ArrayList;

public class Deck {
	
	private ArrayList<card> deck;
	
	public Deck(){
		deck = new ArrayList<card>();
	}

	public void addCard(card card){
		this.deck.add(card);
	}
	
	public void removeCard(card card){
		this.deck.remove(card);
	}
	
	public int getIndex(card card){
		return this.deck.indexOf(card);
	}
	
	public int getSize(){
		return this.deck.size();
	}
	
	//returns a card at an index
	public card getCard(int index){
	    return this.deck.get(index);
	}
	
	//Gets the card in the middle, so we can show it in the graphic
	public card getMiddleCard(){
	    card c = null;
	    
	    if(getSize() >= 1){
	        c = getCard((int)Math.ceil(getSize()/2));
	    }else{
	        c = getCard(1);
	    }
	    
	    return c; 
	}
	
	//Get card to the left of an index (for graphics)
	public card getLeftCard(int index){
	    card c = null;
	    
	    if(index > 0)
	    {
	        c = this.getCard(index - 1);
	    }else{
	        c = this.getCard(0);
	    }
	    
	    return c;
	}
	
	//Get card to the right of an index (for graphics)
	public card getRightCard(int index){
            card c = null;
            
            if(index < this.getSize())
            {
                c = this.getCard(index + 1);
            }else{
                c = this.getCard(this.getSize());
            }
            
            return c;
        }
	
	
}
