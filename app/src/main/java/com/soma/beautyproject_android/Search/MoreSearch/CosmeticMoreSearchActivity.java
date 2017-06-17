package com.soma.beautyproject_android.Search.MoreSearch;

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

import com.soma.beautyproject_android.DetailCosmetic.DetailCosmeticActivity_;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Search.SearchAdapterAutoComplete;
import com.soma.beautyproject_android.Search.SearchFragmentBrand;
import com.soma.beautyproject_android.Search.SearchFragmentSearchResult;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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

@EActivity(R.layout.activity_cosmetic_more_search)
public class CosmeticMoreSearchActivity extends ParentActivity {
    CosmeticMoreSearchActivity activity;
    RecyclerView recyclerView;
    RecyclerView recyclerView_auto_complete;

    public LinearLayout LL_non_search_auto_complete;
    private RecyclerView.LayoutManager layoutManager;
    CosmeticMoreSearchAdapter adapter;
    public SearchAdapterAutoComplete adapter_auto_complete;

    List<Cosmetic> cosmetic_list = new ArrayList<>();
    public TextView TV_product_quantity;

    public int page = 1;
    public boolean endOfPage = false;
    public String keyword;
    public Spinner SP_sort;

    @ViewById
    LinearLayout cell_search_result_cosmetic_more;

    public Button BT_back, BT_search,BT_close_circle;
    public EditText ET_search;

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
        LL_non_search_auto_complete = (LinearLayout) findViewById(R.id.LL_non_search_auto_complete);

        if (recyclerView_auto_complete == null) {
            recyclerView_auto_complete = (RecyclerView) findViewById(R.id.RV_search_auto_complete);
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
                    Intent intent = new Intent(activity, DetailCosmeticActivity_.class);
                    intent.putExtra("cosmetic_id", adapter.getItem(position).id);
                    intent.putExtra("user_id", SharedManager.getInstance().getMe().id);
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
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


        BT_back = (Button) findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        BT_search = (Button) findViewById(R.id.BT_search);
        ET_search = (EditText) findViewById(R.id.ET_search);
        ET_search.clearFocus();
        BT_close_circle = (Button) findViewById(R.id.BT_close_circle);

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
                if(activity.keyword == null){
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

    void refresh(){
        page = 1;
        endOfPage = false;
        adapter.clear();
        adapter.notifyDataSetChanged();
        conn_cosmetic_more_search(page, keyword);
        conn_cosmetic_more_search_quantity(TV_product_quantity, keyword);

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
                        if (response.size() != 0) {
                            for (Cosmetic cosmetic : response) {
                                adapter.addData(cosmetic);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            endOfPage = true;
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

}


