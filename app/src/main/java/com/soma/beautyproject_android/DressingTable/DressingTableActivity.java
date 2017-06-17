package com.soma.beautyproject_android.DressingTable;

/**
 * Created by mijeong on 2017. 4. 23..
 */
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLES20;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.soma.beautyproject_android.DressingTable.CosmeticExpirationDate.CosmeticExpirationDateActivity;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
import com.soma.beautyproject_android.DressingTable.More.MoreActivity_;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FindUserAdapter;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FollowerListActivity_;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FollowingListActivity;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FollowingListActivity_;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Setting.SettingActivity_;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FindUserActivity_;
import com.soma.beautyproject_android.Model.User;
import com.tsengvn.typekit.Typekit;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.soma.beautyproject_android.R.id.BT_find_user;
import static com.soma.beautyproject_android.R.id.TV_expiration_date;

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
    ImageView IV_user;

    @ViewById
    TextView TV_user_name, TV_user_info, TV_skin_type,TV_skin_trouble1,TV_skin_trouble2,TV_skin_trouble3;

    @ViewById
    TextView TV_cosmetic_have_number, TV_following, TV_follower, TV_expiration_date_soon;

    @ViewById
    LinearLayout LL_following, LL_follower;

    @ViewById
    TextView toolbar_title;

    private String imagepath = null;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        //TV_cosmetic_have_number.setText(me.);
        connectTestCall_follow_number();

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

        me = SharedManager.getInstance().getMe();

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("나의 화장대");

        String cosmetic_have_number = getIntent().getStringExtra("cosmetic_have_number");
        String expiration_date_soon = getIntent().getStringExtra("expiration_date_soon");

        TV_cosmetic_have_number.setText(cosmetic_have_number);
        TV_expiration_date_soon.setText(expiration_date_soon);


        String image_url = me.profile_url;

        Glide.with(activity).
                load(image_url).
                thumbnail(0.1f).
                bitmapTransform(new CropCircleTransformation(activity)).into(IV_user);
        TV_user_name.setText(me.nickname);
        //if()
        TV_user_info.setText(me.gender + "/" + (2017 - Integer.valueOf(me.birthyear) + 1)+"세");

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
        finish();
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

    @Click
    void IV_user() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }


    void goCategoryActivity(int view_id){
        Intent intent = new Intent(activity, MoreActivity_.class);
        intent.putExtra("main_category", categorylist.get(view_id));
        intent.putExtra("me",true);
        startActivity(intent);
    }


    void connectTestCall_follow_number() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_follow_number(SharedManager.getInstance().getMe().id)
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


