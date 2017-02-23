package com.makejin.beautyproject_and.DressingTable.CosmeticUpload;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.makejin.beautyproject_and.Model.Category;
import com.makejin.beautyproject_and.R;

import java.util.ArrayList;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class CosmeticUploadAdapter_3 extends RecyclerView.Adapter<CosmeticUploadAdapter_3.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public CosmeticUploadFragment_3 fragment;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<String> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CosmeticUploadAdapter_3(OnItemClickListener onItemClickListener, Context mContext, CosmeticUploadFragment_3 mFragment) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        fragment = mFragment;
        mDataset.clear();
    }

    public void addData(Category category) {
        Log.i("makejin3201_1", "z : " + category.sub_category.toString());
        for(String sub : category.sub_category){
            mDataset.add(sub);
        }
        //mDataset.add(category.sub_category);
    }

    public String getItem(int position) {
        return mDataset.get(position);
    }

    public void clear() {
        mDataset.clear();
    }

    @Override
     public CosmeticUploadAdapter_3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cosmetic_upload_2, parent, false);
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
            String sub_category = mDataset.get(position);

            itemViewHolder.BT_category.setText(sub_category);

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
        public Button BT_category;

        public ItemViewHolder(View v) {
            super(v);
            BT_category = (Button) v.findViewById(R.id.BT_category);

        }
    }

}
