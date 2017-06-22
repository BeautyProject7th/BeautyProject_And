package com.soma.beautyproject_android.Camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.R;

import android.widget.ImageView;
import android.widget.TextView;

import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mijeong on 2017. 6. 18..
 */

@EActivity(R.layout.activity_camera_result)
public class CameraResultActivity extends ParentActivity {
    CameraResultActivity activity;


    @ViewById
    ImageView IV_user;

    @ViewById
    Button BT_back, BT_re_capture, BT_complete;

    @ViewById
    TextView toolbar_title;

    boolean gallery_flag;

    String imagepath = null;


    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        refresh();
    }

    void refresh(){
       //conn_get_my_info();
        conn_camera_image();
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
//            Bitmap myBitmap = BitmapFactory.decodeFile(CameraMainActivity.outFile.getAbsolutePath());
//            IV_user.setImageBitmap(myBitmap);
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


    void conn_camera_image() {
//        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.camera_image(SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public final void onCompleted() {
//                        LoadingUtil.stopLoading(indicator);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        //LoadingUtil.stopLoading(indicator);
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "이미 등록한 화장품입니다.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<String> response) {
                        if (response != null) {
                            imagepath = response.get(0);
                            String url = Constants.IMAGE_BASE_URL_camera+imagepath;
                            Log.i("url", "url : " + url);
                            Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_URL_camera+imagepath).thumbnail(0.1f).bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(IV_user);
                            //Toast.makeText(getApplicationContext(), "정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}