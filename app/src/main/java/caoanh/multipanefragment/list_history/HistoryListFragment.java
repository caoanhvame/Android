package caoanh.multipanefragment.list_history;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import caoanh.multipanefragment.Constants;
import caoanh.multipanefragment.R;

public class HistoryListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ListView historyListView;
    private List<History> historyList;
    public HistoryListFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        historyListView = (ListView) getActivity().findViewById(R.id.historyListView);
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                History h = (History) historyListView.getAdapter().getItem(position);
                mListener.onFragmentInteraction(h);
            }
        });
        historyList = new ArrayList<>();
        new ParseMatch().execute("https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key="+Constants.API_KEY+"&account_id="+Constants.ACCOUNT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and startTime
        void onFragmentInteraction(History history);
    }

    private class ParseMatch extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String server_response;
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = null;
                    StringBuffer response = new StringBuffer();
                    try {
                        reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    server_response = response.toString();
                    parseJson(server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            HistoryListAdapter adapter = new HistoryListAdapter(getActivity(), historyList);
            historyListView.setAdapter(adapter);
        }
    }

    private void parseJson(String jsonString) {
        try {
            JSONObject allHeroesJsonObject = new JSONObject(jsonString);
            JSONObject aa =  allHeroesJsonObject.getJSONObject("result");
            JSONArray matchesJsonArray = aa.getJSONArray("matches");
            for (int i = 0; i < matchesJsonArray.length(); i++) {
                JSONObject match = matchesJsonArray.getJSONObject(i);
                History h = new History();
                h.setMatchId(match.getString("match_id"));
                h.setStartTime(match.getString("start_time"));
                JSONArray players = match.getJSONArray("players");
                for(int j = 0;j<players.length() ;j++){
                    JSONObject player = players.getJSONObject(j);
                    if(player.getString("account_id").equals(Constants.ACCOUNT_ID)){
                        h.setPlayerHeroId(player.getInt("hero_id"));
                        historyList.add(h);
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
