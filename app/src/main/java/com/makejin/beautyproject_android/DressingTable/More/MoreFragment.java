package com.makejin.beautyproject_android.DressingTable.More;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makejin.beautyproject_android.DetailCosmetic.DetailCosmeticActivity_;
import com.makejin.beautyproject_android.Model.Cosmetic;
import com.makejin.beautyproject_android.Model.User;
import com.makejin.beautyproject_android.ParentFragment;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.makejin.beautyproject_android.R.id.BT_home;
import static com.makejin.beautyproject_android.R.id.TV_skin_trouble1;
import static com.makejin.beautyproject_android.R.id.TV_skin_trouble2;
import static com.makejin.beautyproject_android.R.id.TV_skin_trouble3;
import static com.makejin.beautyproject_android.R.id.TV_skin_type;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class MoreFragment extends ParentFragment {
    public static MoreActivity activity;

    public MoreAdapter adapter;

    private RecyclerView recycler_view;

    public LinearLayout indicator;

    TextView TV_desc, TV_category;

    Button BT_back;

    public int page_num = 1;
    public boolean endOfPage = false;

    private User user = null;

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

        TV_category = (TextView) view.findViewById(R.id.TV_category);

        TV_desc = (TextView) view.findViewById(R.id.TV_desc);

        TV_category.setText(moreActivity.main_category);
        TV_desc.setText(user.name + "님의 " + moreActivity.main_category + " 목록입니다.");

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
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recycler_view.setAdapter(adapter);


        indicator = (LinearLayout)view.findViewById(R.id.indicator);

    }

    @Override
    public void refresh() {
        page_num=1;
        endOfPage=false;
        adapter.clear();
        connectTestCall(activity.main_category);
    }

    @Override
    public void reload() {
        refresh();
    }

    void connectTestCall(String main_category) {
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
