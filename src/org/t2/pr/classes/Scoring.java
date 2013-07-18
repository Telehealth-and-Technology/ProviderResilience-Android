package org.t2.pr.classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import android.util.Log;

public class Scoring {

	private static DatabaseProvider db = new DatabaseProvider(Global.appContext);

	public static int LeaveClockScore()
	{
		int cYear;
		int cMonth;
		int cDay;

		int vYear;
		int vMonth;
		int vDay;

		// get the current date
		Calendar c = Calendar.getInstance();
		cYear = c.get(Calendar.YEAR);
		cMonth = c.get(Calendar.MONTH);
		cDay = c.get(Calendar.DAY_OF_MONTH);

		if(SharedPref.getVacationYear() == 0)
		{
			// get the current date
			vYear = cYear;
			vMonth = cMonth;
			vDay = cDay;
		}
		else
		{
			// get the saved date
			vYear = SharedPref.getVacationYear();
			vMonth = SharedPref.getVacationMonth();
			vDay = SharedPref.getVacationDay();
		}

		DateTime vd = new DateTime(vYear, vMonth + 1, vDay, 0, 0);
		DateTime cd = new DateTime(cYear, cMonth + 1, cDay, 0, 0);
		Period p = new Period(vd, cd, PeriodType.days());

		int leavedays = p.getDays();
		if(leavedays <= 60)
		{
			//Global.Log.v("LeaveScore", "20");
			return 20;
		}
		else if(leavedays <= 90)
		{
			//Global.Log.v("LeaveScore", "10");
			return 15;
		}
		else if(leavedays <= 120)
		{
			//Global.Log.v("LeaveScore", "10");
			return 10;
		}
		else if(leavedays <= 150)
		{
			//Global.Log.v("LeaveScore", "10");
			return 5;
		}
		else
		{
			//Global.Log.v("LeaveScore", "0");
			return 0;
		}

	}

	public static int QOLCompassionScore(String date)
	{
		List<String[]> answerList = db.selectQOLAnswers(date);
		int len = answerList.size();

		int[] compassionQuestions = {3,6,12,16,18,20,22,24,27,30};

		int totalScore = 0;

		for(int l = 0; l < len; l++)
		{
			for(int i = 0; i < compassionQuestions.length; i++)
			{
				int innerQnum = compassionQuestions[i];
				int outerQnum = Integer.parseInt(answerList.get(l)[0]);
				if(outerQnum == innerQnum)
				{
					try
					{
						totalScore += Integer.parseInt(answerList.get(l)[1]);
					}
					catch(Exception ex){}
					break;
				}
			}
		}

		if(totalScore<0)totalScore=0;
		//Global.Log.v("QOLCompassionScore", "" + totalScore);
		return totalScore;
	}

	public static int QOLBurnoutScore(String date)
	{
		List<String[]> answerList = db.selectQOLAnswers(date);
		int len = answerList.size();

		int[] burnoutQuestions = {1,4,8,10,15,17,19,21,26,29};
		ArrayList<Integer> reverseScore = new ArrayList<Integer>();
		reverseScore.add(1);
		reverseScore.add(4);
		reverseScore.add(15);
		reverseScore.add(17);
		reverseScore.add(29);
		
		int totalScore = 0;

		for(int l = 0; l < len; l++)
		{
			for(int i = 0; i < burnoutQuestions.length; i++)
			{
				int innerQnum = burnoutQuestions[i];
				int outerQnum = Integer.parseInt(answerList.get(l)[0]);
				if(outerQnum == innerQnum)
				{
					try
					{
						//Minus from the score for reverse value questions
						if(reverseScore.contains(outerQnum))
						{
							totalScore += (6 - Integer.parseInt(answerList.get(l)[1])); //TODO: check me
						}
						else
						{
							totalScore += Integer.parseInt(answerList.get(l)[1]);
						}
					}
					catch(Exception ex){}
					break;
				}
			}
		}

		if(totalScore<0)totalScore=0;
		
		//Global.Log.v("QOLBurnoutScore", "" + totalScore);
		return totalScore;
	}

