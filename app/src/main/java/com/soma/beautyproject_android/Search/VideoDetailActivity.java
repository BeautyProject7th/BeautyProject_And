package com.soma.beautyproject_android.Search;

/**
 * Created by mijeong on 2017. 4. 23..
 */

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLES20;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.soma.beautyproject_android.DressingTable.CosmeticExpirationDate.CosmeticExpirationDateActivity;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
import com.soma.beautyproject_android.DressingTable.More.MoreActivity_;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FindUserActivity_;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FollowerListActivity_;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.FollowingListActivity_;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.Model.Video_Youtuber;
import com.soma.beautyproject_android.Model.Youtuber;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Setting.SettingActivity_;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;
import com.soma.beautyproject_android.Video.Video.DeveloperKey;
import com.soma.beautyproject_android.Video.Video.YouTubeFailureRecoveryActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_video_detail)
public class VideoDetailActivity extends YouTubeFailureRecoveryActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    VideoDetailActivity activity;
    Video_Youtuber video_youtuber;

    @ViewById
    TextView TV_follower_number,TV_youtuber_name,TV_view_cnt, TV_upload_date,TV_video_name;

    @ViewById
    ImageView IV_video, IV_youtuber, IV_youtuber_skin_type, IV_youtuber_skin_trouble_1, IV_youtuber_skin_trouble_2, IV_youtuber_skin_trouble_3;

    @ViewById
    TextView toolbar_title;

    @ViewById
    Button BT_back, BT_like_video;

    public String id;
    public String video_id;


    public boolean like_flag = false;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

       refresh();

    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, activity);

        video_youtuber = (Video_Youtuber) getIntent().getSerializableExtra("video_youtuber");
        id = video_youtuber.id;
        video_id = video_youtuber.video_id;
        toolbar_title.setText("비디오 이름");
        //toolbar_title.setText(video.video_name);

        //TV_video_name.setText(video.video_name);
        TV_youtuber_name.setText(video_youtuber.youtuber_name);
        TV_view_cnt.setText(video_youtuber.view_cnt+"");
        TV_upload_date.setText(video_youtuber.upload_date.substring(0,10));

        String image_url_video = Constants.IMAGE_BASE_URL_video + video_youtuber.thumbnail;
        String image_url_youtuber = video_youtuber.profile_url;
        int image_url_skin_type = -1;
        int image_url_skin_trouble_1 = -1;
        int image_url_skin_trouble_2 = -1;
        int image_url_skin_trouble_3 = -1;

        switch(video_youtuber.skin_type){
            case "건성":
                //Log.i("zxc", position +" youtuber.skin_type(건성) " + youtuber.skin_type);
                image_url_skin_type = R.drawable.skin_type1;
                break;
            case "중성":
                image_url_skin_type = R.drawable.skin_type2;
                break;
            case "지성":
                image_url_skin_type = R.drawable.skin_type3;
                break;
            case "지성(수부지)":
                image_url_skin_type = R.drawable.skin_type4;
                break;

        }

        switch(video_youtuber.skin_trouble_1){
            case "다크서클":
                image_url_skin_trouble_1 = R.drawable.trouble1_darkcircle;
                break;
            case "블랙헤드":
                image_url_skin_trouble_1 = R.drawable.trouble2_blackhead;
                break;
            case "모공":
                image_url_skin_trouble_1 = R.drawable.trouble3_pore;
                break;
            case "각질":
                image_url_skin_trouble_1 = R.drawable.trouble4_deadskin;
                break;
            case "민감성":
                image_url_skin_trouble_1 = R.drawable.trouble5_sensitivity;
                break;
            case "주름":
                image_url_skin_trouble_1 = R.drawable.trouble6_wrinkle;
                break;
            case "여드름":
                image_url_skin_trouble_1 = R.drawable.trouble7_acne;
                break;
            case "안면홍조":
                image_url_skin_trouble_1 = R.drawable.trouble8_flush;
                break;
            case "없음":
                image_url_skin_trouble_1 = R.drawable.trouble9_nothing;
                break;

        }

        switch(video_youtuber.skin_trouble_2){
            case "다크서클":
                image_url_skin_trouble_2 = R.drawable.trouble1_darkcircle;
                break;
            case "블랙헤드":
                image_url_skin_trouble_2 = R.drawable.trouble2_blackhead;
                break;
            case "모공":
                image_url_skin_trouble_2 = R.drawable.trouble3_pore;
                break;
            case "각질":
                image_url_skin_trouble_2 = R.drawable.trouble4_deadskin;
                break;
            case "민감성":
                image_url_skin_trouble_2 = R.drawable.trouble5_sensitivity;
                break;
            case "주름":
                image_url_skin_trouble_2 = R.drawable.trouble6_wrinkle;
                break;
            case "여드름":
                image_url_skin_trouble_2 = R.drawable.trouble7_acne;
                break;
            case "안면홍조":
                image_url_skin_trouble_2 = R.drawable.trouble8_flush;
                break;
            case "없음":
                image_url_skin_trouble_2 = R.drawable.trouble9_nothing;
                break;

        }

        switch(video_youtuber.skin_trouble_3){
            case "다크서클":
                image_url_skin_trouble_3 = R.drawable.trouble1_darkcircle;
                break;
            case "블랙헤드":
                image_url_skin_trouble_3 = R.drawable.trouble2_blackhead;
                break;
            case "모공":
                image_url_skin_trouble_3 = R.drawable.trouble3_pore;
                break;
            case "각질":
                image_url_skin_trouble_3 = R.drawable.trouble4_deadskin;
                break;
            case "민감성":
                image_url_skin_trouble_3 = R.drawable.trouble5_sensitivity;
                break;
            case "주름":
                image_url_skin_trouble_3 = R.drawable.trouble6_wrinkle;
                break;
            case "여드름":
                image_url_skin_trouble_3 = R.drawable.trouble7_acne;
                break;
            case "안면홍조":
                image_url_skin_trouble_3 = R.drawable.trouble8_flush;
                break;
            case "없음":
                image_url_skin_trouble_3 = R.drawable.trouble9_nothing;
                break;
        }


        Glide.with(activity).
                load(image_url_youtuber).
                thumbnail(0.1f).
                bitmapTransform(new CropCircleTransformation(activity)).
                into(IV_youtuber);
