package com.soma.beautyproject_android.Search;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by mijeong on 2017. 4. 23..
 */

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.DetailCosmetic.DetailCosmeticActivity_;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FindUserActivity_;
import com.soma.beautyproject_android.Login.LoginFragment;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;
/**
 * Created by mijeong on 2017. 4. 23..
 */


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_search)
public class SearchActivity extends ParentActivity {
    SearchActivity activity;
    public String keyword;
    public Brand brand;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        Fragment fragment = new SearchFragmentCategory();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.activity_search, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }




    @Click
    void LL_cosmetic_rank_1(){

    }

    /// EXIT
    @Override
    public void onBackPressed() {
//        Intent beforeintent = new Intent(activity, CosmeticUploadActivity_1.class);
//        beforeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        beforeintent.putExtra("brand", brand);
//        startActivity(beforeintent);
        activity.finish();
    }
}


