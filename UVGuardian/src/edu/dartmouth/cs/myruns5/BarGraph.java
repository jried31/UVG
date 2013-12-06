package edu.dartmouth.cs.myruns5;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

import com.parse.ParseUser;

public class BarGraph{

	private ArrayList<Integer> selectedItems; // Used to figure which index of fbFriends to access
	private ArrayList<ParseUser> fbFriends; // Stores all Facebook Friends that user is Friends with
	
	//there should be a color array here with 5 different colors
	// that specify 5 different people (user + 4 friends)
	
	public BarGraph(ArrayList<Integer> selectedItemsIn, ArrayList<ParseUser> fbFriendsIn) {
		selectedItems = selectedItemsIn;
		fbFriends = fbFriendsIn;
		//selectedItems and fbFriends might have NullPointerException here
		//System.out.println(selectedItems.size());
		//System.out.println(fbFriends.size());
		
	}
	
	public Intent getIntent(Context context) {	
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		
		//Initialize different series for user/friends
		XYSeries you = new XYSeries("You");
        XYSeries friend2 = new XYSeries("Friend2");
        XYSeries friend3 = new XYSeries("Friend3");
        XYSeries friend4 = new XYSeries("Friend4");
        XYSeries friend5 = new XYSeries("Friend5");
        
        //Set the UV data points
        you.add(1,10);
        friend2.add(2,35);
        friend3.add(3,50);
        friend4.add(4,20);
        friend5.add(5,7);
        
        //Add series to dataset
        dataset.addSeries(you);
        dataset.addSeries(friend2);
        dataset.addSeries(friend3);
        dataset.addSeries(friend4);
        dataset.addSeries(friend5);
		
        //Set up the chart design
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.setChartTitle("Relative UV Exposure");
 		mRenderer.setXTitle("Facebook Friends");
 		mRenderer.setYTitle("UV Exposure");
 		mRenderer.setAxesColor(Color.BLACK);
 		mRenderer.setLabelsColor(Color.RED);
 	    mRenderer.setApplyBackgroundColor(true);
 	    mRenderer.setBackgroundColor(Color.WHITE);
 	    mRenderer.setMarginsColor(Color.WHITE);
 	    mRenderer.setBarSpacing(-0.5);
       	mRenderer.setMargins(new int[] {20, 30, 15, 0});
       	mRenderer.setShowLegend(false);
       	mRenderer.setPanEnabled(false);
       	mRenderer.setZoomEnabled(false, false);
       	mRenderer.setAxisTitleTextSize(24);
       	mRenderer.setChartTitleTextSize(32);
       	mRenderer.setLabelsTextSize(20);
       	mRenderer.setLegendTextSize(16);
       	mRenderer.setBarWidth(50);
       	mRenderer.setXAxisMin(-1.5);
       	mRenderer.setXAxisMax(7.5);
       	mRenderer.setYAxisMin(0);
       	mRenderer.setYAxisMax(200);
       	mRenderer.setYLabelsAlign(Align.RIGHT);
       	mRenderer.setXLabelsColor(Color.BLACK);
       	mRenderer.setYLabelsColor(0, Color.BLACK);
       	mRenderer.setXLabels(0);
       	mRenderer.addXTextLabel(-0.5, "You");
       	mRenderer.addXTextLabel(1.25, "Demo2");
       	mRenderer.addXTextLabel(3, "Demo3");
       	mRenderer.addXTextLabel(4.75, "Demo4");
       	mRenderer.addXTextLabel(6.5, "Demo5");
	    
	    //Customize bar 1
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.parseColor("#911000")); //deep red
		renderer.setDisplayChartValues(true);
	    renderer.setChartValuesTextSize(24);
	    mRenderer.addSeriesRenderer(renderer);
	  
	    //Customize bar 2
	    XYSeriesRenderer renderer2 = new XYSeriesRenderer();
	    renderer2.setColor(Color.parseColor("#003399")); //dark blue
	    renderer2.setDisplayChartValues(true);
	    renderer2.setChartValuesTextSize(24);
	    mRenderer.addSeriesRenderer(renderer2);

	    //Customize bar 3
	    XYSeriesRenderer renderer3 = new XYSeriesRenderer();
	    renderer3.setColor(Color.parseColor("#006600")); //dark green
	    renderer3.setDisplayChartValues(true);
	    renderer3.setChartValuesTextSize(24);
	    mRenderer.addSeriesRenderer(renderer3);

	    //Customize bar 4
	    XYSeriesRenderer renderer4 = new XYSeriesRenderer();
	    renderer4.setColor(Color.parseColor("#CC00CC")); //magenta
	    renderer4.setDisplayChartValues(true);
	    renderer4.setChartValuesTextSize(24);
	    mRenderer.addSeriesRenderer(renderer4);

		//Customize bar 5
	    XYSeriesRenderer renderer5 = new XYSeriesRenderer();
	    renderer5.setColor(Color.parseColor("#663300")); //brown
	    renderer5.setDisplayChartValues(true);
	    renderer5.setChartValuesTextSize(24);
	    mRenderer.addSeriesRenderer(renderer5);
	    
		Intent intent = ChartFactory.getBarChartIntent(context, dataset,mRenderer, Type.DEFAULT);
		return intent;
	}
}