	public static int QOLSTSScore(String date)
	{
		List<String[]> answerList = db.selectQOLAnswers(date);
		int len = answerList.size();

		int[] stsQuestions = {2,5,7,9,11,13,14,23,25,28};

		int totalScore = 0;

		for(int l = 0; l < len; l++)
		{
			for(int i = 0; i < stsQuestions.length; i++)
			{
				int innerQnum = stsQuestions[i];
				int outerQnum = Integer.parseInt(answerList.get(l)[0]);
				if(outerQnum == innerQnum)
				{
					try
					{
						totalScore += Integer.parseInt(answerList.get(l)[1]);
					}
					catch(Exception ex){}
					break;
				}
			}
		}

		if(totalScore<0)totalScore=0;
		//Global.Log.v("QOLSTSScore", "" + totalScore);
		return totalScore;
	}

	public static String AcuityString(int value)
	{
		String acuity = "";
		if(value >= 42)
			acuity = "HIGH";
		else if(value >= 23)
			acuity = "MED";
		else 
			acuity = "LOW";

		return acuity;
	}

	public static double PROQOLScore(String date)
	{
		//If there have been no answers return 0
		List<String[]> answerList = db.selectQOLAnswers(date);
		if(answerList.size() == 0)
			return 0;
		
		double result = 0.0;
		String cs = AcuityString(QOLCompassionScore(date));
		String bs = AcuityString(QOLBurnoutScore(date));
		String ss = AcuityString(QOLSTSScore(date));

		if(cs == "MED") result += 3;
		if(cs == "HIGH") result += 7;

		if(bs == "LOW") result += 7;
		if(bs == "MED") result += 3;

		if(ss == "LOW") result += 6;
		if(ss == "MED") result += 2;

		//Global.Log.v("TOTALPROQOLScore:", "" + result);
		return result;
		
	}

	public static int BurnoutScore(String date)
	{
		int dbvalues = db.selectBurnoutScore(date);

		
		int totalScore = 0;
		if(dbvalues >=85)
			totalScore = 15;
		else if(dbvalues >= 70)
			totalScore = 10;
		else if(dbvalues >= 50)
			totalScore = 5;

		return totalScore;
	}
	
	public static int RawBurnoutScore(String date)
	{
		return db.selectBurnoutScore(date);
	}

	public static int BuildersKillersScore(String date)
	{
		int totalScore = db.selectBuildersKillersScore(date);

		//Global.Log.v("BuildersScore", "" + totalScore);
		return totalScore;
	}

	public static int MiscScore(String date)
	{
		ArrayList<Integer> laugh = (ArrayList<Integer>) db.selectMisc("laugh", date);
		ArrayList<Integer> video = (ArrayList<Integer>) db.selectMisc("video", date);
		ArrayList<Integer> remindme = (ArrayList<Integer>) db.selectMisc("remindme", date);

		/*int miscScore = 0;
		if(laugh.size() > 0) miscScore += 3;
		if(video.size() > 0) miscScore += 3;
		if(remindme.size() > 0) miscScore += 3;
		if(miscScore > 0) miscScore += 1;
		//Global.Log.v("miscScore", "" + miscScore);
		return miscScore;*/
		
		int miscScore = 0;
		if(laugh.size() > 0) miscScore = 10;
		if(video.size() > 0) miscScore = 10;
		if(remindme.size() > 0) miscScore = 10;
		
		return miscScore;
	}
	
	public static String getLastQOLDate()
	{
		String lastDate = "";
		ArrayList<String> qoldates = (ArrayList<String>) db.selectQOLDatesDesc();
		if(qoldates.size()>0)
			lastDate = qoldates.get(0);
		
		return lastDate;
	}
	
	public static String getLastBurnoutDate()
	{
		String lastDate = "";
		ArrayList<String> qoldates = (ArrayList<String>) db.selectBURNOUTDatesDesc();
		if(qoldates.size()>0)
			lastDate = qoldates.get(0);
		
		return lastDate;
	}
	
