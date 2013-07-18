package org.t2.pr.activities;

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RRatingActivity extends ABSActivity
{

	public ImageView btnProQOL;
	public TextView tvProQOL;

	public ImageView btnRRClock;
	public TextView tvRRClock;

	public ImageView btnBuildersKillers;
	public TextView tvBuildersKillers;

	public ImageView btnBurnout;
	public TextView tvBurnout;

	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		onEvent("Open ResiliencyRating Activity");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.rrating);

		this.SetMenuVisibility(1);
		this.btnMainDashboard.setChecked(true);
		
		btnProQOL = (ImageView)this.findViewById(R.id.btn_proqol);
		btnProQOL.setOnClickListener(this);
		tvProQOL = (TextView)this.findViewById(R.id.txt_proqol);
		tvProQOL.setOnClickListener(this);

		btnRRClock = (ImageView)this.findViewById(R.id.btn_rrclock);
		btnRRClock.setOnClickListener(this);
		tvRRClock = (TextView)this.findViewById(R.id.txt_rrclock);
		tvRRClock.setOnClickListener(this);

		btnBuildersKillers = (ImageView)this.findViewById(R.id.btn_builderskillers);
		btnBuildersKillers.setOnClickListener(this);
		tvBuildersKillers = (TextView)this.findViewById(R.id.txt_builderskillers);
		tvBuildersKillers.setOnClickListener(this);

		btnBurnout = (ImageView)this.findViewById(R.id.btn_burnout);
		btnBurnout.setOnClickListener(this);
		tvBurnout = (TextView)this.findViewById(R.id.txt_burnout);
		tvBurnout.setOnClickListener(this);

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
		case R.id.btn_proqol:
			onEvent("ResiliencyRating Activity: PROQOL Clicked");

			this.startActivity(ActivityFactory.getProQOLActivity(this));
			break;
		case R.id.txt_proqol:
			onEvent("ResiliencyRating Activity: PROQOL Clicked");
			this.startActivity(ActivityFactory.getProQOLActivity(this));
			break;
		case R.id.btn_rrclock:
			onEvent("ResiliencyRating Activity: Clock Clicked");
			this.startActivity(ActivityFactory.getRRClockActivity(this));
			break;
		case R.id.txt_rrclock:
			onEvent("ResiliencyRating Activity: Clock Clicked");
			this.startActivity(ActivityFactory.getRRClockActivity(this));
			break;
		case R.id.btn_builderskillers:
			onEvent("ResiliencyRating Activity: BK Clicked");
			this.startActivity(ActivityFactory.getRBQuestionsActivity(this));
			break;
		case R.id.txt_builderskillers:
			onEvent("ResiliencyRating Activity: BK Clicked");
			this.startActivity(ActivityFactory.getRBQuestionsActivity(this));
			break;
		case R.id.txt_burnout:
			onEvent("ResiliencyRating Activity: Burnout Clicked");
			this.startActivity(ActivityFactory.getUpdateBurnoutActivity(this));
			break;
		case R.id.btn_burnout:
			onEvent("ResiliencyRating Activity: Burnout Clicked");
			this.startActivity(ActivityFactory.getUpdateBurnoutActivity(this));
			break;
		}
	}
	
}
