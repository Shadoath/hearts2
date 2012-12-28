package game.shad.tempus.hearts;

import org.achartengine.ChartFactory;
import org.achartengine.chart.LineChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class LineGraph {

	XYSeries p1Series = new XYSeries("P1");
    XYSeries p2Series = new XYSeries("P2");
    XYSeries p3Series = new XYSeries("P3");
    XYSeries p4Series = new XYSeries("P4");
    public LineChart pointsPerRound;
    public String winnerString ="";
    public int[] data1 = {0, 1, 1, 3, 3, 5, 6, 12, 14, 17, 20, 25};
    public int[] data2 = {0, 0, 0, 6, 8, 9, 23, 24, 25, 25, 27, 22};
    public int[] data3 = {0, 0, 0, 0, 7, 7, 12, 14, 14, 17, 17, 17};
    public int[] data4 = {0, 3, 3, 3, 16, 17, 23, 24, 14, 14, 14, 22};
    public XYMultipleSeriesDataset dataset;
    public XYMultipleSeriesRenderer mRenderer;
    
	public Intent getInent(Context context){
		
//		graphData(data1, data2, data3, data4);
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Points per Player per Round");
		
		
		return intent;

	}
	
	public void graphData(int[] data1, int[] data2, int[] data3, int[] data4){
    	int count=0;
    	for (int i: data1){
      	   	 p1Series.add(count, i);
      	   	 count++;
      	}
    	count=0;
    	for (int i: data2){
      	   	 p2Series.add(count, i);
      	   	 count++;
      	}
    	count=0;
    	for (int i: data3){
      	   	 p3Series.add(count, i);
      	   	 count++;
      	}
    	count=0;
    	for (int i: data4){
      	   	 p4Series.add(count, i);
      	   	 count++;
      	}
    	dataset = new XYMultipleSeriesDataset();
    	dataset.addSeries(p1Series);
    	dataset.addSeries(p2Series);
    	dataset.addSeries(p3Series);
    	dataset.addSeries(p4Series);
    	
    	mRenderer = new XYMultipleSeriesRenderer();
    	
    	XYSeriesRenderer renderer1 = new XYSeriesRenderer();
    	renderer1.setColor(Color.BLUE);
    	mRenderer.addSeriesRenderer(renderer1);
    	
    	XYSeriesRenderer renderer2 = new XYSeriesRenderer();
    	renderer2.setColor(Color.RED);
    	mRenderer.addSeriesRenderer(renderer2);
    	
    	XYSeriesRenderer renderer3 = new XYSeriesRenderer();
    	renderer3.setColor(Color.YELLOW);
    	mRenderer.addSeriesRenderer(renderer3);
    	
    	XYSeriesRenderer renderer4 = new XYSeriesRenderer();
    	renderer4.setColor(Color.GREEN);
    	mRenderer.addSeriesRenderer(renderer4);
    	
    	mRenderer.setXTitle("Round");
    	mRenderer.setYTitle("Points");
    	mRenderer.setXAxisMax(50);
//    	pointsPerRound = new LineChart(dataset, mRenderer);
    	
    }
}
