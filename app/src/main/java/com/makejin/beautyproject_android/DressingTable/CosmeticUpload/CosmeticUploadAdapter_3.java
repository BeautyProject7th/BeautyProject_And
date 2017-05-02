package com.makejin.beautyproject_android.DressingTable.CosmeticUpload;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_android.Model.Cosmetic;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Constants.Constants;

import java.util.ArrayList;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class CosmeticUploadAdapter_3 extends RecyclerView.Adapter<CosmeticUploadAdapter_3.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public CosmeticUploadActivity_3 activity;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Cosmetic> mDataset = new ArrayList<>();
    public ArrayList<Cosmetic> checkedList = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CosmeticUploadAdapter_3(OnItemClickListener onItemClickListener, Context mContext, CosmeticUploadActivity_3 mActivity) {
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
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final Cosmetic cosmetic = mDataset.get(position);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);

                    if(itemViewHolder.CB_product.isChecked()) { //체크되있는 상태
                        Log.i("asdf","체크되어 있음 해제하겠따");
                        itemViewHolder.CB_product.setChecked(false);
                    }else {
                        Log.i("asdf","체크되어 있지 않음 체크하겠다");
                        itemViewHolder.CB_product.setChecked(true);
                    }
                }
            });

            itemViewHolder.CB_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) { //추가한 상태
                        itemViewHolder.CB_product.setChecked(true);
                        checkedList.add(cosmetic);
                        itemViewHolder.CB_product.setButtonDrawable(R.drawable.btn_check);
                        itemViewHolder.TV_product.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    }else { //없앤 상태
                        itemViewHolder.CB_product.setChecked(false);
                        checkedList.remove(cosmetic);
                        itemViewHolder.CB_product.setButtonDrawable(R.drawable.btn_uncheck);
                        itemViewHolder.TV_product.setTextColor(context.getResources().getColor(R.color.colorBlack));
                    }

                    Log.i("asdf","a 개수 : "+checkedList.size()+"개");
                    if(checkedList.size()==0){
                        activity.BT_cosmetic_upload.setBackgroundResource(R.drawable.btn_save);
                    }else{
                        activity.BT_cosmetic_upload.setBackgroundResource(R.drawable.btn_save_press);
                    }
                }
            });


            String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;
            Log.i("ZXc","makejin2:"+image_url);
            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_product);

            itemViewHolder.TV_product.setText(cosmetic.product_name);

            if (position == mDataset.size()-1 && !activity.endOfPage)
                activity.connectTestCall(activity.brand.name, activity.main_category, activity.sub_category, ++activity.page_num);
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
        public LinearLayout Cell_upload3;
        public CheckBox CB_product;
        public ImageView IV_product;
        public TextView TV_product;
        public RelativeLayout RL_product;


        public ItemViewHolder(View v) {
            super(v);
            Cell_upload3 = (LinearLayout) v.findViewById(R.id.LL_product);
            CB_product = (CheckBox) v.findViewById(R.id.CB_product);
            IV_product = (ImageView) v.findViewById(R.id.IV_product);
            TV_product = (TextView) v.findViewById(R.id.TV_product);
            RL_product = (RelativeLayout) v.findViewById(R.id.RL_product);
        }
    }
}
