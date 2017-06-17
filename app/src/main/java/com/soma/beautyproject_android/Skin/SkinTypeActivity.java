package com.soma.beautyproject_android.Skin;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.beautyproject_android.DressingTable.DressingTableActivity_;
import com.soma.beautyproject_android.Main.MainActivity_;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.Type;
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
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Body;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.soma.beautyproject_android.R.id.LL_skin_trouble_9;
import static com.soma.beautyproject_android.R.id.TV_skin_trouble_9;
import static com.soma.beautyproject_android.R.id.indicator;

/*
todo: 우선 선택되면 텍스트 색 변경되도록 해놓았음. 디자인 변동사항 있으면 맞춰 재변경할 것
 */

@EActivity(R.layout.activity_skin_type)
public class SkinTypeActivity extends ParentActivity {
    SkinTypeActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    TextView TV_skin_type_1,TV_skin_type_2,TV_skin_type_3,TV_skin_type_4,toolbar_title;

    @ViewById
    TextView TV_skin_type_1_explain,TV_skin_type_2_explain,TV_skin_type_3_explain,TV_skin_type_4_explain;

    @ViewById
    ImageView IV_skin_type_1,IV_skin_type_2,IV_skin_type_3,IV_skin_type_4;

    @ViewById
    Button BT_next,BT_complete,BT_back;

    @ViewById
    ImageView BT_back_img;

    //LinearLayout id 저장
    Integer skin_type = null;
    Map<Integer,Type> skin_type_list = new HashMap<Integer, Type>();
    Map<Integer,int[]> skin_type_image = new HashMap<Integer, int[]>();

    Boolean before_flag = false;

    LinearLayout indicator;

    @Click
    void BT_back(){
        activity.finish();
    }

    @Click({R.id.BT_complete,R.id.BT_next})
    void BT_complete(){
        if(skin_type == null){
            Toast.makeText(getApplicationContext(), "피부 타입을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Map user = new HashMap();
        user.put("user_id", SharedManager.getInstance().getMe().id);
        user.put("skin_type", skin_type_list.get(skin_type).type);
        connectTestCall_update_skin_type(user);
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        toolbar_title.setText("피부타입 입력");

        Intent intent = getIntent();
        before_flag = intent.getBooleanExtra("before_login",false);
        if(before_flag){
            BT_back.setVisibility(View.GONE);
            BT_back_img.setVisibility(View.GONE);
            BT_complete.setVisibility(View.GONE);
        }else{
            BT_next.setVisibility(View.GONE);
        }

        indicator = (LinearLayout) findViewById(R.id.indicator);

        init_type_image();
        init_skin_type_list();
    }

    private void init_type_image() {
        skin_type_image.put(R.id.LL_skin_type_1, setImageList(R.drawable.skin_type1,R.drawable.skin_type1_select));
        skin_type_image.put(R.id.LL_skin_type_2, setImageList(R.drawable.skin_type2,R.drawable.skin_type2_select));
        skin_type_image.put(R.id.LL_skin_type_3, setImageList(R.drawable.skin_type3,R.drawable.skin_type3_select));
        skin_type_image.put(R.id.LL_skin_type_4, setImageList(R.drawable.skin_type4,R.drawable.skin_type4_select));
    }

    private void init_skin_type_list() {
        skin_type_list.put(R.id.LL_skin_type_1, new Type(TV_skin_type_1,TV_skin_type_1_explain,"건성",IV_skin_type_1));
        skin_type_list.put(R.id.LL_skin_type_2, new Type(TV_skin_type_2,TV_skin_type_2_explain,"중성",IV_skin_type_2));
        skin_type_list.put(R.id.LL_skin_type_3, new Type(TV_skin_type_3,TV_skin_type_3_explain,"지성",IV_skin_type_3));
        skin_type_list.put(R.id.LL_skin_type_4, new Type(TV_skin_type_4,TV_skin_type_4_explain,"수부지",IV_skin_type_4));
    }

    private int[] setImageList(int one, int two){
        int[] skintrouble = new int[2];
        skintrouble[0] = one;
        skintrouble[1] = two;
        return skintrouble;
    }

    @Click({R.id.LL_skin_type_1,R.id.LL_skin_type_2,R.id.LL_skin_type_3,R.id.LL_skin_type_4})
    void onClick(View v){
        click_skin_type(v.getId());
    }

    void click_skin_type(int view_id){
        if(skin_type!=null){ // 기존 설정 값이 있으면 해제 시켜 줌
            unselect_type(skin_type);
        }
        skin_type = view_id;
        select_type(skin_type);
    }

    private void select_type(int skin_type) {
        skin_type_list.get(skin_type).TV_explain.setTextColor(getResources().getColor(R.color.colorAccent));
        skin_type_list.get(skin_type).TV_type.setTextColor(getResources().getColor(R.color.colorAccent));
        skin_type_list.get(skin_type).IV_type.setImageDrawable(getResources().getDrawable(skin_type_image.get(skin_type)[1]));
    }

    private void unselect_type(int skin_type) {
        skin_type_list.get(skin_type).TV_explain.setTextColor(getResources().getColor(R.color.colorBlackText));
        skin_type_list.get(skin_type).TV_type.setTextColor(getResources().getColor(R.color.colorBlackText));
        skin_type_list.get(skin_type).IV_type.setImageDrawable(getResources().getDrawable(skin_type_image.get(skin_type)[0]));
    }

    void connectTestCall_update_skin_type(final Map user) {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_updateSkinType(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        Intent intent;
                        if(before_flag){
                            intent = new Intent(getApplicationContext(), SkinTroubleActivity_.class);
                            intent.putExtra("before_login",true);
                        }else{
                            intent = new Intent(getApplicationContext(), MainActivity_.class);
                            setResult(Constants.ACTIVITY_CODE_MAIN_FRAGMENT_REFRESH_RESULT);
                        }
                        LoadingUtil.stopLoading(indicator);
                        startActivity(intent);
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
                            String skintype = user.get("skin_type").toString();
                            SharedManager.getInstance().updateMeSkinType(skintype);
                            Toast.makeText(getApplicationContext(), "정상적으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

