package edu.dartmouth.cs.myruns5;


import java.io.Serializable;
import java.util.ArrayList;

import com.parse.ParseUser;

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



public class GraphFragment extends Fragment {
	private FriendsFragment friendsFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		friendsFragment = new FriendsFragment();
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

	}
	
	// Switches Intent to Chart Activity
	// Passes along the selected array along with the all the user's Facebook friends
	private void showGraphActivity() {
		Intent intent = new Intent(getActivity(),ChartActivity.class);
		intent.putExtra("selectedItems", friendsFragment.getSelectedItems());
		intent.putExtra("fbFriends", friendsFragment.getFBFriends());
		startActivity(intent);
	}
	
	public void showFriendsList() {
		Context context = getActivity();
		CharSequence text = "Opening friends list!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		friendsFragment = new FriendsFragment();
		
		FragmentTransaction ft  = getFragmentManager().beginTransaction();
		ft.replace(((ViewGroup)getView().getParent()).getId(), friendsFragment);
		ft.addToBackStack(null);
		ft.commit();
		
	}
}