package caoanh.multipanefragment;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.*;
import android.os.Bundle;
import caoanh.multipanefragment.expandable_list_hero.HeroListFragment;
import caoanh.multipanefragment.expandable_list_item.ItemListFragment;

public class MainActivity extends AppCompatActivity implements HeroListFragmentOld.OnHeroListFragmentListener,
        LandingFragment.OnMenuItemClickListener, HeroListFragment.OnHeroClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.fragment_container) !=null){

//            HeroListFragment heroList = new HeroListFragment();

//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, heroList).commit();
//            ItemListFragment itemListFragment = new ItemListFragment();
        	LandingFragment landingFragment = new LandingFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, landingFragment).commit();
        }
    }

    @Override
    public void onHeroClick(int position) {
        HeroDetailsFragment heroDetailsFragment = (HeroDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.hero_detail_fragment);

        if(heroDetailsFragment != null && heroDetailsFragment.isInLayout()){
            heroDetailsFragment.updateView(position);
        }else{
            HeroDetailsFragment newFragment = new HeroDetailsFragment();

            Bundle args = new Bundle();
            args.putInt(HeroDetailsFragment.ARG_POSITION, position);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

	@Override
	public void onMenuClick(String tag) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exist, R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
		if(tag.equals("Hero")){
			HeroListFragment heroListFragment = new HeroListFragment();
			transaction.replace(R.id.fragment_container, heroListFragment);
		}else if(tag.equals("Item")){
			ItemListFragment itemListFragment = new ItemListFragment();
			transaction.replace(R.id.fragment_container, itemListFragment);		
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
}
