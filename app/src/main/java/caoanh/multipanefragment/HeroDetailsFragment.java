package caoanh.multipanefragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeroDetailsFragment extends Fragment {

    final static String ARG_POSITION = "position";
    int currentPosition = 0;
    View v ;
    public HeroDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if(savedInstanceState != null){
            currentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.fragment_hero_details, container, false);
        return v;
    }

    public void onStart(){
        super.onStart();
        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if(args != null){
            updateView(args.getInt(ARG_POSITION));
        }else if (currentPosition != -1) {
            updateView(currentPosition);
        }

    }

    public void updateView(int position) {
        TextView heroName = (TextView)v.findViewById(R.id.fragment_hero_details_hero_name);
        heroName.setText(Ipsum.Articles[position]);
        currentPosition = position;
    }

    public void onSaveInstanceState(Bundle saveState){
        super.onSaveInstanceState(saveState);

        // Save the current article selection in case we need to recreate the fragment
        saveState.putInt(ARG_POSITION, currentPosition);
    }
}
