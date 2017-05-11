package com.makejin.beautyproject_android.Skin;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makejin.beautyproject_android.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_android.Model.GlobalResponse;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.makejin.beautyproject_android.R.id.BT_next;


@EActivity(R.layout.activity_skin_trouble)
public class SkinTroubleActivity extends ParentActivity {

    SkinTroubleActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    TextView TV_skin_trouble_1,TV_skin_trouble_2,TV_skin_trouble_3,TV_skin_trouble_4,TV_skin_trouble_5,TV_skin_trouble_6,TV_skin_trouble_7,TV_skin_trouble_8,TV_skin_trouble_9,toolbar_title;

    @ViewById
    Button BT_complete, BT_back,BT_next;

    Integer skin_trouble = null;

    //LinearLayout id 저장하는 거임
    List<Integer> user_skin_trouble_list = new ArrayList<Integer>();

    Map<Integer,Trouble> skin_trouble_list = new HashMap<Integer, Trouble>();

    LinearLayout indicator;
    Boolean before_flag = false;

    @Click
    void BT_back(){
        if(before_flag){
            Intent intent = new Intent(getApplicationContext(), SkinTypeActivity_.class);
            intent.putExtra("before_login",true);
            startActivity(intent);
            setResult(Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_RESULT);
            finish();
        }else{
            activity.finish();
        }
    }

    @Click
    void BT_complete(){
        String result[] = new String[3];
        for(int i=0;i<user_skin_trouble_list.size();i++) {
            result[i] = skin_trouble_list.get(user_skin_trouble_list.get(i)).trouble;
        }
        Map user = new HashMap();
        user.put("user_id", SharedManager.getInstance().getMe().id);
        user.put("skin_trouble_1", result[0]);
        user.put("skin_trouble_2", result[1]);
        user.put("skin_trouble_3", result[2]);

        Log.i("sad", user.toString());

        connectTestCall_update_skin_trouble(user, result);
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        indicator = (LinearLayout) findViewById(R.id.indicator);
        toolbar_title.setText("피부고민 입력");
        BT_next.setVisibility(View.GONE);

        Intent intent = getIntent();
        before_flag = intent.getBooleanExtra("before_login",false);

        skin_trouble_list.put(R.id.LL_skin_trouble_1, new Trouble(TV_skin_trouble_1,"다크서클"));
        skin_trouble_list.put(R.id.LL_skin_trouble_2, new Trouble(TV_skin_trouble_2,"블랙헤드"));
        skin_trouble_list.put(R.id.LL_skin_trouble_3, new Trouble(TV_skin_trouble_3,"모공"));
        skin_trouble_list.put(R.id.LL_skin_trouble_4, new Trouble(TV_skin_trouble_4,"각질"));
        skin_trouble_list.put(R.id.LL_skin_trouble_5, new Trouble(TV_skin_trouble_5,"민감성"));
        skin_trouble_list.put(R.id.LL_skin_trouble_6, new Trouble(TV_skin_trouble_6,"주름"));
        skin_trouble_list.put(R.id.LL_skin_trouble_7, new Trouble(TV_skin_trouble_7,"여드름/트러블"));
        skin_trouble_list.put(R.id.LL_skin_trouble_8, new Trouble(TV_skin_trouble_8,"안면홍조"));
        skin_trouble_list.put(R.id.LL_skin_trouble_9, new Trouble(TV_skin_trouble_9,"없음"));
    }

    @Click({R.id.LL_skin_trouble_1,R.id.LL_skin_trouble_2,R.id.LL_skin_trouble_3,R.id.LL_skin_trouble_4,R.id.LL_skin_trouble_5, R.id.LL_skin_trouble_6,
            R.id.LL_skin_trouble_7,R.id.LL_skin_trouble_8,R.id.LL_skin_trouble_9})
    void onClick(View v){
        click_skin_trouble(v.getId());
    }


    void click_skin_trouble(int view_id){
        skin_trouble = view_id;

        if(user_skin_trouble_list.contains(skin_trouble)) {//피부고민 취소
            user_skin_trouble_list.remove(skin_trouble);
            skin_trouble_list.get(view_id).TV_trouble.setTextColor(getResources().getColor(R.color.colorBlackText));
        }else if(skin_trouble == R.id.LL_skin_trouble_9){ //없음 선택시 다른 버튼 모두 취소됨(단일 선택이기 때문)
            for (Integer trouble:user_skin_trouble_list){
                skin_trouble_list.get(trouble).TV_trouble.setTextColor(getResources().getColor(R.color.colorBlackText));
            }
            user_skin_trouble_list.removeAll(user_skin_trouble_list);

            skin_trouble_list.get(view_id).TV_trouble.setTextColor(getResources().getColor(R.color.colorAccent));
            user_skin_trouble_list.add(view_id);
        }else if(user_skin_trouble_list.size()>=3){//피부고민 3개 이상일때
            Snackbar.make(TV_skin_trouble_9,"피부고민 사항은 최대 3개까지 선택 가능합니다.",Snackbar.LENGTH_SHORT).show();
        }else{//아니면 고민항목에 담기
            //없음 선택된 경우 없음 체크 해제
            if(user_skin_trouble_list.contains(R.id.LL_skin_trouble_9)){
                TV_skin_trouble_9.setTextColor(getResources().getColor(R.color.colorBlackText));
                user_skin_trouble_list.removeAll(user_skin_trouble_list);
            }
            user_skin_trouble_list.add(skin_trouble);
            skin_trouble_list.get(view_id).TV_trouble.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall_update_skin_trouble(final Map user, final String[] results) {
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
                        Toast.makeText(getApplicationContext(), "서버 문제.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response != null) {
                            SharedManager.getInstance().updateMeSkinTrouble(results[0],results[1],results[2]);
                            Toast.makeText(getApplicationContext(), "정상적으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

class Trouble{
    public TextView TV_trouble;
    public String trouble;

    Trouble(TextView textView, String trouble){
        this.TV_trouble = textView;
        this.trouble = trouble;
    }
}
