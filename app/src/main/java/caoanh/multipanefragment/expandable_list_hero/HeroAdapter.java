/*
 * Author            : AdNovum Informatik AG
 * Version Number    : $Revision: $
 * Date of last edit : $Date: $
 */

package caoanh.multipanefragment.expandable_list_hero;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import caoanh.multipanefragment.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class HeroAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<String> title;
	private Map<String, List<HeroObject>> data;
	private ExpandableListView aa;
	
	HeroAdapter(Context context, List<String> title, Map<String, List<HeroObject>> data) {
		this.context = context;
		this.title = title;
		this.data = data;
	}
	
	@Override
	public Object getChild(int listPosition, int expandedListPosition) {
		return data.get(title.get(listPosition)).get(expandedListPosition);
	}

	@Override
	public long getChildId(int listPosition, int expandedListPosition) {
		return expandedListPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder;
		HeroObject item = (HeroObject) getChild(groupPosition, childPosition);
		if(view == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.fragment_hero_list_cell, null);
			viewHolder = new ViewHolder();
			setViewHolder(viewHolder, view);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)view.getTag();
		}
		viewHolder.avatar.setImageResource(context.getResources().getIdentifier(item.getImage(), "drawable", context.getPackageName()));
		viewHolder.name.setText(item.getName());
		return view;
	}

	@Override
	public int getChildrenCount(int listPosition) {
		return data.get(title.get(listPosition)).size();
	}

	@Override
	public Object getGroup(int listPosition) {
		return title.get(listPosition);
	}

	@Override
	public int getGroupCount() {
		return title.size();
	}

	@Override
	public long getGroupId(int listPosition) {
		return listPosition;
	}

	@Override
	public View getGroupView(int listPosition, boolean isExpand, View view, ViewGroup viewGroup) {
		if(view == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			view = inflater.inflate(R.layout.fragment_hero_list_header, null);
		}

		if(aa == null){
			aa = (ExpandableListView)viewGroup;
		}
		String []fullTitle = title.get(listPosition).split("-");
		TextView fraction = (TextView)view.findViewById(R.id.fragment_hero_list_header_fraction);
		TextView attribute = (TextView)view.findViewById(R.id.fragment_hero_list_header_primary_attribute);
		String fractionLowerName = fullTitle[0].substring(0, 1).toLowerCase(Locale.ENGLISH) + fullTitle[0].substring(1);
		int fractionImageResource = context.getResources().getIdentifier("fraction_" + fractionLowerName, "drawable", context.getPackageName());
		fraction.setCompoundDrawablesWithIntrinsicBounds(fractionImageResource, 0, 0, 0);
		fraction.setText(fullTitle[0]);
		
		String attributelowerName = fullTitle[1].substring(0, 1).toLowerCase(Locale.ENGLISH) + fullTitle[1].substring(1);
		int attributeResource = context.getResources().getIdentifier("attribute_"+ attributelowerName, "drawable", context.getPackageName());
		attribute.setText(fullTitle[1]);
		attribute.setCompoundDrawablesWithIntrinsicBounds(attributeResource, 0, 0, 0);
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	private void setViewHolder(ViewHolder viewHolder, View view){
		viewHolder.avatar = (ImageView)view.findViewById(R.id.fragment_hero_list_cell_avatar);
		viewHolder.name = (TextView)view.findViewById(R.id.fragment_hero_list_cell_name);
	}

	static class ViewHolder{
		ImageView avatar;
		TextView name;
	}
}
