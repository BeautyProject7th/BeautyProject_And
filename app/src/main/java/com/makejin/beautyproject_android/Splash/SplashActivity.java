package com.makejin.beautyproject_android.Splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.makejin.beautyproject_android.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_android.Login.LoginActivity_;
import com.makejin.beautyproject_android.Model.User;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Constants.Constants;
import com.makejin.beautyproject_android.Utils.SharedManager.PreferenceManager;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;
import com.facebook.internal.Utility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;
import io.userhabit.service.Userhabit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {
    SplashActivity activity;

    private Tracker mTracker;

    @AfterViews
    void afterBindingView() {
        this.activity = this;


//        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getDefaultTracker();

        Userhabit.start(this);


        Userhabit.setSessionEndTime(10);

        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "HCPCS75FNYY8H97NSW7Q");

        Fabric.with(this, new Crashlytics());

        // TODO: Move this to where you establish a user session
        logUser();




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1500);

        Log.i("ZXc", "Utility.getMetadataApplicationId(getApplicationContext() : "+Utility.getMetadataApplicationId(getApplicationContext()));


        Timer timer = new Timer();
        timer.schedule(this.spashScreenFinished, 1500);


    }


    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");
    }


    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
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

    @Override
    protected void onResume() {
        super.onResume();
//
//        String name = "google analytics";
//        Log.i(name, "Setting screen name: " + name);
//        mTracker.setScreenName("Image~" + name);
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
//
//        mTracker.send(new HitBuilders.EventBuilder()
//                .setCategory("Action")
//                .setAction("Share")
//                .build());
    }
}
