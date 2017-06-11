package com.soma.beautyproject_android.Search;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchAdapterCategory extends BaseExpandableListAdapter {

    private Context context = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    private Map<String,List<String>> categorylist = null;
    private ArrayList<String> mainCategoryList = null;

    public SearchAdapterCategory(Context c, Map<String,List<String>> categorylist){
        super();

        this.context = c;
        this.inflater = LayoutInflater.from(c);
        this.categorylist = categorylist;
        ArrayList<String> keys = new ArrayList<>(categorylist.keySet());
        this.mainCategoryList = keys;
    }

    // 그룹 포지션을 반환한다.
    @Override
    public String getGroup(int groupPosition) {
        return mainCategoryList.get(groupPosition);
    }

    // 그룹 사이즈를 반환한다.
    @Override
    public int getGroupCount() {
        return mainCategoryList.size();
    }

    // 그룹 ID를 반환한다.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 그룹뷰 각각의 ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.cell_cosmetic_upload2_parent, parent, false);
            viewHolder.tv_groupName = (TextView) v.findViewById(R.id.TV_main_category);
            viewHolder.iv_image = (ImageView) v.findViewById(R.id.IV_category);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
        if(isExpanded){
            Drawable drawable = context.getResources().getDrawable(R.drawable.btn_category_select);
            viewHolder.iv_image.setImageDrawable(drawable);
            viewHolder.tv_groupName.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            Drawable drawable = context.getResources().getDrawable(R.drawable.btn_category);
            viewHolder.iv_image.setImageDrawable(drawable);
            viewHolder.tv_groupName.setTextColor(context.getResources().getColor(R.color.colorBlackText));
        }

        viewHolder.tv_groupName.setText(getGroup(groupPosition));

        return v;
    }

    // 차일드뷰를 반환한다.
    @Override
    public String getChild(int groupPosition, int childPosition) {
        String key = mainCategoryList.get(groupPosition);
        return categorylist.get(key).get(childPosition);
    }

    // 차일드뷰 사이즈를 반환한다.
    @Override
    public int getChildrenCount(int groupPosition) {
        String key = mainCategoryList.get(groupPosition);
        return categorylist.get(key).size();
    }

    // 차일드뷰 ID를 반환한다.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.cell_cosmetic_upload2_child, null);
            viewHolder.tv_childName = (TextView) v.findViewById(R.id.TV_sub_category);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.tv_childName.setText(getChild(groupPosition, childPosition));

        return v;
    }

    @Override
    public boolean hasStableIds() { return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {
        public ImageView iv_image;
        public TextView tv_groupName;
        public TextView tv_childName;
    }

}