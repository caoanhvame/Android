package caoanh.multipanefragment.hero_detail;


import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import caoanh.multipanefragment.R;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("deprecation")
public class HeroSkillsFragment extends Fragment {

    private HeroDetailObject heroDetail;
    private LinearLayout bigLayout;
    private ScrollView scrollView;
    private Context context;
    private Integer skillClicked;
    private static final int PADDING_LEFT = 20;
    private static final String PREFIX= "<font size=3 color=#1E90FF>";
    private static final String AFFFIX= "</font>";
    public HeroSkillsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hero_skills, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        context = getActivity();
        heroDetail = ((HeroDetailActivity) activity).heroDetail;
        bigLayout = (LinearLayout) activity.findViewById(R.id.fragment_hero_skill_big_layout);
        scrollView = (ScrollView) activity.findViewById(R.id.fragment_hero_skill_scroll_view);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            skillClicked = bundle.getInt("Skill_Name", 0);
        }
        for (final Ability ability : heroDetail.getAbilityList()) {
            final RelativeLayout upperLayout = new RelativeLayout(activity);
            RelativeLayout.LayoutParams LLParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            upperLayout.setLayoutParams(LLParams);
            upperLayout.setId(R.id.upper_layout);

            RelativeLayout lowerLayout = new RelativeLayout(activity);
            RelativeLayout.LayoutParams lowerLayoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lowerLayoutParam.addRule(RelativeLayout.BELOW, R.id.upper_layout);
            lowerLayout.setLayoutParams(lowerLayoutParam);
            /*-------*/
            TextView cmb = new TextView(activity);
            RelativeLayout.LayoutParams cmbParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            cmb.setLayoutParams(cmbParam);
            cmb.setPadding(PADDING_LEFT,0,0,0);
            cmb.setId(R.id.skil_cd_mana);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cmb.setText(Html.fromHtml(ability.getCmb(), Html.FROM_HTML_MODE_COMPACT, new ImageGetter(), null));
            } else {
                cmb.setText(Html.fromHtml(ability.getCmb(), new ImageGetter(), null));
            }
            /*------*/
            ImageView image = new ImageView(activity);
            image.setScaleX(0.75f);
            image.setScaleY(0.75f);
            image.setId(R.id.skill_image);
            image.setImageResource(context.getResources().getIdentifier(ability.getImage(), "drawable", context.getPackageName()));
            /*------*/
            TextView dname = new TextView(activity);
            RelativeLayout.LayoutParams dnameParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            dnameParam.addRule(RelativeLayout.RIGHT_OF, R.id.skill_image);
            dname.setId(R.id.skill_name);
            dname.setLayoutParams(dnameParam);
            dname.setText(ability.getDname());
            dname.setTextSize(17);
            dname.setTypeface(dname.getTypeface(), Typeface.BOLD);
            /*------*/
            TextView desc = new TextView(activity);
            RelativeLayout.LayoutParams descParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            descParam.addRule(RelativeLayout.RIGHT_OF, R.id.skill_image);
            descParam.addRule(RelativeLayout.BELOW, R.id.skill_name);
            desc.setLayoutParams(descParam);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                desc.setText(Html.fromHtml(ability.getDesc(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                desc.setText(Html.fromHtml(ability.getDesc()));
            }
            /*------*/
            TextView dmgAndAttr = new TextView(activity);
            RelativeLayout.LayoutParams dmgAndAttrParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            dmgAndAttrParam.addRule(RelativeLayout.BELOW, R.id.skil_cd_mana);
            dmgAndAttr.setId(R.id.skill_damage_and_attr);
            dmgAndAttr.setPadding(PADDING_LEFT ,0,0,0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dmgAndAttr.setText(Html.fromHtml(ability.getDmg() + ability.getAttributes() + ability.getAffects() + PREFIX +ability.getNotes() + AFFFIX, Html.FROM_HTML_MODE_COMPACT));
            } else {
                dmgAndAttr.setText(Html.fromHtml(ability.getDmg() + ability.getAttributes() + ability.getAffects()+ PREFIX +ability.getNotes() + AFFFIX));
            }
            dmgAndAttr.setLayoutParams(dmgAndAttrParam);
            /*---*/
            final TextView lore = new TextView(activity);
            RelativeLayout.LayoutParams loreParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            loreParam.addRule(RelativeLayout.BELOW, R.id.skill_damage_and_attr);
            lore.setLayoutParams(loreParam);
            lore.setPadding(PADDING_LEFT ,0,0,0);
            lore.setTypeface(lore.getTypeface(), Typeface.ITALIC);
            lore.setText(ability.getLore());
            scrollView.scrollTo(0, lore.getBottom());
            /*---*/
            upperLayout.addView(image);
            upperLayout.addView(dname);
            upperLayout.addView(desc);
            lowerLayout.addView(cmb);
            //passive skill
            if(ability.getCmb().equals("")){
                cmb.setVisibility(View.GONE);
            }
            lowerLayout.addView(dmgAndAttr);
            lowerLayout.addView(lore);
            bigLayout.addView(upperLayout);
            bigLayout.addView(lowerLayout);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    if(ability.getId() == skillClicked) {
                        scrollView.scrollTo(0, upperLayout.getTop());
                    }
                }
            });
        }
    }

    private class ImageGetter implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int id;

            switch (source) {
                case "mana":
                    id = R.drawable.mana;
                    break;
                case "cooldown":
                    id = R.drawable.cooldown;
                    break;
                default:
                    return null;
            }

            Drawable d = getResources().getDrawable(id);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            return d;
        }
    }

}
