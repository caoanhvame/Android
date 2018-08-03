package caoanh.multipanefragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class LandingFragment extends Fragment {

	private OnMenuItemClickListener listener;


	public LandingFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_landing, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LinearLayout layout = (LinearLayout)getActivity().findViewById(R.id.fragment_landing_menu);
		final int childCount = layout.getChildCount();
		for(int i = 0 ;i< childCount ; i++){
			final LinearLayout outerLayout = (LinearLayout)layout.getChildAt(i);
			for(int j=0; j< outerLayout.getChildCount(); j++) {
				final View child = outerLayout.getChildAt(j);
				child.setOnTouchListener(new OnTouchListener() {
					boolean animationCompleted = false;

					@Override
					public boolean onTouch(final View v, final MotionEvent event) {
						Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.myanimation);
						animation.setFillAfter(true);
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							child.startAnimation(animation);
							animation.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {

								}

								@Override
								public void onAnimationRepeat(Animation animation) {
								}

								@Override
								public void onAnimationEnd(Animation animation) {
									//press & release before animation completed
									if (event.getAction() == MotionEvent.ACTION_UP) {
										String tag = (String) v.getTag();
										listener.onMenuClick(tag);
									}
									animationCompleted = true;
								}
							});
						} else if (event.getAction() == MotionEvent.ACTION_UP) {
							animation.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {

								}

								@Override
								public void onAnimationRepeat(Animation animation) {
								}

								@Override
								public void onAnimationEnd(Animation animation) {
									//release but wait for animation compleate
									String tag = (String) v.getTag();
									listener.onMenuClick(tag);
								}
							});
							//press & hold until animation completed and release
							if (animationCompleted) {
								String tag = (String) v.getTag();
								listener.onMenuClick(tag);
							}
							v.performClick();
						}
						return true;
					}
				});
			}
		}

	}

	public interface OnMenuItemClickListener {
		void onMenuClick(String tag);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnMenuItemClickListener) {
			listener = (OnMenuItemClickListener) context;
		}
		else {
			throw new RuntimeException(context.toString()
					+ " must implement OnHeroListFragmentListener");
		}
	}
}
