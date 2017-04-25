package com.makejin.beautyproject_android.DressingTable.CosmeticInfoRequest;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.makejin.beautyproject_android.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_android.Model.GlobalResponse;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Constants.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_registration_request)
public class RegistrationRequestActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    RegistrationRequestActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    EditText ET_brand,ET_cosmetic;

    @ViewById
    Button BT_request;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        Toolbar cs_toolbar = (Toolbar)findViewById(R.id.cs_toolbar);
        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");
    }

    @Click
    void BT_request() {
        String brand = ET_brand.getText().toString();
        String cosmetic = ET_cosmetic.getText().toString();

        if(brand.length() == 0){
            Snackbar.make(BT_request,"브랜드 명을 입력해주세요.",Snackbar.LENGTH_SHORT);
        }else if(cosmetic.length() == 0){
            Snackbar.make(BT_request,"화장품 명을 입력해주세요.",Snackbar.LENGTH_SHORT);
        }else{
            Map request = new HashMap();
            request.put("brand", brand);
            request.put("cosmetic", cosmetic);
            connectTestCall_request(request);
        }
    }

    @Click
    void BT_back(){
        onBackPressed();
    }

    @UiThread
    void uiThread() {

    }

    public void showGuide() {
        toast = Toast.makeText(getApplicationContext(), getString(R.string.exit_app), Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    void connectTestCall_request(Map fields) {
        //LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.cosmetic_request(fields)
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
                                Toast.makeText(activity, "요청해주셔서 감사합니다.", Toast.LENGTH_SHORT).show();
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