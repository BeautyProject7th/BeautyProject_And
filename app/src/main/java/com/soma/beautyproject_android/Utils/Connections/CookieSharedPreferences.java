package com.soma.beautyproject_android.Utils.Connections;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;

/**
 * Created by mijeong on 2017. 4. 14..
 */

public class CookieSharedPreferences {
    /**
     * CookieSharedPreferences를 참조하기 위한 key
     *
     * 권고) 겹치지 않는 고유한 형태의 string으로 구성할 것
     */
    public static final String COOKIE_SHARED_PREFERENCES_KEY = "new.devetude.www.cookie";

    // 싱글톤 모델로 객체 초기화
    private static CookieSharedPreferences cookieSharedPreferences = null;

    public static CookieSharedPreferences getInstanceOf(Context c){
        if(cookieSharedPreferences == null){
            cookieSharedPreferences = new CookieSharedPreferences(c);
        }

        return cookieSharedPreferences;
    }

    private SharedPreferences sharedPreferences;

    /**
     * 생성자
     *
     * @param context
     */
    public CookieSharedPreferences(Context context) {
        final String COOKIE_SHARED_PREFERENCE_NAME = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(COOKIE_SHARED_PREFERENCE_NAME, Activity.MODE_PRIVATE);
    }

    /**
     * SharedPreferences에 값을 추가하는 메소드
     *
     * @param key
     * @param hashSet
     */
    public void putHashSet(String key, HashSet<String> hashSet){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, hashSet);
        Log.i("cookie", "put : "+String.valueOf(hashSet));
        editor.commit();
    }

    /**
     * SharedPreferences에서 값을 가져오는 메소드
     *
     * @param key
     * @param cookie
     * @return
     */
    public HashSet<String> getHashSet(String key, HashSet<String> cookie){
        try {
            Log.i("cookie", "get : "+String.valueOf(sharedPreferences.getStringSet(key, cookie)));
            return (HashSet<String>) sharedPreferences.getStringSet(key, cookie);
        } catch (Exception e) {
            return cookie;
        }
    }

    public void deleteHashSet(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        Log.i("cookie", "delete : "+String.valueOf(key));
        editor.commit();
    }
}