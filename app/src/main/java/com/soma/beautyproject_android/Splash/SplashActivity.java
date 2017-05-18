package com.soma.beautyproject_android.Splash;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.flurry.android.FlurryAgent;
import com.soma.beautyproject_android.DressingTable.DressingTableActivity_;
import com.soma.beautyproject_android.Login.LoginActivity_;
import com.soma.beautyproject_android.Model.Category;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.PreferenceManager;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;
import com.facebook.internal.Utility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;
import io.userhabit.service.Userhabit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends ParentActivity {
    SplashActivity activity;

    @AfterViews
    void afterBindingView() {
        this.activity = this;


//        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getDefaultTracker();
//
//        Userhabit.start(this);
//
//
//        Userhabit.setSessionEndTime(10);
//
//        new FlurryAgent.Builder()
//                .withLogEnabled(true)
//                .build(this, "HCPCS75FNYY8H97NSW7Q");
//
//        Fabric.with(this, new Crashlytics());
//
//        logUser();




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
                connectCategoryCall(temp_id);
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

    void connectCategoryCall(final String id) {
        //서버 호출 횟수를 줄이기 위해 카테고리 불러오는 작업은 어플을 처음 실행한 경우에만 불러오도록 한다.
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.category()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Category>>() {
                    @Override
                    public final void onCompleted() {
                        connectTestCall_access(id);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Category> response) {
                        if (response != null) {
                            Map<String, List<String>> categorylist = new HashMap<String, List<String>>();
                            for(int i=0;i<response.size();i++){
                                categorylist.put(response.get(i).main_category, response.get(i).sub_category);
                            }
                            SharedManager.getInstance().setCategory(categorylist);
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

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
