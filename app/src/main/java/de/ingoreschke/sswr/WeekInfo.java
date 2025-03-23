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
        	adView.setAdSize(AdSize.SMART_BANNER);

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
        return switch (week) {
            case 1 -> getString(R.string.info_title_week_1);
            case 2 -> getString(R.string.info_title_week_2);
            case 3 -> getString(R.string.info_title_week_3);
            case 4 -> getString(R.string.info_title_week_4);
            case 5 -> getString(R.string.info_title_week_5);
            case 6 -> getString(R.string.info_title_week_6);
            case 7 -> getString(R.string.info_title_week_7);
            case 8 -> getString(R.string.info_title_week_8);
            case 9 -> getString(R.string.info_title_week_9);
            case 10 -> getString(R.string.info_title_week_10);
            case 11 -> getString(R.string.info_title_week_11);
            case 12 -> getString(R.string.info_title_week_12);
            case 13 -> getString(R.string.info_title_week_13);
            case 14 -> getString(R.string.info_title_week_14);
            case 15 -> getString(R.string.info_title_week_15);
            case 16 -> getString(R.string.info_title_week_16);
            case 17 -> getString(R.string.info_title_week_17);
            case 18 -> getString(R.string.info_title_week_18);
            case 19 -> getString(R.string.info_title_week_19);
            case 20 -> getString(R.string.info_title_week_20);
            case 21 -> getString(R.string.info_title_week_21);
            case 22 -> getString(R.string.info_title_week_22);
            case 23 -> getString(R.string.info_title_week_23);
            case 24 -> getString(R.string.info_title_week_24);
            case 25 -> getString(R.string.info_title_week_25);
            case 26 -> getString(R.string.info_title_week_26);
            case 27 -> getString(R.string.info_title_week_27);
            case 28 -> getString(R.string.info_title_week_28);
            case 29 -> getString(R.string.info_title_week_29);
            case 30 -> getString(R.string.info_title_week_30);
            case 31 -> getString(R.string.info_title_week_31);
            case 32 -> getString(R.string.info_title_week_32);
            case 33 -> getString(R.string.info_title_week_33);
            case 34 -> getString(R.string.info_title_week_34);
            case 35 -> getString(R.string.info_title_week_35);
            case 36 -> getString(R.string.info_title_week_36);
            case 37 -> getString(R.string.info_title_week_37);
            case 38 -> getString(R.string.info_title_week_38);
            case 39 -> getString(R.string.info_title_week_39);
            case 40 -> getString(R.string.info_title_week_40);
            case 41 -> getString(R.string.info_title_week_41);
            case 42 -> getString(R.string.info_title_week_42);
            default -> getString(R.string.info_title_week_default);
        };
	}
	
	private String getInfotxt(int week){
        return switch (week) {
            case 1 -> getString(R.string.info_text_week_1);
            case 2 -> getString(R.string.info_text_week_2);
            case 3 -> getString(R.string.info_text_week_3);
            case 4 -> getString(R.string.info_text_week_4);
            case 5 -> getString(R.string.info_text_week_5);
            case 6 -> getString(R.string.info_text_week_6);
            case 7 -> getString(R.string.info_text_week_7);
            case 8 -> getString(R.string.info_text_week_8);
            case 9 -> getString(R.string.info_text_week_9);
            case 10 -> getString(R.string.info_text_week_10);
            case 11 -> getString(R.string.info_text_week_11);
            case 12 -> getString(R.string.info_text_week_12);
            case 13 -> getString(R.string.info_text_week_13);
            case 14 -> getString(R.string.info_text_week_14);
            case 15 -> getString(R.string.info_text_week_15);
            case 16 -> getString(R.string.info_text_week_16);
            case 17 -> getString(R.string.info_text_week_17);
            case 18 -> getString(R.string.info_text_week_18);
            case 19 -> getString(R.string.info_text_week_19);
            case 20 -> getString(R.string.info_text_week_20);
            case 21 -> getString(R.string.info_text_week_21);
            case 22 -> getString(R.string.info_text_week_22);
            case 23 -> getString(R.string.info_text_week_23);
            case 24 -> getString(R.string.info_text_week_24);
            case 25 -> getString(R.string.info_text_week_25);
            case 26 -> getString(R.string.info_text_week_26);
            case 27 -> getString(R.string.info_text_week_27);
            case 28 -> getString(R.string.info_text_week_28);
            case 29 -> getString(R.string.info_text_week_29);
            case 30 -> getString(R.string.info_text_week_30);
            case 31 -> getString(R.string.info_text_week_31);
            case 32 -> getString(R.string.info_text_week_32);
            case 33 -> getString(R.string.info_text_week_33);
            case 34 -> getString(R.string.info_text_week_34);
            case 35 -> getString(R.string.info_text_week_35);
            case 36 -> getString(R.string.info_text_week_36);
            case 37 -> getString(R.string.info_text_week_37);
            case 38 -> getString(R.string.info_text_week_38);
            case 39 -> getString(R.string.info_text_week_39);
            case 40 -> getString(R.string.info_text_week_40);
            case 41 -> getString(R.string.info_text_week_41);
            case 42 -> getString(R.string.info_text_week_42);
            default -> getString(R.string.info_text_week_default);
        };
	}
}
