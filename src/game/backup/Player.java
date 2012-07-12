package game.backup;


public class Player extends HeartsActivity{
	card[] hand;
	private Deck deck;
	private int seat=0; //position
	private String name;
	playState state;
	private int score=0;
	private int totalScore=0;
	private int passTo=0;
	
	
	
	
	public Player(card[] hand, int score, int seat, String name){
		this.hand = hand;
		this.score = score;
		this.seat = seat;
		this.name = name;
		this.deck = new Deck();
		//this.deck = 
		//deck.sortHand();

	}
	
	public card playcard(int value, int suit){
		//print("player cp", 25);
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
	
	
	public void setDeck(card[] d){
		this.deck = new Deck();
		//deck.
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
	
	public card[] gethand() {
		return hand;
	}
	public void sethand(card[] c) {
		hand=c;
	}
	public int getPass() {
		return passTo;
	}
	public void setPass(int passTo) {
		this.passTo=passTo;
	}

	public boolean checkForTwo(){
		int i;
		for (i=0;i<13;i++){
			if(hand[i].getValue()==(2)&&hand[i].getSuit()==(0)){
				i=15;
				return true;
				
			}

		}
		if(i==15){
			return true;
		}
		else{
			return false;
		}
	}

}
