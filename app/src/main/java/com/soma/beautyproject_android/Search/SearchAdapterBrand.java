package com.soma.beautyproject_android.Search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Constants.Constants;

import java.util.ArrayList;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class SearchAdapterBrand extends RecyclerView.Adapter<SearchAdapterBrand.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public SearchFragmentBrand fragment;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Brand> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public SearchAdapterBrand(OnItemClickListener onItemClickListener, Context mContext, SearchFragmentBrand mFragment) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        fragment = mFragment;
        mDataset.clear();
    }

    public void addData(Brand brand) {
        mDataset.add(brand);
    }

    public Brand getItem(int position) {
        return mDataset.get(position);
    }

    public void clear() {
        mDataset.clear();
    }

    @Override
     public SearchAdapterBrand.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
