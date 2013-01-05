package game.shad.tempus.hearts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class PlayerHolder extends SurfaceView implements Callback, OnTouchListener{
	public static final String TAG = "Hearts--PlayerHolder--";

    private Deck deck;
    private Player player;
    private MainActivity main;
    private Card card;
    private int screenWidth;
    private int screenHeight;
    private int position=0;
	private SurfaceHolder surfaceHolder;
	private Paint paint = new Paint();

    private Context mContext;
	private Game game;
    public int cardWidth=0;
    public int textWidth=0;

    private boolean full=false;
	public boolean initialized = false; //made true on surfaceCreated()

    //Holds players deck, class to call for updates about deck and drawing the deck
   
    public PlayerHolder(Context context, MainActivity main, Game game, int sW, int sH, Player player){
    	super(context);
        this.mContext=context;
        this.main= main;
        this.game = game;
        this.player = player;
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        this.screenWidth = sW;
        this.screenHeight = sH;
        cardWidth=(int) (screenWidth/2);
        textWidth=(int) (screenWidth/3);
        addBlankCard(1);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);

    }
  
    /**
     * 1 green
     * 2 Black
     * 3 Blue
     * 4 Red
     * @param color
     */
    public void addBlankCard(int color){
        Card c = new Card(0, color, game);
    	c.resizeBitmap(cardWidth, screenHeight);
		c.setCoords(cardWidth/2, 0, (int)(cardWidth*1.5), screenHeight);
	    card=c;
        
    }
    @Override
    protected void onDraw(Canvas canvas){
    	super.onDraw(canvas);
//        Log.d(TAG, "onDraw, PH for "+player.getRealName());
        card.draw(canvas);
        paint.setColor(player.colorInt);
     	canvas.drawText(player.getRealName(), textWidth, 15, paint);
    	canvas.drawText(player.getScore()+"", textWidth, 35, paint);

    }
    
    public Rect getBounds() {
		return new Rect(0, 0, this.screenWidth, this.screenHeight);

	}  
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surface Created");
		initialized = true;
		setOnTouchListener(this);
		if(player.seat==4){
			game.updatePH();
		}
			
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surface Destroyed");
		initialized = false;
			
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d(TAG, "Touching at x="+event.getX()+", y="+event.getY()+" For "+player.getRealName());

		if(event.getAction()==MotionEvent.ACTION_UP){
			main.displayPlayerInfo(this.player, game.displayPlayerCards(this.player), player.sneakPeak);
			if(player.seat!=1){
				player.sneakPeak=true;
			}
		}
		return true;

	}

   

}
