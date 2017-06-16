package com.soma.beautyproject_android.DetailCosmetic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.DressingTable;
import com.soma.beautyproject_android.Model.Review;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Search.MoreSearch.CosmeticMoreSearchActivity;
import com.soma.beautyproject_android.Utils.Constants.Constants;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class DetailCosmeticReviewAdapter extends RecyclerView.Adapter<DetailCosmeticReviewAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public DetailCosmeticActivity activity;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Review> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public DetailCosmeticReviewAdapter(OnItemClickListener onItemClickListener, Context mContext, DetailCosmeticActivity mActivity) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        activity = mActivity;
        mDataset.clear();
    }
    public void addData(Review review) {
        mDataset.add(review);
    }

    public Review getItem(int position) {
        return mDataset.get(position);
    }

    public void clear() {
        mDataset.clear();
    }

    @Override
     public DetailCosmeticReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_review, parent, false);
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

            Review review = mDataset.get(position);

            int image_url_skin_type = -1;
            int image_url_skin_trouble_1 = -1;
            int image_url_skin_trouble_2 = -1;
            int image_url_skin_trouble_3 = -1;


            if(review.skin_type != null) {
                switch (review.skin_type) {
                    case "건성":
                        ;
                        image_url_skin_type = R.drawable.skin_type1;
                        break;
                    case "중성":
                        image_url_skin_type = R.drawable.skin_type2;
                        break;
                    case "지성":
                        image_url_skin_type = R.drawable.skin_type3;
                        break;
                    case "지성(수부지)":
                        image_url_skin_type = R.drawable.skin_type4;
                        break;

                }
            }

            if(review.skin_trouble_1 != null) {
                switch (review.skin_trouble_1) {
                    case "다크서클":
                        image_url_skin_trouble_1 = R.drawable.trouble1_darkcircle;
                        break;
                    case "블랙헤드":
                        image_url_skin_trouble_1 = R.drawable.trouble2_blackhead;
                        break;
                    case "모공":
                        image_url_skin_trouble_1 = R.drawable.trouble3_pore;
                        break;
                    case "각질":
                        image_url_skin_trouble_1 = R.drawable.trouble4_deadskin;
                        break;
                    case "민감성":
                        image_url_skin_trouble_1 = R.drawable.trouble5_sensitivity;
                        break;
                    case "주름":
                        image_url_skin_trouble_1 = R.drawable.trouble6_wrinkle;
                        break;
                    case "여드름":
                        image_url_skin_trouble_1 = R.drawable.trouble7_acne;
                        break;
                    case "안면홍조":
                        image_url_skin_trouble_1 = R.drawable.trouble8_flush;
                        break;
                    case "없음":
                        image_url_skin_trouble_1 = R.drawable.trouble9_nothing;
                        break;

                }
            }

            if(review.skin_trouble_2 != null) {
                switch (review.skin_trouble_2) {
                    case "다크서클":
                        image_url_skin_trouble_2 = R.drawable.trouble1_darkcircle;
                        break;
                    case "블랙헤드":
                        image_url_skin_trouble_2 = R.drawable.trouble2_blackhead;
                        break;
                    case "모공":
                        image_url_skin_trouble_2 = R.drawable.trouble3_pore;
                        break;
                    case "각질":
                        image_url_skin_trouble_2 = R.drawable.trouble4_deadskin;
                        break;
                    case "민감성":
                        image_url_skin_trouble_2 = R.drawable.trouble5_sensitivity;
                        break;
                    case "주름":
                        image_url_skin_trouble_2 = R.drawable.trouble6_wrinkle;
                        break;
                    case "여드름":
                        image_url_skin_trouble_2 = R.drawable.trouble7_acne;
                        break;
                    case "안면홍조":
                        image_url_skin_trouble_2 = R.drawable.trouble8_flush;
                        break;
                    case "없음":
                        image_url_skin_trouble_2 = R.drawable.trouble9_nothing;
                        break;

                }
            }

            if(review.skin_trouble_3 != null) {
                switch (review.skin_trouble_3) {
                    case "다크서클":
                        image_url_skin_trouble_3 = R.drawable.trouble1_darkcircle;
                        break;
                    case "블랙헤드":
                        image_url_skin_trouble_3 = R.drawable.trouble2_blackhead;
                        break;
                    case "모공":
                        image_url_skin_trouble_3 = R.drawable.trouble3_pore;
                        break;
                    case "각질":
                        image_url_skin_trouble_3 = R.drawable.trouble4_deadskin;
                        break;
                    case "민감성":
                        image_url_skin_trouble_3 = R.drawable.trouble5_sensitivity;
                        break;
                    case "주름":
                        image_url_skin_trouble_3 = R.drawable.trouble6_wrinkle;
                        break;
                    case "여드름":
                        image_url_skin_trouble_3 = R.drawable.trouble7_acne;
                        break;
                    case "안면홍조":
                        image_url_skin_trouble_3 = R.drawable.trouble8_flush;
                        break;
                    case "없음":
                        image_url_skin_trouble_3 = R.drawable.trouble9_nothing;
                        break;

                }
            }

            String image_url_user = review.profile_url;

            Glide.with(context).
                    load(image_url_user).
                    thumbnail(0.1f).
                    bitmapTransform(new CropCircleTransformation(activity)).
                    into(itemViewHolder.IV_user);
            Glide.with(context).
                    load(image_url_skin_type).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_skin_type);
            Glide.with(context).
                    load(image_url_skin_trouble_1).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_skin_trouble_1);
            Glide.with(context).
                    load(image_url_skin_trouble_2).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_skin_trouble_2);
            Glide.with(context).
                    load(image_url_skin_trouble_3).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_skin_trouble_3);

            itemViewHolder.TV_user_name.setText(review.name);
            if(review.expiration_date != null)
                itemViewHolder.TV_expiration_date.setText(review.expiration_date.substring(0,10));
            if(review.purchase_date != null)
                itemViewHolder.TV_purchase_date.setText(review.purchase_date.substring(0,10));
            itemViewHolder.TV_rating.setText("("+String.valueOf(review.rate_num)+")");
            itemViewHolder.TV_review.setText(review.review);
            itemViewHolder.RB_rate.setRating(Float.valueOf(String.valueOf(review.rate_num)));



            if (position == mDataset.size()-1 && !activity.endOfPage)
                activity.conn_get_review(activity.cosmetic_id, ++activity.page);
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
        public ImageView IV_user, IV_skin_type, IV_skin_trouble_1, IV_skin_trouble_2, IV_skin_trouble_3;
        public TextView TV_expiration_date,TV_purchase_date,TV_rating,TV_review,TV_user_name;
        public RatingBar RB_rate;
        public ItemViewHolder(View v) {
            super(v);
            IV_user = (ImageView) v.findViewById(R.id.IV_user);
            IV_skin_type = (ImageView) v.findViewById(R.id.IV_skin_type);
            IV_skin_trouble_1 = (ImageView) v.findViewById(R.id.IV_skin_trouble_1);
            IV_skin_trouble_2 = (ImageView) v.findViewById(R.id.IV_skin_trouble_2);
            IV_skin_trouble_3 = (ImageView) v.findViewById(R.id.IV_skin_trouble_3);

            TV_user_name = (TextView) v.findViewById(R.id.TV_user_name);
            TV_expiration_date = (TextView) v.findViewById(R.id.TV_expiration_date);
            TV_purchase_date = (TextView) v.findViewById(R.id.TV_purchase_date);
            TV_rating = (TextView) v.findViewById(R.id.TV_rating);
            TV_review = (TextView) v.findViewById(R.id.TV_review);
            RB_rate = (RatingBar) v.findViewById(R.id.RB_rate);
        }
    }

}
