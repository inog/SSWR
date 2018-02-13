package de.ingoreschke.sswr;


import android.app.Activity;
import android.util.Log;

public abstract class ActivityIr extends Activity {
	private String TAG = "ActivityIr";
	protected static final String AD_UNIT_ID_MAIN ="ca-app-pub-1283865206002218/4256611081";//"a14e80f4900e534"; 
	protected static final String AD_UNIT_ID_WEEKINFO ="ca-app-pub-1283865206002218/8768072283"; 
	protected static final int FULL_VERSION_REQUIRED = -1;
	
	public boolean isLiteVersion() {
		Log.d(TAG, getPackageName());
		return getPackageName().toLowerCase().contains("lite"); 
	}
	
}
