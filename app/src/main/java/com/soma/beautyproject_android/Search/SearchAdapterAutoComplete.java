package com.soma.beautyproject_android.Search;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Search.MoreSearch.*;
import com.soma.beautyproject_android.Utils.Constants.Constants;

import java.util.ArrayList;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class SearchAdapterAutoComplete extends RecyclerView.Adapter<SearchAdapterAutoComplete.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public Fragment fragment;
    public Activity activity;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<String> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public SearchAdapterAutoComplete(OnItemClickListener onItemClickListener, Context mContext, SearchFragmentBrand mFragment) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        fragment = mFragment;
        mDataset.clear();
    }
    public SearchAdapterAutoComplete(OnItemClickListener onItemClickListener, Context mContext, SearchFragmentCategory mFragment) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        fragment = mFragment;
        mDataset.clear();
    }
    public SearchAdapterAutoComplete(OnItemClickListener onItemClickListener, Context mContext, Activity mActivity) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        activity = mActivity;
        mDataset.clear();
    }

    public void addData(String keyword) {
        mDataset.add(keyword);
    }

    public String getItem(int position) {
        return mDataset.get(position);
    }

    public void clear() {
        mDataset.clear();
    }

    @Override
     public SearchAdapterAutoComplete.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_keyword_auto_complete, parent, false);
            return new ItemViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String keyword = mDataset.get(position);

            itemViewHolder.TV_auto_complete_keyword.setText(keyword);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    /*
        ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView;
        }
    }
    public class ItemViewHolder extends ViewHolder {
        public TextView TV_auto_complete_keyword;

        public ItemViewHolder(View v) {
            super(v);
            TV_auto_complete_keyword = (TextView) v.findViewById(R.id.TV_auto_complete_keyword);
        }
    }

}
