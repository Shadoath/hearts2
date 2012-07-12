package game.backup;

import game.shad.tempus.hearts.*;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class heartsPanel extends SurfaceView 
implements SurfaceHolder.Callback {

    private static final String TAG = heartsPanel.class.getSimpleName();
	
	public card[] deck=new card[52];
	//public Player p1;
	//public Player p2;
	//public Player p3;
	//public Player p4;
	//public Player curPlayer;
	public Deck pile;
	public int pileI=0;
	public int playState=0;
	//public playState game = new playState();
	//public GridLayout topGrid;
    //public EditText et1;
    public boolean playing=true;
    public boolean heartsBroken;
    public int round=0;
    public int count=0;
    public int players=4;
    private card heartOne;
    private card heartTwo;

    public int testX = 0;
    public int testY = 0;
    public boolean StackCol = false;
    
    private thread thread;
    
    private String avgFps;
    public void setAvgFps(String avgFps){
        this.avgFps = avgFps;
    }

    public heartsPanel(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        heartOne = new card(250, 300, 1, 2, BitmapFactory.decodeResource(getResources(), R.drawable.heart_one),getContext());
        heartTwo = new card(100, 300, 1, 2, BitmapFactory.decodeResource(getResources(), R.drawable.heart_one), getContext());

        thread = new thread(getHolder(), this);
        setFocusable(true);
        
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height)
    {
        // TODO Auto-generated method stub
        
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.d(TAG, "Surface is being destroyed");
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e){
                
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }
    
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            
            //target.handleActionDown((int)event.getX(), (int)event.getY());   
			
			//Card clicked? Button? Call event for what was clicked.
            
            //Detect if card is one touched ??
            if(event.getX() >= heartOne.getCordX() && event.getX() < (heartOne.getCordX() + heartOne.getBitmap().getWidth()) &&
                    event.getY() >= heartOne.getCordY() && event.getY() < (heartOne.getCordY() + heartOne.getBitmap().getHeight())){
                heartOne.handleActionDown((int)event.getX(), (int)event.getY());
            }
            
            testX = (int)event.getX();
            testY = (int)event.getY();
            
  
        } 
        
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            
            if(heartOne.isTouched()){
                    heartOne.handleActionMove((int)event.getX(), (int)event.getY());
            }
            
           
            
			
        }
        
        if (event.getAction() == MotionEvent.ACTION_UP){
            
            if(event.getX() >= heartOne.getCordX() && event.getX() < (heartOne.getCordX() + heartOne.getBitmap().getWidth()) &&
                    event.getY() >= heartOne.getCordY() && event.getY() < (heartOne.getCordY() + heartOne.getBitmap().getHeight())){
                heartOne.handleActionUp((int)event.getX(), (int)event.getY());
            }
            
            if(getColStack((int)event.getX(), (int)event.getY()) == true){
                StackCol = true;
            }else{
                StackCol = false;
            }
		
        }
        return true;
    }
    
    private boolean getColStack(int x, int y){
        
        if(x >= 150 && x <= 400 &&
                y >= 350 && y <= 600){
           return true;
        }
   
        return false;  
    }
    
    public void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        heartOne.draw(canvas);
        heartTwo.draw(canvas);
	//Call card draw, via deck, draw avatars, draw board
		
        displayFps(canvas, avgFps); 
        
        Paint paint = new Paint();
        paint.setARGB(255, 255, 255, 255);
        paint.setTextSize(10);
        canvas.drawText("x: " + String.valueOf(testX), this.getWidth() - 50, 100, paint);
        canvas.drawText("y: " + String.valueOf(testY), this.getWidth() - 50, 150, paint);
        
        if(StackCol){
            paint.setTextSize(34);
            canvas.drawText("Stacked", (this.getWidth()/2)-50, (this.getHeight()/2)-200, paint);
        }
    }

        
    public void update(){
       
	   //Call logic updates, deck, score, tricks, turn
	   
    }
    
    public void displayFps(Canvas canvas, String fps){
        if(canvas != null && fps != null){
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            canvas.drawText(fps, this.getWidth() - 50, 20, paint);
        }
    }

}
