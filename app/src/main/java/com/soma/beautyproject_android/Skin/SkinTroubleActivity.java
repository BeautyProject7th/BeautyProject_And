package com.soma.beautyproject_android.Skin;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.beautyproject_android.Main.MainActivity_;
import com.soma.beautyproject_android.MyPage.MyPageActivity_;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.Trouble;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

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

import static com.soma.beautyproject_android.R.id.LL_skin_trouble_9;


@EActivity(R.layout.activity_skin_trouble)
public class SkinTroubleActivity extends ParentActivity {

    SkinTroubleActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    TextView TV_skin_trouble_1,TV_skin_trouble_2,TV_skin_trouble_3,TV_skin_trouble_4,TV_skin_trouble_5,TV_skin_trouble_6,TV_skin_trouble_7,TV_skin_trouble_8,TV_skin_trouble_9,toolbar_title;

    @ViewById
    ImageView IV_skin_trouble_1,IV_skin_trouble_2,IV_skin_trouble_3,IV_skin_trouble_4,IV_skin_trouble_5,IV_skin_trouble_6,IV_skin_trouble_7,IV_skin_trouble_8,IV_skin_trouble_9;
    @ViewById
    Button BT_complete, BT_back,BT_next;

    Integer skin_trouble = null;

    //LinearLayout id 저장하는 거임
    List<Integer> user_skin_trouble_list = new ArrayList<Integer>();

    Map<Integer,int[]> skin_trouble_image = new HashMap<Integer, int[]>();
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
            Intent intent = new Intent(activity, MyPageActivity_.class);
            startActivity(intent);
            finish();
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

    @Click({R.id.LL_skin_trouble_1,R.id.LL_skin_trouble_2,R.id.LL_skin_trouble_3,R.id.LL_skin_trouble_4,R.id.LL_skin_trouble_5, R.id.LL_skin_trouble_6,
            R.id.LL_skin_trouble_7,R.id.LL_skin_trouble_8, LL_skin_trouble_9})
    void onClick(View v){
        click_skin_trouble(v.getId());
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        indicator = (LinearLayout) findViewById(R.id.indicator);
        toolbar_title.setText("피부고민 입력");
        BT_next.setVisibility(View.GONE);

        Intent intent = getIntent();
        before_flag = intent.getBooleanExtra("before_login",false);

        init_skintrouble_list();
        init_trouble_image();
    }

    private void init_trouble_image() {
        skin_trouble_image.put(R.id.LL_skin_trouble_1, setImageList(R.drawable.trouble1_darkcircle,R.drawable.trouble1_select));
        skin_trouble_image.put(R.id.LL_skin_trouble_2, setImageList(R.drawable.trouble2_blackhead,R.drawable.trouble2_select));
        skin_trouble_image.put(R.id.LL_skin_trouble_3, setImageList(R.drawable.trouble3_pore,R.drawable.trouble3_select));
        skin_trouble_image.put(R.id.LL_skin_trouble_4, setImageList(R.drawable.trouble4_deadskin,R.drawable.trouble4_select));
        skin_trouble_image.put(R.id.LL_skin_trouble_5, setImageList(R.drawable.trouble5_sensitivity,R.drawable.trouble5_select));
        skin_trouble_image.put(R.id.LL_skin_trouble_6, setImageList(R.drawable.trouble6_wrinkle,R.drawable.trouble6_select));
        skin_trouble_image.put(R.id.LL_skin_trouble_7, setImageList(R.drawable.trouble7_acne,R.drawable.trouble7_select));
        skin_trouble_image.put(R.id.LL_skin_trouble_8, setImageList(R.drawable.trouble8_flush,R.drawable.trouble8_select));
        skin_trouble_image.put(LL_skin_trouble_9, setImageList(R.drawable.trouble9_nothing,R.drawable.trouble9_select));
    }

    private int[] setImageList(int one, int two){
        int[] skintrouble = new int[2];
        skintrouble[0] = one;
        skintrouble[1] = two;
        return skintrouble;
    }

    private void init_skintrouble_list() {
        skin_trouble_list.put(R.id.LL_skin_trouble_1, new Trouble(TV_skin_trouble_1,"다크서클",IV_skin_trouble_1));
        skin_trouble_list.put(R.id.LL_skin_trouble_2, new Trouble(TV_skin_trouble_2,"블랙헤드",IV_skin_trouble_2));
        skin_trouble_list.put(R.id.LL_skin_trouble_3, new Trouble(TV_skin_trouble_3,"모공",IV_skin_trouble_3));
        skin_trouble_list.put(R.id.LL_skin_trouble_4, new Trouble(TV_skin_trouble_4,"각질",IV_skin_trouble_4));
        skin_trouble_list.put(R.id.LL_skin_trouble_5, new Trouble(TV_skin_trouble_5,"민감성",IV_skin_trouble_5));
        skin_trouble_list.put(R.id.LL_skin_trouble_6, new Trouble(TV_skin_trouble_6,"주름",IV_skin_trouble_6));
        skin_trouble_list.put(R.id.LL_skin_trouble_7, new Trouble(TV_skin_trouble_7,"여드름",IV_skin_trouble_7));
        skin_trouble_list.put(R.id.LL_skin_trouble_8, new Trouble(TV_skin_trouble_8,"안면홍조",IV_skin_trouble_8));
        skin_trouble_list.put(LL_skin_trouble_9, new Trouble(TV_skin_trouble_9,"없음",IV_skin_trouble_9));
    }

    void click_skin_trouble(int view_id){
        skin_trouble = view_id;

        if(user_skin_trouble_list.contains(skin_trouble)) {//피부고민 취소
            user_skin_trouble_list.remove(skin_trouble);
            unselect_trouble(skin_trouble);
        }else if(skin_trouble == LL_skin_trouble_9){ //없음 선택시 다른 버튼 모두 취소됨(단일 선택이기 때문)
            for (Integer trouble:user_skin_trouble_list){
                unselect_trouble(trouble);
            }
            user_skin_trouble_list.removeAll(user_skin_trouble_list);

            select_trouble(skin_trouble);
        }else if(user_skin_trouble_list.size()>=3){//피부고민 3개 이상일때
            Snackbar.make(TV_skin_trouble_9,"피부고민 사항은 최대 3개까지 선택 가능합니다.",Snackbar.LENGTH_SHORT).show();
        }else{//아니면 고민항목에 담기
            //없음 선택된 경우 없음 체크 해제
            if(user_skin_trouble_list.contains(LL_skin_trouble_9)){
                unselect_trouble(LL_skin_trouble_9);
                user_skin_trouble_list.removeAll(user_skin_trouble_list);
            }
            select_trouble(skin_trouble);
        }
    }

    private void unselect_trouble(int view_id) {
        skin_trouble_list.get(view_id).TV_trouble.setTextColor(getResources().getColor(R.color.colorBlackText));
        skin_trouble_list.get(view_id).IV_trouble.setImageDrawable(getResources().getDrawable(skin_trouble_image.get(view_id)[0]));
    }

    private void select_trouble(int view_id) {
        skin_trouble_list.get(view_id).TV_trouble.setTextColor(getResources().getColor(R.color.colorAccent));
        user_skin_trouble_list.add(view_id);
        skin_trouble_list.get(view_id).IV_trouble.setImageDrawable(getResources().getDrawable(skin_trouble_image.get(view_id)[1]));
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
                        if(!before_flag){
                            Intent intent = new Intent(activity, MyPageActivity_.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), MainActivity_.class);
                            startActivity(intent);
                            setResult(Constants.ACTIVITY_CODE_MAIN_FRAGMENT_REFRESH_RESULT);
                        }
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

