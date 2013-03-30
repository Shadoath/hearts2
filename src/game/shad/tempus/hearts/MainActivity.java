package game.shad.tempus.hearts;







import game.shad.tempus.hearts.GameThread.AutoRunState;
import game.shad.tempus.hearts.GameThread.State;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Starts the craps game,
 * 
 * @author Shadoath
 *
 */
public class MainActivity extends Activity {
	/** For use with Android logging functions */
	public static final String TAG = "Hearts--Main";

	public Game game;
	public GameThread gt;
    private Toast myToast;
    private ProgressDialog progressDialog;  
    private ProgressBar progressBar;  

	public Handler handler; //Handler to UI thread to post tasks to.
	private WakeLock mWakeLock;
	private Bundle gameBundle;
	private Context myContext;
	private MainActivity main;
	private boolean TESTING = true;
	private float backPressedTimer = 0;
	
    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent gameIntent = getIntent();
        gameBundle = gameIntent.getExtras();
        Log.d(TAG, "onCreate");
        myContext = this.getApplicationContext();
    	main= this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    	setContentView(R.layout.startup);

//    	if(gameBundle.getBoolean("screenMode", true)){
//        	Log.d(TAG, "Portait Mode");
//        	
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            setContentView(R.layout.tableportrait);
//
//        }
//        else{
//        	Log.d(TAG, "LandScape Mode");
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        	setContentView(R.layout.tablelandscape);
//        }

//        //TODO create option for landscape mode.
//        if(gameBundle.getBoolean("screenMode", true)){
//        	Log.d(TAG, "Portait Mode");
//        	
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            setContentView(R.layout.tableportrait);
//
//        }
//        else{
//        	Log.d(TAG, "LandScape Mode");
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        	setContentView(R.layout.tablelandscape);
//        }
        myContext = getBaseContext();
	
    	
    	game = new Game(gameBundle, myContext, main);
		myToast  = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);
		gt = new GameThread(myContext, main, game, gameBundle.getInt("aiTime"));


		new LoadResources().execute();


    }
    private class LoadResources extends AsyncTask<Integer, Integer, Integer> {
    	@Override
    	protected Integer doInBackground(Integer... params) {
			Log.d(TAG, "update 15");
    		updateProgressBar(15);
            Log.d(TAG, "initialized game view");
    		game.loadResources(MainActivity.this);
            Log.d(TAG, "created Views");

			updateProgressBar(99);
			
            Log.d(TAG, "created sound service");
    		return null;
    	}
    	
    	@Override
    	protected void onPostExecute(Integer result) {
            if(gameBundle.getBoolean("screenMode", true)){
            	Log.d(TAG, "Portait Mode");
            	
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                MainActivity.this.setContentView(R.layout.tableportrait);

            }
            else{
            	Log.d(TAG, "LandScape Mode");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                MainActivity.this.setContentView(R.layout.tablelandscape);
            }
    		game.findViewsById();
            game.createDeckTableViews();
            game.heartsMe();
    		gt.start();
    	}
    }
    
    public void updateProgressBar(int i){
    	if(progressBar==null){
    		Log.d(TAG, "Progress bar is null, attempting to find.");
    		progressBar=(ProgressBar) findViewById(R.id.progressBar);
    	}
    	progressBar.setProgress(i);
    }
    
	@Override
    protected void onStart(){
    	super.onStart();
    	Log.d(TAG, "onStart");       

//        if(gt==null){
//        	return;
//        }
        if(gt.state.compareAndSet(State.PAUSED, State.RUNNING)){
	        gt.interrupt();
      	}
        gt.autoRunState.compareAndSet(AutoRunState.PAUSED,  AutoRunState.RUNNING);


  	}
	
    @Override
    protected void onPause() {
	    Log.d(TAG, "onPause");
	
	    super.onPause();
		if(gt.state.compareAndSet(State.RUNNING, State.PAUSED)) {
			gt.interrupt();
		}
            
    }
        
    @Override
    protected void onResume() {
    	Log.d(TAG, "onResume");
    	super.onResume();
        handler= new Handler();

    }
    
    @Override
    public void onStop() {
    	Log.d(TAG, "onStop");
    	super.onStop();
    }
    
    @Override
    protected void onDestroy() {
    	
    	Log.d(TAG, "onDestroy");

    	super.onDestroy();
    	gt.state.set(State.DEAD);
    	gt.interrupt();
    }
    
    @Override
    public void onRestart(){
    	super.onRestart();
    	Log.d(TAG, "onRestart");
    	Log.d(TAG, "gt.state="+gt.state);
    }

    @Override 
    public void onBackPressed(){
    	if(System.currentTimeMillis()-backPressedTimer<1000){
        	super.onBackPressed();
        	super.finish();
    	}
    	else{
        	backPressedTimer=System.currentTimeMillis();
        	Toast.makeText(this, "Double tap 'back' to exit", Toast.LENGTH_SHORT).show();

    	}

    	//TODO save the game state.
    }
    
	public void onSettingsPressed(View v){
		SharedPreferences sp = getSharedPreferences("Settings", 0);
		getPreferences(R.menu.preferences);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.gamemenu, menu);

	    return true;
    	}
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch(item.getItemId()){

    	case R.id.help:
    		showHelp();
    		//TODO test go() code to get what a bot would play in your place.
    		break;
    	case R.id.debug:
    		debug();
    		break;
    	case R.id.restart:
    		//TODO fix this
    		game.reset();
    		game.update();
    		game.heartsMe();
    		
    		break;
    	case R.id.autoRun://TODO Remove before release.
    		Log.d(TAG, "FULL AUTO RUN PRESSED");

    		gt.fullAutoRun=!gt.fullAutoRun;
    		if(!gt.autoRunState.compareAndSet(AutoRunState.PAUSED,  AutoRunState.RUNNING))
    			gt.autoRunState.compareAndSet(AutoRunState.RUNNING,  AutoRunState.PAUSED);
			gt.setAutoRunTime(.5);
			break;

    	case R.id.normalRun:
    		Log.d(TAG, "Normal Run");
    		if(gt.fullAutoRun){
    			gt.fullAutoRun=false;
    		}
    		if(!gt.autoRunState.compareAndSet(AutoRunState.PAUSED,  AutoRunState.RUNNING))
    			gt.autoRunState.compareAndSet(AutoRunState.RUNNING,  AutoRunState.PAUSED);
    		break;
    		
    	case R.id.thinkTime:
    		new AlertDialog.Builder(this).setTitle("AI Thinking Time")
    		.setMessage("Current time is set at "+gt.getAutoRunTimeinMilli()/1000+" seconds")
    		.setPositiveButton("One Second", new OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				gt.setAutoRunTime(1);
    				dialog.dismiss();
    			}
    		}).setNeutralButton("Three Seconds", new OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				gt.setAutoRunTime(3);
    				dialog.dismiss();
    			}
    		}).setNegativeButton("Five Seconds", new OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				gt.setAutoRunTime(5);
    				dialog.dismiss();
    			}
    		}).show();
    		    		
    	}
    	
    	return true;
    }
    /**
     * Testing Code
     * @param v
     */
    public void debug(){
		game.displayPlayerCards(game.p1);
		game.displayPlayerCards(game.p2);
		game.displayPlayerCards(game.p3);
		game.displayPlayerCards(game.p4);
		Toast.makeText(this, "Printed Hands to LOG",  Toast.LENGTH_SHORT).show();
//		game.deckHolder.updateDeck(game.p1.getDeck());
		game.update();
		game.updatePlayerInfo();
			
//		Log.d(TAG, game.writeJSON().toString());
		game.saveGameStats("winner");


	}
   		
	public void onExitPressed(View v){
		finish();
	}
	
	
	public boolean onTouchEvent(MotionEvent e){
		//TODO fix gestures
		return false;
        //return gestures.onTouchEvent(e);
    }

	
	public void onPlayCardPressed(View v){
		if(game.trading){
			//Player picked the cards
			game.p1.tradingCardsRemoved=false;
			game.tradeCards();
			return;
		}
		else if(game.curPlayer==game.p1){
				if(game.playing){
					if(game.playerHelperInt>0||game.cardToPlay!=null){
						game.playerHelperInt=0;
						gt.updateLastTime();
						game.GO();
						
					}
					else{
						game.playerHelperInt++;
						game.playerHelperIntTotal++;
						myToast.cancel();
						Card cardToChooseCard = game.p1.go(game.round, game.tableTrick);
						game.slidingDeckHolder.setCardTouched(cardToChooseCard, true);
						myToast.setText("Pick a card, or press 'Play Card' again to let the game play the "+cardToChooseCard.name);
				        myToast.setDuration(Toast.LENGTH_LONG);
						myToast.show();
				        myToast.setDuration(Toast.LENGTH_SHORT);
	
		
					}
				}
			}
			else{
				gt.updateLastTime();
				game.GO();
			}
		}

	public void onFixHandPressed(View v){
		game.slidingDeckHolder.setDeck(game.p1.getArrayListDeck());
	}
	
    private void showHelp() {
    	new AlertDialog.Builder(this).setTitle("Basic Rules of Hearts").setMessage(R.string.howToPlay).setNeutralButton("Ok", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
    }
    
    public void roundStatsPressed(View v){
    	new AlertDialog.Builder(this).setTitle("Cards played this round").setMessage("Jack played ="+game.jackFound+"\nQueen played ="+game.queenFound+"\nHearts Broken ="+game.heartsBroken+"\nRound Points ="+game.roundScore+"\n\n"+game.roundCardString)
    	.setNeutralButton("Ok", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
    }
    
    public void displayPlayerInfo(final Player player, final String info, final boolean sneakPeak){
	//TODO add boolean to not open.
    handler.post(new Runnable() {
    		public void run() {
    			showPlayerInfo(player, info, sneakPeak);
    		}
    	});
    }
     
    private void showPlayerInfo(Player player, String info, final boolean sneakPeak) {
    	String PlayerInfo = player.getRealName()+" Points= "+player.getScore()+" AI lvl="+player.getAISmarts();
    	
    	if(!sneakPeak){	//Or Player 1
	    	new AlertDialog.Builder(this).setTitle(PlayerInfo).setMessage(info).setPositiveButton("Oops!", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).show();
    	}
    	else{    	
    		new AlertDialog.Builder(this).setTitle(PlayerInfo).setMessage(info).setNegativeButton("+10 Points for peeking!!", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(TESTING){
						//TODO set to false before release
						Log.d(TAG, "Testing no Points added!");
					}
					else{
						game.p1.addToScore(10);
					}
					dialog.dismiss();
				}
			}).show();
    	}
		
    }
    
    public void displayTableInfo(final String tableInfo){
   	//TODO add boolean to not open.
    handler.post(new Runnable() {
    		public void run() {
    			showTableInfo(tableInfo);
    		}
    	});
    }
     
    private void showTableInfo(String tableInfo) {
    	new AlertDialog.Builder(this).setTitle("Table Cards").setMessage(tableInfo).setNeutralButton("Ok", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
    }
}






