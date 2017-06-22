package com.soma.beautyproject_android.DetailCosmetic;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.DressingTable.CosmeticInfoRequest.CosmeticReport;
import com.soma.beautyproject_android.DressingTable.CosmeticInfoRequest.CosmeticReport_;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.RatingEach;
import com.soma.beautyproject_android.Model.Review;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.Model.Video_Youtuber;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Search.SearchAdapterAutoComplete;
import com.soma.beautyproject_android.Search.VideoDetailActivity_;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.flurry.sdk.bh.T;
import static com.flurry.sdk.mt.i;
import static com.soma.beautyproject_android.R.id.BT_like;
import static com.soma.beautyproject_android.R.id.IV_video_img;
import static com.soma.beautyproject_android.R.id.RB_rate;

/**
 * Created by mijeong on 2017. 5. 6..
 */

@EActivity(R.layout.detail_cosmetic_activity)
public class DetailCosmeticActivity extends ParentActivity {
    DetailCosmeticActivity activity;
    String user_id = null;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DetailCosmeticReviewAdapter adapter;
    public int page = 1;
    public boolean endOfPage = false;
    @ViewById
    Toolbar cs_toolbar;

    @ViewById
    ImageView IV_product;

    @ViewById
    TextView TV_product_name,TV_brand,TV_main_category, TV_sub_category,toolbar_title;

    @ViewById
    TextView TV_expiration_date,TV_purchase_date,TV_rating,TV_review, TV_product_price, TV_rating_circle, TV_rating_circle_people;

    TextView [] TV_rate_people;
    View [] V_rate_people;


    @ViewById
    RelativeLayout about_me;

    Cosmetic cosmetic = null;
    String cosmetic_id = null;
    String cosmetic_name = null;

    Boolean me_flag = null;

    @ViewById
    RelativeLayout video_1,video_2;
    RelativeLayout[] videoList = new RelativeLayout[2];

    @ViewById
    RatingBar RB_rate;

    @ViewById
    LinearLayout LL_adjust, LL_buy, LL_like, LL_add;

    @ViewById
    TextView TV_review_mine;


    @ViewById
    Button BT_add;

    @ViewById
    TextView TV_add;

    float sum;

    boolean like_flag = false;

    List<Video_Youtuber> relative_content;

    @ViewById
    Button BT_like;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        toolbar_title.setText("화장품 상세정보");

        cosmetic_id = getIntent().getStringExtra("cosmetic_id");
        cosmetic_name = getIntent().getStringExtra("cosmetic_name");
        user_id = getIntent().getStringExtra("user_id");
        //me_flag = getIntent().getBooleanExtra("me",false);

        TV_rate_people = new TextView[5];

        TV_rate_people[0] = (TextView) findViewById(R.id.TV_rate_1_people);
        TV_rate_people[1] = (TextView) findViewById(R.id.TV_rate_2_people);
        TV_rate_people[2] = (TextView) findViewById(R.id.TV_rate_3_people);
        TV_rate_people[3] = (TextView) findViewById(R.id.TV_rate_4_people);
        TV_rate_people[4] = (TextView) findViewById(R.id.TV_rate_5_people);

        V_rate_people = new View[5];

        V_rate_people[0] = (View) findViewById(R.id.V_rate_1_people);
        V_rate_people[1] = (View) findViewById(R.id.V_rate_2_people);
        V_rate_people[2] = (View) findViewById(R.id.V_rate_3_people);
        V_rate_people[3] = (View) findViewById(R.id.V_rate_4_people);
        V_rate_people[4] = (View) findViewById(R.id.V_rate_5_people);

        videoList[0] = video_1;
        videoList[1] = video_2;

        get_relative_content(user_id,cosmetic_name);

        //상세보기 학습
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("user_id",user_id);
        map.put("cosmetic_name",cosmetic_name);
        conn_train_view_cosmetic(map);

        if (recyclerView == null) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);
        }

        if (adapter == null) {
            adapter = new DetailCosmeticReviewAdapter(new DetailCosmeticReviewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                    ET_search.setText(adapter.getItem(position).toString());
//                    BT_search.callOnClick();
                }
            }, activity, activity);
        }

        recyclerView.setAdapter(adapter);
        LL_adjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CosmeticReport_.class);
                intent.putExtra("cosmetic", cosmetic);
                startActivity(intent);
            }
        });

        LL_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("url", cosmetic.link);
                startActivity(intent);
            }
        });

        LL_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), CosmeticReport_.class);
