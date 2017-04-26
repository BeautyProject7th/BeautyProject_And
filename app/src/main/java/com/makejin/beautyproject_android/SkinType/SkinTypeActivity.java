package com.makejin.beautyproject_android.SkinType;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_skin_type)
public class SkinTypeActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    SkinTypeActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    TextView TV_skin_type_1,TV_skin_type_2,TV_skin_type_3,TV_skin_type_4;

    @ViewById
    Button BT_complete;

    String skin_type = null;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        TV_skin_type_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skin_type = TV_skin_type_1.getText().toString();
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
            }
        });

        TV_skin_type_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skin_type = TV_skin_type_2.getText().toString();
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
            }
        });

        TV_skin_type_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skin_type = TV_skin_type_3.getText().toString();
                Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
            }
        });

        TV_skin_type_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skin_type = TV_skin_type_4.getText().toString();
                Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
            }
        });

        BT_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedManager.getInstance().getMe().skin_type = skin_type;

            }
        });
    }

    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
