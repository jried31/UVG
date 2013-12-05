package edu.dartmouth.cs.myruns5;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.parse.ParseUser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class BarGraph{

	private ArrayList<Integer> selectedItems; // Used to figure which index of fbFriends to access
	private ArrayList<ParseUser> fbFriends; // Stores all Facebook Friends that user is Friends with
	
	//there should be a color array here with 5 different colors
		// that specify 5 different people (user + 4 friends)
	
	public BarGraph(ArrayList<Integer> selectedItems, ArrayList<ParseUser> fbFriends){
		this.selectedItems = selectedItems;
	 this.fbFriends = fbFriends;
	}
	
	public Intent getIntent(Context context) 
	{	
/*
		// Bar 1
		int[] y = { 124, 135, 443, 456, 234, 123, 342, 134, 123, 643, 234, 274 };
		CategorySeries series = new CategorySeries("Demo Bar Graph 1");
		for (int i = 0; i < y.length; i++) {
			series.add("Bar " + (i+1), y[i]);
		}
		
		// Bar 2
		int[] y2 = { 224, 235, 243, 256, 234, 223, 242, 234, 223, 243, 234, 274 };
		CategorySeries series2 = new CategorySeries("Demo Bar Graph 2");
		for (int i = 0; i < y.length; i++) {
			series2.add("Bar " + (i+1), y2[i]);
		}
		
		*/
/*
		CategorySeries series1 = new CategorySeries("Demo Arun");
		series1.add("Bar " + 5, 25);
		
		CategorySeries series2 = new CategorySeries("Demo Nathan");
		series2.add("Bar " + 15, 20);
		
		CategorySeries series3 = new CategorySeries("Demo Julian");
		series3.add("Bar " + 25, 30);
		*/
		/*
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());
		dataset.addSeries(series2.toXYSeries());
	//	dataset.addSeries(series3.toXYSeries());
		// This is how the "Graph" itself will look like
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("Relative UV Exposure");
		//mRenderer.setXTitle("X VALUES");
		mRenderer.setXTitle("Facebook Friends");
		//mRenderer.setYTitle("Y VALUES");
		mRenderer.setYTitle("UV exposure");
		mRenderer.setAxesColor(Color.GREEN);
	    mRenderer.setLabelsColor(Color.RED);
	   
	    */
	    
	    //Add each person selected
	    // **** notes ****
	    //  Colors should not be manually selected as so,
	    // but instead by using the array to be intitialized in 
	    // the class members
	    
	    /*
	    // Customize bar 1
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		// renderer.setColor(Color.YELLOW);
		renderer.setDisplayChartValues(true);
	    renderer.setChartValuesSpacing((float) 0.5);
	    mRenderer.addSeriesRenderer(renderer);
	  
	    
	    // Customize bar 2
	    XYSeriesRenderer renderer2 = new XYSeriesRenderer();
	    renderer.setColor(Color.CYAN);
	    renderer.setDisplayChartValues(true);
	    renderer.setChartValuesSpacing((float) 0.5);
	    mRenderer.addSeriesRenderer(renderer2);
*/

		int[] y1 = { 0,111 };
		CategorySeries series1 = new CategorySeries("Demo Arun");
		for (int i = 0; i < y1.length; i++) {
			series1.add("Bar " + (i+1), y1[i]);
		}
			
		int[] y2 = { 0,222 };
		CategorySeries series2 = new CategorySeries("Demo Nathan");
		for (int j = 0; j < y2.length; j++) {
			series2.add("Bar " + (j+1), y2[j]);
		}
			
		int[] y3 = { 0,50 };
		CategorySeries series3 = new CategorySeries("Demo Julian");
		for (int k = 0; k < y3.length; k++) {
			series3.add("Bar " + (k+1), y3[k]);
		}
		
		int[] y4 = { 0,150 };
		CategorySeries series4 = new CategorySeries("Demo Matt");
		for (int m = 0; m < y4.length; m++) {
			series4.add("Bar " + (m+1), y4[m]);
		}
		
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series1.toXYSeries());
		dataset.addSeries(series2.toXYSeries());
		dataset.addSeries(series3.toXYSeries());
		dataset.addSeries(series4.toXYSeries());
		// This is how the "Graph" itself will look like
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setChartTitle("Relative UV Exposure");
		//mRenderer.setXTitle("X VALUES");
		mRenderer.setXTitle("Facebook Friends");
		//mRenderer.setYTitle("Y VALUES");
		mRenderer.setYTitle("UV exposure");
		mRenderer.setAxesColor(Color.GREEN);
	    mRenderer.setLabelsColor(Color.RED);
	   
	    
	    
	    //Add each person selected
	    // **** notes ****
	    //  Colors should not be manually selected as so,
	    // but instead by using the array to be intitialized in 
	    // the class members
	    
	    
	    // Customize bar 1
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.YELLOW);
		renderer.setDisplayChartValues(true);
	    renderer.setChartValuesSpacing((float) 0.25);
	    mRenderer.addSeriesRenderer(renderer);
	  
	    
	    // Customize bar 2
	    XYSeriesRenderer renderer2 = new XYSeriesRenderer();
	    renderer2.setColor(Color.CYAN);
	    renderer2.setDisplayChartValues(true);
	    renderer2.setChartValuesSpacing((float) 0.25);
	    mRenderer.addSeriesRenderer(renderer2);

	 // Customize bar 3
	    XYSeriesRenderer renderer3 = new XYSeriesRenderer();
	    renderer3.setColor(Color.RED);
	    renderer3.setDisplayChartValues(true);
	    renderer3.setChartValuesSpacing((float) 0.25);
	    mRenderer.addSeriesRenderer(renderer3);

	 // Customize bar 4
	    XYSeriesRenderer renderer4 = new XYSeriesRenderer();
	    renderer4.setColor(Color.GREEN);
	    renderer4.setDisplayChartValues(true);
	    renderer4.setChartValuesSpacing((float) 0.25);
	    mRenderer.addSeriesRenderer(renderer4);
	    
		Intent intent = ChartFactory.getBarChartIntent(context, dataset,mRenderer, Type.DEFAULT);
		return intent;
	}
}
