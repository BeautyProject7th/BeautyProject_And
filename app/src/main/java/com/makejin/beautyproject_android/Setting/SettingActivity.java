package com.makejin.beautyproject_android.Setting;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.makejin.beautyproject_android.Login.LoginActivity_;
import com.makejin.beautyproject_android.Model.GlobalResponse;
import com.makejin.beautyproject_android.ParentActivity;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Skin.SkinTroubleActivity_;
import com.makejin.beautyproject_android.Skin.SkinTypeActivity_;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.CookieSharedPreferences;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Constants.Constants;
import com.makejin.beautyproject_android.Utils.SharedManager.PreferenceManager;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_setting)
public class SettingActivity extends ParentActivity {

    SettingActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    TextView toolbar_title;

    @ViewById
    RelativeLayout BT_skin_type, BT_skin_trouble, BT_agreement, BT_privacy_rule, BT_logout;

    @ViewById
    Switch push_switch;

    @CheckedChange(R.id.push_switch)
    void push_switch(CompoundButton push_switch, boolean isChecked) {
        if(isChecked){
            PreferenceManager.getInstance(activity).setPush(true);
        }else{
            PreferenceManager.getInstance(activity).setPush(false);
        }
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        toolbar_title.setText("설정");

        if(PreferenceManager.getInstance(activity).getPush()){
            push_switch.setChecked(true);
        }else push_switch.setChecked(false);

    }

    @Click
    void BT_back(){
        onBackPressed();
    }

    @Click
    void BT_skin_type(){
        startActivity(new Intent(getApplicationContext(), SkinTypeActivity_.class));
    }

    @Click
    void BT_skin_trouble(){
        startActivity(new Intent(getApplicationContext(), SkinTroubleActivity_.class));
    }

    @Click
    void BT_agreement(){
        //startActivity(new Intent(getApplicationContext(), SkinTypeActivity_.class));
    }

    @Click
    void BT_privacy_rule(){
        //startActivity(new Intent(getApplicationContext(), SkinTypeActivity_.class));
    }

    @Click
    void BT_logout(){
        connectTestCall_logout();
        CookieSharedPreferences cookieSharedPreferences;
        cookieSharedPreferences = CookieSharedPreferences.getInstanceOf(activity);
        cookieSharedPreferences.deleteHashSet(CookieSharedPreferences.COOKIE_SHARED_PREFERENCES_KEY);
    }



    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall() {

    }

    void connectTestCall_logout() {
        //LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.user_logout()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        Log.i("zxc", SharedManager.getInstance().getMe().social_type);

                        if(SharedManager.getInstance().getMe().social_type.equals("페이스북")) {
                            LoginManager.getInstance().logOut();
                            startActivity(new Intent(activity, LoginActivity_.class));
                            activity.finish();
                        }
                        else if(SharedManager.getInstance().getMe().social_type.equals("카카오톡")){
                            UserManagement.requestLogout(new LogoutResponseCallback() {
                                @Override
                                public void onCompleteLogout() {
                                    startActivity(new Intent(activity, LoginActivity_.class));
                                    activity.finish();
                                }
                            });
                        }

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("zxc", "zzz : ");
                        Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response != null) {
                            Toast.makeText(activity, "Logout Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


