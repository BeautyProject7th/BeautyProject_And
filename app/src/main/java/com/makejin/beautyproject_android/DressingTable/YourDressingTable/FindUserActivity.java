package com.makejin.beautyproject_android.DressingTable.YourDressingTable;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.makejin.beautyproject_android.Model.User;
import com.makejin.beautyproject_android.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_find_user)
public class FindUserActivity extends AppCompatActivity {
    FindUserActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    RecyclerView recycler_view;

    private RecyclerView.LayoutManager layoutManager;

    public FindUserAdapter adapter;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        if (recycler_view== null) {
            recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
            //item 사이 간격
            recycler_view.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);

                }
            });
            recycler_view.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            recycler_view.setLayoutManager(layoutManager);
        }

        if (adapter == null) {
            adapter = new FindUserAdapter(new FindUserAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.i("Zxc","ZXc999");
                    Intent intent = new Intent(activity, YourDressingTableActivity_.class);
                    intent.putExtra("user",adapter.getItem(position));
                    startActivity(intent);
                }
            }, activity, this);
        }
        recycler_view.setAdapter(adapter);


        User user = new User();
        user.name="asd";
        user.skin_type = "건성";
        user.skin_trouble_1 = "다크서클";
        user.skin_trouble_2 = "모공확장";
        user.skin_trouble_3 = "민감성";

        adapter.addData(user);

        User user2 = new User();
        user2.name="asd2";
        user2.skin_type = "지성";
        user2.skin_trouble_1 = "모공확장";
        user2.skin_trouble_2 = null;
        user2.skin_trouble_3 = null;

        adapter.addData(user2);

        adapter.notifyDataSetChanged();
    }

    void refresh() {

    }

    void connectTestCall() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


