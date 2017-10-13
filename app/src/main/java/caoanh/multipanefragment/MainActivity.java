package caoanh.multipanefragment;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.*;
import android.os.Bundle;

import caoanh.multipanefragment.expandable_list_hero.HeroListFragment;
import caoanh.multipanefragment.expandable_list_item.ItemListFragment;
import caoanh.multipanefragment.hero_detail.HeroDetailActivity;
import caoanh.multipanefragment.list_history.History;
import caoanh.multipanefragment.list_history.HistoryListFragment;
import caoanh.multipanefragment.historydetail.MatchDetail;

public class MainActivity extends AppCompatActivity implements
        LandingFragment.OnMenuItemClickListener, HeroListFragment.OnHeroClickListener, HistoryListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container) != null) {

//            HeroListFragment heroList = new HeroListFragment();

//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, heroList).commit();
//            ItemListFragment itemListFragment = new ItemListFragment();
            if(savedInstanceState==null) {
                LandingFragment landingFragment = new LandingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, landingFragment).commit();
            }
        }
    }

    @Override
    public void onMenuClick(String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exist, R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
        if (tag.equals(getResources().getString(R.string.hero))) {
            HeroListFragment heroListFragment = new HeroListFragment();
            transaction.replace(R.id.fragment_container, heroListFragment);
        } else if (tag.equals(getResources().getString(R.string.item))) {
            ItemListFragment itemListFragment = new ItemListFragment();
            transaction.replace(R.id.fragment_container, itemListFragment);
        } else if(tag.equals(getResources().getString(R.string.history))){
            HistoryListFragment historyListFragment = new HistoryListFragment();
            transaction.replace(R.id.fragment_container, historyListFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(this, HeroDetailActivity.class);
        intent.putExtra("Id", id);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(History history) {
        Intent intent = new Intent(this, MatchDetail.class);
        intent.putExtra(MatchDetail.intentExtra, history);
        startActivity(intent);
    }
}
