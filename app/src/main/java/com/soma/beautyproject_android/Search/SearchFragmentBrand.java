package com.soma.beautyproject_android.Search;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_3;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.DividerItemDecoration;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FindUserActivity_;
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
import static com.soma.beautyproject_android.R.id.people_search;
import static com.soma.beautyproject_android.R.id.recycler_view;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class SearchFragmentBrand extends Fragment {
    SearchActivity activity;

    public SearchAdapterBrand adapter;
    public SearchAdapterAutoComplete adapter_auto_complete;

    public LinearLayout LL_non_search_auto_complete,people_search;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_auto_complete;

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    Button fab;
    public int page = 1;
    public boolean endOfPage = false;

    EditText ET_search;
    Button BT_close_circle, BT_search;
    Button BT_category, BT_back;

    private static List<String> auto_complete_keyword_list = new ArrayList<>();
    private ArrayList<Brand> brandList = null;

    Toolbar cs_toolbar;

    ArrayAdapter<String> autoCompleteAdapter;

    private RecyclerView recyclerView_recommand;
    public SearchAdapterRecommand adapter_recommand;

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


        if (recyclerView_recommand == null) {
            recyclerView_recommand = (RecyclerView) view.findViewById(R.id.RV_search_recommand);
            recyclerView_recommand.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            recyclerView_recommand.setLayoutManager(layoutManager);
        }

        if (adapter_recommand == null) {
            adapter_recommand = new SearchAdapterRecommand(new SearchAdapterRecommand.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ET_search.setText(adapter_recommand.getItem(position).toString());
                    BT_search.callOnClick();
                }
            }, activity, activity);
        }
        recyclerView_recommand.setAdapter(adapter_recommand);


        people_search = (LinearLayout) view.findViewById(R.id.people_search);

        people_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FindUserActivity_.class);
                startActivity(intent);
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        cs_toolbar = (Toolbar) view.findViewById(R.id.cs_toolbar);

        ET_search = (EditText) view.findViewById(R.id.ET_search);
        BT_search = (Button) view.findViewById(R.id.BT_search);
        BT_close_circle = (Button) view.findViewById(R.id.BT_close_circle);
        BT_category = (Button) view.findViewById(R.id.BT_category);

        if (recyclerView == null) {
            recyclerView = (RecyclerView) view.findViewById(recycler_view);


            recyclerView.addItemDecoration(new DividerItemDecoration(activity,1));
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(activity, 1));
        }

        brandList = SharedManager.getInstance().getBrand();
        if (adapter == null) {
            adapter = new SearchAdapterBrand(new SearchAdapterBrand.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    activity.brand = adapter.getItem(position);
                    if(activity.main_category == null & activity.sub_category == null){
                        BT_category.callOnClick();
                    }else{
                        Intent intent = new Intent(getApplicationContext(), CosmeticUploadActivity_3.class);
                        intent.putExtra("brand", activity.brand);
                        intent.putExtra("main_category",activity.main_category);
                        intent.putExtra("sub_category", activity.sub_category);
                        intent.putExtra("before_intent_page", "2");
                        startActivity(intent);
                        activity.finish();
                    }
                }
            }, activity, this);
        }
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        adapter.addData(brandList);
        adapter.notifyDataSetChanged();

        indicator = (LinearLayout)view.findViewById(R.id.indicator);

        ET_search.clearFocus();
        BT_close_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ET_search.setText("");
            }
        });

        BT_back = (Button) view.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
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
            }
        });
        ET_search.addTextChangedListener(textChecker);

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

        ET_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(ET_search.getText().toString().equals("")){
                        LL_non_search_auto_complete.setVisibility(View.INVISIBLE);
                        recyclerView_recommand.setVisibility(View.VISIBLE);
                        conn_recommand_search("a");
                    }
                }
            }
        });

    }


    public void refresh() {
        adapter.clear();
        adapter.notifyDataSetChanged();

    }


    final TextWatcher textChecker = new TextWatcher() {
        public void afterTextChanged(Editable s) {}

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            activity.curKeyword = s.toString();
            if(s.toString().equals("")){
                adapter_auto_complete.clear();
                //LL_non_search_auto_complete.setVisibility(View.VISIBLE);
                recyclerView_auto_complete.setVisibility(View.INVISIBLE);
                recyclerView_recommand.setVisibility(View.VISIBLE);
                conn_recommand_search("a");
            }else{
                //LL_non_search_auto_complete.setVisibility(View.INVISIBLE);
                recyclerView_auto_complete.setVisibility(View.VISIBLE);
                recyclerView_recommand.setVisibility(View.GONE);
                conn_auto_complete_search(s.toString());
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        //refresh();
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


    void conn_recommand_search(String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.recommand_search(keyword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public final void onCompleted() {
                        adapter_recommand.notifyDataSetChanged();
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(activity, "conn_auto_complete_search 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<String> response) {
                        if (response != null) {
                            adapter_recommand.clear();
                            for(int i=0;i<response.size();i++){
                                adapter_recommand.addData(response.get(i));
                            }
                        } else{
                        }
                    }
                });
    }
}