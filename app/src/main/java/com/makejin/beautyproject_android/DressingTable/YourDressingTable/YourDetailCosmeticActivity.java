package com.makejin.beautyproject_android.DressingTable.YourDressingTable;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.makejin.beautyproject_android.DetailCosmetic.DetailCosmeticFragment;
import com.makejin.beautyproject_android.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_your_detail_cosmetic)
public class YourDetailCosmeticActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;
    public String cosmetic_id;

    YourDetailCosmeticActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        cosmetic_id = (String) getIntent().getStringExtra("cosmetic_id");
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


