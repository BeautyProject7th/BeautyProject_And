package com.makejin.beautyproject_android.Login;

import android.app.Fragment;
import android.app.FragmentTransaction;

import com.makejin.beautyproject_android.ParentActivity;
import com.makejin.beautyproject_android.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import org.androidannotations.annotations.UiThread;

@EActivity(R.layout.activity_login)
public class LoginActivity extends ParentActivity {
    LoginActivity activity;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        Fragment fragment = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.activity_login, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }
    void refresh() {

    }

    @UiThread
    void uiThread() {

    }
}
