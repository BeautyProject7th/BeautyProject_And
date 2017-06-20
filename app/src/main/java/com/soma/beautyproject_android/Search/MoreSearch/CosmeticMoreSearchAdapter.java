package com.soma.beautyproject_android.Search.MoreSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Constants.Constants;

import java.util.ArrayList;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class CosmeticMoreSearchAdapter extends RecyclerView.Adapter<CosmeticMoreSearchAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public CosmeticMoreSearchActivity activity;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Cosmetic> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CosmeticMoreSearchAdapter(OnItemClickListener onItemClickListener, Context mContext, CosmeticMoreSearchActivity mActivity) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        activity = mActivity;
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
     public CosmeticMoreSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_result_cosmetic_more, parent, false);
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

            String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;

            itemViewHolder.TV_cosmetic_have.setVisibility(View.GONE);

            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_cosmetic);
            itemViewHolder.TV_brand.setText(cosmetic.brand);
            itemViewHolder.TV_product_name.setText(cosmetic.product_name);
            itemViewHolder.TV_product_price.setText(cosmetic.price+"원");
            itemViewHolder.TV_rate_num.setText(String.valueOf(cosmetic.rate_num));
            itemViewHolder.TV_product_review_quantity.setText("");
            itemViewHolder.RB_rate.setRating(cosmetic.rate_num);


            if (position == mDataset.size()-1 && !activity.endOfPage)
                activity.conn_cosmetic_more_search(++activity.page, activity.keyword);
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
        public ImageView IV_cosmetic;
        public TextView TV_cosmetic_have, TV_brand, TV_product_name, TV_product_price, TV_rate_num, TV_product_review_quantity;
        public RatingBar RB_rate;
        public ItemViewHolder(View v) {
            super(v);
            IV_cosmetic = (ImageView) v.findViewById(R.id.IV_cosmetic);
            TV_cosmetic_have = (TextView) v.findViewById(R.id.TV_cosmetic_have);
            TV_brand = (TextView) v.findViewById(R.id.TV_brand);
            TV_product_name = (TextView) v.findViewById(R.id.TV_product_name);
            TV_product_price = (TextView) v.findViewById(R.id.TV_product_price);
            TV_rate_num = (TextView) v.findViewById(R.id.TV_rate_num);
            TV_product_review_quantity = (TextView) v.findViewById(R.id.TV_product_review_quantity);
            RB_rate = (RatingBar) v.findViewById(R.id.RB_rate);
        }
    }

}
