package com.soma.beautyproject_android.Search.MoreSearch;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.Model.Video_Youtuber;
import com.soma.beautyproject_android.Model.Youtuber;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Search.VideoDetailActivity_;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
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

@EActivity(R.layout.activity_video_more_search)
public class VideoMoreSearchActivity extends ParentActivity {
    VideoMoreSearchActivity activity;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    VideoMoreSearchAdapter adapter;
    List<Video> video_list = new ArrayList<>();
    public TextView TV_product_quantity;

    public int page = 1;
    public boolean endOfPage = false;
    public String keyword;
    public Spinner SP_sort;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual
        refresh();

    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        keyword = getIntent().getStringExtra("keyword");

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
            adapter = new VideoMoreSearchAdapter(new VideoMoreSearchAdapter.OnItemClickListener() {
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


        SP_sort = (Spinner) findViewById(R.id.SP_sort);

        final Spinner SP_sort = (Spinner)findViewById(R.id.SP_sort);
        final String[] items = new String[]{"정렬", "정확도순", "조회순"}; //정렬 -> 최신순
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_2, items);
        SP_sort.setAdapter(adapter);

        SP_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                items[0] = "최신순";
                Log.i("zxc", "items.toString() : " + items.toString());
                //selectedItem = items[position];
                ((TextView) arg0.getChildAt(0)).setText(items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }
    void refresh(){
        page = 1;
        endOfPage = false;
        adapter.clear();
        adapter.notifyDataSetChanged();
        conn_video_more_search(page, keyword);
        conn_video_more_search_quantity(TV_product_quantity, keyword);
    }

    void conn_video_more_search(final int page_num, String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.search_video_more(keyword, page_num)
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
                        if (response != null) {
                            for(int i=0;i<response.size();i++)
                                adapter.addData(response.get(i));
                            adapter.notifyDataSetChanged();
                        } else {
                            endOfPage = true;
                        }
                    }
                });
    }

//
//
//    void conn_get_youtuber(String youtuber_name, final int i) {
//        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
//        conn.get_youtuber(youtuber_name)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Video_Youtuber>() {
//                    @Override
//                    public final void onCompleted() {
//
//                    }
//                    @Override
//                    public final void onError(Throwable e) {
//                        e.printStackTrace();
//                        Toast.makeText(activity, "conn_get_youtuber 에러", Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public final void onNext(Video_Youtuber response) {
//                        if (response != null && !adapter.mDataset_youtuber.contains(response)) {
//                            adapter.addData_video(response);
//                            adapter.addData_youtuber(response);
//                            adapter.notifyDataSetChanged();
//                        } else{
//
//                        }
//                    }
//                });
//    }


    void conn_video_more_search_quantity(final TextView view, String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.search_video_more_quantity(keyword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Integer>>() {
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
                    public final void onNext(List<Integer> response) {
                        if (response != null) {
                            view.setText(response.get(0)+"");
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


