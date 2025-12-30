package de.ingoreschke.sswr;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ActivityIr extends AppCompatActivity {
    protected static final String AD_UNIT_ID_MAIN = "ca-app-pub-1283865206002218/4256611081";
    protected static final String AD_UNIT_ID_WEEKINFO = "ca-app-pub-1283865206002218/8768072283";
    protected static final int FULL_VERSION_REQUIRED = -1;

    protected static final String PREFS_SETTINGS = "app_settings";
    protected static final String KEY_IS_PRO = "is_pro_version";

    public boolean isLiteVersion() {
        SharedPreferences prefs = getSharedPreferences(PREFS_SETTINGS, Context.MODE_PRIVATE);
        // If the user has NOT purchased the Pro version, then it is the Lite version.
        return !prefs.getBoolean(KEY_IS_PRO, false);
    }
}
