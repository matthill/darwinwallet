package com.ndu.mobile.darwinwallet;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class CurrencyInfo implements Parcelable {

	private String description;		// e.g. US Dollar
	private String code;			// e.g. us
	private String symbol;			// e.g. $
	
	public CurrencyInfo(Context context, String currency_code)
	{
		String[] codes = context.getResources().getStringArray(R.array.language_codes);
		String[] descriptions = context.getResources().getStringArray(R.array.language_names);
		String[] symbols = context.getResources().getStringArray(R.array.language_signs);

		//int matchingIndex = 0;
		for (int i = 0; i <  codes.length; i++)
		{
			String c = codes[i];
			if (c.equals(currency_code))
			{
				this.code = currency_code;
				this.description = descriptions[i];
				this.symbol = symbols[i];
			}
		}

	}


	public String getDescription() {
		return description;
	}

	public String getCode() {
		return code;
	}

	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public boolean equals(Object o) {
		if (((CurrencyInfo) o).getCode() == this.getCode())
			return true;
		
		return false;
	};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(description);
		dest.writeString(code);
		dest.writeString(symbol);
		
	}

    public static final Parcelable.Creator<CurrencyInfo> CREATOR = new Parcelable.Creator<CurrencyInfo>() {
	@Override
	public CurrencyInfo createFromParcel(Parcel in) {
	    return new CurrencyInfo(in);
	}
	
	@Override
	public CurrencyInfo[] newArray(int size) {
	    return new CurrencyInfo[size];
	}
	};
	
	private CurrencyInfo(Parcel in) {
		description = in.readString();
		code = in.readString();
		symbol = in.readString();
	}
}
