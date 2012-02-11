//package com.ndu.mobile.darwinwallet;
//
//
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.preference.CheckBoxPreference;
//import android.preference.ListPreference;
//import android.preference.PreferenceManager;
//import android.preference.PreferenceActivity;
//import android.preference.PreferenceCategory;
//import android.preference.PreferenceScreen;
//import android.webkit.WebSettings.RenderPriority;
//
//public class SettingsActivity extends PreferenceActivity
//{
//	public final static String TEXT_COLOR_PREF = "color_text"; 
//	public final static String SENTENCE_COLOR_PREF = "color_sentence";
//	public final static String SECTION_COLOR_PREF = "color_section";
//	public final static String BG_COLOR_PREF = "color_background"; 
//	public final static String DISPLAY_IMAGES_PREF = "color_display_images"; 
//
//	public final static String VOICE_ENABLED_PREF = "voice_enabled";
//	public final static String VOICE_SPEED_PREF = "voice_speed";
//	public final static String VOICE_PITCH_PREF = "voice_pitch";
//	public final static String VOICE_PUNCTUATION_PREF = "voice_punctuation";
//	public final static String VOICE_SPEAK_PAGES_PREF = "voice_speak_pages";
//	public final static String VOICE_EYES_FREE_ONLY_PREF = "voice_eyes_free_only";
//	public final static String VOICE_EARCON_PREF = "voice_earcon_enabled";
//	
//	public final static String FONT_SIZE_PREF = "font_size";
//	public final static String LINE_SPACING_PREF = "line_spacing";
//	public final static String TEXT_JUSTIFICATION_PREF = "text_justification";
//	public final static String SENTENCE_HIGHLIGHT_PREF = "sentence_highlight";
//	
//	
//	public final static String EYES_FREE_MODE_PREF = "eyes_free_mode";
//	public final static String EYES_FREE_TONES_PREF = "eyes_free_tones";
//	public final static String LAST_USED_MAIN_NAV = "last_used_main_nav";
//	
//
//	public final static boolean EYES_FREE_TONES_DEfAULT = true;
//	
//	public final static int TEXT_COLOR_DEFAULT = Color.rgb(0, 0, 0);
//	public final static int SENTENCE_COLOR_DEFAULT = Color.rgb(255, 5, 5);
//	public final static int SECTION_COLOR_DEFAULT = Color.rgb(240, 240, 240);
//	public final static int BG_COLOR_DEFAULT = Color.rgb(255, 255, 255);
// 
//	public final static String FONT_SIZE_DEFAULT = "1.4em";
//	public final static String LINE_SPACING_DEFAULT = "1.3";
//	public final static String TEXT_JUSTIFICATION_DEFAULT = "justify";
//	public final static String SENTENCE_HIGHLIGHT_DEFAULT = "none";
//	public final static boolean DISPLAY_IMAGES_DEFAULT = true;
//
//	public final static String VOICE_SPEED_DEFAULT = "0.0";
//	public final static String VOICE_PITCH_DEFAULT = "1.0";
//	public final static String VOICE_PUNCTUATION_DEFAULT = "";
//	public final static boolean VOICE_ENABLED_DEFAULT = true;
//	public final static boolean VOICE_SPEAK_PAGES_DEFAULT = true;
//	public final static boolean VOICE_EYES_FREE_ONLY_DEFAULT = false;
//	public final static boolean VOICE_EARCON_DEFAULT = true;
//	
//	private final static String ONE_OFF_EYES_FREE_MODE_CHOSEN = "oneoff_eyesfreechosen";
//	private final static String ONE_OFF_DOCUMENTATION_COPIED = "oneoff_doccopied";
//
//	private final static String LIBRARY_SORT_PREFERENCE = "library_sort";
//	
//	public static final int SETTINGS_RESPONSE_CODE = 3301;
//	
//	private SharedPreferences settings;
//
//	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.settings);
//
//		setPreferenceScreen(createPreferenceHierarchy());
//
//		settings = PreferenceManager.getDefaultSharedPreferences(this);
//
//
//		Intent result = new Intent();
//		setResult(SETTINGS_RESPONSE_CODE, result);
//		
//	} 
//	
//
//	
//	
//	private PreferenceScreen createPreferenceHierarchy()
//	{
//		// Root
//		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
//
//		// Inline preferences
//		PreferenceCategory displayPrefCat = new PreferenceCategory(this);
//		displayPrefCat.setTitle("Display Preferences");
//		root.addPreference(displayPrefCat);
//
//		// Toggle preference
////		CheckBoxPreference togglePref = new CheckBoxPreference(this);
////		togglePref.setKey("toggle_preference");
////		togglePref.setTitle("Title");
////		togglePref.setSummary("Summary");
////		colorPrefCat.addPreference(togglePref);
////		
////		SliderPreference bgPref = new SliderPreference(this);
////		bgPref.setKey("bg_color");
////		bgPref.setTitle("Title");
////		bgPref.setSummary("Summary");
////		colorPrefCat.addPreference(bgPref);
//
//
//		CheckBoxPreference prefDisplayImages = new CheckBoxPreference(this);
//		prefDisplayImages.setKey(DISPLAY_IMAGES_PREF);
//		prefDisplayImages.setDefaultValue(DISPLAY_IMAGES_DEFAULT);
//		prefDisplayImages.setTitle("Display Images");
//		//togglePref.setSummary("Determines whether page numbers should be spoken while the book is reading");
//		displayPrefCat.addPreference(prefDisplayImages);
//		
//		 
//		// Dialog based preferences
//		PreferenceCategory fontPrefCat = new PreferenceCategory(this);
//		fontPrefCat.setTitle("Font Preferences");
//		root.addPreference(fontPrefCat);
//
//		
//		/*
//		// List preference
//		ListPreference fontFacePref = new ListPreference(this);
//		fontFacePref.setEntries(R.array.entries_list_preference);
//		fontFacePref.setEntryValues(R.array.entryvalues_list_preference);
//		fontFacePref.setDialogTitle("mTitle");
//		fontFacePref.setKey("font_face");
//		fontFacePref.setTitle("Font Face");
////		fontFacePref.setSummary("Choose from a selection of font styles");
////		fontFacePref.setOnPreferenceChangeListener(this);
//		fontPrefCat.addPreference(fontFacePref);
//*/
//		
//		ListPreference fontSizePref = new ListPreference(this);
//		fontSizePref.setEntries(R.array.preference_font_size);
//		fontSizePref.setEntryValues(R.array.preference_font_size_values);
//		fontSizePref.setDialogTitle("Select font size");
//		fontSizePref.setKey(FONT_SIZE_PREF);
//		fontSizePref.setTitle("Font Size");
////		fontSizePref.setSummary("Enlarge or shrink the size of the font");
////		fontSizePref.setOnPreferenceChangeListener(this);
//		fontPrefCat.addPreference(fontSizePref);
//
// 
//		ListPreference lineSpacingPref = new ListPreference(this);
//		lineSpacingPref.setEntries(R.array.preference_line_spacing);
//		lineSpacingPref.setEntryValues(R.array.preference_line_spacing_values);
//		lineSpacingPref.setDialogTitle("Select spacing between lines");
//		lineSpacingPref.setKey(LINE_SPACING_PREF);
//		lineSpacingPref.setTitle("Line Spacing");
////		fontSizePref.setSummary("Enlarge or shrink the size of the font");
////		fontSizePref.setOnPreferenceChangeListener(this);
//		fontPrefCat.addPreference(lineSpacingPref);
//		
//
//		ListPreference textJustificationPref = new ListPreference(this);
//		textJustificationPref.setEntries(R.array.preference_text_justification);
//		textJustificationPref.setEntryValues(R.array.preference_text_justification_values);
//		textJustificationPref.setDialogTitle("Select spacing between lines");
//		textJustificationPref.setKey(TEXT_JUSTIFICATION_PREF);
//		textJustificationPref.setTitle("Text Alignment");
////		fontSizePref.setSummary("Enlarge or shrink the size of the font");
////		fontSizePref.setOnPreferenceChangeListener(this);
//		fontPrefCat.addPreference(textJustificationPref);
//		
////		ListPreference sentenceHighlightPref = new ListPreference(this);
////		sentenceHighlightPref.setEntries(R.array.preference_sentence_highlight);
////		sentenceHighlightPref.setEntryValues(R.array.preference_sentence_highlight_values);
////		sentenceHighlightPref.setDialogTitle("Select sentence highlight style");
////		sentenceHighlightPref.setKey(SENTENCE_HIGHLIGHT_PREF);
////		sentenceHighlightPref.setTitle("Sentence Highlight");
////		fontPrefCat.addPreference(sentenceHighlightPref);
//		
//		
//
//		// Dialog based preferences
//		PreferenceCategory voicePrefCat = new PreferenceCategory(this);
//		voicePrefCat.setTitle("Voice Preferences");
//		root.addPreference(voicePrefCat);
//		
//
//		CheckBoxPreference voiceSpeechModePref = new CheckBoxPreference(this);
//		voiceSpeechModePref.setKey(VOICE_ENABLED_PREF);
//		voiceSpeechModePref.setDefaultValue(VOICE_ENABLED_DEFAULT);
//		voiceSpeechModePref.setTitle("Speech Mode");
//		//togglePref.setSummary("Determines whether page numbers should be spoken while the book is reading");
//		voicePrefCat.addPreference(voiceSpeechModePref);
//		
//		ListPreference voiceSpeedPref = new ListPreference(this);
//		voiceSpeedPref.setEntries(R.array.preference_voice_speed);
//		voiceSpeedPref.setEntryValues(R.array.preference_voice_speed_values);
//		voiceSpeedPref.setDialogTitle("Choose Voice Speed");
//		voiceSpeedPref.setKey(VOICE_SPEED_PREF);
//		voiceSpeedPref.setTitle("Voice Speed");
//		voicePrefCat.addPreference(voiceSpeedPref);
//		
//		ListPreference voicePitchPref = new ListPreference(this);
//		voicePitchPref.setEntries(R.array.preference_voice_pitch);
//		voicePitchPref.setEntryValues(R.array.preference_voice_pitch_values);
//		voicePitchPref.setDialogTitle("Choose Voice Pitch");
//		voicePitchPref.setKey(VOICE_PITCH_PREF);
//		voicePitchPref.setTitle("Voice Pitch");
//		voicePrefCat.addPreference(voicePitchPref);
//
//		ListPreferenceMultiSelect voicePunctuationLevelPref = new ListPreferenceMultiSelect(this);
//		voicePunctuationLevelPref.setEntries(R.array.preference_punctuation_level);
//		voicePunctuationLevelPref.setEntryValues(R.array.preference_punctuation_level_values);
//		voicePunctuationLevelPref.setDialogTitle("Choose Punctuation Level");
//		voicePunctuationLevelPref.setKey(VOICE_PUNCTUATION_PREF);
//		voicePunctuationLevelPref.setTitle("Punctuation Level");
//		voicePrefCat.addPreference(voicePunctuationLevelPref);
//		
//		CheckBoxPreference voiceSpeakPagesPref = new CheckBoxPreference(this);
//		voiceSpeakPagesPref.setKey(VOICE_SPEAK_PAGES_PREF);
//		voiceSpeakPagesPref.setDefaultValue(VOICE_SPEAK_PAGES_DEFAULT);
//		voiceSpeakPagesPref.setTitle("Speak Page Numbers");
//		//togglePref.setSummary("Determines whether page numbers should be spoken while the book is reading");
//		voicePrefCat.addPreference(voiceSpeakPagesPref);
//
//		
//
//		// Dialog based preferences
//		PreferenceCategory eyesFreePrefCat = new PreferenceCategory(this);
//		eyesFreePrefCat.setTitle("Eyes-Free Mode Preferences");
//		
//		root.addPreference(eyesFreePrefCat);
//		
//
//		CheckBoxPreference eyesFreeEnabledPref = new CheckBoxPreference(this);
//		eyesFreeEnabledPref.setKey(EYES_FREE_MODE_PREF);
//		eyesFreeEnabledPref.setDefaultValue(false);
//		eyesFreeEnabledPref.setTitle("Eyes-Free Mode Enabled");
//		//togglePref.setSummary("Determines whether page numbers should be spoken while the book is reading");
//		eyesFreePrefCat.addPreference(eyesFreeEnabledPref);
//
//		CheckBoxPreference eyesFreeTonesPref = new CheckBoxPreference(this);
//		eyesFreeTonesPref.setKey(EYES_FREE_TONES_PREF);
//		eyesFreeTonesPref.setDefaultValue(EYES_FREE_TONES_DEfAULT);
//		eyesFreeTonesPref.setTitle("Eyes-Free Audio Tones On");
//		//togglePref.setSummary("Determines whether page numbers should be spoken while the book is reading");
//		eyesFreePrefCat.addPreference(eyesFreeTonesPref);
//		
//		// TODO: Decide if I want to reenable this someday...
////		CheckBoxPreference voiceWordTouchPref = new CheckBoxPreference(this);
////		voiceWordTouchPref.setKey(VOICE_EYES_FREE_ONLY_PREF);
////		voiceWordTouchPref.setDefaultValue(VOICE_EYES_FREE_ONLY_DEFAULT);
////		voiceWordTouchPref.setTitle("Eyes Free Mode");
////		voiceWordTouchPref.setSummary("Improves app performance for blind users (requires book reload)");
////		voicePrefCat.addPreference(voiceWordTouchPref);
//
////		CheckBoxPreference voiceEarconPref = new CheckBoxPreference(this);
////		voiceEarconPref.setKey(VOICE_EARCON_PREF);
////		voiceEarconPref.setDefaultValue(VOICE_EARCON_DEFAULT);
////		voiceEarconPref.setTitle("Sound at Sentence End");
////		voicePrefCat.addPreference(voiceEarconPref);
//		
////		ListPreference voicePitchPref = new ListPreference(this);
////		voicePitchPref.setEntries(R.array.preference_sentence_highlight);
////		voicePitchPref.setEntryValues(R.array.preference_sentence_highlight_values);
////		voicePitchPref.setDialogTitle("Voice Pitch");
////		voicePitchPref.setKey(SENTENCE_HIGHLIGHT_PREF);
////		voicePitchPref.setTitle("Sentence Highlight");
////		voicePrefCat.addPreference(voicePitchPref);
//		
//
//		return root;
//	}
//
// 
//
//
//	public static void setEyesFreeMode(Context context, boolean enabled)
//	{
//
//    	// Save the eyesFree mode status to permanent preferences:
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	Editor prefsEditor = appSharedPrefs.edit();
//    	prefsEditor.putBoolean(EYES_FREE_MODE_PREF, enabled);
//    	prefsEditor.commit();
//
//	}
//	public static boolean getEyesFreeMode(Context context)
//	{
//    	// Retrieve the eyes free mode pref:
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	return appSharedPrefs.getBoolean(EYES_FREE_MODE_PREF, false);
//   
//	}
//
//	public static boolean isEyesFreeOptimized(Context context)
//	{
//    	// Retrieve the eyes free mode pref:
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	return appSharedPrefs.getBoolean(VOICE_EYES_FREE_ONLY_PREF, VOICE_EYES_FREE_ONLY_DEFAULT);
//   
//	}
//	
//	public static void setLastUsedNavMode(Context context, NavigationMode mode)
//	{
//		boolean prefValue = true;
//		if (mode == NavigationMode.PageNavigation)
//			prefValue = false;
//		
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	Editor prefsEditor = appSharedPrefs.edit();
//    	prefsEditor.putBoolean(LAST_USED_MAIN_NAV, prefValue);
//    	prefsEditor.commit();
//	}
//	public static NavigationMode getLastUsedNavMode(Context context)
//	{
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	boolean mainNav = appSharedPrefs.getBoolean(LAST_USED_MAIN_NAV, true);
//    	
//    	if (mainNav)
//    		return NavigationMode.MainNavigation;
//    	
//    	return NavigationMode.PageNavigation;
//		
//	}
//	
//	public static void setLibrarySortPreference(Context context, LibraryRowSortables sortOrder)
//	{
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	Editor prefsEditor = appSharedPrefs.edit();
//    	prefsEditor.putInt(LIBRARY_SORT_PREFERENCE, sortOrder.getVal());
//    	prefsEditor.commit();
//	}
//	
//	public static LibraryRowSortables getLibrarySortPreference(Context context)
//	{
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	int prefVal = appSharedPrefs.getInt(LIBRARY_SORT_PREFERENCE, LibraryRowSortables.TITLE.getVal());
//    	
//    	return LibraryRowSortables.getFromVal(prefVal);
//	}
//
//	public static void setSpeedPreference(Context context, float newSpeed)
//	{
//    	setSpeedPreference(context, String.valueOf(newSpeed));
//	}
//	public static void setSpeedPreference(Context context, String newSpeed)
//	{
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	Editor prefsEditor = appSharedPrefs.edit();
//    	prefsEditor.putString(VOICE_SPEED_PREF, newSpeed);
//    	prefsEditor.commit();
//	}
//
//	public static void setSpeechEnabledPreference(Context context, boolean enabled)
//	{
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	Editor prefsEditor = appSharedPrefs.edit();
//    	prefsEditor.putBoolean(VOICE_ENABLED_PREF, enabled);
//    	prefsEditor.commit();
//	}
//	
//	
//	public static boolean getOneOffEyesFreeModeChosen(Context context)
//	{
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	return appSharedPrefs.getBoolean(ONE_OFF_EYES_FREE_MODE_CHOSEN, false);
//	}
//	public static void setOneOffEyesFreeModeChosen(Context context, boolean newVal)
//	{
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	Editor prefsEditor = appSharedPrefs.edit();
//    	prefsEditor.putBoolean(ONE_OFF_EYES_FREE_MODE_CHOSEN, newVal);
//    	prefsEditor.commit();
//	}
//
//	public static boolean getOneOffDocumentationCopied(Context context)
//	{
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	return appSharedPrefs.getBoolean(ONE_OFF_DOCUMENTATION_COPIED, false);
//	}
//	public static void setOneOffDocumentationCopied(Context context, boolean newVal)
//	{
//    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//    	Editor prefsEditor = appSharedPrefs.edit();
//    	prefsEditor.putBoolean(ONE_OFF_DOCUMENTATION_COPIED, newVal);
//    	prefsEditor.commit();
//	}
////	public static boolean isEarconEnabled(Context context)
////	{
////    	SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
////    	return appSharedPrefs.getBoolean(VOICE_EARCON_PREF, VOICE_EARCON_DEFAULT);
////		
////	}
//
//
//
//}
