package com.makejin.beautyproject_and.Splash;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_and.DressingTable.DressingTableActivity;
import com.makejin.beautyproject_and.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_and.Login.LoginActivity_;
import com.makejin.beautyproject_and.Model.Cosmetic;
import com.makejin.beautyproject_and.Model.User;
import com.makejin.beautyproject_and.R;
import com.makejin.beautyproject_and.Utils.Connections.CSConnection;
import com.makejin.beautyproject_and.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_and.Utils.Constants.Constants;
import com.makejin.beautyproject_and.Utils.Loadings.LoadingUtil;
import com.makejin.beautyproject_and.Utils.SharedManager.PreferenceManager;
import com.makejin.beautyproject_and.Utils.SharedManager.SharedManager;
import com.facebook.internal.Utility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import io.userhabit.service.Userhabit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {
    SplashActivity activity;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        Userhabit.start(this);
        Userhabit.setSessionEndTime(10);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1500);

        Log.i("ZXc", "Utility.getMetadataApplicationId(getApplicationContext() : "+Utility.getMetadataApplicationId(getApplicationContext()));


        Timer timer = new Timer();
        timer.schedule(this.spashScreenFinished, 1500);


    }

    private final TimerTask spashScreenFinished = new TimerTask() {
        @Override
        public void run() {
            String temp_id = PreferenceManager.getInstance(getApplicationContext()).get_id();
            Log.i("zxc", "temp_id : " + temp_id);
            if(temp_id.equals("")){
                Intent login = new Intent(activity, LoginActivity_.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
            }else{
                connectTestCall_access(temp_id);
            }
            finish();

        }
    };

    void refresh() {

    }

    @UiThread
    void uiThread() {

    }
/*
    void connectTestCall_access(Map fields) {
        //LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.user_access(fields)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public final void onCompleted() {
                        startActivity(new Intent(getApplicationContext(), DressingTableActivity_.class));
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("zxc", "zzz : ");
                        Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(User response) {
                        if (response != null) {
                            SharedManager.getInstance().setMe(response);
                        } else {
                            Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
*/
    void connectTestCall_access(String id) {
        //LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.oneUser_get(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public final void onCompleted() {
                        startActivity(new Intent(getApplicationContext(), DressingTableActivity_.class));
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("zxc", "zzz : ");
                        Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(User response) {
                        if (response != null) {
                            SharedManager.getInstance().setMe(response);
                        } else {
                            Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
