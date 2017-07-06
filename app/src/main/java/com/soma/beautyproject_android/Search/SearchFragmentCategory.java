package com.soma.beautyproject_android.Search;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_3;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadAdapter_2;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FindUserActivity_;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.Model.Category;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.ViewById;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class SearchFragmentCategory extends Fragment {
    SearchActivity activity;

    SearchFragmentCategory fragment;

    public RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public SearchAdapterCategory adapter;
    public SearchAdapterAutoComplete adapter_auto_complete;


    public LinearLayout indicator;
    Button fab;
    public int page = 1;
    public boolean endOfPage = false;

    EditText ET_search;
    Button BT_close_circle, BT_search;
    Button BT_brand,BT_back;

    private Map<String, List<String>> categorylist = new HashMap<String, List<String>>();

    private ExpandableListView mListView;

    Toolbar cs_toolbar;

    LinearLayout LL_search_category, people_search;

    private RecyclerView recyclerView_recommand;
    public SearchAdapterRecommand adapter_recommand;

    /**
     * Create a new instance of the fragment
     */
    public static SearchFragmentCategory newInstance(int index) {
        SearchFragmentCategory fragment = new SearchFragmentCategory();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_category, container, false);
        initViewSetting(view);

        return view;
    }

    private void initViewSetting(View view) {
        final SearchActivity searchActivity = (SearchActivity) getActivity();
        this.activity = searchActivity;
        fragment = this;

        if (recyclerView == null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.RV_search_auto_complete);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        }

        if (adapter_auto_complete == null) {
            adapter_auto_complete = new SearchAdapterAutoComplete(new SearchAdapterAutoComplete.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ET_search.setText(adapter_auto_complete.getItem(position).toString());
                    BT_search.callOnClick();
                }
            }, getActivity(), fragment);
        }
        recyclerView.setAdapter(adapter_auto_complete);

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

        cs_toolbar = (Toolbar) view.findViewById(R.id.cs_toolbar);
        LL_search_category = (LinearLayout) view.findViewById(R.id.LL_search_category);
        people_search = (LinearLayout) view.findViewById(R.id.people_search);

        people_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FindUserActivity_.class);
                startActivity(intent);
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        mListView = (ExpandableListView) view.findViewById(R.id.ExpandableListView);
        categorylist = SharedManager.getInstance().getCategory();
        adapter = new SearchAdapterCategory(activity, categorylist);
        mListView.setAdapter(adapter);

        ET_search = (EditText) view.findViewById(R.id.ET_search);
        ET_search.clearFocus();
        BT_search = (Button) view.findViewById(R.id.BT_search);
        BT_close_circle = (Button) view.findViewById(R.id.BT_close_circle);
        BT_brand = (Button) view.findViewById(R.id.BT_brand);

        ET_search.addTextChangedListener(textChecker);

        indicator = (LinearLayout)view.findViewById(R.id.indicator);

        BT_back = (Button) view.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        BT_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchFragmentBrand();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.activity_search, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

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

        ET_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(ET_search.getText().toString().equals("")){
                        LL_search_category.setVisibility(View.INVISIBLE);
                        recyclerView_recommand.setVisibility(View.VISIBLE);
                        conn_recommand_search("a");
                    }
                }
            }
        });

        // 그룹 클릭 했을 경우 이벤트
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // 차일드 클릭 했을 경우 이벤트
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                v.setBackgroundColor(getResources().getColor(R.color.colorGrayDark));

                activity.main_category = adapter.getGroup(groupPosition);
                activity.sub_category = adapter.getChild(groupPosition, childPosition);

                if(activity.brand != null){
                    Intent intent = new Intent(getApplicationContext(), CosmeticUploadActivity_3.class);
                    intent.putExtra("brand", activity.brand);
                    intent.putExtra("main_category", adapter.getGroup(groupPosition));
                    intent.putExtra("sub_category", adapter.getChild(groupPosition,childPosition));
                    intent.putExtra("before_intent_page", "2");
                    startActivity(intent);
                    activity.finish();
                }else{
                    BT_brand.callOnClick();
                }
//                Intent intent = new Intent(getApplicationContext(), CosmeticUploadActivity_3.class);
//                intent.putExtra("brand", brand);
//                intent.putExtra("main_category", adapter.getGroup(groupPosition));
//                intent.putExtra("sub_category", adapter.getChild(groupPosition,childPosition));
//
//                startActivity(intent);
//                finish();

                return false;
            }
        });

        // 그룹이 닫힐 경우 이벤트
        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // 그룹이 열릴 경우 이벤트
        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
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
                recyclerView.setVisibility(View.INVISIBLE);
                recyclerView_recommand.setVisibility(View.VISIBLE);
                conn_recommand_search("a");
            }else{
                //LL_non_search_auto_complete.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView_recommand.setVisibility(View.GONE);
                conn_auto_complete_search(s.toString());
            }

        }
    };

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

    @Override
    public void onResume() {
        super.onResume();
    }

}