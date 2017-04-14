package com.makejin.beautyproject_and.Utils.Connections.Intercepter;

import android.content.Context;

import com.makejin.beautyproject_and.Utils.Connections.CookieSharedPreferences;
import com.makejin.beautyproject_and.Utils.SharedManager.PreferenceManager;
import com.makejin.beautyproject_and.Utils.SharedManager.SharedManager;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mijeong on 2017. 4. 14..
 */

public class AddCookiesInterceptor implements Interceptor {
    //private PreferenceManager cookieSharedPreferences;
    private CookieSharedPreferences cookieSharedPreferences;

    /**
     * 생성자
     *
     * @param context
     */
    public AddCookiesInterceptor(Context context){
        // CookieSharedReferences 객체 초기화
        //cookieSharedPreferences = PreferenceManager.getInstance(context);
        cookieSharedPreferences = CookieSharedPreferences.getInstanceOf(context);
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        // 가져온 chain으로 부터 빌더 객체를 생성
        Request.Builder builder = chain.request().newBuilder();

        // CookieSharedPreferences에 저장되어있는 쿠키 값을 가져옴
        //HashSet<String> cookies = (HashSet) cookieSharedPreferences.getHashSet(new HashSet<String>());
        HashSet<String> cookies = (HashSet) cookieSharedPreferences.getHashSet(
                CookieSharedPreferences.COOKIE_SHARED_PREFERENCES_KEY,
                new HashSet<String>()
        );

        // 빌더 헤더 영역에 쿠키 값 추가
        for (String cookie : cookies) {
            builder.addHeader("Cookie", cookie);
        }

        // 체인에 빌더를 적용 및 반환
        return chain.proceed(builder.build());
    }
}