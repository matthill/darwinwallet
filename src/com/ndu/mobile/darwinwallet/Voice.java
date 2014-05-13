package com.ndu.mobile.darwinwallet;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;

public class Voice implements OnUtteranceCompletedListener, OnInitListener
{
	private TextToSpeech tts;
	private String activeText = "";
	private boolean shutdown = false;
	
	private Context context;
	
	public Voice(Activity context)
	{
		this.context = context;
		
		tts = new TextToSpeech(context, this);
	}
	
	@Override
	public void onInit(int status)
	{
		if (status == TextToSpeech.SUCCESS)
		{
			// good...
		}
		else
		{
			// Initialization failed.
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(context.getString(R.string.tts_not_enabled))
			.setCancelable(false)
			.setPositiveButton(context.getString(R.string.android_settings), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int id)
				{
					Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
					dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(dialogIntent);
					
					((Activity)context).finish();
				}
			})
			.setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int id)
				{
					((Activity)context).finish();
				}
			});
			builder.create();
		}
	}
	
	public void shutdown()
	{
		if (tts != null) tts.shutdown();
		shutdown = true;
	}
	
	public void setSpeed(float newSpeed)
	{
		if ((tts != null) && (!shutdown)) tts.setSpeechRate(newSpeed);
	}
	
	public void setPitch(float newPitch)
	{
		if ((tts != null) && (!shutdown)) tts.setPitch(newPitch);
	}
	
	public void speakWithoutCallback(String text)
	{
		// Don't speak anything if we've shut down the TTS
		if (shutdown) return;
		
		if (tts == null) return;
		
		tts.setOnUtteranceCompletedListener(this);
		activeText = "ignore";
		if (tts.isSpeaking() == false) tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	public boolean isTTSAvailable()
	{
		int val = tts.isLanguageAvailable(Locale.getDefault());
		
		if (val == TextToSpeech.LANG_MISSING_DATA) return false;
		
		return true;
	}
	
	@Override
	public void onUtteranceCompleted(String utteranceId)
	{
		System.out.println("TTS Utterance Complete");
		if (utteranceId.equals(activeText) )
		{
		}
	}
	
}