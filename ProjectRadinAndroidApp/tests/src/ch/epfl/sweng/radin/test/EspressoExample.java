/**
 * @author topali2
 */
package ch.epfl.sweng.radin.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.*;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;


import ch.epfl.sweng.radin.LoginActivity;
import android.test.ActivityInstrumentationTestCase2;

import ch.epfl.sweng.radin.R;

/**
 * @author topali2
 * 
 *
 */
public class EspressoExample extends ActivityInstrumentationTestCase2<LoginActivity>{

	/**
	 * 
	 */
	public EspressoExample() {

		super(LoginActivity.class);

	}

	@Override
	public void setUp() throws Exception {
	  super.setUp();
	  getActivity();
	}

	public void test(){

		System.out.println("espresso test");
		onView(withId(R.id.login)).perform(typeText("topali2"));
		
		onView(withId(R.id.password)).perform(typeText("something"));
		
		onView(withId(R.id.loginButton)).perform(click());
	}

}
