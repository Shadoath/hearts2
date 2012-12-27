package game.shad.tempus.hearts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.achartengine.chart.LineChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class History extends Activity{
	
	public static final String TAG = "Hearts--History";

    public TextView bottomText;
    public TextView bottomText2;
    public LinearLayout graphLayout;
    public Button lineGraphButton;
    public String loadPath = "";
    public File path;
    public int winnerCount = 1;
    public int totalWins =10;
    XYSeries p1Series;
    XYSeries p2Series;
    XYSeries p3Series;
    XYSeries p4Series;
    public LineChart pointsPerRound;
    public String winnerString ="";
    public int[] data1 = {0, 1, 1, 3, 3, 5, 6, 12, 14, 17, 20, 25};
    public int[] data2 = {0, 0, 0, 6, 8, 9, 23, 24, 25, 25, 27, 22};
    public int[] data3 = {0, 0, 0, 0, 7, 7, 12, 14, 14, 17, 17, 17};
    public int[] data4 = {0, 3, 3, 3, 16, 17, 23, 24, 14, 14, 14, 22};
    public JSONArray fileJsonArray;
    public ArrayList<String> trickPoints;
    public ArrayList<String> fileArrayString;

    //So far only 10 files for Saves.
    public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.historyportrait);

    	 findViewsByID();
     	 setupSettings();
    	 loadFile();
    	 //TODO create option for landscape mode.
    	 
    }
   
    public void parseData(View view){
 	   if(trickPoints==null){
			loadFile();
 	   }
 	  if(trickPoints!=null){
	 	   
	 	   int size=trickPoints.size();
		   data1 = new int[size];
		   data2 = new int[size];
		   data3 = new int[size];
		   data4 = new int[size];
	 	   setNewData(trickPoints);
 	  }
 	  else{
 		  Log.d(TAG, "Failed JSON SAVE");
 	  }
    	
    }
    
    public void setNewData(ArrayList<String> data){
    	Log.d(TAG, "Setting new Graph Data");
    	int plusSpot=0;
    	for(String d : data){
    		Log.d(TAG, "data="+data);
    		int hand=d.charAt(5);
    		if(d.charAt(6)!=' '){
    			hand=hand*10+d.charAt(6);
    			plusSpot++;
    		}
			Log.d(TAG, "hand="+hand);
			
    		int trick=d.charAt(13+plusSpot);
    		if(d.charAt(13+plusSpot)!='"'){
    			trick=trick*10+d.charAt(13+plusSpot);
    			plusSpot++;
    		}
			Log.d(TAG, "trick="+trick);

			int winnerSeat =d.charAt(19+plusSpot);
    		if(d.charAt(19+plusSpot)!='"'){
    			winnerSeat=winnerSeat*10+d.charAt(19+plusSpot);
    			plusSpot++;
    		}
			Log.d(TAG, "winnerSeat="+winnerSeat);
			if(d.charAt(22+plusSpot)=='-'){
				plusSpot++;
			}
			int points = d.charAt(22+plusSpot);
    		if(d.charAt(22+plusSpot)!='}'){
    			points=points*10+d.charAt(22+plusSpot);
    			plusSpot++;
    		}
			Log.d(TAG, "points="+points);
    	}
    	
    }
    
   public void showLineGraph (View view){
	   LineGraph line = new LineGraph();
	   Intent lineIntent = line.getInent(this);
	   startActivity(lineIntent); 
   }
    

    
    protected void setDatasetRenderer(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer){
 
    }

	
	private void setupSettings(){
    	switch(2){
    	case 1:
        	path = this.getCacheDir();
        	loadPath = path + "/winner"+winnerCount+".json";
        	Log.d(TAG, loadPath);
    	case 2:
        	path = this.getFilesDir();
        	loadPath = path + "/winner"+winnerCount+".json";
        	Log.d(TAG, loadPath);

    	case 3:
        	path = this.getExternalCacheDir();
        	loadPath = path + "/winner"+winnerCount+".json";
        	Log.d(TAG, loadPath);

        
    	}
    	Log.d(TAG, "settings Saved");
    }
    
    /**
     * Loads the Save file
     * @param v
     */
    public void loadFile(){
		String out=""; 
		trickPoints.clear();
    	try
    	  {
    	     FileReader fro = new FileReader(loadPath);
    	     BufferedReader bro = new BufferedReader( fro );
    	     
    	 
   	     // declare String variable and prime the read
    	    String stringRead = bro.readLine(); 

    	    while( stringRead != null ) // end of the file
    	    {
    	    	trickPoints.add(stringRead);	 
	    	    out+=stringRead;
    	      
    	        stringRead = bro.readLine( );  // read next line
    	     }
    	     bro.close( );
			} catch (IOException e) {
 				e.printStackTrace();
				
		}
		Log.d(TAG, out.length()+out);
    	
		winnerString=out;
		bottomText.setText("Winner file:"+winnerCount);
		bottomText2.setText("loaded file="+out);
    }
        
    public void loadNextSave(View v){
	    winnerCount++;
	    if(winnerCount>10 )
	    	winnerCount=1;
    	loadPath = path + "/winner"+winnerCount+".json";
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
    	loadPath = path + "/winner"+winnerCount+".json";
	    loadFile();

    }
    
    public void findViewsByID(){
    	bottomText = (TextView) findViewById(R.id.bottomTV);
    	bottomText2 = (TextView) findViewById(R.id.bottomTV2);
    	graphLayout = (LinearLayout) findViewById(R.id.graphLayout);
    	lineGraphButton = (Button) findViewById(R.id.lineGraphButton);
    	
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
