package caoanh.multipanefragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import caoanh.multipanefragment.hero_detail.HeroBioFragment;
import caoanh.multipanefragment.hero_detail.HeroDetailObject;
import caoanh.multipanefragment.hero_detail.HeroSkillsFragment;


public class HeroDetailActivity extends AppCompatActivity implements  HeroBioFragment.OnSkillClickCallBack{
    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private int id;
    public HeroDetailObject heroDetail;
    private Integer skillIdClick=0;
    private FragmentTransaction transaction;
    private Map<Integer, Integer> bottomMenuOrderMap = new HashMap<Integer, Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        //SUPER IMPORTANT, IF NOT ICON NOT SHOWN CORRECTLY
        bottomNavigation.setItemIconTintList(null);
        bottomNavigation.inflateMenu(R.menu.bottom_menu);
        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        id = intent.getIntExtra("Id",0);
        //set the order of bottom menu for calculating transition animation
        bottomMenuOrderMap.put(R.id.hero_bio, 1);
        bottomMenuOrderMap.put(R.id.hero_skill, 2);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuId = item.getItemId();
                Bundle bundle = new Bundle();
                if(bottomNavigation.getSelectedItemId() != menuId) {
                    switch (menuId) {
                        case R.id.hero_bio:
                            fragment = new HeroBioFragment();
                            bundle.putInt("Id", id);
                            fragment.setArguments(bundle);
                            break;
                        case R.id.hero_skill:
                            fragment = new HeroSkillsFragment();
                            bundle = new Bundle();
                            bundle.putInt("Id", id);
                            bundle.putInt("Skill_Name", skillIdClick);
                            skillIdClick = 0;
                            fragment.setArguments(bundle);
                            break;
//                    case R.id.action_hot_deals:
//                        fragment = new DealsFragment();
//                        break;
                    }
                    transaction = fragmentManager.beginTransaction();
                    setAnimation(bottomMenuOrderMap.get(bottomNavigation.getSelectedItemId()), bottomMenuOrderMap.get(menuId));
                    transaction.replace(R.id.main_container, fragment).commit();
                }
                return true;
            }
        });
        transaction = fragmentManager.beginTransaction();
        fragment = new HeroBioFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Id", id);
        fragment.setArguments(bundle);
        transaction.replace(R.id.main_container, fragment).commit();
    }

    @Override
    public void onSkillClicked(Integer id) {
        skillIdClick = id;
        bottomNavigation.setSelectedItemId(R.id.hero_skill);
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

    private void setAnimation(Integer current, Integer newOne){
        if (current > newOne){
            transaction.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit, R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exist);
        }else{
            transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exist, R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
        }
    }
}
