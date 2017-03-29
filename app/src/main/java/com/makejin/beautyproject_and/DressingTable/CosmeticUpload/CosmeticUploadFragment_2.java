package com.makejin.beautyproject_and.DressingTable.CosmeticUpload;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.makejin.beautyproject_and.DetailCosmetic.DetailCosmeticActivity_;
import com.makejin.beautyproject_and.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_and.DressingTable.DressingTableAdapter;
import com.makejin.beautyproject_and.Model.Brand;
import com.makejin.beautyproject_and.Model.Category;
import com.makejin.beautyproject_and.ParentFragment;
import com.makejin.beautyproject_and.R;
import com.makejin.beautyproject_and.Utils.Connections.CSConnection;
import com.makejin.beautyproject_and.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_and.Utils.Constants.Constants;
import com.makejin.beautyproject_and.Utils.Loadings.LoadingUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class CosmeticUploadFragment_2 extends ParentFragment {
    public static CosmeticUploadActivity activity;

    private RecyclerView recyclerView [] = new RecyclerView[7];

    int recyclerView_id [] = new int[7];

    public CosmeticUploadAdapter_2 adapter[] = new CosmeticUploadAdapter_2[7];

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    SwipeRefreshLayout pullToRefresh;

    ImageView IV_brand;

    String brand [] = new String[5];
    String main_category [] = new String[7];
    String main_category_eng [] = new String[7];

    Button BT_home;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cosmetic_upload_2, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final CosmeticUploadActivity cosmeticUploadActivity = (CosmeticUploadActivity) getActivity();
        this.activity = cosmeticUploadActivity;


        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        BT_home = (Button) cs_toolbar.findViewById(R.id.BT_home);
        BT_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getActivity(), DressingTableActivity_.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        recyclerView_id[0] = R.id.recycler_view_skin_care;
        recyclerView_id[1] = R.id.recycler_view_cleansing;
        recyclerView_id[2] = R.id.recycler_view_base_makeup;
        recyclerView_id[3] = R.id.recycler_view_color_makeup;
        recyclerView_id[4] = R.id.recycler_view_mask_pack;
        recyclerView_id[5] = R.id.recycler_view_perfume;
        recyclerView_id[6] = R.id.recycler_view_etc;


        brand[0] = "더샘";
        brand[1] = "더페이스샵";
        brand[2] = "에뛰드하우스";
        brand[3] = "이니스프리";
        brand[4] = "토니모리";

        main_category[0] = "스킨케어";
        main_category[1] = "클렌징";
        main_category[2] = "베이스메이크업";
        main_category[3] = "색조메이크업";
        main_category[4] = "마스크팩";
        main_category[5] = "향수";
        main_category[6] = "기타";


        main_category_eng[0] = "Skin/Care";
        main_category_eng[1] = "Cleansing";
        main_category_eng[2] = "Base Makeup";
        main_category_eng[3] = "Color Makeup";
        main_category_eng[4] = "Mask/Pack";
        main_category_eng[5] = "Perfume";
        main_category_eng[6] = "Etc";

        IV_brand = (ImageView) view.findViewById(R.id.IV_brand);

        final Brand brand = (Brand) getArguments().getSerializable("brand");

        String image_url = Constants.IMAGE_BASE_URL_brand + brand.logo;

        Glide.with(getActivity()).
                load(image_url).
                thumbnail(0.1f).
                into(IV_brand);

        int cnt = 0;

        for(int i=0;i<7;i++){
            final int temp_i = i;
            if (recyclerView[temp_i]== null) {
                recyclerView[temp_i] = (RecyclerView) view.findViewById(recyclerView_id[temp_i]);
                recyclerView[temp_i].setHasFixedSize(true);
                recyclerView[temp_i].setLayoutManager(new GridLayoutManager(activity, 2));
            }



            if (adapter[temp_i] == null) {
                adapter[temp_i] = new CosmeticUploadAdapter_2(new CosmeticUploadAdapter_2.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("zxc", "z : " + temp_i + " " + adapter[temp_i].getItem(position));

                        Bundle bundle = new Bundle(3);
                        bundle.putSerializable("brand", brand);
                        bundle.putString("main_category", main_category[temp_i]);
                        bundle.putString("sub_category", adapter[temp_i].getItem(position));

                        Fragment fragment = new CosmeticUploadFragment_3();
                        fragment.setArguments(bundle);

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.activity_cosmetic_upload, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();
                    }
                }, activity, this);
            }
            recyclerView[temp_i].setAdapter(adapter[temp_i]);
        }

        indicator = (LinearLayout)view.findViewById(R.id.indicator);
    }

    @Override
    public void refresh() {
        for(int i=0;i<7;i++) {
            adapter[i].clear();
            adapter[i].notifyDataSetChanged();
        }
        connectTestCall();
    }


    @Override
    public void reload() {
        refresh();
    }

    void connectTestCall() {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(CSConnection.class);
        conn.category()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Category>>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Category> response) {
                        if (response != null) {
                            for(int i=0;i<response.size();i++){
                                switch(response.get(i).main_category){
                                    case "스킨케어":
                                        adapter[0].addData(response.get(i));
                                        break;
                                    case "클렌징":
                                        adapter[1].addData(response.get(i));
                                        break;
                                    case "베이스메이크업":
                                        adapter[2].addData(response.get(i));
                                        break;
                                    case "색조메이크업":
                                        adapter[3].addData(response.get(i));
                                        break;
                                    case "마스크팩":
                                        adapter[4].addData(response.get(i));
                                        break;
                                    case "향수":
                                        adapter[5].addData(response.get(i));
                                        break;
                                    case "기타":
                                        adapter[6].addData(response.get(i));
                                        break;
                                    default:
                                        break;
                                }
                                adapter[i].notifyDataSetChanged();
                            }
                        } else {
                            //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

}