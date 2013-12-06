package edu.dartmouth.cs.myruns5;

import java.io.Serializable;
import java.util.ArrayList;

import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;


//This class is used to call the BarGraph class
public class ChartActivity extends Activity{
	
	private ArrayList<Integer> selectedItems; // Used to figure which index of fbFriends to access
	private ArrayList<ParseUser> fbFriends; // Stores all Facebook Friends that user is Friends with
	private ParseUser user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.fragment_graph);
		System.out.println("ABOUT TO ADD SERIALIZABLE");
		selectedItems = (ArrayList<Integer>) getIntent().getSerializableExtra("selectedItems");
		fbFriends = (ArrayList<ParseUser>) getIntent().getSerializableExtra("fbFriends");
		System.out.println("ADDED SERIALIZABLE");
		DisplayChart();
	}

	
	public void DisplayChart()
	{
		BarGraph bar = new BarGraph(selectedItems, fbFriends);
    	Intent lineIntent = bar.getIntent(this);
        startActivity(lineIntent);
	}
	
}
