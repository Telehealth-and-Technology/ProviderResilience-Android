package org.t2.pr.activities;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.Scoring;
import org.t2.pr.classes.PreferenceHelper;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Create screen provides navigation to all activity choosing functionality
 * @author stephenody
 *
 */
public class HomeActivity extends ABSActivity implements OnClickListener
{

	public TextView tv_Vacation;

	public TextView tv_rratingvalue;
	public TextView tv_rratinglabel;

	public TextView tv_qolcsvalue;
	public TextView tv_qolbvalue;
	public TextView tv_qolstsvalue;

	public ImageView iv_gradientarc;

	public ImageView iv_qolcsvalue;
	public ImageView iv_qolbvalue;
	public ImageView iv_qolstsvalue;

	public TextView tvYearShadow;
	public TextView tvYearDigit;
	public TextView tvYearLabel;

	public TextView tvMonthShadow;
	public TextView tvMonthDigit;
	public TextView tvMonthLabel;

	public TextView tvDayShadow;
	public TextView tvDayDigit;
	public TextView tvDayLabel;

	public TextView tvHourShadow;
	public TextView tvHourDigit;
	public TextView tvHourLabel;

	public TextView tvMinuteShadow;
	public TextView tvMinuteDigit;
	public TextView tvMinuteLabel;

	Typeface tfDigitalNum;
	Typeface tfDigitalChr;

	private ImageButton btnUpdateClock;
	private ImageButton btnUpdateBurnout;
	private ImageButton btnUpdateBK;
	private ImageButton btnUpdatePROQOL;

	static final int DATE_DIALOG_ID = 0;
	private int cYear;
	private int cMonth;
	private int cDay;
	private int cHour;
	private int cMin;

	private int vYear;
	private int vMonth;
	private int vDay;

	//	private int dYear;
	//	private int dMonth;
	//	private int dDay;

	private String sDate = "";

	private Timer minTimer;

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		onEvent("Opened Home Activity");

		setContentView(R.layout.home_new);

		this.SetMenuVisibility(1);
		this.btnMainDashboard.setChecked(true);

