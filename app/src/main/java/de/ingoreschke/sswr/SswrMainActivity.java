package de.ingoreschke.sswr;

import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import de.ingoreschke.sswr.utils.Util;


public class SswrMainActivity extends ActivityIr {
	private static final String TAG = "SswrMainActivity";
	private static final String PREFS_NAME = "et_date";
	private static final String KEY_ET_YEAR = "etYear";
	private static final String KEY_ET_MONTH = "etMonth";
	private static final String KEY_ET_DAY= "etDAY";
	
	private TextView mainIntro;
    private TextView currentDateDisplay;
	private TextView etDateDisplay;
	
	private TextView etDaysToBirthDisplay;
	private TextView etDaysUntilNowDisplay;
	private TextView etWeekPlusDaysDisplay;
	private TextView etXteWeekDisplay;
	private TextView etXteMonth;
	
	private AdView adView;
	
	private Button btnEtDate;
	private Button btnTimemachine;
	private Button btnInfoText;
    private int etYear, todayYear;
    private int etMonth, todayMonth;
    private int etDay, todayDay;
    
    private static final int DATE_DIALOG_ID = 0;
    private static final int PRE_CURRENTDATE_DIALOG_ID = 1;
    private static final int CURRENTDATE_DIALOG_ID = 2;
    
    private SchwangerschaftsDate sd;
    
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i(TAG, "Sswr started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_sswr);
        if(isLiteVersion()){
        	//create an ad
        	adView = new AdView(this);
        	adView.setAdUnitId(AD_UNIT_ID_MAIN);
        	adView.setAdSize(AdSize.SMART_BANNER);
        	//add Adview to hierachy
        	LinearLayout lw = (LinearLayout) findViewById(R.id.linearlayout_wrapper);
        	lw.addView(adView);
        	//create an adRequest
        	AdRequest request = new AdRequest.Builder().build();
        	//start loading the ad in the background
        	adView.loadAd(request);
        }
        
        //set ViewElements
        mainIntro = (TextView) findViewById(R.id.main_intro);
        currentDateDisplay = (TextView) findViewById(R.id.currentDate);
        etDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        etDaysToBirthDisplay = (TextView) findViewById(R.id.str_daysToBirth);
        etDaysUntilNowDisplay = (TextView) findViewById(R.id.str_daysUntilNow);
        etWeekPlusDaysDisplay = (TextView) findViewById(R.id.str_weekPlusDays);
        etXteWeekDisplay = (TextView) findViewById(R.id.str_xteWeek);
        etXteMonth = (TextView) findViewById(R.id.str_xteMonth);
        btnEtDate = (Button) findViewById(R.id.main_btnEtDate);   		
    	btnTimemachine = (Button) findViewById(R.id.main_btnTimemachine);
    	btnInfoText = (Button) findViewById(R.id.main_btnWeekInfo);
    	
    	
        
        //set today 
    	final Calendar c = Calendar.getInstance();
    	todayYear = c.get(Calendar.YEAR);
    	todayMonth = c.get(Calendar.MONTH);
    	todayDay = c.get(Calendar.DAY_OF_MONTH);
        
        //Restore stored e.t. (birth) or set default
        SharedPreferences et = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        etYear 	= et.getInt(KEY_ET_YEAR, 0);
        etMonth = et.getInt(KEY_ET_MONTH, 0);
        etDay	= et.getInt(KEY_ET_DAY, 0);
        if(etYear != 0){
        	mainIntro.setText(""); //delete intro text 
        	updateDateDisplay(); 
        	calculateSswDate();
        	btnTimemachine.setEnabled(true);
        }else{
        	//the first use
        	btnEtDate.setText(R.string.pickDateFirstUse);
        	btnTimemachine.setEnabled(false);
        }
        updateCurrentDateDisplay(); 
    }
    
