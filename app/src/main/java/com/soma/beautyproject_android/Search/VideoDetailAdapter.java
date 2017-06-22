package com.soma.beautyproject_android.Search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.Video_Youtuber;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Search.MoreSearch.VideoMoreSearchActivity;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.media.CamcorderProfile.get;
import static com.soma.beautyproject_android.R.id.BT_like;
import static com.soma.beautyproject_android.R.id.BT_video_more;
import static com.soma.beautyproject_android.R.id.IV_cosmetic;
import static com.soma.beautyproject_android.R.id.IV_skin_trouble_1;
import static com.soma.beautyproject_android.R.id.IV_skin_trouble_2;
import static com.soma.beautyproject_android.R.id.IV_skin_trouble_3;
import static com.soma.beautyproject_android.R.id.IV_skin_type;
import static com.soma.beautyproject_android.R.id.IV_video;
import static com.soma.beautyproject_android.R.id.IV_youtuber;
import static com.soma.beautyproject_android.R.id.LL_like;
import static com.soma.beautyproject_android.R.id.TV_brand;
import static com.soma.beautyproject_android.R.id.TV_cosmetic;
import static com.soma.beautyproject_android.R.id.TV_upload_date;
import static com.soma.beautyproject_android.R.id.TV_video_name;
import static com.soma.beautyproject_android.R.id.TV_view_cnt;
import static com.soma.beautyproject_android.R.id.TV_youtuber_name;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class VideoDetailAdapter extends RecyclerView.Adapter<VideoDetailAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;

    public Context context;
    public VideoDetailActivity activity;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Boolean> like_flag = new ArrayList<>();
    public ArrayList<Cosmetic> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public VideoDetailAdapter(OnItemClickListener onItemClickListener, Context mContext, VideoDetailActivity mActivity) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        activity = mActivity;
        mDataset.clear();
    }

    public void addData(Cosmetic cosmetic) {
        mDataset.add(cosmetic);
    }
    public void addFlag(Boolean flag) {
        like_flag.add(flag);
    }

    public Cosmetic getItem(int position) {
        return mDataset.get(position);
    }
    public void clear() {
        mDataset.clear();
    }

    @Override
     public VideoDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_detail_video_cosmetic, parent, false);
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

            Glide.with(context).
                    load(Constants.IMAGE_BASE_URL_cosmetics+cosmetic.img_src).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_cosmetic);
            itemViewHolder.TV_price.setText(cosmetic.price+"원");

            itemViewHolder.TV_cosmetic.setText(cosmetic.product_name);
            Log.i("asdf",cosmetic.product_name);
            itemViewHolder.TV_brand.setText(cosmetic.brand);
            itemViewHolder.BT_purchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            itemViewHolder.LL_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
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
        public TextView TV_price,TV_cosmetic,TV_brand;
        public ImageView IV_cosmetic,IV_heart;
        public Button BT_purchase;
        public LinearLayout LL_like;

        public ItemViewHolder(View v) {
            super(v);
            IV_cosmetic=(ImageView)v.findViewById(R.id.IV_cosmetic);
            IV_heart=(ImageView)v.findViewById(R.id.IV_heart);
            TV_price = (TextView)v.findViewById(R.id.TV_price);
            TV_cosmetic = (TextView)v.findViewById(R.id.TV_cosmetic);
            TV_brand = (TextView)v.findViewById(R.id.TV_brand);
            BT_purchase = (Button)v.findViewById(R.id.BT_purchase);
            LL_like = (LinearLayout)v.findViewById(R.id.LL_like);
        }
    }
}
