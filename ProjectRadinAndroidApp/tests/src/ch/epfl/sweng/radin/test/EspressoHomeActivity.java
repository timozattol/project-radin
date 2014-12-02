/**
 * 
 */
package ch.epfl.sweng.radin.test;

import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.radin.HomeActivity;
import ch.epfl.sweng.radin.R;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

/**
 * @author julied20
 *
 */
public class EspressoHomeActivity extends ActivityInstrumentationTestCase2<HomeActivity> {

	public EspressoHomeActivity() {

		super(HomeActivity.class);

	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		getActivity();
	}

	public void testContactBtn() {

		Espresso.onView(ViewMatchers.withId(R.id.contactsBtn)).perform(ViewActions.click());
		Espresso.pressBack();
	//}
	//public void testGroupBtn() {
		Espresso.onView(ViewMatchers.withId(R.id.myRadinGroupBtn)).perform(ViewActions.click());
		Espresso.pressBack();
	//}
	//public void testNotificationBtn() {
		Espresso.onView(ViewMatchers.withId(R.id.notificationBtn)).perform(ViewActions.click());
		Espresso.pressBack();
	//}
	//public void testOverviewBtn() {
		Espresso.onView(ViewMatchers.withId(R.id.overviewBtn)).perform(ViewActions.click());
		Espresso.pressBack();
	//}
	//public void testProfileBtn() {
		Espresso.onView(ViewMatchers.withId(R.id.profileBtn)).perform(ViewActions.click());
		Espresso.pressBack();
	//}
	//public void testSettingsBtn() {
		Espresso.onView(ViewMatchers.withId(R.id.settingsBtn)).perform(ViewActions.click());
		Espresso.pressBack();

	}

}

