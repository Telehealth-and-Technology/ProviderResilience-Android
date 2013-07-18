package org.t2.pr.activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.t2.pr.R;
import org.t2.pr.activities.StartupActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.JsonReader;
import android.widget.Toast;

public class StudyEnrollmentActivity extends Activity {

    private static final String ENROLL_ACTION = "enroll";
    private static final String KEY_PARTICIPANT_ID = "participantId";
    private static final String KEY_RECIPIENT_EMAIL = "recipientEmail";
    private Toast mInvalidToast, mEnrolledToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInvalidToast = Toast.makeText(this, "The attached enrollment file is invalid or corrupt.", Toast.LENGTH_LONG);
        mEnrolledToast = Toast.makeText(this, "Study enrollment successful!", Toast.LENGTH_LONG);

        Uri uri = getIntent().getData();
        String unencodedData = null;

        if (uri.getScheme().equalsIgnoreCase("file") || uri.getScheme().equalsIgnoreCase("content")) {
            unencodedData = decodeFile(uri);
        }

        if (unencodedData != null) {
            try {
                JSONObject root = new JSONObject(unencodedData);
                String action = root.getString("action");

                if (action.equalsIgnoreCase(ENROLL_ACTION) && enroll(root)) {
                    mEnrolledToast.show();
                }
            } catch (JSONException e) {
                mInvalidToast.show();
            }
        } else {
            mInvalidToast.show();
        }

        Intent intent = new Intent(this, StartupActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean enroll(JSONObject root) throws JSONException {

    	String participantNumber = root.getString(KEY_PARTICIPANT_ID);
        String recipientEmail = root.getString(KEY_RECIPIENT_EMAIL);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
 
        preferences.edit()
                .putString(getString(R.string.prf_study_participant_number), participantNumber)
                .putString(getString(R.string.prf_study_recipient_email), recipientEmail)
                .commit();

        return true;
    }

    private String decodeFile(Uri uri) {

    	String encodedData = null;

        if (uri.getScheme().equalsIgnoreCase("file")) {

        	File file = new File(uri.toString().replace("file:///mnt/", ""));
            if (!file.exists()) {
                finish();
                return null;
            }

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String line, results = "";
 
                while ((line = reader.readLine()) != null) {
                    results += line;
                }
                reader.close();

                encodedData = results;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
 
        } else if (uri.getScheme().equalsIgnoreCase("content")) {

            try {
                InputStream file = getContentResolver().openInputStream(uri);
                BufferedReader reader = null;
 
                try {
                    reader = new BufferedReader(new InputStreamReader(file));
                    String line, results = "";

                    while ((line = reader.readLine()) != null) {
                        results += line;
                    }

                    reader.close();
                    encodedData = results;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        byte[] decodeBytes = Base64.decode(encodedData, Base64.DEFAULT);
        String unencodedData = new String(decodeBytes);

        return unencodedData;
    }

}