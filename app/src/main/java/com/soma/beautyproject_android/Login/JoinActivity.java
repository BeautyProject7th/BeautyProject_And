package com.soma.beautyproject_android.Login;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    ImageView IV_user,BT_nicknameCheck;

    @ViewById
    TextView TV_nicknameResult;

    @ViewById
    EditText ET_nickname;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        User me = SharedManager.getInstance().getMe();
        String image_url = me.profile_url;
        if(image_url!=null){
            Glide.with(activity).
                    load(image_url).
                    thumbnail(0.1f).
                    bitmapTransform(new CropCircleTransformation(activity)).into(IV_user);
        }
        initSexSpinner();
        initYearSpinner();
    }

    @Click
    void BT_nicknameCheck(){
        connectTestCall_my_follow(ET_nickname.getText().toString());
    }

    private void initYearSpinner() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = thisYear; i>1920; i--){
            years.add(Integer.toString(i));
        }
        setSpinnerAdapter(years,SP_birth);
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

    void connectTestCall_my_follow(final String nickname) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.checkNickName(nickname)
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
                                TV_nicknameResult.setText("사용 가능한 닉네임입니다 :)");
                            }else if(response.code == 409){
                                nickname_check = false;
                                TV_nicknameResult.setText("이미 존재하는 닉네임입니다 ㅠㅠ");
                            }
                        } else{
                            Toast.makeText(activity, "결과 갑 없음", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
