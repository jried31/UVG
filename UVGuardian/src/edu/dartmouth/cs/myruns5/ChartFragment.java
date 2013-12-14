package edu.dartmouth.cs.myruns5;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
<<<<<<< HEAD
import org.achartengine.chart.BarChart.Type;
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

<<<<<<< HEAD
// Show bar chart forecast UI
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
public class ChartFragment extends Fragment {

	private GraphicalView chartView;
	private float[] data;
	private UVIBroadcastReciever reciever;
	private IntentFilter filter;
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.chart_fragment, container, false);
		data = ((MainActivity) this.getActivity()).getUVIFragment().getWebUVI();
<<<<<<< HEAD
		//data = new float[]{1, 2, 3, 3, 5, 8, 9, 8, 8, 7, 5, 2, 0};
=======
		//data = new float[]{1, 2, 4, 4, 7, 10, 11, 10, 9, 6, 5, 3, 2};
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		drawChart(v);
		if (v == null)
			System.out.println("View is null!");
		else {
			v.setOnTouchListener(new SwipeListener(this.getActivity(), this
					.getFragmentManager(), ((MainActivity) this.getActivity())
					.getUVIFragment(), this,
					((MainActivity) this.getActivity()).getRecommendFragment(),
					2));
		}
		return v;
	}
<<<<<<< HEAD
	
	// Get the maximum data to set y axis
	private float getMax(float[] data){
		float max = 0;
		for(Float a : data){
			if(a > max)
				max = a;
		}
		return max;
	}

	// Get bar chart dataset
=======

>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
	private XYMultipleSeriesDataset getBarDemoDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		
		if(data == null){
			System.out.println("data is null!");
			return dataset;
		}
		
<<<<<<< HEAD
		XYSeries series1;
		for(int i = 0; i < 13; i++){
			series1 = new XYSeries("Web_UVI" + i);
			series1.add((double)i / 2.9 + 4, data[i]);
=======
		XYSeries series = new XYSeries("Web_UVI");
		for (int i = 0; i < 13; i++) {
			series.add(i + 6, data[i]);
		}
		dataset.addSeries(series);
		
		XYSeries series1;
		for(int i = 0; i < 13; i++){
			series1 = new XYSeries("Web_UVI" + i);
			series1.add(i + 6, data[i]);
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
			dataset.addSeries(series1);
		}

		return dataset;
	}

<<<<<<< HEAD
	// Get different color renderer
	private XYSeriesRenderer getRenderer(String color){
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(Color.parseColor(color));
		r.setDisplayChartValues(true);
		r.setChartValuesFormat(new DecimalFormat("0"));
		r.setChartValuesTextAlign(Align.CENTER);
		r.setChartValuesSpacing(5f);
		r.setChartValuesTextSize(40);
		r.setDisplayChartValuesDistance(4);
		return r;
	}
	
	// Set renderer to different bars
	public XYMultipleSeriesRenderer getBarDemoRenderer(float[] data) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setChartSettings(renderer, getMax(data));
		
		XYSeriesRenderer rg, ry, ro, rr, rp;
		rg = getRenderer("#008800");
		ry = getRenderer("#FFDD33");
		ro = getRenderer("#FF6600");
		rr = getRenderer("#CC0000");
		rp = getRenderer("#6600FF");
		
=======
	public XYMultipleSeriesRenderer getBarDemoRenderer(float[] data) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setChartSettings(renderer);
		
		XYSeriesRenderer rg = new XYSeriesRenderer();
		rg.setColor(Color.parseColor("#008800"));
		rg.setPointStyle(PointStyle.CIRCLE);
		rg.setFillPoints(true);
		rg.setDisplayChartValues(true);
		rg.setChartValuesFormat(new DecimalFormat("0"));
		rg.setChartValuesTextAlign(Align.CENTER);
		rg.setChartValuesSpacing(24f);
		rg.setChartValuesTextSize(40);
		rg.setDisplayChartValuesDistance(5);
		rg.setLineWidth(8f);
		
		XYSeriesRenderer ry = new XYSeriesRenderer();
		ry.setColor(Color.parseColor("#FFDD33"));
		ry.setPointStyle(PointStyle.CIRCLE);
		ry.setFillPoints(true);
		
		XYSeriesRenderer ro = new XYSeriesRenderer();
		ro.setColor(Color.parseColor("#FF6600"));
		ro.setPointStyle(PointStyle.CIRCLE);
		ro.setFillPoints(true);
		
		XYSeriesRenderer rr = new XYSeriesRenderer();
		rr.setColor(Color.parseColor("#CC0000"));
		rr.setPointStyle(PointStyle.CIRCLE);
		rr.setFillPoints(true);
		
		XYSeriesRenderer rp = new XYSeriesRenderer();
		rp.setColor(Color.parseColor("#6600FF"));
		rp.setPointStyle(PointStyle.CIRCLE);
		rp.setFillPoints(true);
		
		renderer.addSeriesRenderer(rg);

