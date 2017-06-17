package com.soma.beautyproject_android.DressingTable.YourDressingTable;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_find_user)
public class FindUserActivity extends AppCompatActivity {
    FindUserActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    Button BT_search, BT_close_circle;

    @ViewById
    EditText ET_search;

    @ViewById
    RelativeLayout RR_search;

    @ViewById
    Button BT_back;

    RecyclerView recycler_view;

    private RecyclerView.LayoutManager layoutManager;

    public FindUserAdapter adapter;

    public LinearLayout indicator;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        ET_search.setHint("다른 유저를 검색해 보세요.");

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
            adapter = new FindUserAdapter(new FindUserAdapter.OnItemClickListener() {
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

        ET_search.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode ==  KeyEvent.KEYCODE_ENTER && KeyEvent.ACTION_DOWN == event.getAction())
                {
                    BT_search.callOnClick();
                    //ET_search.callOnClick();
                    return true;
                }
                // TODO Auto-generated method stub
                return false;
            }
        });

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


    @Click
    void BT_back(){
        onBackPressed();
    }


    @Click
    void BT_close_circle(){
        ET_search.setText("");
    }

    @Click
    void BT_search(){
        connectTestCall_search(ET_search.getText().toString());
        recycler_view.invalidate();
    }

    @Click
    void RR_search(){
        ET_search.callOnClick();
    }

    void refresh() {
        adapter.clear();
        adapter.notifyDataSetChanged();
        connectTestCall_recommend();
    }

    void connectTestCall_recommend() {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_recommendUsers(SharedManager.getInstance().getMe().id)
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
                        //Toast.makeText(getApplicationContext(), "이미 등록한 화장품입니다.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<User> response) {
                        if (response != null) {
                            for(User u : response){
                                adapter.addData(u);
                            }
                            adapter.notifyDataSetChanged();
                            //Toast.makeText(getApplicationContext(), "정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    void connectTestCall_search(String search_keyword) {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_searchUsers(SharedManager.getInstance().getMe().id, search_keyword)
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
                        //Toast.makeText(getApplicationContext(), "이미 등록한 화장품입니다.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<User> response) {
                        if (response != null) {
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                            for(User u : response){
                                adapter.addData(u);
                            }
                            adapter.notifyDataSetChanged();
                            //Toast.makeText(getApplicationContext(), "정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


