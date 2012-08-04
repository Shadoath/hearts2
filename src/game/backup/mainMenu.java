package game.backup;

import game.shad.tempus.hearts.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class mainMenu extends Activity{

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	public void onStartPressed(View v){
		Intent gameIntent  =new Intent(this, game.shad.tempus.hearts.HeartsActivity.class);

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

		
    	startActivity(gameIntent);
    	finish();
	}

}