package com.soma.beautyproject_android.DressingTable.More;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.beautyproject_android.DetailCosmetic.DetailCosmeticActivity_;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.ParentFragment;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class MoreFragment extends ParentFragment {
    public static MoreActivity activity;

    public MoreAdapter adapter;

    private RecyclerView recycler_view;

    public LinearLayout indicator;

    TextView TV_product_quantity;

    RelativeLayout RL_check;

    ImageView IV_check;

    TextView TV_desc,toolbar_title;

    Button BT_back;

    public int page_num = 1;
    public boolean endOfPage = false;

    private User user = null;

    public boolean check = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        if(activity.me_dressing_table) user = SharedManager.getInstance().getMe();
        else user = SharedManager.getInstance().getYou();
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final MoreActivity moreActivity = (MoreActivity) getActivity();
        this.activity = moreActivity;

        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        BT_back = (Button) cs_toolbar.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        toolbar_title = (TextView) cs_toolbar.findViewById(R.id.toolbar_title);

        TV_desc = (TextView) view.findViewById(R.id.TV_desc);

        RL_check = (RelativeLayout) view.findViewById(R.id.RL_check);
        IV_check = (ImageView) view.findViewById(R.id.IV_check);

        toolbar_title.setText(moreActivity.main_category);
        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        if (recycler_view == null) {
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new GridLayoutManager(activity, 3));

        }
        if (adapter == null) {
            adapter = new MoreAdapter(new MoreAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetailCosmeticActivity_.class);
                    intent.putExtra("cosmetic_id", adapter.mDataset.get(position).id);
                    intent.putExtra("user_id",user.id);
                    intent.putExtra("me",activity.me_dressing_table);
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recycler_view.setAdapter(adapter);


        RL_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IV_check.getVisibility() == View.GONE){
                    check = true;
                    IV_check.setVisibility(View.VISIBLE);
                    adapter.clear();
                    page_num = 1;
                    conn_myMainCategoryCosmetic_only_use(activity.main_category);
                }else {
                    check = false;
                    IV_check.setVisibility(View.GONE);
                    adapter.clear();
                    page_num = 1;
                    conn_myMainCategoryCosmetic(activity.main_category);

                }

            }
        });
        indicator = (LinearLayout)view.findViewById(R.id.indicator);

        TV_product_quantity = (TextView) view.findViewById(R.id.TV_product_quantity);

    }

    @Override
    public void refresh() {
        page_num=1;
        endOfPage=false;
        adapter.clear();
        conn_myMainCategoryCosmetic(activity.main_category);
    }

    @Override
    public void reload() {
        refresh();
    }

    void conn_myMainCategoryCosmetic(String main_category) {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.myMainCategoryCosmetic(user.id, main_category, page_num)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cosmetic>>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        endOfPage = true;
                        adapter.notifyDataSetChanged();
                        TV_product_quantity.setText(0+"");
                    }
                    @Override
                    public final void onNext(List<Cosmetic> response) {
                        if(response.size() != 0){
                            for (Cosmetic cosmetic : response) {
                                adapter.addData(cosmetic);
                            }
                            adapter.notifyDataSetChanged();
                            TV_product_quantity.setText(response.size()+"");
                        } else {
                            endOfPage = true;
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    void conn_myMainCategoryCosmetic_only_use(String main_category) {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.myMainCategoryCosmetic_only_use(user.id, main_category, page_num)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cosmetic>>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        endOfPage = true;
                        adapter.notifyDataSetChanged();
//                        TV_product_quantity.setText(0+"");
                    }
                    @Override
                    public final void onNext(List<Cosmetic> response) {
                        Log.i("response", "reponse : " + response.toString());
                        Log.i("response", "reponse : " + response.toString());
                        Log.i("response", "reponse : " + response.toString());

                        if(response.size() != 0){
                            for (Cosmetic cosmetic : response) {
                                adapter.addData(cosmetic);
                            }
                            adapter.notifyDataSetChanged();
                            TV_product_quantity.setText(response.size()+"");
                        } else {
                            endOfPage = true;
                            adapter.notifyDataSetChanged();
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
