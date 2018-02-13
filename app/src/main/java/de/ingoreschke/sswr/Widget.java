package de.ingoreschke.sswr;

import java.util.Calendar;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider {
	private static final String TAG = "Widget";
	private static final String PREFS_NAME = "et_date";
	private static final String KEY_ET_YEAR = "etYear";
	private static final String KEY_ET_MONTH = "etMonth";
	private static final String KEY_ET_DAY= "etDAY";
	private SharedPreferences et;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		Log.d(TAG, "onUpdate");
		
		final int N = appWidgetIds.length;
		for (int i=0; i<N; i++) {
			int widgetId = appWidgetIds[i];
			
			et = context.getSharedPreferences(PREFS_NAME, 0);
			int etYear 	= et.getInt(KEY_ET_YEAR,0);
	    	int etMonth = et.getInt(KEY_ET_MONTH, 0);
	    	int etDay	= et.getInt(KEY_ET_DAY, 0);
	        String week = context.getString(R.string.widget_default);
	        String xteWeek = "";
	        
	        SchwangerschaftsDate sd = calculateSswDate(etYear,etMonth,etDay);
	        
			if (sd != null){
		        week = String.valueOf(sd.getWeeksUntilNow()) + " + " + String.valueOf(sd.getRestOfWeekUntilNow());
		        xteWeek = String.valueOf(sd.getXteWeek()) + context.getString(R.string.str_xteWeek_suffix);
			}
	        
			
	        // Create an Intent to launch Activity
	        Intent intent = new Intent(context, SswrMainActivity.class);
	        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
	
	        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			views.setTextViewText(R.id.widgetTV02, week);
			views.setTextViewText(R.id.widgetTV03, xteWeek);
			views.setOnClickPendingIntent(R.id.widgetCanvas, pendingIntent);

			appWidgetManager.updateAppWidget(widgetId, views);
		}
	}
	
	@Override
	public void onEnabled(Context context){
		
	}
	
	private SchwangerschaftsDate calculateSswDate(int etYear, int etMonth, int etDay){
		Calendar c = Calendar.getInstance();
		Date today = c.getTime();
		c.set(etYear, etMonth, etDay);
		Date birthDate = c.getTime();
		try{
			return new SchwangerschaftsDate(today, birthDate);	
		}catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
			SharedPreferences.Editor editor = et.edit();
			editor.clear();
			editor.commit();
			Log.d(TAG, "Prefs deleted");
			return null;
		}		
	}
}
