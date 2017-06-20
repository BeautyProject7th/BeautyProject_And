package com.soma.beautyproject_android.Search;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.beautyproject_android.DetailCosmetic.DetailCosmeticActivity_;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.Model.Video_Youtuber;
import com.soma.beautyproject_android.Model.Youtuber;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.ViewById;

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

    public LinearLayout LL_non_search_auto_complete;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_auto_complete;
    private RecyclerView.LayoutManager layoutManager;
    public SearchAdapterAutoComplete adapter_auto_complete;
    public SearchAdapterSearchResult adapter;
    public LinearLayout indicator;
    public SearchFragmentSearchResult fragment;
    public TextView TV_product_quantity;
    public Button BT_back, BT_search,BT_close_circle;
    public EditText ET_search;

    public int page_video = 1;
    public boolean endOfPage = false;

    public String brand_product_quantity = "0";
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
        LL_non_search_auto_complete = (LinearLayout) view.findViewById(R.id.LL_non_search_auto_complete);

        if (recyclerView_auto_complete == null) {
            recyclerView_auto_complete = (RecyclerView) view.findViewById(R.id.RV_search_auto_complete);
            recyclerView_auto_complete.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            recyclerView_auto_complete.setLayoutManager(layoutManager);
        }

        if (adapter_auto_complete == null) {
            adapter_auto_complete = new SearchAdapterAutoComplete(new SearchAdapterAutoComplete.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ET_search.setText(adapter_auto_complete.getItem(position).toString());
                    BT_search.callOnClick();
                }
            }, activity, activity);
        }
        recyclerView_auto_complete.setAdapter(adapter_auto_complete);



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

        //TV_product_quantity = (TextView) view.findViewById(R.id.TV_product_quantity);
        BT_back = (Button) view.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        BT_search = (Button) view.findViewById(R.id.BT_search);
        ET_search = (EditText) view.findViewById(R.id.ET_search);
        ET_search.clearFocus();
        BT_close_circle = (Button) view.findViewById(R.id.BT_close_circle);

        BT_close_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ET_search.setText("");
            }
        });

        ET_search.addTextChangedListener(textChecker);

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
                if(activity.keyword.equals("")){
                    Toast.makeText(activity, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Fragment fragment = new SearchFragmentSearchResult();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.activity_search, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

                recyclerView.invalidate();

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ET_search.getWindowToken(), 0);
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
                    //ET_search.callOnClick();
                    return true;
                }
                // TODO Auto-generated method stub
                return false;
            }
        });

    }


    final TextWatcher textChecker = new TextWatcher() {
        public void afterTextChanged(Editable s) {}

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.toString().equals("")){
                adapter_auto_complete.clear();
                LL_non_search_auto_complete.setVisibility(View.VISIBLE);
                recyclerView_auto_complete.setVisibility(View.INVISIBLE);
            }else{
                LL_non_search_auto_complete.setVisibility(View.INVISIBLE);
                recyclerView_auto_complete.setVisibility(View.VISIBLE);
                conn_auto_complete_search(s.toString());
            }

        }
    };
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh(){
        adapter.clear();
        adapter.notifyDataSetChanged();
        conn_search_brand(activity.keyword);
        conn_search_cosmetic(activity.keyword);
        conn_search_video_one(activity.keyword);
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
                            adapter.addData_brand(response.get(0));
                            conn_get_brand_product_quantity(response.get(0).name);
                            adapter.notifyDataSetChanged();
                        } else{

                        }
                    }
                });
    }

    void conn_search_cosmetic(String keyword) {
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
                            //view.setText(response.size()+"");
                            adapter.notifyDataSetChanged();
                        } else{

                        }
                    }
                });
    }

    void conn_search_video_one(String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.search_video_one(keyword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Video_Youtuber>>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_search_video 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Video_Youtuber> response) {
                        if (response != null) {
                            for(int i=0;i<response.size();i++){
                                adapter.addData_video_youtuber(response.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        } else{
                        }
                    }
                });
    }

    void conn_auto_complete_search(String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.auto_complete_search(keyword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public final void onCompleted() {
                        adapter_auto_complete.notifyDataSetChanged();
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_auto_complete_search 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<String> response) {
                        if (response != null) {
                            adapter_auto_complete.clear();
                            for(int i=0;i<response.size();i++){
                                adapter_auto_complete.addData(response.get(i));
                            }
                        } else{
                        }
                    }
                });
    }

    void conn_get_brand_product_quantity(String brand) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_brand_product_quantity(brand)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_get_brand_product_quantity 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<String> response) {
                        if (response != null) {
                            brand_product_quantity = response.get(0);
                            adapter.notifyDataSetChanged();
                        } else{
                        }
                    }
                });
    }
}