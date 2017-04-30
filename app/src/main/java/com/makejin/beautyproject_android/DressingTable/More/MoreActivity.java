package com.makejin.beautyproject_android.DressingTable.More;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.makejin.beautyproject_android.ParentActivity;
import com.makejin.beautyproject_android.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_more)
public class MoreActivity extends ParentActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    public static String main_category;


    MoreActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        main_category = getIntent().getStringExtra("main_category");
        Log.i("vxcvx", main_category+"");

        Fragment fragment = new MoreFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.activity_more, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall() {

    }

}


