package com.soma.beautyproject_android.DressingTable.CosmeticUpload;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.soma.beautyproject_android.DressingTable.DressingTableActivity_;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CosmeticUploadActivity_1 extends ParentActivity {
    public static CosmeticUploadActivity_1 activity;

    public CosmeticUploadAdapter_1 adapter;

    private ArrayList<Brand> brandList = new ArrayList<Brand>();

    private RecyclerView recycler_view;

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    SwipeRefreshLayout pullToRefresh;


    Button BT_back, BT_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_upload_1);
        activity = this;

        Toolbar cs_toolbar = (Toolbar) findViewById(R.id.cs_toolbar);

        BT_back = (Button) cs_toolbar.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        BT_home = (Button) cs_toolbar.findViewById(R.id.BT_home);
        BT_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), DressingTableActivity_.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();
            }
        });

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        brandList = SharedManager.getInstance().getBrand();

        if (recycler_view == null) {
            recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
            recycler_view.addItemDecoration(new DividerItemDecoration(activity,1));
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new GridLayoutManager(activity, 1));
        }
        if (adapter == null) {

            adapter = new CosmeticUploadAdapter_1(new CosmeticUploadAdapter_1.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    view.setBackgroundColor(activity.getResources().getColor(R.color.colorGrayDark));
                    Intent intent = new Intent(getApplicationContext(), CosmeticUploadActivity_2.class);
                    intent.putExtra("brand", adapter.getItem(position));
                    startActivity(intent);
                    finish();
                }
            }, activity, this);
        }
        adapter.notifyDataSetChanged();
        recycler_view.setAdapter(adapter);

        adapter.addData(brandList);
        adapter.notifyDataSetChanged();

        indicator = (LinearLayout) findViewById(R.id.indicator);

    }

    public void refresh() {
        adapter.clear();
        adapter.notifyDataSetChanged();

        //connectTestCall();
    }


    @Override
    public void onResume() {
        super.onResume();
        //refresh();
    }
}
