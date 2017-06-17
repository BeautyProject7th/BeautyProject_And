package com.soma.beautyproject_android.DressingTable.CosmeticExpirationDate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.soma.beautyproject_android.DetailCosmetic.DetailCosmeticActivity_;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.DividerItemDecoration;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.soma.beautyproject_android.R.id.toolbar_title;

/**
 * Created by mijeong on 2017. 4. 30..
 */

public class CosmeticExpirationDateActivity extends ParentActivity {
    public static CosmeticExpirationDateActivity activity;

    public CosmeticExpirationDateAdapter adapter;

    private RecyclerView recycler_view;

    public LinearLayout indicator;

    public TextView TV_product_quantity, TV_expiration_date_soon;

    Button BT_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiration_date_list);
        activity = this;

        Toolbar cs_toolbar = (Toolbar) findViewById(R.id.cs_toolbar);
        TextView toolbar_title = (TextView) cs_toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("유통기한 임박리스트");

        BT_back = (Button) cs_toolbar.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        TV_product_quantity = (TextView) findViewById(R.id.TV_product_quantity);
        TV_expiration_date_soon = (TextView) findViewById(R.id.TV_expiration_date_soon);

        TV_expiration_date_soon.setText(SharedManager.getInstance().getMe().push_interval);

        if (recycler_view == null) {
            recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
            recycler_view.addItemDecoration(new DividerItemDecoration(activity,1));
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new GridLayoutManager(activity, 1));
        }
        if (adapter == null) {
            adapter = new CosmeticExpirationDateAdapter(new CosmeticExpirationDateAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetailCosmeticActivity_.class);
                    intent.putExtra("cosmetic_id", adapter.mDataset.get(position).id);
                    intent.putExtra("user_id",SharedManager.getInstance().getMe().id);
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recycler_view.setAdapter(adapter);

        indicator = (LinearLayout) findViewById(R.id.indicator);

    }

    public void refresh() {
        adapter.clear();
        adapter.notifyDataSetChanged();

        conn_expiration_cosmetic_get();
    }

    void conn_expiration_cosmetic_get() {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.expiration_cosmetic_get(SharedManager.getInstance().getMe().id)
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
                    }
                    @Override
                    public final void onNext(List<Cosmetic> response) {
                        if (response != null) {
                            for (Cosmetic cosmetic : response) {
                                adapter.addData(cosmetic);
                            }
                            TV_product_quantity.setText(response.size()+"");
                            adapter.notifyDataSetChanged();

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
