package game.shad.tempus.hearts;

import android.graphics.Bitmap;

public class Card {
	final static int CLUBS = 0;
	final static int DIAMONDS = 1;
	final static int HEARTS = 2;
	final static int SPADES = 3;
	final static int NOTSET = 4;
	private Bitmap bitmap;
	private int x;
	private int y;
	private int value  = 0;
	private int suit = 4;
	
	public Card(int x, int y, int value, int suit){
		this.x = x;
		this.y = y;
		this.value = value;
		this.suit = suit;
	}
	
	public Bitmap getBitmap(){
		return bitmap;
	}
	
	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y = y;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int c) {
		value = c;
	}
	
	public int getSuit(){
		return suit;
	}
	
	public void setSuit(int suit){
		this.suit = suit;
	}


	

	
	
}
