package edu.dartmouth.cs.myruns5;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class BarGraph{

	
	//there should be a color array here with 5 different colors
		// that specify 5 different people (user + 4 friends)
	
	public Intent getIntent(Context context) 
	{	
		
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
		
		
/*
		CategorySeries series1 = new CategorySeries("Demo Arun");
		series1.add("Bar " + 5, 25);
		
		CategorySeries series2 = new CategorySeries("Demo Nathan");
		series2.add("Bar " + 15, 20);
		
		CategorySeries series3 = new CategorySeries("Demo Julian");
		series3.add("Bar " + 25, 30);
		*/
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
	   
	    
	    
	    //Add each person selected
	    // **** notes ****
	    //  Colors should not be manually selected as so,
	    // but instead by using the array to be intitialized in 
	    // the class members
	    
	    
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


		Intent intent = ChartFactory.getBarChartIntent(context, dataset,mRenderer, Type.DEFAULT);
		return intent;
	}
	
	
	

}
