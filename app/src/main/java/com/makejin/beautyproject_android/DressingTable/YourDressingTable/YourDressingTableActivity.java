package com.makejin.beautyproject_android.DressingTable.YourDressingTable;

/**
 * Created by mijeong on 2017. 4. 23..
 */

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_android.Model.User;
import com.makejin.beautyproject_android.ParentActivity;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

@EActivity(R.layout.activity_your_dressing_table)
public class YourDressingTableActivity extends ParentActivity {

    YourDressingTableActivity activity;

    Map<Integer,String> categorylist = new HashMap<Integer, String>();

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    ImageView IV_user;

    @ViewById
    TextView TV_name;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        User you = SharedManager.getInstance().getYou();
        String image_url = you.profile_url;

        Glide.with(activity).
                load(image_url).
                thumbnail(0.1f).
                bitmapTransform(new CropCircleTransformation(activity)).into(IV_user);
        TV_name.setText(SharedManager.getInstance().getYou().name);

        categorylist.put(R.id.skin_care,"스킨케어");
        categorylist.put(R.id.cleansing,"클렌징");
        categorylist.put(R.id.mask_pack,"마스크/팩");
        categorylist.put(R.id.suncare,"선케어");
        categorylist.put(R.id.base_makeup,"베이스메이크업");
        categorylist.put(R.id.eye_makeup,"아이 메이크업");
        categorylist.put(R.id.lip_makeup,"립 메이크업");
        categorylist.put(R.id.body,"바디");
        categorylist.put(R.id.hair,"헤어");
        categorylist.put(R.id.nail,"네일");
        categorylist.put(R.id.perfume,"향수");
        categorylist.put(R.id.cosmetic_product,"화장 소품");
        categorylist.put(R.id.man,"남성 화장품");
    }

    @Click({R.id.skin_care,R.id.cleansing,R.id.mask_pack,R.id.suncare,R.id.base_makeup, R.id.eye_makeup,
            R.id.lip_makeup,R.id.body,R.id.hair, R.id.nail,R.id.perfume,R.id.cosmetic_product,R.id.man})
    void onClick(View v){
        goCategoryActivity(v.getId());
    }

    void goCategoryActivity(int view_id){
        Intent intent = new Intent(activity, YourMoreActivity_.class);
        intent.putExtra("main_category", categorylist.get(view_id));
        startActivity(intent);
    }

    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


