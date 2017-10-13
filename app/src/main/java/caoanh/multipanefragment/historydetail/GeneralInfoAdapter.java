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

import java.util.List;

import caoanh.multipanefragment.R;
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
        if(player.getPlayerSlot() == 0){
            viewHolder.fraction.setText(context.getResources().getText(R.string.radiant));
            viewHolder.fraction.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.blueRadiant, null));
            viewHolder.fraction.setVisibility(View.VISIBLE);
        }else if(player.getPlayerSlot() == 128){
            viewHolder.fraction.setText(context.getResources().getText(R.string.dire));
            viewHolder.fraction.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.redDire, null));
            viewHolder.fraction.setVisibility(View.VISIBLE);
        }
        viewHolder.avatar.setImageResource(context.getResources().getIdentifier("hero_" + player.getHeroId(), "drawable", context.getPackageName()));
        viewHolder.kda.setText("K/D/A: " + player.getKills() + "/" + player.getDeaths() + "/" + player.getAssists());
        viewHolder.level.setText("Level: "+ player.getLevel());
        viewHolder.gpmXpm.setText("GPM/XPM: " + player.getGpm() + "/" + player.getXpm());
        viewHolder.misc.setText("DMG: "+ player.getHeroDamage() + "   Heal: "+ player.getHeroHealing()+ "   BLD: "+ player.getTowerDamage());
        for(ImageView itemView: viewHolder.items){
            itemView.setVisibility(View.GONE);
        }
        if (player.getItem0() != 0){
            viewHolder.items[0].setVisibility(View.VISIBLE);
            viewHolder.items[0].setImageResource(convertView.getResources().getIdentifier("item_"+player.getItem0(),"drawable", context.getPackageName()));
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
    }

    private void setViewHolder(ViewHolder viewHolder, View view) {
        viewHolder.fraction = (TextView)view.findViewById(R.id.content_match_detail_general_info_cell_fraction);
        viewHolder.avatar = (ImageView) view.findViewById(R.id.content_match_detail_general_info_cell_avatar);
        viewHolder.kda = (TextView)view.findViewById(R.id.content_match_detail_general_info_cell_kda);
        viewHolder.level = (TextView)view.findViewById(R.id.content_match_detail_general_info_cell_level);
        viewHolder.gpmXpm = (TextView)view.findViewById(R.id.content_match_detail_general_info_cell_gpm_xpm);
        viewHolder.misc = (TextView)view.findViewById(R.id.content_match_detail_general_info_cell_misc);
        viewHolder.items[0] = (ImageView)view.findViewById(R.id.content_match_detail_general_info_cell_item_0);
        viewHolder.items[1] = (ImageView)view.findViewById(R.id.content_match_detail_general_info_cell_item_1);
        viewHolder.items[2] = (ImageView)view.findViewById(R.id.content_match_detail_general_info_cell_item_2);
        viewHolder.items[3] = (ImageView)view.findViewById(R.id.content_match_detail_general_info_cell_item_3);
        viewHolder.items[4] = (ImageView)view.findViewById(R.id.content_match_detail_general_info_cell_item_4);
        viewHolder.items[5] = (ImageView)view.findViewById(R.id.content_match_detail_general_info_cell_item_5);
    }

}
