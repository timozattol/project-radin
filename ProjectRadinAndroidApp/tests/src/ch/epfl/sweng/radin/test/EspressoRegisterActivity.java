package ch.epfl.sweng.radin.test;


import org.joda.time.DateTime;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import ch.epfl.sweng.radin.R;
import ch.epfl.sweng.radin.RadinGroupAddExpenseActivity;
import ch.epfl.sweng.radin.RegisterActivity;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.UserModel;
import ch.epfl.sweng.radin.ActionBar;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

/**
 * GUI tests on RadinGroupAddExpenseActivity
 * 
 * @author julied20
 */
public class EspressoRegisterActivity extends
	ActivityInstrumentationTestCase2<RegisterActivity> {

	public EspressoRegisterActivity() {
		super(RegisterActivity.class);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		/*UserModel rgModel = new UserModel("Cedric", "Cook", "Agepoly", 
				"cedric.cook@epfl.ch", "EPFL", "CH01", "bic01", null, 2);
		
		Intent myIntent = new Intent();
		setActivityIntent(myIntent);
		getActivity();*/
	}
	
	private void scrollTo(int rId) {
		Espresso.onView(ViewMatchers.withId(rId)).perform(ViewActions.scrollTo());
	}
	
	
	
}
