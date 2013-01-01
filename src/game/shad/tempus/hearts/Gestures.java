package game.shad.tempus.hearts;

import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class Gestures implements GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener
{

    private Game game;
    private MainActivity main;
    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 75;
    
    private DeckHolder deckView = null;
    private TableHolder tableView = null;
    public Gestures(Game game, MainActivity main)
    {
       this.game = game;
       this.main = main;
       this.deckView = game.getDeckHolder();
       this.tableView = game.getTableHolder();

    }

    @Override
    public boolean onDoubleTap(MotionEvent e)
    {
        //To select a card and send to pile
        Toast.makeText(main.getApplicationContext(), "Double Tap", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e)
    {
        // TODO Auto-generated method stub
    	
        return false;
    }
    
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e)
    {
        //Add for buttons or info??
    	int x = (int) e.getX();
    	int y = (int) e.getY();
        Rect bondsDV=deckView.getBounds();
        Rect bondsTV=tableView.getBounds();

        if(bondsDV.contains(x, y)){
        	int y2=y-bondsDV.top;
            Toast.makeText(main.getApplicationContext(), "DeckView", Toast.LENGTH_SHORT).show();

        	game.deckViewTouched(x, y2);
        }
        else if(bondsTV.contains(x, y)){
            Toast.makeText(main.getApplicationContext(), "TableView", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        //to move through cards in hand (fix sensitvity in up and down)
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    //Toast.makeText(game.getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
                    this.deckView.swipeLeft();
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    //Toast.makeText(game.getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
                    this.deckView.swipeRight();
                }
                else if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(game.getApplicationContext(), "Swipe up", Toast.LENGTH_SHORT).show();
                } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(game.getApplicationContext(), "Swipe down", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            // nothing
           }
        
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
