package caoanh.multipanefragment.list_history;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import caoanh.multipanefragment.Constants;
import caoanh.multipanefragment.R;

/**
 * Created by anhhpc on 9/21/2017.
 * a
 */

class HistoryListAdapter extends ArrayAdapter<History> {
    private List<History> data;
    private Context context;

    HistoryListAdapter(Context context, List<History> data) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        History history = data.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_history_list_cell, parent, false);
            viewHolder = new ViewHolder();
            setViewHolder(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.duration.setText("");
        }
        viewHolder.loadingMask.setAlpha(1);
        viewHolder.content.setAlpha(0);
        viewHolder.avatar.setImageResource(context.getResources().getIdentifier("hero_" + history.getPlayerHeroId() + "", "drawable", context.getPackageName()));
        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        String output = outputFormatter.format(new Date(Long.parseLong(history.getStartTime() + "000")));
        viewHolder.startTime.setText(output);
        if (history.getDuration() == 0) {
            RotateAnimation anim = new RotateAnimation(0f, 360f, viewHolder.loadingWheel.getWidth() / 2.0f, viewHolder.loadingWheel.getHeight() / 2.0f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(700);
            viewHolder.loadingWheel.startAnimation(anim);

            String matchDetail = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?match_id=%s&key=" + Constants.API_KEY;
            new ParseMatchDetail(parent, viewHolder, position).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, String.format(matchDetail, history.getMatchId()));
        } else {
            viewHolder.duration.setText(convertSecondToHour(history.getDuration()));
            viewHolder.content.setAlpha(1);
            viewHolder.loadingMask.setAlpha(0);
        }
        return convertView;
    }

    private void setViewHolder(ViewHolder viewHolder, View view) {
        viewHolder.avatar = (ImageView) view.findViewById(R.id.fragment_history_cell_ava);
        viewHolder.startTime = (TextView) view.findViewById(R.id.fragment_history_cell_start_time);
        viewHolder.duration = (TextView) view.findViewById(R.id.fragment_history_cell_duration);
        viewHolder.matchResult = (TextView) view.findViewById(R.id.fragment_history_cell_match_result);
        viewHolder.kda = (TextView) view.findViewById(R.id.fragment_history_cell_kda);
        viewHolder.mode = (TextView) view.findViewById(R.id.fragment_history_cell_mode);
        viewHolder.loadingWheel = (ImageView) view.findViewById(R.id.fragment_history_cell_loading_wheel);
        viewHolder.loadingMask = (RelativeLayout) view.findViewById(R.id.fragment_history_list_cell_loading_mask);
        viewHolder.content = (RelativeLayout) view.findViewById(R.id.fragment_history_cell_content);
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView startTime;
        TextView duration;
        TextView matchResult;
        TextView kda;
        TextView mode;
        RelativeLayout content;
        RelativeLayout loadingMask;
        ImageView loadingWheel;
    }

    private class ParseMatchDetail extends AsyncTask<String, Void, Void> {
        private ViewHolder mV;
        private int position;
        private ListView parent;

        private ParseMatchDetail(ViewGroup parent, ViewHolder mV, int position) {
            this.mV = mV;
            this.position = position;
            this.parent = (ListView) parent;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String server_response;
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = null;
                    StringBuilder response = new StringBuilder();
                    try {
                        reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String line;
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
                    parseJson(server_response, position);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (position >= parent.getFirstVisiblePosition() && position <= parent.getLastVisiblePosition()) {
                History h = data.get(position);
                for (Player player : h.getPlayers()) {
                    if (player.getAccountId().equals(Constants.ACCOUNT_ID)) {
                        if ((player.getPlayerSlot() < 128 && h.isRadiantWin()) ||
                                (player.getPlayerSlot() > 128 && !h.isRadiantWin())) {
                            mV.matchResult.setText("WIN");
                            mV.matchResult.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.blueRadiant, null));
                        } else {
                            mV.matchResult.setText("LOSS");
                            mV.matchResult.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.redDire, null));
                        }
                        mV.kda.setText("K/D/A: " + player.getKills() + "/" + player.getDeaths() + "/" + player.getAssists());
                        mV.mode.setText(modeCodeToString(h.getGameMode()));
                        break;
                    }
                }
                mV.duration.setText(convertSecondToHour(h.getDuration()));
                Animation animationFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                mV.content.startAnimation(animationFadeIn);
                mV.content.setAlpha(1);
                mV.loadingMask.setAlpha(0);
            }
        }
    }

    private void parseJson(String server_response, int position) {
        try {
            History h = data.get(position);
            JSONObject object = new JSONObject(server_response).getJSONObject("result");
            h.setDuration(object.getInt("duration"));
            h.setRadiantWin(object.getBoolean("radiant_win"));
            h.setGameMode(object.getInt("game_mode"));
            h.setRadiantScore(object.getInt("radiant_score"));
            h.setDireScore(object.getInt("dire_score"));
            JSONArray players = object.getJSONArray("players");
            for (int i = 0; i < players.length(); i++) {
                JSONObject playerJson = players.getJSONObject(i);
                Player pl = new Player();
                pl.setAccountId(playerJson.getString("account_id"));
                pl.setPlayerSlot(playerJson.getInt("player_slot"));
                pl.setHeroId(playerJson.getInt("hero_id"));
                pl.setItem0(playerJson.getInt("item_0"));
                pl.setItem1(playerJson.getInt("item_1"));
                pl.setItem2(playerJson.getInt("item_2"));
                pl.setItem3(playerJson.getInt("item_3"));
                pl.setItem4(playerJson.getInt("item_4"));
                pl.setItem5(playerJson.getInt("item_5"));
                pl.setBackpack0(playerJson.getInt("backpack_0"));
                pl.setBackpack1(playerJson.getInt("backpack_1"));
                pl.setBackpack2(playerJson.getInt("backpack_2"));
                pl.setKills(playerJson.getInt("kills"));
                pl.setDeaths(playerJson.getInt("deaths"));
                pl.setAssists(playerJson.getInt("assists"));
                pl.setLeaver_status(playerJson.getInt("leaver_status"));
                pl.setLastHits(playerJson.getInt("last_hits"));
                pl.setDenies(playerJson.getInt("denies"));
                pl.setGpm(playerJson.getInt("gold_per_min"));
                pl.setXpm(playerJson.getInt("xp_per_min"));
                pl.setLevel(playerJson.getInt("level"));
                pl.setHeroDamage(playerJson.getInt("hero_damage"));
                pl.setTowerDamage(playerJson.getInt("tower_damage"));
                pl.setHeroHealing(playerJson.getInt("hero_healing"));
                pl.setGold(playerJson.getInt("gold"));
                pl.setGoldSpent(playerJson.getInt("gold_spent"));
                JSONArray abilityUpgrades = playerJson.getJSONArray("ability_upgrades");
                for (int j = 0; j < abilityUpgrades.length(); j++) {
                    JSONObject abiJson = abilityUpgrades.getJSONObject(j);
                    AbilityUpgrade skill = new AbilityUpgrade();
                    skill.setAbility(abiJson.getInt("ability"));
                    skill.setTime(abiJson.getInt("time"));
                    skill.setLevel(abiJson.getInt("level"));
                    pl.addAbilityUpgrades(skill);
                }
                h.addPlayers(pl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String convertSecondToHour(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;
        if (hours > 0) {
            return String.format(Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
        }

    }

    private String modeCodeToString (int code){
        switch (code){
            case 1:
                return "All Pick";
            case 22:
                return "All Pick";
            case 2:
                return "Captain's Mode";
            case 3:
                return "Random Draft";
            case 4:
                return "Single Draft";
            case 5:
                return "All random";
            case 6:
                return "Intro";
            case 7:
                return "Directide";
            case 8:
                return "Reverse Captain's Mode";
            case 9:
                return "The Greeviling";
            case 10:
                return "Tutorial";
            case 11:
                return "Mid only";
            case 12:
                return "Least played";
            case 13:
                return "New Player Pool";
            case 14:
                return "Compendium Matchmaking";
            case 15:
                return "Co-op vs Bots";
            case 16:
                return "Captains Draft";
            case 18:
                return "Ability Draft";
            case 20:
                return "All Random Deatmatch";
            case 21:
                return "1v1 Mid Only";
            default:
                return "";
        }

    }
}
