package com.soma.beautyproject_android.Search;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadAdapter_3;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by mijeong on 2017. 4. 23..
 * <p>
 * Created by mijeong on 2017. 4. 23..
 */
/**
 * Created by mijeong on 2017. 4. 23..
 */

@EActivity(R.layout.activity_cosmetic_more_search)
public class CosmeticMoreSearchActivity extends ParentActivity {
    CosmeticMoreSearchActivity activity;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    CosmeticMoreSearchAdapter adapter;
    List<Cosmetic> cosmetic_list = new ArrayList<>();
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
            adapter = new CosmeticMoreSearchAdapter(new CosmeticMoreSearchAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                }
            }, activity, this);
        }
        recyclerView.setAdapter(adapter);
        conn_cosmetic_more_search(page, keyword);

        TV_product_quantity = (TextView) findViewById(R.id.TV_product_quantity);
        conn_cosmetic_more_search_quantity(TV_product_quantity, keyword);

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
    }

    void conn_cosmetic_more_search(final int page_num, String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.search_cosmetic_more(keyword, page_num)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cosmetic>>() {
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
                    public final void onNext(List<Cosmetic> response) {
                        if (response != null) {
                            for (Cosmetic cosmetic : response) {
                                adapter.addData(cosmetic);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            endOfPage = true;
                            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    void conn_cosmetic_more_search_quantity(final TextView view, String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.search_cosmetic_more_quantity(keyword)
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
                            //Log.i("Zxc", "response.toString() : " + response.get(0)+"");
                            view.setText(response.get(0)+"");
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


