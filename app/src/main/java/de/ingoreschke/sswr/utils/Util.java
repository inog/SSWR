package de.ingoreschke.sswr.utils;

public class Util {
	public static int safeLongToInt(long l) {
	    if (Integer.MIN_VALUE > l || l > Integer.MAX_VALUE ) {
	        throw new IllegalArgumentException
	            (l + " cannot be cast to int without changing its value.");
	    }
	    return (int) l;
	}
}
