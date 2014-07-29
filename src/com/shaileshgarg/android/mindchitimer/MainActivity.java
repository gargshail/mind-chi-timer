package com.shaileshgarg.android.mindchitimer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private TextView timerValue;
	
	private TextView stepValue;

	
	private Vibrator v ;
	
	Timer myTimer ;
	
	static final String STATE_SECS = "SECS";
	
	static final String STATE_STEP = "STEPS";
	
	int secs = 60;
	
	int step = 1;
	
	String stepDesc[] =  {"", "Breathe", "Chi-One", "Past -ve", "Past +ve", "Check-BEAT", "Choose-BEAT", "24HR-Preview", "Gratitude", "Done" };
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(savedInstanceState != null) {
			secs = savedInstanceState.getInt(STATE_SECS);
			
			step = savedInstanceState.getInt(STATE_STEP);
		}
		setContentView(R.layout.activity_main);
		
		timerValue = (TextView) findViewById(R.id.timerValue);
		
		stepValue = (TextView) findViewById(R.id.stepValue);
		
		v = (Vibrator)getSystemService( VIBRATOR_SERVICE);
		
		myTimer = new Timer();
		
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				secs--;
				if(secs == -1) {
					secs =59;
					v.vibrate(150l);
					step++;
				}
				

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						timerValue.setText("" + secs);						
						stepValue.setText(stepDesc[step]);
					}
				});
				if(step ==9 ) {
					myTimer.cancel();
				}
			}
		}, 0, 1000);	
	}
	@Override
	protected void onStop() {
		super.onStop();
		myTimer.cancel();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		myTimer.cancel();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	
		outState.putInt(STATE_SECS, secs);
		outState.putInt(STATE_STEP, step);
		
		super.onSaveInstanceState(outState);
	}
}
