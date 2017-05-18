package com.soma.beautyproject_android.DressingTable.More;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import static android.R.attr.fragment;

@EActivity(R.layout.activity_more)
public class MoreActivity extends ParentActivity {

    public static String main_category = null;

    public static Boolean me_dressing_table = null;

    MoreActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        main_category = getIntent().getStringExtra("main_category");
        me_dressing_table = getIntent().getBooleanExtra("me",true);

        Fragment fragment = new MoreFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.activity_more, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}


