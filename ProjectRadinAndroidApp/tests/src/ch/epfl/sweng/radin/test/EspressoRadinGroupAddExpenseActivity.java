package ch.epfl.sweng.radin.test;


import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import ch.epfl.sweng.radin.R;
import ch.epfl.sweng.radin.RadinGroupAddExpenseActivity;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.ViewAction;
import com.google.android.apps.common.testing.ui.espresso.action.GeneralLocation;
import com.google.android.apps.common.testing.ui.espresso.action.GeneralSwipeAction;
import com.google.android.apps.common.testing.ui.espresso.action.Press;
import com.google.android.apps.common.testing.ui.espresso.action.Swipe;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

/**
 * GUI tests on RadinGroupAddExpenseActivity
 * CAUTION, only works with emulator (soft keyboard hides items)
 * 
 * @author Jolauka
 */
public class EspressoRadinGroupAddExpenseActivity extends
	ActivityInstrumentationTestCase2<RadinGroupAddExpenseActivity> {
	public EspressoRadinGroupAddExpenseActivity() {
		super(RadinGroupAddExpenseActivity.class);
	}
	
	/**
	 * http://qathread.blogspot.de/2014/01/discovering-espresso-for-android-swiping.html
	 * swipes up
	 */
	private ViewAction swipeUp() {
	    return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER,
	    		GeneralLocation.TOP_CENTER, Press.FINGER);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		Intent myIntent = new Intent();
		myIntent.putExtra("key", "My example list");
		setActivityIntent(myIntent);
		getActivity();
	}
	
	public void testAddCreditor() {	
		//purpose TextField
		Espresso.onView(ViewMatchers.withId(R.id.purpose_title)).perform(ViewActions.typeText("Commissions du 15.11.14"));
		
		//open creditor dialog button		
		Espresso.onView(ViewMatchers.withId(R.id.add_creditor)).perform(ViewActions.click());
		//Dialog : select creditor
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		
		Espresso.onView(ViewMatchers.withId(R.id.creditor_selected)).check(ViewAssertions.matches(ViewMatchers.withText("julie")));
	}
	
	public void testAddDebtor() {
		//open debtor dialog button
		Espresso.onView(ViewMatchers.withId(R.id.add_debtors)).perform(ViewActions.click());
		
		//Dialog : select debtors
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("Igor")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("JT")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
				
		Espresso.onView(ViewMatchers.withId(R.id.debtors_selected)).check(ViewAssertions.matches(ViewMatchers.withText(" julie Igor JT")));
	}
	
	public void testAmountField() {
		//should allow only numericals
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field)).perform(ViewActions.typeText("Salut")).check(ViewAssertions.matches(ViewMatchers.withText("")));
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field)).perform(ViewActions.typeText("234")).check(ViewAssertions.matches(ViewMatchers.withText("234")));
	}
	
	public void testNoCreditor() {
		//purpose TextField
		Espresso.onView(ViewMatchers.withId(R.id.purpose_title)).perform(ViewActions.typeText("Commissions du 15.11.14"));
		
		//open debtor dialog button
		Espresso.onView(ViewMatchers.withId(R.id.add_debtors)).perform(ViewActions.click());
		//Dialog : select debtors
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("Igor")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("JT")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
			
		Espresso.onView(ViewMatchers.withId(R.id.addExpenseRadinGroupLayout)).perform(swipeUp());
		
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field)).perform(ViewActions.typeText("234"));
		
		Espresso.onView(ViewMatchers.withId(R.id.add_expense_button)).perform(ViewActions.click());
		//should be fine (because of default creditor)
		Log.i("ACTIVITY", getActivity().toString());
	}
	
	public void testNoDebtors() {
		//purpose TextField
		Espresso.onView(ViewMatchers.withId(R.id.purpose_title)).perform(ViewActions.typeText("Commissions du 15.11.14"));
				
		//open creditor dialog button		
		Espresso.onView(ViewMatchers.withId(R.id.add_creditor)).perform(ViewActions.click());
		//Dialog : select creditor
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		
		Espresso.onView(ViewMatchers.withId(R.id.addExpenseRadinGroupLayout)).perform(swipeUp());
		
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field)).perform(ViewActions.typeText("234")).check(ViewAssertions.matches(ViewMatchers.withText("234")));
		
		Espresso.onView(ViewMatchers.withId(R.id.add_expense_button)).perform(ViewActions.click());
		//should toast a message, stay on RadinGroupAddExpenseActivity activity
	}
	
	public void testNoName() {
		//open creditor dialog button		
		Espresso.onView(ViewMatchers.withId(R.id.add_creditor)).perform(ViewActions.click());
			
		//Dialog : select creditor
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		
		//open debtor dialog button
		Espresso.onView(ViewMatchers.withId(R.id.add_debtors)).perform(ViewActions.click());
		//Dialog : select debtors
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("Igor")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("JT")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		
		Espresso.onView(ViewMatchers.withId(R.id.addExpenseRadinGroupLayout)).perform(swipeUp());
		
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field)).perform(ViewActions.typeText("234"));
		
		Espresso.onView(ViewMatchers.withId(R.id.add_expense_button)).perform(ViewActions.click());
		//should toast a message, stay on RadinGroupAddExpenseActivity activity
	}
}