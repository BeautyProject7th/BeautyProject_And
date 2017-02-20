package com.makejin.beautyproject_and.DressingTable;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.makejin.beautyproject_and.DetailCosmetic.DetailCosmeticActivity;
import com.makejin.beautyproject_and.ParentFragment;
import com.makejin.beautyproject_and.R;
/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class DressingTableFragment extends ParentFragment {
    public static DressingTableActivity activity;

    public DressingTableAdapter adapter_skin_care;
    public DressingTableAdapter adapter_cleansing;
    public DressingTableAdapter adapter_base_makeup;
    public DressingTableAdapter adapter_color_makeup;
    public DressingTableAdapter adapter_mask_pack;
    public DressingTableAdapter adapter_perfume;

    private RecyclerView recyclerView_skin_care;
    private RecyclerView recyclerView_cleansing;
    private RecyclerView recyclerView_base_makeup;
    private RecyclerView recyclerView_color_makeup;
    private RecyclerView recyclerView_mask_pack;
    private RecyclerView recyclerView_perfume;

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    SwipeRefreshLayout pullToRefresh;
    Button BT_setting;
    Button BT_cosmetic_upload;

    Button BT_more_skin_care;
    Button BT_more_cleansing;
    Button BT_more_base_makeup;
    Button BT_more_color_makeup;
    Button BT_more_mask_pack;
    Button BT_more_perfume;


    ImageView IV_user;

    TextView TV_name, TV_id, TV_skin_trouble, TV_skin_type;

    /**
     * Create a new instance of the fragment
     */
    public static DressingTableFragment newInstance(int index) {
        DressingTableFragment fragment = new DressingTableFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dressing_table, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final DressingTableActivity tabActivity = (DressingTableActivity) getActivity();
        this.activity = tabActivity;

        BT_setting = (Button)view.findViewById(R.id.BT_setting);
        BT_cosmetic_upload = (Button) view.findViewById(R.id.BT_cosmetic_upload);
        BT_more_skin_care = (Button) view.findViewById(R.id.BT_more_skin_care);
        BT_more_cleansing = (Button) view.findViewById(R.id.BT_more_cleansing);
        BT_more_base_makeup = (Button) view.findViewById(R.id.BT_more_base_makeup);
        BT_more_color_makeup = (Button) view.findViewById(R.id.BT_more_color_makeup);
        BT_more_mask_pack = (Button) view.findViewById(R.id.BT_more_mask_pack);
        BT_more_perfume = (Button) view.findViewById(R.id.BT_more_perfume);

        IV_user = (ImageView) view.findViewById(R.id.IV_user);

        TV_name = (TextView) view.findViewById(R.id.TV_name);
        TV_id = (TextView) view.findViewById(R.id.TV_id);
        TV_skin_trouble = (TextView) view.findViewById(R.id.TV_skin_trouble);
        TV_skin_type = (TextView) view.findViewById(R.id.TV_skin_type);

        //connectTestCall();
        //connectTestCall_UserInfo();

        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (recyclerView_skin_care == null) {
            recyclerView_skin_care = (RecyclerView) view.findViewById(R.id.recycler_view_skin_care);
            recyclerView_skin_care.setHasFixedSize(true);
            recyclerView_skin_care.setLayoutManager(new GridLayoutManager(activity, 4));
        }
        if (adapter_skin_care == null) {
            adapter_skin_care = new DressingTableAdapter(new DressingTableAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetailCosmeticActivity.class);
                    intent.putExtra("cosmetic", adapter_skin_care.mDataset.get(position));
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recyclerView_skin_care.setAdapter(adapter_skin_care);

        if (recyclerView_cleansing == null) {
            recyclerView_cleansing = (RecyclerView) view.findViewById(R.id.recycler_view_cleansing);
            recyclerView_cleansing.setHasFixedSize(true);
            recyclerView_cleansing.setLayoutManager(new GridLayoutManager(activity, 4));
        }
        if (adapter_cleansing == null) {
            adapter_cleansing = new DressingTableAdapter(new DressingTableAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetailCosmeticActivity.class);
                    intent.putExtra("cosmetic", adapter_cleansing.mDataset.get(position));
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recyclerView_cleansing.setAdapter(adapter_cleansing);

        if (recyclerView_base_makeup == null) {
            recyclerView_base_makeup = (RecyclerView) view.findViewById(R.id.recycler_view_base_makeup);
            recyclerView_base_makeup.setHasFixedSize(true);
            recyclerView_base_makeup.setLayoutManager(new GridLayoutManager(activity, 4));
        }
        if (adapter_base_makeup== null) {
            adapter_base_makeup = new DressingTableAdapter(new DressingTableAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetailCosmeticActivity.class);
                    intent.putExtra("cosmetic", adapter_base_makeup.mDataset.get(position));
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recyclerView_base_makeup.setAdapter(adapter_base_makeup);

        if (recyclerView_color_makeup == null) {
            recyclerView_color_makeup = (RecyclerView) view.findViewById(R.id.recycler_view_color_makeup);
            recyclerView_color_makeup.setHasFixedSize(true);
            recyclerView_color_makeup.setLayoutManager(new GridLayoutManager(activity, 4));
        }
        if (adapter_color_makeup== null) {
            adapter_color_makeup = new DressingTableAdapter(new DressingTableAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetailCosmeticActivity.class);
                    intent.putExtra("cosmetic", adapter_color_makeup.mDataset.get(position));
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recyclerView_color_makeup.setAdapter(adapter_color_makeup);

        if (recyclerView_mask_pack == null) {
            recyclerView_mask_pack = (RecyclerView) view.findViewById(R.id.recycler_view_mask_pack);
            recyclerView_mask_pack.setHasFixedSize(true);
            recyclerView_mask_pack.setLayoutManager(new GridLayoutManager(activity, 4));
        }
        if (adapter_mask_pack == null) {
            adapter_mask_pack = new DressingTableAdapter(new DressingTableAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetailCosmeticActivity.class);
                    intent.putExtra("cosmetic", adapter_mask_pack.mDataset.get(position));
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recyclerView_mask_pack.setAdapter(adapter_mask_pack);

        if (recyclerView_perfume == null) {
            recyclerView_perfume = (RecyclerView) view.findViewById(R.id.recycler_view_perfume);
            recyclerView_perfume.setHasFixedSize(true);
            recyclerView_perfume.setLayoutManager(new GridLayoutManager(activity, 4));
        }
        if (adapter_perfume == null) {
            adapter_perfume = new DressingTableAdapter(new DressingTableAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetailCosmeticActivity.class);
                    intent.putExtra("cosmetic", adapter_perfume.mDataset.get(position));
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recyclerView_perfume.setAdapter(adapter_perfume);

        indicator = (LinearLayout)view.findViewById(R.id.indicator);
//        pullToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.pull_to_refresh);
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                pullToRefresh.setRefreshing(false);
//                refresh();
//            }
//        });

        BT_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
        BT_cosmetic_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CosmeticUploadActivity.class));
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });
        IV_user.setOnClickListener(new View.OnClickListener() {
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
        adapter_skin_care.clear();
        adapter_cleansing.clear();
        adapter_base_makeup.clear();
        adapter_color_makeup.clear();
        adapter_mask_pack.clear();
        adapter_perfume.clear();

        adapter_skin_care.notifyDataSetChanged();
        adapter_cleansing.notifyDataSetChanged();
        adapter_base_makeup.notifyDataSetChanged();
        adapter_color_makeup.notifyDataSetChanged();
        adapter_mask_pack.notifyDataSetChanged();
        adapter_perfume.notifyDataSetChanged();
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
