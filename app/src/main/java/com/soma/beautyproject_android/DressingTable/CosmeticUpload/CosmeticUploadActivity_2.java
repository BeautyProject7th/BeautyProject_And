package com.soma.beautyproject_android.DressingTable.CosmeticUpload;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.soma.beautyproject_android.DressingTable.DressingTableActivity_;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mijeong on 2017. 4. 21..
 */
public class CosmeticUploadActivity_2 extends ParentActivity {

    //새로 시도
    private Map<String, List<String>> categorylist = new HashMap<String, List<String>>();

    public LinearLayout indicator;
    public static CosmeticUploadActivity_2 activity;
    public static CosmeticUploadAdapter_2 adapter = null;

    ImageView IV_brand;
    Brand brand;
    Button BT_back, BT_home;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_upload_2);

        activity = this;

        setLayout();
        categorylist = SharedManager.getInstance().getCategory();
        adapter = new CosmeticUploadAdapter_2(activity, categorylist);
        mListView.setAdapter(adapter);
        Log.i("test","category : "+categorylist);

        Toolbar cs_toolbar = (Toolbar)findViewById(R.id.cs_toolbar);

        BT_back = (Button) cs_toolbar.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        BT_home = (Button) cs_toolbar.findViewById(R.id.BT_home);
        BT_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), DressingTableActivity_.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();
            }
        });

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");


        brand = (Brand) getIntent().getSerializableExtra("brand");

        String image_url = Constants.IMAGE_BASE_URL_brand + brand.logo;

        Glide.with(getApplicationContext()).
                load(image_url).
                thumbnail(0.1f).
                diskCacheStrategy(DiskCacheStrategy.SOURCE).
                into(IV_brand);

        //mListView.setAdapter(new CosmeticUploadAdapter_2(this, categorylist));

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
                Intent intent = new Intent(getApplicationContext(), CosmeticUploadActivity_3.class);
                intent.putExtra("brand", brand);
                intent.putExtra("main_category", adapter.getGroup(groupPosition));
                intent.putExtra("sub_category", adapter.getChild(groupPosition,childPosition));
                intent.putExtra("before_intent_page", "1");
                startActivity(intent);
                finish();
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

    /*
     * Layout
     */
    private ExpandableListView mListView;

    private void setLayout(){
        mListView = (ExpandableListView) findViewById(R.id.ExpandableListView);
        IV_brand = (ImageView) findViewById(R.id.IV_brand);
    }

    /// EXIT
    @Override
    public void onBackPressed() {
        Intent beforeintent = new Intent(getApplicationContext(), CosmeticUploadActivity_1.class);
        beforeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        beforeintent.putExtra("brand", brand);
        startActivity(beforeintent);
        finish();
    }
}