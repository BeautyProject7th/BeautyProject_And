package com.makejin.beautyproject_android.Utils.Push;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Splash.SplashActivity;
import com.makejin.beautyproject_android.Splash.SplashActivity_;
import com.makejin.beautyproject_android.Utils.SharedManager.PreferenceManager;

import android.support.v4.app.NotificationCompat;
import android.util.Log;


/**
 * Created by mijeong on 2016. 11. 15..
 */
public class MyFcmListenerService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {
        /*
        String from = message.getFrom();
        Map<String, String> data = message.getData();
        String title = data.get("title");
        String msg = data.get("message");
        */

        RemoteMessage.Notification dd = message.getNotification();
        String title = dd.getTitle();
        String msg = dd.getBody();

        Log.i("push","주제 : "+message.getFrom());

        Log.i("push","title : "+title);
        Log.i("push","msg : "+msg);

        Context ctx = getApplicationContext();

        if(PreferenceManager.getInstance(ctx).getPush()){
            Log.i("test","push on");
            sendNotification(title,msg);
        }else {
            Log.i("test","push off");
        }

    }

    /**
     * 실제 디바에스에 GCM으로부터 받은 메세지를 알려주는 함수이다. 디바이스 Notification Center에 나타난다.
     * @param title
     * @param message
     */
    private void sendNotification(String title, String message) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}