package game.shad.tempus.hearts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.achartengine.GraphicalView;
import org.achartengine.chart.LineChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.json.JSONArray;

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
    public LinearLayout graphView;
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
    public String[] fileArrayString;
    public String winningPlayer = "nobody";

    //So far only 10 files for Saves.
    public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         setContentView(R.layout.historyportrait);
         winnerString = "";
    	 findViewsByID();
     	 setupSettings();
    	 loadFile();
    	 //TODO create option for landscape mode.
    	 
    }
   
    public boolean parseData(){
		if(winnerString==null||winnerString.length()==0){
			loadFile();
		}
		Log.d(TAG, "parsing data for:"+winnerString);
		   
		fileArrayString=winnerString.split("\\$");
		int size = fileArrayString.length;
		Log.d(TAG,"Size="+size);
		if(size<5){
			Log.d(TAG, "Not enough data to build a graph");
			bottomText.setText("Not enough data to Parse.");
			return false;
		}
		size++;
		data1 = new int[size];
		data2 = new int[size];
		data3 = new int[size];
		data4 = new int[size];
		data1[0] = 0;
		data2[0] = 0;
		data3[0] = 0;
		data4[0] = 0;
		setNewData(fileArrayString);
		return true;
    }
    
    public void setNewData(String[] data){
    	//TODO dont error out when reading who WON!!
    	Log.d(TAG, "Setting new Graph Data, size ="+data.length);
    	int count = -1;
    	
    	for(String d : data){
    		if(d.charAt(0)=='H'){
	    		boolean negativePoints=false;
	    		count++;
	        	int plusSpot=0;
	
	    		Log.d(TAG, "data="+d);
	    		int hand=d.charAt(5) - '0';
	    		if(d.charAt(6)!=' '){
	    			hand=hand*10;
	    			hand+=d.charAt(6) - '0';
	    			plusSpot++;
	    			Log.d(TAG, "plusSpot++ hand");
	    		}
				Log.d(TAG, "hand="+hand);
				
	    		int trick=d.charAt(13+plusSpot) - '0';
	    		if(d.charAt(14+plusSpot)!=':'){
	    			trick=trick*10;
	    			trick+=d.charAt(14+plusSpot) - '0';
	    			plusSpot++;
	    			Log.d(TAG, "plusSpot++ Trick");
	
	
	    		}
				Log.d(TAG, "trick="+trick);
	
				int winnerSeat = d.charAt(18+plusSpot) - '0';
	    		Log.d(TAG, "winnerSeat="+winnerSeat);
				
				if(d.charAt(21+plusSpot)=='-'){
					negativePoints=true;
					plusSpot++;
					Log.d(TAG, "negative Points");
	
				}
				int points = d.charAt(21+plusSpot) - '0';
	    		if(d.charAt(22+plusSpot)!='}'){
	    			points=points*10;
	    			points+=d.charAt(22+plusSpot) - '0';
	    			plusSpot++;
	    			Log.d(TAG, "plusSpot++ Points");
	
	        		if(d.charAt(22+plusSpot)!='}'){
	        			points=points*10;
	        			points+=(int)d.charAt(22+plusSpot) - '0';
	        			Log.d(TAG, "LOTS OF POINTS");
	
	        		}
	    		}
	    		if(negativePoints){
	    			points*=-1;
	    		}
				Log.d(TAG, "points="+points);
				if(points==26){
					
				}
				addToData(hand, trick, winnerSeat, points);
    		}
    		else{
    			int dataSize = data1.length-1;
    			int dataSizeMinusOne = dataSize-1;
    	    	data1[dataSize]=data1[dataSizeMinusOne];
    	    	data2[dataSize]=data2[dataSizeMinusOne];
    			data3[dataSize]=data3[dataSizeMinusOne];
    			data4[dataSize]=data4[dataSizeMinusOne];
    			this.winningPlayer=d.toString();
    			
				}
			
			
    	}
		bottomText.setText("Loaded winner file:" + winnerCount);
    	//makeNewLineGraph();
    	
    }
    
    public void makeNewLineGraph(String title){
    	if (parseData()){
	 	   if(data1.length>7 && data2.length==data3.length){	//could also check length of 2 and 4
	 		  LineGraph line = new LineGraph();
		 	   Log.d(TAG, "data1.length="+data1.length);
		 	   Log.d(TAG, "data1.data="+data1.toString());
	 		   Intent lineIntent = line.getInent(this, data1, data2, data3, data4, title);
		 	   startActivity(lineIntent); 
	 	   }
    	}
 	   else{
			bottomText.setText("Not enough data to build a graph");
 	   }
    }
    public boolean drawGraphInView(){
		if (parseData()){
    		graphView.removeAllViews();
			LineGraph line = new LineGraph();
	 	    Log.d(TAG, "data1.length="+data1.length);
	 	    Log.d(TAG, "data1.data="+data1.toString());
	 	    GraphicalView gView = line.getView(this, data1, data2, data3, data4);
	 	    
	 	    graphView.addView(gView);
	 	    return true;
		}
		else{
			bottomText.setText("Not enough data to build a graph");
			
		}
		return false;
    }
    
    public void addToData(int hand, int trick, int winnerSeat, int points){
    	int count = (hand-1)*13+trick;
    	int countMinusOne = count-1; 
    	data1[count]=data1[countMinusOne];
    	data2[count]=data2[countMinusOne];
		data3[count]=data3[countMinusOne];
		data4[count]=data4[countMinusOne];
    	switch(winnerSeat){
    	case 1:
    		points += data1[countMinusOne];
    		data1[count]=points;
    		break;
    	case 2:
    		points += data2[countMinusOne];
    		data2[count]=points;
    		break;
    	case 3:
    		points += data3[countMinusOne];
  		    data3[count]=points;
    		break;
    	case 4:
    		points += data4[countMinusOne];
    		data4[count]=points;
    		break;
    	}
    	Log.d(TAG, "P:"+winnerSeat+" Added data at:"+count+" points:"+points);
    }
    
    /**
     * When the moon is shoot everyone one else gets 26 points and 26 is taken from shooters score
     * 
     * @param hand
     * @param trick
     * @param winnerSeat
     * @param points
     */
    public void addToDataMoonShot(int hand, int trick, int winnerSeat, int points){
    	int count = (hand-1)*13+trick;
    	int countMinusOne = count-1; 
    	data1[count]=data1[countMinusOne]+points;
    	data2[count]=data2[countMinusOne]+points;
		data3[count]=data3[countMinusOne]+points;
		data4[count]=data4[countMinusOne]+points;
    	switch(winnerSeat){
    	case 1:
    		
    		data1[count]=data1[countMinusOne]-points;
    		break;
    	case 2:
    		data2[count]=data2[countMinusOne]-points;
    		break;
    	case 3:
  		    data3[count]=data3[countMinusOne]-points;
    		break;
    	case 4:
    		data4[count]=data4[countMinusOne]-points;
    		break;
    	}
    	Log.d(TAG, "P:"+winnerSeat+" Added data at:"+count+" points:-"+points);
    }
    public void showLineGraph (View view){
	   makeNewLineGraph(winningPlayer);
    }
        
    protected void setDatasetRenderer(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer){
 
    }
	
	private void setupSettings(){
    	switch(2){
    	case 1:
        	path = this.getCacheDir();
        	loadPath = path + "/winner"+winnerCount+".txt";
        	Log.d(TAG, loadPath);
        	break;
    	case 2:
        	path = this.getFilesDir();
        	loadPath = path + "/winner"+winnerCount+".txt";
        	Log.d(TAG, loadPath);
        	break;
    	case 3:
        	path = this.getExternalCacheDir();
        	loadPath = path + "/winner"+winnerCount+".txt";
        	Log.d(TAG, loadPath);
        	break;
        
    	}
    	Log.d(TAG, "settings Saved");
    }
    
    /**
     * Loads the Save file
     * @param v
     */
    public void loadFile(){
		String out=""; 
		winningPlayer="nobody";
    	try
    	  {
    	     FileReader fro = new FileReader(loadPath);
    	     BufferedReader bro = new BufferedReader( fro );
    	     
    	 
   	     // declare String variable and prime the read
    	    String stringRead = bro.readLine(); 

    	    while( stringRead != null ) // end of the file
    	    {
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

		if(drawGraphInView()){
			bottomText2.setText("");
			int lastSpot = fileArrayString.length-1;
			
			if(winningPlayer=="nobody"){
				bottomText2.append("No Winner");
	
			}
		
			else{
				bottomText2.setText("loaded file="+out);
			}
		}
    }
        
    public void loadNextSave(View v){
	    winnerCount++;
	    if(winnerCount>10 )
	    	winnerCount=1;
    	loadPath = path + "/winner"+winnerCount+".txt";
	    loadFile();
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
    	graphView = (LinearLayout) findViewById(R.id.graphLayout);
    	lineGraphButton = (Button) findViewById(R.id.lineGraphButton);
    	
    }
      
}
