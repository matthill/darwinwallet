package com.ndu.mobile.darwinwallet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.AsyncTask;
import android.util.Log;

public class Recognizer implements Camera.PreviewCallback, AutoFocusCallback {

	private byte[] buffer = null;
	private Camera activeCamera = null;
    private AutoFocuser			mAutoFocuser;
	
	private int width;
	private int height;
	
	private String trained_locality = "";
	
	private Context context;
	private IRecognitionEvent callback = null;
	
	
	public Recognizer(Context context, IRecognitionEvent callback, IAutoFocusEvent afcallback)
	{
		this.context = context;
		
		this.callback = callback;
		
        mAutoFocuser = new AutoFocuser(afcallback);
        nvInitialize();
        
        nvResetTrainedDatabase(); 

	}  
	  
	public void close()
	{
		activeCamera = null;
	}
	
	public boolean train(String locality)
	{
		if (trained_locality.equalsIgnoreCase(locality))
			return true;
		
        try
        {
        	trainImages(locality);
        	nvFinalizeTraining();
        	
        	trained_locality = locality;
        	
        	return true;
        } 
        catch (Exception e)
        {
        	String temp = "";
        }
        
        return false;
	}
	

    private void trainImages(String locality) throws Exception
    {

    	

        Field[] fields = R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
        	if (fields[count].getName().startsWith(locality))
        	{
        		// This is our bill, let's parse it and process it.
        		String res_name = fields[count].getName();
        		
        		int f_index = res_name.indexOf('f', 2);
        		int b_index = res_name.indexOf('b', 2);
        		
        		int index = f_index;
        		if ((b_index < f_index) && (b_index != -1))
        			index = b_index;
        		
        		String bill_name = res_name.substring(2, index + 1);
        		
        		int resourceID=fields[count].getInt(fields[count]);
        		
        		String filePath = org.opencv.android.Utils.exportResource(context, resourceID);
        		
				nvTrainImage(bill_name, filePath);
        	}
        	 
        }
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
	public void onPreviewFrame(byte[] data, Camera camera) {

		if (mAutoFocuser.needsAutoFocus())
		{
			startAutoFocus(camera);
		}

		if (mAutoFocuser.isAutoFocusing() == false)
		{
			activeCamera = camera;
			
			new RecognizeBillTask().execute();
		}
		else
		{
			camera.addCallbackBuffer(data);
		}
	} 
	
	public void startAutoFocus(Camera camera)
	{
		mAutoFocuser.autoFocusStart();
		camera.autoFocus(this);
	}
	
	public void disableAutoFocus()
	{
		mAutoFocuser.disable();
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		mAutoFocuser.autoFocusComplete();
	}
	
 
	private class RecognizeBillTask extends AsyncTask<Void, Void, RecognitionResult> {
		
	     protected RecognitionResult doInBackground(Void... asdf ) {
	         //return loadImageFromNetwork(urls[0]);
	    	 
	    	 // Get buffer
	    	 
	    	 String response = "";
	    	 if (buffer != null)
	    	 {
    			response = nvRecognize(width, height, buffer  );
	    			
	    	 }
	    	 

	    	 RecognitionResult result = new RecognitionResult(response);
	    	 
	    	 
	    	 return result;
	     }

	     protected void onPostExecute(RecognitionResult result) {
	         //mImageView.setImageBitmap(result);

 			Log.d("Recognizer",  "buffer size: " + buffer.length + " : " + result.bill_value + " : conf " + result.confidence);
 			
 			if (callback != null)
 				callback.recognitionEvent(result);
 			
	    	 if (activeCamera != null)
	    		 activeCamera.addCallbackBuffer(buffer);
	     }
	 }
	
	
	
	
	
    public native String nvRecognize(int width, int height, byte yuv[]);
    public native void nvInitialize();

    public native void nvResetTrainedDatabase();
    public native void nvTrainImage(String billname, String billpath);
    public native void nvFinalizeTraining();
    static {
        System.loadLibrary("native_wallet");
    }
    
}
