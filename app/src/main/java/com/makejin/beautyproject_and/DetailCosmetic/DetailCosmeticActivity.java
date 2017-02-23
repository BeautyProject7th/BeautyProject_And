package com.makejin.beautyproject_and.DetailCosmetic;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.makejin.beautyproject_and.Model.Cosmetic;
import com.makejin.beautyproject_and.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_detail_cosmetic)
public class DetailCosmeticActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;
    public Cosmetic cosmetic;

    DetailCosmeticActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        cosmetic = (Cosmetic) getIntent().getSerializableExtra("cosmetic");
        Fragment fragment = new DetailCosmeticFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.activity_detail_cosmetic, fragment);
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


