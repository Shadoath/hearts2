package game.shad.tempus.hearts;


public class Player {
	Card[] hand;
	private Deck deck;
	private int seat=0; //position
	private String name;
	playState state;
	private int score=0;
	private int totalScore=0;
	private int passTo=0;
	
	
	
	
	public Player(Card[] hand, int score, int seat, String name){
		this.hand = hand;
		this.score = score;
		this.seat = seat;
		this.name = name;
	}
	
	public Card playCard(int value, int suit){
		for(int i=0;i<hand.length;i++){
			if(hand[i].getSuit()==suit&&hand[i].getValue()==value){
				return hand[i];
			}
		}
		return null;
	}
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	public void setSeat(int s) {
		seat=s;
	}
	public int getSeat() {
		return seat;
	}
	public void setValue(int c) {
		seat = c;
	}
	
	
	public void setDeck(Deck deck){
		this.deck = deck;
	}
	
	public Deck getDeck(){
		return deck;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return score;
	}
	
	public Card[] gethand() {
		return hand;
	}
	public void sethand(Card[] c) {
		hand=c;
	}
	public int getPass() {
		return passTo;
	}
	public void setPass(int passTo) {
		this.passTo=passTo;
	}

	public boolean checkForTwo(){
		for (int i=0;i<13;i++){
			if(hand[i].getValue()==(2)&&hand[i].getSuit()==('c')){
				return true;
			}
		}
		return false;
	}
}
