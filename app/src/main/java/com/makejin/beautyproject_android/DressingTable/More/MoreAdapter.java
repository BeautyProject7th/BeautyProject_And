package com.makejin.beautyproject_android.DressingTable.More;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_android.Model.Cosmetic;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Constants.Constants;

import java.util.ArrayList;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public MoreFragment fragment;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Cosmetic> mDataset = new ArrayList<>();
    //public final int temp_main_category_num = MoreActivity.main_category_num;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public MoreAdapter(OnItemClickListener onItemClickListener, Context mContext, MoreFragment mFragment) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        fragment = mFragment;
        mDataset.clear();
    }

    public void addData(Cosmetic cosmetic) {
        mDataset.add(cosmetic);
    }

    public Cosmetic getItem(int position) {
        return mDataset.get(position);
    }

    public void clear() {
        mDataset.clear();
    }

    @Override
     public MoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_more_cosmetic, parent, false);
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
            Cosmetic cosmetic = mDataset.get(position);

            itemViewHolder.TV_cosmetic_name.setText(cosmetic.product_name);
            itemViewHolder.TV_sub_category.setText(cosmetic.sub_category);
            itemViewHolder.RB_rate.setRating(cosmetic.rate_num);

            String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;
            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_cosmetic);

            if (position == mDataset.size()-1 && !fragment.endOfPage) {
                //fragment.connectTestCall(SharedManager.getInstance().getMe().id, fragment.main_category[temp_main_category_num], ++fragment.page_num);
                fragment.page_num++;
                fragment.connectTestCall(MoreActivity.main_category_num);
            }
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
        public TextView TV_cosmetic_name, TV_sub_category;
        public RatingBar RB_rate;
        public ImageView IV_cosmetic;

        public ItemViewHolder(View v) {
            super(v);
            TV_cosmetic_name = (TextView) v.findViewById(R.id.TV_cosmetic_name);
            TV_sub_category = (TextView) v.findViewById(R.id.TV_sub_category);
            RB_rate = (RatingBar) v.findViewById(R.id.RB_rate);
            IV_cosmetic = (ImageView) v.findViewById(R.id.IV_cosmetic);

        }
    }

}
