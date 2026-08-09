package de.ingoreschke.sswr.utils;

public class Util {
	public static int safeLongToInt(long number) {
	    if (Integer.MIN_VALUE > number || number > Integer.MAX_VALUE ) {
	        throw new IllegalArgumentException
	            (number + " cannot be cast to int without changing its value.");
	    }
	    return (int) number ;
	}

	public static int getFetusDrawableResId(android.content.Context context, int week) {
		if (week < 1) week = 1;
		if (week > 42) week = 42;
		String name = "fetus_week_" + week;
		int resId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
		if (resId == 0) {
			resId = context.getResources().getIdentifier("fetus_week_1", "drawable", context.getPackageName());
		}
		return resId;
	}
}
