package game.shad.tempus.hearts;


import java.util.ArrayList;

public class Deck {
	
	private ArrayList<Card> deck;
	
	public Deck(){
		deck = new ArrayList<Card>();
	}

	public void addCard(Card card){
		this.deck.add(card);
	}
	
	public void addAllCards(Deck cards){
		for(int i = 0; i<cards.getSize();i++){
			this.deck.add(cards.getCard(i));
		}
	}
	
	public void removeCard(Card card){
		this.deck.remove(card);
	}
	
	public void removeCardAtIndex(int i){
		this.deck.remove(i);
	}
	
	public void addCardAtIndex(int index, Card card) {
		this.deck.add(index, card);
		
	}
	public void clearALL(){
		this.deck.clear();
	}
	
	public void cloneMe(){
		
		this.deck.clear();
	}
	
	
	public void updateDeck(Deck deck2){
		this.deck.clear();
		for(int i=0;i<deck.size();i++){
			this.deck.add(deck2.getCard(i));
		}
	}
	public int getIndex(Card card){
		
		return this.deck.indexOf(card);
	}
	
	public int getSize(){
		return this.deck.size();
	}
	
	//returns a card at an index
	public Card getCard(int index){
		return this.deck.get(index);
	}
	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}
	//Gets the card in the middle, so we can show it in the graphic
	public Card getMiddleCard(){
	    Card c = null;
	    
	    if(getSize() >= 1){
	        c = getCard((int)Math.ceil(getSize()/2));
	    }else{
	        c = getCard(1);
	    }
	    
	    return c; 
	}
	
	//Get card to the left of an index (for graphics)
	public Card getLeftCard(int index){
	    Card c = null;
	    
	    if(index > 0)
	    {
	        c = this.getCard(index - 1);
	    }else{
	        c = this.getCard(0);
	    }
	    
	    return c;
	}
	
	//Get card to the right of an index (for graphics)
	public Card getRightCard(int index){
            Card c = null;
            
            if(index < this.getSize())
            {
                c = this.getCard(index + 1);
            }else{
                c = this.getCard(this.getSize());
            }
            
            return c;
        }

	
	
	
}
