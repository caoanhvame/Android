package caoanh.multipanefragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnHeroListFragmentListener} interface
 * to handle interaction events.
 */
public class HeroListFragmentOld extends ListFragment implements AdapterView.OnItemClickListener {

    private OnHeroListFragmentListener mListener;
    private String[] allTitles;
    private TypedArray allImages;
    private List<RowItem> allItems;
    private CustomAdapter adapter;

    public HeroListFragmentOld() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        allTitles = getResources().getStringArray(R.array.titles);
        allImages = getResources().obtainTypedArray(R.array.icons);

        allItems = new ArrayList<RowItem>();
        for (int i = 0; i < allTitles.length; i++) {
            RowItem rowItem = new RowItem(allTitles[i], allImages.getResourceId(i, -1));
            allItems.add(rowItem);
        }

        adapter = new CustomAdapter(getActivity(), allItems);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    public void onStart() {
        super.onStart();
        if (getFragmentManager().findFragmentById(R.id.fragment_hero_details_hero_name) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnHeroListFragmentListener) {
            mListener = (OnHeroListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHeroListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mListener.onHeroClick(i);
    }

    public interface OnHeroListFragmentListener {
        void onHeroClick(int position);
    }
}
