/*
 * Author            : AdNovum Informatik AG
 * Version Number    : $Revision: $
 * Date of last edit : $Date: $
 */

package caoanh.multipanefragment.expandable_list_item;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import caoanh.multipanefragment.R;

import static caoanh.multipanefragment.Constants.RECIPE_ITEM;

@SuppressWarnings("ConstantConditions, unchecked")
public class ItemExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<String> expandableListTitle;

    private HashMap<String, List<ItemObject>> expandableListDetail;

    private List<ItemObject> allItems;

    ItemExpandableListAdapter(Context context, List<String> expandableListTitle,
                              HashMap<String, List<ItemObject>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        fillAllItemsList();
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return expandableListDetail.get(expandableListTitle.get(listPosition)).get(
                expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
                             ViewGroup viewGroup) {
        final ItemObject item = (ItemObject) getChild(groupPosition, childPosition);
        ViewHolderFull holder;
        ViewHolderShort holder2;
        //		final ItemObject2 item = allItems.get(i);
        if (item.isExpand()) {
            if (view == null || view.getTag() instanceof ViewHolderShort) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.fragment_item_list_cell_full, viewGroup, false);
                holder = new ViewHolderFull();
                setViewHolderFull(holder, view);
                view.setTag(holder);
            } else {
                holder = (ViewHolderFull) view.getTag();
            }

            holder.image.setImageResource(context.getResources().getIdentifier(item.getImage(),
                    "drawable", context.getPackageName()));
            holder.name.setText(item.getName());
            holder.cost.setText(item.getCost());
            if (item.getCoolDown().equals("")) {
                holder.cooldown.setVisibility(View.GONE);
                holder.cooldownIcon.setVisibility(View.GONE);
            } else {
                holder.cooldown.setVisibility(View.VISIBLE);
                holder.cooldownIcon.setVisibility(View.VISIBLE);
                holder.cooldown.setText(item.getCoolDown());
            }
            if (item.getManaCost().equals("")) {
                holder.mana.setVisibility(View.GONE);
                holder.manaIcon.setVisibility(View.GONE);
            } else {
                holder.mana.setVisibility(View.VISIBLE);
                holder.manaIcon.setVisibility(View.VISIBLE);
                holder.mana.setText(item.getManaCost());
            }

            if (item.getMainDescription().equals("")) {
                holder.mainDescription.setVisibility(View.GONE);
            } else {
                holder.mainDescription.setVisibility(View.VISIBLE);
                holder.mainDescription.setText(item.getMainDescription());
            }
            if (item.getAlternateDescription().equals("")) {
                holder.altDescription.setVisibility(View.GONE);
            } else {
                holder.altDescription.setVisibility(View.VISIBLE);
                holder.altDescription.setText(item.getAlternateDescription());
            }
            if (item.getExtraAttribute().equals("")) {
                holder.extraAttr.setVisibility(View.GONE);
            } else {
                holder.extraAttr.setVisibility(View.VISIBLE);
                holder.extraAttr.setText(item.getExtraAttribute());
            }
            holder.lore.setText(item.getLore());

            setCurrentItemImage(view, viewGroup, item);
            setUpgradeToImage(view, viewGroup, item);
            setUpgradeFromImage(view, viewGroup, item);
        } else {
            if (view == null || view.getTag() instanceof ViewHolderFull) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.fragment_item_list_cell_short, viewGroup, false);
                holder2 = new ViewHolderShort();
                setViewHolderShort(holder2, view);
                view.setTag(holder2);
            } else {
                holder2 = (ViewHolderShort) view.getTag();
            }

            holder2.image.setImageResource(context.getResources().getIdentifier(item.getImage(),
                    "drawable", context.getPackageName()));
            holder2.name.setText(item.getName());
            holder2.cost.setText(item.getCost());
            if (item.getCoolDown().equals("")) {
                holder2.cooldown.setVisibility(View.GONE);
                holder2.cooldownIcon.setVisibility(View.GONE);
            } else {
                holder2.cooldown.setVisibility(View.VISIBLE);
                holder2.cooldownIcon.setVisibility(View.VISIBLE);
                holder2.cooldown.setText(item.getCoolDown());
            }
            if (item.getManaCost().equals("")) {
                holder2.mana.setVisibility(View.GONE);
                holder2.manaIcon.setVisibility(View.GONE);
            } else {
                holder2.mana.setVisibility(View.VISIBLE);
                holder2.manaIcon.setVisibility(View.VISIBLE);
                holder2.mana.setText(item.getManaCost());
            }
        }
        return view;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return expandableListDetail.get(expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_item_list_header, parent, false);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.fragment_item_list_header);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        ImageView headerImage = (ImageView) convertView.findViewById(R.id.fragment_item_list_header_image);
        int imageRes = context.getResources().getIdentifier(
                convertCategoryNameToImageName(listTitle), "drawable", context.getPackageName());
        headerImage.setImageResource(imageRes);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

    private void setCurrentItemImage(View view, final ViewGroup viewGroup, ItemObject item) {
        TableRow tr2 = (TableRow) view.findViewById(R.id.currentItem);
        tr2.removeAllViews();
        TableRow.LayoutParams imageViewParam = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageViewParam.setMargins(0, 0, 30, 0);
        float scale = context.getResources().getDisplayMetrics().density;
        imageViewParam.height = (int) (35 * scale);
        imageViewParam.width = (int) (45 * scale);
        if (item.getCreatedFrom().size() > 0 || item.getCreatedTo().size() > 0) {
            final ImageView itemUpgradeImageView = new ImageView(context);
            itemUpgradeImageView.setImageResource(context.getResources().getIdentifier(
                    item.getImage(), "drawable", context.getPackageName()));
            itemUpgradeImageView.setLayoutParams(imageViewParam);
            tr2.addView(itemUpgradeImageView);
        }
    }

    private void setUpgradeToImage(View view, final ViewGroup viewGroup, final ItemObject item) {
        TableRow tr = (TableRow) view.findViewById(R.id.fragment_item_list_item_upgrade);

        tr.removeAllViews();


        TableRow.LayoutParams imageViewParam = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageViewParam.setMargins(0, 0, 30, 0);
        float scale = context.getResources().getDisplayMetrics().density;
        imageViewParam.height = (int) (35 * scale);
        imageViewParam.width = (int) (45 * scale);

        for (String toItemKey : item.getCreatedTo()) {
            imageViewParam.weight = 1 / item.getCreatedTo().size();
            final ImageView toItemImage = createImageViewFromItemKey(imageViewParam, toItemKey, viewGroup);
            toItemImage.setOnClickListener(v -> scrollToSelectedItem((ImageView) v, viewGroup));
            tr.addView(toItemImage);
        }
    }

    private void setUpgradeFromImage(View view, final ViewGroup viewGroup, ItemObject item) {
        TableRow tr = (TableRow) view.findViewById(R.id.fragment_item_list_item_components);
        tr.removeAllViews();


        TableRow.LayoutParams imageViewParam = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageViewParam.setMargins(0, 0, 30, 0);
        float scale = context.getResources().getDisplayMetrics().density;
        imageViewParam.height = (int) (35 * scale);
        imageViewParam.width = (int) (45 * scale);
        for (String fromItemKey : item.getCreatedFrom()) {
            final ImageView fromItemImage = createImageViewFromItemKey(imageViewParam, fromItemKey, viewGroup);
            fromItemImage.setOnClickListener(v -> scrollToSelectedItem((ImageView) v, viewGroup));
            tr.addView(fromItemImage);
        }
    }

    private ImageView createImageViewFromItemKey(TableRow.LayoutParams imageViewParam, String itemKey, ViewGroup viewGroup) {
        final ImageView toItemImage = new ImageView(context);
        int imageName = context.getResources().getIdentifier(getImageNameFromItemKey(itemKey), "drawable",
                context.getPackageName());

        toItemImage.setImageResource(imageName);
        toItemImage.setLayoutParams(imageViewParam);
        toItemImage.setTag(itemKey);

        toItemImage.setOnClickListener(v -> scrollToSelectedItem((ImageView) v, viewGroup));
        return toItemImage;
    }

    private void setViewHolderFull(ViewHolderFull holder, View view) {
        holder.image = (ImageView) view.findViewById(R.id.fragment_item_list_cell_image);
        holder.name = (TextView) view.findViewById(R.id.fragment_item_list_cell_name);
        holder.cost = (TextView) view.findViewById(R.id.fragment_item_list_cell_cost);
        holder.manaIcon = (ImageView) view.findViewById(R.id.fragment_item_list_cell_mana_icon);
        holder.mana = (TextView) view.findViewById(R.id.fragment_item_list_cell_mana);
        holder.cooldownIcon = (ImageView) view.findViewById(R.id.fragment_item_list_cell_cooldown_icon);
        holder.cooldown = (TextView) view.findViewById(R.id.fragment_item_list_cell_cooldown);
        holder.mainDescription = (TextView) view.findViewById(R.id.fragment_item_list_cell_main_description);
        holder.altDescription = (TextView) view.findViewById(R.id.fragment_item_list_cell_alt_description);
        holder.extraAttr = (TextView) view.findViewById(R.id.fragment_item_list_cell_extra_attribute);
        holder.lore = (TextView) view.findViewById(R.id.fragment_item_list_cell_lore);
    }

    private void setViewHolderShort(ViewHolderShort holder, View view) {
        holder.image = (ImageView) view.findViewById(R.id.fragment_item_list_cell2_image);
        holder.name = (TextView) view.findViewById(R.id.fragment_item_list_cell2_name);
        holder.cost = (TextView) view.findViewById(R.id.fragment_item_list_cell2_cost);
        holder.manaIcon = (ImageView) view.findViewById(R.id.fragment_item_list_cell2_mana_icon);
        holder.mana = (TextView) view.findViewById(R.id.fragment_item_list_cell2_mana);
        holder.cooldownIcon = (ImageView) view.findViewById(R.id.fragment_item_list_cell2_cooldown_icon);
        holder.cooldown = (TextView) view.findViewById(R.id.fragment_item_list_cell2_cooldown);
    }

    @NonNull
    private String convertCategoryNameToImageName(String categoryName) {
        if (categoryName.equals("Consumables")) {
            return "itemcat_consumables";
        }
        if (categoryName.equals("Attributes")) {
            return "itemcat_attributes";
        }
        if (categoryName.equals("Armaments")) {
            return "itemcat_armaments";
        }
        if (categoryName.equals("Arcane")) {
            return "itemcat_arcane";
        }
        if (categoryName.equals("Common")) {
            return "itemcat_common";
        }
        if (categoryName.equals("Support")) {
            return "itemcat_support";
        }
        if (categoryName.equals("Caster")) {
            return "itemcat_caster";
        }
        if (categoryName.equals("Weapons")) {
            return "itemcat_weapons";
        }
        if (categoryName.equals("Armor")) {
            return "itemcat_armor";
        }
        if (categoryName.equals("Artifacts")) {
            return "itemcat_artifacts";
        }
        if (categoryName.equals("Secret Shop")) {
            return "itemcat_secret";
        }
        return "";
    }

    static class ViewHolderFull {

        ImageView image;

        TextView name;

        TextView cost;

        ImageView cooldownIcon;

        TextView cooldown;

        ImageView manaIcon;

        TextView mana;

        TextView mainDescription;

        TextView altDescription;

        TextView extraAttr;

        TextView lore;
    }

    static class ViewHolderShort {
        ImageView image;

        TextView name;

        TextView cost;

        ImageView cooldownIcon;

        TextView cooldown;

        ImageView manaIcon;

        TextView mana;
    }

    private void scrollToSelectedItem(ImageView selectedImage, ViewGroup viewGroup) {
        String itemKey = (String) selectedImage.getTag();
        if (!itemKey.equals(RECIPE_ITEM)) {
            Iterator<List<ItemObject>> it = expandableListDetail.values().iterator();
            int groupIndex = 0;
            while (it.hasNext()) {
                List<ItemObject> itemList = it.next();
                for (int i = 0; i < itemList.size(); i++) {
                    if (itemList.get(i).getItemKey().equals(itemKey)) {
                        itemList.get(i).setExpand(!itemList.get(i).isExpand());
                        ((ExpandableListView) viewGroup).expandGroup(groupIndex);
                        long packedPosition = ExpandableListView.getPackedPositionForChild(
                                groupIndex, i);
                        int flatPosition = ((ExpandableListView) viewGroup).getFlatListPosition(packedPosition);
                        ((ExpandableListView) viewGroup).setSelectedChild(groupIndex,
                                i, true);
                        ((ExpandableListView) viewGroup).smoothScrollToPosition(flatPosition);
                    } else {
                        itemList.get(i).setExpand(false);
                    }
                }
                groupIndex++;
            }
            notifyDataSetChanged();
        }
    }

    private String getImageNameFromItemKey(String filter) {
        if (filter.equals(RECIPE_ITEM)) {
            return RECIPE_ITEM;
        }
        for (ItemObject object : allItems) {
            if (object.getItemKey().equals(filter)) {
                return object.getImage();
            }
        }
        return null;
    }

    private void fillAllItemsList() {
        allItems = new ArrayList<>();
        List<List<ItemObject>> itemsByCategory = new ArrayList(expandableListDetail.values());
        for (int i = 0; i < itemsByCategory.size(); i++) {
            List<ItemObject> items = itemsByCategory.get(i);
            allItems.addAll(items);
        }
    }
}
