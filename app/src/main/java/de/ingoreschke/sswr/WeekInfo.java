package de.ingoreschke.sswr;

//import com.google.ads.AdRequest;
//import com.google.ads.AdSize;
//import com.google.ads.AdView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class WeekInfo extends ActivityIr {
	private static final String TAG = "WeekInfo";
	private TextView tw_title;
	private TextView tw_text;
	private AdView adView;
	private int actualWeek;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.week_info);
		
		if(isLiteVersion()){
        	adView = new AdView(this);
        	adView.setAdUnitId(AD_UNIT_ID_WEEKINFO);
        	adView.setAdSize(AdSize.SMART_BANNER);
        	//add Adview to hierachy
        	LinearLayout lw = (LinearLayout) findViewById(R.id.weekinfo_LinearLayout);
        	lw.addView(adView);
        	//create an adRequest
        	AdRequest request = new AdRequest.Builder().build();
        	//start loading the ad in the background
        	adView.loadAd(request);
        }
		
		Bundle b = getIntent().getExtras();
		if (b == null){
			return;
		}
		actualWeek = b.getInt("week");
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
		tw_title = (TextView) findViewById(R.id.weekinfo_title);
		tw_text = (TextView) findViewById(R.id.weekinfo_text);
		tw_title.setText(getTitletxt(week));
		tw_text.setText(getInfotxt(week));
	}
	
	private String getTitletxt(int week){
		switch (week) {
		case 1:
			return getString(R.string.info_title_week_1);
		case 2:
			return getString(R.string.info_title_week_2);
		case 3:
			return getString(R.string.info_title_week_3);
		case 4:
			return getString(R.string.info_title_week_4);
		case 5:
			return getString(R.string.info_title_week_5);
		case 6:
			return getString(R.string.info_title_week_6);
		case 7:
			return getString(R.string.info_title_week_7);
		case 8:
			return getString(R.string.info_title_week_8);
		case 9:
			return getString(R.string.info_title_week_9);
		case 10:
			return getString(R.string.info_title_week_10);
		case 11:
			return getString(R.string.info_title_week_11);
		case 12:
			return getString(R.string.info_title_week_12);
		case 13:
			return getString(R.string.info_title_week_13);
		case 14:
			return getString(R.string.info_title_week_14);
		case 15:
			return getString(R.string.info_title_week_15);
		case 16:
			return getString(R.string.info_title_week_16);
		case 17:
			return getString(R.string.info_title_week_17);
		case 18:
			return getString(R.string.info_title_week_18);
		case 19:
			return getString(R.string.info_title_week_19);
		case 20:
			return getString(R.string.info_title_week_20);
		case 21:
			return getString(R.string.info_title_week_21);
		case 22:
			return getString(R.string.info_title_week_22);
		case 23:
			return getString(R.string.info_title_week_23);
		case 24:
			return getString(R.string.info_title_week_24);
		case 25:
			return getString(R.string.info_title_week_25);
		case 26:
			return getString(R.string.info_title_week_26);
		case 27:
			return getString(R.string.info_title_week_27);
		case 28:
			return getString(R.string.info_title_week_28);
		case 29:
			return getString(R.string.info_title_week_29);
		case 30:
			return getString(R.string.info_title_week_30);
		case 31:
			return getString(R.string.info_title_week_31);
		case 32:
			return getString(R.string.info_title_week_32);
		case 33:
			return getString(R.string.info_title_week_33);
		case 34:
			return getString(R.string.info_title_week_34);
		case 35:
			return getString(R.string.info_title_week_35);
		case 36:
			return getString(R.string.info_title_week_36);
		case 37:
			return getString(R.string.info_title_week_37);
		case 38:
			return getString(R.string.info_title_week_38);
		case 39:
			return getString(R.string.info_title_week_39);
		case 40:
			return getString(R.string.info_title_week_40);	
		case 41:
			return getString(R.string.info_title_week_41);
		case 42:
			return getString(R.string.info_title_week_42);	
			
		default:
			return getString(R.string.info_title_week_default);	
		}
	}
	
	private String getInfotxt(int week){
		switch (week) {
		case 1:
			return getString(R.string.info_text_week_1);
		case 2:
			return getString(R.string.info_text_week_2);
		case 3:
			return getString(R.string.info_text_week_3);
		case 4:
			return getString(R.string.info_text_week_4);
		case 5:
			return getString(R.string.info_text_week_5);
		case 6:
			return getString(R.string.info_text_week_6);
		case 7:
			return getString(R.string.info_text_week_7);
		case 8:
			return getString(R.string.info_text_week_8);
		case 9:
			return getString(R.string.info_text_week_9);
		case 10:
			return getString(R.string.info_text_week_10);
		case 11:
			return getString(R.string.info_text_week_11);
		case 12:
			return getString(R.string.info_text_week_12);
		case 13:
			return getString(R.string.info_text_week_13);
		case 14:
			return getString(R.string.info_text_week_14);
		case 15:
			return getString(R.string.info_text_week_15);
		case 16:
			return getString(R.string.info_text_week_16);
		case 17:
			return getString(R.string.info_text_week_17);
		case 18:
			return getString(R.string.info_text_week_18);
		case 19:
			return getString(R.string.info_text_week_19);
		case 20:
			return getString(R.string.info_text_week_20);
		case 21:
			return getString(R.string.info_text_week_21);
		case 22:
			return getString(R.string.info_text_week_22);
		case 23:
			return getString(R.string.info_text_week_23);
		case 24:
			return getString(R.string.info_text_week_24);
		case 25:
			return getString(R.string.info_text_week_25);
		case 26:
			return getString(R.string.info_text_week_26);
		case 27:
			return getString(R.string.info_text_week_27);
		case 28:
			return getString(R.string.info_text_week_28);
		case 29:
			return getString(R.string.info_text_week_29);
		case 30:
			return getString(R.string.info_text_week_30);
		case 31:
			return getString(R.string.info_text_week_31);
		case 32:
			return getString(R.string.info_text_week_32);
		case 33:
			return getString(R.string.info_text_week_33);
		case 34:
			return getString(R.string.info_text_week_34);
		case 35:
			return getString(R.string.info_text_week_35);
		case 36:
			return getString(R.string.info_text_week_36);
		case 37:
			return getString(R.string.info_text_week_37);
		case 38:
			return getString(R.string.info_text_week_38);
		case 39:
			return getString(R.string.info_text_week_39);
		case 40:
			return getString(R.string.info_text_week_40);
		case 41:
			return getString(R.string.info_text_week_41);
		case 42:
			return getString(R.string.info_text_week_42);
		
		default:
			return getString(R.string.info_text_week_default);	
		}
	}
}
