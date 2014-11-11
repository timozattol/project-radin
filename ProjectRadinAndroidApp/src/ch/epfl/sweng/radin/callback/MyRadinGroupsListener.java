package ch.epfl.sweng.radin.callback;

import java.util.List;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.storage.Model;
import ch.epfl.sweng.radin.storage.RadinGroupModel;

/**
 * 
 * @author Fabien Zellweger
 *
 */
public class MyRadinGroupsListener implements RadinListener{
	private boolean mIsStorageManagercalled = false;
	private int mUserId = -1;
	@Override
	
	
	public void callback(List<Model> myRadinGroupsList){
		//normaly load data on the DB and update the activity when ready
		if (!mIsStorageManagercalled){
			//do nothing because the associated storage manager wasn't call
		} else {
			
		
		//fake code to return something
		RadinGroupModel fakeRadinGroup0 = new RadinGroupModel(0, new DateTime() ,
				"fake RadinGroup 0", "Fake description 0", "*.*");
		myRadinGroupsList.add(fakeRadinGroup0);
		RadinGroupModel fakeRadinGroup1 = new RadinGroupModel(1, new DateTime() ,
				"fake RadinGroup 1", "Fake description 1", "*-*");
		myRadinGroupsList.add(fakeRadinGroup1);
		RadinGroupModel fakeRadinGroup2 = new RadinGroupModel(2, new DateTime() ,
				"fake RadinGroup 2", "Fake description 2", "*o*");
		myRadinGroupsList.add(fakeRadinGroup2);
		RadinGroupModel fakeRadinGroup3 = new RadinGroupModel(3, new DateTime() ,
				"fake RadinGroup 3", "Fake description 3", "*0*");
		myRadinGroupsList.add(fakeRadinGroup3);
		RadinGroupModel fakeRadinGroup4 = new RadinGroupModel(4, new DateTime() ,
				"fake RadinGroup 4", "Fake description 4", "*_*");
		myRadinGroupsList.add(fakeRadinGroup4);
		}
	}
	
	public void callFromStorageManagerTrue(){
		mIsStorageManagercalled = true;
	}



	@Override
	public void setId(int userId) {
		mUserId = userId;
		
	}


}
