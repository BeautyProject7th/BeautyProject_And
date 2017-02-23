package com.makejin.beautyproject_and.DressingTable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

import com.makejin.beautyproject_and.DetailCosmetic.DetailCosmeticActivity;
import com.makejin.beautyproject_and.DetailCosmetic.DetailCosmeticActivity_;
import com.makejin.beautyproject_and.DressingTable.CosmeticUpload.CosmeticUploadActivity_;
import com.makejin.beautyproject_and.DressingTable.Setting.SettingActivity_;
import com.makejin.beautyproject_and.DressingTable.More.MoreActivity_;
import com.makejin.beautyproject_and.Model.Cosmetic;
import com.makejin.beautyproject_and.ParentFragment;
import com.makejin.beautyproject_and.R;
import com.makejin.beautyproject_and.Utils.Connections.CSConnection;
import com.makejin.beautyproject_and.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_and.Utils.Constants.Constants;
import com.makejin.beautyproject_and.Utils.Loadings.LoadingUtil;
import com.makejin.beautyproject_and.Utils.SharedManager.SharedManager;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class DressingTableFragment extends ParentFragment {
    public static DressingTableActivity activity;


    //index - main_category
    //0-스킨케어
    //1-클렌징
    //2-베이스메이크업
    //3-색조메이크업
    //4-마스크팩
    //5-향수

    public DressingTableAdapter adapter[] = new DressingTableAdapter[6];

    private RecyclerView recyclerView [] = new RecyclerView[6];

    private int recyclerView_id [] = new int[6];

    public LinearLayout indicator;
    Button BT_setting;
    Button BT_cosmetic_upload;

    Button BT_more [] = new Button[6];

    String main_category [] = new String[6];

    ImageView IV_user;

    TextView TV_name, TV_id, TV_skin_trouble, TV_skin_type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dressing_table, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final DressingTableActivity dressingTableActivity = (DressingTableActivity) getActivity();
        this.activity = dressingTableActivity;

        BT_setting = (Button)view.findViewById(R.id.BT_setting);
        BT_cosmetic_upload = (Button) view.findViewById(R.id.BT_cosmetic_upload);
        BT_more[0] = (Button) view.findViewById(R.id.BT_more_skin_care);
        BT_more[1] = (Button) view.findViewById(R.id.BT_more_cleansing);
        BT_more[2] = (Button) view.findViewById(R.id.BT_more_base_makeup);
        BT_more[3] = (Button) view.findViewById(R.id.BT_more_color_makeup);
        BT_more[4] = (Button) view.findViewById(R.id.BT_more_mask_pack);
        BT_more[5] = (Button) view.findViewById(R.id.BT_more_perfume);

        main_category[0] = "스킨케어";
        main_category[1] = "클렌징";
        main_category[2] = "베이스메이크업";
        main_category[3] = "색조메이크업";
        main_category[4] = "마스크팩";
        main_category[5] = "향수";

        recyclerView_id[0] = R.id.recycler_view_skin_care;
        recyclerView_id[1] = R.id.recycler_view_cleansing;
        recyclerView_id[2] = R.id.recycler_view_base_makeup;
        recyclerView_id[3] = R.id.recycler_view_color_makeup;
        recyclerView_id[4] = R.id.recycler_view_mask_pack;
        recyclerView_id[5] = R.id.recycler_view_perfume;

        IV_user = (ImageView) view.findViewById(R.id.IV_user);

        TV_name = (TextView) view.findViewById(R.id.TV_name);
        TV_id = (TextView) view.findViewById(R.id.TV_id);
        TV_skin_trouble = (TextView) view.findViewById(R.id.TV_skin_trouble);
        TV_skin_type = (TextView) view.findViewById(R.id.TV_skin_type);

        for(int i=0;i<6;i++)
            connectTestCall(i);

        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        for(int i=0;i<6;i++){
            final int temp_i = i;
            if (recyclerView[temp_i]== null) {
                recyclerView[temp_i] = (RecyclerView) view.findViewById(recyclerView_id[temp_i]);
                recyclerView[temp_i].setHasFixedSize(true);
                recyclerView[temp_i].setLayoutManager(new GridLayoutManager(activity, 4));
            }

            if (adapter[temp_i] == null) {
                adapter[temp_i] = new DressingTableAdapter(new DressingTableAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(activity, DetailCosmeticActivity_.class);
                        intent.putExtra("cosmetic", adapter[temp_i].mDataset.get(position));
                        startActivity(intent);
                        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    }
                }, activity, this);
            }
            recyclerView[temp_i].setAdapter(adapter[i]);

        }
        indicator = (LinearLayout)view.findViewById(R.id.indicator);


        BT_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity_.class));
            }
        });
        BT_cosmetic_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CosmeticUploadActivity_.class));
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

        for(int i=0; i<6; i++){
            final int temp_i = i;
            BT_more[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //이미지 사진 바꿀수 있는 페이지
                    Intent intent = new Intent(getActivity(), MoreActivity_.class);
                    intent.putExtra("main_category_num", temp_i);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void refresh() {
        adapter[0].clear();
        adapter[1].clear();
        adapter[2].clear();
        adapter[3].clear();
        adapter[4].clear();
        adapter[5].clear();

        adapter[0].notifyDataSetChanged();
        adapter[1].notifyDataSetChanged();
        adapter[2].notifyDataSetChanged();
        adapter[3].notifyDataSetChanged();
        adapter[4].notifyDataSetChanged();
        adapter[5].notifyDataSetChanged();
    }

    @Override
    public void reload() {
        refresh();
    }

    void connectTestCall(int main_category_num) {
        final int temp_main_category_num = main_category_num;
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(CSConnection.class);
        conn.myMainCategoryCosmetic(SharedManager.getInstance().getMe().id, main_category[temp_main_category_num])
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cosmetic>>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Cosmetic> response) {
                        if (response.size() != 0) {
                            for (Cosmetic cosmetic : response) {
                                adapter[temp_main_category_num].addData(cosmetic);
                            }
                            adapter[temp_main_category_num].notifyDataSetChanged();
                        } else {
                            //Toast.makeText(getActivity(), "데이터 없", Toast.LENGTH_SHORT).show();\
                        }
                    }
                });
    }
    @Override
    public void onResume() {
        super.onResume();
        //refresh();
    }
}