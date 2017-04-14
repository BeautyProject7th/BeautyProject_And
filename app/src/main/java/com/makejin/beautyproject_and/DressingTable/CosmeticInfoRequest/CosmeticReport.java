package com.makejin.beautyproject_and.DressingTable.CosmeticInfoRequest;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.makejin.beautyproject_and.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_and.Model.GlobalResponse;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.makejin.beautyproject_and.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_and.R;
import com.makejin.beautyproject_and.Utils.Connections.CSConnection;
import com.makejin.beautyproject_and.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_and.Utils.Constants.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.makejin.beautyproject_and.R.id.BT_request;
import static com.makejin.beautyproject_and.R.id.ET_brand;
import static com.makejin.beautyproject_and.R.id.ET_cosmetic;

@EActivity(R.layout.activity_registration_request)
public class CosmeticReport extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    CosmeticReport activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    Button BT_back, BT_home;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        Toolbar cs_toolbar = (Toolbar)findViewById(R.id.cs_toolbar);
        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        BT_back = (Button) cs_toolbar.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        BT_home = (Button) cs_toolbar.findViewById(R.id.BT_home);
        BT_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), DressingTableActivity_.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
    }

    @UiThread
    void uiThread() {

    }

    /// EXIT
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(getApplicationContext(), getString(R.string.exit_app), Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}