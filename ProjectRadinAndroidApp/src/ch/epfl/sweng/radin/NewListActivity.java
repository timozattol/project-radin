package ch.epfl.sweng.radin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

/**
 * Activity allows the user to create a new list of expenses.
 * The user must provide a name for the list and the names of the people that he wants share this list with. 
 *
 */
public class NewListActivity extends Activity {
	private final int mClientID = 0;
	private MultiAutoCompleteTextView mPeopleInList;
	private EditText mNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        
		mPeopleInList = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoFriends);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,
				getFriendsOfArray(mClientID));
		mPeopleInList.setAdapter(adapter);
		mPeopleInList.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		mPeopleInList.setThreshold(0);
		
        mNameEdit = (EditText) findViewById(R.id.edit_name);

        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	String listName = mNameEdit.getText().toString();
            	if (listName.equals("") || (listName == null)) {
            		Toast.makeText(getBaseContext(), "You must provide a name for the list", Toast.LENGTH_SHORT).show();
	            } else {
	            	//TODO SERVER : create new list name=listName
	            	
	            	//Compare input with client's friends
	            	ArrayList<String> friendsOfClient = getFriendsOfArray(mClientID);
	            	
	            	String strPeopleInList = mPeopleInList.getText().toString();
	            	List<String> peopleToAdd = new ArrayList<String>(Arrays.asList(strPeopleInList.split(", ")));
	            	
	            	// remove duplicates
	            	HashSet<String> h = new HashSet<String>(peopleToAdd);
	            	peopleToAdd.clear();
	            	peopleToAdd.addAll(h);
	            	
	            	//checks whether people are already friends or need to be invited
					List<String> newfriends = new ArrayList<String>();
					List<String> alreadyfriends = new ArrayList<String>();
					String friend;
	            	for (int i = 0; i < peopleToAdd.size(); ++i) {
	        			friend = peopleToAdd.get(i);
	        			if (!friend.equals("")) { //TODO requires more tests (exclude newline, or concat of spaces)
	        				if (friendsOfClient.contains(friend)) {
	        					alreadyfriends.add(friend);
	            			} else {
	            				newfriends.add(friend);
	            			}
	            		}
	            	}
	            	//TODO SERVER add client to list
	            	if (!alreadyfriends.isEmpty()) {
	            		//SERVER add these people to list
	            	}
	            	if (!newfriends.isEmpty()) {
	            		//TODO SERVER add people
//	            		Add people through pop-up?
//	            		Add people through another activity? shared with ListConfigurationActivity/addContact?
//	                    Intent switchToListConfigurationActivity = new Intent(getBaseContext(), XXX.class);
//	                    addNewfriends.putExtra(this.getClass().getName(), newfriends.toString());
//	                    startActivity(addNewfriends);
	            	}
                Toast.makeText(getBaseContext(), "List created", Toast.LENGTH_LONG).show();
                mPeopleInList.clearComposingText();
                mNameEdit.clearComposingText();
                Intent returnToLists = new Intent(getBaseContext(), MyListsActivity.class);
                startActivity(returnToLists);
	            }
            }
	    });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
//    /**
//     * Gets the friends' names of the client from the server
//     * @param clientId : the id of the client
//     * @Return an array of Strings containing the client's friends
//     * 
//     */
//	private String[] getFriendsOf(int clientId){
//    	//ask the server, do stuff to extract names, store in String[]
//		//Find a way to have a unique identifier for each client in database
//    	String[] friendsOfClient = {"Julie", "Fabien", "Timothée", "Cédric",
//    	"Simon", "Thomas", "Joël", "t", "tt", "ttt", "tttt", "tttttt", "ttttttt"};
//    	return friendsOfClient;
//    }
	
    /**
     * Gets the friends' names of the client from the server
     * @param clientId : the id of the client
     * @Return an ArrayList of Strings containing the client's friends
     * 
     */
	private ArrayList<String> getFriendsOfArray(int clientId) {
    	//TODO SERVER: ask the server, do stuff to extract names, store in String[]
		//TODO Use a way to have a unique identifier for each client in database
		
		//hard-coded example
    	ArrayList<String> friendsOfClient = new ArrayList<String>();
    	friendsOfClient.add("Julie");
    	friendsOfClient.add("Fabien");
    	friendsOfClient.add("Timothée");
    	friendsOfClient.add("Cédric");
    	friendsOfClient.add("Simon");
    	friendsOfClient.add("Thomas");
    	friendsOfClient.add("Joël");
    	friendsOfClient.add("t");
    	friendsOfClient.add("tt");
    	friendsOfClient.add("ttt");
    	
    	return friendsOfClient;
    }
}