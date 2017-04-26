package com.makejin.beautyproject_android.DressingTable.YourDressingTable;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_android.Model.User;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Constants.Constants;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.ArrayList;

public class FindUserAdapter extends RecyclerView.Adapter<FindUserAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;

    public Context context;
    public FindUserActivity activity;
    private FindUserAdapter.OnItemClickListener mOnItemClickListener;
    public ArrayList<User> mDataset = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public FindUserAdapter(FindUserAdapter.OnItemClickListener onItemClickListener, Context mContext, FindUserActivity mActivity) {
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
    public FindUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_find_user, parent, false);
            return new FindUserAdapter.ItemViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(FindUserAdapter.ViewHolder holder, final int position) {
        if (holder instanceof FindUserAdapter.ItemViewHolder) {
            final FindUserAdapter.ItemViewHolder itemViewHolder = (FindUserAdapter.ItemViewHolder) holder;
            final User user = mDataset.get(position);

            String image_url = Constants.IMAGE_BASE_URL_users + user.profile_url;
            Log.i("ZXc","makejin2:"+image_url);
            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    into(itemViewHolder.IV_user);

            itemViewHolder.TV_user_name.setText(user.name);
            itemViewHolder.TV_skin_type_.setText(user.skin_type);
            itemViewHolder.TV_skin_trouble_.setText(user.skin_trouble_1);
            if(user.skin_trouble_2 != null)
                itemViewHolder.TV_skin_trouble_.append(", "+user.skin_trouble_2);
            if(user.skin_trouble_3 != null)
                itemViewHolder.TV_skin_trouble_.append(", "+user.skin_trouble_3);



            itemViewHolder.LL_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, YourDressingTableActivity_.class);
                    //intent.putExtra("user",user);
                    SharedManager.getInstance().setYou(user);
                    activity.startActivity(intent);
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
    public class ItemViewHolder extends FindUserAdapter.ViewHolder {
        public Button BT_follow;
        public ImageView IV_user;
        public TextView TV_user_name;
        public TextView TV_skin_type_;
        public TextView TV_skin_trouble_;
        public LinearLayout LL_user;


        public ItemViewHolder(View v) {
            super(v);
            BT_follow = (Button) v.findViewById(R.id.BT_follow);
            IV_user = (ImageView) v.findViewById(R.id.IV_user);
            TV_user_name = (TextView) v.findViewById(R.id.TV_user_name);
            TV_skin_type_ = (TextView) v.findViewById(R.id.TV_skin_type_);
            TV_skin_trouble_ = (TextView) v.findViewById(R.id.TV_skin_trouble_);

            LL_user = (LinearLayout) v.findViewById(R.id.LL_user);
        }
    }
}
