package com.makejin.beautyproject_android.DressingTable;

/**
 * Created by mijeong on 2017. 4. 23..
 */
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_android.DressingTable.CosmeticExpirationDate.CosmeticExpirationDateActivity;
import com.makejin.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
import com.makejin.beautyproject_android.DressingTable.More.MoreActivity_;
import com.makejin.beautyproject_android.DressingTable.YourDressingTable.FindUserAdapter;
import com.makejin.beautyproject_android.DressingTable.YourDressingTable.FollowerListActivity_;
import com.makejin.beautyproject_android.DressingTable.YourDressingTable.FollowingListActivity;
import com.makejin.beautyproject_android.DressingTable.YourDressingTable.FollowingListActivity_;
import com.makejin.beautyproject_android.Model.GlobalResponse;
import com.makejin.beautyproject_android.Setting.SettingActivity_;
import com.makejin.beautyproject_android.ParentActivity;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Constants.Constants;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;
import com.makejin.beautyproject_android.DressingTable.YourDressingTable.FindUserActivity_;
import com.makejin.beautyproject_android.Model.User;
import com.tsengvn.typekit.Typekit;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.makejin.beautyproject_android.R.id.BT_find_user;

@EActivity(R.layout.activity_dressing_table)
public class DressingTableActivity extends ParentActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    DressingTableActivity activity;

    Map<Integer,String> categorylist = new HashMap<Integer, String>();

    User me;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    ImageView IV_user,IV_back;

    @ViewById
    TextView TV_name,TV_skin_type,TV_skin_trouble1,TV_skin_trouble2,TV_skin_trouble3;

    @ViewById
    TextView TV_following, TV_follower;

    @ViewById
    Button BT_back,BT_profile_setting, BT_find_user;

    @ViewById
    LinearLayout LL_following, LL_follower;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        connectTestCall_my_follow();

        if(me.skin_type==null) TV_skin_type.setText("미설정");
        else TV_skin_type.setText(me.skin_type);

        if(me.skin_trouble_1==null) TV_skin_trouble1.setText("미설정");
        else TV_skin_trouble1.setText(me.skin_trouble_1);

        if(me.skin_trouble_2==null) TV_skin_trouble2.setText("미설정");
        else TV_skin_trouble2.setText(me.skin_trouble_2);

        if(me.skin_trouble_3==null) TV_skin_trouble3.setText("미설정");
        else TV_skin_trouble3.setText(me.skin_trouble_3);
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        BT_back.setVisibility(View.GONE);
        IV_back.setVisibility(View.GONE);

        me = SharedManager.getInstance().getMe();

        String image_url = me.profile_url;

        Glide.with(activity).
                load(image_url).
                thumbnail(0.1f).
                bitmapTransform(new CropCircleTransformation(activity)).into(IV_user);
        TV_name.setText(me.name);

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
    void LL_following(){
        startActivity(new Intent(activity, FollowingListActivity_.class));
    }

    @Click
    void LL_follower(){
        startActivity(new Intent(activity, FollowerListActivity_.class));
    }

    @Click({R.id.BT_find_user})
    void BT_find_user(){
        Intent intent = new Intent(activity, FindUserActivity_.class);
        startActivity(intent);
//        startActivityForResult(intent, Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_REQUEST);
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Click({R.id.BT_profile_setting})
    void BT_profile_setting(){
        startActivity(new Intent(activity, SettingActivity_.class));
    }

    @Click
    void BT_cosmetic_upload(){
        Intent intent = new Intent(activity, CosmeticUploadActivity_1.class);
        startActivityForResult(intent, Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_REQUEST);
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Click
    void BT_expiration_date(){
        Intent intent = new Intent(activity, CosmeticExpirationDateActivity.class);
        startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    void goCategoryActivity(int view_id){
        Intent intent = new Intent(activity, MoreActivity_.class);
        intent.putExtra("main_category", categorylist.get(view_id));
        intent.putExtra("me",true);
        startActivity(intent);
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


    void connectTestCall_my_follow() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_my_follow(SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "팔로우 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<String> response) {
                        if (response != null) {
                            TV_following.setText(response.get(0));
                            TV_follower.setText(response.get(1));
                        } else{

                        }
                    }
                });
    }

}


