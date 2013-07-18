package org.t2.pr.activities;

import java.util.Calendar;

import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.SharedPref;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class StartupActivity extends Activity {
	/** Called when the activity is first created. */

	Button btnStart;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		this.startActivity(ActivityFactory.getSplashActivity(this));
		this.finish();
	}
}