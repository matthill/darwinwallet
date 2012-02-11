package com.ndu.mobile.darwinwallet;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;

public class Voice implements OnUtteranceCompletedListener, OnInitListener
{
//	private static final String EARCON_SENTENCE_END = "[sentence_end]";
//	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//	private static final int RAND_LENGTH = 16; 
//	private static Random rnd = new Random();
	private int MY_DATA_CHECK_CODE = 0;

	private TextToSpeech tts;
	private String activeText = "";
	//private boolean paused = false;
	private boolean shutdown = false;
	
//	private boolean earconInitialized = false;
//	private boolean earconEnabled = true;
	
	
	private Context context;
	
	public Voice(Activity context)
	{
		//tts = new TextToSpeech(context, this);
		this.context = context;

        tts = new TextToSpeech(context, this);
		

		       

	}



	@Override
	public void onInit(int status) {
	       if (status == TextToSpeech.SUCCESS) {
	    	   // good...

	        } else {
	            // Initialization failed.
	        	
	        	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        	builder.setMessage("Your Android device does not have Text-to-Speech enabled.  You must enable Text-to-Speech in your Android settings in order to play text books.  Do you wish to go to your Android settings now?")
	        	       .setCancelable(false)
	        	       .setPositiveButton("Android Settings", new DialogInterface.OnClickListener() {
	        	           @Override
						public void onClick(DialogInterface dialog, int id) {
	        	                //MyActivity.this.finish();

	        		        	Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
	        	                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	                context.startActivity(dialogIntent);
	        	                
	        	                ((Activity)context).finish();
	        	           }
	        	       })
	        	       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        	           @Override
						public void onClick(DialogInterface dialog, int id) {
	        	                //dialog.cancel();
	        	        	   ((Activity)context).finish();
	        	           }
	        	       });
	        	//AlertDialog alert = 
	        	builder.create();
	        	
	        }
	}
	
	public void shutdown()
	{
		if (tts != null)
			tts.shutdown();
		shutdown = true;
	}
	
//	public void speakWithCallback(String text)
//	{
//		// Don't speak anything if we've shut down the TTS
//		if (shutdown)
//			return;
//		
//		tts.setOnUtteranceCompletedListener(this);
//		
//		HashMap<String, String> ttsParamsHash = new HashMap<String, String>();
//		
//		activeText = randomString(RAND_LENGTH);
//    	ttsParamsHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, activeText);
//    	
//
//    	
//    	
//        tts.speak(text, TextToSpeech.QUEUE_FLUSH, ttsParamsHash);
//	}

	public void setSpeed(float newSpeed)
	{
		if ((tts != null) && (!shutdown))
			tts.setSpeechRate(newSpeed);
	}
	public void setPitch(float newPitch)
	{
		if ((tts != null) && (!shutdown))
			tts.setPitch(newPitch);
	}
	
	
	
	public void speakWithoutCallback(String text)
	{
		// Don't speak anything if we've shut down the TTS
		if (shutdown)
			return;
		
		if (tts == null)
			return;
		
		tts.setOnUtteranceCompletedListener(this);
		activeText = "ignore";
		if (tts.isSpeaking() == false)
			tts.speak(text, TextToSpeech.QUEUE_ADD, null);
    	
	}
	
	
	public boolean isTTSAvailable()
	{
		int val = tts.isLanguageAvailable(Locale.getDefault());
		
		if (val == TextToSpeech.LANG_MISSING_DATA)// || (val == TextToSpeech.LANG_NOT_SUPPORTED))
			return false;
		
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
	
	
	
//	private String randomString( int len ) 
//	{
//	   StringBuilder sb = new StringBuilder( len );
//	   for( int i = 0; i < len; i++ ) 
//	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
//	   return sb.toString();
//	}

	
}
