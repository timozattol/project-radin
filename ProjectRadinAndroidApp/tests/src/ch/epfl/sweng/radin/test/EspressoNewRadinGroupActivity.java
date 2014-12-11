package ch.epfl.sweng.radin.test;

import org.joda.time.DateTime;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.radin.ActionBar;
import ch.epfl.sweng.radin.NewRadinGroupActivity;
import ch.epfl.sweng.radin.R;
import ch.epfl.sweng.radin.storage.RadinGroupModel;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

/**
 * A class for testing everything that concerns RadinGroups
 * @author Jokau
 * TODO Mock connections!
 */
public class EspressoNewRadinGroupActivity extends ActivityInstrumentationTestCase2<NewRadinGroupActivity> {
	public EspressoNewRadinGroupActivity() {
		super(NewRadinGroupActivity.class);
	}
	
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
	
	public void testNewRadinGroup() {	
		//TODO add restriction on special characters
		Espresso.onView(ViewMatchers.withId(R.id.edit_name)).perform(ViewActions.typeText("Ma super liste !"))
		.check(ViewAssertions.matches(ViewMatchers.withText("Ma super liste !")));
		
		Espresso.onView(ViewMatchers.withId(R.id.people)).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("second beforLast")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		scrollTo(R.id.create);
		Espresso.onView(ViewMatchers.withId(R.id.create)).perform(ViewActions.click());
		//should close activity and toast list created
	}
	
	public void testNewRadinGroupWithoutName() {
		scrollTo(R.id.people);
		Espresso.onView(ViewMatchers.withId(R.id.people)).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("second beforLast")).perform(ViewActions.click());
		Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
		scrollTo(R.id.create);
		Espresso.onView(ViewMatchers.withId(R.id.create)).perform(ViewActions.click());
		// should toast no name and stay on activity
	}
	public void testNewRadinGroupWithoutParticipant() {
		scrollTo(R.id.edit_name);
		Espresso.onView(ViewMatchers.withId(R.id.edit_name)).perform(ViewActions.typeText("Ma super liste!"));
		scrollTo(R.id.create);
		Espresso.onView(ViewMatchers.withId(R.id.create)).perform(ViewActions.click());
		// should toast no people on list and stay on activity
	}
	
}
