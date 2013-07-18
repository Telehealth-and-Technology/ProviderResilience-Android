package org.t2.pr.activities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.t2.pr.R;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.SharedPref;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;

/**
 * Flurry class provides analytics and should be the base class for all activities. Also handles Study Enrollment logging
 * 
 * @author Steve Ody (stephen.ody@tee2.org)
 */
public class FlurryActivity extends Activity {

	private DatabaseProvider db = new DatabaseProvider(this);
	private static final Map<String, Long> sStartMap;
	private static final Map<String, Long> sDurationMap;

	static {
		sStartMap = new HashMap<String, Long>();
		sDurationMap = new HashMap<String, Long>();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Global.sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onStartSession(this, Global.FLURRY_KEY);
		}

		String key = this.getClass().getSimpleName();
		if (sStartMap.containsKey(key)) {
			return;
		}

		sStartMap.put(key, System.currentTimeMillis());
		Log.d("StatisticsEvent",
				"Timer " + (sDurationMap.containsKey(key) ? "Resumed: " :
						"Registered: ") + key + " - "
						+ new Date(System.currentTimeMillis()).toString());
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onEndSession(this);
		}

		String key = this.getClass().getSimpleName();

		if (this instanceof Activity && ((Activity) this).isFinishing()) {
			Long start = sStartMap.remove(key);
			Long duration = sDurationMap.remove(key);

			if (start == null && duration == null) {
				return;
			}

			long finalDuration = 0;
			if (duration != null) {
				finalDuration += duration;
			}

			if (start != null) {
				finalDuration += System.currentTimeMillis() - start;
			}

			if (finalDuration > 3000) {
				logData(this, finalDuration, "Session", null);
				Log.d("StatisticsEvent", "Timer Stopped: " + key + " - " + new
						Date(System.currentTimeMillis()).toString()
						+ " | Duration: "
						+ finalDuration);
			}

			return;
		}

		long duration = 0;
		if (sDurationMap.containsKey(key)) {
			duration = sDurationMap.get(key);
		}

		if (sStartMap.containsKey(key)) {
			duration += (System.currentTimeMillis() - sStartMap.remove(key));
			sDurationMap.put(key, duration);
			Log.d("StatisticsEvent", "Timer Paused: " + key + " - " + new
					Date(System.currentTimeMillis()).toString() + " | Duration: "
					+ duration);
		}
	}

	public void onEvent(Context context, String event, String key,
			String value) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(key, value);
		onEvent(context, event, params);
	}

	public void onEvent(Context context, String event, Bundle parameters) {
		HashMap<String, String> params = new HashMap<String, String>();
		for (String key : parameters.keySet()) {
			Object val = parameters.get(key);
			params.put(key, val + "");
		}

		onEvent(context, event, params);
	}

	public void onEvent(Context context, String event) {

		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onEvent(event);
		}

		if (event.equals(context.getClass().getSimpleName()) ||
				event.equals(context.getClass().getName())) {
			return;
		}

		logData(context, null, event, null);
	}

	public void onEvent(Context context, String event, Map<String,
			String> parameters) {

		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onEvent(event, parameters);
		}

		logData(context, null, event, parameters);
	}

	public void onEvent(String event) 
	{
		if(SharedPref.getSendAnnonData())
		{
			Global.Log.v("", "onEvent:"+event);

			FlurryAgent.logEvent(event);
		}

		if (event.equals(this.getClass().getSimpleName()) ||
				event.equals(this.getClass().getName())) {
			return;
		}

		logData(this, null, event, null);
	}

	public void onError(String arg0, String arg1, String arg2)
	{
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onError(arg0, arg1, arg2);
		}
	}

	public void onPageView() 
	{
		if(SharedPref.getSendAnnonData())
		{
			FlurryAgent.onPageView();
		}
	}

	public boolean isEnrolled() {
		return !TextUtils.isEmpty(SharedPref.getStudyParticipantNumber());
	}

	public void logData(Context context, Long duration, String
			event, Map<String, String> parameters) {
		if (context instanceof SplashActivity || !isEnrolled()) {
			return;
		}

		CharSequence title = ((Activity) context).getTitle();
		String data = title != null ? title.toString() : "";
		long timeDuration = 0;

		if (duration != null) {
			timeDuration = duration;
		}

		data = data  + "," + event;

		if (parameters != null) {
			for (Entry<String, String> param : parameters.entrySet()) {
				data =  data + "," + param.getKey() + ": " + param.getValue();
			}
		}

		db.insertLogEntry(System.currentTimeMillis(), timeDuration, context.getClass().getSimpleName(), data);

	}

	public Uri generateStatisticsCsv() {

		List<Object[]> entries = db.selectLogEntries();
		File csvFile = new File(getCacheDirectory(), "stats.csv");
		int count = 1;
		try {
			csvFile.createNewFile();
			FileWriter fw = new FileWriter(csvFile);
			fw.write("Event Time, Class Name, Duration, Title, Additional Fields...\n");
			for (Object[] entry : entries) {
				writeRow(entry, fw);
				count++;

			}
			fw.close();
		} catch (IOException e) {
			Log.e("StatisticsEvent", "Unable to write statistics CSV", e);
		}
		System.out.println("Count" + count);

		return Uri.fromFile(csvFile);
	}

	private static void writeRow(Object[] entry, FileWriter fw) throws
	IOException {
		StringBuilder line = new StringBuilder();

		line.append(new Date((Long)entry[1]).toString());
		try {
			line.append("," + (String)entry[3]);
			String data =(String)entry[4];
			Map<String, String> paramMap = generateMap((String)entry[3], data);
			String duration = formatTime((Long)entry[2]);
			if (paramMap == null && duration == null) {
				return;
			}

			line.append(",");
			if (duration != null) {
				line.append(duration);
			}

			for (Entry<String, String> param : paramMap.entrySet()) {
				line.append(",").append(param.getValue());
			}
			line.append("\n");

			fw.write(line.toString());
		} catch (IllegalArgumentException e) {
			Log.e("StatisticsEvent", "Unable to write log row", e);
		}

	}

	private static String formatTime(long duration) {
        if (duration < 3000) {
            return null;
        }
        return DateUtils.formatElapsedTime(duration / 1000);
    }
	
	public static final File getCacheDirectory() {
		File dir = new
				File(Environment.getExternalStorageDirectory().getAbsolutePath() +
						"/Android/data/com.t2.pr/");

		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	
	private static Map<String, String> generateMap(String type, String data) {
        Map<String, String> map = new HashMap<String, String>();
        if (data != null && data.trim().length() > 0) {
            map.put("Data", data);
        }
        return map;
    }

}
