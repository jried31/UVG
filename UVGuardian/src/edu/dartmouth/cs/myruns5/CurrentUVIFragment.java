package edu.dartmouth.cs.myruns5;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

<<<<<<< HEAD
import android.app.AlertDialog;
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
<<<<<<< HEAD
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
=======
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
import android.os.Bundle;
import android.os.Messenger;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
<<<<<<< HEAD
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

// Show current UVI level UI
=======
import android.view.ViewGroup;
import android.widget.TextView;

>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
public class CurrentUVIFragment extends Fragment {
	Messenger myService = null;
	boolean isBound;
	private Timer timer;
	private UVIBroadcastReciever reciever;
	private IntentFilter filter;
	private View v;
<<<<<<< HEAD
	private int currentUVI = 0;
=======
	private float currentUVI = 0;
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
	private ExecutorService executorService;
	private float[] data;
	private Context context;
	private NotificationManager nm;
	private static final int ALERT_NOTIFICATION = 9;
<<<<<<< HEAD
	private long[] vibrate = { 400, 1000, 400, 1000, 400, 1000 };
=======
	private long[] vibrate = {400, 1000, 400, 1000, 400, 1000};
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = this.getActivity();
<<<<<<< HEAD
		nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
=======
		nm = ( NotificationManager ) context.getSystemService(Context.NOTIFICATION_SERVICE);
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		executorService = Executors.newSingleThreadExecutor();
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				updateUVIWidget();
			}
		});
<<<<<<< HEAD
		
		// Get current UI data
		currentUVI = ((MainActivity) this.getActivity()).getRecommendFragment().getUVI();
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e

		v = inflater.inflate(R.layout.uvg_fragment_current_uvi, container,
				false);
		if (v == null)
			System.out.println("View is null!");
		else
			v.setOnTouchListener(new SwipeListener(context, this
					.getFragmentManager(), this, ((MainActivity) context)
					.getChartFragment(), ((MainActivity) context)
					.getRecommendFragment(), 1));

<<<<<<< HEAD
		// Set show UVI scale chart help
		ImageView scale = (ImageView) v.findViewById(R.id.uvi_scale);
		scale.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showHelp();
			}
		});
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		updateDisplay(v);
		return v;
	}

<<<<<<< HEAD
	// Show UVI scale chart help
	private void showHelp() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		LayoutInflater adbInflater = LayoutInflater.from(this.getActivity());
		View helpLayout = adbInflater.inflate(R.layout.show_scale_help, null);
		builder.setView(helpLayout);
		builder.setTitle("UVI Scale Help");
		builder.setIcon(R.drawable.hint1);
		// Setting OK Button
		builder.setPositiveButton("BACK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
			}
		});

		// Showing Alert Message
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	// Set timer, not used here
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
	private TimerTask updateUVITask = new TimerTask() {
		@Override
		public void run() {
			// Call the UVI Service to grab the current UVI
			updateUVIWidget();
		}
	};
	final int updateInterval = 15;// minutes

	public UVIBroadcastReciever getReceiver() {
		return reciever;
	}

<<<<<<< HEAD
	// Start UltravioletIndexService
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
	private void updateUVIWidget() {
		final Intent currentUVIIntent = new Intent(getActivity(),
				UltravioletIndexService.class);
		currentUVIIntent.setAction(UltravioletIndexService.CURRENT_UV_INDEX);
		getActivity().startService(currentUVIIntent);
	}

<<<<<<< HEAD
	// Update display upon receiving broadcast info
	private void updateDisplay(View v) {
		// currentUVI = 11;
		TextView currentUVIView = (TextView) v.findViewById(R.id.current_uvi);
		currentUVIView.setText(Integer.toString(currentUVI));
=======
	private void updateDisplay(View v) {
		//currentUVI = 11;
		TextView currentUVIView = (TextView) v.findViewById(R.id.current_uvi);
		currentUVIView.setText(Float.toString(currentUVI));
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		switch ((int) currentUVI) {
		case 0:
		case 1:
		case 2:
			currentUVIView.setBackgroundResource(R.drawable.circle_green);
			break;
		case 3:
		case 4:
		case 5:
			currentUVIView.setBackgroundResource(R.drawable.circle_yellow);
			break;
		case 6:
		case 7:
			currentUVIView.setBackgroundResource(R.drawable.circle_orange);
			break;
		case 8:
		case 9:
		case 10:
			currentUVIView.setBackgroundResource(R.drawable.circle_red);
			break;
		case 11:
			currentUVIView.setBackgroundResource(R.drawable.circle_purple);
			break;
		default:
			currentUVIView.setBackgroundResource(R.drawable.circle_purple);
			break;
		}
<<<<<<< HEAD

		// If current UVI value larger than 8, make alert and vibration.
		if (currentUVI >= 8) {
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					context)
					.setSmallIcon(R.drawable.alert)
					.setAutoCancel(true)
					.setContentTitle("UVI Alert")
					.setContentText(
							"Your local UVI level is very high, please take care!")
					.setLights(0xFF00FF00, 400, 400).setVibrate(vibrate);
=======
		
		if(currentUVI >= 8){
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(context)
			        .setSmallIcon(R.drawable.alert)
			        .setAutoCancel(true)
			        .setContentTitle("UVI Alert")
			        .setContentText("Your local UVI level is very high, please take care!")
			        .setLights(0xFF00FF00, 400, 400)
			        .setVibrate(vibrate);
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
			Notification notif = mBuilder.build();
			nm.notify(ALERT_NOTIFICATION, notif);
		}
	}

	public int getUVI() {
		return (int) currentUVI;
	}

	public float[] getWebUVI() {
<<<<<<< HEAD
		if (data == null)
=======
		if(data == null)
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
			return new float[13];
		return data;
	}

	// Recieves the current UVI broadcast updates from the Service
	class UVIBroadcastReciever extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
<<<<<<< HEAD
			currentUVI = (int)arg1.getExtras().getFloat(
=======
			currentUVI = arg1.getExtras().getFloat(
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
					UltravioletIndexService.CURRENT_UV_INDEX);
			data = arg1.getExtras().getFloatArray(
					UltravioletIndexService.WEB_UVI);
			updateDisplay(v);
		}
	}

	private void registerReciever() {
		filter = new IntentFilter(UltravioletIndexService.CURRENT_UV_INDEX);
		reciever = new UVIBroadcastReciever();
		getActivity().registerReceiver(reciever, filter);
	}

	@Override
	public void onResume() {
		super.onResume();
		registerReciever();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (reciever != null) {
			getActivity().unregisterReceiver(reciever);
			reciever = null;
			filter = null;
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (reciever != null) {
			getActivity().unregisterReceiver(reciever);
			reciever = null;
			filter = null;
		}
	}

}
