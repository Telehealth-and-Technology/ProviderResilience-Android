package org.t2.pr.activities;

import java.util.ArrayList;
import java.util.List;

import org.t2.pr.R;
import org.t2.pr.classes.ActivityFactory;
import org.t2.pr.classes.Global;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class RBQuestionsActivity  extends ABSActivity
{

	public Button btnCustomize;
	public Button btnKillers;
	public static String customBuilder = "";
	private List<String[]> questionList;
	private ArrayList<int[]> answers;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		onEvent("Opened BuildersQuestions Activity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rbquestions);

		this.SetMenuVisibility(1);
		this.btnMainDashboard.setChecked(true);

		btnCustomize = (Button)this.findViewById(R.id.btn_custom);
		btnCustomize.setOnClickListener(this);

		btnKillers = (Button)this.findViewById(R.id.btn_killers);
		btnKillers.setOnClickListener(this);

		PopulateRBQuestions();//

	}

	private void PopulateRBQuestions()
	{
		questionList = Global.databaseHelper.selectRBQuestions();
		answers = new ArrayList<int[]>();

		if (questionList.size() > 0) 
		{

			int len = questionList.size();

			for(int l = 0; l < len; l++)
			{
				int cid = getResources().getIdentifier("chk" + (l + 1), "id", this.getPackageName());
				int tid = getResources().getIdentifier("txt" + (l + 1), "id", this.getPackageName());
				CheckBox cb = (CheckBox) this.findViewById(cid);
				cb.setTag(l);
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
					@Override 
					public void onCheckedChanged(CompoundButton buttonView, 
							boolean isChecked) { 

						int position = (Integer) buttonView.getTag();
						if(isChecked)
							answers.get(position)[1] = 1;
						else
							answers.get(position)[1] = 0;

					} 
				}); 

				TextView txt = (TextView) this.findViewById(tid);

				txt.setText(questionList.get(l)[1]);

				if((questionList.get(l)[1].trim() != null) && (!questionList.get(l)[1].trim().equals("")))
				{
					txt.setVisibility(View.VISIBLE);
					cb.setVisibility(View.VISIBLE);
				}
				else
				{
					txt.setVisibility(View.GONE);
					cb.setVisibility(View.GONE);
				}

				int[] tmp = {Integer.parseInt(questionList.get(l)[0]), 0};
				answers.add(tmp);
			}

		}
	}

	public void SaveQuestions()
	{
		onEvent("BuildersQuestions Activity: Saved Answers");

		String date = (String) android.text.format.DateFormat.format("MM/dd/yyyy hh:mm aa", new java.util.Date());
		Global.databaseHelper.insertRBAnswers(answers, date);
	}

	public void CustomQuestion()
	{
		onEvent("BuildersQuestions Activity: Custom Question");

		//Ask for question
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("New Resilience Builder");
		final EditText input = new EditText(this);
		
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				customBuilder = input.getText().toString().trim();
				
				//Save to database
				Global.databaseHelper.insertRBQuestion(customBuilder);
					reloadActivity();
				
			}
		});

		alert.show();
	}

	public void reloadActivity()
	{
		this.finish();
		this.startActivity(ActivityFactory.getRBQuestionsActivity(this));
		
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
		case R.id.btn_killers:
			SaveQuestions();
			this.startActivity(ActivityFactory.getRKQuestionsActivity(this));
			this.finish();
			break;
		case R.id.btn_custom:
			CustomQuestion();
			break;
		}
	}


}
