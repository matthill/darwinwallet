package com.ndu.mobile.darwinwallet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.AsyncTask;

public class Recognizer implements Camera.PreviewCallback, AutoFocusCallback
{
	private byte[] buffer = null;
	private Camera activeCamera = null;
	private AutoFocuser			mAutoFocuser;
	
	private boolean training_complete = false;
	
	private int width;
	private int height;
	
	private String trained_locality = "";
	
	private Context context;
	private IRecognitionEvent callback = null;
	
	private List<RecognitionResult> previousMatches = new ArrayList<RecognitionResult>(5);
	
	public Recognizer(Context context, IRecognitionEvent callback, IAutoFocusEvent afcallback)
	{
		this.context = context;
		this.callback = callback;
		
		mAutoFocuser = new AutoFocuser(afcallback);
		nvInitialize();
	}  
	
	public void close()
	{
		activeCamera = null;
	}
	
	public boolean isTrainingComplete()
	{
		return training_complete;
	}
	
	public boolean train(String locality)
	{
		if (trained_locality.equalsIgnoreCase(locality)) return true;
		
		try
		{
			nvResetTrainedDatabase();
			trainImages(locality);
			nvFinalizeTraining();
			
			trained_locality = locality;
			
			return true;
		}
		catch (Exception e)
		{
			//String temp = "";
		}
		
		return false;
	}
	
	private void trainImages(String locality) throws Exception
	{
		mAutoFocuser.disable();
		training_complete = false;
		Field[] fields = R.raw.class.getFields();
		for(int count=0; count < fields.length; count++)
		{
			if (fields[count].getName().startsWith(locality))
			{
				// This is our bill, let's parse it and process it.
				String res_name = fields[count].getName();
				
				int f_index = res_name.indexOf('f', 2);
				int b_index = res_name.indexOf('b', 2);
				
				int index = f_index;
				if ((b_index < f_index) && (b_index != -1)) index = b_index;
				String bill_name = res_name.substring(2, index + 1);
				int resourceID=fields[count].getInt(fields[count]);
				String filePath = org.opencv.android.Utils.exportResource(context, resourceID);
				nvTrainImage(bill_name, filePath);
			}
			
		}
		
		training_complete = true;
		if (SettingsActivity.getAutoFocusMode(context) == AutoFocusModes.ON) mAutoFocuser.enable();
	} 
	
	public void allocateBuffer(int width, int height, int bits_per_pixel)
	{
		this.width = width;
		this.height = height;
		int buffersize = width * height * bits_per_pixel / 8;
		
		buffer = new byte[buffersize]; 
	}
	
	public byte[] getBuffer()
	{
		return buffer;
	}
	
	public void deallocateBuffer()
	{
		buffer = null;
	}
	
	@Override
	public void onPreviewFrame(byte[] data, Camera camera)
	{
		//int test = camera.getParameters().getPreviewFormat();
		
		if ((mAutoFocuser.isAutoFocusing() == false) && (training_complete))
		{
			activeCamera = camera;
			buffer = data;
			new RecognizeBillTask().execute();
			
			// Do an auto-focus here, since the next time we get data we'll have a nice focused image.
			if (mAutoFocuser.needsAutoFocus())
			{
				startAutoFocus(camera);
			}
		}
		else
		{
			camera.addCallbackBuffer(data);
		}
	}
	
	public void startAutoFocus(Camera camera)
	{
		if (training_complete == false) return;
		
		if (mAutoFocuser.isAutoFocusing() == false)
		{
			mAutoFocuser.autoFocusStart();
			camera.autoFocus(this);
		}
	}
	
	public void setAutoFocus(boolean enabled)
	{
		if (enabled) mAutoFocuser.enable();
		else mAutoFocuser.disable();
	}
	
	@Override
	public void onAutoFocus(boolean success, Camera camera)
	{
		mAutoFocuser.autoFocusComplete();
		
		//Log.d("Recognizer", camera.getParameters().getAuto)
	}
	
	private class RecognizeBillTask extends AsyncTask<Void, Void, RecognitionResult>
	{
		@Override
		protected RecognitionResult doInBackground(Void... asdf )
		{
			//return loadImageFromNetwork(urls[0]);
			// Get buffer
			String response = "";
			if (buffer != null)
			{
				//writeToJpg();
				//response = nvRecognize(baos.size(), baos.size(), baos.toByteArray()  );
				
				response = nvRecognize(width, height, buffer  );
			}
			
			RecognitionResult result = new RecognitionResult(response);
			
			return result;
		}
		
		@Override
		protected void onPostExecute(RecognitionResult result)
		{
			//mImageView.setImageBitmap(result);
			boolean doubleCheck = SettingsActivity.getDoubleCheck(context);
			//Log.d("Recognizer",  "buffer size: " + buffer.length + " : " + result.bill_value + " : conf " + result.confidence);
			
			if (callback != null)
			{
				if (doubleCheck)
				{
					// If double check is enabled, do some fancy magic to clean up the results.
					previousMatches.add(result);
					if (previousMatches.size() > 4) previousMatches.remove(0);
					if (result.match_found == false)
					{
						// if this is no match, send it on.
						callback.recognitionEvent(result);
					}
					else if (result.confidence > 70)
					{
						// If this is a high confidence match, send it on... always
						callback.recognitionEvent(result);
					}
					else
					{
						int doublecheck_matches = 0;
						// Check the previous matches to see if there's at least one other
						for (RecognitionResult r : previousMatches)
						{
							if (r.bill_value == result.bill_value)
							{
								doublecheck_matches++;
							}
						}
						
						if (doublecheck_matches > 1) callback.recognitionEvent(result);
						else callback.recognitionEvent(new RecognitionResult(""));
					}
					
				}
				else
				{
					// If double check is not enabled, always send the result as-is
					callback.recognitionEvent(result);
				}
			}
			
			if (activeCamera != null) activeCamera.addCallbackBuffer(buffer);
		}
	}
	
	public native String nvRecognize(int width, int height, byte yuv[]);
	public native void nvInitialize();
	
	public native void nvResetTrainedDatabase();
	public native void nvTrainImage(String billname, String billpath);
	public native void nvFinalizeTraining();
	static
	{
		System.loadLibrary( "opencv_java" );
		System.loadLibrary("native_wallet");
	}
	
}