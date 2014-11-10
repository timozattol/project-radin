package ch.epfl.sweng.radin.callback;

import java.util.List;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.storage.RadinGroupModel;

/**
 * 
 * @author Fabien Zellweger
 *
 */
public class MyRadinGroupsListener implements RadinListener{
	private boolean isStorageManagercalled = false;
	
	//counter to simulate the loading data
	private int counter = 0;
	
	public boolean isUpdateRunning(List<RadinGroupModel> rgm){
		if (!isStorageManagercalled){
			return true;
		}
		
		if (counter <= 3) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;
			return true;
		}
		
		
		
		//fake code to return something
		RadinGroupModel fakeRadinGroup0 = new RadinGroupModel(0, new DateTime() ,
				"fake RadinGroup 0", "Fake description 0", "*.*");
		rgm.add(fakeRadinGroup0);
		RadinGroupModel fakeRadinGroup1 = new RadinGroupModel(1, new DateTime() ,
				"fake RadinGroup 1", "Fake description 1", "*-*");
		rgm.add(fakeRadinGroup1);
		RadinGroupModel fakeRadinGroup2 = new RadinGroupModel(2, new DateTime() ,
				"fake RadinGroup 2", "Fake description 2", "*o*");
		rgm.add(fakeRadinGroup2);
		RadinGroupModel fakeRadinGroup3 = new RadinGroupModel(3, new DateTime() ,
				"fake RadinGroup 3", "Fake description 3", "*0*");
		rgm.add(fakeRadinGroup3);
		RadinGroupModel fakeRadinGroup4 = new RadinGroupModel(4, new DateTime() ,
				"fake RadinGroup 4", "Fake description 4", "*_*");
		rgm.add(fakeRadinGroup4);
		return false;
	}
	
	public void callFromStorageManagerTrue(){
		isStorageManagercalled = true;
	}
}
