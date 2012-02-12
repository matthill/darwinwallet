package com.ndu.mobile.darwinwallet;

import java.util.Calendar;

public class AutoFocuser {

	private static final long AUTO_FOCUS_INTERVAL_MS = 9000;
    private boolean 			mAutoFocusing;
    private long 				lastAutoFocus;
    
    private boolean enabled = false;
    private IAutoFocusEvent afcallback;
    
    public AutoFocuser(IAutoFocusEvent afcallback)
    {
    	this.afcallback = afcallback;
    	lastAutoFocus = getCurTime();
    }

    public boolean needsAutoFocus()
    {
    	if (enabled == false)
    		return false;
    	
    	if ((getCurTime() - lastAutoFocus) > AUTO_FOCUS_INTERVAL_MS)
    	{
    		if (mAutoFocusing == false)
    			return true;
    	}
    	
    	return false;
    }
    
    public boolean isAutoFocusing()
    {
    	return mAutoFocusing;
    }
    
    public void autoFocusStart()
    {
    	mAutoFocusing = true;
    	
    	if (afcallback != null)
    		afcallback.autoFocusUpdate(false);
    }
    
    public void disable()
    {
    	enabled = false;
    }
    public void enable()
    {
    	enabled = true;
    }
    public boolean isEnabled()
    {
    	return enabled;
    }
    
    public void autoFocusComplete()
    {
    	lastAutoFocus = getCurTime();
    	mAutoFocusing = false;

    	if (afcallback != null)
    		afcallback.autoFocusUpdate(true);
    }
    
    private long getCurTime()
    {
    	 Calendar rightNow = Calendar.getInstance();
    	 return rightNow.getTimeInMillis();
    }
}
