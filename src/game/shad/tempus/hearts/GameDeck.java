package game.shad.tempus.hearts;


import java.util.ArrayList;

import android.graphics.Color;
import android.util.Log;

public class GameDeck{
	public static final String TAG = "Hearts--GameDeck";

	public Card twoOfClubs;
	public Card queenOfSpades;
	public boolean hasQueen;
	ArrayList<Card> gameDeck;

	public GameDeck(Game game) {
		gameDeck = new ArrayList<Card>();
		// TODO Auto-generated constructor stub
	}
	
	
	public void shuffle(int type){
		Log.d(TAG, "shuffle");
		Log.d(TAG, "Deck size="+gameDeck.size());
//		gameDeck = this.getDeck();
		Log.d(TAG, "Deck size="+gameDeck.size());
		boolean stop = false;
		int x = 0;
		int z = 50;
		int j=0;
		int a=(int)  (Math.random()*7)+1;
		ArrayList<Card> deck2 = new ArrayList<Card>();
		ArrayList<Card> deck3 = new ArrayList<Card>();
		ArrayList<Card> deck4 = new ArrayList<Card>();
		switch(type){
		case 1:
				Log.d(TAG, "shuffle type 1");
				//New shuffle going in 8/16
				deck2.addAll(gameDeck);

				int dLength=0;;
				while(x<z&&!stop){
					x++;
					dLength=deck2.size();
					while(dLength>0){
						if(dLength<=a){
							Log.d(TAG, "STOP, too Small. "+dLength);
							deck3.addAll(deck2);
							deck2.clear();
							a=-1;	//must be negative or it will go into the while loop
							stop=true;
							//maybe just break and drop all cards.
						}		
						//print ("a="+a, 449);
						while(a>=0){
							deck3.add(deck2.get(a));
							deck2.remove(a);
							a--;	//goes to -1, but thats ok
							j++;	//seemed like it adds an extra but a>=0 is why.
						}
						
						deck4.addAll(deck3);
						deck3.clear();
						
						a=(int) (Math.random()*7)+1;
						dLength=deck2.size();
						//Log.d(TAG, "deck2= "+dLength, 461);
						//Log.d(TAG, "deck3= "+deck3.getSize(), 462);
						//Log.d(TAG, "deck4= "+deck4.getSize(), 463);
					}
					//deck2.clearALL();  should be empty
					stop=false;
					deck2.addAll(deck4);  //dont use = it makes them clones of each other
					deck4.clear();
					Log.d(TAG, "x="+x+"  z="+z);
					
				}
				Log.d(TAG, "j is "+j);
				gameDeck.clear();
				gameDeck.addAll(deck2);
			
		break;
		case 2:
			//TODO FIX ME
			Log.d(TAG, "shuffle type 2");
			x=0;
			z=50;//times to loop the deck and 'randomly' switch cards.
			int r = 51;
			stop=false;
			deck2 = new ArrayList<Card>();
	
			while(x<z){
				x++;
				j=0;
				a=(int) (Math.random()*r);
				for(int i=52; i>0&&!stop; i--){
					int loop=0;
					while(gameDeck.get(a)!=null&&!stop){
						loop++;
						a=(int) (Math.random()*r);
						if(loop>15&&!stop){ //careful on this Set loop to 15 from 10.
							r = 0;
							for(int q=0; q<gameDeck.size();q++){//Random math not finding cards, Empty rest and start again.
								if(gameDeck.get(q)!=null){
									deck2.add(q, gameDeck.get(q));
									r++;
									j++;
								}
								
								
							}
							stop=true;
							
							
						}
					}
					if(!stop){
						deck2.add(a, gameDeck.get(a));
						gameDeck.add(a, null);	//WTF is going on here?		
						j++;
					}
				}
				
				gameDeck=deck2;
				
				
				
			}
		break;
		case 3:
				Log.d(TAG, "shuffle type 3");
				dartBoardShuffle();
				break;
			}
		}
		
