package com.soma.beautyproject_android.Utils.Connections.Intercepter;

import android.content.Context;

import com.soma.beautyproject_android.Utils.Connections.CookieSharedPreferences;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by mijeong on 2017. 4. 14..
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    //private PreferenceManager cookieSharedPreferences;
    private CookieSharedPreferences cookieSharedPreferences;

    /**
     * 생성자
     *
     * @param context
     */
    public ReceivedCookiesInterceptor(Context context){
        // CookieSharedReferences 객체 초기화
        //cookieSharedPreferences = PreferenceManager.getInstance(context);
        cookieSharedPreferences = CookieSharedPreferences.getInstanceOf(context);
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        // 가져온 chain으로 부터 리스폰스 객체를 생성
        Response response = chain.proceed(chain.request());

        // 리스폰스의 헤더 영역에 Set-Cookie가 설정되어있는 경우
        if (!response.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            // 쿠키 값을 읽어옴
            for (String header : response.headers("Set-Cookie")) {
                cookies.add(header);
            }

            // 쿠키 값을 CookieSharedPreferences에 저장
            //cookieSharedPreferences.putHashSet(cookies);
            cookieSharedPreferences.putHashSet(CookieSharedPreferences.COOKIE_SHARED_PREFERENCES_KEY, cookies);
        }

        // 리스폰스 객체 반환
        return response;
    }
}