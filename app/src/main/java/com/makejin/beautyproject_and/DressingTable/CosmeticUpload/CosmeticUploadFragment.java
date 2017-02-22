package com.makejin.beautyproject_and.DressingTable.CosmeticUpload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makejin.beautyproject_and.DetailCosmetic.DetailCosmeticActivity;
import com.makejin.beautyproject_and.DressingTable.More.MoreActivity;
import com.makejin.beautyproject_and.DressingTable.More.MoreAdapter;
import com.makejin.beautyproject_and.ParentFragment;
import com.makejin.beautyproject_and.R;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class CosmeticUploadFragment extends ParentFragment {
    public static CosmeticUploadActivity activity;

    public CosmeticUploadAdapter adapter;

    private RecyclerView recycler_view;

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    SwipeRefreshLayout pullToRefresh;

    TextView TV_category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cosmetic_upload, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final CosmeticUploadActivity cosmeticUploadActivity = (CosmeticUploadActivity) getActivity();
        this.activity = cosmeticUploadActivity;

        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);

        //connectTestCall();
        //connectTestCall_UserInfo();

        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (recycler_view == null) {
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new GridLayoutManager(activity, 2));
        }
//        if (adapter == null) {
//            adapter = new CosmeticUploadAdapter(new CosmeticUploadAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    Intent intent = new Intent(activity, CosmeticUploadActivity.class);
//                    intent.putExtra("brand", adapter.mDataset.get(position));
//                    startActivity(intent);
//                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
//                }
//            }, activity, this);
//        }
        recycler_view.setAdapter(adapter);


        indicator = (LinearLayout)view.findViewById(R.id.indicator);
//        pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pull_to_refresh);
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                pullToRefresh.setRefreshing(false);
//                refresh();
//            }
//        });

    }

    @Override
    public void refresh() {
//        connectTestCall();
//        connectTestCall_UserInfo();

    }

    @Override
    public void reload() {
        refresh();
    }
//
//    void connectTestCall() {
//        LoadingUtil.startLoading(indicator);
//        CSConnection conn = ServiceGenerator.createService(CSConnection.class);
//        conn.getLikedFood(SharedManager.getInstance().getMe()._id)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<Food>>() {
//                    @Override
//                    public final void onCompleted() {
//                        LoadingUtil.stopLoading(indicator);
//                    }
//                    @Override
//                    public final void onError(Throwable e) {
//                        e.printStackTrace();
//                        Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public final void onNext(List<Food> response) {
//                        if (response != null) {
//                            for (Food food : response) {
//                                adapter.addData(food);
//                            }
//                            adapter.notifyDataSetChanged();
//
//                        } else {
//                            Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//    void connectTestCall_UserInfo() {
//        LoadingUtil.startLoading(indicator);
//        CSConnection conn = ServiceGenerator.createService(CSConnection.class);
//        conn.getUserInfo(SharedManager.getInstance().getMe()._id)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<User>() {
//                    @Override
//                    public final void onCompleted() {
//                        LoadingUtil.stopLoading(indicator);
//                        TV_user_name.setText(SharedManager.getInstance().getMe().nickname);
//                        TV_about_me.setText(SharedManager.getInstance().getMe().about_me);
//                    }
//                    @Override
//                    public final void onError(Throwable e) {
//                        e.printStackTrace();
//                        Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public final void onNext(User response) {
//                        if (response != null) {
//                            SharedManager.getInstance().setMe(response);
//                            image_url = SharedManager.getInstance().getMe().thumbnail_url;
//                            if(image_url.contains("facebook")){
//                                Glide.with(getActivity()).
//                                        load(image_url).
//                                        thumbnail(0.1f).
//                                        bitmapTransform(new CropCircleTransformation(getActivity())).into(IV_profile);
//                            }else{
//                                Glide.with(getActivity()).
//                                        load(Constants.IMAGE_BASE_URL + image_url).
//                                        thumbnail(0.1f).
//                                        bitmapTransform(new CropCircleTransformation(getActivity())).into(IV_profile);
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
}
