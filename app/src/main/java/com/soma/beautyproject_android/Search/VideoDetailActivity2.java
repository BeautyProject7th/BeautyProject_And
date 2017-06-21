/*

package com.soma.beautyproject_android.Search;

import org.androidannotations.annotations.EActivity;




import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.Model.Youtuber;
import com.soma.beautyproject_android.R;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_video_detail)
public class VideoDetailActivity2 extends YouTubeFailureRecoveryActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    VideoDetailActivity2 activity;

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

    private Video video;
    private Youtuber youtuber;

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

        video = (Video) getIntent().getSerializableExtra("video");
        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, activity);


        Map<String, Object> map = new HashMap<>();
        map.put("user_id", SharedManager.getInstance().getMe().id);
        map.put("content", video.title);
        conn_train_view_cosmetic(map);
        connectTestCall_creater();

        toolbar_title.setText("영상 상세정보");

        TV_video_name.setText(video.title);
        TV_youtuber_name.setText(video.youtuber_name);
        TV_view_cnt.setText(video.view_cnt + 1 + "회");
        TV_upload_date.setText(video.upload_date.substring(0, 10));
    }

    private void set_skin_type() {
        int image_url_skin_type = -1;
        int image_url_skin_trouble_1 = -1;
        int image_url_skin_trouble_2 = -1;
        int image_url_skin_trouble_3 = -1;

        switch(youtuber.skin_type){
            case "건성":
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

        switch(youtuber.skin_trouble_1){
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

        switch(youtuber.skin_trouble_2){
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

        switch(youtuber.skin_trouble_3){
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
        conn_view_video(id);
        conn_get_my_like_video(id);
        conn_get_follower_number(TV_follower_number);
        //conn_video_product(video_youtuber.id);
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
    void BT_like_video() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", SharedManager.getInstance().getMe().id);
        map.put("id", video.id);
        map.put("titld", video.title);
        if (like_flag) {
            conn_delete_like_video(map);
        } else {
            conn_post_like_video(map);
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




    void conn_post_like_video(Map<String,Object> map) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.post_like_video(map)
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

    void conn_delete_like_video(Map<String,Object> map) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.delete_like_video(map)
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
                            BT_like_video.setBackgroundResource(R.drawable.heart);
                            BT_like_video.setBackgroundResource(R.drawable.ic_heart_empty);

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
                        like_flag = false;

                        BT_like_video.setBackgroundResource(R.drawable.heart);

                        BT_like_video.setBackgroundResource(R.drawable.ic_heart_empty);

                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response.code == 200) { // isLike
                            like_flag = true;
                            BT_like_video.setBackgroundResource(R.drawable.ic_heart);
                        } else {
                            like_flag = false;

                            BT_like_video.setBackgroundResource(R.drawable.heart);

                            BT_like_video.setBackgroundResource(R.drawable.ic_heart_empty);
                        }
                    }
                });
    }

    void conn_view_video(String id) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.view_video(SharedManager.getInstance().getMe().id, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                    }

                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response.code == 200) {
                            video.view_cnt++;
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
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

    void connectTestCall_creater() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_youtuber(video.youtuber_name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Youtuber>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_cosmetic_rank error", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(final Youtuber response) {
                        if (response != null) {
                            youtuber = response;
                            Glide.with(activity).
                                    load(response.profile_url).
                                    thumbnail(0.1f).
                                    into(IV_youtuber);
                            TV_youtuber_name.setText(response.name);
                            TV_view_cnt.setText(video.view_cnt);
                            TV_upload_date.setText(video.upload_date.substring(0,10));
                            Glide.with(activity).
                                    load(youtuber.profile_url).
                                    thumbnail(0.1f).
                                    bitmapTransform(new CropCircleTransformation(activity)).
                                    into(IV_youtuber);
                            //TV_video_name.setText(video.video_name);
                            TV_youtuber_name.setText(youtuber.name);
                            TV_view_cnt.setText(video.view_cnt+1+"회");
                            TV_upload_date.setText(video.upload_date.substring(0,10));

                            set_skin_type();
                            //String image_url_video = Constants.IMAGE_BASE_URL_video + youtuber.thumbnail;
                        } else{
                            Toast.makeText(activity,"크리에이터 서버 오류",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void conn_train_view_cosmetic(Map<String,Object> map) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.train_video_view(map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        Log.i("train","success");
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();;
                        Log.i("train","fail to server error");
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response.code == 200) { // isLike
                            Log.i("train","success");
                        } else {
                            Log.i("train","fail");
                        }
                    }
                });
    }

}


*/
