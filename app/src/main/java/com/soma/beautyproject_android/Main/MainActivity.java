package com.soma.beautyproject_android.Main;

/**
 * Created by mijeong on 2017. 4. 23..
 */

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.DetailCosmetic.DetailCosmeticActivity_;
import com.soma.beautyproject_android.DressingTable.DressingTableActivity_;
import com.soma.beautyproject_android.DressingTable.YourDressingTable.YourDressingTableActivity_;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Search.SearchActivity_;
import com.soma.beautyproject_android.Search.VideoDetailActivity_;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity(R.layout.activity_main)
public class MainActivity extends ParentActivity {
    private long backKeyPressedTime = 0;
    private Toast toast;

    MainActivity activity;

    Map<Integer,String> categorylist = new HashMap<Integer, String>();

    User me;

    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    Button BT_mypage, BT_search;

//    @ViewById
//    Button BT_cosmetic_ranking_more, BT_recommend_cosmetic_more, BT_recommend_video_more, BT_match_user_more, BT_match_creator_more, BT_dressing_table_ranking_more;


    //1.화장품 랭킹
    @ViewById
    LinearLayout LL_cosmetic_rank_1, LL_cosmetic_rank_2, LL_cosmetic_rank_3;

    @ViewById
    ImageView IV_cosmetic_rank_1, IV_cosmetic_rank_2, IV_cosmetic_rank_3;

    @ViewById
    TextView TV_cosmetic_rank_1_brand, TV_cosmetic_rank_2_brand, TV_cosmetic_rank_3_brand;

    @ViewById
    TextView TV_cosmetic_rank_1_name, TV_cosmetic_rank_2_name, TV_cosmetic_rank_3_name;


    //1.5.화장품 추천
    @ViewById
    LinearLayout LL_recommend_cosmetic_1, LL_recommend_cosmetic_2, LL_recommend_cosmetic_3;

    @ViewById
    ImageView IV_recommend_cosmetic_rank_1, IV_recommend_cosmetic_rank_2, IV_recommend_cosmetic_rank_3;

    @ViewById
    TextView TV_recommend_cosmetic_rank_1_brand, TV_recommend_cosmetic_rank_2_brand,TV_recommend_cosmetic_rank_3_brand;

    @ViewById
    TextView TV_recommend_cosmetic_rank_1_name, TV_recommend_cosmetic_rank_2_name, TV_recommend_cosmetic_rank_3_name;

    //2.추천 영상
    @ViewById
    RelativeLayout video_1, video_2;

    @ViewById
    ImageView IV_video_img;

    @ViewById
    TextView TV_creator_name, TV_video_name;


    //3.나와 비슷한 타입의 유저
    @ViewById
    RelativeLayout user_1, user_2, user_3;


    //4.나와 비슷한 타입의 크리에이터
    @ViewById
    RelativeLayout creator_1, creator_2, creator_3;

    @ViewById
    LinearLayout LL_user_info;

    //5.화장대 랭킹
    @ViewById
    RelativeLayout ranker_1, ranker_2, ranker_3;

    @ViewById
    TextView TV_cosmetic_have_number ,TV_user_name, TV_cosmetic_expiration_soon;

    @ViewById
    LinearLayout LL_move_dressing_table;

    //화장품 랭킹
    TextView[] TV_cosmetic_rank_brand = new TextView[3];
    TextView[] TV_cosmetic_rank_name = new TextView[3];
    LinearLayout[] LL_cosmetic_rank = new LinearLayout[3];
    ImageView[] IV_cosmetic_rank = new ImageView[3];
    ImageView[] IV_recommend_cosmetic_rank = new ImageView[3];
    TextView[] TV_recommend_cosmetic_rank_brand = new TextView[3];
    TextView[] TV_recommend_cosmetic_rank_name = new TextView[3];
    LinearLayout[] LL_recommend_cosmetic = new LinearLayout[3];
    //영상 추천
    RelativeLayout[] video = new RelativeLayout[2];
    private String imagepath = null;

    @Override
    protected void onResume() {
        super.onResume();
        // just as usual

        refresh();
    }

