package com.soma.beautyproject_android.Search;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.Model.Youtuber;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class SearchFragmentSearchResult extends Fragment {
    SearchActivity activity;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public SearchAdapterSearchResult adapter;
    public LinearLayout indicator;
    public SearchFragmentSearchResult fragment;
    public TextView TV_product_quantity;
    public Button BT_search,BT_close_circle;
    public EditText ET_search;

    public int page_video = 1;
    public boolean endOfPage = false;

    /**
     * Create a new instance of the fragment
     */
    public static SearchFragmentSearchResult newInstance(int index) {
        SearchFragmentSearchResult fragment = new SearchFragmentSearchResult();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        initViewSetting(view);

        return view;
    }

    private void initViewSetting(View view) {
        final SearchActivity searchActivity = (SearchActivity) getActivity();
        this.activity = searchActivity;
        fragment = this;

        if (recyclerView == null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        }

        if (adapter == null) {
            adapter = new SearchAdapterSearchResult(new SearchAdapterSearchResult.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            }, getActivity(), fragment);
        }
        recyclerView.setAdapter(adapter);

        TV_product_quantity = (TextView) view.findViewById(R.id.TV_product_quantity);
        BT_search = (Button) view.findViewById(R.id.BT_search);
        ET_search = (EditText) view.findViewById(R.id.ET_search);

        BT_close_circle = (Button) view.findViewById(R.id.BT_close_circle);

        BT_close_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ET_search.setText("");
            }
        });

        BT_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.keyword = ET_search.getText().toString();
                Fragment fragment = new SearchFragmentSearchResult();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.activity_search, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                //conn_search_cosmetic(ET_search.getText().toString());
                //conn_search_video(ET_search.getText().toString());
            }
        });
        ET_search.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode ==  KeyEvent.KEYCODE_ENTER && KeyEvent.ACTION_DOWN == event.getAction())
                {
                    BT_search.callOnClick();

                    recyclerView.invalidate();

                    //ET_search.callOnClick();
                    return true;
                }
                // TODO Auto-generated method stub
                return false;
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh(){
        adapter.clear();
        adapter.notifyDataSetChanged();
        conn_search_brand(activity.keyword);
        conn_search_cosmetic(TV_product_quantity, activity.keyword);
        conn_search_video(activity.keyword, page_video);
        //conn_search_video(activity.keyword);
    }

    void conn_search_brand(String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.search_brand(keyword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Brand>>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_search_brand 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Brand> response) {
                        if (response != null) {
                            for(int i=0;i<response.size();i++){
                                adapter.addData_brand(response.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        } else{

                        }
                    }
                });
    }

    void conn_search_cosmetic(final TextView view, String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.search_cosmetic_limit_3(keyword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cosmetic>>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_search_cosmetic 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Cosmetic> response) {
                        if (response != null) {
                            for(int i=0;i<response.size() && i<3; i++){
                                adapter.addData_cosmetic(response.get(i));
                            }
                            view.setText(response.size()+"");
                            adapter.notifyDataSetChanged();
                        } else{

                        }
                    }
                });
    }

    void conn_search_video(String keyword, final int page_num) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.search_video(keyword, page_num)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Video>>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_search_video 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Video> response) {
                        if (response != null) {
                            for(int i=0;i<response.size();i++){
                                adapter.addData_video(response.get(i));
                                conn_get_youtuber(response.get(i).youtuber_name, i);
                            }
                            adapter.notifyDataSetChanged();
                        } else{
                            endOfPage = true;
                        }
                    }
                });
    }

    void conn_get_youtuber(String youtuber_name, final int i) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_youtuber(youtuber_name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Youtuber>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_get_youtuber 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(Youtuber response) {
                        if (response != null) {
                            adapter.addData_youtuber(response, i);
                            adapter.notifyDataSetChanged();
                        } else{
                            endOfPage = true;
                        }
                    }
                });
    }
}