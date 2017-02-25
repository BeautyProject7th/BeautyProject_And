package com.makejin.beautyproject_and.DressingTable.CosmeticUpload;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.makejin.beautyproject_and.DressingTable.More.MoreFragment;
import com.makejin.beautyproject_and.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_cosmetic_upload)
public class CosmeticUploadActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    CosmeticUploadActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        getListener();

        Fragment fragment = new CosmeticUploadFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.activity_cosmetic_upload, fragment);
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

    private FragmentManager.OnBackStackChangedListener getListener()
    {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener()
        {
            public void onBackStackChanged()
            {
                FragmentManager manager = getFragmentManager();

                if (manager != null)
                {
                    if(manager.getBackStackEntryCount() >= 1){
                        String topOnStack = manager.getBackStackEntryAt(manager.getBackStackEntryCount()-1).getName();
                        Log.i("TOP ON BACK STACK",topOnStack);
                    }
                }
            }
        };

        return result;
    }
}


