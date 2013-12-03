package edu.dartmouth.cs.myruns5;


import com.fima.chartview.ChartView;
import com.fima.chartview.LinearSeries;
import com.fima.chartview.LinearSeries.LinearPoint;
import com.parse.ParseUser;

import edu.dartmouth.cs.myruns5.ValueLabelAdapter.LabelOrientation;

import android.app.Activity;
import android.os.Bundle;

public class GraphActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_graph);
		DisplayChart();
		
	}

	
	private void DisplayChart()
	{
	
	// Find the chart view
			ChartView chartView = (ChartView) findViewById(R.id.chart_view);

			// Create the data points
			LinearSeries series = new LinearSeries();
			series.setLineColor(0xFF0099CC);
			series.setLineWidth(2);

		/*	for (double i = 0d; i <= (2d * Math.PI); i += 0.1d) {
				series.addPoint(new LinearPoint(i, Math.sin(i)));
			}
*/
			int uvIndex;
			ParseUser currentUser = ParseUser
					.getCurrentUser();
			uvIndex=(Integer) currentUser.get("UVI");
	//		currentUser.put("fb_friends", friendUsers);
			for (double i = 0d; i <= (2d * Math.PI); i += 0.1d) {
				series.addPoint(new LinearPoint(i,i));
			}
			series.addPoint(new LinearPoint(3, uvIndex));
			// Add chart view data
			chartView.addSeries(series);
			chartView.setLeftLabelAdapter(new ValueLabelAdapter(this, LabelOrientation.VERTICAL));
			chartView.setBottomLabelAdapter(new ValueLabelAdapter(this, LabelOrientation.HORIZONTAL));
	
	}
	
	
}
