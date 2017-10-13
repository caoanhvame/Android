package caoanh.multipanefragment.hero_detail;

import android.app.Activity;
import android.content.Context;
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
import java.util.ArrayList;
import java.util.List;

import caoanh.multipanefragment.R;


public class HeroBioFragment extends Fragment {

    private OnSkillClickCallBack callBack;
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
    private Context context;
    private ProgressBar hp;
    private ProgressBar mana;
    private SeekBar levelSeekbar;
    private HeroDetailObject heroDetail;
    private int level;
    private static final int HP_BASE = 200;
    private static final int MANA_BASE = 75;
    private static final int HP_GAIN_PER_STRENGTH = 20;
    private static final int MANA_GAIN_PER_INTELLIGENCE = 11;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hero_bio, null);
        return v;
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

        context = getActivity();
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
        hp = (ProgressBar) activity.findViewById(R.id.activity_hero_bio_hp);
        mana = (ProgressBar) activity.findViewById(R.id.activity_hero_bio_mana);
        levelSeekbar = (SeekBar) activity.findViewById(R.id.fragment_hero_bio_level);
        levelSeekbar.setOnSeekBarChangeListener(new levelBarListener());
        hp.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        mana.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        new ParseItem().execute();
    }


    private class levelBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            level = progress + 1;
            hpText.setText(calculateHP() + " hp");
            manaText.setText(calculateMana() + " mp");
            int[] attackValue = calculateAttackBaseOnPrimaryAttribute();
            attack.setText(attackValue[0] + " - " + attackValue[1]);
            defense.setText(calculateDefense() + "");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class ParseItem extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            int imageRes = context.getResources().getIdentifier("hero_" + id
                    , "raw", context.getPackageName());

            InputStream is = getResources().openRawResource(imageRes);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (Exception e) {
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
            heroDetail = new HeroDetailObject();
            heroDetail.setId(jsonObject.getInt("Id"));
            heroDetail.setBio(jsonObject.getString("Bio"));
            heroDetail.setPrimaryAttribute(jsonObject.getString("PrimaryAttribute"));
            heroDetail.setImage(jsonObject.getString("Image"));
            heroDetail.setStrengthIni(jsonObject.getInt("StrengthIni"));
            heroDetail.setStrengthGain(jsonObject.getDouble("StrengthGain"));
            heroDetail.setAgilityIni(jsonObject.getInt("AgilityIni"));
            heroDetail.setAgilityGain(jsonObject.getDouble("AgilityGain"));
            heroDetail.setIntelligenceIni(jsonObject.getInt("IntelligenceIni"));
            heroDetail.setIntelligenceGain(jsonObject.getDouble("IntelligenceGain"));
            heroDetail.setSpeed(jsonObject.getInt("Speed"));
            heroDetail.setAttackMinIni(jsonObject.getInt("AttackMinIni"));
            heroDetail.setAttackMaxIni(jsonObject.getInt("AttackMaxIni"));
            heroDetail.setDefenseIni(jsonObject.getInt("DefenseIni"));
            heroDetail.setTurnRate(jsonObject.getDouble("TurnRate"));
            heroDetail.setAttackRange(jsonObject.getInt("AttackRange"));
            heroDetail.setBaseAttackTime(jsonObject.getDouble("BAT"));
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
                ability.setMainSkill(abilityJson.getInt("mainSkill") == 0 ? false : true);
                heroDetail.getAbilityList().add(ability);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ((HeroDetailActivity)getActivity()).heroDetail = heroDetail;
            level = 1;
            lore.setText(heroDetail.getBio());
            avatar.setImageResource(context.getResources().getIdentifier("hero_" + heroDetail.getId(), "drawable", context.getPackageName()));
            hpText.setText(calculateHP() + " hp");
            manaText.setText(calculateMana() + " mp");
            strength.setText(heroDetail.getStrengthIni() + "");
            agility.setText(heroDetail.getAgilityIni() + "");
            intelligence.setText(heroDetail.getIntelligenceIni() + "");
            moveSpeed.setText(heroDetail.getSpeed() + "");
            int[] attackValue = calculateAttackBaseOnPrimaryAttribute();
            attack.setText(attackValue[0] + " - " + attackValue[1]);
            defense.setText(calculateDefense() + "");
            strengthGain.setText("STR+: " + heroDetail.getStrengthGain());
            agilityGain.setText("AGI+: " + heroDetail.getAgilityGain());
            intelligenceGain.setText("INT+: " + heroDetail.getIntelligenceGain());
            turnRate.setText("Turn rate: " + heroDetail.getTurnRate());
            attackRange.setText("Range: " + heroDetail.getAttackRange());
            bat.setText("BAT: " + heroDetail.getBaseAttackTime());
            List<Ability> abilitiesList = new ArrayList<>();
            for (Ability ability: heroDetail.getAbilityList()) {
                if(ability.isMainSkill()){
                    abilitiesList.add(ability);
                }
            }
            OnSkillClickListener skillClickListener = new OnSkillClickListener();
            skill1.setImageResource(context.getResources().getIdentifier(abilitiesList.get(0).getImage(), "drawable", context.getPackageName()));
            skill1.setTag(abilitiesList.get(0).getId());
            skill1.setOnClickListener (skillClickListener);

            skill2.setImageResource(context.getResources().getIdentifier(abilitiesList.get(1).getImage(), "drawable", context.getPackageName()));
            skill2.setOnClickListener (skillClickListener);
            skill2.setTag(abilitiesList.get(1).getId());

            skill3.setImageResource(context.getResources().getIdentifier(abilitiesList.get(2).getImage(), "drawable", context.getPackageName()));
            skill3.setOnClickListener (skillClickListener);
            skill3.setTag(abilitiesList.get(2).getId());

            skill4.setImageResource(context.getResources().getIdentifier(abilitiesList.get(3).getImage(), "drawable", context.getPackageName()));
            skill4.setOnClickListener (skillClickListener);
            skill4.setTag(abilitiesList.get(3).getId());
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
        public void onSkillClicked(Integer id);
    }
    private class OnSkillClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            callBack.onSkillClicked((Integer)v.getTag());
        }
    }
}
