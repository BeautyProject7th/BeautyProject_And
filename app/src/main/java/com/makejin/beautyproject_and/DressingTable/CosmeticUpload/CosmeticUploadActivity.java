//package com.makejin.beautyproject_and.DressingTable.CosmeticUpload;
//
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.makejin.beautyproject_and.DressingTable.DressingTableActivity_;
//import com.makejin.beautyproject_and.DressingTable.More.MoreFragment;
//import com.makejin.beautyproject_and.R;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.UiThread;
//import org.androidannotations.annotations.ViewById;
//
//import java.util.List;
//
//@EActivity(R.layout.activity_cosmetic_upload)
//public class CosmeticUploadActivity extends AppCompatActivity {
//    private long backKeyPressedTime = 0;
//    private Toast toast;
//
//    CosmeticUploadActivity activity;
//
//    @ViewById
//    Toolbar cs_toolbar;
//
//    @AfterViews
//    void afterBindingView() {
//        this.activity = this;
//
//        Fragment fragment = new CosmeticUploadFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.activity_cosmetic_upload, fragment);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.addToBackStack(null);
//        ft.commit();
//
//
//
//    }
//
//    void refresh() {
//
//    }
//
//    @UiThread
//    void uiThread() {
//
//    }
//
//    void connectTestCall() {
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if(getFragmentManager().getBackStackEntryCount() > 1){
//            getFragmentManager().popBackStack();
//        }
//        else{
//            activity.finish();
//        }
//    }
//
//}
//
//
