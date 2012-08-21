package game.shad.tempus.hearts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class mainMenu extends Activity{
	EditText et;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		et = (EditText) findViewById(R.id.playerName);
		et.setText("Your name");

    }

	public void onStartPressed(View v){
		Intent gameIntent  =new Intent(this, game.shad.tempus.hearts.Game.class);
		et = (EditText) findViewById(R.id.playerName);
		String  s = et.getText().toString();
		System.out.println(s);
    	gameIntent.putExtra("name", s);
    	gameIntent.putExtra("restart", true);
		RadioButton easy = (RadioButton) findViewById(R.id.easy);
		RadioButton medium = (RadioButton) findViewById(R.id.medium);
		RadioButton hard = (RadioButton) findViewById(R.id.hard);

		if(easy.isChecked()){
	    	gameIntent.putExtra("diff", 1);
		}
		else if(medium.isChecked()){
	    	gameIntent.putExtra("diff", 2);
		}
		else if(hard.isChecked()){
	    	gameIntent.putExtra("diff", 3);
		}	

		RadioButton shuffleDrop = (RadioButton) findViewById(R.id.dropShuffle);
		RadioButton shuffleSwap = (RadioButton) findViewById(R.id.randomSwap);
		
		if(shuffleDrop.isChecked()){
	    	gameIntent.putExtra("shuffle", 1);
		}
		else if(shuffleSwap.isChecked()){
	    	gameIntent.putExtra("shuffle", 2);
		}
		CheckBox playerHelper = (CheckBox) findViewById(R.id.playerHelper);
		boolean ph = playerHelper.isChecked();
		gameIntent.putExtra("playerHelper", ph);

		CheckBox voidHelper = (CheckBox) findViewById(R.id.trackVoidsBox);
		boolean vh = voidHelper.isChecked();
		gameIntent.putExtra("voidHelper", vh);
    	startActivity(gameIntent);
    	// finish();
	}
	public void onResumePressed(View v){
		Intent gameIntent  =new Intent(this, game.shad.tempus.hearts.Game.class);
		et = (EditText) findViewById(R.id.playerName);
		String  s = et.getText().toString();
		System.out.println(s);
    	gameIntent.putExtra("name", s);
    	gameIntent.putExtra("restart", false);
		RadioButton easy = (RadioButton) findViewById(R.id.easy);
		RadioButton medium = (RadioButton) findViewById(R.id.medium);
		RadioButton hard = (RadioButton) findViewById(R.id.hard);
		if(easy.isChecked()){
	    	gameIntent.putExtra("diff", 1);
		}
		else if(medium.isChecked()){
	    	gameIntent.putExtra("diff", 2);
		}
		else if(hard.isChecked()){
	    	gameIntent.putExtra("diff", 3);
		}	
		
		
		CheckBox voidHelper = (CheckBox) findViewById(R.id.trackVoidsBox);
		boolean vh = voidHelper.isChecked();
		gameIntent.putExtra("voidHelper", vh);
    	startActivity(gameIntent);
    	// finish();
	}
	
	public void textSelected(View v){
		
	}
	
	
	public void todo(View v){
		Toast.makeText(mainMenu.this, "Still in progress...",  Toast.LENGTH_SHORT).show();

	}
	

}