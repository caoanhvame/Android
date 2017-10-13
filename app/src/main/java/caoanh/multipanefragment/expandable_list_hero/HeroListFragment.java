package caoanh.multipanefragment.expandable_list_hero;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import caoanh.multipanefragment.R;
import caoanh.multipanefragment.LandingFragment.OnMenuItemClickListener;

public class HeroListFragment extends Fragment implements OnChildClickListener {

	private List<String> heroTarven;
	private Map<String,List<HeroObject>> heroMap;
	private ExpandableListView heroListView;
	private HeroAdapter adapter;
	
	private OnHeroClickListener listener;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_hero_list, null);
		return v;
	};

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		heroListView = (ExpandableListView) getActivity().findViewById(R.id.heroListView);
		heroListView.setBackgroundColor(Color.WHITE);
		//check in home computer
		heroListView.setGroupIndicator(null);
		heroListView.setOnChildClickListener(this);
		
		heroMap = new LinkedHashMap<String, List<HeroObject>>();
		new PraseHero().execute();
	};

	@Override
	public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
		HeroObject heroClick = (HeroObject)adapter.getChild(groupPosition, childPosition);
        listener.onItemClick(heroClick.getId());
		return true;
	}

	
	public interface OnHeroClickListener{
		void onItemClick(int id);
	}
	@Override
	public void onAttach(Activity context) {
		super.onAttach(context);
		if (context instanceof OnHeroClickListener) {
			listener = (OnHeroClickListener) context;
		}
		else {
			throw new RuntimeException(context.toString()
					+ " must implement OnHeroListFragmentListener");
		}
	}

	private class PraseHero extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			InputStream is = getResources().openRawResource(R.raw.heroes_short);
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
			parseJson(jsonString);
			return null;
		}

		private void parseJson(String jsonString) {
			try {
				JSONObject allHeroesJsonObject = new JSONObject(jsonString);
				JSONArray herosJsonArray = allHeroesJsonObject.getJSONArray("heroes");

				for (int i = 0; i < herosJsonArray.length(); i++) {
					JSONObject heroJsonObject = herosJsonArray.getJSONObject(i);
					HeroObject hero = new HeroObject();
					hero.setName(heroJsonObject.getString("Name"));
					hero.setImage(heroJsonObject.getString("Image"));
					hero.setId(heroJsonObject.getInt("Id"));
					String tavern = heroJsonObject.getString("Fraction") + "-" + heroJsonObject.getString("PrimaryAttribute");
					
					List<HeroObject> heroInTavern = heroMap.get(tavern);
					if(heroInTavern !=null){
						heroInTavern.add(hero);
					}else{
						List<HeroObject> newTarven = new ArrayList<HeroObject>();
						newTarven.add(hero);
						heroMap.put(tavern, newTarven);
					}
				}
				heroTarven = new ArrayList<String>(heroMap.keySet());
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			adapter = new HeroAdapter(getActivity(), heroTarven, heroMap);
			heroListView.setAdapter(adapter);
		}
	}

}