    void refresh(){
        conn_get_my_cosmetic_info();
        connectTestCall_cosmetic_rank();
        connectTestCall_match_user();
        connectTestCall_cosmetic_recommend();
        connectTestCall_video_recommend();
        connectTestCall_match_creator();
        connectTestCall_dressing_table_rank();
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        TV_cosmetic_rank_brand[0] = TV_cosmetic_rank_1_brand;
        TV_cosmetic_rank_brand[1] = TV_cosmetic_rank_2_brand;
        TV_cosmetic_rank_brand[2] = TV_cosmetic_rank_3_brand;

        TV_cosmetic_rank_name[0] = TV_cosmetic_rank_1_name;
        TV_cosmetic_rank_name[1] = TV_cosmetic_rank_2_name;
        TV_cosmetic_rank_name[2] = TV_cosmetic_rank_3_name;

        LL_cosmetic_rank[0] = LL_cosmetic_rank_1;
        LL_cosmetic_rank[1] = LL_cosmetic_rank_2;
        LL_cosmetic_rank[2] = LL_cosmetic_rank_3;

        IV_cosmetic_rank[0] = IV_cosmetic_rank_1;
        IV_cosmetic_rank[1] = IV_cosmetic_rank_2;
        IV_cosmetic_rank[2] = IV_cosmetic_rank_3;

        video[0] = video_1; video[1] = video_2;

        IV_recommend_cosmetic_rank[0] = IV_recommend_cosmetic_rank_1;
        IV_recommend_cosmetic_rank[1] = IV_recommend_cosmetic_rank_2;
        IV_recommend_cosmetic_rank[2] = IV_recommend_cosmetic_rank_3;

        TV_recommend_cosmetic_rank_brand[0] = TV_recommend_cosmetic_rank_1_brand;
        TV_recommend_cosmetic_rank_brand[1] = TV_recommend_cosmetic_rank_2_brand;
        TV_recommend_cosmetic_rank_brand[2] = TV_recommend_cosmetic_rank_3_brand;

        TV_recommend_cosmetic_rank_name[0] = TV_recommend_cosmetic_rank_1_name;
        TV_recommend_cosmetic_rank_name[1] = TV_recommend_cosmetic_rank_2_name;
        TV_recommend_cosmetic_rank_name[2] = TV_recommend_cosmetic_rank_3_name;

        LL_recommend_cosmetic[0] = LL_recommend_cosmetic_1;
        LL_recommend_cosmetic[1] = LL_recommend_cosmetic_2;
        LL_recommend_cosmetic[2] = LL_recommend_cosmetic_3;

        TV_user_name.setText(SharedManager.getInstance().getMe().nickname+"님의\n화장대로 이동");
    }


    @Click
    void BT_mypage(){
//        Intent intent = new Intent(activity, CosmeticUploadActivity_1.class);
//        startActivityForResult(intent, Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_REQUEST);
//        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        Intent intent = new Intent(activity, com.soma.beautyproject_android.MyPage.MyPageActivity_.class);
        startActivity(intent);
//        startActivityForResult(intent, Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_REQUEST);
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Click
    void BT_search(){
        Intent intent = new Intent(activity, SearchActivity_.class);
        startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Click
    void LL_cosmetic_rank_1(){

    }

    @Click
    void LL_move_dressing_table(){
        Intent intent = new Intent(activity, DressingTableActivity_.class);
        intent.putExtra("cosmetic_have_number", TV_cosmetic_have_number.getText().toString());
        intent.putExtra("expiration_date_soon", TV_cosmetic_expiration_soon.getText().toString());

        startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

//    @Click({R.id.skin_care,R.id.cleansing,R.id.mask_pack,R.id.suncare,R.id.base_makeup, R.id.eye_makeup,
//            R.id.lip_makeup,R.id.body,R.id.hair, R.id.nail,R.id.perfume,R.id.cosmetic_product,R.id.man})
//    void onClick(View v){
//        goCategoryActivity(v.getId());
//    }

    void conn_get_my_cosmetic_info() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_my_cosmetic_info(SharedManager.getInstance().getMe().id, SharedManager.getInstance().getMe().push_interval)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "conn_get_my_cosmetic_info error", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(final List<Integer> response) {
                        if (response != null) {
                            TV_cosmetic_have_number.setText(response.get(0)+"");
                            TV_cosmetic_expiration_soon.setText(response.get(1)+"");
                        } else{

                        }
                    }
                });
    }

