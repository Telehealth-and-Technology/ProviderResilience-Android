package org.t2.pr.classes;

/*
 * PreferenceHelper.java
 * Interface to simplify migration from SharedPreferences to FIPS DB
 *
 * Created by Steve Ody
 *
 * PROVIDER RESILIENCE
 *
 * Copyright © 2009-2014 United States Government as represented by
 * the Chief Information Officer of the National Center for Telehealth
 * and Technology. All Rights Reserved.
 *
 * Copyright © 2009-2014 Contributors. All Rights Reserved.
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
 * Government Agency Original Software Designation: PECoach
 * Government Agency Original Software Title: PE Coach
 * User Registration Requested. Please send email
 * with your contact information to: robert.a.kayl.civ@mail.mil
 * Government Agency Point of Contact for Original Software: robert.a.kayl.civ@mail.mil
 *
 */

public class PreferenceHelper {

	/**
	 * Universal preference methods
	 */
	
	public static String getStringForKey(String key, String value) {
		String result = Global.databaseHelper.getPreference(key);
		if( (result == null) || (result.trim().equals("")) )
			return value;
		else
			return result;
	}

	public static void setStringForKey(String key, String value) {
		Global.databaseHelper.setPreference(key, value);
	}

	public static int getIntForKey(String key, int value) {
		String val = Global.databaseHelper.getPreference(key);
		try{
			int parsed = Integer.parseInt(val);
			return parsed;
		}
		catch(Exception ex)
		{
			return value;
		}
	}

	public static void setIntForKey(String key, int value) {
		Global.databaseHelper.setPreference(key, ""+value);
	}

	public static Boolean getBooleanForKey(String key, Boolean value) {
		try
		{
			String val = Global.databaseHelper.getPreference(key);
			if (val != null && val.equals("true"))
					return true;
			else if (val != null && val.equals("false"))
					return false;
			else
				return value;
		}catch(Exception ex)
		{
			return value;
		}
	}

	public static void setBooleanForKey(String key, Boolean value) {
		
		if(value)
			Global.databaseHelper.setPreference(key, "true");
		else
			Global.databaseHelper.setPreference(key, "false");
	}

	
	/**
	 * App Specific preference methods
	 */
	
	public static String getStudyParticipantNumber() {
		return getStringForKey("prf_study_participant_number", "");
	}
	
	public static void clearStudyParticipantData() {
		setStringForKey("prf_study_participant_number", "");
		setStringForKey("prf_study_participant_email", "");
	}
	
	public static String getStudyParticipantEmail() {
		return getStringForKey("prf_study_participant_email", "");
	}

	public static int getPopupCardDay() {
		return getIntForKey("card_day", 0);
	}

	public static void setPopupCardDay(int dayofyear) {
		setIntForKey("card_day", dayofyear);
	}
	
	public static boolean getIsEulaAccepted() {
		return getBooleanForKey("eula_accepted", false);
	}

	public static void setIsEulaAccepted(boolean enabled) {
		setBooleanForKey("eula_accepted", enabled);
	}

	public static boolean getSendAnnonData() {
		return getBooleanForKey("send_anon_data", true);
	}

	public static void setSendAnnonData(boolean enabled) {
		setBooleanForKey("send_anon_data", enabled);
	}

	public static int getVacationYear() {
		return getIntForKey("vacation_year", 0);
	}

	public static void setVacationYear(int year) {
		setIntForKey("vacation_year", year);
	}

	public static int getVacationMonth() {
		return getIntForKey("vacation_month", 0);
	}

	public static void setVacationMonth(int month) {
		setIntForKey("vacation_month", month);
	}

	public static int getVacationDay() {
		return getIntForKey("vacation_day", 0);
	}

	public static void setVacationDay(int day) {
		setIntForKey("vacation_day", day);
	}

	public static boolean getOnVacation() {
		return getBooleanForKey("on_vacation", false);
	}

	public static void setOnVacation(boolean enabled) {
		setBooleanForKey("on_vacation", enabled);
	}

	public static String getLastAssessmentDate() {
		return getStringForKey("last_assessment_date", "null");
	}
	
	public static void setLastAssessmentDate(String date) {
		setStringForKey("last_assessment_date", date);
	}
	
	public static boolean getWelcomeMessage() {
		return getBooleanForKey("welcome", false);
	}

	public static void setWelcomeMessage(boolean enabled) {
		setBooleanForKey("welcome", enabled);
	}
	
	public static boolean getReminders() {
		return getBooleanForKey("reminders", false);
	}

	public static void setReminders(boolean enabled) {
		setBooleanForKey("reminders", enabled);
	}
	
	public static int getNotifyHour() {
		return getIntForKey("notify_hour", 1);
	}

	public static void setNotifyHour(int hour) {
		setIntForKey("notify_hour", hour);
	}
	
	public static int getNotifyMinute() {
		return getIntForKey("notify_minute", 1);
	}

	public static void setNotifyMinute(int minute) {
		setIntForKey("notify_minute", minute);
	}

	public static int getResetHour() {
		return getIntForKey("reset_hour", 1);
	}

	public static void setResetHour(int hour) {
		setIntForKey("reset_hour", hour);
	}
	
	public static int getResetMinute() {
		return getIntForKey("reset_minute", 0);
	}

	public static void setResetMinute(int minute) {
		setIntForKey("reset_minute", minute);
	}
	
	public static int getCardIndex() {
		return getIntForKey("card_index", 0);
	}

	public static void setCardIndex(int card) {
		setIntForKey("card_index", card);
	}
}

