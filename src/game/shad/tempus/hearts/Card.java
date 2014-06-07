package game.shad.tempus.hearts;



import game.shad.tempus.hearts.Game;
import game.shad.tempus.hearts.Player;
import game.shad.tempus.hearts.R;
import game.shad.tempus.hearts.R.drawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.Log;

public class Card {
	public static final String TAG = "Hearts--Card";
    private Bitmap bitmap;
    public int bitmapID;
    private boolean touched =false;
    private boolean lastTouched =false;
    public boolean inView=false;
    private Player owner = null;
    public boolean played = false;
	final static int CLUBS = 0;
	final static int DIAMONDS = 1;
	final static int SPADES = 2;
	final static int HEARTS = 3;
	final static int NOTSET = 4;
	private int x;
	private int y;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int z;
	private Rect r;
	protected long lastHighlight = 0;
	protected boolean highlighted = false;
	
	private int value  = 0;
	private int suit = 4;
	private Game game;
	public String name = "";
    
	
	public Card( int value, int suit, Game game){
	    this.game = game;
		this.value = value;
		this.suit = suit;
//	    this.bitmap = getImageFromSuitAndValue();
		this.name = toString();
	}
	 
   public void draw(Canvas canvas) {
//		Log.d(TAG, "OnDraw Card");
		//Add z layer
        //Paint paint = new Paint();
        //paint.setStrokeWidth(2);
        //paint.setColor(Color.CYAN);
        //canvas.drawRect(150,350,400,600, paint);
        //canvas.drawBitmap(bitmap, x - (bitmap.getWidth() /2), y - (bitmap.getHeight() /2), null);
        //Rect r = new Rect(x, y, x + bitmap.getWidth()/2, y + bitmap.getHeight()/2)
//        r = new Rect(x1, y1, x2,  y2);
//        if(touched!=lastTouched){
//        	Log.d(TAG, "Card touched--- changing Image");
//        	refreshBitmap();
//        	lastTouched=touched;
//        }
//        canvas.drawBitmap(bitmap, null, r, null);
       	r = new Rect(x1, y1, x2,  y2);
       	if(touched!=lastTouched){
       		Log.d(TAG, "Card touched--- changing Image");
       		refreshBitmap();
       		lastTouched=touched;
        }
        int height = x2-x1;
        int width = y2-y1;
       	bitmap = Game.decodeSampledBitmapFromResource(game.getResources(), bitmapID, width, height);
       	canvas.drawBitmap(bitmap, null, r, null);
		Paint paint = new Paint();
		paint.setColor(Color.argb((int) (255 - Math.min((System.currentTimeMillis() - lastHighlight) / 4, 255)), 222, 222, 25));
		paint.setStyle(Style.FILL);
		paint.setAlpha(100);
		canvas.drawRect(r, paint);

		if ((System.currentTimeMillis() - lastHighlight) > 1000)
			highlighted = false;
    }
   
	public void highlight() {
		highlighted = true;
		lastHighlight = System.currentTimeMillis();
	}
   
