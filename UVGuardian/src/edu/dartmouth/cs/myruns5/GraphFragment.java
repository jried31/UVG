package edu.dartmouth.cs.myruns5;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


//at the moment it is a PreferenceFragment 
public class GraphFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//return inflater.inflate(R.layout.fragment_graph, container, false);
		LinearLayout graphMain;
		
		graphMain= (LinearLayout)inflater.inflate(R.layout.fragment_graph_main, container, false);
	
		
		
		   Button showGraph = (Button) graphMain.findViewById(R.id.button_show_graph);
	         showGraph.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 showGraphActivity();
	             }
	         });
	         
	         Button addFriend = (Button) graphMain.findViewById(R.id.button_add_friend);
	         addFriend.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	                 // Perform action on click
	            	 showFriendsList();
	             }
	         });

	
	return graphMain;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*
		// Find the chart view
				ChartView chartView = (ChartView) getView().findViewById(R.id.chart_view);

				// Create the data points
				LinearSeries series = new LinearSeries();
				series.setLineColor(0xFF0099CC);
				series.setLineWidth(2);

				for (double i = 0d; i <= (2d * Math.PI); i += 0.1d) {
					series.addPoint(new LinearPoint(i, Math.sin(i)));
				}

				// Add chart view data
				chartView.addSeries(series);
				chartView.setLeftLabelAdapter(new ValueLabelAdapter(getActivity(), LabelOrientation.VERTICAL));
				chartView.setBottomLabelAdapter(new ValueLabelAdapter(getActivity(), LabelOrientation.HORIZONTAL));
		*/
//showGraphActivity();
	}
	
	private void showGraphActivity() {
//	Intent intent = new Intent(getActivity(),GraphActivity.class);
	
		
		Intent intent = new Intent(getActivity(),ChartActivity.class);
		startActivity(intent);
	}
	
	public void showFriendsList() {
		
		//Context context = getApplicationContext();
		Context context = getActivity();
		CharSequence text = "Opening friends list!";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		FriendsFragment friendsFragment = new FriendsFragment();
		
		FragmentTransaction ft  = getFragmentManager().beginTransaction();
		ft.replace(((ViewGroup)getView().getParent()).getId(), friendsFragment);
		ft.addToBackStack(null);
		ft.commit();
		
	}
}