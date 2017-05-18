package com.soma.beautyproject_android.DressingTable.YourDressingTable;

/**
 * Created by mijeong on 2017. 4. 23..
 */

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.DressingTable.More.MoreActivity_;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/*
xml 하나로 통일했어요
 */
@EActivity(R.layout.activity_dressing_table)
public class YourDressingTableActivity extends ParentActivity {

    YourDressingTableActivity activity;
    User you;
    Map<Integer,String> categorylist = new HashMap<Integer, String>();

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    ImageView IV_user, IV_find_user, IV_setting;

    @ViewById
    TextView TV_name,TV_skin_type,TV_skin_trouble1,TV_skin_trouble2,TV_skin_trouble3,textView;

    @ViewById
    LinearLayout button_space;

    @ViewById
    Button BT_profile_setting,BT_find_user,BT_back;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        if(you.skin_type==null) TV_skin_type.setText("미설정");
        else TV_skin_type.setText(you.skin_type);

        if(you.skin_trouble_1==null) TV_skin_trouble1.setText("미설정");
        else TV_skin_trouble1.setText(you.skin_trouble_1);

        if(you.skin_trouble_2==null) TV_skin_trouble2.setText("미설정");
        else TV_skin_trouble2.setText(you.skin_trouble_2);

        if(you.skin_trouble_3==null) TV_skin_trouble3.setText("미설정");
        else TV_skin_trouble3.setText(you.skin_trouble_3);
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        you = SharedManager.getInstance().getYou();

        BT_profile_setting.setVisibility(View.GONE);
        BT_find_user.setVisibility(View.GONE);
        IV_setting.setVisibility(View.GONE);
        IV_find_user.setVisibility(View.GONE);
        BT_back.setVisibility(View.VISIBLE);

        textView.setText(you.name + "님의 화장대");

        String image_url = you.profile_url;

        Glide.with(activity).
                load(image_url).
                thumbnail(0.1f).
                bitmapTransform(new CropCircleTransformation(activity)).into(IV_user);
        TV_name.setText(SharedManager.getInstance().getYou().name);

        button_space.setVisibility(View.GONE);

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

    @Click
    void BT_back(){
        finish();
    }

    @Click({R.id.skin_care,R.id.cleansing,R.id.mask_pack,R.id.suncare,R.id.base_makeup, R.id.eye_makeup,
            R.id.lip_makeup,R.id.body,R.id.hair, R.id.nail,R.id.perfume,R.id.cosmetic_product,R.id.man})
    void onClick(View v){
        goCategoryActivity(v.getId());
    }

    void goCategoryActivity(int view_id){
        Intent intent = new Intent(activity, MoreActivity_.class);
        intent.putExtra("main_category", categorylist.get(view_id));
        intent.putExtra("me",false);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


