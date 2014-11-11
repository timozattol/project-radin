package ch.epfl.sweng.radin.test;

import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.radin.LoginActivity;
import ch.epfl.sweng.radin.R;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

public class EspressoNewRadinGroupActivity extends ActivityInstrumentationTestCase2<LoginActivity>{
	public static final int SLEEP_TIME_MS = 500;
	
	public EspressoNewRadinGroupActivity(){
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
	
	public void goToActivity() {
		//Go to NewRadinGroupActivity
		Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());
		sleep();
		Espresso.onView(ViewMatchers.withId(R.id.myListBtn)).perform(ViewActions.click());
		sleep();
		Espresso.onView(ViewMatchers.withId(R.id.addBtn)).perform(ViewActions.click());
	}
	
	
	public void testNewRadinGroup() {	
		goToActivity();
		//maybe add restriction on special characters
		Espresso.onView(ViewMatchers.withId(R.id.edit_name)).perform(ViewActions.typeText("Ma super liste!")).check(ViewAssertions.matches(ViewMatchers.withText("Ma super liste!")));
		
		Espresso.onView(ViewMatchers.withId(R.id.people)).perform(ViewActions.click());
		sleep();
		Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack());
		sleep();
		Espresso.onView(ViewMatchers.withId(R.id.create)).perform(ViewActions.click());
		sleep();
	}
	
}
