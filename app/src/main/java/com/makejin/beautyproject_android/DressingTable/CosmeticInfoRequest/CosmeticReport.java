package com.makejin.beautyproject_android.DressingTable.CosmeticInfoRequest;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_android.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_android.Model.Cosmetic;
import com.makejin.beautyproject_android.Model.GlobalResponse;

import com.makejin.beautyproject_android.ParentActivity;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Constants.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_cosmetic_report)
public class CosmeticReport extends ParentActivity {

    /*
    TODO:기획 변동사항 말하기, 궁금한 점 묻기(기획)
    원래 '카테고리 정보' 대신 '브랜드 이미지' 였으나,
    기본 정보 제공해주는 곳에 브랜드 이미지에 대한 정보는 없는데 체크박스에 있으면 이상할 것 같기도 하고, 브랜드 이미지는 틀릴일이 없을 것 같아 넣지 않았다
    카테고리 정보에 관한 내용은 있는데 체크 항목에는 없길래 안 넣음

    해당 페이지의 로직은
    체크박스는 무조건 하나 이상 체크 가능 하도록, 세부사항은 선택항목
     */
    CosmeticReport activity;

    Cosmetic cosmetic;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    TextView TV_main_category, TV_sub_category, TV_brand, TV_product_name;

    @ViewById
    ImageView IV_product;

    @ViewById
    Button BT_send;

    @ViewById
    RelativeLayout whole;

    @ViewById
    EditText ET_detail;

    List<String> problem = new ArrayList<String>();

    @CheckedChange({R.id.CB_cos_img, R.id.CB_cos_name, R.id.CB_category, R.id.CB_bra_name})
    void checkedChangedOnSomeCheckBoxs(CheckBox checkBox, boolean isChecked) {
        if(isChecked){
            //Snackbar.make(whole,"checkbox : "+button.getText(),Snackbar.LENGTH_SHORT).show();
            problem.add(checkBox.getText().toString());
        }else{
            problem.remove(checkBox.getText().toString());
        }
    }

    @Click
    void BT_back() {
        onBackPressed();
    }

    @Click
    void BT_send() {
        String detail = ET_detail.getText().toString();
        String str_problem = TextUtils.join(",", problem);

        Map report = new HashMap();
        report.put("cosmetic_id", cosmetic.id);
        report.put("problem", str_problem);
        report.put("detail", detail);
        connectTestCall_response(report);

    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        Toolbar cs_toolbar = (Toolbar)findViewById(R.id.cs_toolbar);
        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        cosmetic = (Cosmetic) intent.getSerializableExtra("cosmetic");

        TV_main_category.setText("("+cosmetic.main_category);
        TV_sub_category.setText(cosmetic.sub_category+")");
        TV_brand.setText(cosmetic.brand);
        TV_product_name.setText(cosmetic.product_name);
        String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;

        Glide.with(activity).
                load(image_url).
                thumbnail(0.1f).
                into(IV_product);
    }

    @UiThread
    void uiThread() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    void connectTestCall_response(Map fields) {
        //LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.cosmetic_report(fields)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        startActivity(new Intent(getApplicationContext(), DressingTableActivity_.class));
                        finish();
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response != null) {
                            if(response.code==201){
                                Toast.makeText(activity, "신고해주셔서 감사합니다.", Toast.LENGTH_SHORT).show();
                            }else if(response.code==409){
                                Toast.makeText(activity, "이미 요청하셨습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}