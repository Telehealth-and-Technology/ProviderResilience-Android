package org.t2.pr.activities;

import java.util.Calendar;

import org.t2.pr.R;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.NotificationService;
import org.t2.pr.classes.PreferenceHelper;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.ToggleButton;

/**
 * Allows changing application wide settings
 * @author stephenody
 *
 */
public class SettingsActivity extends ABSActivity
{
	Button btnReset;
	Button btnSetReset;
	Button btnFeedback;
	Button btnSetTime;
	private ToggleButton toggle_welcome;
	private ToggleButton toggle_reminders;
	static final int TIME_DIALOG_ID = 1;
	static final int RESET_DIALOG_ID = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		onEvent("Opened Settings Activity ");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		this.SetMenuVisibility(1);
		this.btnMainSettings.setChecked(true);

		btnReset = (Button)this.findViewById(R.id.toggle_reset);
		btnReset.setOnClickListener(this);

		btnFeedback = (Button)this.findViewById(R.id.toggle_feedback);
		btnFeedback.setOnClickListener(this);

		btnSetTime = (Button)this.findViewById(R.id.toggle_setrtime);
		btnSetTime.setOnClickListener(this);

		toggle_welcome = (ToggleButton)this.findViewById(R.id.toggle_welcome);
		toggle_welcome.setOnClickListener(this);
		toggle_welcome.setChecked(PreferenceHelper.getWelcomeMessage());

		toggle_reminders = (ToggleButton)this.findViewById(R.id.toggle_reminders);
		toggle_reminders.setOnClickListener(this);
		toggle_reminders.setChecked(PreferenceHelper.getReminders());

		btnSetReset = (Button)this.findViewById(R.id.toggle_setreset);
		btnSetReset.setOnClickListener(this);

	}

	/**
	 * Confirmation dialog before clearing user data
	 */
	public void AskClearData()
	{
		onEvent("Settings Activity: Clear Data");

		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle("Accessibility");
		myAlertDialog.setMessage("Are you sure you want to delete all your saved data?");
		myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				onEvent("Settings Activity: Cleared Data (yes selected)");

				ClearData();
			}});
		myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				onEvent("Settings Activity: Clear Data (no selected)");

			}});
		myAlertDialog.show();
	}

	/**
	 * Clears all user entered data (QOL/VAS)
	 */
	public void ClearData()
	{
		//Clear the data
		Global.databaseHelper.ClearData();

		//Clear Vacation clock
		PreferenceHelper.setVacationDay(0);
		PreferenceHelper.setVacationMonth(0);
		PreferenceHelper.setVacationYear(0);

		//If debug, ask insert test data
		if(Global.DebugOn)
		{
			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
			myAlertDialog.setTitle("Accessibility");
			myAlertDialog.setMessage("Do you want to enter some test data?");
			myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					EnterTestData();
				}});
			myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					// do nothing
				}});
			myAlertDialog.show();
		}

	}

	/**
	 * Adds some test data in the database for chart display
	 */
	public void EnterTestData()
	{
		Global.databaseHelper.EnterTestData();
	}

	/* (non-Javadoc)
	 * @see org.t2.pr.activities.ABSActivity#onStart()
	 */
	@Override
	public void onStart()
	{

		super.onStart();
	}

	/**
	 * Opens and pre-populates an email intent
	 */
	public void Feedback()
	{
		onEvent("Settings Activity: Feedback pressed");

		try
		{
			//Send action_send to OS
			Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
			shareIntent.setType("plain/text");

			//populate content for message body and email recipients
			shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Provider Resilience Feedback");
			shareIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"usarmy.ncr.medcom-usamrmc-dcoe.mbx.t2-central@mail.mil"});
			shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Type feedback here, thank you!");

			startActivity(Intent.createChooser(shareIntent, "Send Feedback"));
		}
		catch(Exception ex)
		{}
	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
			new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			PreferenceHelper.setNotifyHour(hourOfDay);
			PreferenceHelper.setNotifyMinute(minute);
			SetReminder();

		}
	};
	private TimePickerDialog.OnTimeSetListener mResetTimeSetListener =
			new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			PreferenceHelper.setResetHour(hourOfDay);
			PreferenceHelper.setResetMinute(minute);
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this,
					mTimeSetListener,
					PreferenceHelper.getNotifyHour(), PreferenceHelper.getNotifyMinute(), false);
		case RESET_DIALOG_ID:
			return new TimePickerDialog(this,
					mResetTimeSetListener,
					PreferenceHelper.getResetHour(), PreferenceHelper.getResetMinute(), false);
		}
		super.onCreateDialog(id);
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
		case R.id.toggle_reset:
			AskClearData();
			break;
		case R.id.toggle_feedback:
			Feedback();
			break;
		case R.id.toggle_welcome:	
			onEvent("Settings Activity: Toggle Welcome: " + toggle_welcome.isChecked());

			PreferenceHelper.setWelcomeMessage(toggle_welcome.isChecked());
			break;
		case R.id.toggle_reminders:		
			onEvent("Settings Activity: Toggle Reminders: " + toggle_reminders.isChecked());

			PreferenceHelper.setReminders(toggle_reminders.isChecked());
			SetReminder();
			break;
		case R.id.toggle_setrtime:
			onEvent("Settings Activity: Set reminder time");

			showDialog(TIME_DIALOG_ID);
			break;
		case R.id.toggle_setreset:
			showDialog(RESET_DIALOG_ID);
			break;
		}
	}

	public void SetReminder()
	{
		final AlarmManager mgr =
				(AlarmManager)SettingsActivity.this.getSystemService(Context.ALARM_SERVICE);
		final Intent intent = new Intent(SettingsActivity.this,NotificationService.class);
		final PendingIntent pend =
				PendingIntent.getService(SettingsActivity.this, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);

		if(PreferenceHelper.getReminders())
		{
			Calendar nc = Calendar.getInstance();
			nc.set(Calendar.HOUR_OF_DAY, PreferenceHelper.getNotifyHour());
			nc.set(Calendar.MINUTE, PreferenceHelper.getNotifyMinute());
			nc.set(Calendar.SECOND, 0);

			// Schedule an alarm
			mgr.setRepeating(AlarmManager.RTC_WAKEUP, nc.getTimeInMillis(), (1000*60*60*24*7), pend);
		}
		else
		{
			mgr.cancel(pend);
		}
	}

}
