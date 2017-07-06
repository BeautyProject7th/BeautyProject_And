package com.soma.beautyproject_android.Camera;

import android.content.Intent;
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

/**
 * Created by mijeong on 2017. 6. 18..
 */

@EActivity(R.layout.activity_camera_not_found)
public class CameraNotFoundActivity extends ParentActivity {
    CameraNotFoundActivity activity;

    @ViewById
    ImageView IV_user;

    @ViewById
    Button BT_back;

    @ViewById
    TextView toolbar_title;

    @ViewById
    ImageView IV_background;

    @ViewById
    ImageView IV_pentagon, IV_not_found;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        refresh();
    }

    void refresh(){

    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        toolbar_title.setText("얼굴 인식 실패");

        Glide.with(getApplicationContext()).load(R.drawable.ic_background).thumbnail(0.1f).into(IV_background);

        Glide.with(getApplicationContext()).
                load(R.drawable.ic_pentagon).
                thumbnail(0.1f).
                into(IV_pentagon);

        Glide.with(getApplicationContext()).
                load(R.drawable.ic_not_found).
                thumbnail(0.1f).
                into(IV_not_found);
    }

    @Click
    void BT_re_capture(){
        startActivity(new Intent(getApplicationContext(), CameraMainActivity.class));
    }
}
