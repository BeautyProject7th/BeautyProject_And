package com.soma.beautyproject_android.MyPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soma.beautyproject_android.DetailCosmetic.DetailCosmeticActivity_;
import com.soma.beautyproject_android.DressingTable.CosmeticExpirationDate.CosmeticExpirationDateAdapter;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.ExpirationCosmetic;
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

/**
 * Created by mijeong on 2017. 4. 30..
 */

public class LikeCosmeticListActivity extends ParentActivity {
    public static LikeCosmeticListActivity activity;

    public LikeCosmeticListAdapter adapter;
    private RecyclerView recycler_view;
    public LinearLayout indicator;
    public TextView TV_product_quantity;
    public RelativeLayout RL_cosmetic;
    Button BT_back;
    private RecyclerView.LayoutManager layoutManager;
    TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_cosmetic_list);
        activity = this;

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("나의 찜 목록");

        BT_back = (Button) findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TV_product_quantity = (TextView) findViewById(R.id.TV_product_quantity);

        RL_cosmetic = (RelativeLayout) findViewById(R.id.RL_cosmetic);
        if (recycler_view == null) {
            recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
            //recycler_view.addItemDecoration(new DividerItemDecoration(activity,1));
            recycler_view.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            recycler_view.setLayoutManager(layoutManager);
        }
        if (adapter == null) {
            adapter = new LikeCosmeticListAdapter(new LikeCosmeticListAdapter.OnItemClickListener() {
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

        conn_like_cosmetic_get();
    }

    void conn_like_cosmetic_get() {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.like_cosmetic_get(SharedManager.getInstance().getMe().id)
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
                        adapter.notifyDataSetChanged();
                        TV_product_quantity.setText(0+"");
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