		Global.appContext = this.getApplicationContext();
		sDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());

		tv_Vacation = (TextView)this.findViewById(R.id.tv_Vacation);

		tfDigitalNum = Typeface.createFromAsset(getAssets(), "digitalkmono.ttf");
		tfDigitalChr = Typeface.createFromAsset(getAssets(), "digiitalic.ttf");

		tvYearShadow = (TextView)this.findViewById(R.id.tv_year_shadow);
		tvYearDigit = (TextView)this.findViewById(R.id.tv_year_digit);
		tvYearLabel = (TextView)this.findViewById(R.id.tv_year_label);
		tvYearShadow.setTypeface(tfDigitalNum);
		tvYearDigit.setTypeface(tfDigitalNum);
		tvYearLabel.setTypeface(tfDigitalChr);

		tvMonthShadow = (TextView)this.findViewById(R.id.tv_month_shadow);
		tvMonthDigit = (TextView)this.findViewById(R.id.tv_month_digit);
		tvMonthLabel = (TextView)this.findViewById(R.id.tv_month_label);
		tvMonthShadow.setTypeface(tfDigitalNum);
		tvMonthDigit.setTypeface(tfDigitalNum);
		tvMonthLabel.setTypeface(tfDigitalChr);

		tvDayShadow = (TextView)this.findViewById(R.id.tv_day_shadow);
		tvDayDigit = (TextView)this.findViewById(R.id.tv_day_digit);
		tvDayLabel = (TextView)this.findViewById(R.id.tv_day_label);
		tvDayShadow.setTypeface(tfDigitalNum);
		tvDayDigit.setTypeface(tfDigitalNum);
		tvDayLabel.setTypeface(tfDigitalChr);

		tvHourShadow = (TextView)this.findViewById(R.id.tv_hour_shadow);
		tvHourDigit = (TextView)this.findViewById(R.id.tv_hour_digit);
		tvHourLabel = (TextView)this.findViewById(R.id.tv_hour_label);
		tvHourShadow.setTypeface(tfDigitalNum);
		tvHourDigit.setTypeface(tfDigitalNum);
		tvHourLabel.setTypeface(tfDigitalChr);

		tvMinuteShadow = (TextView)this.findViewById(R.id.tv_minute_shadow);
		tvMinuteDigit = (TextView)this.findViewById(R.id.tv_minute_digit);
		tvMinuteLabel = (TextView)this.findViewById(R.id.tv_minute_label);
		tvMinuteShadow.setTypeface(tfDigitalNum);
		tvMinuteDigit.setTypeface(tfDigitalNum);
		tvMinuteLabel.setTypeface(tfDigitalChr);

		btnUpdateClock = (ImageButton)this.findViewById(R.id.btn_updateclock);
		btnUpdateClock.setOnClickListener(this);
		btnUpdateBurnout = (ImageButton)this.findViewById(R.id.btn_updateburnout);
		btnUpdateBurnout.setOnClickListener(this);
		btnUpdateBK = (ImageButton)this.findViewById(R.id.btn_updatebk);
		btnUpdateBK.setOnClickListener(this);
		btnUpdatePROQOL = (ImageButton)this.findViewById(R.id.btn_updateproqol);
		btnUpdatePROQOL.setOnClickListener(this);

		iv_gradientarc = (ImageView)this.findViewById(R.id.iv_gradientarc);

		// get the current date
		Calendar c = Calendar.getInstance();
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

		tv_rratingvalue = (TextView)this.findViewById(R.id.tv_rratingvalue);
		tv_rratinglabel = (TextView)this.findViewById(R.id.tv_rratinglabel);

		tv_qolcsvalue = (TextView)this.findViewById(R.id.tv_qolcsvalue);
		tv_qolbvalue = (TextView)this.findViewById(R.id.tv_qolbvalue);
		tv_qolstsvalue = (TextView)this.findViewById(R.id.tv_qolstsvalue);

		iv_qolcsvalue = (ImageView)this.findViewById(R.id.iv_qolcsvalue);
		iv_qolbvalue = (ImageView)this.findViewById(R.id.iv_qolbvalue);
		iv_qolstsvalue = (ImageView)this.findViewById(R.id.iv_qolstsvalue);

		updateDisplay();

		//ShowWelcome();

		//Show daily card (once a day)
		/*int dayofyear = SharedPref.getPopupCardDay();
		int cdayofyear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		if(dayofyear != cdayofyear)
		{
			SharedPref.setPopupCardDay(cdayofyear);
			mHandler.postDelayed(startCardsRunnable, 500);
		}*/

		/*if(!SharedPref.getWelcomeMessage())
		{
			this.finish();
			btnMainAbout.performClick();
			SharedPref.setWelcomeMessage(true);
			Intent intent = ActivityFactory.getAboutActivity(this);
			intent.putExtra("shownav", false);
			this.startActivity(intent);
		}*/

	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.FlurryActivity#onStop()
	 */
	@Override
	protected void onStop() {

		super.onStop();
	}

	/**
	 * A timer to handle the display of the vacation clock updates
	 */
	private void TimerTick()
	{
		runOnUiThread(new Runnable(){

			@Override
			public void run() {
				updateDisplay();
			}});
	}

	/**
	 * Shows a welcome message on start of application, provides a checkbox to disable.
	 */
	public void ShowWelcomeNotUsed()
	{
		if(!Global.seenWelcome)
		{
			onEvent("Showing Welcome Message");

			if(PreferenceHelper.getWelcomeMessage())
			{
				//Build welcome HTML
				String results = "<HTML>";
				results += "<head>";

				results += "<BODY>";
				results += "<P>This app has been desinged for healthcare professionals who treat service members, veterans and their families. <P>It's purpose is to provide you with a set of tools that support resilience and fight burnout and compassion fatigue. Healthcare professionals know that they need to take care of themselves and they've told us they tend to know what do do to fight stress - that they just don't take the time to do it.";
				results += "<P>That's where this app comes in. We've developed the things providers have asked for: tools to help you keep track of how you're doing, reminders to do the things you already know you should be doing, and some interesting things to do that support resilience. <P>We even remind you on a daily basis to use the app. It only takes a few minutes a day to take care of yourself.";

				results += "";

				results += "</BODY>";
				results += "</HTML>";

				final Dialog dialog = new Dialog(this);
				dialog.setContentView(R.layout.popup);
				dialog.setTitle("Welcome:");
				dialog.setCancelable(true);

				TextView text = (TextView) dialog.findViewById(R.id.dialogbody);
				text.setText(Html.fromHtml(results));
				text.setTextSize(14f);
				text.setMovementMethod(new ScrollingMovementMethod());
				final CheckBox chk = (CheckBox) dialog.findViewById(R.id.dontShowAgain);
				Button button = (Button) dialog.findViewById(R.id.btnOK);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(chk.isChecked())
							PreferenceHelper.setWelcomeMessage(false);
						Global.seenWelcome = true;
						dialog.cancel();
					}
				});
				dialog.show();
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override 
	public void onPause()
	{
		super.onPause();
		minTimer.cancel();
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onResume()
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		updateDisplay();
		minTimer = new Timer();
		minTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerTick();
			}

		}, 0, 5000);

	}

	/**
	 * Updates the scoring display and the vacation clock display
	 */
	private void updateDisplay()
	{
		try
		{
			sDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());

			if(PreferenceHelper.getOnVacation())
			{
				tv_Vacation.setVisibility(View.VISIBLE);
				tvYearDigit.setVisibility(View.GONE);
				tvMonthDigit.setVisibility(View.GONE);
				tvDayDigit.setVisibility(View.GONE);
				tvHourDigit.setVisibility(View.GONE);
				tvMinuteDigit.setVisibility(View.GONE);

				tvYearShadow.setVisibility(View.GONE);
				tvMonthShadow.setVisibility(View.GONE);
				tvDayShadow.setVisibility(View.GONE);
				tvHourShadow.setVisibility(View.GONE);
				tvMinuteShadow.setVisibility(View.GONE);

				tvYearLabel.setVisibility(View.GONE);
				tvMonthLabel.setVisibility(View.GONE);
				tvDayLabel.setVisibility(View.GONE);
				tvHourLabel.setVisibility(View.GONE);
				tvMinuteLabel.setVisibility(View.GONE);

				tvYearDigit.setTextColor(Color.argb(255, 18, 145, 18));
				tvMonthDigit.setTextColor(Color.argb(255, 18, 145, 18));
				tvDayDigit.setTextColor(Color.argb(255, 18, 145, 18));
				tvHourDigit.setTextColor(Color.argb(255, 18, 145, 18));
				tvMinuteDigit.setTextColor(Color.argb(255, 18, 145, 18));
			}
			else
			{
				tv_Vacation.setVisibility(View.GONE);
				tvYearDigit.setVisibility(View.VISIBLE);
				tvMonthDigit.setVisibility(View.VISIBLE);
				tvDayDigit.setVisibility(View.VISIBLE);
				tvHourDigit.setVisibility(View.VISIBLE);
				tvMinuteDigit.setVisibility(View.VISIBLE);

				tvYearShadow.setVisibility(View.VISIBLE);
				tvMonthShadow.setVisibility(View.VISIBLE);
				tvDayShadow.setVisibility(View.VISIBLE);
				tvHourShadow.setVisibility(View.VISIBLE);
				tvMinuteShadow.setVisibility(View.VISIBLE);

				tvYearLabel.setVisibility(View.VISIBLE);
				tvMonthLabel.setVisibility(View.VISIBLE);
				tvDayLabel.setVisibility(View.VISIBLE);
				tvHourLabel.setVisibility(View.VISIBLE);
				tvMinuteLabel.setVisibility(View.VISIBLE);

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
				DateTime c = new DateTime(cYear, cMonth + 1, cDay, 0, 0);
				Period p = new Period(v, c, PeriodType.yearMonthDay());

				if(p.getYears() < 0)
					tvYearDigit.setText("00");
				else if(p.getYears() < 10)
					tvYearDigit.setText("0" + p.getYears());
				else
					tvYearDigit.setText("" + p.getYears());

				if(p.getMonths() < 0)
					tvMonthDigit.setText("00");
				else if(p.getMonths() < 10)
					tvMonthDigit.setText("0" + p.getMonths());
				else
					tvMonthDigit.setText("" + p.getMonths());

				if(p.getDays() < 0)
					tvDayDigit.setText("00");
				else if(p.getDays() < 10)
					tvDayDigit.setText("0" + p.getDays());
				else
					tvDayDigit.setText("" + p.getDays());

				if(cHour < 0)
					tvHourDigit.setText("00");
				else if(cHour < 10)
					tvHourDigit.setText("0" + cHour);
				else
					tvHourDigit.setText("" + cHour);

				if(cMin < 0)
					tvMinuteDigit.setText("00");
				else if(cMin < 10)
					tvMinuteDigit.setText("0" + cMin);
				else
					tvMinuteDigit.setText("" + cMin);

				//Set color based on leave score
				if(Scoring.LeaveClockScore() == 20)
				{
					tvYearDigit.setTextColor(Color.GREEN);
					tvMonthDigit.setTextColor(Color.GREEN);
					tvDayDigit.setTextColor(Color.GREEN);
					tvHourDigit.setTextColor(Color.GREEN);
					tvMinuteDigit.setTextColor(Color.GREEN);
				}
				else if(Scoring.LeaveClockScore() == 15)
				{
					tvYearDigit.setTextColor(Color.YELLOW);
					tvMonthDigit.setTextColor(Color.YELLOW);
					tvDayDigit.setTextColor(Color.YELLOW);
					tvHourDigit.setTextColor(Color.YELLOW);
					tvMinuteDigit.setTextColor(Color.YELLOW);
				}
				else if(Scoring.LeaveClockScore() == 10)
				{
					tvYearDigit.setTextColor(Color.YELLOW);
					tvMonthDigit.setTextColor(Color.YELLOW);
					tvDayDigit.setTextColor(Color.YELLOW);
					tvHourDigit.setTextColor(Color.YELLOW);
					tvMinuteDigit.setTextColor(Color.YELLOW);
				}
				else if(Scoring.LeaveClockScore() == 5)
				{
					tvYearDigit.setTextColor(Color.YELLOW);
					tvMonthDigit.setTextColor(Color.YELLOW);
					tvDayDigit.setTextColor(Color.YELLOW);
					tvHourDigit.setTextColor(Color.YELLOW);
					tvMinuteDigit.setTextColor(Color.YELLOW);
				}
				else
				{
					tvYearDigit.setTextColor(Color.RED);
					tvMonthDigit.setTextColor(Color.RED);
					tvDayDigit.setTextColor(Color.RED);
					tvHourDigit.setTextColor(Color.RED);
					tvMinuteDigit.setTextColor(Color.RED);
				}

			}

			int totalResScore = Scoring.TotalResilienceScore(sDate);
			tv_rratingvalue.setText("" + totalResScore);


			BitmapFactory.Options opts=new BitmapFactory.Options();

			//Only available on api 11+
			if(android.os.Build.VERSION.SDK_INT >= 11)
				opts.inMutable = true;
			

			opts.inDither=true;
			opts.inPurgeable=true;
			opts.inInputShareable=true;
			opts.inTempStorage=new byte[32 * 1024];
			opts.inPreferredConfig = Bitmap.Config.RGB_565;

			if(android.os.Build.VERSION.SDK_INT >= 11)
			{
				Global.setHomeBitmap1(BitmapFactory.decodeResource(this.getResources(), R.drawable.arcgradient, opts));
				Global.setHomeBitmap2(BitmapFactory.decodeResource(this.getResources(), R.drawable.arcarrow, opts));
			}
			else
			{
				Global.setHomeBitmap1(BitmapFactory.decodeResource(this.getResources(), R.drawable.arcgradient, opts).copy(Bitmap.Config.ARGB_8888, true));
				Global.setHomeBitmap2(BitmapFactory.decodeResource(this.getResources(), R.drawable.arcarrow, opts).copy(Bitmap.Config.ARGB_8888, true));				
			}

			int width = Global.bmpHome2.getWidth();
			int height = Global.bmpHome2.getHeight();
			int newWidth = 100;
			int newHeight = 100;

			int halfWidth = 50;
			int meterHypotenuseL = 320 - 50;
			int meterCenterPtX = (Global.bmpHome1.getWidth()/2);
			int meterCenterPtY = (Global.bmpHome1.getHeight()/2) + 100;

			float scaleWidth = ((float) newWidth) / width;
			float scaleHeight = ((float) newHeight) / height;

			double angle = (totalResScore*Math.PI)/100;
			double meterHypotenuse = meterHypotenuseL + halfWidth*Math.cos(angle);
			int xPos = (int) (meterCenterPtX - meterHypotenuse*Math.cos(angle) - halfWidth);
			int yPos = (int) (meterCenterPtY - meterHypotenuse*Math.sin(angle));

			if (Math.cos(angle)< 0) xPos += -2.3*Math.cos(angle)*halfWidth; 

			Matrix matrix = new Matrix();
			matrix.preScale(scaleWidth, scaleHeight);
			matrix.preRotate(-90);
			matrix.postRotate((float) Math.toDegrees(angle));

			//set the arrow bitmap to the rotated view.
			Global.setHomeBitmap2(Bitmap.createBitmap(Global.bmpHome2, 0, 0, width, height, matrix, true));

			Global.canvas.drawBitmap(Global.bmpHome2, xPos, yPos, null);
			iv_gradientarc.setBackgroundDrawable(new BitmapDrawable(getResources(),Global.bmpHome1));

			if(Scoring.getBurnoutRemainder() == 0)
				((TextView)this.findViewById(R.id.tv_burnoutlabeltime)).setText("Update is due");
			else
				((TextView)this.findViewById(R.id.tv_burnoutlabeltime)).setText("Update in " + Scoring.getBurnoutRemainder() + " days");

			if(Scoring.getQOLRemainder() == 0)
				((TextView)this.findViewById(R.id.tv_proqollabeltime)).setText("Update is due");
			else
				((TextView)this.findViewById(R.id.tv_proqollabeltime)).setText("Update in " + Scoring.getQOLRemainder() + " days");

			//System.gc();
		}
		catch(Exception ex){}

	}

	private DatePickerDialog.OnDateSetListener mDateSetListener =
			new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, 
				int monthOfYear, int dayOfMonth) {
			vYear = year;
			vMonth = monthOfYear;
			vDay = dayOfMonth;
			PreferenceHelper.setVacationYear(vYear);
			PreferenceHelper.setVacationMonth(vMonth);
			PreferenceHelper.setVacationDay(vDay);
			updateDisplay();
		}
	};

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,
					mDateSetListener,
					vYear, vMonth, vDay);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) 
	{
		super.onClick(v);

		switch(v.getId()) 
		{
		case R.id.btn_updateclock:
			onEvent("Home Activity: Update Clock Pressed");

			this.startActivity(ActivityFactory.getRRClockActivity(this));
			break;
		case R.id.btn_updateburnout:
			onEvent("Home Activity: Update Burnout Pressed");

			this.startActivity(ActivityFactory.getUpdateBurnoutActivity(this));
			break;
		case R.id.btn_updatebk:
			onEvent("Home Activity: Update BK Pressed");

			this.startActivity(ActivityFactory.getRBQuestionsActivity(this));
			break;
		case R.id.btn_updateproqol:
			onEvent("Home Activity: Update PROQOL Pressed");

			this.startActivity(ActivityFactory.getProQOLActivity(this));
			break;
		}
	}

	/*private Runnable startCardsRunnable = new Runnable() {
		public void run() {
			startCardsActivity();
		}
	};

	private void startCardsActivity() 
	{
		Intent intent = new Intent(this, CardsActivity.class);
		intent.putExtra("random", true);
		this.startActivity(intent);
	}*/

}
