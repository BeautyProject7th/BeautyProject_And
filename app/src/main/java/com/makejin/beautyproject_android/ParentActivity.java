package com.makejin.beautyproject_android;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by mijeong on 2017. 4. 30..
 */

public class ParentActivity extends AppCompatActivity {

    //폰트 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //폰트 설정
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NanumSquareOTFRegular.otf"))
                .addBold(Typekit.createFromAsset(this, "NanumSquareOTFBold.otf"));
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }
}
