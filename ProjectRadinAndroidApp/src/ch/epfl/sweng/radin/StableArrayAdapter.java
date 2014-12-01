package ch.epfl.sweng.radin;

import java.util.HashMap;
import android.content.Context;
import android.widget.ArrayAdapter;

/**
* A StableArrayAdapter<T> is needed to use getCheckedItemIds() on checkable listViews
* @see <a href="http://www.vogella.com/tutorials/AndroidListView/article.html">
		Based on : http://www.vogella.com/tutorials/AndroidListView/article.html</a>
* @see android.widget.ArrayAdapter
* ArrayAdapter<T>
* @param <T> generic parameter
*/
public class StableArrayAdapter<T> extends  ArrayAdapter<T> {
	private HashMap<T, Integer> mIdMap = new HashMap<T, Integer>();

	public StableArrayAdapter(Context context, int textViewResourceId, T[] items) {
		super(context, textViewResourceId, items);
		for (int i = 0; i < items.length; ++i) {
			mIdMap.put(items[i], i);
		}
	}
	@Override
	public long getItemId(int position) {
		T item = getItem(position);
		return mIdMap.get(item);
	}
	@Override
	public boolean hasStableIds() {
		return true;
	}
}
