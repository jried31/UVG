package edu.dartmouth.cs.myruns5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

import weka.core.Attribute;
import weka.core.Instance;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import edu.dartmouth.cs.myruns5.util.LocationUtils;

public class TrackingService extends Service
	implements LocationListener, SensorEventListener
	{
	private File mWekaClassificationFile;
	private Attribute mClassNameForData,meanAttribute,stdAttribute,maxAttribute,minAttribute,meanAbsDeviationAttribute;
	
	public ArrayList<Location> mLocationList;
	
	private int[] mInferenceCount = {0, 0, 0};

	// Location manager
	private LocationManager mLocationManager;
	
	// Intents for broadcasting location/motion updates
	private Intent mLocationUpdateBroadcast;
	private Intent mMotionUpdateBroadcast;
	
	private int mInputType;
	public int mInferredActivityType;
	
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Sensor mLightSensor;
	
	private float[] mGeomagnetic;
	private static ArrayBlockingQueue<Double> mAccBuffer;
	private static ArrayBlockingQueue<LumenDataPoint> mLightIntensityReadingBuffer;
	private AccelerometerActivityClassificationTask mAccelerometerActivityClassificationTask;
	private LightSensorActivityClassificationTask mLightSensorActivityClassificationTask;

	
	private final IBinder mBinder = new TrackingBinder();

	private float[] mGravity;

	private long pitchReading;

	
	public static final String LOCATION_UPDATE = "location update";
	public static final int NEW_LOCATION_AVAILABLE = 400;
	
	// broadcast 
	public static final String ACTION_MOTION_UPDATE = "motion update";
	public static final String CURRENT_MOTION_TYPE = "new motion type";
	public static final String VOTED_MOTION_TYPE = "voted motion type";
	public static final String ACTION_TRACKING = "tracking action";

	private static final String TAG = "TrackingService";
	
	private static Timer dataCollector;
  	private TimerTask dataCollectorTask = new TimerTask() {
  		@Override
  		public void run() 
  		{
  			dataCollectionEnabled=true;
  		}
  	};
	private static boolean dataCollectionEnabled = true;
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		mLocationList = new ArrayList<Location>();
		mLocationUpdateBroadcast = new Intent();
		mLocationUpdateBroadcast.setAction(ACTION_TRACKING);
		mMotionUpdateBroadcast = new Intent();
		mMotionUpdateBroadcast.setAction(ACTION_MOTION_UPDATE);
		mLightIntensityReadingBuffer = new ArrayBlockingQueue<LumenDataPoint>(Globals.LIGHT_BUFFER_CAPACITY);
		mAccBuffer = new ArrayBlockingQueue<Double>(Globals.ACCELEROMETER_BUFFER_CAPACITY);
		mAccelerometerActivityClassificationTask = new AccelerometerActivityClassificationTask();
		mLightSensorActivityClassificationTask = new LightSensorActivityClassificationTask();
		mInferredActivityType = Globals.ACTIVITY_TYPE_STANDING;
		
		//Start the timer for data collection
		dataCollector = new Timer();
		dataCollector.scheduleAtFixedRate(dataCollectorTask, Globals.DATA_COLLECTOR_START_DELAY, Globals.DATA_COLLECTOR_INTERVAL);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//Create Weka features.arff file reference
		mWekaClassificationFile = new File(getExternalFilesDir(null), Globals.FEATURE_LIGHT_FILE_NAME);
		Log.d(Globals.TAG, mWekaClassificationFile.getAbsolutePath());
		
		// Read inputType, can be GPS or Automatic.
		mInputType = intent.getIntExtra(MapDisplayActivity.INPUT_TYPE, -1);

		//JERRID I uncommented this
		Toast.makeText(getApplicationContext(), String.valueOf(mInputType), Toast.LENGTH_SHORT).show();
				
		// Get LocationManager and set related provider.
	    mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    boolean gpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    
	    if (gpsEnabled)
	    	mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Globals.RECORDING_GPS_INTERVAL_DEFAULT, Globals.RECORDING_GPS_DISTANCE_DEFAULT, this);
	    else
	    	mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Globals.RECORDING_NETWORK_PROVIDER_INTERVAL_DEFAULT,Globals.RECORDING_NETWORK_PROVIDER_DISTANCE_DEFAULT, this);
	 
