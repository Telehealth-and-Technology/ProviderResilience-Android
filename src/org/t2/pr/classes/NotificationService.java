package org.t2.pr.classes;

import java.util.Calendar;

import org.t2.pr.R;
import org.t2.pr.activities.SettingsActivity;
import org.t2.pr.activities.StartupActivity;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationService extends IntentService {

    @SuppressWarnings("unused")
    private static final String TAG = "NotificationService";

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        
    	final NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            final Notification note = new Notification(R.drawable.icon, "Provider Resilience", System.currentTimeMillis() + 100);

            final Intent homeIntent = new Intent(this, StartupActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            final PendingIntent contentIntent = PendingIntent.getActivity(this, 0, homeIntent, 0);
            note.flags |= Notification.FLAG_AUTO_CANCEL;
            note.setLatestEventInfo(this, "Provider Resilience", "Reminder to do your assessment for today!", contentIntent);
            nm.notify(1001, note);

    }
}