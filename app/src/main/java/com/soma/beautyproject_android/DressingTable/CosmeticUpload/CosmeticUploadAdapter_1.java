package com.soma.beautyproject_android.DressingTable.CosmeticUpload;

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
import com.soma.beautyproject_android.Utils.Constants.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class CosmeticUploadAdapter_1 extends RecyclerView.Adapter<CosmeticUploadAdapter_1.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public CosmeticUploadActivity_1 activity;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Brand> mDataset = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CosmeticUploadAdapter_1(OnItemClickListener onItemClickListener, Context mContext, CosmeticUploadActivity_1 mActivity) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        activity = mActivity;
        //mDataset.clear();
    }

    public void addData(ArrayList<Brand> brandlist) {
       // mDataset.add(brand);

        this.mDataset = brandlist;
    }

    public Brand getItem(int position) {
        return mDataset.get(position);
    }

    public void clear() {
        mDataset.clear();
    }

    @Override
     public CosmeticUploadAdapter_1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cosmetic_upload1, parent, false);
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
            Brand brand = mDataset.get(position);
            Log.i("brand","브랜드 가져옴 "+brand.name);

            String image_url = Constants.IMAGE_BASE_URL_brand + brand.logo;

            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_brand);
            itemViewHolder.TV_brand.setText(mDataset.get(position).name);

            Log.i("zxc","onBindViewHolder()");
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
        public ImageView IV_brand;
        public TextView TV_brand;

        public ItemViewHolder(View v) {
            super(v);
            IV_brand = (ImageView) v.findViewById(R.id.IV_brand);
            TV_brand = (TextView) v.findViewById(R.id.TV_brand);
        }
    }

}
