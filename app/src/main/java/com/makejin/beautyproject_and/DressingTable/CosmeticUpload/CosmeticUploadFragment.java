package com.makejin.beautyproject_and.DressingTable.CosmeticUpload;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makejin.beautyproject_and.DetailCosmetic.DetailCosmeticActivity;
import com.makejin.beautyproject_and.DressingTable.DressingTableActivity;
import com.makejin.beautyproject_and.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_and.DressingTable.More.MoreActivity;
import com.makejin.beautyproject_and.DressingTable.More.MoreAdapter;
import com.makejin.beautyproject_and.Model.Brand;
import com.makejin.beautyproject_and.Model.DressingTable;
import com.makejin.beautyproject_and.ParentFragment;
import com.makejin.beautyproject_and.R;
import com.makejin.beautyproject_and.Utils.Connections.CSConnection;
import com.makejin.beautyproject_and.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_and.Utils.Constants.Constants;
import com.makejin.beautyproject_and.Utils.Loadings.LoadingUtil;
import com.makejin.beautyproject_and.Utils.SharedManager.SharedManager;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class CosmeticUploadFragment extends ParentFragment {
    public static CosmeticUploadActivity activity;

    public CosmeticUploadAdapter adapter;

    private RecyclerView recycler_view;

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    SwipeRefreshLayout pullToRefresh;

    TextView TV_category;

    Button BT_home;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cosmetic_upload, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final CosmeticUploadActivity cosmeticUploadActivity = (CosmeticUploadActivity) getActivity();
        this.activity = cosmeticUploadActivity;
        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        BT_home = (Button) cs_toolbar.findViewById(R.id.BT_home);
        BT_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getActivity(), DressingTableActivity_.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");


        if (recycler_view == null) {
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new GridLayoutManager(activity, 2));
        }
        if (adapter == null) {
            adapter = new CosmeticUploadAdapter(new CosmeticUploadAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("brand", adapter.getItem(position));

                    Fragment fragment = new CosmeticUploadFragment_2();
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.activity_cosmetic_upload, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            }, activity, this);
        }
        recycler_view.setAdapter(adapter);

        indicator = (LinearLayout)view.findViewById(R.id.indicator);




    }

    @Override
    public void refresh() {
        adapter.clear();
        adapter.notifyDataSetChanged();

        connectTestCall();

    }

    @Override
    public void reload() {
        refresh();
    }

    void connectTestCall() {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(CSConnection.class);
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
                        Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Brand> response) {
                        if (response != null) {
                            for (Brand brand : response) {
                                adapter.addData(brand);
                            }
                            adapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
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
