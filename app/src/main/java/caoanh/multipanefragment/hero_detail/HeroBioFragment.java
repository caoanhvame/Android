package caoanh.multipanefragment.hero_detail;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import caoanh.multipanefragment.R;

import static caoanh.multipanefragment.Constants.ONE_DECIMAL_FORMAT;
import static java.lang.String.format;
import static java.util.Locale.getDefault;


public class HeroBioFragment extends Fragment {

    private static OnSkillClickCallBack callBack;
    private TextView lore;
    private TextView hpText;
    private TextView manaText;
    private TextView strength;
    private TextView agility;
    private TextView intelligence;
    private TextView moveSpeed;
    private TextView attack;
    private TextView defense;
    private TextView strengthGain;
    private TextView agilityGain;
    private TextView intelligenceGain;
    private TextView turnRate;
    private TextView attackRange;
    private TextView bat;
    private ImageView avatar;
    private ImageView skill1;
    private ImageView skill2;
    private ImageView skill3;
    private ImageView skill4;
    private int id;
    private HeroDetailObject heroDetail;
    private int level;
    private static final int HP_BASE = 200;
    private static final int MANA_BASE = 75;
    private static final int HP_GAIN_PER_STRENGTH = 20;
    private static final int MANA_GAIN_PER_INTELLIGENCE = 11;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hero_bio, container ,false);
    }


    @Override
    public void onAttach(Context  context) {
        super.onAttach(context);
        Activity activity;
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if (context instanceof Activity){
            activity=(Activity) context;
            try {
                callBack = (OnSkillClickCallBack) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("Id", 0);
        }
        FragmentActivity activity = getActivity();

        lore = (TextView) activity.findViewById(R.id.fragment_hero_bio_lore);
        hpText = (TextView) activity.findViewById(R.id.hp_text);
        manaText = (TextView) activity.findViewById(R.id.mana_text);
        strength = (TextView) activity.findViewById(R.id.fragment_hero_bio_strength);
        agility = (TextView) activity.findViewById(R.id.activity_hero_bio_agility);
        intelligence = (TextView) activity.findViewById(R.id.activity_hero_bio_intelligence);
        moveSpeed = (TextView) activity.findViewById(R.id.activity_hero_bio_speed);
        attack = (TextView) activity.findViewById(R.id.activity_hero_bio_attack);
        defense = (TextView) activity.findViewById(R.id.activity_hero_bio_defense);
        strengthGain = (TextView) activity.findViewById(R.id.fragment_hero_bio_strength_gain);
        agilityGain = (TextView) activity.findViewById(R.id.fragment_hero_bio_agility_gain);
        intelligenceGain = (TextView) activity.findViewById(R.id.fragment_hero_bio_intelligence_gain);
        turnRate = (TextView) activity.findViewById(R.id.fragment_hero_bio_turn_rate);
        attackRange = (TextView) activity.findViewById(R.id.fragment_hero_bio_attack_range);
        bat = (TextView) activity.findViewById(R.id.fragment_hero_bio_bat);
        avatar = (ImageView) activity.findViewById(R.id.activity_hero_bio_avatar);
        skill1 = (ImageView) activity.findViewById(R.id.fragment_hero_bio_skill_1);
        skill2 = (ImageView) activity.findViewById(R.id.fragment_hero_bio_skill_2);
        skill3 = (ImageView) activity.findViewById(R.id.fragment_hero_bio_skill_3);
        skill4 = (ImageView) activity.findViewById(R.id.fragment_hero_bio_skill_4);
        ProgressBar hp = (ProgressBar) activity.findViewById(R.id.activity_hero_bio_hp);
        ProgressBar mana = (ProgressBar) activity.findViewById(R.id.activity_hero_bio_mana);
        SeekBar levelSeekbar = (SeekBar) activity.findViewById(R.id.fragment_hero_bio_level);
        levelSeekbar.setOnSeekBarChangeListener(new levelBarListener());
        hp.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        mana.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        new ParseItem(this, getResources()).execute();
    }


    private class levelBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            level = progress + 1;
            hpText.setText(format(getResources().getString(R.string.hp), calculateHP()));
            manaText.setText(format(getResources().getString(R.string.mp), calculateMana()));
            int[] attackValue = calculateAttackBaseOnPrimaryAttribute();
            attack.setText(format(getResources().getString(R.string.damage_range), attackValue[0], attackValue[1]));
            defense.setText(format(getDefault(),"%1d", calculateDefense()));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private static class ParseItem extends AsyncTask<Void, Void, Void> {
        private WeakReference<HeroBioFragment> fragmentWeakReference;
        private HeroBioFragment fragment;
        private Resources resources;
        ParseItem(HeroBioFragment heroBioFragment, Resources resources) {
            fragmentWeakReference = new WeakReference<>(heroBioFragment);
            fragment = fragmentWeakReference.get();
            this.resources = resources;
        }

        @Override
        protected Void doInBackground(Void... params) {
            int imageRes = fragment.getResources().getIdentifier("hero_" + fragment.id
                    , "raw", fragment.getContext().getPackageName());

            InputStream is = resources.openRawResource(imageRes);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            String jsonString = writer.toString();
            try {
                parseJson(jsonString);
            } catch (Exception e) {
                Log.e("ERROR", "Json parsing error: " + e.getMessage());
            }

            return null;
        }

        private void parseJson(String jsonString) throws JSONException {
            JSONObject jsonObject = new JSONObject(jsonString);
            fragment.heroDetail = new HeroDetailObject();
            fragment.heroDetail.setId(jsonObject.getInt("Id"));
            fragment.heroDetail.setBio(jsonObject.getString("Bio"));
            fragment.heroDetail.setPrimaryAttribute(jsonObject.getString("PrimaryAttribute"));
            fragment.heroDetail.setStrengthIni(jsonObject.getInt("StrengthIni"));
            fragment.heroDetail.setStrengthGain(jsonObject.getDouble("StrengthGain"));
            fragment.heroDetail.setAgilityIni(jsonObject.getInt("AgilityIni"));
            fragment.heroDetail.setAgilityGain(jsonObject.getDouble("AgilityGain"));
            fragment.heroDetail.setIntelligenceIni(jsonObject.getInt("IntelligenceIni"));
            fragment.heroDetail.setIntelligenceGain(jsonObject.getDouble("IntelligenceGain"));
            fragment.heroDetail.setSpeed(jsonObject.getInt("Speed"));
            fragment.heroDetail.setAttackMinIni(jsonObject.getInt("AttackMinIni"));
            fragment.heroDetail.setAttackMaxIni(jsonObject.getInt("AttackMaxIni"));
            fragment.heroDetail.setDefenseIni(jsonObject.getInt("DefenseIni"));
            fragment.heroDetail.setTurnRate(jsonObject.getDouble("TurnRate"));
            fragment.heroDetail.setAttackRange(jsonObject.getInt("AttackRange"));
            fragment.heroDetail.setBaseAttackTime(jsonObject.getDouble("BAT"));
            JSONArray abilities = jsonObject.getJSONArray("Abilities");
            for (int i = 0; i < abilities.length(); i++) {
                JSONObject abilityJson = abilities.getJSONObject(i);
                Ability ability = new Ability();
                ability.setId(abilityJson.getInt("id"));
                ability.setImage(abilityJson.getString("image"));
                ability.setDname(abilityJson.getString("dname"));
                ability.setAffects(abilityJson.getString("affects"));
                ability.setDesc(abilityJson.getString("desc"));
                ability.setNotes(abilityJson.getString("notes"));
                ability.setDmg(abilityJson.getString("dmg"));
                ability.setAttributes(abilityJson.getString("attribute"));
                ability.setCmb(abilityJson.getString("cmb"));
                ability.setLore(abilityJson.getString("lore"));
                ability.setMainSkill(abilityJson.getInt("mainSkill") != 0);
                fragment.heroDetail.getAbilities().add(ability);
            }
            JSONArray taletns = jsonObject.getJSONArray("Talents");
            for (int i = 0; i < taletns.length() ; i++) {
                JSONObject talent = taletns.getJSONObject(i);
                Skill s = new Skill();
                s.setId(talent.getInt("id"));
                s.setDesc(talent.getString("desc"));
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ((HeroDetailActivity)fragment.getActivity()).heroDetail = fragment.heroDetail;
            fragment.level = 1;
            fragment.lore.setText(fragment.heroDetail.getBio());
            fragment.avatar.setImageResource(fragment.getResources().getIdentifier("hero_" + fragment.heroDetail.getId(), "drawable", fragment.getContext().getPackageName()));
            fragment.hpText.setText(format(resources.getString(R.string.hp), fragment.calculateHP()));
            fragment.manaText.setText(format(resources.getString(R.string.mp), fragment.calculateMana()));
            fragment.strength.setText(format(getDefault(),"%1d", fragment.heroDetail.getStrengthIni()));
            fragment.agility.setText(format(getDefault(),"%1d", fragment.heroDetail.getAgilityIni()));
            fragment.intelligence.setText(format(getDefault(),"%1d", fragment.heroDetail.getIntelligenceIni()));
            fragment. moveSpeed.setText(format(getDefault(), "%1d", fragment.heroDetail.getSpeed()));
            int[] attackValue = fragment.calculateAttackBaseOnPrimaryAttribute();
            fragment.attack.setText(format(resources.getString(R.string.damage_range), attackValue[0], attackValue[1]));
            fragment.defense.setText(format(getDefault(), ONE_DECIMAL_FORMAT, fragment.calculateDefense()));
            fragment.strengthGain.setText(format(resources.getString(R.string.strength_gain), Double.toString(fragment.heroDetail.getStrengthGain())));
            fragment.agilityGain.setText(format(resources.getString(R.string.agility_gain), Double.toString(fragment.heroDetail.getAgilityGain())));
            fragment.intelligenceGain.setText(format(resources.getString(R.string.intelligence_gain), Double.toString(fragment.heroDetail.getIntelligenceGain())));
            fragment.turnRate.setText(format(resources.getString(R.string.turnrate), Double.toString(fragment.heroDetail.getTurnRate())));
            fragment.attackRange.setText(format(resources.getString(R.string.range), fragment.heroDetail.getAttackRange()));
            fragment.bat.setText(format(resources.getString(R.string.bat), Double.toString(fragment.heroDetail.getBaseAttackTime())));
            List<Ability> abilitiesList = new ArrayList<>();
            for (Ability ability: fragment.heroDetail.getAbilities()) {
                if(ability.isMainSkill()){
                    abilitiesList.add(ability);
                }
            }
            OnSkillClickListener skillClickListener = new OnSkillClickListener();

            fragment.skill1.setImageResource(fragment.getResources().getIdentifier(abilitiesList.get(0).getImage(), "drawable", fragment.getContext().getPackageName()));
            fragment.skill1.setTag(abilitiesList.get(0).getId());
            fragment.skill1.setOnClickListener (skillClickListener);

            fragment.skill2.setImageResource(fragment.getResources().getIdentifier(abilitiesList.get(1).getImage(), "drawable", fragment.getContext().getPackageName()));
            fragment.skill2.setOnClickListener (skillClickListener);
            fragment.skill2.setTag(abilitiesList.get(1).getId());

            fragment.skill3.setImageResource(fragment.getResources().getIdentifier(abilitiesList.get(2).getImage(), "drawable", fragment.getContext().getPackageName()));
            fragment.skill3.setOnClickListener (skillClickListener);
            fragment.skill3.setTag(abilitiesList.get(2).getId());

            fragment.skill4.setImageResource(fragment.getResources().getIdentifier(abilitiesList.get(3).getImage(), "drawable", fragment.getContext().getPackageName()));
            fragment.skill4.setOnClickListener (skillClickListener);
            fragment.skill4.setTag(abilitiesList.get(3).getId());
        }
        private class OnSkillClickListener implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                callBack.onSkillClicked((Integer)v.getTag());
            }
        }
    }

    private int calculateDefense() {
        int defense;
        defense = (int) Math.round(heroDetail.getDefenseIni() + (heroDetail.getAgilityIni() + heroDetail.getAgilityGain() * level) / 7);
        return defense;
    }

    private int[] calculateAttackBaseOnPrimaryAttribute() {
        int attack[] = new int[2];
        switch (heroDetail.getPrimaryAttribute()) {
            case "Strength":
                attack[0] = (heroDetail.getAttackMinIni() + heroDetail.getStrengthIni() + (int) (heroDetail.getStrengthGain() * (level - 1)));
                attack[1] = (heroDetail.getAttackMaxIni() + heroDetail.getStrengthIni() + (int) (heroDetail.getStrengthGain() * (level - 1)));
                break;
            case "Agility":
                attack[0] = (heroDetail.getAttackMinIni() + heroDetail.getAgilityIni() + (int) (heroDetail.getAgilityGain() * (level - 1)));
                attack[1] = (heroDetail.getAttackMaxIni() + heroDetail.getAgilityIni() + (int) (heroDetail.getAgilityGain() * (level - 1)));
                break;
            case "Intelligence":
                attack[0] = (heroDetail.getAttackMinIni() + heroDetail.getIntelligenceIni() + (int) (heroDetail.getIntelligenceGain() * (level - 1)));
                attack[1] = (heroDetail.getAttackMaxIni() + heroDetail.getIntelligenceIni() + (int) (heroDetail.getIntelligenceGain() * (level - 1)));
                break;
        }
        return attack;
    }

    private int calculateHP() {
        int hp;
        hp = HeroBioFragment.HP_BASE + HeroBioFragment.HP_GAIN_PER_STRENGTH * (heroDetail.getStrengthIni() + (int) (heroDetail.getStrengthGain() * (level - 1)));
        return hp;
    }

    private int calculateMana() {
        int mana;
        double manaNotRounded = HeroBioFragment.MANA_BASE + HeroBioFragment.MANA_GAIN_PER_INTELLIGENCE * (heroDetail.getIntelligenceIni() + heroDetail.getIntelligenceGain() * (level - 1));
        mana = (int) Math.round(manaNotRounded);
        return mana;
    }

    public interface OnSkillClickCallBack {
        void onSkillClicked(Integer id);
    }

}
