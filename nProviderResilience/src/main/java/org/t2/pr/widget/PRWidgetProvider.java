package org.t2.pr.widget;

import java.util.Calendar;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.DatabaseHelper;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.Scoring;
import org.t2.pr.classes.PreferenceHelper;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.RemoteViews;

public class PRWidgetProvider extends AppWidgetProvider {

	//private static final String ACTION_CLICK = "ACTION_CLICK";

	private int vYear;
	private int vMonth;
	private int vDay;
	private int cYear;
	private int cMonth;
	private int cDay;
	private int cHour;
	private int cMin;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		Global.appContext = context;
		if(Global.databaseHelper == null)
			Global.databaseHelper = new DatabaseHelper(context);
		
		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				PRWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			// Create some random data
			//int number = (new Random().nextInt(100));

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			
			Bitmap myBitmap = Bitmap.createBitmap(300, 50, Bitmap.Config.ARGB_4444);
		    Canvas myCanvas = new Canvas(myBitmap);
		    Paint paint = new Paint();
			Typeface tfDigitalNum = Typeface.createFromAsset(context.getAssets(), "digitalkmono.ttf");
			paint.setAntiAlias(true);
		    //paint.setSubpixelText(true);
		    paint.setTypeface(tfDigitalNum);
		    paint.setStyle(Paint.Style.FILL);
		    paint.setColor(Color.GREEN);
		    
		    if(Scoring.LeaveClockScore() == 20)
			{
		    	paint.setColor(Color.GREEN);
			}
			else if(Scoring.LeaveClockScore() == 15)
			{
		    	paint.setColor(Color.YELLOW);
			}
			else if(Scoring.LeaveClockScore() == 10)
			{
		    	paint.setColor(Color.YELLOW);
			}
			else if(Scoring.LeaveClockScore() == 5)
			{
		    	paint.setColor(Color.YELLOW);
			}
			else
			{
		    	paint.setColor(Color.RED);
			}
		    
		    paint.setTextSize(44);
		    //paint.setTextAlign(Align.LEFT);
		    
		    final Calendar c = Calendar.getInstance();
			cYear = c.get(Calendar.YEAR);
			cMonth = c.get(Calendar.MONTH);
			cDay = c.get(Calendar.DAY_OF_MONTH);
			cHour = c.get(Calendar.HOUR_OF_DAY);
			cMin = c.get(Calendar.MINUTE);
			
		    if(PreferenceHelper.getVacationYear() == 0)
			{
				// get the current date
				vYear = cYear;
				vMonth = cMonth;
				vDay = cDay;
			}
			else
			{
				// get the saved date
				vYear = PreferenceHelper.getVacationYear();
				vMonth = PreferenceHelper.getVacationMonth();
				vDay = PreferenceHelper.getVacationDay();
			}

			DateTime v = new DateTime(vYear, vMonth + 1, vDay, 0, 0);
			DateTime cs = new DateTime(cYear, cMonth + 1, cDay, 0, 0);
			Period p = new Period(v, cs, PeriodType.yearMonthDay());

			String tvYearDigit = "88";
			String tvMonthDigit = "88";
			String tvDayDigit = "88";
			String tvHourDigit = "88";
			String tvMinuteDigit = "88";

			if(p.getYears() < 0)
				tvYearDigit="00";
			else if(p.getYears() < 10)
				tvYearDigit="0" + p.getYears();
			else
				tvYearDigit="" + p.getYears();

			if(p.getMonths() < 0)
				tvMonthDigit="00";
			else if(p.getMonths() < 10)
				tvMonthDigit="0" + p.getMonths();
			else
				tvMonthDigit="" + p.getMonths();

			if(p.getDays() < 0)
				tvDayDigit="00";
			else if(p.getDays() < 10)
				tvDayDigit="0" + p.getDays();
			else
				tvDayDigit="" + p.getDays();

			if(cHour < 0)
				tvHourDigit="00";
			else if(cHour < 10)
				tvHourDigit="0" + cHour;
			else
				tvHourDigit="" + cHour;

			if(cMin < 0)
				tvMinuteDigit="00";
			else if(cMin < 10)
				tvMinuteDigit="0" + cMin;
			else
				tvMinuteDigit="" + cMin;
		    
		    //myCanvas.drawRect(new Rect(0,0,300,100), paint);
		    //paint.setColor(Color.BLACK);
		    myCanvas.drawText(tvYearDigit + ":" + tvMonthDigit + ":" + tvDayDigit + ":" + tvHourDigit + ":" + tvMinuteDigit, 10, 40, paint);
			
		    String sDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
		    int totalResScore = Scoring.TotalResilienceScore(sDate);
		    if(Scoring.TotalResilienceString(totalResScore).equals("HIGH"))
		    {
		    	remoteViews.setImageViewResource(R.id.iv_rrating, R.drawable.gaugehoriz_green);
		    }
			else if(Scoring.TotalResilienceString(totalResScore).equals("LOW"))
		    {
		    	remoteViews.setImageViewResource(R.id.iv_rrating, R.drawable.gaugehoriz_red);
		    }
			else
		    {
		    	remoteViews.setImageViewResource(R.id.iv_rrating, R.drawable.gaugehoriz_blue);
		    }
	    	remoteViews.setTextViewText(R.id.tv_rratinglabel, Scoring.TotalResilienceString(totalResScore));
		    
			// Set the text
		    remoteViews.setImageViewBitmap(R.id.iv_rrclock, myBitmap);
			// Register an onClickListener
			//Intent intent = new Intent(context, PRWidgetProvider.class);
			Intent intent = ActivityFactory.getHomeActivity(context);

			//intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			//intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			remoteViews.setOnClickPendingIntent(R.id.layout, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
			
			
		}
	}
} 