/**
 * @author topali2
 */
package ch.epfl.sweng.radin.test;

import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.radin.LoginActivity;
import ch.epfl.sweng.radin.R;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

/**
 * @author topali2
 */
public class EspressoLoginActivity extends ActivityInstrumentationTestCase2<LoginActivity> {

	private final int mSleepTime = 100;
	
	public EspressoLoginActivity() {

		super(LoginActivity.class);

	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		getActivity();
	}

	public void testEspresso() {

		Espresso.onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.typeText("topali2"));

		Espresso.closeSoftKeyboard();

		try {
			Thread.sleep(mSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("youwish..."));

		Espresso.closeSoftKeyboard();
		try {
			Thread.sleep(mSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
	}

}
