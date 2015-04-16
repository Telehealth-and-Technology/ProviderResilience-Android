package org.t2.pr.activities;

import java.util.ArrayList;
import java.util.List;

import org.t2.pr.R;
import org.t2.pr.classes.Global;
import org.t2.pr.classes.Slider;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class UpdateBurnoutActivity extends ABSActivity implements Slider.OnSeekBarChangeListener
{
	private List<String[]> questionList;
	private ArrayList<int[]> answers;

	public Button btnKontinue;
	private ArrayList<Integer> reverseScore;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		onEvent("Opened UpdateBurnout Activity");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.updateburnout);

		this.SetMenuVisibility(1);
		this.btnMainAbout.setChecked(true);

		btnKontinue = (Button)this.findViewById(R.id.btn_ubcontinue);
		btnKontinue.setOnClickListener(this);

		populateBurnoutQuestions();
		inflateSliderView();

		reverseScore = new ArrayList<Integer>();
		reverseScore.add(2);
		reverseScore.add(4);
		reverseScore.add(6);
		reverseScore.add(8);
		reverseScore.add(10);
	}

	private void populateBurnoutQuestions()
	{
		questionList = Global.databaseHelper.selectBurnoutQuestions();
		answers = new ArrayList<int[]>();

		for(int i= 0; i< questionList.size(); i++)
		{
			int[] tmp = {Integer.parseInt(questionList.get(i)[0]), 50};
			answers.add(tmp);
		}
	}

	public void inflateSliderView()
	{
		for(int i= 0; i< questionList.size(); i++)
		{
			LinearLayout item = (LinearLayout)findViewById(R.id.ll_sliders);
			View child = getLayoutInflater().inflate(R.layout.slidercontrol, null);
			item.addView(child);

			TextView title = (TextView) child.findViewById(R.id.tv_mediumvalue);
			title.setText(questionList.get(i)[1]);

			Slider seeker = (Slider) child.findViewById(R.id.seekbar);
			seeker.setTag(i);
			seeker.setOnSeekBarChangeListener(this);
			seeker.setProgress(50);
			onProgressChanged(seeker, 50, true);
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

		onEvent("UpdateBurnout Activity: Changed Slider Value");

		int answerValue = 0;
		int position = 0;
		try{position = Integer.parseInt(seekBar.getTag().toString().trim());}catch(Exception ex){}
		//Global.Log.v("position", "" + position);
		View parent = (View) seekBar.getParent();

		TextView low = (TextView) parent.findViewById(R.id.tv_lowvalue);
		TextView med = (TextView) parent.findViewById(R.id.tv_mediumvalue);
		TextView high = (TextView) parent.findViewById(R.id.tv_highvalue);

		if(progress <= 33)
		{
			low.setTextColor(Color.GREEN);
			med.setTextColor(Color.WHITE);
			high.setTextColor(Color.WHITE);
			//answerValue = 0;
		}
		else if((progress > 33) && (progress <= 66))
		{
			low.setTextColor(Color.WHITE);
			med.setTextColor(Color.GREEN);
			high.setTextColor(Color.WHITE);
			//answerValue = 1;
		}
		else if(progress > 66)
		{
			low.setTextColor(Color.WHITE);
			med.setTextColor(Color.WHITE);
			high.setTextColor(Color.GREEN);
			//answerValue = 2;
		}

		try
		{
			//Reverse answers 2,4,6,8,10
			if(reverseScore.contains(Integer.parseInt(questionList.get(position)[0])) )
				answerValue = (10 - (progress / 10));
			else
				answerValue = progress / 10;
			
			answers.get(position)[0] = Integer.parseInt(questionList.get(position)[0]);
			answers.get(position)[1] = answerValue;
		}
		catch(Exception ex){}

	}

	@Override
	public void onStart()
	{

		super.onStart();
	}

	public void SaveQuestions()
	{
		onEvent("UpdateBurnout Activity: Saved Answers");

		String date = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
		Global.databaseHelper.insertBurnoutAnswers(answers, date);
	}

	@Override
	public void onClick(View v) 
	{
		super.onClick(v);

		switch(v.getId()) 
		{
		case R.id.btn_ubcontinue:
			SaveQuestions();
			//this.startActivity(ActivityFactory.getRRatingActivity(this));
			this.finish();
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

}
