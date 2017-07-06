package com.soma.beautyproject_android.DressingTable.CosmeticExpirationDate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.ExpirationCosmetic;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.TimeFormatter.TimeFormmater;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;


import static com.soma.beautyproject_android.R.id.IV_brand;
import static com.soma.beautyproject_android.R.id.RL_expiration_date_day;
import static com.soma.beautyproject_android.R.id.TV_brand;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class CosmeticExpirationDateAdapter extends RecyclerView.Adapter<CosmeticExpirationDateAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public CosmeticExpirationDateActivity activity;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<ExpirationCosmetic> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CosmeticExpirationDateAdapter(OnItemClickListener onItemClickListener, Context mContext, CosmeticExpirationDateActivity mActivity) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        activity = mActivity;
        mDataset.clear();
    }

    public void addData(ExpirationCosmetic expirationCosmetic) {
        mDataset.add(expirationCosmetic);
    }

    public ExpirationCosmetic getItem(int position) {
        return mDataset.get(position);
    }

    public void clear() {
        mDataset.clear();
    }

    @Override
     public CosmeticExpirationDateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cosmetic_expiration, parent, false);
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
            ExpirationCosmetic expirationCosmetic = mDataset.get(position);

            String image_url = Constants.IMAGE_BASE_URL_cosmetics + expirationCosmetic.img_src;
            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    diskCacheStrategy(DiskCacheStrategy.SOURCE).
                    into(itemViewHolder.IV_cosmetic);

            itemViewHolder.TV_brand_name.setText(expirationCosmetic.brand);
            itemViewHolder.TV_cosmetic_name.setText(expirationCosmetic.product_name);


            Date now = new Date();
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar now_calendar = Calendar.getInstance();
            String now_date = format.format(now);
            try {
                now_calendar.setTime(format.parse(now_date));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Calendar expiration_calendar = Calendar.getInstance();
            String expiration_date = expirationCosmetic.expiration_date;
            try {
                expiration_calendar.setTime(format.parse(expiration_date));
            } catch (ParseException e) {
                e.printStackTrace();
            }



            long time = (long)(expiration_calendar.getTimeInMillis() - now_calendar.getTimeInMillis());
            Log.i("xzc", "now_calendar.compareTo(expiration_calendar) : " + time);
            //expiration_calendar.setTimeInMillis((long(expiration_calendar.getTimeInMillis() - now_calendar.getTimeInMillis()));
            Log.i("xzc", "now_calendar.compareTo(expiration_calendar)3 : " + time / (1000*60*60*24));

            int dday = (int)(time / (1000*60*60*24));

            if(dday >= 0){
                itemViewHolder.TV_expiration_date_day.setText("D-"+dday);
                itemViewHolder.TV_expiration_date_day.setTextColor(activity.getResources().getColor(R.color.colorAccent));
                itemViewHolder.RL_expiration_date_day.setBackgroundResource(R.drawable.btn_circle_empty);
                itemViewHolder.IV_cosmetic_border.setBackgroundResource(0);
            }else{
                dday*=-1;
                itemViewHolder.IV_cosmetic_border.setBackgroundResource(R.drawable.ic_rectangle_empty);
                itemViewHolder.RL_expiration_date_day.setBackgroundResource(R.drawable.btn_circle);
                itemViewHolder.TV_expiration_date_day.setTextColor(activity.getResources().getColor(R.color.colorWhite));
                itemViewHolder.TV_expiration_date_day.setText("D+"+dday);
            }

//
//            Date resultdate = new Date(c.getTimeInMillis());
//            expirationCosmetic.expiration_date
//
//                    2017-06-19 (현재)
//                    2017-06-21 (유통기한)
//                    D-2(D-day)
            //itemViewHolder.TV_expiration_date_day.setText();

//            int dday = doDiffOfDate(cosmetic.expiration_date);
//            if(dday == 100){
//                Toast.makeText(activity,"디데이 계산 문제 있음",Toast.LENGTH_SHORT).show();
//            }else if(dday>0) itemViewHolder.TV_dday.setText("D-"+dday);
//            else if(dday==0) itemViewHolder.TV_dday.setText("TODAY");
//            else if(dday<0) itemViewHolder.TV_dday.setText("유통기한 지남");
        }
    }

    public int doDiffOfDate(String expiration_date) {
        int result = 100;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date expirationDate = formatter.parse(expiration_date);
            Date nowDate = formatter.parse(formatter.format(System.currentTimeMillis()));

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff = expirationDate.getTime() - nowDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Log.i("date","날짜차이=" + diffDays);
            result = (int)diffDays;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
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
        public TextView TV_expiration_date_day,TV_brand_name,TV_cosmetic_name;
        public ImageView IV_cosmetic_border;
        public RelativeLayout RL_expiration_date_day;

        public ItemViewHolder(View v) {
            super(v);
            IV_cosmetic = (ImageView) v.findViewById(R.id.IV_cosmetic);
            TV_expiration_date_day = (TextView) v.findViewById(R.id.TV_expiration_date_day);
            TV_brand_name = (TextView) v.findViewById(R.id.TV_brand_name);
            TV_cosmetic_name = (TextView) v.findViewById(R.id.TV_cosmetic_name);
            IV_cosmetic_border = (ImageView) v.findViewById(R.id.IV_cosmetic_border);
            RL_expiration_date_day = (RelativeLayout) v.findViewById(R.id.RL_expiration_date_day);

        }
    }

}
