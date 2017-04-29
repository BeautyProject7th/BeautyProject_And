package com.makejin.beautyproject_android.SkinTrouble;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makejin.beautyproject_android.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_android.Model.GlobalResponse;
import com.makejin.beautyproject_android.Model.User;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@EActivity(R.layout.activity_skin_trouble)
public class SkinTroubleActivity extends AppCompatActivity {

    SkinTroubleActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    //@ViewById
    //TextView TV_skin_trouble_1,TV_skin_trouble_2,TV_skin_trouble_3,TV_skin_trouble_4,TV_skin_trouble_5,TV_skin_trouble_6,TV_skin_trouble_7,TV_skin_trouble_8;

    TextView [] TV_skin_trouble = new TextView[8];

    @ViewById
    Button BT_complete;

    String skin_trouble = null;

    User user = SharedManager.getInstance().getMe();

    List<String> user_skin_trouble_list = new ArrayList<String>();

    Map<Integer,String> skin_trouble_list = new HashMap<Integer, String>();

    LinearLayout indicator;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
//        if(user.skin_trouble_1)
//        user_skin_trouble_list.add(user.skin_trouble_1);
//        user_skin_trouble_list.add(user.skin_trouble_2);
//        user_skin_trouble_list.add(user.skin_trouble_3);

        indicator = (LinearLayout) findViewById(R.id.indicator);

        skin_trouble_list.put(R.id.TV_skin_trouble_1, "다크서클");
        skin_trouble_list.put(R.id.TV_skin_trouble_2, "블랙헤드");
        skin_trouble_list.put(R.id.TV_skin_trouble_3, "모공확장");
        skin_trouble_list.put(R.id.TV_skin_trouble_4, "각질");
        skin_trouble_list.put(R.id.TV_skin_trouble_5, "주름");
        skin_trouble_list.put(R.id.TV_skin_trouble_6, "민감성");
        skin_trouble_list.put(R.id.TV_skin_trouble_7, "여드름/트러블");
        skin_trouble_list.put(R.id.TV_skin_trouble_8, "안면홍조");



        TV_skin_trouble[0] = (TextView) findViewById(R.id.TV_skin_trouble_1);
        TV_skin_trouble[1] = (TextView) findViewById(R.id.TV_skin_trouble_2);
        TV_skin_trouble[2] = (TextView) findViewById(R.id.TV_skin_trouble_3);
        TV_skin_trouble[3] = (TextView) findViewById(R.id.TV_skin_trouble_4);
        TV_skin_trouble[4] = (TextView) findViewById(R.id.TV_skin_trouble_5);
        TV_skin_trouble[5] = (TextView) findViewById(R.id.TV_skin_trouble_6);
        TV_skin_trouble[6] = (TextView) findViewById(R.id.TV_skin_trouble_7);
        TV_skin_trouble[7] = (TextView) findViewById(R.id.TV_skin_trouble_8);

//        //기존에 이미 유저가 선택했었던 피부고민 사항들 체크
//        for(int i=0;i<8;i++){
//            for(String s : user_skin_trouble_list){
//                if(skin_trouble == s){
//                    TV_skin_trouble[i].setBackgroundColor(Color.GREEN);
//                    break;
//                }
//            }
//        }

    }

    @Click({R.id.TV_skin_trouble_1,R.id.TV_skin_trouble_2,R.id.TV_skin_trouble_3,R.id.TV_skin_trouble_4,R.id.TV_skin_trouble_5, R.id.TV_skin_trouble_6,
            R.id.TV_skin_trouble_7,R.id.TV_skin_trouble_8})
    void onClick(View v){
        click_TV_skin_trouble(v.getId());
    }


    void click_TV_skin_trouble(int view_id){
        skin_trouble = skin_trouble_list.get(view_id);
        int i=0;
        for(;i<user_skin_trouble_list.size();i++){
            if(skin_trouble_list.get(view_id) == user_skin_trouble_list.get(i)) {
                user_skin_trouble_list.remove(skin_trouble);
                findViewById(view_id).setBackgroundColor(Color.GRAY);
                return;
            }
        }

        if(user_skin_trouble_list.size()>2){
            Toast.makeText(getApplicationContext(), "피부고민 사항은 최대 3개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(i==user_skin_trouble_list.size()) {
            user_skin_trouble_list.add(skin_trouble);
            findViewById(view_id).setBackgroundColor(Color.GREEN);
        }

    }

    @Click
    void BT_complete(){
        Map user = new HashMap();
        user.put("user_id", SharedManager.getInstance().getMe().id);
        user.put("skin_trouble_1", user_skin_trouble_list.get(0));
        user.put("skin_trouble_2", user_skin_trouble_list.get(1));
        user.put("skin_trouble_3", user_skin_trouble_list.get(2));

        Log.i("sad", user.toString());

        connectTestCall_update_skin_trouble(user);
    }

    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall_update_skin_trouble(Map user) {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_updateSkinTrouble(user)
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
