package game.shad.tempus.hearts;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class Gestures implements GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener
{

    private HeartsActivity view;
    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    public Gestures(HeartsActivity view)
    {
       this.view = view;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e)
    {
        //To select a card and send to pile
        Toast.makeText(view.getApplicationContext(), "Double Tap", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(view.getApplicationContext(), "Single Click", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY)
    {
        //to move through cards in hand (fix sensitvity in up and down)
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(view.getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(view.getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
                }
                else if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(view.getApplicationContext(), "Swipe up", Toast.LENGTH_SHORT).show();
                } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(view.getApplicationContext(), "Swipe down", Toast.LENGTH_SHORT).show();
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
