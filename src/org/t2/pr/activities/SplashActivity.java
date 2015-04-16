package org.t2.pr.activities;

import java.util.Timer;
import java.util.TimerTask;

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.PreferenceHelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Opens a (non-dismissible) timed splash screen
 * 
 * @author Steve Ody (stephen.ody@tee2.org)
 */

public class SplashActivity extends PRActivity implements OnClickListener 
{
	private Timer timeoutTimer;

	private Handler startHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			stopTimer();
			startStartActivity();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.splash_activity);
		int splashDelay = 500;
		if(Global.DebugOn)
			splashDelay = 500;

		timeoutTimer = new Timer();
		timeoutTimer.schedule(new TimerTask() 
		{
			@Override
			public void run() 
			{
				startHandler.sendEmptyMessage(0);
			}
		}, splashDelay);

	}

	private void startStartActivity() 
	{
		if (PreferenceHelper.getIsEulaAccepted()) 
		{
			startMainActivity();
			this.finish();
		} 
		else
		{
			startEulaActivity();
		}
	}

	private void startEulaActivity() 
	{
		this.stopTimer();
		this.startActivity(ActivityFactory.getEULAActivity(this));
		this.finish();
	}

	private void startMainActivity() 
	{
		this.stopTimer();

		this.startActivity(ActivityFactory.getHomeActivity(this));

		/*int dayofyear = SharedPref.getPopupCardDay();
		int cdayofyear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		if(dayofyear != cdayofyear)
		{
			SharedPref.setPopupCardDay(cdayofyear);
			Intent intent = new Intent(this, CardsActivity.class);
			intent.putExtra("random", true);
			this.startActivity(intent);
		}
		else */
		

		this.finish();
	} 

	@Override
	protected void onStop() 
	{
		super.onStop();
		this.stopTimer();
	}

	private void stopTimer() 
	{
		if (timeoutTimer != null) 
		{
			timeoutTimer.cancel();
			timeoutTimer = null;
		}
	}

	@Override
	public void onClick(View v) 
	{
		startStartActivity();
	}
}