	public Integer getImageFromSuitAndValue(){
		if(!touched){
	        switch (suit){
	        case 0:
	            switch(value){	 
	            	case 0:
	            		return (R.drawable.green_back);
	            	case 1:
	            		return (R.drawable.cardback);
	                case 2:
	                    return (R.drawable.c2);
	                case 3:
	                    return (R.drawable.c3);
	                case 4:
	                    return (R.drawable.c4);
	
	                case 5:
	                    return (R.drawable.c5);
	
	                case 6:
	                    return (R.drawable.c6);
	
	                case 7:
	                    return (R.drawable.c7);
	
	                case 8:
	                    return (R.drawable.c8);
	
	                case 9:
	                    return (R.drawable.c9);
	
	                case 10:
	                    return (R.drawable.c10);
	
	                case 11:
	                    return (R.drawable.cj);
	
	                case 12:
	                    return (R.drawable.cq);
	
	                case 13:
	                    return (R.drawable.ck);
	
	                case 14:
	                    return (R.drawable.ca);
	
	                default:
	                    return (R.drawable.green_back);
	
	            }
	            case 1:
	                switch(value){
	            	case 0:
	            		return (R.drawable.blue_back);
	                case 2:
	                    return (R.drawable.d2);
	                case 3:
	                    return (R.drawable.d3);
	                case 4:
	                    return (R.drawable.d4);
	
	                case 5:
	                    return (R.drawable.d5);
	
	                case 6:
	                    return (R.drawable.d6);
	
	                case 7:
	                    return (R.drawable.d7);
	
	                case 8:
	                    return (R.drawable.d8);
	
	                case 9:
	                    return (R.drawable.d9);
	
	                case 10:
	                    return (R.drawable.d10);
	
	                case 11:
	                    return (R.drawable.dj);
	
	                case 12:
	                    return (R.drawable.dq);
	
	                case 13:
	                    return (R.drawable.dk);
	
	                case 14:
	                    return (R.drawable.da);
	
	                default:
	                    return (R.drawable.blue_back);
	
	            }
	            case 2:
	                switch(value){
	            	case 0:
	            		return (R.drawable.black_back);
	                case 2:
	                    return (R.drawable.s2);
	                case 3:
	                    return (R.drawable.s3);
	                case 4:
	                    return (R.drawable.s4);
	
	                case 5:
	                    return (R.drawable.s5);
	
	                case 6:
	                    return (R.drawable.s6);
	
	                case 7:
	                    return (R.drawable.s7);
	
	                case 8:
	                    return (R.drawable.s8);
	
	                case 9:
	                    return (R.drawable.s9);
	
	                case 10:
	                    return (R.drawable.s10);
	
	                case 11:
	                    return (R.drawable.sj);
	
	                case 12:
	                    return (R.drawable.sq);
	
	                case 13:
	                    return (R.drawable.sk);
	
	                case 14:
	                    return (R.drawable.sa);
	
	                default:
	            		return (R.drawable.black_back);
	
	            }
	            case 3:
	                switch(value){
	            	case 0:
	            		return (R.drawable.red_back);
	                case 2:
	                    return (R.drawable.h2);
	                case 3:
	                    return (R.drawable.h3);
	                case 4:
	                    return (R.drawable.h4);
	
	                case 5:
	                    return (R.drawable.h5);
	
	                case 6:
	                    return (R.drawable.h6);
	
	                case 7:
	                    return (R.drawable.h7);
	
	                case 8:
	                    return (R.drawable.h8);
	
	                case 9:
	                    return (R.drawable.h9);
	
	                case 10:
	                    return (R.drawable.h10);
	
	                case 11:
	                    return (R.drawable.hj);
	
	                case 12:
	                    return (R.drawable.hq);
	
	                case 13:
	                    return (R.drawable.hk);
	
	                case 14:
	                    return (R.drawable.ha);
	
	                default:
	            		return (R.drawable.red_back);
	
	            }
	        }
    		return (R.drawable.blue_back);
		}
		else
		{
			Log.d(TAG, "Highlighted Card to be selected");
			switch (suit){
	        case 0:
	            switch(value){	 
	            	case 0:
	            		return (R.drawable.green_back);
	            	case 1:
	            		return (R.drawable.cardback);
	                case 2:
	                    return (R.drawable.hc2);
	                case 3:
	                    return (R.drawable.hc3);
	                case 4:
	                    return (R.drawable.hc4);
	
	                case 5:
	                    return (R.drawable.hc5);
	
	                case 6:
	                    return (R.drawable.hc6);
	
	                case 7:
	                    return (R.drawable.hc7);
	
	                case 8:
	                    return (R.drawable.hc8);
	
	                case 9:
	                    return (R.drawable.hc9);
	
	                case 10:
	                    return (R.drawable.hc10);
	
	                case 11:
	                    return (R.drawable.hcj);
	
	                case 12:
	                    return (R.drawable.hcq);
	
	                case 13:
	                    return (R.drawable.hck);
	
	                case 14:
	                    return (R.drawable.hca);
	
	                default:
	                    return (R.drawable.green_back);
	
	            }
	            case 1:
	                switch(value){
	            	case 0:
	            		return (R.drawable.blue_back);
	                case 2:
	                    return (R.drawable.hd2);
	                case 3:
	                    return (R.drawable.hd3);
	                case 4:
	                    return (R.drawable.hd4);
	
	                case 5:
	                    return (R.drawable.hd5);
	
	                case 6:
	                    return (R.drawable.hd6);
	
	                case 7:
	                    return (R.drawable.hd7);
	
	                case 8:
	                    return (R.drawable.hd8);
	
	                case 9:
	                    return (R.drawable.hd9);
	
	                case 10:
	                    return (R.drawable.hd10);
	
	                case 11:
	                    return (R.drawable.hdj);
	
	                case 12:
	                    return (R.drawable.hdq);
	
	                case 13:
	                    return (R.drawable.hdk);
	
	                case 14:
	                    return (R.drawable.hda);
	
	                default:
	                    return (R.drawable.blue_back);
	
	            }
	            case 2:
	                switch(value){
	            	case 0:
	            		return (R.drawable.black_back);
	                case 2:
	                    return (R.drawable.hs2);
	                case 3:
	                    return (R.drawable.hs3);
	                case 4:
	                    return (R.drawable.hs4);
	
	                case 5:
	                    return (R.drawable.hs5);
	
	                case 6:
	                    return (R.drawable.hs6);
	
	                case 7:
	                    return (R.drawable.hs7);
	
	                case 8:
	                    return (R.drawable.hs8);
	
	                case 9:
	                    return (R.drawable.hs9);
	
	                case 10:
	                    return (R.drawable.hs10);
	
	                case 11:
	                    return (R.drawable.hsj);
	
	                case 12:
	                    return (R.drawable.hsq);
	
	                case 13:
	                    return (R.drawable.hsk);
	
	                case 14:
	                    return (R.drawable.hsa);
	
	                default:
	            		return (R.drawable.black_back);
	
	            }
	            case 3:
	                switch(value){
	            	case 0:
	            		return (R.drawable.red_back);
	                case 2:
	                    return (R.drawable.hh2);
	                case 3:
	                    return (R.drawable.hh3);
	                case 4:
	                    return (R.drawable.hh4);
	
	                case 5:
	                    return (R.drawable.hh5);
	
	                case 6:
	                    return (R.drawable.hh6);
	
	                case 7:
	                    return (R.drawable.hh7);
	
	                case 8:
	                    return (R.drawable.hh8);
	
	                case 9:
	                    return (R.drawable.hh9);
	
	                case 10:
	                    return (R.drawable.hh10);
	
	                case 11:
	                    return (R.drawable.hhj);
	
	                case 12:
	                    return (R.drawable.hhq);
	
	                case 13:
	                    return (R.drawable.hhk);
	
	                case 14:
	                    return (R.drawable.hha);
	
	                default:
	            		return (R.drawable.red_back);
	
	            }
	        }
    		return (R.drawable.blue_back);
		}
		}
	