	public static int getBurnoutRemainder()
	{
		int cYear;
		int cMonth;
		int cDay;

		int c2Year;
		int c2Month;
		int c2Day;

		// get the current date
		Calendar c = Calendar.getInstance();
		cYear = c.get(Calendar.YEAR);
		cMonth = c.get(Calendar.MONTH);
		cDay = c.get(Calendar.DAY_OF_MONTH);

		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(Date.parse(getLastBurnoutDate()));
		c2Year = c2.get(Calendar.YEAR);
		c2Month = c2.get(Calendar.MONTH);
		c2Day = c2.get(Calendar.DAY_OF_MONTH);

		DateTime vd = new DateTime(c2Year, c2Month + 1, c2Day, 0, 0);
		DateTime cd = new DateTime(cYear, cMonth + 1, cDay, 0, 0);
		Period p = new Period(vd, cd, PeriodType.days());

		int days = 7 - p.getDays();
		
		if(days < 0)
			days = 0;
		
		return days;
	}
	
	public static int getQOLRemainder()
	{
		int cYear;
		int cMonth;
		int cDay;

		int c2Year;
		int c2Month;
		int c2Day;

		// get the current date
		Calendar c = Calendar.getInstance();
		cYear = c.get(Calendar.YEAR);
		cMonth = c.get(Calendar.MONTH);
		cDay = c.get(Calendar.DAY_OF_MONTH);

		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(Date.parse(getLastQOLDate()));
		c2Year = c2.get(Calendar.YEAR);
		c2Month = c2.get(Calendar.MONTH);
		c2Day = c2.get(Calendar.DAY_OF_MONTH);

		DateTime vd = new DateTime(c2Year, c2Month + 1, c2Day, 0, 0);
		DateTime cd = new DateTime(cYear, cMonth + 1, cDay, 0, 0);
		Period p = new Period(vd, cd, PeriodType.days());

		int days = 30 - p.getDays();
		
		if(days < 0)
			days = 0;
		
		return days;
	}
	
	public static int TotalResilienceScore(String date)
	{
		
		double proQOLPercentage = 45;
		double burnoutPercentage = 15;
		double builderskillersPercentage = 10;
		double clockPercentage = 20;
		
		double maxPROQOLPoints = 20;
		double maxBurnoutPoints = 15;
		double maxBuildersKillersPoints = 10; 
		double maxLeavePoints = 20;
		
		//ProQOL
		String lastQOL = getLastQOLDate();
		double proqolPercent = (proQOLPercentage * (((PROQOLScore(lastQOL) / maxPROQOLPoints) * 100) * .01));
		if(PROQOLScore(lastQOL) <= 0f)
			proqolPercent = 0;
		Global.Log.v("proqolScore", "" + PROQOLScore(lastQOL));
		Global.Log.v("proqolPercent", "" + proqolPercent);

		//Burnout
		double burnPercent = (burnoutPercentage * (((BurnoutScore(date) / maxBurnoutPoints) * 100) * .01));
		if(BurnoutScore(date) <= 0)
			burnPercent = 0;
		Global.Log.v("burnPercent", "" + burnPercent);

		//BuildersKillers
		double buildersPercent = (builderskillersPercentage * (((BuildersKillersScore(date) / maxBuildersKillersPoints) * 100) * .01));
//		if(BuildersKillersScore(date) <= 0)
//			buildersPercent = 0;
		Global.Log.v("buildersPercent", "" + buildersPercent);

		//Leave clock
		double leavePercent = (clockPercentage * (((LeaveClockScore() / maxLeavePoints) * 100) * .01));
		Global.Log.v("leavePercent", "" + leavePercent);

		//Misc is 10%
		double miscPercent = MiscScore(date);
		Global.Log.v("miscPercent", "" + miscPercent);
		
		//Total up all the scores
		int outScore = (int)proqolPercent + (int)leavePercent + (int)burnPercent + (int)buildersPercent + (int)miscPercent;
		Global.Log.v("TotalScore", "" + outScore);
		return outScore;
	}
	
	public static String TotalResilienceString(int value)
	{
		String acuity = "";
		if(value >= 80)
			acuity = "HIGH";
		else if(value >= 40)
			acuity = "MED";
		else 
			acuity = "LOW";

		return acuity;
	}
}
