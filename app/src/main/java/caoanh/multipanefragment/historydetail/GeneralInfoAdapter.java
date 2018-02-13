package caoanh.multipanefragment.historydetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import caoanh.multipanefragment.R;
import caoanh.multipanefragment.list_history.AbilityUpgrade;
import caoanh.multipanefragment.list_history.Player;

/**
 * Created by anhhpc on 10/4/2017.
 */


class GeneralInfoAdapter extends ArrayAdapter<Player> {

    private Context context;
    private List<Player> data;

    GeneralInfoAdapter(Context context, List<Player> data) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_match_detail_general_info_cell, parent, false);
            viewHolder = new ViewHolder();
            setViewHolder(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.fraction.setVisibility(View.GONE);
        Player player = data.get(position);
        if (player.getPlayerSlot() == 0) {
            viewHolder.fraction.setText(context.getResources().getText(R.string.radiant));
            viewHolder.fraction.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.blueRadiant, null));
            viewHolder.fraction.setVisibility(View.VISIBLE);
        } else if (player.getPlayerSlot() == 128) {
            viewHolder.fraction.setText(context.getResources().getText(R.string.dire));
            viewHolder.fraction.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.redDire, null));
            viewHolder.fraction.setVisibility(View.VISIBLE);
        }
        viewHolder.avatar.setImageResource(context.getResources().getIdentifier("hero_" + player.getHeroId(), "drawable", context.getPackageName()));
        viewHolder.kda.setText("K/D/A: " + player.getKills() + "/" + player.getDeaths() + "/" + player.getAssists());
        viewHolder.level.setText("Level: " + player.getLevel());
        viewHolder.gpmXpm.setText("GPM/XPM: " + player.getGpm() + "/" + player.getXpm());
        viewHolder.misc.setText("DMG: " + player.getHeroDamage() + "   Heal: " + player.getHeroHealing() + "   BLD: " + player.getTowerDamage());
        for (ImageView itemView : viewHolder.items) {
            itemView.setVisibility(View.GONE);
        }
        resetItemImageViewVisible(viewHolder);
        for(TextView textView : viewHolder.skills){
            textView.setVisibility(View.GONE);
        }

        if (player.getItem0() != 0) {
            viewHolder.items[0].setVisibility(View.VISIBLE);
            viewHolder.items[0].setImageResource(convertView.getResources().getIdentifier("item_" + player.getItem0(), "drawable", context.getPackageName()));
        }
        if (player.getItem1() != 0) {
            viewHolder.items[1].setVisibility(View.VISIBLE);
            viewHolder.items[1].setImageResource(convertView.getResources().getIdentifier("item_" + player.getItem1(), "drawable", context.getPackageName()));
        }
        if (player.getItem2() != 0) {
            viewHolder.items[2].setVisibility(View.VISIBLE);
            viewHolder.items[2].setImageResource(convertView.getResources().getIdentifier("item_" + player.getItem2(), "drawable", context.getPackageName()));
        }
        if (player.getItem3() != 0) {
            viewHolder.items[3].setVisibility(View.VISIBLE);
            viewHolder.items[3].setImageResource(convertView.getResources().getIdentifier("item_" + player.getItem3(), "drawable", context.getPackageName()));
        }
        if (player.getItem4() != 0) {
            viewHolder.items[4].setVisibility(View.VISIBLE);
            viewHolder.items[4].setImageResource(convertView.getResources().getIdentifier("item_" + player.getItem4(), "drawable", context.getPackageName()));
        }
        if (player.getItem5() != 0) {
            viewHolder.items[5].setVisibility(View.VISIBLE);
            viewHolder.items[5].setImageResource(convertView.getResources().getIdentifier("item_" + player.getItem5(), "drawable", context.getPackageName()));
        }
        List<AbilityUpgrade> abilityList = player.getAbilityUpgradeUpgrades();
        for (int i= 0; i< abilityList.size(); i++) {
            AbilityUpgrade ability = abilityList.get(i);
            viewHolder.skills[i].setVisibility(View.VISIBLE);
            viewHolder.skills[i].setBackgroundResource(convertView.getResources().getIdentifier("skill_"+ ability.getAbility(),"drawable", context.getPackageName()));
            viewHolder.skills[i].setText((i+1)+"");
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView fraction;
        ImageView avatar;
        TextView kda;
        TextView level;
        TextView gpmXpm;
        TextView misc;
        ImageView[] items = new ImageView[6];
        TextView[] skills = new TextView[25];
    }

    private void setViewHolder(ViewHolder viewHolder, View view) {
        viewHolder.fraction = (TextView) view.findViewById(R.id.content_match_detail_general_info_cell_fraction);
        viewHolder.avatar = (ImageView) view.findViewById(R.id.content_match_detail_general_info_cell_avatar);
        viewHolder.kda = (TextView) view.findViewById(R.id.content_match_detail_general_info_cell_kda);
        viewHolder.level = (TextView) view.findViewById(R.id.content_match_detail_general_info_cell_level);
        viewHolder.gpmXpm = (TextView) view.findViewById(R.id.content_match_detail_general_info_cell_gpm_xpm);
        viewHolder.misc = (TextView) view.findViewById(R.id.content_match_detail_general_info_cell_misc);
        viewHolder.items[0] = (ImageView) view.findViewById(R.id.content_match_detail_general_info_cell_item_0);
        viewHolder.items[1] = (ImageView) view.findViewById(R.id.content_match_detail_general_info_cell_item_1);
        viewHolder.items[2] = (ImageView) view.findViewById(R.id.content_match_detail_general_info_cell_item_2);
        viewHolder.items[3] = (ImageView) view.findViewById(R.id.content_match_detail_general_info_cell_item_3);
        viewHolder.items[4] = (ImageView) view.findViewById(R.id.content_match_detail_general_info_cell_item_4);
        viewHolder.items[5] = (ImageView) view.findViewById(R.id.content_match_detail_general_info_cell_item_5);
        viewHolder.skills[0] = (TextView) view.findViewById(R.id.skill_level1);
        viewHolder.skills[1] = (TextView) view.findViewById(R.id.skill_level2);
        viewHolder.skills[2] = (TextView) view.findViewById(R.id.skill_level3);
        viewHolder.skills[3] = (TextView) view.findViewById(R.id.skill_level4);
        viewHolder.skills[4] = (TextView) view.findViewById(R.id.skill_level5);
        viewHolder.skills[5] = (TextView) view.findViewById(R.id.skill_level6);
        viewHolder.skills[6] = (TextView) view.findViewById(R.id.skill_level7);
        viewHolder.skills[7] = (TextView) view.findViewById(R.id.skill_level8);
        viewHolder.skills[8] = (TextView) view.findViewById(R.id.skill_level9);
        viewHolder.skills[9] = (TextView) view.findViewById(R.id.skill_level10);
        viewHolder.skills[10] = (TextView) view.findViewById(R.id.skill_level11);
        viewHolder.skills[11] = (TextView) view.findViewById(R.id.skill_level12);
        viewHolder.skills[12] = (TextView) view.findViewById(R.id.skill_level13);
        viewHolder.skills[13] = (TextView) view.findViewById(R.id.skill_level14);
        viewHolder.skills[14] = (TextView) view.findViewById(R.id.skill_level15);
        viewHolder.skills[15] = (TextView) view.findViewById(R.id.skill_level16);
        viewHolder.skills[16] = (TextView) view.findViewById(R.id.skill_level17);
        viewHolder.skills[17] = (TextView) view.findViewById(R.id.skill_level18);
        viewHolder.skills[18] = (TextView) view.findViewById(R.id.skill_level19);
        viewHolder.skills[19] = (TextView) view.findViewById(R.id.skill_level20);
        viewHolder.skills[20] = (TextView) view.findViewById(R.id.skill_level21);
        viewHolder.skills[21] = (TextView) view.findViewById(R.id.skill_level22);
        viewHolder.skills[22] = (TextView) view.findViewById(R.id.skill_level23);
        viewHolder.skills[23] = (TextView) view.findViewById(R.id.skill_level24);
        viewHolder.skills[24] = (TextView) view.findViewById(R.id.skill_level25);
    }

    private void resetItemImageViewVisible(ViewHolder viewHolder) {
        viewHolder.items[0].setVisibility(View.GONE);
        viewHolder.items[1].setVisibility(View.GONE);
        viewHolder.items[2].setVisibility(View.GONE);
        viewHolder.items[3].setVisibility(View.GONE);
        viewHolder.items[4].setVisibility(View.GONE);
        viewHolder.items[5].setVisibility(View.GONE);
    }
}
