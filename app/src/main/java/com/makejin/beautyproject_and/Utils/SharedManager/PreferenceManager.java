package com.makejin.beautyproject_and.Utils.SharedManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;

/**
 * Created by mijeong on 2016. 11. 22..
 */

public class PreferenceManager {
    private volatile static PreferenceManager single;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    public static PreferenceManager getInstance(Context ctx) {
        if (single == null) {
            synchronized(PreferenceManager.class) {
                if (single == null) {
                    single = new PreferenceManager(ctx);
                }
            }
        }

        return single;
    }
    public boolean set_id(String _id){
        try {
            editor.putString("_id", _id);
            editor.commit();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String get_id(){
        return prefs.getString("_id","");
    }

    public PreferenceManager(Context ctx){
        prefs = ctx.getSharedPreferences("beauty", ctx.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public Boolean getPush() {
        return prefs.getBoolean("push",true);
    }

    public boolean setPush(Boolean push) {
        try {
            editor.putBoolean("push",push);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * SharedPreferences에 값을 추가하는 메소드
     *
     * @param hashSet
     */
    public void putHashSet(HashSet<String> hashSet){
        editor.putStringSet("cookie", hashSet);
        Log.i("cookie", String.valueOf(hashSet));
        editor.commit();
    }

    /**
     * SharedPreferences에서 값을 가져오는 메소드
     *
     * @param cookie
     * @return
     */
    public HashSet<String> getHashSet(HashSet<String> cookie){
        try {
            Log.i("cookie", String.valueOf(prefs.getStringSet("cookie", cookie)));
            return (HashSet<String>) prefs.getStringSet("cookie", cookie);
        } catch (Exception e) {
            return cookie;
        }
    }
}
