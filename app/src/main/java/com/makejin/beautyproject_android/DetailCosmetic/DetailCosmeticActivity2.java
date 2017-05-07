package com.makejin.beautyproject_android.DetailCosmetic;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_android.Model.Cosmetic;
import com.makejin.beautyproject_android.ParentActivity;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Constants.Constants;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;


import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mijeong on 2017. 5. 6..
 */

@EActivity(R.layout.detail_cosmetic_activity)
public class DetailCosmeticActivity2 extends ParentActivity {
    DetailCosmeticActivity2 activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    ImageView IV_product,BT_delete,BT_modify;

    @ViewById
    TextView TV_product_name,TV_brand,TV_main_category, TV_sub_category;

    @ViewById
    TextView TV_expiration_date,TV_purchase_date,TV_rating,TV_review;

    Cosmetic cosmetic = null;
    String cosmetic_id = null;

    @ViewById
    RatingBar RB_rate;

    @ViewById
    Switch using_switch;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        cosmetic_id = getIntent().getStringExtra("cosmetic_id");
    }

    @Override
    public void onResume() {
        super.onResume();
        connectTestCall_get(cosmetic_id);
    }

    @Click
    void BT_delete(){
        Toast.makeText(activity,"삭제버튼 눌림",Toast.LENGTH_SHORT).show();
    }

    @Click
    void BT_modify(){
        Intent intent = new Intent(activity, ModifyCosmeticActivity_.class);
        intent.putExtra("cosmetic",cosmetic);
        startActivity(intent);
    }

    @Click
    void BT_back(){
        activity.finish();
    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall_get(final String cosmetic_id) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.myOneCosmetic_get(SharedManager.getInstance().getMe().id, cosmetic_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Cosmetic>() {
                    @Override
                    public final void onCompleted() {
                        String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;

                        Glide.with(activity).
                                load(image_url).
                                thumbnail(0.1f).
                                into(IV_product);
                        TV_product_name.setText(cosmetic.product_name);
                        TV_main_category.setText(cosmetic.main_category);
                        TV_sub_category.setText(cosmetic.sub_category);
                        TV_brand.setText(cosmetic.brand);
                        RB_rate.setRating(cosmetic.rate_num);

                        if(cosmetic.review!=null) TV_review.setText(cosmetic.review);

                        if(cosmetic.status == 1) using_switch.setChecked(true);
                        else using_switch.setChecked(false);

                        if(cosmetic.expiration_date != null){
                            TV_expiration_date.setText("유통기한 : " + cosmetic.expiration_date.substring(0,10));
                        }

                        if(cosmetic.rate_num >0 ){
                            RB_rate.setRating(cosmetic.rate_num);
                            TV_rating.setText("별점 : "+cosmetic.rate_num);
                        }
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(Cosmetic response) {
                        if (response != null) {
                            cosmetic = response;
                        } else {
                            Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


