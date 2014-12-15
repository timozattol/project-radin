package ch.epfl.sweng.radin.test;

import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.radin.RadinGroupAddExpenseActivity;

/*
import org.joda.time.DateTime;

import android.content.Intent;
import android.util.Log;
import ch.epfl.sweng.radin.R;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.ActionBar;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;
*/
/**
 * GUI tests on RadinGroupAddExpenseActivity
 * 
 * @author Jolauka
 */
public class EspressoRadinGroupAddExpenseActivity extends
	ActivityInstrumentationTestCase2<RadinGroupAddExpenseActivity> {
	public EspressoRadinGroupAddExpenseActivity() {
		super(RadinGroupAddExpenseActivity.class);
	}
	/*This test was used until server interaction. 
	 *  Since Mockito testing was to hard to implement on this project or not the good tool
	 *  we discarded this test.
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		RadinGroupModel rgModel = new RadinGroupModel(
		        0, DateTime.now(), "My example list", "A simple example list", 
		        "https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		
		Intent myIntent = new Intent();
		myIntent.putExtras(ActionBar.makeModelToBundle(rgModel));
		setActivityIntent(myIntent);
		getActivity();
	}
	
	private void scrollTo(int rId) {
		Espresso.onView(ViewMatchers.withId(rId)).perform(ViewActions.scrollTo());
	}
	
	
	
	public void testAddCreditor() {	
		//purpose TextField
		Espresso.onView(ViewMatchers.withId(R.id.purpose_title))
		.perform(ViewActions.typeText("Commissions du 15.11.14"));
		
		//open creditor dialog button
		scrollTo(R.id.add_creditor);
		Espresso.onView(ViewMatchers.withId(R.id.add_creditor)).perform(ViewActions.click());
		//Dialog : select creditor
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		
		scrollTo(R.id.creditor_selected);
		Espresso.onView(ViewMatchers.withId(R.id.creditor_selected))
			.check(ViewAssertions.matches(ViewMatchers.withText("julie")));
	}
	
	public void testAddDebtor() {
	    closeKeyboardAndSleepForAWhile();
	    
		//open debtor dialog button
		scrollTo(R.id.add_debtors);
		Espresso.onView(ViewMatchers.withId(R.id.add_debtors)).perform(ViewActions.click());
		
		//Dialog : select debtors
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("Igor")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("JT")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		
		scrollTo(R.id.debtors_selected);
		Espresso.onView(ViewMatchers.withId(R.id.debtors_selected))
			.check(ViewAssertions.matches(ViewMatchers.withText(" julie Igor JT")));
	}
	
	public void testAmountField() {
		//should allow only numericals
		scrollTo(R.id.amount_Field);
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field))
			.perform(ViewActions.typeText("Salut")).check(ViewAssertions.matches(ViewMatchers.withText("")));
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field))
			.perform(ViewActions.typeText("234")).check(ViewAssertions.matches(ViewMatchers.withText("234")));
	}
	
	public void testNoCreditor() {
		//purpose TextField
		scrollTo(R.id.purpose_title);
		Espresso.onView(ViewMatchers.withId(R.id.purpose_title))
			.perform(ViewActions.typeText("Commissions du 15.11.14"));
		
		closeKeyboardAndSleepForAWhile();

		//open debtor dialog button.
		scrollTo(R.id.add_debtors);
		Espresso.onView(ViewMatchers.withId(R.id.add_debtors)).perform(ViewActions.click());
		//Dialog : select debtors
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("Igor")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("JT")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
			
		//Espresso.onView(ViewMatchers.withId(R.id.addExpenseRadinGroupLayout)).perform(swipeUp());
		scrollTo(R.id.amount_Field);
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field)).perform(ViewActions.typeText("234"));
		
		closeKeyboardAndSleepForAWhile();
		
		scrollTo(R.id.add_expense_button);
		Espresso.onView(ViewMatchers.withId(R.id.add_expense_button)).perform(ViewActions.click());
		//should be fine (because of default creditor)
		Log.i("ACTIVITY", getActivity().toString());
	}
	
	public void testNoDebtors() {
		//purpose TextField
		scrollTo(R.id.purpose_title);
		Espresso.onView(ViewMatchers.withId(R.id.purpose_title))
			.perform(ViewActions.typeText("Commissions du 15.11.14"));
				
		//open creditor dialog button
		scrollTo(R.id.add_creditor);
		Espresso.onView(ViewMatchers.withId(R.id.add_creditor)).perform(ViewActions.click());
		//Dialog : select creditor
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		
		//Espresso.onView(ViewMatchers.withId(R.id.addExpenseRadinGroupLayout)).perform(swipeUp());
		
		closeKeyboardAndSleepForAWhile();
		
		scrollTo(R.id.amount_Field);
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field))
			.perform(ViewActions.typeText("234")).check(ViewAssertions.matches(ViewMatchers.withText("234")));
		
		closeKeyboardAndSleepForAWhile();
		
		scrollTo(R.id.add_expense_button);
		Espresso.onView(ViewMatchers.withId(R.id.add_expense_button)).perform(ViewActions.click());
		//should toast a message, stay on RadinGroupAddExpenseActivity activity
	}
	
	public void testNoName() {
		//open creditor dialog button	
		scrollTo(R.id.add_creditor);
		Espresso.onView(ViewMatchers.withId(R.id.add_creditor)).perform(ViewActions.click());
			
		//Dialog : select creditor
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		
		//open debtor dialog button
		scrollTo(R.id.add_debtors);
		Espresso.onView(ViewMatchers.withId(R.id.add_debtors)).perform(ViewActions.click());
		//Dialog : select debtors
		Espresso.onView(ViewMatchers.withText("julie")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("Igor")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("JT")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		
		//Espresso.onView(ViewMatchers.withId(R.id.addExpenseRadinGroupLayout)).perform(swipeUp());
		
		closeKeyboardAndSleepForAWhile();
		
		scrollTo(R.id.amount_Field);
		Espresso.onView(ViewMatchers.withId(R.id.amount_Field)).perform(ViewActions.typeText("234"));
		
		closeKeyboardAndSleepForAWhile();
		
		scrollTo(R.id.add_expense_button);
		Espresso.onView(ViewMatchers.withId(R.id.add_expense_button)).perform(ViewActions.click());
		//should toast a message, stay on RadinGroupAddExpenseActivity activity
	}
	
	/**
	 * Avoids "Error performing 'single click' on view" with fast phones
	 */
	/*private void closeKeyboardAndSleepForAWhile() {
	    Espresso.closeSoftKeyboard();
	    try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	*/
}