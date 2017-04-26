package com.makejin.beautyproject_android.DressingTable;

/**
 * Created by mijeong on 2017. 4. 23..
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
import com.makejin.beautyproject_android.DressingTable.More.MoreActivity_;
import com.makejin.beautyproject_android.DressingTable.Setting.SettingActivity_;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Constants.Constants;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;
import com.makejin.beautyproject_android.DressingTable.YourDressingTable.FindUserActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

@EActivity(R.layout.activity_dressing_table)
public class DressingTableActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    DressingTableActivity activity;

    Map<Integer,String> categorylist = new HashMap<Integer, String>();

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    ImageView IV_user;

    @ViewById
    TextView TV_name;

    @ViewById
    Button BT_find_user;

    @ViewById
    Button BT_profile_setting;



    @AfterViews
    void afterBindingView() {
        this.activity = this;
        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        String image_url = SharedManager.getInstance().getMe().profile_url;

        Glide.with(activity).
                load(image_url).
                thumbnail(0.1f).
                bitmapTransform(new CropCircleTransformation(activity)).into(IV_user);
        TV_name.setText(SharedManager.getInstance().getMe().name);

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

    @Click
    void BT_find_user(){
        Intent intent = new Intent(activity, FindUserActivity_.class);
        startActivity(intent);
//        startActivityForResult(intent, Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_REQUEST);
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Click
    void BT_profile_setting(){
        startActivity(new Intent(activity, SettingActivity_.class));
    }

    @Click
    void BT_cosmetic_upload(){
        Intent intent = new Intent(activity, CosmeticUploadActivity_1.class);
        startActivityForResult(intent, Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_REQUEST);
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    void goCategoryActivity(int view_id){
        Intent intent = new Intent(activity, MoreActivity_.class);
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

    /// EXIT
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(getApplicationContext(), getString(R.string.exit_app), Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


