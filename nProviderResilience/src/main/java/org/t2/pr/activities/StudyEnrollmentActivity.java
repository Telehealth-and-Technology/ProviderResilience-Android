/*
* StudyEnrollmentActivity.java
* Get detected enrollment data, decodes it and then registers that information as its research enrollment data
*
* Provider Resilience
*
* Copyright  2009-2014 United States Government as represented by
* the Chief Information Officer of the National Center for Telehealth
* and Technology. All Rights Reserved.
*
* Copyright  2009-2014 Contributors. All Rights Reserved.
*
* THIS OPEN SOURCE AGREEMENT ("AGREEMENT") DEFINES THE RIGHTS OF USE,
* REPRODUCTION, DISTRIBUTION, MODIFICATION AND REDISTRIBUTION OF CERTAIN
* COMPUTER SOFTWARE ORIGINALLY RELEASED BY THE UNITED STATES GOVERNMENT
* AS REPRESENTED BY THE GOVERNMENT AGENCY LISTED BELOW ("GOVERNMENT AGENCY").
* THE UNITED STATES GOVERNMENT, AS REPRESENTED BY GOVERNMENT AGENCY, IS AN
* INTENDED THIRD-PARTY BENEFICIARY OF ALL SUBSEQUENT DISTRIBUTIONS OR
* REDISTRIBUTIONS OF THE SUBJECT SOFTWARE. ANYONE WHO USES, REPRODUCES,
* DISTRIBUTES, MODIFIES OR REDISTRIBUTES THE SUBJECT SOFTWARE, AS DEFINED
* HEREIN, OR ANY PART THEREOF, IS, BY THAT ACTION, ACCEPTING IN FULL THE
* RESPONSIBILITIES AND OBLIGATIONS CONTAINED IN THIS AGREEMENT.
*
* Government Agency: The National Center for Telehealth and Technology
* Government Agency Original Software Designation: T2MoodTracker001
* Government Agency Original Software Title: T2MoodTracker
* User Registration Requested. Please send email
* with your contact information to: robert.a.kayl.civ@mail.mil
* Government Agency Point of Contact for Original Software: robert.a.kayl.civ@mail.mil
*
*/
package org.t2.pr.activities;

import java.util.UUID;
import net.sqlcipher.database.SQLiteDatabase;
import org.json.JSONException;
import org.json.JSONObject;
import org.t2.pr.R;
import org.t2.pr.classes.DatabaseHelper;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.PreferenceHelper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.t2.fcads.FipsWrapper;

public class StudyEnrollmentActivity extends Activity {

    private static final String ENROLL_ACTION = "enroll";
    private static final String KEY_PARTICIPANT_ID = "participantId";
    private static final String KEY_RECIPIENT_EMAIL = "recipientEmail";
    private final String encodeKey = "T2!SEf1l3*";
    private FipsWrapper fipsWrapper;
    private String sEncodedData = null;
    private Button doneButton;
    private Button emailButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Global.databaseHelper == null)
        	Global.databaseHelper = new DatabaseHelper(this);
        
        this.setContentView(R.layout.enrollment_activity);
        
		//Load FIPS libraries
		SQLiteDatabase.loadLibs(this);
				
		FipsWrapper.setContext(this);
		fipsWrapper = FipsWrapper.getInstance(this);
		fipsWrapper.doFIPSmode();
		fipsWrapper.doPrepare(false);	
		fipsWrapper.doDeInitializeLogin();
		fipsWrapper.doInitializeLogin(encodeKey, encodeKey);
		
		Intent incomingIntent = getIntent();
		
		if (Intent.ACTION_VIEW.equals(incomingIntent.getAction())) {
		  Uri uri = incomingIntent.getData();
		  sEncodedData = uri.getQueryParameter("reg");
		}
		
        emailButton = (Button) this.findViewById(R.id.emailButton);
        emailButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				emailClick();
			}
		});
        doneButton = (Button) this.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				doneClick();
			}
		});
    }
    private void doneClick()
    {
		Intent intent = new Intent(this, StartupActivity.class);
		startActivity(intent);
		finish();
    }
    private void emailClick()
    {
		String sEnrollData = fipsWrapper.doDecryptRaw(encodeKey, sEncodedData);
		
		try {
			
			JSONObject root = new JSONObject(sEnrollData);
			String action = root.getString("action");
			String participantNumber = root.getString(KEY_PARTICIPANT_ID);
	        String recipientEmail = root.getString(KEY_RECIPIENT_EMAIL);

			if (action.equalsIgnoreCase(ENROLL_ACTION)) {
		        PreferenceHelper.setStringForKey(getString(R.string.prf_study_participant_number), participantNumber);
		        PreferenceHelper.setStringForKey(getString(R.string.prf_study_recipient_email), recipientEmail);
		        
		        String encryptedPass = "";
		        UUID Uuid = new UUID(participantNumber.hashCode(), System.currentTimeMillis());
		        encryptedPass = fipsWrapper.doEncryptRaw(encodeKey, Uuid.toString());
		        
				PreferenceHelper.setStringForKey("EnrollmentPassword", Uuid.toString().trim());
				
				Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.fromParts("mailto", "", null));
	    		emailIntent.setType("message/rfc822");
	    		emailIntent.putExtra(Intent.EXTRA_EMAIL,
        				new String[] { recipientEmail });
	    		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Provider Resilience Study Enrollment");
	    		emailIntent.putExtra(Intent.EXTRA_TEXT,    "Password record for:\r\nParticipant#:" + participantNumber + "\r\n\r\n http://key.enrollment.local?key=" + encryptedPass.trim());
	    		this.startActivity(Intent.createChooser(emailIntent, "Send Enrollment Email"));
	    		doneButton.setEnabled(true);
	    		emailButton.setEnabled(false);
			}
		} catch (JSONException e) {
			Toast.makeText(this, "Study enrollment failed", Toast.LENGTH_LONG).show();
			finish();
		}
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(this, StartupActivity.class);
		startActivity(intent);
        
    }
}