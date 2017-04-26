package com.makejin.beautyproject_android.SkinTrouble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_skin_trouble)
public class SkinTroubleActivity extends AppCompatActivity {

    SkinTroubleActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    TextView TV_skin_trouble_1,TV_skin_trouble_2,TV_skin_trouble_3,TV_skin_trouble_4,TV_skin_trouble_5,TV_skin_trouble_6,TV_skin_trouble_7,TV_skin_trouble_8;

    @ViewById
    Button BT_complete;

    String skin_type = null;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
    }

    @Click
    void TV_skin_trouble_1(){
        skin_type = TV_skin_trouble_1.getText().toString();
        Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
    }
    @Click
    void TV_skin_trouble_2(){
        skin_type = TV_skin_trouble_2.getText().toString();
        Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
    }
    @Click
    void TV_skin_trouble_3(){
        skin_type = TV_skin_trouble_3.getText().toString();
        Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
    }
    @Click
    void TV_skin_trouble_4(){
        skin_type = TV_skin_trouble_4.getText().toString();
        Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
    }
    @Click
    void TV_skin_trouble_5(){
        skin_type = TV_skin_trouble_5.getText().toString();
        Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
    }
    @Click
    void TV_skin_trouble_6(){
        skin_type = TV_skin_trouble_6.getText().toString();
        Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_SHORT).show();
    }
    @Click
    void TV_skin_trouble_7(){
        skin_type = TV_skin_trouble_7.getText().toString();
        Toast.makeText(getApplicationContext(), "7", Toast.LENGTH_SHORT).show();
    }
    @Click
    void TV_skin_trouble_8(){
        skin_type = TV_skin_trouble_8.getText().toString();
        Toast.makeText(getApplicationContext(), "8", Toast.LENGTH_SHORT).show();
    }

    @Click
    void BT_complete(){
        SharedManager.getInstance().getMe().skin_type = skin_type;
    }

    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
