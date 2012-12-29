package game.shad.tempus.hearts;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicReference;

import android.content.Context;
import android.util.Log;

public class GameThread extends Thread
{
	public static final String TAG = "Hearts--GameThread";

	public enum State {
		RUNNING(), PAUSED(), DEAD();
	}
	public enum AutoRunState {
		RUNNING(), PAUSED();
	}
	public volatile AtomicReference<State> state = new AtomicReference<GameThread.State>(State.PAUSED);
	public volatile AtomicReference<AutoRunState> autoRunState = new AtomicReference<GameThread.AutoRunState>(AutoRunState.PAUSED);

	public String eol = System.getProperty("line.separator");

    private final static int MAX_FPS = 50;
    private final static int MAX_FRAME_SKIPS = 5;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    
    private DecimalFormat df = new DecimalFormat("0.##");
    private final static int STAT_INTERVAL = 1000;
    private final static int FPS_HISTORY_NR = 10;
    private long statusIntervalTimer = 0l;
    private long totalFramesSkipped = 0l;
    private long framesSkippedPerStatCycle = 0l;
    
    private int frameCountPerStatCycle = 0;
    private long totalFrameCount = 0l;
    private double fpsStore[];
    private long statsCount = 0;
    private double averageFps = 0.0;
    
    private Context context;
    private Game game;
    private MainActivity main;
	private long AutoRunTime = 1000;
	private long lastTime = System.currentTimeMillis();

	public boolean fullAutoRun = false;

    
    public GameThread(Context context, MainActivity main, Game game, int autoRunTime){
        super();
        this.context = context;
        this.main = main;
        this.game = game;
        this.AutoRunTime=(1000*autoRunTime);//Starts as int, Need time in Milli
    }

    public void run(){
    	Thread.currentThread().setName("Craps Game Thread");
		
		while (state.get() != State.DEAD) {
			try{
				if(state.get() == State.PAUSED) {
					sleep(5000);
				}
				pause(42);
				if(autoRunState.get() == AutoRunState.RUNNING){
					if(lastTime <= System.currentTimeMillis()){
						Log.d(TAG, "Looping autorun");
						if(game.curPlayer!=null){
							if(fullAutoRun||!game.curPlayer.equals(game.p1)&&!game.trading){
								main.handler.post(new Runnable() {
									@Override
									public void run() {
										game.GO();								
									}
								});

							}
						}
						main.handler.post(new Runnable() {
							@Override
							public void run() {
								game.update();								
							}
						});
						updateLastTime();
					}
					
					
				}
  
            } catch(InterruptedException e){
				Log.d(TAG, "Interrupted Exception!!! state=" + state.get(), e);
            }
		}
    }
	
    private long lastUpdate = System.currentTimeMillis();
	private long timeElapsed = 1;
	private void pause(long ms) throws InterruptedException{
		Thread.sleep(Math.max(0, ms - System.currentTimeMillis() + lastUpdate));
	
		timeElapsed = System.currentTimeMillis() - lastUpdate;
		lastUpdate = System.currentTimeMillis();
	}
	
	public void updateLastTime(){
		lastTime=System.currentTimeMillis()+AutoRunTime; 
	}
	public void setAutoRunTime(int autoRunTime){
		this.AutoRunTime=(1000*autoRunTime);
		
	}
	public int getAutoRunTimeinMilli(){
		return (int) (this.AutoRunTime);
		
	}
   

}
