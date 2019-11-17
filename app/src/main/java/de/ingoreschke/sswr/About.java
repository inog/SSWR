package de.ingoreschke.sswr;


import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class About extends Activity implements OnClickListener{
	private TextView tw_promo_nike;
	private TextView tw_promo_robert;
	private TextView tw_contact;
	private Button btn_babyinmom1;
	private MediaPlayer mp;
	private int sound1;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		tw_promo_nike = (TextView) findViewById(R.id.about_promo_nike);
		tw_promo_nike.setOnClickListener(this);
		tw_promo_robert = (TextView) findViewById(R.id.about_promo_robert);
		tw_promo_robert.setOnClickListener(this);
		tw_contact = (TextView) findViewById(R.id.about_contact);
		tw_contact.setOnClickListener(this);
		btn_babyinmom1 = (Button) findViewById(R.id.about_btn_babyinmom1);
		btn_babyinmom1.setOnClickListener(this);
		//sound1 = R.raw.babyinmom005;
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		stopSound();
	}
	
	public void onClick(View v) {
		int id =v.getId();
		if (id == R.id.about_promo_robert) {
			final Intent ri = new Intent(Intent.ACTION_SEND);
			ri.setType("text/plain");
			ri.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_promo_robert_email_sub));
			ri.putExtra(Intent.EXTRA_TEXT, getString(R.string.about_promo_robert_email_txt));
			ri.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.about_promo_robert_email)});
			startActivity(Intent.createChooser(ri, "Send email..."));
		} else if (id == R.id.about_contact) {
			final Intent ci = new Intent(Intent.ACTION_SEND);
			ci.setType("text/plain");
			ci.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_contact_email_sub));
			ci.putExtra(Intent.EXTRA_TEXT, getString(R.string.about_contact_email_text));
			ci.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.about_contact_email)});
			startActivity(Intent.createChooser(ci, "Send email..."));
		} else if (id == R.id.about_btn_babyinmom1){
			toggleSoundOnOff(sound1);
		} 
    }
	
	private void toggleSoundOnOff(int resId){
		if (mp != null && mp.isPlaying()){
			stopSound();
			btn_babyinmom1.setText(R.string.about_btn_babyinmon1);
		}else{
			playSound(resId);
			btn_babyinmom1.setText(R.string.about_btn_babyinmon1_stop);
		}
	}
	
	private void playSound(int resId){
		mp = MediaPlayer.create(this, resId);
		mp.setLooping(true);
		mp.start();
	}
	
	private void stopSound(){
		if (mp != null){
			mp.stop();
			mp.release();
			mp=null;
		}
	}
}
