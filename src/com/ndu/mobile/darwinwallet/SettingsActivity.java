package com.ndu.mobile.darwinwallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class SettingsActivity extends PreferenceActivity
{
	public static String CURRENCY_PREF_KEY = "user_currency";
	public final static String CURRENCY_DEFAULT = "us";
	
	public final static String FLASH_PREF_KEY = "flash_on";
	public final static boolean FLASH_DEFAULT = true;
	
	public final static String DOUBLE_CHECK_PREF_KEY = "double_check";
	public final static boolean DOUBLE_CHECK_DEFAULT = false;
	
	public final static String AUTO_FOCUS_PREF_KEY = "user_autofocus_mode";
	public final static String AUTO_FOCUS_DEFAULT = String.valueOf(AutoFocusModes.FocusOnTouch.getVal());
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.settings);
		
		setPreferenceScreen(createPreferenceHierarchy());
	}
	
	private PreferenceScreen createPreferenceHierarchy()
	{
		// Root
		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
		
		// Inline preferences
		PreferenceCategory userPrefCat = new PreferenceCategory(this);
		userPrefCat.setTitle(getString(R.string.pref_title));
		root.addPreference(userPrefCat);
		
		// Currency Preference
		ListPreference prefCurrency = new ListPreference(this);
		prefCurrency.setKey(CURRENCY_PREF_KEY);
		prefCurrency.setTitle(getString(R.string.pref_currency));
		prefCurrency.setEntries(R.array.language_names);
		prefCurrency.setEntryValues(R.array.language_codes);
		prefCurrency.setDialogTitle(getString(R.string.pref_currency_dialog));
		userPrefCat.addPreference(prefCurrency);
		
		// Focus mode
		String[] focusNames = getResources().getStringArray(R.array.pref_focus_names);
		String[] focusValues = { 
				String.valueOf(AutoFocusModes.OFF.getVal()),
				String.valueOf(AutoFocusModes.FocusOnTouch.getVal()),
				String.valueOf(AutoFocusModes.ON.getVal())
		};
		ListPreference prefAutoFocus = new ListPreference(this);
		prefAutoFocus.setKey(AUTO_FOCUS_PREF_KEY);
		prefAutoFocus.setTitle(getString(R.string.pref_focus_title));
		prefAutoFocus.setEntries(focusNames);
		prefAutoFocus.setEntryValues(focusValues);
		prefAutoFocus.setDialogTitle(getString(R.string.pref_focus_dialog));
		userPrefCat.addPreference(prefAutoFocus);
		
		// Double check preference
		CheckBoxPreference prefDoubleCheck = new CheckBoxPreference(this);
		prefDoubleCheck.setKey(DOUBLE_CHECK_PREF_KEY);
		prefDoubleCheck.setDefaultValue(DOUBLE_CHECK_DEFAULT);
		prefDoubleCheck.setTitle(getString(R.string.pref_double_check_title));
		prefDoubleCheck.setSummary(getString(R.string.pref_double_check_summary));
		userPrefCat.addPreference(prefDoubleCheck);
		
		return root;
	}
	
	public static String getCurrency(Context context)
	{
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		
		return appSharedPrefs.getString(CURRENCY_PREF_KEY, CURRENCY_DEFAULT);
	}
	
	public static AutoFocusModes getAutoFocusMode(Context context)
	{
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		
		String value = appSharedPrefs.getString(AUTO_FOCUS_PREF_KEY, AUTO_FOCUS_DEFAULT);
		
		return AutoFocusModes.getMode(value);
	}
	
	public static boolean getFlash(Context context)
	{
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		
		return appSharedPrefs.getBoolean(FLASH_PREF_KEY, FLASH_DEFAULT);
	}
	
	public static void setFlash(Context context, boolean enabled)
	{
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		Editor prefsEditor = appSharedPrefs.edit();
		prefsEditor.putBoolean(FLASH_PREF_KEY, enabled);
		prefsEditor.commit();
	}
	
	public static boolean getDoubleCheck(Context context)
	{
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		
		return appSharedPrefs.getBoolean(DOUBLE_CHECK_PREF_KEY, DOUBLE_CHECK_DEFAULT);
	}
	
}