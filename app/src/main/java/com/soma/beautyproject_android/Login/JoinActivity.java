package com.soma.beautyproject_android.Login;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.DressingTable.DressingTableActivity_;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Skin.SkinTroubleActivity_;
import com.soma.beautyproject_android.Skin.SkinTypeActivity_;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_join)
public class JoinActivity extends ParentActivity {
    JoinActivity activity;
    Boolean nickname_check = false;

    @ViewById
    Spinner SP_sex,SP_birth;

    @ViewById
    ImageView BT_nicknameCheck;

    @ViewById
    RelativeLayout RL_nicknameCheck;

    @ViewById
    TextView TV_nicknameResult;

    @ViewById
    EditText ET_nickname;

    @ViewById
    Button BT_next;

    String nickname = null;

    @ViewById
    TextView toolbar_title;

    User me;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        me = SharedManager.getInstance().getMe();
        //String image_url = me.profile_url;
//        if(image_url!=null){
//            Glide.with(activity).
//                    load(image_url).
//                    thumbnail(0.1f).
//                    bitmapTransform(new CropCircleTransformation(activity)).into(IV_user);
//        }
        initSexSpinner();
        initYearSpinner();
        toolbar_title.setText("정보 입력");

    }


    @Click
    void BT_next(){
        if(!nickname_check) {
            Toast.makeText(activity, "닉네임 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(nickname == null) {
            Toast.makeText(activity, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        conn_join(nickname,(String)SP_sex.getSelectedItem(),(String)SP_birth.getSelectedItem());
    }

    @Click
    void RL_nicknameCheck(){
        connectTestCall_check_nickname(ET_nickname.getText().toString());
    }

    private void initYearSpinner() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = thisYear; i>1920; i--){
            years.add(Integer.toString(i));
        }
        setSpinnerAdapter(years,SP_birth);
        SP_birth.setSelection(25);
    }

    private void initSexSpinner() {
        List<String> sexs = new ArrayList<>();
        sexs.add("여자");
        sexs.add("남자");
        setSpinnerAdapter(sexs,SP_sex);
    }

    private void setSpinnerAdapter(List<String> sexs, Spinner spinner) {
        UserInfoSpinnerAdapter adapter = new UserInfoSpinnerAdapter(this, sexs);
        spinner.setAdapter(adapter);
    }

    void connectTestCall_check_nickname(final String temp_nickname) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.checkNickName(temp_nickname)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "서버 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response != null) {
                            if(response.code == 200){
                                nickname_check = true;
                                nickname = temp_nickname;
                                TV_nicknameResult.setText("사용 가능한 닉네임입니다 :)");
                            }else if(response.code == 409){
                                nickname_check = false;
                                TV_nicknameResult.setText("이미 존재하는 닉네임입니다 ㅠㅠ");
                            }
                        } else{
                            Toast.makeText(activity, "결과 값 없음", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    void conn_join(String temp_nickname, String sex, String birthyear) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        //SharedManager.getInstance().getMe().id, name, sex, birthyear
        Map fields = new HashMap();
        Log.i("user_id", me.id);
        Log.i("nickname", temp_nickname);
        Log.i("sex,", sex);
        Log.i("birthyear", birthyear);

        fields.put("user_id", me.id);
        fields.put("nickname", temp_nickname);
        fields.put("sex", sex);
        fields.put("birthyear", birthyear);

        conn.join(fields)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public final void onCompleted() {
                        Intent intent;
                        intent = new Intent(getApplicationContext(), SkinTypeActivity_.class);
                        intent.putExtra("before_login",true);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "서버 에러", Toast.LENGTH_SHORT).show();
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
}