    public Bitmap getBitmap(){
		return bitmap;
	}
    public Integer getBitmapID(){
    	return getImageFromSuitAndValue();
    }
    public Rect getBounds(){
    	return new Rect(x1, y1, x2, y2);
    }
       
	public void refreshBitmap(){
		Log.d(TAG, "Refreshing Bitmap");
    	lastTouched=touched;
		this.bitmapID = getImageFromSuitAndValue();
	}
	
	public void resizeBitmap(int width, int height){
		Bitmap.createScaledBitmap(this.bitmap, width, height, true);
	}

	public void setCoords(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		
	}
	public int[] getCoords(){
		int[] a=new int[]{x1, y1, x2, y2};
		return a;
			
	}
	
	public int getValue() {
		return value;
	}
	
	public String getShortReadableValue() {
		switch (value){
		case 0:
            return "CC";
		case 1:
            return "CB";
		case 2:
            return "2";
        case 3:
            return "3";
        case 4:
            return "4";

        case 5:
            return "5";

        case 6:
            return "6";

        case 7:
            return "7";

        case 8:
            return "8";

        case 9:
            return "9";

        case 10:
            return "10";

        case 11:
            return "J";

        case 12:
            return "Q";
        case 13:
            return "K";
        case 14:
        	return "A";
		}
		return "?";
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

	/**
	 * Return the name of the card
	 * value +of+ suit
	 * Four of Clubs
	 */
	@Override
	public String toString(){
		String sValue = suittoString(suit);
		String sSuit = valueToString(value);
		return sSuit+" of "+sValue;
		
	}


	/**
	 * @param suit converted to:
	 * Clubs    = 0
	 * Diamonds = 1
	 * Spades   = 2
	 * Hearts   = 3
	 * @return
	 */
	public static String suittoString(int suit){
		String sSuit = null;
		switch(suit){
		case -1:
			sSuit="Test";
			break;
		case 0:
			sSuit="Clubs";
			break;
		case 1:
			sSuit="Diamonds";
			break;
		case 2:
			sSuit="Spades";
			break;
		case 3: 
			sSuit="Hearts";
			break;
		}
		return sSuit;
	}
	
	/**
	 * 
	 * @param value converted--- range 0-14
	 * Zero  = 0
	 * ...
	 * Ten   = 10
	 * Jack  = 11
	 * Queen = 12
	 * King  = 13
	 * Ace   = 14
	 * @return
	 */
	public static String valueToString(int value){
		String sValue = null;
		switch (value){
		case 0: 
			sValue="Zero";
			break;
		case 1: 
			sValue="One";
			break;
		case 2: 
			sValue="Two";
			break;
		case 3:
			sValue="Three";
			break;
		case 4:
			sValue="Four";
			break;
		case 5:
			sValue="Five";
			break;
		case 6:
			sValue="Six";
			break;
		case 7:
			sValue="Seven";
			break;
		case 8:
			sValue="Eight";
			break;
		case 9:
			sValue="Nine";
			break;
		case 10:
			sValue="Ten";
			break;
		case 11:
			sValue="Jack";
			break;
		case 12:
			sValue="Queen";
			break;
		case 13:
			sValue="King";
			break;
		case 14:
			sValue="Ace";
			break;
		}
		return sValue;
	}
	
	public int getZ(){
        return z;
    }
    
    public void setZ(int z){
        this.z = z;
    }
    
    public boolean getPlayed(){
        return played;
    }
    
    public void setPlayed(boolean played){
        this.played = played;
    }
    
    public boolean isTouched(){
        return touched;
    }
    
    public void setTouched(boolean touched){
		Log.d(TAG, "Setting Card Touched to--"+touched);
        this.touched = touched;
        refreshBitmap();
    }
    
    public int getCordX(){
        return x - (bitmap.getWidth() /2);
    }
    
    public int getCordY(){
        return y - (bitmap.getHeight() /2);
    }
    
    public void setOwner(Player curPlayer){
    		owner=curPlayer;
    }
    public Player getOwner(){
		return owner;
}
 
    public void handleActionDown(int eventX, int eventY) {
        if(eventX >= (x1-bitmap.getWidth() /2) && (eventX <= (x1 + bitmap.getWidth()/2))){
            if(eventY >= (y1-bitmap.getHeight() /2) && (y <= (y1+bitmap.getHeight() /2))){
                setTouched(true);
    		    bitmap = BitmapFactory.decodeResource(game.getResources(), R.drawable.blue_back);
    		    
            }else{
                setTouched(false);
            }
        }else{
            setTouched(false);
        }
    }
	
	public void handleActionMove(int eventX, int eventY){
		//this.x = eventX;
		//this.y = eventY;
	}
	
	/**
	 * 
	 * @param eventX
	 * @param eventY
	 */
	public void handleActionUp(int eventX, int eventY){
		setTouched(false);
		this.x = eventX;
		this.y = eventY;
		bitmap = BitmapFactory.decodeResource(game.getResources(), R.drawable.blue_back);
	}
    public boolean col(int x, int y){
        if (x >= this.x1 && x < (this.x1 + (bitmap.getWidth()) )
                && y >= this.y1 && y < (this.y1 + (bitmap.getHeight()))) {
           return true;
        }        
        return false;
    }


	
}
