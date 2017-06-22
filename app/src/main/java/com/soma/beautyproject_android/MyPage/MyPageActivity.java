package com.soma.beautyproject_android.MyPage;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Camera.CameraMainActivity;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Setting.SettingActivity_;
import com.soma.beautyproject_android.Skin.SkinTroubleActivity_;
import com.soma.beautyproject_android.Skin.SkinTypeActivity_;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.soma.beautyproject_android.R.id.TV_skin_type;
import static com.soma.beautyproject_android.R.id.TV_user_info;

/**
 * Created by mijeong on 2017. 6. 18..
 */

@EActivity(R.layout.activity_mypage)
public class MyPageActivity extends ParentActivity {
    MyPageActivity activity;


    @ViewById
    ImageView IV_user, IV_skin_type, IV_skin_trouble_1, IV_skin_trouble_2, IV_skin_trouble_3;

    @ViewById
    Button BT_back, BT_setting, BT_skin_type, BT_skin_trouble, BT_like_cosmetic, BT_like_video, BT_camera;

    @ViewById
    TextView toolbar_title, TV_user_name, TV_user_age,TV_user_sex, TV_skin_type, TV_skin_trouble_1, TV_skin_trouble_2, TV_skin_trouble_3;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        refresh();
    }

    void refresh(){
        conn_get_my_info();
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        toolbar_title.setText("내 정보");
        BT_camera.setVisibility(View.VISIBLE);

    }


    void conn_get_my_info() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_my_info(SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public final void onCompleted() {
                        User me = SharedManager.getInstance().getMe();
                        Glide.with(activity).load(me.profile_url.replace("type=normal", "type=large")).thumbnail(0.1f).bitmapTransform(new CropCircleTransformation(activity)).into(IV_user);
                        TV_user_name.setText(me.nickname);
                        TV_user_sex.setText(me.gender);

                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);

                        TV_user_age.setText((year - Integer.valueOf(me.birthyear) + 1)+"세");
                        TV_skin_type.setText(me.skin_type);
                        TV_skin_trouble_1.setText(me.skin_trouble_1);
                        TV_skin_trouble_2.setText(me.skin_trouble_2);
                        TV_skin_trouble_3.setText(me.skin_trouble_3);
                        //TV_user_info.setText(me.gender + " / " + (2017 - Integer.valueOf(me.birthyear) - 1)+"세");

//                        TV_skin_trouble_1.setText(me.skin_trouble_1);
//                        TV_skin_trouble_2.setText(me.skin_trouble_2);
//                        TV_skin_trouble_3.setText(me.skin_trouble_3);


                        int image_url_skin_type = -1;
                        int image_url_skin_trouble_1 = -1;
                        int image_url_skin_trouble_2 = -1;
                        int image_url_skin_trouble_3 = -1;


                        if(me.skin_type != null){
                            TV_skin_type.setText(me.skin_type);
                            switch(me.skin_type){
                                case "건성":
                                    image_url_skin_type = R.drawable.skin_type1;
                                    break;
                                case "중성":
                                    image_url_skin_type = R.drawable.skin_type2;
                                    break;
                                case "지성":
                                    image_url_skin_type = R.drawable.skin_type3;
                                    break;
                                case "수부지":
                                    image_url_skin_type = R.drawable.skin_type4;
                                    break;

                            }

                            Glide.with(activity).
                                    load(image_url_skin_type).
                                    thumbnail(0.1f).
                                    into(IV_skin_type);

                        }

                        if(me.skin_trouble_1 != null){
                            TV_skin_trouble_1.setText(me.skin_trouble_1);
                            switch(me.skin_trouble_1){
                                case "다크서클":
                                    image_url_skin_trouble_1 = R.drawable.trouble1_darkcircle;
                                    break;
                                case "블랙헤드":
                                    image_url_skin_trouble_1 = R.drawable.trouble2_blackhead;
                                    break;
                                case "모공":
                                    image_url_skin_trouble_1 = R.drawable.trouble3_pore;
                                    break;
                                case "각질":
                                    image_url_skin_trouble_1 = R.drawable.trouble4_deadskin;
                                    break;
                                case "민감성":
                                    image_url_skin_trouble_1 = R.drawable.trouble5_sensitivity;
                                    break;
                                case "주름":
                                    image_url_skin_trouble_1 = R.drawable.trouble6_wrinkle;
                                    break;
                                case "여드름":
                                    image_url_skin_trouble_1 = R.drawable.trouble7_acne;
                                    break;
                                case "안면홍조":
                                    image_url_skin_trouble_1 = R.drawable.trouble8_flush;
                                    break;
                                case "없음":
                                    image_url_skin_trouble_1 = R.drawable.trouble9_nothing;
                                    break;
                            }
                            Glide.with(activity).
                                    load(image_url_skin_trouble_1).
                                    thumbnail(0.1f).
                                    into(IV_skin_trouble_1);


                        }
                        if(me.skin_trouble_2 != null){
                            TV_skin_trouble_2.setText(me.skin_trouble_2);
                            switch(me.skin_trouble_2){
                                case "다크서클":
                                    image_url_skin_trouble_2 = R.drawable.trouble1_darkcircle;
                                    break;
                                case "블랙헤드":
                                    image_url_skin_trouble_2 = R.drawable.trouble2_blackhead;
                                    break;
                                case "모공":
                                    image_url_skin_trouble_2 = R.drawable.trouble3_pore;
                                    break;
                                case "각질":
                                    image_url_skin_trouble_2 = R.drawable.trouble4_deadskin;
                                    break;
                                case "민감성":
                                    image_url_skin_trouble_2 = R.drawable.trouble5_sensitivity;
                                    break;
                                case "주름":
                                    image_url_skin_trouble_2 = R.drawable.trouble6_wrinkle;
                                    break;
                                case "여드름":
                                    image_url_skin_trouble_2 = R.drawable.trouble7_acne;
                                    break;
                                case "안면홍조":
                                    image_url_skin_trouble_2 = R.drawable.trouble8_flush;
                                    break;
                                case "없음":
                                    image_url_skin_trouble_2 = R.drawable.trouble9_nothing;
                                    break;
                            }
                            Glide.with(activity).
                                    load(image_url_skin_trouble_2).
                                    thumbnail(0.1f).
                                    into(IV_skin_trouble_2);


                        }
                        if(me.skin_trouble_3 != null){
                            TV_skin_trouble_3.setText(me.skin_trouble_3);
                            switch(me.skin_trouble_3){
                                case "다크서클":
                                    image_url_skin_trouble_3 = R.drawable.trouble1_darkcircle;
                                    break;
                                case "블랙헤드":
                                    image_url_skin_trouble_3 = R.drawable.trouble2_blackhead;
                                    break;
                                case "모공":
                                    image_url_skin_trouble_3 = R.drawable.trouble3_pore;
                                    break;
                                case "각질":
                                    image_url_skin_trouble_3 = R.drawable.trouble4_deadskin;
                                    break;
                                case "민감성":
                                    image_url_skin_trouble_3 = R.drawable.trouble5_sensitivity;
                                    break;
                                case "주름":
                                    image_url_skin_trouble_3 = R.drawable.trouble6_wrinkle;
                                    break;
                                case "여드름":
                                    image_url_skin_trouble_3 = R.drawable.trouble7_acne;
                                    break;
                                case "안면홍조":
                                    image_url_skin_trouble_3 = R.drawable.trouble8_flush;
                                    break;
                                case "없음":
                                    image_url_skin_trouble_3 = R.drawable.trouble9_nothing;
                                    break;
                            }
                            Glide.with(activity).
                                    load(image_url_skin_trouble_3).
                                    thumbnail(0.1f).
                                    into(IV_skin_trouble_3);

                        }


                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_get_my_info error", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(User response) {
                        if (response != null) {
                            SharedManager.getInstance().setMe(response);
                        } else{

                        }
                    }
                });
    }

    @Click
    void BT_back(){
        onBackPressed();
    }
    @Click
    void BT_setting(){
        Intent intent = new Intent(this, SettingActivity_.class);
        startActivity(intent);
    }
    @Click
    void BT_skin_type(){
        Intent intent = new Intent(this, SkinTypeActivity_.class);
        startActivity(intent);
    }
    @Click
    void BT_skin_trouble(){
        Intent intent = new Intent(this, SkinTroubleActivity_.class);
        startActivity(intent);
    }
    @Click
    void BT_like_cosmetic(){
        Intent intent = new Intent(this, LikeCosmeticListActivity.class);
        startActivity(intent);
    }
    @Click
    void BT_like_video(){
        Intent intent = new Intent(this, LikeVideoListActivity_.class);
        startActivity(intent);
    }
    @Click
    void BT_camera(){
        startActivity(new Intent(getApplicationContext(), CameraMainActivity.class));
    }
}
