package com.makejin.beautyproject_and.DressingTable.CosmeticUpload;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_and.Model.Category;
import com.makejin.beautyproject_and.Model.Cosmetic;
import com.makejin.beautyproject_and.R;
import com.makejin.beautyproject_and.Utils.Constants.Constants;
import com.makejin.beautyproject_and.Utils.SharedManager.SharedManager;

import java.util.ArrayList;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class CosmeticUploadAdapter_3 extends RecyclerView.Adapter<CosmeticUploadAdapter_3.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public CosmeticUploadFragment_3 fragment;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Cosmetic> mDataset = new ArrayList<>();
    public ArrayList<Cosmetic> checkedList = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CosmeticUploadAdapter_3(OnItemClickListener onItemClickListener, Context mContext, CosmeticUploadFragment_3 mFragment) {
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
     public CosmeticUploadAdapter_3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cosmetic_upload_3, parent, false);
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
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final Cosmetic cosmetic = mDataset.get(position);

            String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;

            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_product);

            itemViewHolder.TV_product.setText(cosmetic.product_name);

            //체크박스 초기화(나의 화장품 목록에 있는 거면 체크되있어야함
            //if()
            //itemViewHolder.CB_product.setChecked(true);

            itemViewHolder.IV_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemViewHolder.CB_product.isChecked()) { //체크되있는 상태
                        itemViewHolder.CB_product.setChecked(false);
                        checkedList.remove(cosmetic);
                    }else {
                        itemViewHolder.CB_product.setChecked(true);
                        checkedList.add(cosmetic);
                    }
                }
            });

            if (position == mDataset.size()-1 && !fragment.endOfPage)
                fragment.connectTestCall(fragment.brand.name, fragment.main_category, fragment.sub_category, ++fragment.page_num);
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
        public CheckBox CB_product;
        public ImageView IV_product;
        public TextView TV_product;


        public ItemViewHolder(View v) {
            super(v);
            CB_product = (CheckBox) v.findViewById(R.id.CB_product);
            IV_product = (ImageView) v.findViewById(R.id.IV_product);
            TV_product = (TextView) v.findViewById(R.id.TV_product);
        }
    }

}
