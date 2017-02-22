package com.makejin.beautyproject_and.DetailCosmetic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makejin.beautyproject_and.ParentFragment;
import com.makejin.beautyproject_and.R;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class DetailCosmeticFragment extends ParentFragment {
    public static DetailCosmeticActivity activity;

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    SwipeRefreshLayout pullToRefresh;

    ImageView IV_product;

    TextView TV_product_name, TV_main_category, TV_sub_category, TV_brand;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_cosmetic, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final DetailCosmeticActivity detailCosmeticActivity = (DetailCosmeticActivity) getActivity();
        this.activity = detailCosmeticActivity;

        IV_product = (ImageView) view.findViewById(R.id.IV_product);

        TV_product_name = (TextView) view.findViewById(R.id.TV_product_name);
        TV_main_category = (TextView) view.findViewById(R.id.TV_main_category);
        TV_sub_category = (TextView) view.findViewById(R.id.TV_sub_category);
        TV_brand = (TextView) view.findViewById(R.id.TV_brand);

        //connectTestCall();
        //connectTestCall_UserInfo();


        TV_product_name.setText(activity.cosmetic.product_name);
        TV_main_category.setText(activity.cosmetic.main_category);
        TV_sub_category.setText(activity.cosmetic.sub_category);
        TV_brand.setText(activity.cosmetic.brand);


        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        indicator = (LinearLayout)view.findViewById(R.id.indicator);
//        pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pull_to_refresh);
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                pullToRefresh.setRefreshing(false);
//                refresh();
//            }
//        });

        IV_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 사진 바꿀수 있는 페이지
//                Intent intent = new Intent(getActivity(), .class);
//                intent.putExtra("user_id", SharedManager.getInstance().getMe().id);
//                startActivity(intent);
            }
        });

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
