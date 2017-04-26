package com.makejin.beautyproject_android.DressingTable.YourDressingTable;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.makejin.beautyproject_android.DressingTable.More.MoreFragment;
import com.makejin.beautyproject_android.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_your_more)
public class YourMoreActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    public static String main_category;


    YourMoreActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        main_category = getIntent().getStringExtra("main_category");
        Log.i("vxcvx", main_category+"");

        Fragment fragment = new YourMoreFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.activity_your_more, fragment);
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


