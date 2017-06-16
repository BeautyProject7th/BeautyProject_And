package com.soma.beautyproject_android.Search.MoreSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.Model.Video_Youtuber;
import com.soma.beautyproject_android.Model.Youtuber;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Constants.Constants;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class VideoMoreSearchAdapter extends RecyclerView.Adapter<VideoMoreSearchAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public VideoMoreSearchActivity activity;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Video_Youtuber> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public VideoMoreSearchAdapter(OnItemClickListener onItemClickListener, Context mContext, VideoMoreSearchActivity mActivity) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        activity = mActivity;
        mDataset.clear();
    }

    public void addData(Video_Youtuber video_youtuber) {
        mDataset.add(video_youtuber);
    }

    public Video_Youtuber getItem(int position) {
        return mDataset.get(position);
    }
    public void clear() {
        mDataset.clear();
    }

    @Override
     public VideoMoreSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_result_video, parent, false);
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

            Video_Youtuber video_youtuber;

            video_youtuber = mDataset.get(position);
            String image_url_youtuber = "";
            int image_url_skin_type = -1;
            int image_url_skin_trouble_1 = -1;
            int image_url_skin_trouble_2 = -1;
            int image_url_skin_trouble_3 = -1;


            if(position < mDataset.size()){
                image_url_youtuber = video_youtuber.profile_url;
                Log.i("zxc", position +" youtuber.skin_type" + video_youtuber.skin_type);


                switch(video_youtuber.skin_type){
                    case "건성":
                        Log.i("zxc", position +" youtuber.skin_type(건성) " + video_youtuber.skin_type);
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

                switch(video_youtuber.skin_trouble_1){
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

                switch(video_youtuber.skin_trouble_2){
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

                switch(video_youtuber.skin_trouble_3){
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
//                }

                itemViewHolder.TV_video_name.setText(video_youtuber.title);
                itemViewHolder.TV_view_cnt.setText(String.valueOf(video_youtuber.view_cnt));
                itemViewHolder.TV_upload_date.setText(video_youtuber.upload_date.substring(0,10));
                itemViewHolder.BT_video_more.setVisibility(View.GONE);
                itemViewHolder.TV_youtuber_name.setText(video_youtuber.youtuber_name);

//                String image_url_skin_type = Constants.IMAGE_BASE_URL_youtuber + y
//                String image_url_skin_trouble_1 = Constants.IMAGE_BASE_URL_youtuber + y
//                String image_url_skin_trouble_2 = Constants.IMAGE_BASE_URL_youtuber + y
//                String image_url_skin_trouble_3 = Constants.IMAGE_BASE_URL_youtuber + y

                String image_url_video = Constants.IMAGE_BASE_URL_video + video_youtuber.thumbnail;

                Glide.with(context).
                        load(image_url_youtuber).
                        thumbnail(0.1f).
                        bitmapTransform(new CropCircleTransformation(activity)).
                        into(itemViewHolder.IV_youtuber);
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

                Log.i("ZXc",image_url_video);

                Glide.with(context)
                        .load(image_url_video)
                        .thumbnail(0.1f).
                        into(itemViewHolder.IV_video);

            }

            if (position == mDataset.size()-1 && !activity.endOfPage)
                activity.conn_video_more_search(++activity.page, activity.keyword);
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
        public TextView TV_video_name, TV_view_cnt, TV_upload_date, TV_youtuber_name;
        public ImageView IV_youtuber, IV_video, IV_skin_type, IV_skin_trouble_1, IV_skin_trouble_2, IV_skin_trouble_3;
        public Button BT_video_more;

        public ItemViewHolder(View v) {
            super(v);
            TV_video_name = (TextView) v.findViewById(R.id.TV_video_name);
            TV_view_cnt = (TextView) v.findViewById(R.id.TV_view_cnt);
            TV_upload_date = (TextView) v.findViewById(R.id.TV_upload_date);
            TV_youtuber_name = (TextView) v.findViewById(R.id.TV_youtuber_name);

            BT_video_more = (Button) v.findViewById(R.id.BT_video_more);

            IV_youtuber = (ImageView) v.findViewById(R.id.IV_youtuber);
            IV_video = (ImageView) v.findViewById(R.id.IV_video);
            IV_skin_type = (ImageView) v.findViewById(R.id.IV_skin_type);
            IV_skin_trouble_1 = (ImageView) v.findViewById(R.id.IV_skin_trouble_1);
            IV_skin_trouble_2 = (ImageView) v.findViewById(R.id.IV_skin_trouble_2);
            IV_skin_trouble_3 = (ImageView) v.findViewById(R.id.IV_skin_trouble_3);
        }
    }

}