//    	Toast.makeText(getApplicationContext(), "mInputType: "+String.valueOf(mInputType), Toast.LENGTH_SHORT).show();

	    if (mInputType == Globals.INPUT_TYPE_AUTOMATIC){
	    	// init sensor manager
	    	mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    	mAccelerometer = mSensorManager .getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		
	    	// register listener
	    	mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
	    	mAccelerometerActivityClassificationTask.execute();
	    	
	    	//JERRID: Register light Sensor
			mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
			mSensorManager.registerListener(this, mLightSensor,SensorManager.SENSOR_DELAY_FASTEST);
	    }
	    
		// Using pending intent to bring back the MapActivity from notification center.
		// Use NotificationManager to build notification(icon, content, title, flag and pIntent)
		String notificationTitle = "MyRuns";
		String notificationText = "Tracking Location";
		Intent myIntent = new Intent(this, MapDisplayActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		
		Notification notification = new Notification.Builder(this)
	        .setContentTitle(notificationTitle)
	        .setContentText(notificationText).setSmallIcon(R.drawable.greend)
	        .setContentIntent(pendingIntent).build();
		
		NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
		//notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
				
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
//    	Toast.makeText(getApplicationContext(), "service onDestroy", Toast.LENGTH_SHORT).show();

		// Unregistering listeners
		mLocationManager.removeUpdates(this);
		// Remove notification
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    notificationManager.cancelAll();
	    
//    	Toast.makeText(getApplicationContext(), "mInputType: "+String.valueOf(mInputType), Toast.LENGTH_SHORT).show();

	    // unregister listener
	    if (mInputType == Globals.INPUT_TYPE_AUTOMATIC){
//	    	Toast.makeText(getApplicationContext(), "unregister linstener", Toast.LENGTH_SHORT).show();
	    	mSensorManager.unregisterListener(this);
	    }
	    
	    // cancel task
	    mAccelerometerActivityClassificationTask.cancel(true);
	}
	
	public class TrackingBinder extends Binder{
		public TrackingService getService(){
			return TrackingService.this;
		}
	}

	
	/************ implement LocationLister interface ***********/
	public void onLocationChanged(Location location) {
//    	Toast.makeText(getApplication(), "OnLocationChanged", Toast.LENGTH_SHORT).show();

		//JERRID Adds--------------
		// Check whether location is valid, drop if invalid
	      if (!LocationUtils.isValidLocation(location)) {
	        Log.w(TAG, "Ignore onLocationChangedAsync. location is invalid.");
	        return;
	      }
		
	      //Check whether location reading is accurate
	      if (!location.hasAccuracy() || location.getAccuracy() >= Globals.RECORDING_GPS_ACCURACY_DEFAULT) {
	          Log.d(TAG, "Ignore onLocationChangedAsync. Poor accuracy.");
	          return;
	        }
	      
	      // Fix for phones that do not set the time field
	      if (location.getTime() == 0L) {
	        location.setTime(System.currentTimeMillis());
	      }
	      //------------------
	      
		// update location list
		mLocationList.add(location);

		// Send broadcast saying new location is updated
		mLocationUpdateBroadcast.putExtra(TrackingService.LOCATION_UPDATE, TrackingService.NEW_LOCATION_AVAILABLE);
		sendBroadcast(mLocationUpdateBroadcast);
	}
	
	public void onProviderDisabled(String provider) {}
	public void onProviderEnabled(String provider) {}
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	
	private void pauseDataCollection(){
		dataCollectionEnabled = false;
		mGravity = null;
		mGeomagnetic=null;
		//clear the buffer becasue we don't need it anymore
		mAccBuffer.clear();
		mLightIntensityReadingBuffer.clear();
	}
	
	/************ implement SensorEventLister interface ***********/
	public void onSensorChanged(SensorEvent event) {
//		Toast.makeText(getApplicationContext(), "onSensorChanged", Toast.LENGTH_SHORT).show();
		 // Many sensors return 3 values, one for each axis.
		
	      if (event.sensor.getType() == android.hardware.Sensor.TYPE_MAGNETIC_FIELD){
	           mGeomagnetic = event.values;
	      }else if(event.sensor.getType() == android.hardware.Sensor.TYPE_ACCELEROMETER){
	    	  
              mGravity = event.values;
              double x = mGravity[0];
              double y = mGravity[1];
              //Jerrid: Dont care about z component (for now)
              double z = mGravity[2];				
              double m = Math.sqrt(x*x + y*y + z*z);
	
              // Add m to the mAccBuffer one by one.
              try {
            	  mAccBuffer.add(m);
              } catch (IllegalStateException e) {
            	  ArrayBlockingQueue<Double> newBuf = new ArrayBlockingQueue<Double>(2*mAccBuffer.size());
            	  mAccBuffer.drainTo(newBuf);
            	  mAccBuffer = newBuf;
            	  mAccBuffer.add(m);				
              }
	      }else
	      if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
	    	  //JERRID: Light Sensor Reading
	          if (mGravity != null && mGeomagnetic != null)
	          {
	              float R[] = new float[9];
	              float I[] = new float[9];
	              boolean success = android.hardware.SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
	              if (success) 
	              {
	                  float orientation[] = new float[3];
	                  android.hardware.SensorManager.getOrientation(R, orientation);             
	                  pitchReading = Math.round(Math.abs((orientation[1]*180)/Math.PI));
	         
	                  float uvi = 0;
	                  LumenDataPoint intensityReading = new LumenDataPoint(event.timestamp, pitchReading, event.values[0], uvi);
	                    
	                  try {
	                      //JERRID: Add the magnitude reading to the buffer
	                      mLightIntensityReadingBuffer.add(intensityReading);
	                  } catch (IllegalStateException e) {
	                
	                      // Exception happens when reach the capacity.
	                      // Doubling the buffer. ListBlockingQueue has no such issue,
	                      // But generally has worse performance
	                      ArrayBlockingQueue<LumenDataPoint> newBuf = new ArrayBlockingQueue<LumenDataPoint>( mLightIntensityReadingBuffer.size() * 2);
	                      mLightIntensityReadingBuffer.drainTo(newBuf);
	                      mLightIntensityReadingBuffer = newBuf;
	                      mLightIntensityReadingBuffer.add(intensityReading);
	                  }
	              }
	          }
//			Toast.makeText(getApplicationContext(), String.valueOf(mAccBuffer.size()), Toast.LENGTH_SHORT).show();
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {}


	/************ AsyncTask **************/
	private class LightSensorActivityClassificationTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg0) {

	          /*
	           * The training phase stores the min, max, mean, std, and mean absolute deviation of an intensity window
	           * for each of the 16 light intensity readings (m0..m14), and the label the user provided to the 
	           *  collector (see collector UI later). Collectively, we call these features the 
	           *  feature vector comprises: magnitudes (f0..f14), MAX magnitude, label....
	           */          
			Instance featureInstance = new Instance(Globals.FEAT_NUMBER_FEATURES - 1);
			int blockSize = 0;
			LumenDataPoint[] lightIntensityDataBlock = new LumenDataPoint[Globals.LIGHT_BLOCK_CAPACITY];
			double maxLightMagnitude = Double.MIN_VALUE,
					minLightMagnitude = Double.MAX_VALUE,
					meanLightIntensity = 0,
					varianceIntensity = 0,
					stdLightMagnitude = 0,
					meanAbsoluteDeveationLightIntensity = 0;

	          while (true) 
	          {
	        	  try 
	        	  {
	                  // need to check if the AsyncTask is cancelled or not in the while loop
	                  if (isCancelled () == true)
	                  {
	                      return null;
	                  }

	                  // JERRID: Pops the "head" element from the Blocking Queue one at a time
	                  LumenDataPoint dataPoint = mLightIntensityReadingBuffer.take();
	                  double intensityReading = dataPoint.getIntensity();
	                  lightIntensityDataBlock[blockSize++] = dataPoint;
	                  
	                  //Calculate Mean Intensity Value
	                  if(blockSize <= 1)
	                      meanLightIntensity = intensityReading;
	                  else
	                      meanLightIntensity = (intensityReading + meanLightIntensity*(blockSize-1))/blockSize;
	              
	                  
	                  //JERRID: Once 16 readings are found, identify the MIN, MAX, magnitude
	                  if (blockSize == Globals.LIGHT_BLOCK_CAPACITY) 
	                  {
	                      //Compute the Mean Absolute Deviation since we have a full buffer=
	                      for (LumenDataPoint dp : lightIntensityDataBlock) {
	                          //find mean absolute deviation
	                          double val = dp.getIntensity();
	                          double diff = val - meanLightIntensity;
	                          varianceIntensity += diff * diff;
	                          meanAbsoluteDeveationLightIntensity += Math.abs(diff);
	                          
	                          //Calculate the MIN/MAX (seen so far)
	                          if (maxLightMagnitude < intensityReading) {
	                              maxLightMagnitude = intensityReading;
	                          }
	                          
	                          //find the min intensity
	                          if (minLightMagnitude > intensityReading) {
	                              minLightMagnitude = intensityReading;
	                          }
	                      }
	                      
	                      varianceIntensity = varianceIntensity/Globals.LIGHT_BLOCK_CAPACITY;
	                      stdLightMagnitude = Math.sqrt(varianceIntensity);
	                      meanAbsoluteDeveationLightIntensity = meanAbsoluteDeveationLightIntensity / Globals.LIGHT_BLOCK_CAPACITY;
	                  
	                      featureInstance.setValue(minAttribute,minLightMagnitude);
	                      featureInstance.setValue(maxAttribute,maxLightMagnitude);
	                      featureInstance.setValue(meanAttribute,meanLightIntensity);
	                      featureInstance.setValue(stdAttribute,stdLightMagnitude);
	                      featureInstance.setValue(meanAbsDeviationAttribute,meanAbsoluteDeveationLightIntensity);
	                      
	                      //Classifier
	                      /*WekaLightWrapper wrapper = new WekaLightWrapper();
	                      double prediction = wrapper.classifyInstance(featureInstance);
	                      String classification = featureInstance.classAttribute().value((int) prediction);
	                      
	                      File dir = new File (android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/accelerometer");
	                      dir.mkdirs();
	                      FileWriter sunClassificationFile=null;
	                  
	                      try {
	                          sunClassificationFile = new FileWriter(dir.getAbsolutePath()+"/"+Globals.LIGHT_INTENSITY_FILE_NAME, true);                 
	      
	                          try {                                  
	                        	  long uviReading=0;
								sunClassificationFile.append(System.currentTimeMillis() +"\t" + classification + "\t" + uviReading + "\n");           
	                          } catch (IOException ex){
	                          }
	                          finally{
	                        	  sunClassificationFile.flush();
	                        	  sunClassificationFile.close();
	                          }
	                          
	                      } catch (IOException e) {
	                          e.printStackTrace();
	                      }
	                      */
	                      
	                      //Reset the Values
	                      blockSize = 0;
	                      // time = System.currentTimeMillis();
	                      maxLightMagnitude = Double.MIN_VALUE;
	                      minLightMagnitude = Double.MAX_VALUE;
	                      stdLightMagnitude = 0;
	                      varianceIntensity = 0;
	                      meanAbsoluteDeveationLightIntensity = 0;
	                      meanLightIntensity = 0;
	                  }
	              } catch (Exception e) {
	                  e.printStackTrace();
	              }
			}
		}
	}
	
	/************ AsyncTask **************/

	private int mActivityInference = -1;
	private int mMaxActivityInferenceWindow = 5;//Allow 5 readings to dictate the Voted activity
	int inferenceCount=0;
	Map<Integer,Integer> mInferredActivityTypeMap = new HashMap<Integer,Integer>(Globals.FEAT_NUMBER_FEATURES);
	
	private class AccelerometerActivityClassificationTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg0) {
			int blockSize = 0;
			FFT fft = new FFT(Globals.ACCELEROMETER_BLOCK_CAPACITY);
			double[] accBlock = new double[Globals.ACCELEROMETER_BLOCK_CAPACITY];
			double[] re = accBlock;
			double[] im = new double[Globals.ACCELEROMETER_BLOCK_CAPACITY];
			
			double max = Double.MIN_VALUE;
			
			while (true) {
				try {
					// need to check if the AsyncTask is cancelled or not in the while loop
					if (isCancelled () == true)
						return null;
					
					ArrayList<Double> featVect = new ArrayList<Double>(Globals.ACCELEROMETER_BLOCK_CAPACITY + 1);
					
					// Dumping buffer
					double accelValue = mAccBuffer.take().doubleValue();
					accBlock[blockSize++] = accelValue;
					
					
					// JERRID: Pops the "head" element from the Blocking Queue one at a time
					if(accelValue > max)
						max = accelValue;
					
					
					if (blockSize == Globals.ACCELEROMETER_BLOCK_CAPACITY) {
						//Recieved a full block/disable data collection						
						pauseDataCollection();
						
						blockSize = 0;
						
						// time = System.currentTimeMillis();
						/* OLD CODE - jerrid
						max = .0;						 
						for (double val : accBlock) {
							if (max < val) {
								max = val;
							}
						}	
						*/
						fft.fft(re, im);
						for (int i = 0; i < re.length; i++) {
							double mag = Math.sqrt(re[i] * re[i] + im[i] * im[i]);
							featVect.add(mag);
							im[i] = .0; // Clear the field
						}
							
						// Append max after frequency component
						featVect.add(max);						
						int value = (int) WekaClassifier.classify(featVect.toArray());
						Log.d("mag", String.valueOf(value));
						

						//JERRID: Infer motion type based upon majority vote------
						int count = mInferredActivityTypeMap.containsKey(value) ? mInferredActivityTypeMap.get(value) : 0;
						mInferredActivityTypeMap.put(value, count + 1);
						inferenceCount++;
						/* JERRID: Old code
						 * This code block performs running mean of Activity inferences. 
						 * This won't work becasue of truncation (eg: 3+3+3+2+2/5 yeilds 2.8 roughly
						 * but 3 should be the inferred
						if(inferenceCount++ == 0){//1st motion value
							
		                	  mActivityInference = value;
						}else{//1..* activity values averaged using running mean
							mActivityInference = (value + mActivityInference * (inferenceCount-1)) / inferenceCount;
						}
						*/
		                  
						//Once the max inference count is reached, do a half sliding window 
						//(ie: the current mean inference value weight counts for 2 of next 5 readings)

	                	int maxInferenceKey=-1,maxInferenceValue=-1;
	                	
		                if(inferenceCount == mMaxActivityInferenceWindow)  {
		                	//Old Code --- inferenceCount = 2;
		                	//iterating over keys only
		                	int maxIndex = -1;
		                	for (Map.Entry<Integer, Integer> entry : mInferredActivityTypeMap.entrySet()) {
		                		int val = entry.getValue();
		                		if(val > maxIndex){
		                			maxIndex = val;
		                			maxInferenceValue = val;
		                			maxInferenceKey = entry.getKey();
		                		}
		                		
		                		//Reset instance counts to 0 to avoid a 2nd for loop to clear vector
		                		entry.setValue(0);
		                	}
		                
		                	//Weight the Mode activity with the highest
		                	mInferredActivityTypeMap.put(maxInferenceKey, 2);
		                	inferenceCount=0;
		                }
						
						//int maxIndex = 0;their original code
						//if (mInferenceCount[maxIndex] < mInferenceCount[1]) maxIndex = 1;
						//if (mInferenceCount[maxIndex] < mInferenceCount[2]) maxIndex = 2;their original code
		                
						mInferredActivityType = Globals.INFERENCE_MAPPING[maxInferenceValue == -1 ? value : maxInferenceValue];//maxIndex];
						int currentActivity = Globals.INFERENCE_MAPPING[value];
						// send broadcast with the CURRENT activity type
						//---------------
						
						mMotionUpdateBroadcast.putExtra(CURRENT_MOTION_TYPE, currentActivity);
						mMotionUpdateBroadcast.putExtra(VOTED_MOTION_TYPE, mInferredActivityType);
						
						sendBroadcast(mMotionUpdateBroadcast);
						
						//Reset the max value
						max = Double.MIN_VALUE;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		}
	}
}
