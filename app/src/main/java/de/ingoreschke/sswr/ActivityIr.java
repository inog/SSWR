package de.ingoreschke.sswr;


import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ActivityIr extends AppCompatActivity {
    protected static final String AD_UNIT_ID_MAIN = "ca-app-pub-1283865206002218/4256611081";//"a14e80f4900e534";
    protected static final String AD_UNIT_ID_WEEKINFO = "ca-app-pub-1283865206002218/8768072283";
    protected static final int FULL_VERSION_REQUIRED = -1;

	public boolean isLiteVersion() {
		String TAG = "ActivityIr";
		Log.d(TAG, getPackageName());
        return getPackageName().toLowerCase().contains("lite");
    }
}
