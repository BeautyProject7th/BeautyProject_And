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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.DetailCosmetic.DetailCosmeticActivity_;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.ViewById;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class SearchFragmentSearchResultBrand extends Fragment {
    SearchActivity activity;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public SearchAdapterSearchResultBrand adapter;
    public SearchFragmentSearchResultBrand fragment;
    public TextView TV_product_quantity;

    public Toolbar cs_toolbar;
    public ImageView IV_brand;
    public TextView TV_brand;
    public Brand brand;
    public TextView toolbar_title;

    public int page = 1;
    public boolean endOfPage = false;

    public Button BT_back;


    /**
     * Create a new instance of the fragment
     */
    public static SearchFragmentSearchResultBrand newInstance(int index) {
        SearchFragmentSearchResultBrand fragment = new SearchFragmentSearchResultBrand();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result_brand, container, false);
        initViewSetting(view);

        return view;
    }

    private void initViewSetting(View view) {
        final SearchActivity searchActivity = (SearchActivity) getActivity();
        this.activity = searchActivity;
        fragment = this;


        brand = activity.brand;

//        cs_toolbar = (Toolbar) view.findViewById(R.id.cs_toolbar);
//        cs_toolbar.setTitle(brand.name+" 브랜드관");

        String image_url = Constants.IMAGE_BASE_URL_brand + brand.logo;
        IV_brand = (ImageView) view.findViewById(R.id.IV_brand);
        Glide.with(getActivity()).
                load(image_url).
                thumbnail(0.1f).
                into(IV_brand);
        TV_brand = (TextView) view.findViewById(R.id.TV_brand);

        TV_brand.setText(brand.name);
        toolbar_title = (TextView) view.findViewById(R.id.toolbar_title);
        toolbar_title.setText(brand.name+" 브랜드관");

        BT_back = (Button) view.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });


        if (recyclerView == null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        }

        if (adapter == null) {
            adapter = new SearchAdapterSearchResultBrand(new SearchAdapterSearchResultBrand.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetailCosmeticActivity_.class);
                    intent.putExtra("cosmetic_id", adapter.getItem(position).id);
                    intent.putExtra("user_id", SharedManager.getInstance().getMe().id);
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, getActivity(), fragment);
        }
        recyclerView.setAdapter(adapter);

        TV_product_quantity = (TextView) view.findViewById(R.id.TV_product_quantity);

    }



    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh(){
        page = 1;
        endOfPage = false;
        adapter.clear();
        adapter.notifyDataSetChanged();

        conn_search_cosmetic_by_brand(page, brand.name);
        conn_search_cosmetic_by_brand_quantity(TV_product_quantity, brand.name);
    }

    void conn_search_cosmetic_by_brand(final int page_num, String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        Log.i("by_brand", "page_num : "+page_num);
        conn.search_cosmetic_by_brand(keyword, page_num)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cosmetic>>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "search_cosmetic_by_brand 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Cosmetic> response) {
                        if (response.size() != 0) {
                            for(int i=0;i<response.size(); i++){
                                adapter.addData(response.get(i));
                            }
                            adapter.notifyDataSetChanged();
                        } else{
                            endOfPage = true;
                        }
                    }
                });
    }

    void conn_search_cosmetic_by_brand_quantity(final TextView view, String keyword) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.search_cosmetic_by_brand_quantity(keyword)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_search_cosmetic_by_brand_quantity 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Integer> response) {
                        if (response != null) {
                            view.setText(response.get(0)+"");
                        } else{
                        }
                    }
                });
    }

}