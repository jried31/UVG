package edu.dartmouth.cs.myruns5;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String KEY_TAB_INDEX = "tab index";
	public static final String ACTIVITY_TYPE = "activity type";
	public static final String INPUT_TYPE = "input type";
	public static final String TASK_TYPE = "task type";
	public static final long MIN_TIME_BW_UPDATES = 5000;
	public static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	private LocationManager myTracksLocationManager;
	private NotificationManager nm;
	private Location location = null;
	private Context mContext;
	CurrentUVIFragment uviFragment;
	ChartFragment chartFragment;
	RecommendFragment recommendFragment;
	DateFragment dateFragment;
	private String filename = "UVI_Data";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// set up action bar

		writeFile();
		myTracksLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mContext = this;

		location = getLocation(myTracksLocationManager);
		/*final Intent currentUVIIntent = new Intent(this,
				UltravioletIndexService.class);
		currentUVIIntent.setAction(UltravioletIndexService.CURRENT_UV_INDEX);
		currentUVIIntent.putExtra("Location", location);
		startService(currentUVIIntent);*/

		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// create fragments
		Fragment startFragment = new StartFragment();
		Fragment historyFragment = new HistoryFragment();
		Fragment settingsFragment = new SettingsFragment();
		uviFragment = new CurrentUVIFragment();
		chartFragment = new ChartFragment();
		recommendFragment = new RecommendFragment();
		//dateFragment = new DateFragment();

		// create tabs
		ActionBar.Tab startTab = bar.newTab().setText(
				getString(R.string.startTab_title));
		startTab.setTabListener(new MyTabListener(startFragment,
				getApplicationContext()));

		ActionBar.Tab historyTab = bar.newTab().setText(
				getString(R.string.historyTab_title));
		historyTab.setTabListener(new MyTabListener(historyFragment,
				getApplicationContext()));

		ActionBar.Tab settingsTab = bar.newTab().setText(
				getString(R.string.settingsTab_title));
		settingsTab.setTabListener(new MyTabListener(settingsFragment,
				getApplicationContext()));

		ActionBar.Tab uviTab = bar.newTab().setText(
				getString(R.string.uviTab_title));
		uviTab.setTabListener(new MyTabListener(uviFragment,
				getApplicationContext()));

		// add tabs
		bar.addTab(startTab);
		bar.addTab(historyTab);
		bar.addTab(settingsTab);
		bar.addTab(uviTab);

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

	public String getFileName() {
		return filename;
	}

	private void writeFile() {
		File file = new File(this.getFilesDir(), filename);
		FileOutputStream outputStream;
		String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
		System.out.println(date);
		String info = date
				+ "\t6:3 7:4 8:4 9:5 10:6 11:8 12:9 13:10 14:11 15:9 16:8 17:5 18:4";
		try {
			outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(info.getBytes());
			outputStream.write("\r\n".getBytes());
			info = "11-10-2013\t6:1 7:2 8:3 9:4 10:5 11:6 12:7 13:8 14:9 15:8 16:7 17:5 18:4";
			outputStream.write(info.getBytes());
			outputStream.write("\r\n".getBytes());
			info = "11-09-2013\t6:2 7:3 8:4 9:5 10:5 11:5 12:7 13:9 14:11 15:10 16:7 17:5 18:4";
			outputStream.write(info.getBytes());
			outputStream.write("\r\n".getBytes());
			info = "11-08-2013\t6:1 7:3 8:5 9:6 10:5 11:7 12:8 13:9 14:11 15:9 16:7 17:3 18:2";
			outputStream.write(info.getBytes());
			outputStream.write("\r\n".getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
	};

	public Location getLocation(LocationManager locationManager) {
		try {
			// getting GPS status
			boolean isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			if (!isGPSEnabled) {
				// no network provider is enabled
				NotificationCompat.Builder n = new NotificationCompat.Builder(
						this)
						.setSmallIcon(R.drawable.runner)
						.setContentTitle("UV notification")
						.setContentText(
								"GPS locating service is off, please turn it on!");
				n.setAutoCancel(true);
				nm.notify(0, n.build());
				Handler handler = new Handler(Looper.getMainLooper());
				handler.post(new Runnable() {
					public void run() {
						Toast.makeText(
								mContext,
								"GPS function is off. Please enable GPS locating service!",
								Toast.LENGTH_LONG).show();
					}
				});
				System.out.println("No provider enabled!");
				return null;
			} else {
				// if GPS Enabled get lat/long using GPS Services
				System.out.println("GPS provider enabled!");
				if (location == null) {
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
					location = locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (location != null)
			System.out.println("Longitude: " + location.getLongitude()
					+ ", Latitude: " + location.getLatitude());
		return location;
	}

	public Location retrieveLoc() {
		return location;
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
	public Fragment mFragment;
	public Context mContext;

	public MyTabListener(Fragment frgmt, Context cntxt) {
		mFragment = frgmt;
		mContext = cntxt;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
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