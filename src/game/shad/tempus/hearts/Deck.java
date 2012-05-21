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
	
	public void removeCard(Card card){
		this.deck.remove(card);
	}
	
	public int getIndex(Card card){
		return this.deck.indexOf(card);
	}
	
	public int getSize(){
		return this.deck.size();
	}
	
}
