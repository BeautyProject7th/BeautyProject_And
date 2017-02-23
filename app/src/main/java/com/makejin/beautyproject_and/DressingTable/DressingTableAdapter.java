package com.makejin.beautyproject_and.DressingTable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_and.Model.Cosmetic;
import com.makejin.beautyproject_and.R;
import com.makejin.beautyproject_and.Utils.Constants.Constants;

import java.util.ArrayList;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class DressingTableAdapter extends RecyclerView.Adapter<DressingTableAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public DressingTableFragment fragment;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Cosmetic> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public DressingTableAdapter(OnItemClickListener onItemClickListener, Context mContext, DressingTableFragment mFragment) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        fragment = mFragment;
        mDataset.clear();
    }

    public void addData(Cosmetic cosmetic) {
        mDataset.add(cosmetic);
        //Toast.makeText(context, "[addData]" + "size : " + mDataset.size() + " / " + getItemCount(), Toast.LENGTH_SHORT).show();
    }

    public Cosmetic getItem(int position) {
        return mDataset.get(position);
    }

    public void clear() {
        mDataset.clear();
    }

    @Override
     public DressingTableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Toast.makeText(context, "[onCreateViewHolder]" + "size : " + mDataset.size() + " / " + getItemCount(), Toast.LENGTH_SHORT).show();
        if (viewType == TYPE_ITEM) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_dressing_table, parent, false);
            return new ItemViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Toast.makeText(context, "[onBindViewHolder]" + "size : " + mDataset.size() + " / " + getItemCount(), Toast.LENGTH_SHORT).show();
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

            String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;

            Log.i("makejin", "image_url : " + image_url);


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
            //Toast.makeText(context, "[ViewHolder]" + "size : " + mDataset.size() + " / " + getItemCount(), Toast.LENGTH_SHORT).show();
            container = itemView;
        }
    }
    public class ItemViewHolder extends ViewHolder {
        public TextView TV_cosmetic_name;
        public ImageView IV_cosmetic;

        public ItemViewHolder(View v) {
            super(v);
            //Toast.makeText(context, "[ItemViewHolder]" + "size : " + mDataset.size() + " / " + getItemCount(), Toast.LENGTH_SHORT).show();
            TV_cosmetic_name = (TextView) v.findViewById(R.id.TV_cosmetic_name);
            IV_cosmetic = (ImageView) v.findViewById(R.id.IV_cosmetic);

        }
    }

}
