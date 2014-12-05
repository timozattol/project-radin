package ch.epfl.sweng.radin;
import ch.epfl.sweng.radin.storage.managers.StorageManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
/**
 *
 * @author Fabien Zellweger
 * Login activity, to log an user, if right login, can go further in the app, or
 * give the opportunity to register.
 *
 */
public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button loginBtn = (Button) findViewById(R.id.loginButton);
		loginBtn.setOnClickListener(loginActivityButtonListener);
		Button newAccountBtn = (Button) findViewById(R.id.createAcountButton);
		newAccountBtn.setOnClickListener(loginActivityButtonListener);
		
		StorageManager.init(this);
	}
	private OnClickListener loginActivityButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int selectedId = v.getId();
			Intent displayActivityIntent = null;
			
			switch (selectedId){
				case R.id.loginButton:
					displayActivityIntent = new Intent(v.getContext(), HomeActivity.class);
					break;
				case R.id.createAcountButton:
					displayActivityIntent = new Intent(v.getContext(), RegisterActivity.class);
					break;
				default:
					Toast.makeText(v.getContext(), "Error, this button shouldn't exist!",
						Toast.LENGTH_SHORT).show();				
			}
			if (!(displayActivityIntent == null)) {
				startActivity(displayActivityIntent);	
			}
		}		
	};
}