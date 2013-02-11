package game.shad.tempus.hearts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Card {
	public static final String TAG = "Hearts--Card";
    private Bitmap bitmap;
    public int Rid;
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
	
	private int value  = 0;
	private int suit = 4;
	private Game game;
	public String name = "";
    
	public Card( int value, int suit, Game game){
	    this.game = game;
		this.value = value;
		this.suit = suit;
	    this.bitmap = getImage();
		this.name = toString();
	}
	 
   public void draw(Canvas canvas) {
		//Add z layer
        //Paint paint = new Paint();
        //paint.setStrokeWidth(2);
        //paint.setColor(Color.CYAN);
        //canvas.drawRect(150,350,400,600, paint);
        //canvas.drawBitmap(bitmap, x - (bitmap.getWidth() /2), y - (bitmap.getHeight() /2), null);
        //Rect r = new Rect(x, y, x + bitmap.getWidth()/2, y + bitmap.getHeight()/2);
        r = new Rect(x1, y1, x2,  y2);
        if(touched!=lastTouched){
        	this.bitmap = getImage();
        	lastTouched=touched;
        }
        canvas.drawBitmap(bitmap, null, r, null);
    }

   
	public Bitmap getImage(){
		if(!touched){
	        switch (suit){
	        case 0:
	            switch(value){	 
	            	case 0:
	            		return game.GreenBack;
	            	case 1:
	            		return game.CardBack;
	                case 2:
	                    return game.ClubsTwo;
	                case 3:
	                    return game.ClubsThree;
	                case 4:
	                    return game.ClubsFour;
	
	                case 5:
	                    return game.ClubsFive;
	
	                case 6:
	                    return game.ClubsSix;
	
	                case 7:
	                    return game.ClubsSeven;
	
	                case 8:
	                    return game.ClubsEight;
	
	                case 9:
	                    return game.ClubsNine;
	
	                case 10:
	                    return game.ClubsTen;
	
	                case 11:
	                    return game.ClubsJack;
	
	                case 12:
	                    return game.ClubsQueen;
	
	                case 13:
	                    return game.ClubsKing;
	
	                case 14:
	                    return game.ClubsAce;
	
	                default:
	                    return game.GreenBack;
	
	            }
	            case 1:
	                switch(value){
	            	case 0:
	            		return game.BlueBack;
	                case 2:
	                    return game.DiamondsTwo;
	                case 3:
	                    return game.DiamondsThree;
	                case 4:
	                    return game.DiamondsFour ;
	
	                case 5:
	                    return game.DiamondsFive;
	
	                case 6:
	                    return game.DiamondsSix;
	
	                case 7:
	                    return game.DiamondsSeven;
	
	                case 8:
	                    return game.DiamondsEight;
	
	                case 9:
	                    return game.DiamondsNine;
	
	                case 10:
	                    return game.DiamondsTen;
	
	                case 11:
	                    return game.DiamondsJack;
	
	                case 12:
	                    return game.DiamondsQueen;
	
	                case 13:
	                    return game.DiamondsKing;
	
	                case 14:
	                    return game.DiamondsAce;
	
	                default:
	                    return game.BlueBack;
	
	            }
	            case 2:
	                switch(value){
	            	case 0:
	            		return game.BlackBack;
	                case 2:
	                    return game.SpadesTwo;
	                case 3:
	                    return game.SpadesThree;
	                case 4:
	                    return game.SpadesFour ;
	
	                case 5:
	                    return game.SpadesFive;
	
	                case 6:
	                    return game.SpadesSix;
	
	                case 7:
	                    return game.SpadesSeven;
	
	                case 8:
	                    return game.SpadesEight;
	
	                case 9:
	                    return game.SpadesNine;
	
	                case 10:
	                    return game.SpadesTen;
	
	                case 11:
	                    return game.SpadesJack;
	
	                case 12:
	                    return game.SpadesQueen;
	
	                case 13:
	                    return game.SpadesKing;
	
	                case 14:
	                    return game.SpadesAce;
	
	                default:
	                    return game.BlackBack;
	
	            }
	            case 3:
	                switch(value){
	            	case 0:
	            		return game.RedBack;
	                case 2:
	                    return game.HeartsTwo;
	                case 3:
	                    return game.HeartsThree;
	                case 4:
	                    return game.HeartsFour ;
	
	                case 5:
	                    return game.HeartsFive;
	
	                case 6:
	                    return game.HeartsSix;
	
	                case 7:
	                    return game.HeartsSeven;
	
	                case 8:
	                    return game.HeartsEight;
	
	                case 9:
	                    return game.HeartsNine;
	
	                case 10:
	                    return game.HeartsTen;
	
	                case 11:
	                    return game.HeartsJack;
	
	                case 12:
	                    return game.HeartsQueen;
	
	                case 13:
	                    return game.HeartsKing;
	
	                case 14:
	                    return game.HeartsAce;
	
	                default:
	                    return game.RedBack;
	
	            }
	        }
	        return game.BlueBack;
		}
		else{
		switch (suit){
		    case 0:
		        switch(value){	 
		        	case 0:
		        		return game.GreenBack;
		        	case 1:
		        		return game.CardBack;
		            case 2:
		                return game.ClubsTwoHighLight;
		            case 3:
		                return game.ClubsThreeHighLight;
		            case 4:
		                return game.ClubsFourHighLight;
		
		            case 5:
		                return game.ClubsFiveHighLight;
		
		            case 6:
		                return game.ClubsSixHighLight;
		
		            case 7:
		                return game.ClubsSevenHighLight;
		
		            case 8:
		                return game.ClubsEightHighLight;
		
		            case 9:
		                return game.ClubsNineHighLight;
		
		            case 10:
		                return game.ClubsTenHighLight;
		
		            case 11:
		                return game.ClubsJackHighLight;
		
		            case 12:
		                return game.ClubsQueenHighLight;
		
		            case 13:
		                return game.ClubsKingHighLight;
		
		            case 14:
		                return game.ClubsAceHighLight;
		
		            default:
		                return game.GreenBack;
		
		        }
		        case 1:
		            switch(value){
		        	case 0:
		        		return game.BlueBack;
		            case 2:
		                return game.DiamondsTwoHighLight;
		            case 3:
		                return game.DiamondsThreeHighLight;
		            case 4:
		                return game.DiamondsFourHighLight;
		
		            case 5:
		                return game.DiamondsFiveHighLight;
		
		            case 6:
		                return game.DiamondsSixHighLight;
		
		            case 7:
		                return game.DiamondsSevenHighLight;
		
		            case 8:
		                return game.DiamondsEightHighLight;
		
		            case 9:
		                return game.DiamondsNineHighLight;
		
		            case 10:
		                return game.DiamondsTenHighLight;
		
		            case 11:
		                return game.DiamondsJackHighLight;
		
		            case 12:
		                return game.DiamondsQueenHighLight;
		
		            case 13:
		                return game.DiamondsKingHighLight;
		
		            case 14:
		                return game.DiamondsAceHighLight;
		
		            default:
		                return game.BlueBack;
		
		        }
		        case 2:
		            switch(value){
		        	case 0:
		        		return game.BlackBack;
		            case 2:
		                return game.SpadesTwoHighLight;
		            case 3:
		                return game.SpadesThreeHighLight;
		            case 4:
		                return game.SpadesFourHighLight;
		
		            case 5:
		                return game.SpadesFiveHighLight;
		
		            case 6:
		                return game.SpadesSixHighLight;
		
		            case 7:
		                return game.SpadesSevenHighLight;
		
		            case 8:
		                return game.SpadesEightHighLight;
		
		            case 9:
		                return game.SpadesNineHighLight;
		
		            case 10:
		                return game.SpadesTenHighLight;
		
		            case 11:
		                return game.SpadesJackHighLight;
		
		            case 12:
		                return game.SpadesQueenHighLight;
		
		            case 13:
		                return game.SpadesKingHighLight;
		
		            case 14:
		                return game.SpadesAceHighLight;
		
		            default:
		                return game.BlackBack;
		
		        }
		        case 3:
		            switch(value){
		        	case 0:
		        		return game.RedBack;
		            case 2:
		                return game.HeartsTwoHighLight;
		            case 3:
		                return game.HeartsThreeHighLight;
		            case 4:
		                return game.HeartsFourHighLight;
		
		            case 5:
		                return game.HeartsFiveHighLight;
		
		            case 6:
		                return game.HeartsSixHighLight;
		
		            case 7:
		                return game.HeartsSevenHighLight;
		
		            case 8:
		                return game.HeartsEightHighLight;
		
		            case 9:
		                return game.HeartsNineHighLight;
		
		            case 10:
		                return game.HeartsTenHighLight;
		
		            case 11:
		                return game.HeartsJackHighLight;
		
		            case 12:
		                return game.HeartsQueenHighLight;
		
		            case 13:
		                return game.HeartsKingHighLight;
		
		            case 14:
		                return game.HeartsAceHighLight;
		
		            default:
		                return game.RedBack;
		
		        }
		    }
		    return game.BlueBack;
		}
		}
	
    public Bitmap getBitmap(){
		return bitmap;
	}
    public Rect getBounds(){
    	return new Rect(x1, y1, x2, y2);
    }
       
	public int getRid(){
		return Rid;
	}
	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
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
		return sValue+" of "+sSuit;
		
	}


	/**
	 * @param suit converted to:
	 * Clubs    = 0
	 * Diamonds = 1
	 * Spades   = 2
	 * Hearts   = 3
	 * @return
	 */
	public String suittoString(int suit){
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
	public String valueToString(int value){
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
        this.touched = touched;
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
