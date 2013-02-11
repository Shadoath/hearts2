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
    public boolean graphable = false;
    XYSeries p1Series;
    XYSeries p2Series;
    XYSeries p3Series;
    XYSeries p4Series;
    public LineChart pointsPerRound;
    public String winnerString;
    public int[] p1Data = {0, 1, 1, 3, 3, 5, 6, 12, 14, 17, 20, 25};
    public int[] p2Data = {0, 0, 0, 6, 8, 9, 23, 24, 25, 25, 27, 22};
    public int[] p3Data = {0, 0, 0, 0, 7, 7, 12, 14, 14, 17, 17, 17};
    public int[] p4Data = {0, 3, 3, 3, 16, 17, 23, 24, 14, 14, 14, 22};
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
    	p1Data[count]=p1Data[countMinusOne]+points;
    	p2Data[count]=p2Data[countMinusOne]+points;
		p3Data[count]=p3Data[countMinusOne]+points;
		p4Data[count]=p4Data[countMinusOne]+points;
    	switch(winnerSeat){
    	case 1:
    		
    		p1Data[count]=p1Data[countMinusOne]-points;
    		break;
    	case 2:
    		p2Data[count]=p2Data[countMinusOne]-points;
    		break;
    	case 3:
  		    p3Data[count]=p3Data[countMinusOne]-points;
    		break;
    	case 4:
    		p4Data[count]=p4Data[countMinusOne]-points;
    		break;
    	}
    	Log.d(TAG, "P:"+winnerSeat+" Added data at:"+count+" points:-"+points);
    }
    public void showLineGraph (View view){
    	drawGraphInView();
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
		parseData();
		drawGraphInView();
//		if(drawGraphInView()){
//			bottomText2.setText("");
//			int lastSpot = fileArrayString.length-1;
//			
//			if(winningPlayer=="nobody"){
//				bottomText2.append("No Winner");
//	
//			}
//		
//			else{
//				bottomText2.setText("loaded file="+out);
//			}
//		}
    }
        
    public boolean parseData(){
		if(winnerString==null){
			loadFile();
		}
		Log.d(TAG, "parsing data for:"+winnerString);
		   
		fileArrayString=winnerString.split("\\$");
		int size = fileArrayString.length;
		Log.d(TAG,"Size="+size);
		if(size<5){
			Log.d(TAG, "Not enough data to build a graph");
			bottomText.setText("Not enough data to Parse.");
			graphable = false;
			return false;
		}
		size++;
		p1Data = new int[size];
		p2Data = new int[size];
		p3Data = new int[size];
		p4Data = new int[size];
		p1Data[0] = 0;
		p2Data[0] = 0;
		p3Data[0] = 0;
		p4Data[0] = 0;
		setNewData(fileArrayString);
		graphable =true;
		return true;
    }
        
    /**
     * Sets the data after it is parsed 
     * so that the arrays can be used to create a graph
     * @param data
     */
    public void setNewData(String[] data){
    	//TODO dont error out when reading who WON!!
    	Log.d(TAG, "Setting new Graph Data, size ="+data.length);
    	
    	for(String d : data){
    		if(d!=null)
    		if(d.charAt(0)=='H'){
	    		boolean negativePoints=false;
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
    			int dataSize = p1Data.length-1;
    			int dataSizeMinusOne = dataSize-1;
    	    	p1Data[dataSize]=p1Data[dataSizeMinusOne];
    	    	p2Data[dataSize]=p2Data[dataSizeMinusOne];
    			p3Data[dataSize]=p3Data[dataSizeMinusOne];
    			p4Data[dataSize]=p4Data[dataSizeMinusOne];
    			this.winningPlayer=d.toString();
    			
				}
			
			
    	}
//		bottomText.setText("Loaded winner file:" + winnerCount);
    	
    }
    
    /**
     * Save one Tricks data to arrays.
     * @param hand
     * @param trick
     * @param winnerSeat
     * @param points
     */
    public void addToData(int hand, int trick, int winnerSeat, int points){
    	int count = (hand-1)*13+trick;
    	int countMinusOne = count-1; 
    	if(points==26||points==-26){//Don't copy old data, Points for moon being shot.
    		switch(winnerSeat){
        	case 1:
        		points += p1Data[count];
        		p1Data[count]=points;
        		break;
        	case 2:
        		points += p2Data[count];
        		p2Data[count]=points;
        		break;
        	case 3:
        		points += p3Data[count];
      		    p3Data[count]=points;
        		break;
        	case 4:
        		points += p4Data[count];
        		p4Data[count]=points;
        		break;
        	}
    	}
    	else{
	    	p1Data[count]=p1Data[countMinusOne];
	    	p2Data[count]=p2Data[countMinusOne];
			p3Data[count]=p3Data[countMinusOne];
			p4Data[count]=p4Data[countMinusOne];
	    	switch(winnerSeat){
	    	case 1:
	    		points += p1Data[countMinusOne];
	    		p1Data[count]=points;
	    		break;
	    	case 2:
	    		points += p2Data[countMinusOne];
	    		p2Data[count]=points;
	    		break;
	    	case 3:
	    		points += p3Data[countMinusOne];
	  		    p3Data[count]=points;
	    		break;
	    	case 4:
	    		points += p4Data[countMinusOne];
	    		p4Data[count]=points;
	    		break;
	    	}
    	}
    	Log.d(TAG, "P:"+winnerSeat+" Added data at:"+count+" points:"+points);
    }

    
    /**
     * Starts a new intent with a graph as the view.
     * @param title
     */
    public void makeNewLineGraph(String title){
    	if (graphable){
	 	   if(p1Data.length>7 && p2Data.length==p3Data.length){	//could also check length of 2 and 4
	 		  LineGraph line = new LineGraph();
		 	   Log.d(TAG, "data1.length="+p1Data.length);
		 	   Log.d(TAG, "data1.data="+p1Data.toString());
	 		   Intent lineIntent = line.getInent(this, p1Data, p2Data, p3Data, p4Data, title);
		 	   startActivity(lineIntent); 
	 	   }
    	}
 	   else{
			bottomText.setText("Not enough data to build a graph");
 	   }
    }
    
    /**
     * Draw the graph in the main view
     * @return
     */
    public boolean drawGraphInView(){
		if (graphable){
    		graphView.removeAllViews();
			LineGraph line = new LineGraph();
	 	    Log.d(TAG, "data1.length="+p1Data.length);
	 	    Log.d(TAG, "data1.data="+p1Data.toString());
	 	    GraphicalView gView = line.getView(this, p1Data, p2Data, p3Data, p4Data);
	 	    
	 	    graphView.addView(gView);
	 	    return true;
		}
		else{
			bottomText.setText("Not enough data to build a graph");
			
		}
		return false;
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
