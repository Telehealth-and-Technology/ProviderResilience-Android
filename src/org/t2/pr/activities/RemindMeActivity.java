package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.DatabaseProvider;
import org.t2.pr.classes.Global;

import com.brightcove.mobile.mediaapi.ReadAPI;
import com.brightcove.mobile.mediaapi.model.ItemCollection;
import com.brightcove.mobile.mediaapi.model.Playlist;
import com.brightcove.mobile.mediaapi.model.Video;
import com.brightcove.mobile.mediaapi.model.enums.MediaDeliveryTypeEnum;
import com.brightcove.mobile.mediaapi.model.enums.SortByTypeEnum;
import com.brightcove.mobile.mediaapi.model.enums.SortOrderTypeEnum;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class RemindMeActivity extends ABSActivity implements OnClickListener
{

	private ImageView btnDepression;
	private ImageView btnAlcohol;
	private ImageView btnAnger;
	private ImageView btnSeekDepression;
	private ImageView btnStigma;
	private DatabaseProvider db = new DatabaseProvider(this);
	private ReadAPI readAPI;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		onEvent("Opened RemindMe Activity");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.remindme);

		this.SetMenuVisibility(1);
		this.btnMainTools.setChecked(true);

		btnDepression = (ImageView)this.findViewById(R.id.iv_depression);
		btnDepression.setOnClickListener(this);

		btnAlcohol = (ImageView)this.findViewById(R.id.iv_alcohol);
		btnAlcohol.setOnClickListener(this);

		btnAnger = (ImageView)this.findViewById(R.id.iv_anger);
		btnAnger.setOnClickListener(this);

		btnSeekDepression = (ImageView)this.findViewById(R.id.iv_seekdepression);
		btnSeekDepression.setOnClickListener(this);

		btnStigma = (ImageView)this.findViewById(R.id.iv_stigma);
		btnStigma.setOnClickListener(this);

		readAPI = new ReadAPI(Global.ANOTHER_BRIGHTCOVE_READ_TOKEN);
		readAPI.setMediaDeliveryType(MediaDeliveryTypeEnum.HTTP);
	}

	@Override
	public void onStart()
	{

		super.onStart();
	}

	@Override
	public void onClick(View v) 
	{
		super.onClick(v);

		switch(v.getId()) 
		{
		case R.id.iv_depression: //1125410612001
			onEvent("RemindMe Activity: Depression Clicked");

			try{
				String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
				db.insertMisc("remindme", 1, answerDate);
				//Read the video url from brightcove and load with standard media player (sorry, brightcove's player is junk.)
				Video video = readAPI.findVideoById(Long.parseLong("1125410612001"), null, null);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(video.getFlvUrl()), "video/*");
				startActivity(intent);
			}
			catch(Exception ex){ErrorMsg();}

			break;
		case R.id.iv_alcohol:
			onEvent("RemindMe Activity: Alcohol Clicked");

			try{
				String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
				db.insertMisc("remindme", 1, answerDate);
				//Read the video url from brightcove and load with standard media player (sorry, brightcove's player is junk.)
				Video video = readAPI.findVideoById(Long.parseLong("1124090491001"), null, null);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(video.getFlvUrl()), "video/*");
				startActivity(intent);
			}
			catch(Exception ex){ErrorMsg();}

			break;

		case R.id.iv_anger:
			onEvent("RemindMe Activity: Anger Clicked");

			try{
				String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
				db.insertMisc("remindme", 1, answerDate);
				//Read the video url from brightcove and load with standard media player (sorry, brightcove's player is junk.)
				Video video = readAPI.findVideoById(Long.parseLong("1125552100001"), null, null);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(video.getFlvUrl()), "video/*");
				startActivity(intent);
			}
			catch(Exception ex){ErrorMsg();}

			break;

		case R.id.iv_seekdepression:
			onEvent("RemindMe Activity: SeekDepression Clicked");

			try{
				String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
				db.insertMisc("remindme", 1, answerDate);
				//Read the video url from brightcove and load with standard media player (sorry, brightcove's player is junk.)
				Video video = readAPI.findVideoById(Long.parseLong("1125380434001"), null, null);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(video.getFlvUrl()), "video/*");
				startActivity(intent);
			}
			catch(Exception ex){ErrorMsg();}

			break;

		case R.id.iv_stigma:
			onEvent("RemindMe Activity: Stigma Clicked");

			try{
				String answerDate = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
				db.insertMisc("remindme", 1, answerDate);
				//Read the video url from brightcove and load with standard media player (sorry, brightcove's player is junk.)
				Video video = readAPI.findVideoById(Long.parseLong("1125586040001"), null, null);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(video.getFlvUrl()), "video/*");
				startActivity(intent);
			}
			catch(Exception ex){ErrorMsg();}

			break;
			//
		}
	}
	
	public void ErrorMsg()
	{
		onEvent("RemindMe Activity: Network Issue");

		
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle("Error");
		myAlertDialog.setMessage("Please check your network connectivity.");
		myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				
			}});
		
		myAlertDialog.show();
	}

}
