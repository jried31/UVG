package edu.dartmouth.cs.myruns5;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;


//This class is used to call the BarGraph class
public class ChartActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.fragment_graph);
		DisplayChart();
		
	}

	
	public void DisplayChart()
	{
		
		BarGraph bar = new BarGraph();
    	Intent lineIntent = bar.getIntent(this);
        startActivity(lineIntent);
	
	}
	
}
