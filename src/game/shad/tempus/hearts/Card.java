package game.shad.tempus.hearts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Card {
    private static final String TAG = Card.class.getSimpleName();
    private Bitmap bitmap;
    public int Rid;
    private boolean touched;
    private Player owner = null;
    public boolean played = false;
	final static int CLUBS = 0;
	final static int DIAMONDS = 1;
	final static int SPADES = 2;
	final static int blue_backS = 3;
	final static int NOTSET = 4;
	private int x;
	private int y;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int z;
	private int value  = 0;
	private int suit = 4;
	private Context con;
	public String name = "";
    private Game view;
    
	 public Card( int value, int suit, Context con){
		this.view = view;
	    this.con = con;
	    this.bitmap = getImage(value, suit);
		this.value = value;
		this.suit = suit;
		this.name = cardToString();
	 }
	    
	    private Bitmap getImage(int v, int s){
	        int im = 0;
	        
	        switch (s){
	            case 0:
	                switch(v){	   
		                case 1:
	                        im = R.drawable.clubs_ace;
	                        break;
	                    case 2:
                            im = R.drawable.clubs2;
                            break;
	                    case 3:
                            im = R.drawable.clubs3;
                            break;
	                    case 4:
                    		im = R.drawable.clubs4;
                    		break;
	                    case 5:
                    		im = R.drawable.clubs5;
	                        break;
	                    case 6:
                    		im = R.drawable.clubs6;
	                        break;
	                    case 7:
                    		im = R.drawable.clubs7;
	                        break;
	                    case 8:
                    		im = R.drawable.clubs8;
	                        break;
	                    case 9:
                    		im = R.drawable.clubs9;
	                        break;
	                    case 10:
                    		im = R.drawable.clubs10;
	                        break;
	                    case 11:
                            im = R.drawable.clubs_jack;
	                        break;
	                    case 12:
                            im = R.drawable.clubs_queen;
	                        break;
	                    case 13:
                            im = R.drawable.clubs_king;
	                        break;

	                    default:
	                            im = R.drawable.blue_back;
	                        break;  
	                }
	                break;
	            case 1:
	                switch(v){
	                case 1:
	                		im = R.drawable.diamonds_ace;
                        break;
	                case 2:
	                        im = R.drawable.diamonds2;
	                    break;
	                case 3:
	                        im = R.drawable.diamonds3;
	                    break;
	                case 4:
	                        im = R.drawable.diamonds4;
	                    break;
	                case 5:
	                        im = R.drawable.diamonds5;
	                    break;
	                case 6:
	                        im = R.drawable.diamonds6;
	                    break;
	                case 7:
	                        im = R.drawable.diamonds7;
	                    break;
	                case 8:
	                        im = R.drawable.diamonds8;
	                    break;
	                case 9:
	                        im = R.drawable.diamonds9;
	                    break;
	                case 10:
	                        im = R.drawable.diamonds10;
	                    break;
	                case 11:
	                        im = R.drawable.diamonds_jack;
	                    break;
	                case 12:
	                        im = R.drawable.diamonds_queen;
	                    break;
	                case 13:
	                        im = R.drawable.diamonds_king;
	                    break;

	                default:
	                        im = R.drawable.red_back;
	                    break;  
	            }
	                break;
	            case 2:
	                switch(v){
	                case 1:
	                		im = R.drawable.spade_ace;
	                	break;
	                case 2:
	                        im = R.drawable.spade2;
	                    break;
	                case 3:
	                        im = R.drawable.spade3;
	                    break;
	                case 4:
	                        im = R.drawable.spade4;
	                    break;
	                case 5:
	                        im = R.drawable.spade5;
	                    break;
	                case 6:
	                        im = R.drawable.spade6;
	                    break;
	                case 7:
	                        im = R.drawable.spade7;
	                    break;
	                case 8:
	                        im = R.drawable.spade8;
	                    break;
	                case 9:
	                        im = R.drawable.spade9;
	                    break;
	                case 10:
	                        im = R.drawable.spade10;
	                    break;
	                case 11:
	                        im = R.drawable.spade_jack;
	                    break;
	                case 12:
	                        im = R.drawable.spade_queen;
	                    break;
	                case 13:
	                        im = R.drawable.spade_king;
	                    break;

	                default:
	                        im = R.drawable.blue_back;
	                    break;  
	            }
	                break;
	            case 3:
	                switch(v){
	                case 1:
                        	im = R.drawable.hearts_ace;
                        	break;
	                case 2:
	                        im = R.drawable.hearts2;
	                    break;
	                case 3:
	                        im = R.drawable.hearts3;
	                    break;
	                case 4:
	                        im = R.drawable.hearts4;
	                    break;
	                case 5:
	                        im = R.drawable.hearts5;
	                    break;
	                case 6:
	                        im = R.drawable.hearts6;
	                    break;
	                case 7:
	                        im = R.drawable.hearts7;
	                    break;
	                case 8:
	                        im = R.drawable.hearts8;
	                    break;
	                case 9:
	                        im = R.drawable.hearts9;
	                    break;
	                case 10:
	                        im = R.drawable.hearts10;
	                    break;
	                case 11:
	                        im = R.drawable.hearts_jack;
	                    break;
	                case 12:
	                        im = R.drawable.hearts_queen;
	                    break;
	                case 13:
	                        im = R.drawable.hearts_king;
	                    break;

	                default:
	                        im = R.drawable.red_back;
	                    break;  
	            }
	                break;  
	            default:
	                switch(v){
	                case 1:
	                        im = R.drawable.blue_back;
	                    break;
	                case 2:
	                        im = R.drawable.blue_back;
	                    break;
	                case 3:
	                        im = R.drawable.blue_back;
	                    break;
	                case 4:
	                        im = R.drawable.blue_back;
	                    break;
	                case 5:
	                        im = R.drawable.blue_back;
	                    break;
	                case 6:
	                        im = R.drawable.blue_back;
	                    break;
	                case 7:
	                        im = R.drawable.blue_back;
	                    break;
	                case 8:
	                        im = R.drawable.blue_back;
	                    break;
	                case 9:
	                        im = R.drawable.blue_back;
	                    break;
	                case 10:
	                        im = R.drawable.blue_back;
	                    break;
	                case 11:
	                        im = R.drawable.blue_back;
	                    break;
	                case 12:
	                        im = R.drawable.blue_back;
	                    break;
	                case 13:
	                        im = R.drawable.blue_back;
	                    break;
	                
	                default:
	                        im = R.drawable.blue_back;
	                    break;  
	            }
	                break;
	        }
	        Rid=im;
	        return BitmapFactory.decodeResource(this.con.getResources(),im);
	    }
	
    public Bitmap getBitmap(){
		return bitmap;
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
	public void setValue(int c) {
		value = c;
	}
	
	public int getSuit(){
		return suit;
	}
	
	public void setSuit(int suit){
		this.suit = suit;
	}

	public String cardToString(){
		String sValue = "";
		String sSuit="";
		switch(suit){
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
		
		switch (value){
			case 1:
				sValue="Ace";
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
			}
		return sValue+" of "+sSuit;
		
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
    
    public void setOwner(Player p){
    		owner=p;
    }
    public Player getOwner(){
		return owner;
}
    public void draw(Canvas canvas) {
		//Add z layer
        //Paint paint = new Paint();
        //paint.setStrokeWidth(2);
        //paint.setColor(Color.CYAN);
        //canvas.drawRect(150,350,400,600, paint);
        //canvas.drawBitmap(bitmap, x - (bitmap.getWidth() /2), y - (bitmap.getHeight() /2), null);
        //Rect r = new Rect(x, y, x + bitmap.getWidth()/2, y + bitmap.getHeight()/2);
        Rect r = new Rect(x1, y1, x2,  y2);

    	canvas.drawBitmap(bitmap, null, r, null);
    }
    
    public void handleActionDown(int eventX, int eventY) {
        if(eventX >= (x1-bitmap.getWidth() /2) && (eventX <= (x1 + bitmap.getWidth()/2))){
            if(eventY >= (y1-bitmap.getHeight() /2) && (y <= (y1+bitmap.getHeight() /2))){
                setTouched(true);
    		    bitmap = BitmapFactory.decodeResource(con.getResources(), R.drawable.blue_back);
    		    
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
		bitmap = BitmapFactory.decodeResource(con.getResources(), R.drawable.blue_back);
	}
    public boolean col(int x, int y){
        if (x >= this.x1 && x < (this.x1 + (bitmap.getWidth()) )
                && y >= this.y1 && y < (this.y1 + (bitmap.getHeight()))) {
           return true;
        }
        
        return false;
    }


	
}
