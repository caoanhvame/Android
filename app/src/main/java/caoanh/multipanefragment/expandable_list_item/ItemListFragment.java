package caoanh.multipanefragment.expandable_list_item;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import caoanh.multipanefragment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends Fragment implements OnChildClickListener {

	private List<String> expandableListTitle;

	private HashMap<String, List<ItemObject>> expandableListDetail;

	private ItemExpandableListAdapter adapter2;

	private ExpandableListView itemExpandableList;

	public ItemListFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_item_list, null);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new ParseItem().execute();

		expandableListDetail = new LinkedHashMap<String, List<ItemObject>>();
		itemExpandableList = (ExpandableListView) getActivity().findViewById(
				R.id.itemListView);
		itemExpandableList.setBackgroundColor(Color.WHITE);
		itemExpandableList.setOnChildClickListener(this);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
			int childPosition, long id) {
		ItemObject itemClick = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(
				childPosition);
		Iterator<List<ItemObject>> it = expandableListDetail.values().iterator();
		while (it.hasNext()) {
			List<ItemObject> itemList = (ArrayList<ItemObject>)it.next();
			for (int i = 0; i < itemList.size(); i++) {
				if (itemList.get(i).getID().equals(itemClick.getID())) {
					itemClick.setExpand(!itemClick.isExpand());
				}
				else {
					itemList.get(i).setExpand(false);
				}
			}
		}
		long packedPosition = ExpandableListView.getPackedPositionForChild(groupPosition,
		childPosition);
		int flatPosition = itemExpandableList.getFlatListPosition(packedPosition);
		itemExpandableList.expandGroup(groupPosition);
		itemExpandableList.setSelectedChild(groupPosition, childPosition, true);
		itemExpandableList.smoothScrollToPosition(flatPosition);
		adapter2.notifyDataSetChanged();
		return false;
	}

	private class ParseItem extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			InputStream is = getResources().openRawResource(R.raw.items_custom);
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			}
			catch (Exception e) {

			}
			finally {
				try {
					is.close();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String jsonString = writer.toString();
			try {
				parseJson(jsonString);
			}
			catch (Exception e) {
				Log.e("ERROR", "Json parsing error: " + e.getMessage());
			}

			return null;
		}

		private void parseJson(String jsonString) throws JSONException {
			JSONObject allItems = new JSONObject(jsonString);
			Iterator<String> iterator = allItems.keys();
			while (iterator.hasNext()) {
				String objectKey = iterator.next();
				JSONObject itemJsonObject = allItems.getJSONObject(objectKey);
				ItemObject item = new ItemObject();
				item.setID(itemJsonObject.getString("ID"));
				item.setImage(itemJsonObject.getString("Image"));
				item.setName(itemJsonObject.getString("Name"));
				item.setCost(itemJsonObject.getString("Cost"));
				item.setMainDescription(itemJsonObject.getString("MainDescription"));
				item.setAlternateDescription(itemJsonObject.getString("AlternateDescription"));
				item.setExtraAttribute(itemJsonObject.getString("ExtraAttribute"));
				item.setCoolDown(itemJsonObject.getString("Cooldown"));
				item.setManaCost(itemJsonObject.getString("ManaCost"));
				JSONArray createdTo = itemJsonObject.getJSONArray("CreatedTo");
				ArrayList<String> createToList = new ArrayList<String>();
				for (int i = 0; i < createdTo.length(); i++) {
					createToList.add(createdTo.getJSONObject(i).getString("name"));
				}
				JSONArray createdFrom = itemJsonObject.getJSONArray("CreatedFrom");
				ArrayList<String> createdFromList = new ArrayList<String>();
				for (int i = 0; i < createdFrom.length(); i++) {
					createdFromList.add(createdFrom.getJSONObject(i).getString("name"));
				}
				item.setCreatedTo(createToList);
				item.setCreatedFrom(createdFromList);
				item.setLore(itemJsonObject.getString("Lore"));
				item.setExpand(false);
				String itemGroup = itemJsonObject.getString("Group");
				List<ItemObject> itemGroupList = expandableListDetail.get(itemGroup);
				if(itemGroupList != null){
					itemGroupList.add(item);
				}else{
					List<ItemObject> newGroup = new ArrayList<ItemObject>();
					newGroup.add(item);
					expandableListDetail.put(itemGroup, newGroup);
				}
			}
			expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			adapter2 = new ItemExpandableListAdapter(getActivity(), expandableListTitle,
					expandableListDetail);

			itemExpandableList.setAdapter(adapter2);
		}
	}
}
