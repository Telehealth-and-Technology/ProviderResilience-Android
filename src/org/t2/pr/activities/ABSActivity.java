package org.t2.pr.activities;

import java.util.List;

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.SharedPref;
import org.t2.pr.classes.ToggledImageButton;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Base class that handles application navigation
 * @author stephenody
 *
 */
public abstract class ABSActivity extends FlurryActivity implements OnClickListener
{

	private static final int DIALOG_STATISTICS = 0;
	public static final int Menu1 = Menu.FIRST + 1;
	public static final int Menu2 = Menu.FIRST + 2;
	
	@SuppressWarnings("unused")
	private boolean isContentViewSet = false;
	
	private LinearLayout llMainMenu;
	public ToggledImageButton btnMainDashboard;
	public ToggledImageButton btnMainTools;
	public ToggledImageButton btnMainCards;
	public ToggledImageButton btnMainAbout;
	public ToggledImageButton btnMainSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		this.onPageView();
	}



	@Override
	public void setContentView(int layoutResID) 
	{
		super.setContentView(layoutResID);
		this.isContentViewSet = true;
		initButtons();
	}

	@Override
	public void setContentView(View view, LayoutParams params) 
	{
		super.setContentView(view, params);
		this.isContentViewSet = true;
		initButtons();
	}

	@Override
	public void setContentView(View view) 
	{
		super.setContentView(view);
		this.isContentViewSet = true;
		initButtons();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
	}

	@Override
	protected void onStart() 
	{
		super.onStart();
	}

	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		if((this instanceof HomeActivity))
		{
	    new AlertDialog.Builder(this)
	           .setMessage("Are you sure you want to exit?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    ABSActivity.this.finish();
	               }
	           })
	           .setNegativeButton("No", null)
	           .show();
		}
		else
		{
			this.finish();
		}
	}

	protected boolean isCallable(Intent intent) 
	{
		List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	
	/**
	 * Shows or hides the navigation toolbar
	 * @param visibility
	 */
	public void SetMenuVisibility(int visibility)
	{
		llMainMenu.setVisibility(visibility);
		llMainMenu.refreshDrawableState();
	}
	
	/**
	 * Initializes buttons on the main navigation toolbar
	 */
	protected void initButtons() 
	{

		try
		{
		llMainMenu = (LinearLayout)this.findViewById(R.id.llMainMenu);

		btnMainDashboard = (ToggledImageButton)this.findViewById(R.id.btnMainDashboard);
		btnMainDashboard.setOnClickListener(this);
		btnMainDashboard.onResource = R.drawable.dashboardon;
		btnMainDashboard.offResource = R.drawable.dashboardoff;
		btnMainDashboard.setChecked(false);

		btnMainTools = (ToggledImageButton)this.findViewById(R.id.btnMainTools);
		btnMainTools.setOnClickListener(this);
		btnMainTools.offResource = R.drawable.toolsoff;
		btnMainTools.onResource = R.drawable.toolson;
		btnMainTools.setChecked(false);
		
		btnMainCards = (ToggledImageButton)this.findViewById(R.id.btnMainCards);
		btnMainCards.setOnClickListener(this);
		btnMainCards.offResource = R.drawable.cardsoff;
		btnMainCards.onResource = R.drawable.cardson;
		btnMainCards.setChecked(false);
		
		btnMainAbout = (ToggledImageButton)this.findViewById(R.id.btnMainAbout);
		btnMainAbout.setOnClickListener(this);
		btnMainAbout.offResource = R.drawable.helpoff;
		btnMainAbout.onResource = R.drawable.helpon;
		btnMainAbout.setChecked(false);
		
		btnMainSettings = (ToggledImageButton)this.findViewById(R.id.btnMainSettings);
		btnMainSettings.setOnClickListener(this);
		btnMainSettings.offResource = R.drawable.settingsoff;
		btnMainSettings.onResource = R.drawable.settingson;
		btnMainSettings.setChecked(false);
		}
		catch(Exception ex){}
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) 
	{
		switch(v.getId()) 
		{
		case R.id.btnMainDashboard:
			this.startActivity(ActivityFactory.getHomeActivity(this));
			if((this instanceof HomeActivity == false))
				this.finish();
			break;
		case R.id.btnMainTools:
			this.startActivity(ActivityFactory.getToolsActivity(this));
			if((this instanceof ToolsActivity == false))
				this.finish();
			break;
		case R.id.btnMainCards:
			this.startActivity(ActivityFactory.getCardsActivity(this));
			if((this instanceof CardsActivity == false))
				this.finish();
			break;
		case R.id.btnMainAbout:
			this.startActivity(ActivityFactory.getAboutActivity(this));
			if((this instanceof AboutActivity == false))
				this.finish();
			break;
		case R.id.btnMainSettings:
			this.startActivity(ActivityFactory.getSettingsActivity(this));
			if((this instanceof SettingsActivity == false))
				this.finish();
			break;
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_STATISTICS:
			ProgressDialog dlg = new ProgressDialog(this);
			dlg.setIndeterminate(true);
			dlg.setTitle("Send Log");
			dlg.setMessage("Please wait, generating log file...");
			return dlg;
		}
		return null;
	}
	
	public void populateMenu(Menu menu) {

		menu.setQwertyMode(true);

		MenuItem item1 = menu.add(0, Menu1, 0, "Send Study Log");
		MenuItem item2 = menu.add(0, Menu2, 0, "Disenroll From Study");
	}

	public boolean applyMenuChoice(MenuItem item) {
		switch (item.getItemId()) {
		case Menu1:
			new AsyncTask<Void, Void, Uri>() {
				@Override
				protected Uri doInBackground(Void... params) {
					return generateStatisticsCsv();
				}

				@Override
				protected void onPostExecute(Uri result) {
					final Intent emailIntent = new Intent(Intent.ACTION_SEND);
					emailIntent.setType("message/rfc822");
					emailIntent.putExtra(Intent.EXTRA_EMAIL,
							new String[] {
							SharedPref.getStudyParticipantEmail()
					});
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Provider Resilience Log File");
					emailIntent.putExtra(Intent.EXTRA_TEXT,
							"Attached is a Provider Resilience log file.");
					emailIntent.putExtra(Intent.EXTRA_STREAM, result);
					startActivity(Intent.createChooser(emailIntent, "Send mail..."));
				}
			}.execute((Void) null);
			break;
		case Menu2:
			SharedPref.clearStudyParticipantData();
			finish();
			Toast.makeText(this, "Study enrollment removed.", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
			break;
		}
		return false;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(Menu1).setVisible(isEnrolled());
		menu.findItem(Menu2).setVisible(isEnrolled());
		return super.onPrepareOptionsMenu(menu);
	}

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		populateMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}

	/** when menu button option selected */
	@Override public boolean onOptionsItemSelected(MenuItem item) {
		return applyMenuChoice(item) || super.onOptionsItemSelected(item);
	}

}
