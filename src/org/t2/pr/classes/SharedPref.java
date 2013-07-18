package org.t2.pr.classes;

import org.t2.pr.R;

public class SharedPref {

	public static String getStudyParticipantNumber() {
		return Global.sharedPref.getString("prf_study_participant_number", "");
	}
	
	public static void clearStudyParticipantData() {
		Global.sharedPref.edit().putString("prf_study_participant_number", "").commit();
		Global.sharedPref.edit().putString("prf_study_participant_email", "").commit();
	}
	
	public static String getStudyParticipantEmail() {
		return Global.sharedPref.getString("prf_study_participant_email", "");
	}

	public static int getPopupCardDay() {
		return Global.sharedPref.getInt("card_day", 0);
	}

	public static void setPopupCardDay(int dayofyear) {
		Global.sharedPref.edit().putInt("card_day", dayofyear).commit();
	}
	
	public static boolean getIsEulaAccepted() {
		return Global.sharedPref.getBoolean("eula_accepted", false);
	}

	public static void setIsEulaAccepted(boolean enabled) {
		Global.sharedPref.edit().putBoolean("eula_accepted", enabled).commit();
	}

	public static boolean getSendAnnonData() {
		return Global.sharedPref.getBoolean("send_anon_data", true);
	}

	public static void setSendAnnonData(boolean enabled) {
		Global.sharedPref.edit().putBoolean("send_anon_data", enabled).commit();
	}

	public static int getVacationYear() {
		return Global.sharedPref.getInt("vacation_year", 0);
	}

	public static void setVacationYear(int year) {
		Global.sharedPref.edit().putInt("vacation_year", year).commit();
	}

	public static int getVacationMonth() {
		return Global.sharedPref.getInt("vacation_month", 0);
	}

	public static void setVacationMonth(int month) {
		Global.sharedPref.edit().putInt("vacation_month", month).commit();
	}

	public static int getVacationDay() {
		return Global.sharedPref.getInt("vacation_day", 0);
	}

	public static void setVacationDay(int day) {
		Global.sharedPref.edit().putInt("vacation_day", day).commit();
	}

	public static boolean getOnVacation() {
		return Global.sharedPref.getBoolean("on_vacation", false);
	}

	public static void setOnVacation(boolean enabled) {
		Global.sharedPref.edit().putBoolean("on_vacation", enabled).commit();
	}

	public static String getLastAssessmentDate() {
		return Global.sharedPref.getString("last_assessment_date", "null");
	}
	
	public static void setLastAssessmentDate(String date) {
		Global.sharedPref.edit().putString("last_assessment_date", date).commit();
	}
	
	public static boolean getWelcomeMessage() {
		return Global.sharedPref.getBoolean("welcome", false);
	}

	public static void setWelcomeMessage(boolean enabled) {
		Global.sharedPref.edit().putBoolean("welcome", enabled).commit();
	}
	
	public static boolean getReminders() {
		return Global.sharedPref.getBoolean("reminders", false);
	}

	public static void setReminders(boolean enabled) {
		Global.sharedPref.edit().putBoolean("reminders", enabled).commit();
	}
	
	public static int getNotifyHour() {
		return Global.sharedPref.getInt("notify_hour", 1);
	}

	public static void setNotifyHour(int hour) {
		Global.sharedPref.edit().putInt("notify_hour", hour).commit();
	}
	
	public static int getNotifyMinute() {
		return Global.sharedPref.getInt("notify_minute", 1);
	}

	public static void setNotifyMinute(int minute) {
		Global.sharedPref.edit().putInt("notify_minute", minute).commit();
	}

	public static int getResetHour() {
		return Global.sharedPref.getInt("reset_hour", 1);
	}

	public static void setResetHour(int hour) {
		Global.sharedPref.edit().putInt("reset_hour", hour).commit();
	}
	
	public static int getResetMinute() {
		return Global.sharedPref.getInt("reset_minute", 0);
	}

	public static void setResetMinute(int minute) {
		Global.sharedPref.edit().putInt("reset_minute", minute).commit();
	}
	
	public static int getCardIndex() {
		return Global.sharedPref.getInt("card_index", 0);
	}

	public static void setCardIndex(int card) {
		Global.sharedPref.edit().putInt("card_index", card).commit();
	}
	
	public static boolean getAnonData() {
		return Global.sharedPref.getBoolean("anondata", true);
	}

	public static void setAnonData(boolean enabled) {
		Global.sharedPref.edit().putBoolean("anondata", enabled).commit();
	}
}
