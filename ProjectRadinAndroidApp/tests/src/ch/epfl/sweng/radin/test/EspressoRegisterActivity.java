package ch.epfl.sweng.radin.test;


import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.radin.R;
import ch.epfl.sweng.radin.RegisterActivity;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

/**
 * GUI tests on RegisterActivity
 * 
 * @author julied20
 */
public class EspressoRegisterActivity extends
	ActivityInstrumentationTestCase2<RegisterActivity> {
	
	private final int mSleepTime = 100;

	public EspressoRegisterActivity() {
		super(RegisterActivity.class);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		getActivity();
	}
	
	public void testEspresso() {

		Espresso.onView(ViewMatchers.withId(R.id.first_name_new_user))
		.perform(ViewActions.typeText("Fabien"));

		Espresso.closeSoftKeyboard();

		try {
			Thread.sleep(mSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Espresso.onView(ViewMatchers.withId(R.id.username_new_user))
		.perform(ViewActions.typeText("Walono"));

		Espresso.closeSoftKeyboard();

		try {
			Thread.sleep(mSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Espresso.onView(ViewMatchers.withId(R.id.email_new_user))
		.perform(ViewActions.typeText("fabien.zellweger@epfl.ch"));

		
		Espresso.closeSoftKeyboard();

		try {
			Thread.sleep(mSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Espresso.onView(ViewMatchers.withId(R.id.password_new_user))
		.perform(ViewActions.typeText("Sushi"));

		Espresso.closeSoftKeyboard();

		try {
			Thread.sleep(mSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Espresso.onView(ViewMatchers.withId(R.id.address_new_user))
		.perform(ViewActions.typeText("Trop loin d'ici"));
		
		Espresso.closeSoftKeyboard();

		try {
			Thread.sleep(mSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Espresso.onView(ViewMatchers.withId(R.id.iban_new_user))
		.perform(ViewActions.typeText("CH01"));

		Espresso.closeSoftKeyboard();

		try {
			Thread.sleep(mSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Espresso.onView(ViewMatchers.withId(R.id.bic_swift_address_new_user))
		.perform(ViewActions.typeText("Bic01"));
		
		Espresso.closeSoftKeyboard();
		try {
            Thread.sleep(mSleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		Espresso.onView(ViewMatchers.withId(R.id.sign_up_button))
		.perform(ViewActions.click());
	}
	
	
}
