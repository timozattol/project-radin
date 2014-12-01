package ch.epfl.sweng.radin.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.meta.When;

import org.json.JSONObject;
//import org.mockito.Mockito;

import android.os.storage.StorageManager;
import android.test.ActivityInstrumentationTestCase2;
import ch.epfl.sweng.radin.LoginActivity;
import ch.epfl.sweng.radin.R;
import ch.epfl.sweng.radin.callback.RadinListener;
import ch.epfl.sweng.radin.callback.StorageManagerRequestStatus;
import ch.epfl.sweng.radin.storage.RadinGroupModel;
import ch.epfl.sweng.radin.storage.RequestType;
import ch.epfl.sweng.radin.storage.managers.*;
import ch.epfl.sweng.radin.storage.parsers.RadinGroupJSONParser;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.Tapper.Status;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

/**
 * A class for testing everything that concerns RadinGroups
 * @author Jokau
 *
 */
public class DemoRadinGroupView extends ActivityInstrumentationTestCase2<LoginActivity> {
	private HttpURLConnection connection;
	private static final String JSON_RESPONSE = "{\"radinGroup\":[" +
			"{\"RG_name\":\"radinGroup\"," +
			"\"RG_creationDate\":\"2014/11/28 10/11\"," +
			"\"RG_description\":\"description bidon\"," +
			"\"RG_masterID\":1," +
			"\"RG_avatar\":\"\"," +
			"\"RG_deletedAt\":\"\"," +
			"\"RG_ID\":1}," +
			"{\"RG_name\":\"radinGroup2\"," +
			"\"RG_creationDate\":\"2014/11/29 10/11\"," +
			"\"RG_description\":\"description balo\"," +
			"\"RG_masterID\":2," +
			"\"RG_avatar\":\"\"," +
			"\"RG_deletedAt\":\"\"," +
			"\"RG_ID\":2}]}";

	public DemoRadinGroupView() {
		super(LoginActivity.class);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
//		connection = Mockito.mock(HttpURLConnection.class);
		 InputStream dataStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());
//		 Mockito.doReturn(200).when(connection).getResponseCode();
//		 Mockito.doReturn(dataStream).when(connection).getInputStream();
//		 
		 getActivity();
	}
	
	
	private void scrollTo(int rId) {
		Espresso.onView(ViewMatchers.withId(rId)).perform(ViewActions.scrollTo());
	}
	
	public void testAll() {
		//go to RadinGroupView
		Espresso.onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.typeText("topali2"));
		Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("to2Ra1din@pali"));
		Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click());

		Espresso.onView(ViewMatchers.withId(R.id.myRadinGroupBtn)).perform(ViewActions.click());
//		Espresso.onView(viewMatcher)
	}
}