/**
 * 
 */
package ch.epfl.sweng.radin.test;

import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.radin.SettingsActivity;
import ch.epfl.sweng.radin.R;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;


/**
 * @author julied20
 *
 */
public class EspressoSettingsActivity extends ActivityInstrumentationTestCase2<SettingsActivity> {

	
	public EspressoSettingsActivity() {

		super(SettingsActivity.class);

	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		getActivity();
	}

	public void testLogout() {
		Espresso.onView(ViewMatchers.withId(R.id.logout_btn)).perform(ViewActions.click());

	}
	
	public void testHome() {
		Espresso.onView(ViewMatchers.withId(R.id.action_home)).perform(ViewActions.click());
		
	}
	
}

