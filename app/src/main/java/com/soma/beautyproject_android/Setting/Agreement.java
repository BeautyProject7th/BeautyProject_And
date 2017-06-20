package com.soma.beautyproject_android.Setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Constants.Constants;

public class Agreement extends AppCompatActivity {
    WebView webView;
    Button BT_back;
    TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_webview);

        webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());//클릭시 새창 안뜸
        webView.loadUrl(Constants.API_BASE_URL+"/agreement");

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("메화 이용약관");

        BT_back = (Button) findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}