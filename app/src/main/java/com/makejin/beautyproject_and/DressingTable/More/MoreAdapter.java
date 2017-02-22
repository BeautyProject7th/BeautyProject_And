package com.makejin.beautyproject_and.DressingTable.More;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_and.Model.Cosmetic;
import com.makejin.beautyproject_and.R;

import java.util.ArrayList;

import static com.makejin.beautyproject_and.Utils.Constants.Constants.IMAGE_BASE_URL;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public MoreFragment fragment;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Cosmetic> mDataset = new ArrayList<>();

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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_dressing_table, parent, false);
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

            String image_url = IMAGE_BASE_URL + cosmetic.img_src;
            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_cosmetic);
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
        public TextView TV_cosmetic_name;
        public ImageView IV_cosmetic;

        public ItemViewHolder(View v) {
            super(v);
            TV_cosmetic_name = (TextView) v.findViewById(R.id.TV_cosmetic_name);
            IV_cosmetic = (ImageView) v.findViewById(R.id.IV_cosmetic);

        }
    }

}
