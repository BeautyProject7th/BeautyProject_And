package com.soma.beautyproject_android.DetailCosmetic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.soma.beautyproject_android.R;

import static com.soma.beautyproject_android.R.id.webview;
import static io.userhabit.service.main.c.n;

/**
 * Created by mijeong on 2017. 6. 22..
 */

public class WebViewActivity extends Activity {

    private WebView mWebView;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        setLayout();

        toolbar_title.setText("구매하기");

        String url = getIntent().getStringExtra("url");

        // 구글홈페이지 지정
        mWebView.loadUrl(url);
        // WebViewClient 지정
        mWebView.setWebViewClient(new WebViewClientClass());
        //mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        // 웹뷰에서 자바스크립트실행가능
        mWebView.getSettings().setJavaScriptEnabled(true);
        String userAgent = mWebView.getSettings().getUserAgentString();

        mWebView.getSettings().setUserAgentString(userAgent+" APP_WISHRROM_Android");
        mWebView.setVerticalScrollBarEnabled(true);

        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void setLayout(){
        mWebView = (WebView) findViewById(webview);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
    }
}