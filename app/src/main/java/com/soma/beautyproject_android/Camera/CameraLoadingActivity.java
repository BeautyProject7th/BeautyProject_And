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

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Constants.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

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

    boolean gallery_flag;

    Animation anim_slide_down;
    Animation anim_slide_up;


    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        refresh();
    }

    void refresh(){
       //conn_get_my_info();
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        toolbar_title.setText("메이크업 진단");

        final Boolean flag = getIntent().getBooleanExtra("gallery_flag",false);

        //V_line
        Glide.with(getApplicationContext()).
                load(R.drawable.line).
                thumbnail(0.1f).
                into(IV_line);

        anim_slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_down2);
        anim_slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_up);

        uithread_down();
        uithread_up();

        new Handler().postDelayed(new Runnable() {// 1 초 후에 실행
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), CameraResultActivity_.class);
                    intent.putExtra("gallery_flag", flag);
                    startActivity(intent);

                }
            }, 5000);
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
}
