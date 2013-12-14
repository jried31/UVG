package edu.dartmouth.cs.myruns5;

<<<<<<< HEAD
import com.parse.Parse;

=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;

public class MainActivity extends Activity {
	public static final String KEY_TAB_INDEX = "tab index";
	public static final String ACTIVITY_TYPE = "activity type";
	public static final String INPUT_TYPE = "input type";
	public static final String TASK_TYPE = "task type";
	private Context mContext;
	private SharedPreferences sp;
	CurrentUVIFragment uviFragment;
	ChartFragment chartFragment;
	RecommendFragment recommendFragment;
	DateFragment dateFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

<<<<<<< HEAD
		// Create SharedPreference to save program settings
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		sp = this.getPreferences(MODE_PRIVATE);
		saveSettings();
		mContext = this;

<<<<<<< HEAD
		// Start UltravioletIndexService
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		final Intent currentUVIIntent = new Intent(this,
				UltravioletIndexService.class);
		currentUVIIntent.setAction(UltravioletIndexService.CURRENT_UV_INDEX);
		startService(currentUVIIntent);

		// set up action bar
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// create fragments
		Fragment startFragment = new StartFragment();
		Fragment historyFragment = new HistoryFragment();
		Fragment settingsFragment = new SettingsFragment();
<<<<<<< HEAD
		FriendsFragment friendsFragment = new FriendsFragment();
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		uviFragment = new CurrentUVIFragment();
		chartFragment = new ChartFragment();
		recommendFragment = new RecommendFragment();
		// dateFragment = new DateFragment();

<<<<<<< HEAD
		//initialize parse
        Parse.initialize(this, "2zU6YnzC8DLSMJFuAOiLNr3MD6X0ryG52mZsxoo0", "m4rlzlSWyUvgcEkNULlVqRBlsX2iGRilskltCqYG"); 
		
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		// create tabs
		ActionBar.Tab startTab = bar.newTab().setText(
				getString(R.string.startTab_title));
		startTab.setTabListener(new MyTabListener(startFragment, mContext));

		ActionBar.Tab historyTab = bar.newTab().setText(
				getString(R.string.historyTab_title));
		historyTab.setTabListener(new MyTabListener(historyFragment, mContext));

		ActionBar.Tab settingsTab = bar.newTab().setText(
				getString(R.string.settingsTab_title));
		settingsTab
				.setTabListener(new MyTabListener(settingsFragment, mContext));

		ActionBar.Tab uviTab = bar.newTab().setText(
				getString(R.string.uviTab_title));
		uviTab.setTabListener(new MyTabListener(uviFragment, mContext));
<<<<<<< HEAD
		
		ActionBar.Tab graphTab = bar.newTab().setText(getString(R.string.graphTab_title));
        graphTab.setTabListener(new MyTabListener(friendsFragment, getApplicationContext()));
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e

		// add tabs
		bar.addTab(startTab);
		bar.addTab(historyTab);
		bar.addTab(settingsTab);
		bar.addTab(uviTab);
<<<<<<< HEAD
		bar.addTab(graphTab);
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e

		// resume state if applicable
		if (savedInstanceState != null) {
			bar.setSelectedNavigationItem(savedInstanceState
					.getInt(KEY_TAB_INDEX));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (uviFragment.getReceiver() != null)
			this.unregisterReceiver(uviFragment.getReceiver());
	}

	@Override
	public void onStop() {
		super.onStop();
		if (uviFragment.getReceiver() != null)
			this.unregisterReceiver(uviFragment.getReceiver());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_TAB_INDEX, getActionBar()
				.getSelectedNavigationIndex());
	}

	
<<<<<<< HEAD
	// Get show hint status (Show swipe hint)
=======
	
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
	public boolean getShowHintStatus() {
		return sp.getBoolean("ShowHintStatus", true);
	}

<<<<<<< HEAD
	// Save show hint setting
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
	public void setShowHintStatus(boolean hint) {
		SharedPreferences.Editor editor = sp.edit();
		editor.remove("ShowHintStatus");
		editor.putBoolean("ShowHintStatus", hint);
		editor.commit();
	}

<<<<<<< HEAD
	// Save setting
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
	private void saveSettings() {
		if (sp.contains("ShowHintStatus")) {
			return;
		} else {
			SharedPreferences.Editor editor = sp.edit();
			editor.putBoolean("ShowHintStatus", true);
			editor.commit();
		}
	}

	public CurrentUVIFragment getUVIFragment() {
		return uviFragment;
	}

	public ChartFragment getChartFragment() {
		return chartFragment;
	}

	public RecommendFragment getRecommendFragment() {
		return recommendFragment;
	}

	public void onStartClicked(View view) {
		// check input type
		Spinner spinner = (Spinner) findViewById(R.id.Spinner_InputType);
		int pos = spinner.getSelectedItemPosition();
		Intent intent;
		switch (pos) {
		case 0:
			intent = new Intent(this, ManualInputActivity.class);
			break;
		case 1:
		case 2:
			intent = new Intent(this, MapDisplayActivity.class);
			break;
		default:
			intent = new Intent(this, MapDisplayActivity.class);
		}

		// put extra
		intent.putExtra(INPUT_TYPE, pos);

		spinner = (Spinner) findViewById(R.id.Spinner_ActivityType);
		pos = spinner.getSelectedItemPosition();
		intent.putExtra(ACTIVITY_TYPE, pos);
		intent.putExtra(TASK_TYPE, Globals.TASK_TYPE_NEW);

		// fire intent
		startActivity(intent);

	}
}



class MyTabListener implements ActionBar.TabListener {
	private Fragment mFragment;
	private Context mContext;
	private boolean hint;
	private CheckBox doNotShowHint;

	public MyTabListener(Fragment frgmt, Context cntxt) {
		mFragment = frgmt;
		mContext = cntxt;
	}

	public void createDialog(boolean hint) {
		if (!hint)
			return;
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			LayoutInflater adbInflater = LayoutInflater.from(mContext);
			View hintLayout = adbInflater.inflate(R.layout.show_hint, null);
			doNotShowHint = (CheckBox) hintLayout.findViewById(R.id.skip);
			builder.setView(hintLayout);
			builder.setTitle("Swipe Hint");
			builder.setMessage("You can swipe left and right to see different contents of UVI!\n\n");
			builder.setIcon(R.drawable.hint1);

			// Setting OK Button
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							// closed
							if (doNotShowHint.isChecked()) {
								System.out.println("The checkbox is checked!");
								((MainActivity) mContext)
										.setShowHintStatus(false);
							}
						}
					});

			// Showing Alert Message
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.getText().equals("UVI")) {
			hint = ((MainActivity) mContext).getShowHintStatus();
			createDialog(hint);
		}
		ft.replace(R.id.fragment_container, mFragment);
		// Toast.makeText(mContext, "onTabSelected", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Toast.makeText(mContext, "onTabReSelected",
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(mFragment);
		// Toast.makeText(mContext, "onTabUnSelected",
		// Toast.LENGTH_SHORT).show();
	}
}