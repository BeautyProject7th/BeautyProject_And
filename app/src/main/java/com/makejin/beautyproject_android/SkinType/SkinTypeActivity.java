package com.makejin.beautyproject_android.SkinType;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makejin.beautyproject_android.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_android.Model.GlobalResponse;
import com.makejin.beautyproject_android.Model.User;
import com.makejin.beautyproject_android.ParentActivity;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Constants.Constants;
import com.makejin.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

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

/*
todo: 우선 선택되면 텍스트 색 변경되도록 해놓았음. 디자인 변동사항 있으면 맞춰 재변경할 것
 */

@EActivity(R.layout.activity_skin_type)
public class SkinTypeActivity extends ParentActivity {
    SkinTypeActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    TextView TV_skin_type_1,TV_skin_type_2,TV_skin_type_3,TV_skin_type_4;

    @ViewById
    TextView TV_skin_type_1_explain,TV_skin_type_2_explain,TV_skin_type_3_explain,TV_skin_type_4_explain;

    @ViewById
    Button BT_complete;

    String skin_type = null;

    User user = SharedManager.getInstance().getMe();

    Map<Integer,String> skin_type_list = new HashMap<Integer, String>();

    LinearLayout indicator;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        indicator = (LinearLayout) findViewById(R.id.indicator);

        /*
        skin_type_list.put(R.id.TV_skin_type_1, "skin_type1");
        skin_type_list.put(R.id.TV_skin_type_2, "중성");
        skin_type_list.put(R.id.TV_skin_type_3, "지성(일반)");
        skin_type_list.put(R.id.TV_skin_type_4, "지성(수부지)");
        */
    }


    @Click({R.id.LL_skin_type_1,R.id.LL_skin_type_2,R.id.LL_skin_type_3,R.id.LL_skin_type_4})
    void onClick(View v){
        setAllGray();
        switch (v.getId()){
            case R.id.LL_skin_type_1 :
                TV_skin_type_1.setTextColor(getResources().getColor(R.color.colorAccent));
                TV_skin_type_1_explain.setTextColor(getResources().getColor(R.color.colorAccent));
                skin_type = "건성";
                break;
            case R.id.LL_skin_type_2 :
                TV_skin_type_2.setTextColor(getResources().getColor(R.color.colorAccent));
                TV_skin_type_2_explain.setTextColor(getResources().getColor(R.color.colorAccent));
                skin_type = "중성";
                break;
            case R.id.LL_skin_type_3 :
                TV_skin_type_3.setTextColor(getResources().getColor(R.color.colorAccent));
                TV_skin_type_3_explain.setTextColor(getResources().getColor(R.color.colorAccent));
                skin_type = "지성(일반)";
                break;
            case R.id.LL_skin_type_4 :
                TV_skin_type_4.setTextColor(getResources().getColor(R.color.colorAccent));
                TV_skin_type_4_explain.setTextColor(getResources().getColor(R.color.colorAccent));
                skin_type = "지성(수부지)";
                break;
        }
    }

    void setAllGray(){
        TV_skin_type_1.setTextColor(getResources().getColor(R.color.colorBlackText));
        TV_skin_type_2.setTextColor(getResources().getColor(R.color.colorBlackText));
        TV_skin_type_3.setTextColor(getResources().getColor(R.color.colorBlackText));
        TV_skin_type_4.setTextColor(getResources().getColor(R.color.colorBlackText));

        TV_skin_type_1_explain.setTextColor(getResources().getColor(R.color.colorBlackText));
        TV_skin_type_2_explain.setTextColor(getResources().getColor(R.color.colorBlackText));
        TV_skin_type_3_explain.setTextColor(getResources().getColor(R.color.colorBlackText));
        TV_skin_type_4_explain.setTextColor(getResources().getColor(R.color.colorBlackText));
    }

    /*
    void click_LL_skin_type(int view_id){
        findViewById(R.id.TV_skin_type_1).setBackgroundColor(Color.GRAY);
        findViewById(R.id.TV_skin_type_2).setBackgroundColor(Color.GRAY);
        findViewById(R.id.TV_skin_type_3).setBackgroundColor(Color.GRAY);
        findViewById(R.id.TV_skin_type_4).setBackgroundColor(Color.GRAY);
        if(skin_type_list.get(view_id) == skin_type) {
            skin_type = null;
            findViewById(view_id).setBackgroundColor(Color.GRAY);
            return;
        }

        skin_type = skin_type_list.get(view_id);
        findViewById(view_id).setBackgroundColor(Color.GREEN);

    }
    */

    @Click
    void BT_complete(){
        if(skin_type == null){
            Toast.makeText(getApplicationContext(), "피부 타입을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map user = new HashMap();
        user.put("user_id", SharedManager.getInstance().getMe().id);
        user.put("skin_type", skin_type);
        connectTestCall_update_skin_type(user);
    }


    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall_update_skin_type(Map user) {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_updateSkinType(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
                        Intent intent = new Intent(getApplicationContext(), DressingTableActivity_.class);
                        startActivity(intent);
                        setResult(Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_RESULT);
                        finish();
                    }
                    @Override
                    public final void onError(Throwable e) {
                        LoadingUtil.stopLoading(indicator);
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "이미 등록한 화장품입니다.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response != null) {
                            Toast.makeText(getApplicationContext(), "정상적으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
