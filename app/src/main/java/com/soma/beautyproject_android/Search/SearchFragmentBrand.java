package com.soma.beautyproject_android.Search;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.Click;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class SearchFragmentBrand extends Fragment {
    SearchActivity activity;

    public SearchAdapterBrand adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    Button fab;
    public int page = 1;
    public boolean endOfPage = false;

    EditText ET_search;
    Button BT_close_circle, BT_search;
    Button BT_category;

    private static List<String> auto_complete_keyword_list = new ArrayList<>();

    Toolbar cs_toolbar;

    ArrayAdapter<String> autoCompleteAdapter;
    /**
     * Create a new instance of the fragment
     */
    public static SearchFragmentBrand newInstance(int index) {
        SearchFragmentBrand fragment = new SearchFragmentBrand();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_brand, container, false);
        initViewSetting(view);

        return view;
    }

    private void initViewSetting(View view) {
        final SearchActivity searchActivity = (SearchActivity) getActivity();
        this.activity = searchActivity;

        cs_toolbar = (Toolbar) view.findViewById(R.id.cs_toolbar);

        ET_search = (EditText) view.findViewById(R.id.ET_search);
        BT_search = (Button) view.findViewById(R.id.BT_search);
        BT_close_circle = (Button) view.findViewById(R.id.BT_close_circle);
        BT_category = (Button) view.findViewById(R.id.BT_category);

        if (recyclerView == null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);
        }

        if (adapter == null) {
            adapter = new SearchAdapterBrand(new SearchAdapterBrand.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                    Intent intent = new Intent(getContext(), DetailActivity_.class);
//                    intent.putExtra("food", adapter.getItem(position));
//                    startActivity(intent);
//                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recyclerView.setAdapter(adapter);

        indicator = (LinearLayout)view.findViewById(R.id.indicator);

        BT_close_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ET_search.setText("");
            }
        });

        BT_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchFragmentCategory();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.activity_search, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
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


    public void refresh() {
        adapter.clear();
        adapter.notifyDataSetChanged();

        connectTestCall();
    }


    void connectTestCall() {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.brand()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Brand>>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Brand> response) {
                        if (response != null) {
                            for (Brand brand : response) {
                                adapter.addData(brand);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
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