package com.soma.beautyproject_android.Camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator_ml;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.flurry.sdk.me.m;

/**
 * Created by mijeong on 2017. 6. 18..
 */

@EActivity(R.layout.activity_camera_loading)
public class CameraLoadingActivity extends ParentActivity {
    CameraLoadingActivity activity;

    @ViewById
    ImageView IV_user;

    @ViewById
    Button BT_back;

    @ViewById
    TextView toolbar_title;

    @ViewById
    ImageView IV_line,IV_line_2;

    @ViewById
    ImageView IV_background;

    Animation anim_slide_down;
    Animation anim_slide_up;

    @ViewById
    ImageView IV_pentagon, IV_makeup_example;

    String filename;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        refresh();
    }

    void refresh(){
       //conn_get_my_info();
        conn_get_call_ml_server(filename);
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        toolbar_title.setText("화장법 찾기 결과");

        Glide.with(getApplicationContext()).load(R.drawable.ic_background).thumbnail(0.1f).into(IV_background);

        final Boolean flag = getIntent().getBooleanExtra("gallery_flag",false);
        filename = getIntent().getStringExtra("filename");


        Glide.with(getApplicationContext()).
                load(R.drawable.ic_pentagon).
                thumbnail(0.1f).
                into(IV_pentagon);

        Glide.with(getApplicationContext()).
                load(R.drawable.ic_woman).
                thumbnail(0.1f).
                into(IV_makeup_example);

        //V_line
        Glide.with(getApplicationContext()).
                load(R.drawable.line).
                thumbnail(0.1f).
                into(IV_line);

        anim_slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_down2);
        anim_slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_up);

        uithread_down();
        uithread_up();

//        new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
//                @Override
//                public void run() {
//                    Intent intent = new Intent(getApplicationContext(), CameraResultActivity_.class);
//                    intent.putExtra("gallery_flag", flag);
//                    startActivity(intent);
//                    finish();
//                }
//        }, 40000);
    }


    @UiThread
    void uithread_up(){
            IV_line_2.setVisibility(View.VISIBLE);
            IV_line_2.startAnimation(anim_slide_up);
            IV_line_2.setVisibility(View.INVISIBLE);
    }

    @UiThread
    void uithread_down(){
            IV_line.setVisibility(View.VISIBLE);
            IV_line.startAnimation(anim_slide_down);
            IV_line.setVisibility(View.INVISIBLE);
//            new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
//                @Override
//                public void run() {
//                    IV_line.setVisibility(View.INVISIBLE);
//                    uithread_up();
//                }
//            }, 3000);


    }
    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    */


    void conn_get_call_ml_server(String filename) {
        CSConnection conn = ServiceGenerator_ml.createService(activity,CSConnection.class);
        conn.get_call_ml_server(filename)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_get_call_ml_server error", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(final List<String> response) {
                        if (response != null) {

                            String isFace = response.get(0);
                            Log.i("isFace", "isFace : " + isFace);
                            if(isFace.equals("48")){ // 얼굴 아님 48 => '0'의 아스키코드 값
                                Intent intent = new Intent(getApplicationContext(), CameraNotFoundActivity_.class);
                                startActivity(intent);
                                finish();
//                                Toast.makeText(getApplicationContext(), "재촬영 해주세요.", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), CameraMainActivity.class);
//                                startActivity(intent);
//                                finish();
                            }else{
                                Intent intent = new Intent(getApplicationContext(), CameraResultActivity_.class);
                                startActivity(intent);
                                finish();
                            }
                            //Toast.makeText(activity, ""+response.get(0).toString(), Toast.LENGTH_SHORT).show();
                        } else{

                        }
                    }
                });
    }
}