	public void dartBoardShuffle(){
		Log.d(TAG, "DartBoard Shuffle");
		int mixRounds =50;
		int counter =0;
		ArrayList<Card> cardPile = new ArrayList<Card>();
		ArrayList<Card> tossedCardPile = new ArrayList<Card>();
		cardPile.addAll(gameDeck);
		synchronized (cardPile) {
			synchronized (tossedCardPile) {
				while(counter<mixRounds){
					counter++;
					Log.d(TAG, "counter="+counter);
					tossedCardPile.addAll(dartShuffle(cardPile));
					cardPile.clear();
					cardPile.addAll(tossedCardPile);
					tossedCardPile.clear();
				}
			}
		}
		this.gameDeck.clear();
		this.gameDeck.addAll(cardPile);
		

	}
	/**
	 * Shuffle inspired by darts
	 * Toss cards at an array Randomly Each spot hit is where the card stays
	 * If another hits the same spot, then the first 'drops' into an array
	 * clear board when done by 'dropping' all cards
	 * @param startDeck
	 * @return Cards more shuffled.
	 */
	public ArrayList<Card> dartShuffle(ArrayList<Card> startDeck){
		int dartBoardX =3;
		int dartBoardY =3;
		ArrayList<Card> floorCards = new ArrayList<Card>();
		Card[][] cardDartBoard= new Card[dartBoardX][dartBoardY];
		int x = 0;
		int y = 0;
		for(Card card : startDeck){
			x = (int) (Math.random()*dartBoardX);
			y = (int) (Math.random()*dartBoardY);

			//Toss Card
			if(cardDartBoard[x][y]==null){
				cardDartBoard[x][y]=card;
			}
			else{
				floorCards.add(cardDartBoard[x][y]);
				cardDartBoard[x][y]=card;			
			}
		}
		//Clear Dart Board
		for(int i=0; i<dartBoardX;i++){
			for(int j=0; j<dartBoardY;j++){
				if(cardDartBoard[i][j]!=null){
					floorCards.add(cardDartBoard[i][j]);
				}
			}
		}
		return floorCards;
		
	}
		
	/**
	 * REDO TO USE 4 per spot
	 * Takes a Deck and returns an array of decks, 11 of them 5 per array and 2 in the last one.
	 * @param a The Deck to be split
	 * @return	ArrayList<Deck>(size = 11)
	 */
	private ArrayList<oldDeck> splitDeck(oldDeck a) {
		ArrayList<oldDeck> decks = new ArrayList<oldDeck>();
		for(int i =0; i<10;i++){
			oldDeck deck2 = new oldDeck();
	
			int t=0;
			while(t<5){
				deck2.addCard(a.getCard(t));
				t++;
			}
			t--;
			while(t>=0){
				a.removeCardAtIndex(t);
				t--;
			}
			decks.add(deck2);
			Log.d(TAG, "deck size="+ deck2.getSize());

		}
		Log.d(TAG, "final cards size="+ a.getSize());
		decks.add(a);
		return decks;
	}
		
	/**
	 * Mix an ArrayList<Deck> into 
	 * @param a
	 * @return
	 */
	private oldDeck mixOutIn(ArrayList<oldDeck> a){
		int size = a.size();
		boolean extra=false;
		if(size%2==1){
			size--;
			extra=true;
		}
		oldDeck d= new oldDeck();
		for(int i =0;i<size/2;i++){
			d.addAllCards(mixDecks(a.get(i), a.get(size-i-1)));
		}
		d.addAllCards(a.get(size/2));
		
		return d;
	}
	
	public oldDeck mixDecks(oldDeck a, oldDeck b){
		for(int i=0; i<a.getSize();i++){
			b.addCardAtIndex(i+i, a.getCard(i));
		}
		return b;
	}

	/**
	* Create the and deals them out and then finds the two
	* NEEDS SHUFFLE.
	*/
	public ArrayList<ArrayList<Card>> deal() {
		Log.d(TAG, "dealing");
		Log.d(TAG, "Deck size="+this.gameDeck.size());
		ArrayList<ArrayList<Card>> playerDecks = new ArrayList<ArrayList<Card>>();
		ArrayList<Card> hand1 = new ArrayList<Card>();
		ArrayList<Card> hand2 = new ArrayList<Card>();
		ArrayList<Card> hand3 = new ArrayList<Card>();
		ArrayList<Card> hand4 = new ArrayList<Card>();
		for(int i=0;i<gameDeck.size();i+=4){
			int d1=i;
			int d2=i+1;
			int d3=i+2;
			int d4=i+3;
			hand1.add(gameDeck.get(d1));
			hand2.add(gameDeck.get(d2));
			hand3.add(gameDeck.get(d3));
			hand4.add(gameDeck.get(d4));
		}
		playerDecks.add(hand1);
		playerDecks.add(hand2);
		playerDecks.add(hand3);
		playerDecks.add(hand4);
		return playerDecks;
		
	}
	
	public void addGameDeckCard(Card deckCard){
		gameDeck.add(deckCard);
	}
	
		
}