//                intent.putExtra("cosmetic", cosmetic);
//                startActivity(intent);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("user_id",SharedManager.getInstance().getMe().id);
                map.put("cosmetic_id",cosmetic_id);
                map.put("cosmetic_name",cosmetic.product_name);
                if(!like_flag){
                    conn_post_like_cosmetic(map);
                }else {
                    conn_delete_like_cosmetic(map);
                }

            }
        });

        LL_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyCosmeticActivity_.class);
                intent.putExtra("cosmetic", cosmetic);
                startActivity(intent);
            }
        });

    }

    void refresh(){
        adapter.clear();
        adapter.notifyDataSetChanged();

        connectTestCall_get(cosmetic_id);
        conn_get_my_like_cosmetic(cosmetic_id);
        conn_get_my_review(cosmetic_id);
        conn_get_review(cosmetic_id, page);
        conn_get_each_rating(cosmetic_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
//
//    @Click
//    void BT_modify(){
//        Intent intent = new Intent(activity, ModifyCosmeticActivity_.class);
//        intent.putExtra("cosmetic",cosmetic);
//        startActivity(intent);
//    }

    @Click
    void BT_back(){
        activity.finish();
    }


    void connectTestCall_get(final String cosmetic_id) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.myOneCosmetic_get(user_id, cosmetic_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Cosmetic>() {
                    @Override
                    public final void onCompleted() {
                        String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;

                        Log.i("zxc", "image_url : " + image_url);

                        Glide.with(activity).
                                load(image_url).
                                thumbnail(0.1f).
                                into(IV_product);
                        TV_product_name.setText(cosmetic.product_name.replaceAll(cosmetic.brand,""));
                        TV_main_category.setText(cosmetic.main_category);
                        TV_sub_category.setText(cosmetic.sub_category);
                        TV_brand.setText(cosmetic.brand);
                        TV_product_price.setText(cosmetic.price+"원");
                        TV_rating_circle.setText(cosmetic.rate_num+"");
                        TV_rating_circle_people.setText(cosmetic.rate_people+"명");

                        if(cosmetic.rate_num >0 ){
                            RB_rate.setRating(cosmetic.rate_num);
                            TV_rating.setText(""+cosmetic.rate_num);
                        }
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(Cosmetic response) {
                        if (response != null) {
                            cosmetic = response;
                        } else {
                            Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void conn_get_my_review(String cosmetic_id) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.get_my_review(cosmetic_id, SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Review>>() {
                    @Override
                    public final void onCompleted() {
                        Log.i("ZXC", "conn_get_my_review 2");
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        Log.i("ZXC", "conn_get_my_review 1");
                    }
                    @Override
                    public final void onNext(List<Review> response) {
                        if (response.size() != 0) {
                            me_flag = true;
                            TV_add.setText("변경하기");

                            Review review = response.get(0);

                            Log.i("ZXC", "TV_review_mine : " + TV_review_mine.toString());
                            Log.i("ZXC", "review.expiration_date.toString() " + review.expiration_date.toString());
                            Log.i("ZXC", "review.purchase_date.toString() : " + review.purchase_date.toString());


                            if(review.review == null){
                                TV_review_mine.setText("리뷰 없음");
                            }else{
                                TV_review_mine.setText(review.review);
                            }

                            TV_rating.setText(String.valueOf("("+review.rate_num)+")");
                            RB_rate.setRating(Float.valueOf(String.valueOf(review.rate_num)));
                            if(review.expiration_date != null)
                                TV_expiration_date.setText(review.expiration_date.substring(0,10));
                            else
                                TV_expiration_date.setText("미기재");
                            if(review.purchase_date != null)
                                TV_purchase_date.setText(review.purchase_date.substring(0,10));
                            else
                                TV_purchase_date.setText("미기재");

                        } else {
                            me_flag = false;
                            about_me.setVisibility(View.GONE);
                        }
                    }
                });
    }

    void conn_get_review(String cosmetic_id, final int page_num) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.get_review(cosmetic_id, SharedManager.getInstance().getMe().id, page_num)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Review>>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Review> response) {
                        if (response.size() != 0) {
                            for(int i=0;i<response.size();i++){
                                Review review = response.get(i);
                                if(review.review != null){
                                    adapter.addData(review);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            endOfPage = true;
                        }
                    }
                });
    }


    void conn_get_each_rating(final String cosmetic_id) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.get_each_rating(cosmetic_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RatingEach>>() {
                    @Override
                    public final void onCompleted() {

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<RatingEach> response) {
                        if (response != null) {
                            sum = 0;
                            int rate_num;
                            int rate_people;
                            int maxWidth = 167;
                            float width;
                            for(int i=0;i<response.size();i++){
                                sum += Integer.valueOf(response.get(i).rate_people);
                            }

                            Log.i("zxc", "sum : " + sum);

                            for(int i=0;i<5;i++){
                                TV_rate_people[i].setText("0");
                                V_rate_people[i].setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT));
                            }
                            for(int i=0;i<response.size();i++){
                                rate_num = Integer.valueOf(response.get(i).rate_num);
                                rate_people = Integer.valueOf(response.get(i).rate_people);
                                TV_rate_people[rate_num-1].setText(rate_people+"");
                                //int width = (해당 평점 평가 인원) / (전체 평점 평가 인원) * 100 = ?%;
                                width = (rate_people / sum) * maxWidth;
                                Log.i("zxc", "rate_num : " + rate_num);
                                Log.i("zxc", "rate_people : " + rate_people);
                                Log.i("zxc", "width : " + width);
                                final float scale = getResources().getDisplayMetrics().density;

                                V_rate_people[rate_num-1].setLayoutParams(new LinearLayout.LayoutParams((int)(width * scale), LinearLayout.LayoutParams.MATCH_PARENT));
                            }
                        } else {
                            Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void conn_post_like_cosmetic(Map<String,Object> map) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.post_like_cosmetic(map)
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
                            BT_like.setBackgroundResource(R.drawable.ic_heart);
                            Toast.makeText(activity, "정상적으로 찜 했습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "찜에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    void conn_delete_like_cosmetic(Map<String,Object> fields) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.delete_like_cosmetic(fields)
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
                            BT_like.setBackgroundResource(R.drawable.heart);
                            BT_like.setBackgroundResource(R.drawable.ic_heart_empty);
                            Toast.makeText(activity, "정상적으로 찜을 취소했습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "찜 취소에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void conn_get_my_like_cosmetic(String cosmetic_id) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.get_my_like_cosmetic(SharedManager.getInstance().getMe().id, cosmetic_id)
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
                        Log.i("ZXC", "conn_get_my_like_cosmetic error");
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response.code == 200) { // isLike
                            like_flag = true;
                            BT_like.setBackgroundResource(R.drawable.ic_heart);
                        } else {
                            like_flag = false;
                            BT_like.setBackgroundResource(R.drawable.heart);
                        }
                    }
                });
    }

    void conn_train_view_cosmetic(Map<String,Object> map) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.train_cosmetic_view(map)
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
                            BT_like.setBackgroundResource(R.drawable.ic_heart_empty);
                        }
                    }
                });
    }

    void get_relative_content(String user_id, String cosmetic_name) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.relative_video_get(user_id,cosmetic_name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber <List<Video_Youtuber>>() {
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
                    public final void onNext(final List<Video_Youtuber> response) {
                        if (response != null) { // isLike
                            relative_content = response;

                                for (int index=0;index<2;index++){
                                    final int i = index;
                                    ImageView IV_video_img = (ImageView)videoList[i].findViewById(R.id.IV_video_img);
                                    TextView TV_creator_name = (TextView)videoList[i].findViewById(R.id.TV_creator_name);
                                    TextView TV_video_name = (TextView)videoList[i].findViewById(R.id.TV_video_name);

                                    Glide.with(activity).
                                            load(Constants.IMAGE_BASE_URL_video+response.get(i).thumbnail).
                                            thumbnail(0.1f).
                                            into(IV_video_img);
                                    TV_creator_name.setText(response.get(i).youtuber_name);
                                    TV_video_name.setText(response.get(i).title);
                                    videoList[i].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(activity, VideoDetailActivity_.class);
                                            intent.putExtra("video", (Serializable) response.get(i));
                                            startActivity(intent);
                                            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                                        }
                                    });
                                }



                        } else {
                            Log.i("train","fail");
                        }
                    }
                });
    }
}


