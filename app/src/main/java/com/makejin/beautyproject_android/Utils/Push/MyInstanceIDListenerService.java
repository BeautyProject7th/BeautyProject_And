package com.makejin.beautyproject_android.Utils.Push;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mijeong on 2017. 4. 25..
 * FCM 토큰 갱신용
 */

public class MyInstanceIDListenerService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // 이 token을 서버에 전달 한다.
    }
}