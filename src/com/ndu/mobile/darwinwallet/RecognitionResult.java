package com.ndu.mobile.darwinwallet;

public class RecognitionResult
{
	boolean match_found = false;
	int bill_value;
	boolean front;
	int confidence;
	
	public RecognitionResult(String rawData)
	{
		if ((rawData == null) || (rawData.equals("")))
		{
			match_found = false;
			bill_value = 0;
			front = false;
			confidence = 0;
		}
		else
		{
			match_found = true; 
			String[] splits = rawData.split(",");
			String bill_val_str;
			if (splits[0].contains("f"))
			{
				bill_val_str = splits[0].substring(0, splits[0].indexOf('f'));
				front = true;
			}
			else 
			{
				bill_val_str = splits[0].substring(0, splits[0].indexOf('b'));
				front = false;
			}
			bill_value = Integer.parseInt(bill_val_str);
			confidence = Integer.parseInt(splits[1]);
		}
	}
	
}