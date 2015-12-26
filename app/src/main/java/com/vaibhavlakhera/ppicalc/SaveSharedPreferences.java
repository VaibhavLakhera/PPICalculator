package com.vaibhavlakhera.ppicalc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferences
{
	static final String PREF_SCREEN_SIZE = "screen_size";
	static final String PREF_SCREEN_HEIGHT = "screen_height";
	static final String PREF_SCREEN_WIDTH = "screen_width";
	static final Double PREF_SCREEN_PPI = 0.0;

	static SharedPreferences getSharedPreferences(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static void setScreenSize(Context context, String screenSize)
	{
		SharedPreferences.Editor editor = getSharedPreferences(context).edit();
		editor.putString(PREF_SCREEN_SIZE, screenSize);
		editor.apply();
	}

	public static void clearSharedPreferences(Context context)
	{
		SharedPreferences.Editor editor = getSharedPreferences(context).edit();
		editor.clear(); //clear all stored data
		editor.apply();
	}
}
