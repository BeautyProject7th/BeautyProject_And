package com.soma.beautyproject_android.DressingTable.YourDressingTable;

/**
 * Created by mijeong on 2017. 4. 23..
 */

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.DressingTable.More.MoreActivity_;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
xml 하나로 통일했어요
 */
@EActivity(R.layout.activity_dressing_table)
public class YourDressingTableActivity extends ParentActivity {

    YourDressingTableActivity activity;
    User you;
    Map<Integer,String> categorylist = new HashMap<Integer, String>();

    @ViewById
    ImageView IV_user;

    @ViewById
    TextView TV_user_name,TV_user_info,TV_skin_type,TV_skin_trouble1,TV_skin_trouble2,TV_skin_trouble3;

    @ViewById
    TextView TV_cosmetic_have_number, TV_following, TV_follower;

    @ViewById
    ImageView BT_expiration_date;

    @ViewById
    Button BT_cosmetic_upload;

    @ViewById
    TextView TV_expiration_date_soon, TV_expiration_date;

    @ViewById
    TextView toolbar_title;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        conn_get_my_cosmetic_info();
        connectTestCall_follow_number();

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

        BT_expiration_date.setVisibility(View.INVISIBLE);
        BT_cosmetic_upload.setVisibility(View.INVISIBLE);
        TV_expiration_date_soon.setVisibility(View.INVISIBLE);
        TV_expiration_date.setVisibility(View.INVISIBLE);


        TV_user_name.setText(you.nickname);
        Log.i("zxc", "you.id : " + you.id + " you.gender" + you.gender + "you.birthyear : " + you.birthyear);

        TV_user_info.setText(you.gender + "/" + (2017 - Integer.valueOf(you.birthyear) + 1)+"세");
        toolbar_title.setText(you.nickname+"님의 화장대");
        String image_url = you.profile_url;

        Glide.with(activity).
                load(image_url).
                thumbnail(0.1f).
                bitmapTransform(new CropCircleTransformation(activity)).into(IV_user);

        categorylist.put(R.id.skin_care,"스킨케어");
        categorylist.put(R.id.cleansing,"클렌징");
        categorylist.put(R.id.mask_pack,"마스크/팩");
        categorylist.put(R.id.suncare,"선케어");
        categorylist.put(R.id.base_makeup,"베이스 메이크업");
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

    void connectTestCall_follow_number() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_follow_number(you.id)
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

    void conn_get_my_cosmetic_info() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_my_cosmetic_info(you.id, you.push_interval)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_get_my_cosmetic_info error", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(final List<Integer> response) {
                        if (response != null) {
                            TV_cosmetic_have_number.setText(response.get(0)+"");
                            TV_expiration_date_soon.setText(response.get(1)+"");
                        } else{

                        }
                    }
                });
    }

}


