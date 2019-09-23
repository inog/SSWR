package de.ingoreschke.sswr.utils;

public class Util {
	public static int safeLongToInt(long number) {
	    if (Integer.MIN_VALUE > number || number > Integer.MAX_VALUE ) {
	        throw new IllegalArgumentException
	            (number + " cannot be cast to int without changing its value.");
	    }
	    return (int) number ;
	}
}
