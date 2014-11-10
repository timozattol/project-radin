package ch.epfl.sweng.radin.test;

import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.radin.LoginActivity;
import ch.epfl.sweng.radin.R;
import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

public class EspressoRadinGroupAddExpenseActivity extends ActivityInstrumentationTestCase2<LoginActivity> {
	public static final int SLEEP_TIME_MS = 500;
	
	public EspressoRadinGroupAddExpenseActivity(){
		super(LoginActivity.class);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		getActivity();
	}
	private void sleep(){
		try {
			Thread.sleep(SLEEP_TIME_MS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void testEspresso() {
		//Go to RadinGroupAddExenseActivity
		Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
		sleep();
		Espresso.onView(ViewMatchers.withId(R.id.myListBtn)).perform(ViewActions.click());
		sleep();
		Espresso.onView(ViewMatchers.withId(R.id.aRadinGroupExemple)).perform(ViewActions.click());
		sleep();
		Espresso.onView(ViewMatchers.withId(R.id.addExpeseActionBar)).perform(ViewActions.click());
		
		//tests on RadinGroupAddExenseActivity
		
		//should allow only numericals
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field)).perform(ViewActions.typeText("Salut")).check(ViewAssertions.matches(ViewMatchers.withText("")));
		sleep();
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field)).perform(ViewActions.typeText("00234")).check(ViewAssertions.matches(ViewMatchers.withText("00234")));
	}
	
}
