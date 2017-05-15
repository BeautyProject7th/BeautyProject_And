package com.soma.beautyproject_android.Utils.Push;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.soma.beautyproject_android.DressingTable.DressingTableActivity_;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.PreferenceManager;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mijeong on 2017. 4. 25..
 * FCM 토큰 갱신용
 */

public class MyInstanceIDListenerService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // 이 token을 서버에 전달 한다.

        PreferenceManager.getInstance(getApplicationContext()).setPushToken(refreshedToken);

        /*
        Log.i("push","token값 : "+refreshedToken);

        Map user = new HashMap();
        user.put("user_id", SharedManager.getInstance().getMe().id);
        user.put("push_token", refreshedToken);
        connectTestCall_update_skin_type(user);
        */
    }

    void connectTestCall_update_skin_type(final Map user) {
        CSConnection conn = ServiceGenerator.createService(getApplicationContext(),CSConnection.class);
        conn.user_updateToken(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        Log.i("push","token값 재갱신-완료");
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response != null) {
                            Log.i("push","token값 재갱신-성공");
                            Toast.makeText(getApplicationContext(), "정상적으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}