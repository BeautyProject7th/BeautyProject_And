package com.makejin.beautyproject_android.SkinType;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.makejin.beautyproject_android.Model.User;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EActivity(R.layout.activity_skin_type)
public class SkinTypeActivity extends AppCompatActivity {
    SkinTypeActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    TextView TV_skin_type_1,TV_skin_type_2,TV_skin_type_3,TV_skin_type_4;

    @ViewById
    Button BT_complete;

    String skin_type = null;

    User user = SharedManager.getInstance().getMe();

    Map<Integer,String> skin_type_list = new HashMap<Integer, String>();

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        skin_type_list.put(R.id.TV_skin_type_1, "건성");
        skin_type_list.put(R.id.TV_skin_type_2, "중성");
        skin_type_list.put(R.id.TV_skin_type_3, "지성(일반)");
        skin_type_list.put(R.id.TV_skin_type_4, "지성(수분부족)");

//        TV_skin_type_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                skin_type = TV_skin_type_1.getText().toString();
//                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        TV_skin_type_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                skin_type = TV_skin_type_2.getText().toString();
//                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        TV_skin_type_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                skin_type = TV_skin_type_3.getText().toString();
//                Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        TV_skin_type_4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                skin_type = TV_skin_type_4.getText().toString();
//                Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        BT_complete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedManager.getInstance().getMe().skin_type = skin_type;
//
//            }
//        });
    }


    @Click({R.id.TV_skin_type_1,R.id.TV_skin_type_2,R.id.TV_skin_type_3,R.id.TV_skin_type_4})
    void onClick(View v){
        click_TV_skin_type(v.getId());
    }


    void click_TV_skin_type(int view_id){
//        if(skin_type != null){
//
//        }
        if(skin_type_list.get(view_id) == skin_type) {
            skin_type = null;
            findViewById(view_id).setBackgroundColor(Color.GRAY);
            return;
        }
        findViewById(R.id.TV_skin_type_1).setBackgroundColor(Color.GRAY);
        findViewById(R.id.TV_skin_type_2).setBackgroundColor(Color.GRAY);
        findViewById(R.id.TV_skin_type_3).setBackgroundColor(Color.GRAY);
        findViewById(R.id.TV_skin_type_4).setBackgroundColor(Color.GRAY);

        skin_type = skin_type_list.get(view_id);
        findViewById(view_id).setBackgroundColor(Color.GREEN);

    }

    @Click
    void BT_complete(){
        user.skin_type = skin_type;

        Log.i("user.skin_type", user.skin_type);

        //SharedManager.getInstance().getMe().skin_type = skin_type;
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
