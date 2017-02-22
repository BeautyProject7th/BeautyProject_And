package com.makejin.beautyproject_and.DressingTable.Setting;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.makejin.beautyproject_and.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_dressing_table)
public class SettingActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    SettingActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

    }

    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall() {

    }
}


