package caoanh.multipanefragment.expandable_list_item;

import android.content.res.Resources;
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
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        expandableListTitle = new ArrayList<>();
        expandableListDetail = new LinkedHashMap<>();
        new ParseItem(getResources(), expandableListTitle, expandableListDetail,
                this).execute();

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
        for (List<ItemObject> itemList: expandableListDetail.values()) {
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).getID().equals(itemClick.getID())) {
                    itemClick.setExpand(!itemClick.isExpand());
                } else {
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
        if(adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
        return false;
    }

    private ExpandableListView getList() {
        return itemExpandableList;
    }

    private void setAdapter2(ItemExpandableListAdapter adapter2) {
        this.adapter2 = adapter2;
    }
    private static class ParseItem extends AsyncTask<Void, Void, Void> {
        private Resources resources;
        private WeakReference<ItemListFragment> activityReference;
        private List<String> expandableListTitle;
        private HashMap<String, List<ItemObject>> expandableListDetail;
        private ItemExpandableListAdapter adapter2;
        private ItemListFragment itemListFragment;
        ParseItem(Resources resources, List<String> expandableListTitle,
                         HashMap<String, List<ItemObject>> expandableListDetail,
                         ItemListFragment context) {
            this.resources = resources;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetail = expandableListDetail;
            activityReference = new WeakReference<>(context);
            itemListFragment = activityReference.get();
        }

        @Override
        protected Void doInBackground(Void... params) {
            expandableListTitle.clear();
            InputStream is = resources.openRawResource(R.raw.items_custom);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String jsonString = writer.toString();
            try {
                parseJson(jsonString);
            } catch (Exception e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }

            return null;
        }

        private void parseJson(String jsonString) throws JSONException {
            JSONObject itemsJson = new JSONObject(jsonString);
            Iterator<String> iterator = itemsJson.keys();
            Set<ItemObject> items = new HashSet<>();
            while (iterator.hasNext()) {
                String objectKey = iterator.next();
                JSONObject itemJsonObject = itemsJson.getJSONObject(objectKey);
                ItemObject item = new ItemObject();
                item.setItemKey(objectKey);
                item.setID(itemJsonObject.getString("ID"));
                item.setImage(itemJsonObject.getString("Image"));
                item.setName(itemJsonObject.getString("Name"));
                item.setCost(itemJsonObject.getString("Cost"));
                item.setMainDescription(itemJsonObject.getString("MainDescription"));
                item.setAlternateDescription(itemJsonObject.getString("AlternateDescription"));
                item.setExtraAttribute(itemJsonObject.getString("ExtraAttribute"));
                item.setCoolDown(itemJsonObject.getString("Cooldown"));
                item.setManaCost(itemJsonObject.getString("ManaCost"));
//                JSONArray createdTo = itemJsonObject.getJSONArray("CreatedTo");
//                ArrayList<String> createToList = new ArrayList<String>();
//                for (int i = 0; i < createdTo.length(); i++) {
//                    createToList.add(createdTo.getJSONObject(i).getString("name"));
//                }
                JSONArray createdFrom = itemJsonObject.getJSONArray("CreatedFrom");
                ArrayList<String> createdFromList = new ArrayList<>();
                for (int i = 0; i < createdFrom.length(); i++) {
                    createdFromList.add(createdFrom.getJSONObject(i).getString("name"));
                }
//                item.setCreatedTo(createToList);
                item.setCreatedFrom(createdFromList);
                item.setLore(itemJsonObject.getString("Lore"));
                item.setExpand(false);
                String itemGroup = itemJsonObject.getString("Group");
                item.setGroup(itemJsonObject.getString("Group"));
                List<ItemObject> itemGroupList = expandableListDetail.get(itemGroup);
                if (itemGroupList != null) {
                    itemGroupList.add(item);
                } else {
                    List<ItemObject> newGroup = new ArrayList<>();
                    newGroup.add(item);
                    expandableListDetail.put(itemGroup, newGroup);
                }
                items.add(item);
            }
            addCreateTo(items);
            expandableListTitle.addAll(expandableListDetail.keySet());
        }

        private void addCreateTo(Set<ItemObject> items) {
            for (ItemObject item : items) {
                for (String itemCreateFrom : item.getCreatedFrom()) {
                    ItemObject createFromItem = getItemBasedOnItemKey(itemCreateFrom, items);
                    if (createFromItem != null) {
                        createFromItem.addCreatedTo(item.getItemKey());
                    }
                }
            }
        }

        private ItemObject getItemBasedOnItemKey(String itemKey, Set<ItemObject> items) {
            for (ItemObject item : items) {
                if (item.getItemKey().equals(itemKey)) {
                    return item;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter2 = new ItemExpandableListAdapter(itemListFragment.getActivity(), expandableListTitle,
                    expandableListDetail);
            itemListFragment.setAdapter2(adapter2);
            itemListFragment.getList().setAdapter(adapter2);
        }
    }
}