>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		for (int i = 0; i < data.length; i++) {
			switch ((int)data[i]) {
			case 0:
			case 1:
			case 2:
				renderer.addSeriesRenderer(rg);
				break;
			case 3:
			case 4:
			case 5:
				renderer.addSeriesRenderer(ry);
				break;
			case 6:
			case 7:
				renderer.addSeriesRenderer(ro);
				break;
			case 8:
			case 9:
			case 10:
				renderer.addSeriesRenderer(rr);
				break;
			case 11:
				renderer.addSeriesRenderer(rp);
				break;
			default:
				renderer.addSeriesRenderer(rp);
				break;
			}
		}
<<<<<<< HEAD
		
		return renderer;
	}

	// Set bar chart basic settings
	private void setChartSettings(XYMultipleSeriesRenderer renderer, float max) {
		SimpleDateFormat sf = new SimpleDateFormat("MMM dd, yyyy");
		renderer.setChartTitle("Hourly UVI Forecast\n" + sf.format(new Date()));
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setLabelsColor(Color.BLUE);
		renderer.setMarginsColor(Color.WHITE);
		renderer.setAxesColor(Color.BLUE);
		renderer.setXLabelsColor(Color.DKGRAY);
		renderer.setYLabelsColor(0, Color.DKGRAY);
		renderer.setXTitle("Time (Hour)");
		renderer.setYTitle("UV Index Level");
		renderer.setMargins(new int[]{0, 50, 10, 15});
		renderer.setBarWidth(30f);
		renderer.setXAxisMin(-1);
		renderer.setXAxisMax(13);
		for(int i = -1; i < 14; i++){
			renderer.addXTextLabel(i, String.valueOf((i + 6) > 12 ? (i - 6) : (i + 6)));
		}
		renderer.setXLabels(0);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(max + 2);
		renderer.setYLabels(15);
		renderer.setYLabelsAlign(Align.LEFT);
		renderer.setAxisTitleTextSize(40);
=======

		return renderer;
	}

	private void setChartSettings(XYMultipleSeriesRenderer renderer) {
		SimpleDateFormat sf = new SimpleDateFormat("MMM dd, yyyy");
		renderer.setChartTitle("Hourly UVI Forecast\n" + sf.format(new Date()));
		renderer.setLabelsColor(Color.BLUE);
		renderer.setXTitle("Time (Hour)");
		renderer.setYTitle("UV Index Level");
		renderer.setMargins(new int[]{0, 55, 10, 15});
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.parseColor("#D0E6FF"));
		renderer.setXAxisMin(5);
		renderer.setXAxisMax(19);
		renderer.setXLabels(15);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(12);
		renderer.setYLabels(15);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setAxisTitleTextSize(30);
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		renderer.setChartTitleTextSize(60);
		renderer.setShowLabels(true);
		renderer.setLabelsTextSize(30);
		renderer.setShowLegend(false);
		renderer.setPointSize(15f);
		renderer.setShowGrid(true);
		renderer.setDisplayValues(true);
		renderer.setZoomEnabled(false, false);
		renderer.setPanEnabled(false, false);
<<<<<<< HEAD
	}

	// Generate the chart
	public void drawChart(View v) {
		chartView = ChartFactory.getBarChartView(getActivity(),
				getBarDemoDataset(), getBarDemoRenderer(data), Type.DEFAULT);
=======
		// renderer.setPanLimits(new double[] { 0, 50, 0, 12 });
		// renderer.setZoomLimits(new double[] { 0, 50, 0, 12 });
	}

	public void drawChart(View v) {
		chartView = ChartFactory.getLineChartView(getActivity(),
				getBarDemoDataset(), getBarDemoRenderer(data));
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
		LinearLayout layout = (LinearLayout) v
				.findViewById(R.id.chart_fragment_container);
		
		if (chartView == null) {
			System.out.println("Chart is null!");
			return;
		}
		if (layout == null) {
			System.out.println("Layout is null!");
			return;
		}
		layout.addView(chartView, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		return;
	}
	
<<<<<<< HEAD
	// Update the chart
=======
>>>>>>> 41caeac9d001c06e34ff3e61ed5c48033055001e
	public void updateView(View v){
		drawChart(v);
	}

	// Recieves the current UVI broadcast updates from the Service
	class UVIBroadcastReciever extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			data = arg1.getExtras().getFloatArray(
					UltravioletIndexService.WEB_UVI);
			updateView(v);
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