    void connectTestCall_cosmetic_rank() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.ranking_cosmetic_get(SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cosmetic>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_cosmetic_rank error", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(final List<Cosmetic> response) {
                        if (response != null) {
                            for (int index=0;index<response.size();index++){
                                final int i = index;
                                Glide.with(activity).
                                        load(Constants.IMAGE_BASE_URL_cosmetics+response.get(i).img_src).
                                        thumbnail(0.1f).
                                        into(IV_cosmetic_rank[i]);

                                Log.i("img_video","화장품 이미지 주소 : "+Constants.IMAGE_BASE_URL_cosmetics+response.get(i).img_src);
                                TV_cosmetic_rank_brand[i].setText(response.get(i).brand);
                                TV_cosmetic_rank_name[i].setText(response.get(i).product_name);
                                LL_cosmetic_rank[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(activity, DetailCosmeticActivity_.class);
                                        intent.putExtra("cosmetic_id", response.get(i).id);
                                        intent.putExtra("cosmetic_name", response.get(i).product_name);
                                        intent.putExtra("user_id",SharedManager.getInstance().getMe().id);
                                        startActivity(intent);
                                        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                                    }
                                });
                            }
                        } else{
                            Toast.makeText(activity,"화장품 랭킹 서버 오류",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void connectTestCall_cosmetic_recommend() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.recommend_cosmetic_get(SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cosmetic>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_cosmetic_rank error", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(final List<Cosmetic> response) {
                        if (response != null) {
                            for (int index=0;index<response.size();index++){
                                final int i = index;
                                Glide.with(activity).
                                        load(Constants.IMAGE_BASE_URL_cosmetics+response.get(i).img_src).
                                        thumbnail(0.1f).
                                        into(IV_recommend_cosmetic_rank[i]);
                                TV_recommend_cosmetic_rank_brand[i].setText(response.get(i).brand);
                                TV_recommend_cosmetic_rank_name[i].setText(response.get(i).product_name);
                                LL_recommend_cosmetic[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(activity, DetailCosmeticActivity_.class);
                                        intent.putExtra("cosmetic_id", response.get(i).id);
                                        intent.putExtra("cosmetic_name", response.get(i).product_name);
                                        intent.putExtra("user_id",SharedManager.getInstance().getMe().id);
                                        startActivity(intent);
                                        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                                    }
                                });
                            }
                        } else{
                            Toast.makeText(activity,"화장품 추천 서버 오류",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void connectTestCall_video_recommend() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.recommend_video_get(SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Video>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_cosmetic_rank error", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(final List<Video> response) {
                        if (response != null) {
                            for (int index=0;index<response.size();index++){
                                final int i = index;
                                ImageView IV_video_img = (ImageView)video[i].findViewById(R.id.IV_video_img);
                                TextView TV_creator_name = (TextView)video[i].findViewById(R.id.TV_creator_name);
                                TextView TV_video_name = (TextView)video[i].findViewById(R.id.TV_video_name);

                                Glide.with(activity).
                                        load(Constants.IMAGE_BASE_URL_video+response.get(i).thumbnail).
                                        thumbnail(0.1f).
                                        into(IV_video_img);
                                TV_creator_name.setText(response.get(i).youtuber_name);
                                TV_video_name.setText(response.get(i).title);
                                video[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(activity, VideoDetailActivity_.class);
                                        intent.putExtra("video", (Serializable) response.get(i));
                                        startActivity(intent);
                                        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                                    }
                                });
                            }
                        } else{
                            Toast.makeText(activity,"화장품 랭킹 서버 오류",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void connectTestCall_match_user() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.match_user(SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_match_user 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(final List<User> response) {
                        if (response != null) {
                            Log.i("ZXC", "u response.size() : " + response.size());
                            TextView t;

                            if(response.size()>0) {
                                LinearLayout LL_user_info_1 = (LinearLayout) user_1.findViewById(R.id.LL_user_info);
                                LL_user_info_1.setVisibility(View.VISIBLE);
                                user_1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(activity, YourDressingTableActivity_.class);
                                        SharedManager.getInstance().setYou(response.get(0));
                                        activity.startActivity(intent);
                                    }
                                });

                                Glide.with(activity).
                                        load(response.get(0).profile_url).
                                        thumbnail(0.1f).
                                        bitmapTransform(new CropCircleTransformation(activity)).into((ImageView) user_1.findViewById(R.id.IV_user_img));
                                t = (TextView) user_1.findViewById(R.id.TV_user_name);
                                t.setText(response.get(0).nickname);
                                connectTestCall_get_follower_number_1(response.get(0).id, user_1);
                                connectTestCall_get_cosmetic_number_1(response.get(0).id, user_1);
                            }

                            if(response.size()>1) {
                                LinearLayout LL_user_info_2 = (LinearLayout) user_2.findViewById(R.id.LL_user_info);
                                LL_user_info_2.setVisibility(View.VISIBLE);
                                user_2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(activity, YourDressingTableActivity_.class);
                                        SharedManager.getInstance().setYou(response.get(1));
                                        activity.startActivity(intent);
                                    }
                                });

                                Glide.with(activity).
                                        load(response.get(1).profile_url).
                                        thumbnail(0.1f).
                                        bitmapTransform(new CropCircleTransformation(activity)).into((ImageView) user_2.findViewById(R.id.IV_user_img));
                                t = (TextView) user_2.findViewById(R.id.TV_user_name);
                                t.setText(response.get(1).nickname);
                                connectTestCall_get_follower_number_2(response.get(1).id, user_2);
                                connectTestCall_get_cosmetic_number_2(response.get(1).id, user_2);
                            }

                            if(response.size()>2) {
                                LinearLayout LL_user_info_3 = (LinearLayout) user_3.findViewById(R.id.LL_user_info);
                                LL_user_info_3.setVisibility(View.VISIBLE);
                                user_3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(activity, YourDressingTableActivity_.class);
                                        SharedManager.getInstance().setYou(response.get(2));
                                        activity.startActivity(intent);
                                    }
                                });

                                Glide.with(activity).
                                        load(response.get(2).profile_url).
                                        thumbnail(0.1f).
                                        bitmapTransform(new CropCircleTransformation(activity)).into((ImageView) user_3.findViewById(R.id.IV_user_img));
                                t = (TextView) user_3.findViewById(R.id.TV_user_name);
                                t.setText(response.get(2).nickname);
                                connectTestCall_get_follower_number_3(response.get(2).id, user_3);
                                connectTestCall_get_cosmetic_number_3(response.get(2).id, user_3);
                            }

                            // 팔로워 수 불러오기


                            // 보유화장품 수 불러오기



                        } else{

                        }
                    }
                });
    }

    void connectTestCall_match_creator() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.match_creator(SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_match_creator 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(final List<User> response) {
                        if (response != null) {
                            Log.i("ZXC", "c response.size() : " + response.size());

                            TextView t;
                            if(response.size()>0) {
                                LinearLayout LL_user_info_1 = (LinearLayout) creator_1.findViewById(R.id.LL_user_info);
                                LL_user_info_1.setVisibility(View.VISIBLE);
                                creator_1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        Intent intent = new Intent(activity, YourDressingTableActivity_.class);
//                                        SharedManager.getInstance().setYou(response.get(0));
//                                        activity.startActivity(intent);
                                    }
                                });

                                Glide.with(activity).
                                        load(response.get(0).profile_url).
                                        thumbnail(0.1f).
                                        bitmapTransform(new CropCircleTransformation(activity)).into((ImageView) creator_1.findViewById(R.id.IV_user_img));
                                t = (TextView) creator_1.findViewById(R.id.TV_user_name);
                                t.setText(response.get(0).name);
                                connectTestCall_get_follower_number_1(response.get(0).id, creator_1);
                                connectTestCall_get_cosmetic_number_1(response.get(0).id, creator_1);
                            }

                            if(response.size()>1) {
                                LinearLayout LL_user_info_2 = (LinearLayout) creator_2.findViewById(R.id.LL_user_info);
                                LL_user_info_2.setVisibility(View.VISIBLE);
                                creator_2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        Intent intent = new Intent(activity, YourDressingTableActivity_.class);
//                                        SharedManager.getInstance().setYou(response.get(1));
//                                        activity.startActivity(intent);
                                    }
                                });

                                Glide.with(activity).
                                        load(response.get(1).profile_url).
                                        thumbnail(0.1f).
                                        bitmapTransform(new CropCircleTransformation(activity)).into((ImageView) creator_2.findViewById(R.id.IV_user_img));
                                t = (TextView) creator_2.findViewById(R.id.TV_user_name);
                                t.setText(response.get(1).name);
                                connectTestCall_get_follower_number_2(response.get(1).id, creator_2);
                                connectTestCall_get_cosmetic_number_2(response.get(1).id, creator_2);
                            }


                            if(response.size()>2) {
                                LinearLayout LL_user_info_3 = (LinearLayout) creator_3.findViewById(R.id.LL_user_info);
                                LL_user_info_3.setVisibility(View.VISIBLE);
                                creator_3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        Intent intent = new Intent(activity, YourDressingTableActivity_.class);
//                                        SharedManager.getInstance().setYou(response.get(2));
//                                        activity.startActivity(intent);
                                    }
                                });

                                Glide.with(activity).
                                        load(response.get(2).profile_url).
                                        thumbnail(0.1f).
                                        bitmapTransform(new CropCircleTransformation(activity)).into((ImageView) creator_3.findViewById(R.id.IV_user_img));
                                t = (TextView) creator_3.findViewById(R.id.TV_user_name);
                                t.setText(response.get(2).name);
                                connectTestCall_get_follower_number_3(response.get(2).id, creator_3);
                                connectTestCall_get_cosmetic_number_3(response.get(2).id, creator_3);
                            }



                            // 팔로워 수 불러오기




                            // 보유화장품 수 불러오기



                        } else{

                        }
                    }
                });
    }

    void connectTestCall_get_follower_number_1(String user_id, final View view) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_follower_number(user_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Count>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_get_follower_number_1 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Count> response) {
                        if (response != null) {
                            //팔로워 수와 보유화장품 수는 다른 요청으로
                            TextView t = (TextView)view.findViewById(R.id.TV_group_number);
                            t.setText(response.get(0).count);
                        } else{

                        }
                    }
                });
    }

    void connectTestCall_get_follower_number_2(String user_id, final View view) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_follower_number(user_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Count>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_get_follower_number_2 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Count> response) {
                        if (response != null) {
                            //팔로워 수와 보유화장품 수는 다른 요청으로
                            TextView t = (TextView)view.findViewById(R.id.TV_group_number);
                            t.setText(response.get(0).count);
                        } else{

                        }
                    }
                });
    }

    void connectTestCall_get_follower_number_3(String user_id, final View view) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_follower_number(user_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Count>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_get_follower_number_3 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Count> response) {
                        if (response != null) {
                            //팔로워 수와 보유화장품 수는 다른 요청으로
                            TextView t = (TextView)view.findViewById(R.id.TV_group_number);
                            t.setText(response.get(0).count);
                        } else{

                        }
                    }
                });
    }

    void connectTestCall_get_cosmetic_number_1(String user_id, final View view) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_cosmetic_number(user_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Count>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_get_cosmetic_number_1 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Count> response) {
                        if (response != null) {
                            //팔로워 수와 보유화장품 수는 다른 요청으로
                            TextView t = (TextView)view.findViewById(R.id.TV_cosmetic_number);
                            t.setText(response.get(0).count);
                        } else{

                        }
                    }
                });
    }


    void connectTestCall_get_cosmetic_number_2(String user_id, final View view) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_cosmetic_number(user_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Count>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_get_cosmetic_number_2 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Count> response) {
                        if (response != null) {
                            //팔로워 수와 보유화장품 수는 다른 요청으로
                            TextView t = (TextView)view.findViewById(R.id.TV_cosmetic_number);
                            t.setText(response.get(0).count);
                        } else{

                        }
                    }
                });
    }


    void connectTestCall_get_cosmetic_number_3(String user_id, final View view) {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.get_cosmetic_number(user_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Count>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_get_cosmetic_number_3 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Count> response) {
                        if (response != null) {
                            //팔로워 수와 보유화장품 수는 다른 요청으로
                            TextView t = (TextView)view.findViewById(R.id.TV_cosmetic_number);
                            t.setText(response.get(0).count);
                        } else{

                        }
                    }
                });
    }

    void connectTestCall_dressing_table_rank() {
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.dressing_table_rank()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "connectTestCall_dressing_table_rank 에러", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(final List<User> response) {
                        if (response != null) {

                            LinearLayout LL_user_info_1 = (LinearLayout) ranker_1.findViewById(R.id.LL_user_info);
                            LL_user_info_1.setVisibility(View.VISIBLE);
                            LinearLayout LL_user_info_2 = (LinearLayout) ranker_2.findViewById(R.id.LL_user_info);
                            LL_user_info_2.setVisibility(View.VISIBLE);
                            LinearLayout LL_user_info_3 = (LinearLayout) ranker_3.findViewById(R.id.LL_user_info);
                            LL_user_info_3.setVisibility(View.VISIBLE);

                            ranker_1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity, YourDressingTableActivity_.class);
                                    SharedManager.getInstance().setYou(response.get(0));
                                    activity.startActivity(intent);
                                }
                            });
                            Glide.with(activity).
                                    load(response.get(0).profile_url).
                                    thumbnail(0.1f).
                                    bitmapTransform(new CropCircleTransformation(activity)).into((ImageView)ranker_1.findViewById(R.id.IV_user_img));
                            TextView t = (TextView)ranker_1.findViewById(R.id.TV_user_name);
                            t.setText(response.get(0).nickname);

                            ranker_2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity, YourDressingTableActivity_.class);
                                    SharedManager.getInstance().setYou(response.get(1));
                                    activity.startActivity(intent);
                                }
                            });
                            Glide.with(activity).
                                    load(response.get(1).profile_url).
                                    thumbnail(0.1f).
                                    bitmapTransform(new CropCircleTransformation(activity)).into((ImageView)ranker_2.findViewById(R.id.IV_user_img));
                            t = (TextView)ranker_2.findViewById(R.id.TV_user_name);
                            t.setText(response.get(1).nickname);

                            ranker_3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity, YourDressingTableActivity_.class);
                                    SharedManager.getInstance().setYou(response.get(2));
                                    activity.startActivity(intent);
                                }
                            });
                            Glide.with(activity).
                                    load(response.get(2).profile_url).
                                    thumbnail(0.1f).
                                    bitmapTransform(new CropCircleTransformation(activity)).into((ImageView)ranker_3.findViewById(R.id.IV_user_img));
                            t = (TextView)ranker_3.findViewById(R.id.TV_user_name);
                            t.setText(response.get(2).nickname);

                            // 팔로워 수 불러오기
                            connectTestCall_get_follower_number_1(response.get(0).id, ranker_1);
                            connectTestCall_get_follower_number_2(response.get(1).id, ranker_2);
                            connectTestCall_get_follower_number_3(response.get(2).id, ranker_3);

                            // 보유화장품 수 불러오기
                            connectTestCall_get_cosmetic_number_1(response.get(0).id, ranker_1);
                            connectTestCall_get_cosmetic_number_2(response.get(1).id, ranker_2);
                            connectTestCall_get_cosmetic_number_3(response.get(2).id, ranker_3);

                        } else{

                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_CODE_MAIN_FRAGMENT_REFRESH_RESULT) {
           refresh();
        }
    }

    public class Count implements Serializable {
        public String count;

        public Count(){
            this.count = count;

        }
    }

}


