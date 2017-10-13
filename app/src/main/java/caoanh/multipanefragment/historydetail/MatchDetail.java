package caoanh.multipanefragment.historydetail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import caoanh.multipanefragment.R;
import caoanh.multipanefragment.list_history.History;


public class MatchDetail extends AppCompatActivity {
    public static final String intentExtra = "data";
    private TextView victoryBanner;
    private History history;
    private ListView generalInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        victoryBanner = (TextView)findViewById(R.id.content_match_detail_victory_banner);

        history = (History)getIntent().getSerializableExtra(MatchDetail.intentExtra);
        victoryBanner.setText(history.isRadiantWin() ? "Radiant Victory" : "Dire Victory");
        victoryBanner.setTextColor(ResourcesCompat.getColor(getResources(), history.isRadiantWin() ? R.color.blueRadiant : R.color.redDire, null));
        generalInfoList = (ListView)findViewById(R.id.content_match_detail_general_info);
        GeneralInfoAdapter adapter = new GeneralInfoAdapter(this, history.getPlayers());
        generalInfoList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //simulate the physical back button
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
