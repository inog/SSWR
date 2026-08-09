package de.ingoreschke.sswr;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class WeekInfo extends ActivityIr {
	private static final String TAG = "WeekInfo";
	private AdView adView;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.week_info);
		
		if(isLiteVersion()){
        	adView = new AdView(this);
        	adView.setAdUnitId(AD_UNIT_ID_WEEKINFO);
        	adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, AdSize.FULL_WIDTH));

        	LinearLayout lw = findViewById(R.id.weekinfo_LinearLayout);
        	lw.addView(adView);
        	AdRequest request = new AdRequest.Builder().build();
        	adView.loadAd(request);
        }
		
		Bundle bundle = getIntent().getExtras();
		if (bundle == null){
			return;
		}
		int actualWeek = bundle.getInt("week");
		updateView(actualWeek);
	}
	
	@Override
	protected void onDestroy() {
		if (adView != null) {
	          adView.destroy();
	        }
		super.onDestroy();
	}

	@Override
	protected void onResume() {
      super.onResume();
      if (adView != null) {
        adView.resume();
      }
    }

    @Override
    protected void onPause() {
      if (adView != null) {
		  adView.pause();
	  }
      super.onPause();
    }

	private void updateView(int week){
		Log.d(TAG,"aktuelle Woche: "+ week);
		TextView tw_title = findViewById(R.id.weekinfo_title);
		TextView tw_text = findViewById(R.id.weekinfo_text);
		tw_title.setText(getTitletxt(week));
		tw_text.setText(getInfotxt(week));
	}
	

    private String getTitletxt(int week){
        String name = "info_title_week_" + week;
        int resId = getResources().getIdentifier(name, "string", getPackageName());
        return resId != 0 ? getString(resId) : getString(R.string.info_title_week_default);
    }
    
    private String getInfotxt(int week){
        String name = "info_text_week_" + week;
        int resId = getResources().getIdentifier(name, "string", getPackageName());
        return resId != 0 ? getString(resId) : getString(R.string.info_text_week_default);
    }
}
