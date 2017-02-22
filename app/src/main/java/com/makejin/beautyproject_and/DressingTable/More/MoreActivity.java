package com.makejin.beautyproject_and.DressingTable.More;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.makejin.beautyproject_and.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_more)
public class MoreActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    public int category_num;


    MoreActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        category_num = getIntent().getIntExtra("category",0);

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


