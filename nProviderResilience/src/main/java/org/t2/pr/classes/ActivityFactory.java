package org.t2.pr.classes;

import org.t2.pr.activities.AboutActivity;
import org.t2.pr.activities.BurnoutChartActivity;
import org.t2.pr.activities.CardsActivity;
import org.t2.pr.activities.EulaActivity;
import org.t2.pr.activities.HelperCardActivity;
import org.t2.pr.activities.HomeActivity;
import org.t2.pr.activities.LaughActivity;
import org.t2.pr.activities.PROQOLChartActivity;
import org.t2.pr.activities.ProQOLActivity;
import org.t2.pr.activities.RBQuestionsActivity;
import org.t2.pr.activities.RKQuestionsActivity;
import org.t2.pr.activities.RRClockActivity;
import org.t2.pr.activities.RRatingActivity;
import org.t2.pr.activities.RemindMeActivity;
import org.t2.pr.activities.SettingsActivity;
import org.t2.pr.activities.SplashActivity;
import org.t2.pr.activities.StretchesActivity;
import org.t2.pr.activities.ToolsActivity;
import org.t2.pr.activities.UpdateBurnoutActivity;
import org.t2.pr.activities.UpdateQOLActivity;
import org.t2.pr.activities.VideosActivity;

import android.content.Context;
import android.content.Intent;

public class ActivityFactory 
{

	// Splash
	public static Intent getSplashActivity(Context c) 
	{
		Intent intent = new Intent(c, SplashActivity.class);
		return intent;
	}

	public static Intent getEULAActivity(Context c) 
	{
		Intent intent = new Intent(c, EulaActivity.class);
		return intent;
	}

	// Home
	public static Intent getHomeActivity(Context c) 
	{
		Intent intent = new Intent(c, HomeActivity.class);
		return intent;
	}
	
	public static Intent getToolsActivity(Context c) 
	{
		Intent intent = new Intent(c, ToolsActivity.class);
		return intent;
	}
	
	public static Intent getCardsActivity(Context c) 
	{
		Intent intent = new Intent(c, CardsActivity.class);
		return intent;
	}
	
	public static Intent getAboutActivity(Context c) 
	{
		Intent intent = new Intent(c, AboutActivity.class);
		return intent;
	}
	
	public static Intent getSettingsActivity(Context c) 
	{
		Intent intent = new Intent(c, SettingsActivity.class);
		return intent;
	}
	
	public static Intent getRRClockActivity(Context c) 
	{
		Intent intent = new Intent(c, RRClockActivity.class);
		return intent;
	}
	
	public static Intent getRRatingActivity(Context c) 
	{
		Intent intent = new Intent(c, RRatingActivity.class);
		return intent;
	}
	
	public static Intent getProQOLActivity(Context c) 
	{
		Intent intent = new Intent(c, ProQOLActivity.class);
		return intent;
	}
	
	public static Intent getVideosActivity(Context c) 
	{
		Intent intent = new Intent(c, VideosActivity.class);
		return intent;
	}
	
	public static Intent getStretchesActivity(Context c) 
	{
		Intent intent = new Intent(c, StretchesActivity.class);
		return intent;
	}
	
	public static Intent getRemindMeActivity(Context c) 
	{
		Intent intent = new Intent(c, RemindMeActivity.class);
		return intent;
	}
	
	public static Intent getHelperCardActivity(Context c) 
	{
		Intent intent = new Intent(c, HelperCardActivity.class);
		return intent;
	}
	
	public static Intent getRBQuestionsActivity(Context c) 
	{
		Intent intent = new Intent(c, RBQuestionsActivity.class);
		return intent;
	}
	
	public static Intent getRKQuestionsActivity(Context c) 
	{
		Intent intent = new Intent(c, RKQuestionsActivity.class);
		return intent;
	}
	
	public static Intent getUpdateQOLActivity(Context c) 
	{
		Intent intent = new Intent(c, UpdateQOLActivity.class);
		return intent;
	}
	
	public static Intent getUpdateBurnoutActivity(Context c) 
	{
		Intent intent = new Intent(c, UpdateBurnoutActivity.class);
		return intent;
	}
	
	public static Intent getPROQOLChartActivity(Context c) 
	{
		Intent intent = new Intent(c, PROQOLChartActivity.class);
		return intent;
	}
	
	public static Intent getBurnoutChartActivity(Context c) 
	{
		Intent intent = new Intent(c, BurnoutChartActivity.class);
		return intent;
	}
	
	public static Intent getLaughActivity(Context c) 
	{
		Intent intent = new Intent(c, LaughActivity.class);
		return intent;
	}
	
}