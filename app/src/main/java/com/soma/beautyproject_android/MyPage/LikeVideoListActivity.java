package com.soma.beautyproject_android.MyPage;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.beautyproject_android.Model.Video_Youtuber;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Search.MoreSearch.VideoMoreSearchAdapter;
import com.soma.beautyproject_android.Search.SearchAdapterAutoComplete;
import com.soma.beautyproject_android.Search.SearchFragmentSearchResult;
import com.soma.beautyproject_android.Search.VideoDetailActivity_;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mijeong on 2017. 4. 23..
 * <p>
 * Created by mijeong on 2017. 4. 23..
 */

/**
 * Created by mijeong on 2017. 4. 23..
 */

@EActivity(R.layout.activity_like_video_list)
public class LikeVideoListActivity extends ParentActivity {
    LikeVideoListActivity activity;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    LikeVideoListAdapter adapter;
    public TextView TV_product_quantity;

    public int page = 1;
    public boolean endOfPage = false;

    public Button BT_back;

    @ViewById
    TextView toolbar_title;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual
        refresh();

    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        toolbar_title.setText("나의 찜 영상");

        if (recyclerView== null) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//            //item 사이 간격
//            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//                @Override
//                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                    super.getItemOffsets(outRect, view, parent, state);
//
//                    outRect.right = 10;
//                }
//            });
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
        }

        if (adapter == null) {
            adapter = new LikeVideoListAdapter(new LikeVideoListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getApplicationContext(), VideoDetailActivity_.class);
                    intent.putExtra("video_youtuber", adapter.getItem(position));
                    startActivity(intent);
                }
            }, activity, this);
        }
        recyclerView.setAdapter(adapter);


        TV_product_quantity = (TextView) findViewById(R.id.TV_product_quantity);


//        final Spinner SP_sort = (Spinner)findViewById(R.id.SP_sort);
//        final String[] items = new String[]{"정렬", "정확도순", "조회순"}; //정렬 -> 최신순
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_2, items);
//        SP_sort.setAdapter(adapter);
//
//        SP_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//                items[0] = "최신순";
//                Log.i("zxc", "items.toString() : " + items.toString());
//                //selectedItem = items[position];
//                ((TextView) arg0.getChildAt(0)).setText(items[position]);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });


        BT_back = (Button) findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }
    void refresh(){
        page = 1;
        endOfPage = false;
        adapter.clear();
        adapter.notifyDataSetChanged();
        conn_like_video_get(SharedManager.getInstance().getMe().id);
    }

    void conn_like_video_get(String user_id) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.like_video_get(user_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Video_Youtuber>>() {
                    @Override
                    public final void onCompleted() {
                        //LoadingUtil.stopLoading(indicator);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Video_Youtuber> response) {
                        if (response.size() != 0) {
                            for(int i=0;i<response.size();i++)
                                adapter.addData(response.get(i));
                            adapter.notifyDataSetChanged();
                            TV_product_quantity.setText(response.size()+"");
                        } else {
                            endOfPage = true;
                        }
                    }
                });
    }


}