//        Glide.with(activity).
//                load(image_url_video).
//                thumbnail(0.1f).
//                into(IV_video);
        Glide.with(activity).
                load(image_url_skin_type).
                thumbnail(0.1f).
                into(IV_youtuber_skin_type);
        Glide.with(activity).
                load(image_url_skin_trouble_1).
                thumbnail(0.1f).
                into(IV_youtuber_skin_trouble_1);
        Glide.with(activity).
                load(image_url_skin_trouble_2).
                thumbnail(0.1f).
                into(IV_youtuber_skin_trouble_2);
        Glide.with(activity).
                load(image_url_skin_trouble_3).
                thumbnail(0.1f).
                into(IV_youtuber_skin_trouble_3);

    }

    void refresh(){
        conn_get_my_like_video(id);
        conn_get_follower_number(TV_follower_number);
        conn_video_product(video_youtuber.id);
    }

    void conn_get_follower_number(final TextView view) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.user_follow_number(SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_get_follower_number 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<String> response) {
                        if (response != null) {
                            view.setText(response.get(0) + "명");
//                            TV_following.setText(response.get(0));
//                            TV_follower.setText(response.get(1));
                        } else{

                        }
                    }
                });
    }

    @Click
    void BT_back(){
        onBackPressed();
    }

    @Click
    void BT_like_video(){
        if(like_flag){
            conn_delete_like_video(SharedManager.getInstance().getMe().id, id);
        }else{
            conn_post_like_video(SharedManager.getInstance().getMe().id, id);
        }

    }



    void conn_video_product(String video_id) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.video_product(video_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cosmetic>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_video_product 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Cosmetic> response) {
                        if (response != null) {
//                            TV_following.setText(response.get(0));
//                            TV_follower.setText(response.get(1));
                        } else{

                        }
                    }
                });
    }



    void conn_post_like_video(String user_id, String id) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.post_like_video(user_id, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response.code == 200) {
                            like_flag = true;
                            BT_like_video.setBackgroundResource(R.drawable.ic_heart);
                            Toast.makeText(activity, "정상적으로 찜 했습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "찜에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void conn_delete_like_video(String user_id, String id) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.delete_like_video(user_id, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response.code == 200) {
                            like_flag = false;
                            BT_like_video.setBackgroundResource(R.drawable.ic_garage);
                            Toast.makeText(activity, "정상적으로 찜을 취소했습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "찜 취소에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    void conn_get_my_like_video(String id) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.get_my_like_video(SharedManager.getInstance().getMe().id, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        Log.i("ZXC", "conn_get_my_review 2");
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();;
                        Log.i("ZXC", "conn_get_my_like_video error");
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response.code == 200) { // isLike
                            like_flag = true;
                            BT_like_video.setBackgroundResource(R.drawable.ic_heart);
                        } else {
                            like_flag = false;
                            BT_like_video.setBackgroundResource(R.drawable.ic_garage);
                        }
                    }
                });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(video_id);
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}


