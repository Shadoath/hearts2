package game.backup;


import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Canvas;

public class Deck extends HeartsActivity{
	int[] clubs = new int[13];     //0
	int highClub=0;
	int lowClub=0;
	int[] diamonds = new int[13];  //1
	int highDiamonds=0;
	int lowDiamonds=0;
	int[] hearts = new int[13];    //2
	int highHearts=0;
	int lowHearts=0;
	int[] spades = new int[13];	   //3
	int highSpades=0;
	int lowSpades=0;
	public boolean voidClubs;
	public boolean voidDiamonds;
	public boolean voidHearts;
	public boolean voidSpades;
	public String clubsString="";
	public String diamondsString="";
	public String heartsString="";
	public String spadesString="";
	private ArrayList<card> deck;
	
	public Deck(){
		deck = new ArrayList<card>();
	}
	public void newDeck(ArrayList<card> nCard){
		deck=nCard;
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
	public Object getCards(){
		return  deck.toArray();
	}
	
	public card getCard(int index){
		return deck.get(index);
	}
	
	public card getFirst(){
		return deck.get(0);
	}
	public card getSecond(){
		return deck.get(1);
	}
	
	public card getThird(){
		return deck.get(2);
	}
	
	public card getFourth(){  //Dumb code never called
		return deck.get(3);
	}
	
	public void sortHand(){
		for(int i=0;i<deck.size();i++){
			int suit = deck.get(i).getSuit();
			int value = deck.get(i).getValue();
			int c=0;
			int d=0;
			int h=0;
			int s=0;
			switch(suit){
				case 0:{
					clubs[c]=value;
					c++;
				}
				case 1:{
					diamonds[d]=value;
					d++;		
				}
				case 2:{
					hearts[h]=value;
					h++;
				}
					
				case 3:{
					spades[s]=value;
					s++;
				}
			}
			if(c==0){
				voidClubs=true;			
			}
			if(d==0){
				voidDiamonds=true;			
			}
			if(h==0){
				voidHearts=true;			
			}
			if(s==0){
				voidSpades=true;			
			}
		}
	}	
	
	public int[] getHighLowSuit(){
		int high=0;
		int low =0;
		int suit=0;
		int[] HLS=new int[3];
		
		if(deck.size()>1){
			suit = deck.get(0).getSuit();
			for(int i=0;i<deck.size();i++){
				int curSuit=deck.get(i).getSuit();
				int curCard =deck.get(i).getValue();
				if(high<curCard){
					if(suit==curSuit){
						high=curCard;
					}
					if(suit!=curSuit){
						//Not a valid high
					}
				}
				if (low>curCard){
					if(suit==curSuit){
						low=curCard;
					}
					if(suit!=curSuit){
						//Not a valid low
					}

				}
			}
			HLS[0]=high;
			HLS[1]=low;
			HLS[2]=suit;
			
			return HLS;		
		}
		else
		return null;
	}
	
	public void draw(Canvas canvas) {
		for(int i = 0; i < deck.size(); i++){
			getCard(i).draw(canvas);
		}
	}
	/*
	private Card[] Deck;
	
	public Deck(Card[] cards){
		Deck = cards;
	}

	public int getLength(){
		return Deck.length;
	}
	
	public Card[] getCards(){
		return  Deck;
	}
	
	public void sortHighLow(){
		if(!voidClubs){
			Arrays.sort(clubs);
		}
		if(!voidDiamonds){
			Arrays.sort(diamonds);
		}
		if(!voidHearts){
			Arrays.sort(hearts);
		}
		if(!voidSpades){
			Arrays.sort(spades);
		}
	}
	//used when getting Cards from the pile on table.
	public Card getFirst(){
		return Deck[0];
	}
	//used when getting Cards from the pile on table.
	public Card getSecond(){
		return Deck[1];
	}
	//used when getting Cards from the pile on table.
	public Card getThird(){
		return Deck[2];
	}
	
	public void setFirst(Card c){
		Deck[0]=c;
	}	
	public void setSecond(Card c){
		Deck[1]=c;
	}	
	public void setThird(Card c){
		Deck[2]=c;
	}
	public void setForth(Card c){
		Deck[3]=c;
	}
	
	public void sortHand(){
		for(int i=0;i<Deck.length;i++){
			int suit = Deck[i].getSuit();
			int value = Deck[i].getValue();
			int c=0;
			int d=0;
			int h=0;
			int s=0;
			switch(suit){
				case 0:{
					if(value<=lowClub){
						lowClub=value;
					}
					if(value>highClub){
						highClub=value;
					}
					clubsString+=value+" ";
					clubs[c]=value;
					c++;
				}
				case 1:{
					if(value<=lowDiamonds){
						lowDiamonds=value;
					}
					if(value>highDiamonds){
						highDiamonds=value;
					}
					diamondsString+=value+" ";

					diamonds[d]=value;
					d++;		
				}
				case 2:{
					if(value<=lowHearts){
						lowHearts=value;
					}
					if(value>highHearts){
						highHearts=value;
					}
					heartsString+=value+" ";

					hearts[h]=value;
					h++;
				}
					
				case 3:{
					if(value<=lowSpades){
						lowSpades=value;
					}
					if(value>highSpades){
						highSpades=value;
					}
					spadesString+=value+" ";

					spades[s]=value;
					s++;
				}
			}
			if(c==0){
				voidClubs=true;			
			}
			if(d==0){
				voidDiamonds=true;			
			}
			if(h==0){
				voidHearts=true;			
			}
			if(s==0){
				voidSpades=true;			
			}
		}
		sortHighLow();
	}


	

	public int[] getHighLowSuit(){
		int high=0;
		int low =0;
		int suit=0;
		int[] HLS=new int[3];
		
		if(Deck.length>1){
			suit = Deck[0].getSuit();
			for(int i=0;i<Deck.length;i++){
				int curSuit=Deck[i].getSuit();
				int curCard =Deck[i].getValue();
				if(high<curCard){
					if(suit==curSuit){
						high=curCard;
					}
					if(suit!=curSuit){
						//Not a valid high
					}
				}
				if (low>curCard){
					if(suit==curSuit){
						low=curCard;
					}
					if(suit!=curSuit){
						//Not a valid low
					}

				}
			}
			HLS[0]=high;
			HLS[1]=low;
			HLS[2]=suit;
			
			return HLS;		
		}
		else
		return null;
	}
	
	public void draw(Canvas canvas) {
		for(int i = 0; i < Deck.size(); i++){
			getCard(i).draw(canvas);
		}
	}
	*/
	
}
