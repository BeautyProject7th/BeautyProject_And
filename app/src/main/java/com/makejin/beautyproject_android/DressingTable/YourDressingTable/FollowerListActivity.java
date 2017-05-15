package com.makejin.beautyproject_android.DressingTable.YourDressingTable;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makejin.beautyproject_android.Model.User;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_following_list)
public class FollowerListActivity extends AppCompatActivity {
    FollowerListActivity activity;

    @ViewById
    TextView toolbar_title;

    RecyclerView recycler_view;

    private RecyclerView.LayoutManager layoutManager;

    public FollowerListAdapter adapter;

    public LinearLayout indicator;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        toolbar_title.setText("팔로워 리스트");

        if (recycler_view== null) {
            recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
            //item 사이 간격
            recycler_view.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);

                }
            });
            recycler_view.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            recycler_view.setLayoutManager(layoutManager);
        }

        if (adapter == null) {
            adapter = new FollowerListAdapter(new FollowerListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                    Log.i("Zxc","ZXc999");
//                    Intent intent = new Intent(activity, YourDressingTableActivity_.class);
//                    intent.putExtra("user",adapter.getItem(position));
//                    SharedManager.getInst ance().setYou(adapter.getItem(position));
//                    startActivity(intent);
                }
            }, activity, this);
        }
        recycler_view.setAdapter(adapter);

//
//        User user = new User();
//        user.name="asd";
//        user.skin_type = "skin_type1";
//        user.skin_trouble_1 = "다크서클";
//        user.skin_trouble_2 = "모공확장";
//        user.skin_trouble_3 = "민감성";
//
//        adapter.addData(user);
//
//        User user2 = new User();
//        user2.name="asd2";
//        user2.skin_type = "지성";
//        user2.skin_trouble_1 = "모공확장";
//        user2.skin_trouble_2 = null;
//        user2.skin_trouble_3 = null;
//
//        adapter.addData(user2);
//
//        adapter.notifyDataSetChanged();

        indicator = (LinearLayout) findViewById(R.id.indicator);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    void refresh() {
        adapter.clear();
        adapter.notifyDataSetChanged();
        connectTestCall_load();
    }

    void connectTestCall_load() {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_loadFollowerUsers(SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        LoadingUtil.stopLoading(indicator);
                        e.printStackTrace();
                    }
                    @Override
                    public final void onNext(List<User> response) {
                        if (response != null) {
                            for(User u : response){
                                adapter.addData(u);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


