package com.soma.beautyproject_android.Video.Video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.R;

import java.util.ArrayList;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class VideoContentsAdapter extends RecyclerView.Adapter<VideoContentsAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public VideoContentsFragment fragment;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Cosmetic> mDataset = new ArrayList<>();
    //public final int temp_main_category_num = MoreActivity.main_category_num;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public VideoContentsAdapter(OnItemClickListener onItemClickListener, Context mContext, VideoContentsFragment mFragment) {
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
     public VideoContentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_video_product, parent, false);
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

//            itemViewHolder.TV_cosmetic_name.setText(cosmetic.product_name);
//
//            String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;
//            Glide.with(context).
//                    load(image_url).
//                    thumbnail(0.1f).
//                    into(itemViewHolder.IV_cosmetic_img);

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
        public TextView TV_cosmetic_name, TV_cosmetic_brand, TV_cosmetic_price;
        public ImageView IV_cosmetic_img;
        public Button BT_reserve, BT_buy;

        public ItemViewHolder(View v) {
            super(v);
            TV_cosmetic_name = (TextView) v.findViewById(R.id.TV_cosmetic_name);
            TV_cosmetic_brand = (TextView) v.findViewById(R.id.TV_cosmetic_brand);
            TV_cosmetic_price = (TextView) v.findViewById(R.id.TV_cosmetic_price);
            IV_cosmetic_img = (ImageView) v.findViewById(R.id.IV_cosmetic_img);
            BT_reserve = (Button) v.findViewById(R.id.BT_reserve);
            BT_buy = (Button) v.findViewById(R.id.BT_buy);

        }
    }

}
