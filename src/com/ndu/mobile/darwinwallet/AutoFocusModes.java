package com.ndu.mobile.darwinwallet;

public enum AutoFocusModes {
	OFF 			{ @Override
	public int getVal() { return 1; } },
	FocusOnTouch    { @Override
	public int getVal() { return 2; } },
	ON				{ @Override
	public int getVal() { return 3; } };
		

	public abstract int getVal();
	

	public static AutoFocusModes getMode(String value)
	{
		return getMode(Integer.parseInt(value));
	}
	public static AutoFocusModes getMode(int value)
	{
		for (AutoFocusModes mode : AutoFocusModes.values())
		{
			if (mode.getVal() == value)
				return mode;
		}
		
		return AutoFocusModes.FocusOnTouch;
	}
}
