package edu.dartmouth.cs.myruns5;

import java.util.ArrayList;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class FriendsFragment extends ListFragment {

	Toast m_currentToast;
	private ArrayList<Integer> selectedItems; // Used to figure which index of fbFriends to access
	private ArrayList<ParseUser> fbFriends; // Stores all Facebook Friends that user is Friends with

	public ArrayList<Integer> getSelectedItems(){
		return selectedItems;
	}
	
	public ArrayList<ParseUser> getFBFriends(){
		return fbFriends;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LinearLayout friendsMain = (LinearLayout)inflater.inflate(R.layout.fragment_friends, container, false);
		
		//Test Parse initialization for grabbing Facebook friends
		Parse.initialize(getActivity(), "2zU6YnzC8DLSMJFuAOiLNr3MD6X0ryG52mZsxoo0", "m4rlzlSWyUvgcEkNULlVqRBlsX2iGRilskltCqYG");
	//ParseFacebookUtils.initialize("613060905424062");
		ParseFacebookUtils.initialize("1429856927242803");
		selectedItems = new ArrayList<Integer>();
		fbFriends = new ArrayList<ParseUser>();
		
		return friendsMain;
	}
	
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
        
        // Puts the position of the item that was selected into selectedItem
        // Toggles whether or not a friend was added or not
    	@Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        	if(selectedItems.contains((Integer)position)){
        		selectedItems.remove((Integer)position);
        		showToast("Friend Removed!");
        	}
        	else{
        		selectedItems.add(position);
        		showToast("Friend added to chart!");
        	}
            return true;
        }
    });
    
    ParseUser currentUser = ParseUser.getCurrentUser();
    ArrayList<String> friends = new ArrayList<String>();

    if(currentUser!=null) {
    	fbFriends = (ArrayList<ParseUser>)currentUser.get("fb_friends");
    	
    	// Adds all the names to populate the Friends List
    	for(ParseUser user : fbFriends) {
    		friends.add((String)user.get("name"));
    	}
    }
    else {
    	//not logged in, so do nothing
    	friends.add("NO FRIENDS :[");
    }
    
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
			android.R.layout.simple_list_item_1, friends);
	setListAdapter(adapter);
    
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    // do nothing (do not add friend) on single click 
  }
  
  private void showToast(String text) {
	  //get rid of current toast before showing the next message
      if(m_currentToast != null)
          m_currentToast.cancel();
      m_currentToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
      m_currentToast.show();
  }
} 