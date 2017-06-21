package com.soma.beautyproject_android.Camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.opengl.GLES20;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.soma.beautyproject_android.MyPage.LikeCosmeticListActivity;
import com.soma.beautyproject_android.MyPage.LikeVideoListActivity_;
import com.soma.beautyproject_android.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Splash.SplashActivity_;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mijeong on 2017. 6. 18..
 */

@EActivity(R.layout.activity_result)
public class ResultActivity extends ParentActivity {
    ResultActivity activity;


    @ViewById
    ImageView IV_user;

    @ViewById
    Button BT_back, BT_re_capture, BT_complete;

    @ViewById
    TextView toolbar_title;

    boolean gallery_flag;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        refresh();
    }

    void refresh(){
       //conn_get_my_info();
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        toolbar_title.setText("메이크업 진단");

        gallery_flag = getIntent().getBooleanExtra("gallery_flag", false);
        Log.i("ZXC", "gallery_flag : " + gallery_flag);
        if(gallery_flag){
//            Log.i("ZXC", "imagepath : " + getIntent().getByteArrayExtra("imagepath_byte"));
//            IV_user.setImageBitmap(byteArrayToBitmap(getIntent().getByteArrayExtra("imagepath_byte")));



//            imagepath = getIntent().getStringExtra("imagepath");
//            Log.i("ZXC", "1");
//            Log.i("ZXC", "imagepath2 : " +imagepath);
//            //Glide.with(getApplicationContext()).load(imagepath).thumbnail(0.1f).bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(IV_user);
//            Glide.with(activity).load(imagepath).asBitmap()./*override(500,300).*/into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    IV_user.setImageBitmap(resource);
//
//                    int[] maxTextureSize = new int[1];
//                    GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
//
//                    final int[] tempMaxTextureSize = maxTextureSize;
//
//                    if (resource.getHeight() > tempMaxTextureSize[0]) {
//                        int resizedWidth = IV_user.getWidth();
//                        int resizedHeight = IV_user.getHeight();
//                        IV_user.setImageBitmap(resource.createScaledBitmap(resource, resizedWidth, resizedHeight, false));
//                    }
//
//                }
//            });
        }else{
            //Log.i("ZXC", "imagepath : " + getIntent().getByteArrayExtra("imagepath_byte"));
            //IV_user.setImageBitmap(byteArrayToBitmap(CameraMainActivity.imagepath_byte));
            Bitmap myBitmap = BitmapFactory.decodeFile(CameraMainActivity.outFile.getAbsolutePath());
            IV_user.setImageBitmap(myBitmap);
        }

    }

//    public Uri getUriFromPath(String path)
//        String fileName= "file:///sdcard/DCIM/Camera/2013_07_07_12345.jpg";
//        Uri fileUri = Uri.parse( fileName );
//        String filePath = fileUri.getPath();
//        Cursor c = getContentResolver().query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, "_data = '" + filePath + "'", null, null );
//        cursor.moveToNext()
//        int id = cursor.getInt( cursor.getColumnIndex( "_id" ) );
//        Uri uri = ContentUris.withAppendedId( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id );
//
//        return uri;
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_CODE_RESULT_ACTIVITY_REFRESH_RESULT) {
            if (requestCode == Constants.ACTIVITY_CODE_RESULT_ACTIVITY_REFRESH_REQUEST) {
//                Glide.with(getApplicationContext()).load(data.getStringExtra(imagepath)).thumbnail(0.1f).bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(IV_user);

//                Log.e("imagepath : ", imagepath);
//                Log.e("upload message : ", "Uploading file path:" + imagepath);
//                //TODO:임시데이터 넣음 user+현재시간으로 바꿀 것
//                SimpleDateFormat sdfNow = new SimpleDateFormat("yyMMddHHmmssSSS");
//                String current_time = sdfNow.format(new Date(System.currentTimeMillis()));
//                //n_food.image_url = "lmjing_" + current_time;
//
//                int[] maxTextureSize = new int[1];
//                GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
//
//                final int[] tempMaxTextureSize = maxTextureSize;
//
//                Glide.with(getApplicationContext()).load(imagepath).asBitmap()./*override(500,300).*/into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        food_image.setImageBitmap(resource);
//
//                        if (resource.getHeight() > tempMaxTextureSize[0]) {
//                            int resizedWidth = food_image.getWidth();
//                            int resizedHeight = food_image.getHeight();
//                            food_image.setImageBitmap(resource.createScaledBitmap(resource, resizedWidth, resizedHeight, false));
//                        }
//
//                    }
//                });
            }
        }
    }

    public Bitmap byteArrayToBitmap( byte[] $byteArray ) {
        Bitmap bitmap = BitmapFactory.decodeByteArray( $byteArray, 0, $byteArray.length ) ;
        return bitmap ;
    }

    @Click
    void BT_back(){
        onBackPressed();
    }

    @Click
    void BT_re_capture(){
        Intent intent = new Intent(this, CameraMainActivity.class);
        startActivity(intent);
    }
    @Click
    void BT_complete(){
//        Intent intent = new Intent(this, Result2Activity_.class);
//        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BT_re_capture.callOnClick();
    }
}
