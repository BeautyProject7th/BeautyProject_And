package com.soma.beautyproject_android.DressingTable.YourDressingTable;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FollowingListAdapter extends RecyclerView.Adapter<FollowingListAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;

    public Context context;
    public FollowingListActivity activity;
    private FollowingListAdapter.OnItemClickListener mOnItemClickListener;
    public ArrayList<User> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public FollowingListAdapter(FollowingListAdapter.OnItemClickListener onItemClickListener, Context mContext, FollowingListActivity mActivity) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        activity = mActivity;
        mDataset.clear();
    }

    public void addData(User User) {
        mDataset.add(User);
    }

    public User getItem(int position) {
        return mDataset.get(position);
    }

    public void clear() {
        mDataset.clear();
    }

    @Override
    public FollowingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_find_user, parent, false);
            return new FollowingListAdapter.ItemViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(FollowingListAdapter.ViewHolder holder, final int position) {
        if (holder instanceof FollowingListAdapter.ItemViewHolder) {
            final FollowingListAdapter.ItemViewHolder itemViewHolder = (FollowingListAdapter.ItemViewHolder) holder;
            final User user = mDataset.get(position);

            connectTestCall_follow_get(itemViewHolder, user.id);

            //String image_url =
            String image_url = user.profile_url;
            Log.i("ZXc","soma2:"+image_url);
            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    bitmapTransform(new CropCircleTransformation(activity)).
                    into(itemViewHolder.IV_user);

            itemViewHolder.TV_user_name.setText(user.name);

            int image_url_skin_type = 0;
            int image_url_skin_trouble_1 = 0;
            int image_url_skin_trouble_2 = 0;
            int image_url_skin_trouble_3 = 0;

            if(user.skin_type != null) {
                switch (user.skin_type) {
                    case "건성":
                        image_url_skin_type = R.drawable.skin_type1;
                        break;
                    case "중성":
                        image_url_skin_type = R.drawable.skin_type2;
                        break;
                    case "지성(일반)":
                        image_url_skin_type = R.drawable.skin_type3;
                        break;
                    case "지성(수부지)":
                        image_url_skin_type = R.drawable.skin_type4;
                        break;
                }

                Glide.with(context).
                        load(image_url_skin_type).
                        thumbnail(0.1f).
                        bitmapTransform(new CropCircleTransformation(activity)).
                        into(itemViewHolder.IV_skin_type);

                itemViewHolder.TV_skin_type.setText(user.skin_type);
            }



            if(user.skin_trouble_1 != null) {
                switch (user.skin_trouble_1) {
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
                    case "여드름/트러블":
                        image_url_skin_trouble_1 = R.drawable.trouble7_acne;
                        break;
                    case "안면홍조":
                        image_url_skin_trouble_1 = R.drawable.trouble8_flush;
                        break;
                    case "없음":
                        break;
                }

                Glide.with(context).
                        load(image_url_skin_trouble_1).
                        thumbnail(0.1f).
                        bitmapTransform(new CropCircleTransformation(activity)).
                        into(itemViewHolder.IV_skin_trouble_1);

                itemViewHolder.TV_skin_trouble_1.setText(user.skin_trouble_1);
            }



            if(user.skin_trouble_2 != null) {
                switch (user.skin_trouble_2){
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
                    case "여드름/트러블":
                        image_url_skin_trouble_2 = R.drawable.trouble7_acne;
                        break;
                    case "안면홍조":
                        image_url_skin_trouble_2 = R.drawable.trouble8_flush;
                        break;
                    case "없음":
                        break;
                }

                Glide.with(context).
                        load(image_url_skin_trouble_2).
                        thumbnail(0.1f).
                        bitmapTransform(new CropCircleTransformation(activity)).
                        into(itemViewHolder.IV_skin_trouble_2);
                itemViewHolder.TV_skin_trouble_2.setText(user.skin_trouble_2);
            }
            if(user.skin_trouble_3 != null) {
                switch (user.skin_trouble_3){
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
                    case "여드름/트러블":
                        image_url_skin_trouble_3 = R.drawable.trouble7_acne;
                        break;
                    case "안면홍조":
                        image_url_skin_trouble_3 = R.drawable.trouble8_flush;
                        break;
                    case "없음":
                        break;
                }
                Glide.with(context).
                        load(image_url_skin_trouble_3).
                        thumbnail(0.1f).
                        bitmapTransform(new CropCircleTransformation(activity)).
                        into(itemViewHolder.IV_skin_trouble_3);
                itemViewHolder.TV_skin_trouble_3.append(user.skin_trouble_3);
            }

            itemViewHolder.LL_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, YourDressingTableActivity_.class);
                    //intent.putExtra("user",user);
                    SharedManager.getInstance().setYou(user);
                    activity.startActivity(intent);
                }
            });

            itemViewHolder.FF_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connectTestCall_follow_post(itemViewHolder, user.id);
                }
            });

