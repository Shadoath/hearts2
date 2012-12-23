package game.shad.tempus.hearts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.achartengine.model.XYSeries;

import android.app.Activity;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class History extends  Activity{
	
	public static final String TAG = "Hearts--History";

    public TextView bottomText;
    public TextView bottomText2;
    public String loadPath = "";
    public File path;
    public int winnerCount = 1;
    public int totalWins =10;
    XYSeries p1Series;
    XYSeries p2Series;
    XYSeries p3Series;
    XYSeries p4Series;
    public String winnerString ="";

    //So far only 10 files for Saves.
    public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.historyportrait);

    	 findViewsByID();
     	 setupSettings();
    	 loadFile();
    	 //TODO create option for landscape mode.
    }

	
	private void setupSettings(){
    	switch(2){
    	case 1:
        	path = this.getCacheDir();
        	loadPath = path + "/winner"+winnerCount+".txt";
        	Log.d(TAG, loadPath);
    	case 2:
        	path = this.getApplicationContext().getFilesDir();
        	loadPath = path + "/winner"+winnerCount+".txt";
        	Log.d(TAG, loadPath);

    	case 3:
        	path = this.getExternalCacheDir();
        	loadPath = path + "/winner"+winnerCount+".txt";
        	Log.d(TAG, loadPath);

        
    	}
    	Log.d(TAG, "settings Saved");
    }
    
    /**
     * Loads the Save file
     * @param v
     */
    public void loadFile(){
		String out=" Last Save \r\n "; 
    	try
    	  {
    	     FileReader fro = new FileReader(loadPath);
    	     BufferedReader bro = new BufferedReader( fro );
    	 
   	      	String split;
   	     // declare String variable and prime the read
    	    String stringRead = bro.readLine( ); 

    	    while( stringRead != null ) // end of the file
    	    {
    	    	 split=stringRead;
    	    	 if(split.length()>20){
	    	    	 
	    	         out+=stringRead+ "\r\n";
    	    	 }
    	         
    	        stringRead = bro.readLine( );  // read next line
    	     }
    	     bro.close( );
			} catch (IOException e) {
 				e.printStackTrace();
				
		}
	winnerString=out;
	bottomText2.setText("loaded file="+out);
	bottomText.setText("Winner file:"+winnerCount);
    }
        
    public void loadNextSave(View v){
	    winnerCount++;
	    if(winnerCount>10 )
	    	winnerCount=1;
    	loadPath = path + "/winner"+winnerCount+".txt";
	    loadFile();
	    loadGraph();
    }
    
    public void onDeletePressed(View v){
    	File file = new File(loadPath);
    	if(file.exists()){
    		file.delete();
    		bottomText.setText("Winner file:"+winnerCount+" was deleted.");
 		   bottomText2.setText("Empty");
    	}
    }
    
    public void onPrevPressed(View v){
	    winnerCount--;
	    if(winnerCount<1)
	    	winnerCount=totalWins;
    	loadPath = path + "/winner"+winnerCount+".txt";
	    loadFile();

    }
    
    public void findViewsByID(){
    	bottomText = (TextView) findViewById(R.id.bottomTV);
    	bottomText2 = (TextView) findViewById(R.id.bottomTV2);

    }
   
    

    public void loadGraph(){
//    p1Series= new XYSeries("P1");	
//    p2Series= new XYSeries("P2");	
//    p3Series= new XYSeries("P3");	
//    p4Series= new XYSeries("P4");
    String[] wonTricks =winnerString.split(",");
    for(String s: wonTricks){
    	Log.d(TAG, s);
    }
    
    
    }
    
}
