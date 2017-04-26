package com.makejin.beautyproject_android.DressingTable.Setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.SkinTrouble.SkinTroubleActivity_;
import com.makejin.beautyproject_android.SkinType.SkinTypeActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_setting)
public class SettingActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    SettingActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    Button BT_skin_type, BT_skin_trouble, BT_agreement, BT_privacy_rule, BT_logout;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

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
        //startActivity(new Intent(getApplicationContext(), SkinTypeActivity_.class));
    }



    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall() {

    }
}