    @Override
    protected Dialog onCreateDialog(int id){
    	switch(id){
    	case FULL_VERSION_REQUIRED:
    		return new AlertDialog.Builder(this)
    			.setIcon(android.R.drawable.ic_dialog_alert)
    			.setTitle(R.string.full_version_required)
    			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	    			public void onClick(DialogInterface dialog, int whichButton) {
	    				Toast.makeText(SswrMainActivity.this, "Replace this toast with an intent to start the android market to buy your full version.", Toast.LENGTH_SHORT).show(); 
	    			} 
	    		}) 
	    		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
	    		{ 
	    			public void onClick(DialogInterface dialog, int whichButton) {
	    				dialog.dismiss();
	    			} 
	    		}
	    		).create();
    		
    	case DATE_DIALOG_ID:
    		return new DatePickerDialog(this, mDateSetListener,todayYear, todayMonth, todayDay); //todayYear, todayMonth, todayDay for das erste mal aufrufen.
    	case CURRENTDATE_DIALOG_ID:
    		Log.d(TAG, "auf Button showDialog(CURRENTDATE_DIALOG_ID) gestartet ");
    		return new DatePickerDialog(this, mCurDateSetListener, todayYear, todayMonth, todayDay);
    	case PRE_CURRENTDATE_DIALOG_ID:
    		Log.d(TAG, "auf Button Zeitmaschine geklickt");
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setTitle(R.string.alertboxTimemachine_title);
    		builder.setMessage(R.string.alertboxTimemachine_text);
    		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					showDialog(CURRENTDATE_DIALOG_ID);
				}
			});
    		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {	
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
    		return builder.create();
    	}
    	return null;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	int id = item.getItemId();
    	if (id == R.id.menu_et_date){
    		showDialog(DATE_DIALOG_ID);
    		return true;	
    	} else if (id == R.id.menu_et_timemaschine){
    		showDialog(PRE_CURRENTDATE_DIALOG_ID);
    		return true;
    	} else if (id == R.id.about) {
			startActivity(new Intent(this, About.class));
			return true;
		} else if (id == R.id.menu_feedback){
			final Intent ci = new Intent(Intent.ACTION_SEND);
			ci.setType("text/html");
			ci.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_contact_email_sub));
			ci.putExtra(Intent.EXTRA_TEXT, getString(R.string.about_contact_email_text));
			ci.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.about_contact_email)});
			startActivity(Intent.createChooser(ci, getString(R.string.about_contact_email_Intent)));
		} else if (id == R.id.menu_tellAFriend){
			String title = getString(R.string.tellAFriendSubject);
			String text = "";
			if (sd != null){
				text =  getString(R.string.tellAFriendText) + " " + String.valueOf(sd.getXteWeek()) + getString(R.string.str_xteWeek_suffix) +".";
			}
			text += getString(R.string.tellAFriendText2);
			if (isLiteVersion()){
				text += getString(R.string.tellAFriendLinkLight);
			}else{
				text += getString(R.string.tellAFriendLinkFull);
			}
			
			final Intent tellAFriendIntent = new Intent(Intent.ACTION_SEND);
			tellAFriendIntent.setType("text/plain");
			tellAFriendIntent.putExtra(Intent.EXTRA_SUBJECT, title);
			tellAFriendIntent.putExtra(Intent.EXTRA_TEXT, text);
			startActivity(Intent.createChooser(tellAFriendIntent, getString(R.string.tellAFriend_title)));
		}else if (id == R.id.menu_weekinfo){
			callWeekInfo();
		}
    		//More items go here (if any) ...
    	return false;
    }
    
    @Override
    protected void onStop(){
       super.onStop();
       saveEtDate(etYear, etMonth, etDay);
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

    public void onClick(View v) {
    	int id = v.getId();
    	if (id == R.id.main_btnEtDate || 
    		id == R.id.main_et_row) {
			showDialog(DATE_DIALOG_ID);
		}
    	else if (id == R.id.main_btnTimemachine){
    		if (isTimeMachineMode()){
    			showDialog(CURRENTDATE_DIALOG_ID);
    		}else{    			
    			showDialog(PRE_CURRENTDATE_DIALOG_ID);
    		}
    	}
    	else if (id == R.id.main_btnWeekInfo || 
    			 id == R.id.main_xteWeek_row) {
			callWeekInfo();
		}
    	
    	else {
		}
    }
    
    private void callWeekInfo(){
    	if (this.sd != null){
    		long weekl = sd.getXteWeek();
    		int week = Util.safeLongToInt(weekl);
    		Intent i = new Intent(this, WeekInfo.class);
    		i.putExtra("week", week);
    		startActivity(i);
    	}else{
    		Toast.makeText(this, R.string.errorNoDate, Toast.LENGTH_LONG).show();
    	}    	
    }
    
    private void updateCurrentDateDisplay(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(todayDay).append(".").append(todayMonth + 1).append(".").append(todayYear);
    	currentDateDisplay.setText(sb.toString());
    }
    
    private void updateDateDisplay(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(etDay).append(".").append(etMonth + 1).append(".").append(etYear);
    	etDateDisplay.setText(sb.toString());
    }
    
    //the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			etYear = year;
			etMonth = monthOfYear;
			etDay = dayOfMonth;
			saveEtDate(year, monthOfYear, dayOfMonth);
			updateDateDisplay();
			calculateSswDate();				
		}
	};
	
	// callback for CurrentDatePicker
	private DatePickerDialog.OnDateSetListener mCurDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			 //set today 
	    	final Calendar c = Calendar.getInstance();
	    	todayYear = year;
	    	todayMonth = monthOfYear;
	    	todayDay = dayOfMonth;
			if (year != c.get(Calendar.YEAR) || monthOfYear != c.get(Calendar.MONTH)  || dayOfMonth != c.get(Calendar.DAY_OF_MONTH)){
				btnEtDate.setEnabled(false);
				mainIntro.setText(R.string.str_intro_timemachinemode);
			}else{
				btnEtDate.setEnabled(true);
				mainIntro.setText("");
			}
			updateCurrentDateDisplay();
			calculateSswDate();
		}
	};
	
	private void calculateSswDate(){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();	
		c1.set(todayYear, todayMonth, todayDay);
		c2.set(etYear, etMonth, etDay);
		Date today = c1.getTime();
		Date birthDate = c2.getTime();
		try{
			this.sd = new SchwangerschaftsDate(today, birthDate);
			showResult();
			if(!isTimeMachineMode()){
				updateWidget();				
			}
		}catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
			String error = "";
			if (e.getMessage().equals(SchwangerschaftsDate.DATE1_TOO_SMALL)){
				error = getString(R.string.errorIllegalDate_d1TooSmall);
			}else if(e.getMessage().equals(SchwangerschaftsDate.DATE2_TOO_BIG)){
				error = getString(R.string.errorIllegalDate_d2TooBig);
			}else{
				error = e.getMessage();
			}
			showError(error);
		}		
	}
    
	private void showResult(){
		etDaysToBirthDisplay.setText(String.valueOf(sd.getDaysToBirth()));
		etDaysUntilNowDisplay.setText(String.valueOf(sd.getDaysUntilNow()));
		etWeekPlusDaysDisplay.setText(String.valueOf(sd.getWeeksUntilNow()) + " + " + String.valueOf(sd.getRestOfWeekUntilNow()));
		
		String w = String.valueOf(sd.getXteWeek());
		w = w + getString(R.string.str_xteWeek_suffix);
		etXteWeekDisplay.setText(w);
		String m = String.valueOf(sd.getXteMonth());
		m = m + getString(R.string.str_xteMonth_suffix);
		etXteMonth.setText(m);
	}
	
	private void showError(String error){
		
		
		new AlertDialog.Builder(this)
			.setTitle(getString(R.string.errorTitle))
			.setMessage(error)
			.setCancelable(true)
			.setNeutralButton(android.R.string.cancel, 
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				})
			.show();
	}

	private void updateWidget(){
		Log.d(TAG,"updateWidget");
		String week = String.valueOf(sd.getWeeksUntilNow()) + " + " + String.valueOf(sd.getRestOfWeekUntilNow());
		String xteWeek = String.valueOf(sd.getXteWeek()) + getString(R.string.str_xteWeek_suffix);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this); //this is context
		RemoteViews views = new RemoteViews(this.getPackageName(),R.layout.widget_layout);
		//Perform update on the view
		views.setTextViewText(R.id.widgetTV02, week);
		views.setTextViewText(R.id.widgetTV03, xteWeek);
		appWidgetManager.updateAppWidget(new ComponentName(this,Widget.class), views);
	}
	
	
	private boolean saveEtDate(int year, int month, int day){
		Log.d(TAG,"saveEtDate is called");
    	if (year != 0){
     	   // We need an Editor object to make preference changes.
     	   // All objects are from android.context.Context
     	   SharedPreferences et = getSharedPreferences(PREFS_NAME, 0);
     	   SharedPreferences.Editor editor = et.edit();
     	   editor.putInt(KEY_ET_YEAR, year);
     	   editor.putInt(KEY_ET_MONTH, month);
     	   editor.putInt(KEY_ET_DAY, day);
     	  
     	   Log.d(TAG,"year:" + year + " month:"+month +  " day:" +day);
     	   
     	   // Commit the edits!
     	   editor.commit();
     	   return true;
        }else{
        	return false;
        }
    }
	
	private boolean isTimeMachineMode(){
		Calendar c = Calendar.getInstance();
    	if (todayYear == c.get(Calendar.YEAR) &&
    	    todayMonth == c.get(Calendar.MONTH) &&
    	    todayDay == c.get(Calendar.DAY_OF_MONTH)){
    		return false;
    	}else{
    		return true;
    	}
	}
}