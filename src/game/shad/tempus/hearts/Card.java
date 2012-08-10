package game.shad.tempus.hearts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class card {
    private static final String TAG = card.class.getSimpleName();
    
    private Bitmap bitmap;
    private boolean touched;
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
	private int value  = 0;
	private int suit = 4;
	private Context con;
	
	public card( int value, int suit, Bitmap bitmap){
		this.bitmap = bitmap;

		this.value = value;
		this.suit = suit;
	}
	
	public Bitmap getBitmap(){
		return bitmap;
	}
	
	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	
	
	public void setCoords(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.x1 = x2;
		this.y1 = y1;
		this.y2 = y1;
		
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
        Paint paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setColor(Color.CYAN);
        canvas.drawRect(150,350,400,600, paint);
        
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() /2), y - (bitmap.getHeight() /2), null);

    }
    
    public void handleActionDown(int eventX, int eventY) {
        if(eventX >= (x-bitmap.getWidth() /2) && (eventX <= (x + bitmap.getWidth()/2))){
            if(eventY >= (y-bitmap.getHeight() /2) && (y <= (y+bitmap.getHeight() /2))){
                setTouched(true);
                bitmap = BitmapFactory.decodeResource(con.getResources(), R.drawable.heart_one_back);
            }else{
                setTouched(false);
            }
        }else{
            setTouched(false);
        }
    }
	
	public void handleActionMove(int eventX, int eventY){
		this.x = eventX;
		this.y = eventY;
	}
	
	public void handleActionUp(int eventX, int eventY){
		setTouched(false);
		this.x = eventX;
		this.y = eventY;
		bitmap = BitmapFactory.decodeResource(con.getResources(), R.drawable.heart_one);
	}
	
}
