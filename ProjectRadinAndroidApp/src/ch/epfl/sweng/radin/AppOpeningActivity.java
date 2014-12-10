package ch.epfl.sweng.radin;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * 
 * @author topali2
 *
 */
public class AppOpeningActivity extends Activity {

	private static final long DELAY = 2000;
	private boolean scheduled = false;
	private Timer splashTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_opening);

		splashTimer = new Timer();
		splashTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				AppOpeningActivity.this.finish();
				startActivity(new Intent(AppOpeningActivity.this, LoginActivity.class));
			}
		}, DELAY);
		scheduled = true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (scheduled) {

			splashTimer.cancel();
		}
		splashTimer.purge();
	}

}
