//package com.soma.beautyproject_android.DressingTable;
//
///**
// * Created by mijeong on 2017. 4. 23..
// */
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.opengl.GLES20;
//import android.provider.MediaStore;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.SimpleTarget;
//import com.soma.beautyproject_android.DressingTable.CosmeticExpirationDate.CosmeticExpirationDateActivity;
//import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
//import com.soma.beautyproject_android.DressingTable.More.MoreActivity_;
//import com.soma.beautyproject_android.DressingTable.YourDressingTable.FindUserActivity_;
//import com.soma.beautyproject_android.DressingTable.YourDressingTable.FollowerListActivity_;
//import com.soma.beautyproject_android.DressingTable.YourDressingTable.FollowingListActivity_;
//import com.soma.beautyproject_android.Model.GlobalResponse;
//import com.soma.beautyproject_android.Model.User;
//import com.soma.beautyproject_android.ParentActivity;
//import com.soma.beautyproject_android.R;
//import com.soma.beautyproject_android.Setting.SettingActivity_;
//import com.soma.beautyproject_android.Utils.Connections.CSConnection;
//import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
//import com.soma.beautyproject_android.Utils.Constants.Constants;
//import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;
//
//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.Click;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.ViewById;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import jp.wasabeef.glide.transformations.CropCircleTransformation;
//import okhttp3.MediaType;
//import okhttp3.RequestBody;
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
//@EActivity(R.layout.activity_dressing_table)
//public class ThumbnailUpdatePopupActivity extends ParentActivity {
//    private Toast toast;
//
//    ThumbnailUpdatePopupActivity activity;
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // just as usual
//
//    }
//
//    @AfterViews
//    void afterBindingView() {
//        this.activity = this;
//
//    }
//
//    public String getPath(Uri uri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {
//                Uri selectedImageUri = data.getData();
//                imagepath = getPath(selectedImageUri);
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
//                Glide.with(activity).load(imagepath).asBitmap()./*override(500,300).*/into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        IV_user.setImageBitmap(resource);
//
//                        if (resource.getHeight() > tempMaxTextureSize[0]) {
//                            int resizedWidth = IV_user.getWidth();
//                            int resizedHeight = IV_user.getHeight();
//                            IV_user.setImageBitmap(resource.createScaledBitmap(resource, resizedWidth, resizedHeight, false));
//                        }
//
//                    }
//                });
//            }
//        }
//    }
//
//    public File saveBitmapToFile(File file){
//        try {
//
//            // BitmapFactory options to downsize the image
//            BitmapFactory.Options o = new BitmapFactory.Options();
//            o.inJustDecodeBounds = true;
//            o.inSampleSize = 6;
//            // factor of downsizing the image
//
//            FileInputStream inputStream = new FileInputStream(file);
//            //Bitmap selectedBitmap = null;
//            BitmapFactory.decodeStream(inputStream, null, o);
//            inputStream.close();
//
//            // The new size we want to scale to
//            final int REQUIRED_SIZE=75;
//
//            // Find the correct scale value. It should be the power of 2.
//            int scale = 1;
//            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
//                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
//                scale *= 2;
//            }
//
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize = scale;
//            inputStream = new FileInputStream(file);
//
//            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
//            inputStream.close();
//
//            // here i override the original image file
//            file.createNewFile();
//            FileOutputStream outputStream = new FileOutputStream(file);
//
//            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 50 , outputStream);
//
//            return file;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    void uploadFile1() {
//        File file = new File(imagepath);
//        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), saveBitmapToFile(file));
//        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
//        conn.fileUploadWrite(SharedManager.getInstance().getMe().id, fbody)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<GlobalResponse>() {
//                    @Override
//                    public final void onCompleted() {
//                        //setResult(Constants.ACTIVITY_CODE_TAB2_REFRESH_RESULT);
//                        finish();
//                    }
//
//                    @Override
//                    public final void onError(Throwable e) {
//                        e.printStackTrace();
//                        Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public final void onNext(GlobalResponse response) {
//                        if (response != null) {
//                        } else {
//                            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//}
//
//