//            if (position == mDataset.size()-1 && !activity.endOfPage)
//                activity.connectTestCall(activity.brand.name, activity.main_category, activity.sub_category, ++activity.page_num);
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

    public class ItemViewHolder extends FollowingListAdapter.ViewHolder {
        public Button BT_follow;
        public ImageView IV_user, IV_skin_type, IV_skin_trouble_1, IV_skin_trouble_2, IV_skin_trouble_3;
        public TextView TV_user_name;
        public TextView TV_skin_type;
        public TextView TV_skin_trouble_1, TV_skin_trouble_2, TV_skin_trouble_3;
        public LinearLayout LL_user;
        public FrameLayout FF_follow;
        public ImageView IV_follow;


        public ItemViewHolder(View v) {
            super(v);
            BT_follow = (Button) v.findViewById(R.id.BT_follow);
            IV_user = (ImageView) v.findViewById(R.id.IV_user);
            IV_skin_type = (ImageView) v.findViewById(R.id.IV_skin_type);
            IV_skin_trouble_1 = (ImageView) v.findViewById(R.id.IV_skin_trouble_1);
            IV_skin_trouble_2 = (ImageView) v.findViewById(R.id.IV_skin_trouble_2);
            IV_skin_trouble_3 = (ImageView) v.findViewById(R.id.IV_skin_trouble_3);

            TV_user_name = (TextView) v.findViewById(R.id.TV_user_name);
            TV_skin_type = (TextView) v.findViewById(R.id.TV_skin_type);
            TV_skin_trouble_1 = (TextView) v.findViewById(R.id.TV_skin_trouble_1);
            TV_skin_trouble_2 = (TextView) v.findViewById(R.id.TV_skin_trouble_2);
            TV_skin_trouble_3 = (TextView) v.findViewById(R.id.TV_skin_trouble_3);


            LL_user = (LinearLayout) v.findViewById(R.id.LL_user);

            IV_follow = (ImageView) v.findViewById(R.id.IV_follow);
            FF_follow = (FrameLayout) v.findViewById(R.id.FF_follow);
        }
    }


    void connectTestCall_follow_post(final ItemViewHolder itemViewHolder, String followee_id) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_follow_post(SharedManager.getInstance().getMe().id, followee_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "팔로우 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response.message.equals("Created")) {
                            Toast.makeText(activity, "팔로우 완료", Toast.LENGTH_SHORT).show();
                            itemViewHolder.IV_follow.setBackgroundResource(R.drawable.btn_follow_check);
                            itemViewHolder.BT_follow.setBackgroundResource(R.drawable.ic_account_check);
                        } else if(response.message.equals("Deleted")){
                            Toast.makeText(activity, "팔로우 취소 완료", Toast.LENGTH_SHORT).show();
                            itemViewHolder.IV_follow.setBackgroundResource(R.drawable.btn_follow);
                            itemViewHolder.BT_follow.setBackgroundResource(R.drawable.ic_account_plus_4);
                        }
                    }
                });
    }


    void connectTestCall_follow_get(final ItemViewHolder itemViewHolder, String followee_id) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_follow_get(SharedManager.getInstance().getMe().id, followee_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "팔로우 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response.message.equals("Conflict. Already exists")) {
                            itemViewHolder.IV_follow.setBackgroundResource(R.drawable.btn_follow_check);
                            itemViewHolder.BT_follow.setBackgroundResource(R.drawable.ic_account_check);
                        } else if(response.message.equals("No content")){
                            //Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
