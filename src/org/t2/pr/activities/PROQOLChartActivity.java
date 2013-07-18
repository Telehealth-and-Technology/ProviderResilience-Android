package org.t2.pr.activities;

import java.util.ArrayList;
import java.util.Date;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.Scoring;

import zencharts.charts.DateChart;
import zencharts.charts.LineChart;
import zencharts.data.DatePoint;
import zencharts.data.DateSeries;
import zencharts.data.LinePoint;
import zencharts.data.LineSeries;

import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class PROQOLChartActivity extends ABSActivity implements OnClickListener
{

	private static DatabaseProvider db = new DatabaseProvider(Global.appContext);
	private Button btnUpdate;
	GraphicalView mChartView;
	//LinearLayout chartLayout;
	public DateChart dateChart;

	private static final double THREEDAYS = 81300000 *13;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		onEvent("Open PROQOLChart Activity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proqolchart);

		dateChart = (DateChart)this.findViewById(R.id.datechart);
		DateTime startTime = new DateTime().withMonthOfYear(1).withTime(0, 0, 0, 0);
		DateTime endTime = new DateTime().withMonthOfYear(12).withTime(0, 0, 0, 0);
		
		dateChart.loadFont("Elronmonospace.ttf", 16, 2, 2);
		dateChart.setPeriod(new Duration(startTime, endTime));
		dateChart.setPeriodStartTime(startTime);
		dateChart.maxValueManual = 50;
		
		getZenChartData();
		//chartLayout = (LinearLayout) findViewById(R.id.chart);

		try
		{
			this.SetMenuVisibility(1);
			this.btnMainTools.setChecked(true);
			btnUpdate = (Button)this.findViewById(R.id.btn_proqolbutton);
			btnUpdate.setOnClickListener(this);
		}
		catch(Exception ex){}

	}

	private void getZenChartData()
	{
		ArrayList<String> qoldates = (ArrayList<String>) db.selectQOLDates();

		if(qoldates.size() > 0)
		{
			DateSeries qolSeries = new DateSeries(this, R.drawable.circle);
			qolSeries.dashEffect = new float[] {10,20};
			qolSeries.lineColor = Color.RED;
			qolSeries.lineWidth = 5;

			DateSeries burnSeries = new DateSeries(this, R.drawable.triangle);
			burnSeries.dashEffect = new float[] {10,20};
			burnSeries.lineColor = Color.GREEN;
			burnSeries.lineWidth = 5;

			DateSeries stsSeries = new DateSeries(this, R.drawable.square);
			stsSeries.lineColor = Color.BLUE;
			stsSeries.lineWidth = 5;

			for(int cs = 0; cs < qoldates.size(); cs++)
			{
				String tdate = qoldates.get(cs);
				long date = new java.util.Date(tdate).getTime();
				double score = Scoring.QOLCompassionScore(qoldates.get(cs));
				qolSeries.add(new DatePoint(date, (int)score, ""));
			}

			for(int burn = 0; burn < qoldates.size(); burn++)
			{

				String tdate = qoldates.get(burn);
				long date = new java.util.Date(tdate).getTime();
				double score = Scoring.QOLBurnoutScore(qoldates.get(burn));
				burnSeries.add(new DatePoint(date, (int)score, ""));
			}

			for(int sts = 0; sts < qoldates.size(); sts++)
			{

				String tdate = qoldates.get(sts);
				long date = new java.util.Date(tdate).getTime();
				double score = Scoring.QOLSTSScore(qoldates.get(sts));
				stsSeries.add(new DatePoint(date, (int)score, ""));
			}

			dateChart.addSeries(qolSeries);
			dateChart.addSeries(burnSeries);
			dateChart.addSeries(stsSeries);
			
			long sdate = new java.util.Date(qoldates.get(0)).getTime();
			long edate = new java.util.Date(qoldates.get(qoldates.size()-1)).getTime();
			DateTime startTime = new DateTime(sdate).withTime(0, 0, 0, 0);
			DateTime endTime = new DateTime(edate).withTime(0, 0, 0, 0).plusHours(24);
			dateChart.setPeriod(new Duration(startTime, endTime));
			dateChart.setPeriodStartTime(startTime);
			
		}
	}

	protected void onResume() 
	{
		super.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}

	@Override
	public void onStart()
	{

		super.onStart();
	}

	@Override
	public void onClick(View v) 
	{
		super.onClick(v);

		switch(v.getId()) 
		{
		case R.id.btn_proqolbutton:
			onEvent("PROQOLChart Activity: Update Pressed");
			this.startActivity(ActivityFactory.getUpdateQOLActivity(this));
			break;
		}
	}

}