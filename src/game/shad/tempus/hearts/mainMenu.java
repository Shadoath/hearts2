package game.shad.tempus.hearts;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainMenu extends Activity{
	public static final String TAG = "Hearts--Main";
	private int AITime = 2;
	EditText et;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    	et = (EditText) findViewById(R.id.playerName);

    }
 
	public void onStartPressed(View v){
		Intent gameIntent  =new Intent(this, game.shad.tempus.hearts.MainActivity.class);
		Bundle gameBundle = new Bundle();
		et = (EditText) findViewById(R.id.playerName);
		String  s = et.getText().toString();
		System.out.println(s);
    	gameBundle.putString("name", s);
    	gameBundle.putBoolean("restart", true);
		RadioButton easy = (RadioButton) findViewById(R.id.easy);
		RadioButton medium = (RadioButton) findViewById(R.id.medium);
		RadioButton hard = (RadioButton) findViewById(R.id.hard);

		if(easy.isChecked()){
	    	gameBundle.putInt("diff", 1);
		}
		else if(medium.isChecked()){
	    	gameBundle.putInt("diff", 2);
		}
		else if(hard.isChecked()){
	    	gameBundle.putInt("diff", 3);
		}	

		RadioButton shuffleDrop = (RadioButton) findViewById(R.id.dropShuffle);
		RadioButton shuffleSwap = (RadioButton) findViewById(R.id.randomSwap);
		RadioButton mixShuffle = (RadioButton) findViewById(R.id.mixShuffle);

		if(shuffleDrop.isChecked()){
	    	gameBundle.putInt("shuffle", 1);
		}
		else if(shuffleSwap.isChecked()){
	    	gameBundle.putInt("shuffle", 2);
		}
		else if(mixShuffle.isChecked()){
	    	gameBundle.putInt("shuffle", 3);
		}

		CheckBox cardCounter = (CheckBox) findViewById(R.id.cardCounterCheckBox);
		boolean cC = cardCounter.isChecked();
		gameBundle.putBoolean("cardCounter", cC);

		CheckBox screenMode = (CheckBox) findViewById(R.id.PortaitMode);
		boolean sMode = screenMode.isChecked();
		gameBundle.putBoolean("screenMode", sMode);

		Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();
        int height = display.getHeight();
        gameBundle.putInt("height", height);
        gameBundle.putInt("width", width);
        gameBundle.putInt("aiTime", AITime);
        gameIntent.putExtras(gameBundle);
    	startActivity(gameIntent);
	
	}
    	
	
	
    /**
     * Testing Code
	/*/
	public void onResumePressed(View v){
		Intent gameIntent  =new Intent(this, game.shad.tempus.hearts.MainActivity.class);
		Bundle gameBundle = new Bundle();
		et = (EditText) findViewById(R.id.playerName);
		String  s = et.getText().toString();
		System.out.println(s);
    	gameBundle.putString("name", s);
    	gameBundle.putBoolean("restart", false);//Herp derp only changed line
		RadioButton easy = (RadioButton) findViewById(R.id.easy);
		RadioButton medium = (RadioButton) findViewById(R.id.medium);
		RadioButton hard = (RadioButton) findViewById(R.id.hard);
		if(easy.isChecked()){
	    	gameBundle.putInt("diff", 1);
		}
		else if(medium.isChecked()){
	    	gameBundle.putInt("diff", 2);
		}
		else if(hard.isChecked()){
	    	gameBundle.putInt("diff", 3);
		}	

		RadioButton shuffleDrop = (RadioButton) findViewById(R.id.dropShuffle);
		RadioButton shuffleSwap = (RadioButton) findViewById(R.id.randomSwap);
		RadioButton mixShuffle = (RadioButton) findViewById(R.id.mixShuffle);

		if(shuffleDrop.isChecked()){
	    	gameBundle.putInt("shuffle", 1);
		}
		else if(shuffleSwap.isChecked()){
	    	gameBundle.putInt("shuffle", 2);
		}
		else if(mixShuffle.isChecked()){
	    	gameBundle.putInt("shuffle", 3);
		}

		CheckBox cardCounter = (CheckBox) findViewById(R.id.cardCounterCheckBox);
		boolean cC = cardCounter.isChecked();
		gameBundle.putBoolean("cardCounter", cC);
        Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();
        int height = display.getHeight();
        gameBundle.putInt("height", height);
        gameBundle.putInt("width", width);
        gameIntent.putExtras(gameBundle);
    	startActivity(gameIntent);
    	//TODO finish();
	}
	
	public void textSelected(View v){
		
	}
	
	public void onHistoryPressed(View v){
		Intent gameIntent  =new Intent(this, game.shad.tempus.hearts.History.class);
		startActivity(gameIntent);
	}
	
	public void todo(View v){
		Toast.makeText(MainMenu.this, "Still in progress...",  Toast.LENGTH_SHORT).show();

	}
	

}