package com.soma.beautyproject_android.DetailCosmetic;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.soma.beautyproject_android.Model.Review;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Search.SearchAdapterAutoComplete;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;


import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    TextView TV_expiration_date,TV_purchase_date,TV_rating,TV_review, TV_product_price;

    @ViewById
    RelativeLayout about_me;

    Cosmetic cosmetic = null;
    String cosmetic_id = null;

    Boolean me_flag = null;

    @ViewById
    RatingBar RB_rate;

    @ViewById
    Switch using_switch;

    @ViewById
    LinearLayout LL_adjust, LL_buy, LL_like, LL_add;

    @ViewById
    TextView TV_review_mine;

    @ViewById
    Button BT_delete, BT_pencil;

    @AfterViews
    void afterBindingView() {
        this.activity = this;
        toolbar_title.setText("화장품 상세정보");

        cosmetic_id = getIntent().getStringExtra("cosmetic_id");
        user_id = getIntent().getStringExtra("user_id");
        me_flag = getIntent().getBooleanExtra("me",true);

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
//                Intent intent = new Intent(getApplicationContext(), CosmeticReport_.class);
//                intent.putExtra("cosmetic", cosmetic);
//                startActivity(intent);
            }
        });

        LL_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), CosmeticReport_.class);
//                intent.putExtra("cosmetic", cosmetic);
//                startActivity(intent);
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
        if(me_flag==false) {
            about_me.setVisibility(View.GONE);
        }else{
            conn_get_my_review(cosmetic_id);
        }
        conn_get_review(cosmetic_id, page);
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
                        TV_product_name.setText(cosmetic.product_name);
                        TV_main_category.setText(cosmetic.main_category);
                        TV_sub_category.setText(cosmetic.sub_category);
                        TV_brand.setText(cosmetic.brand);
                        TV_product_price.setText(cosmetic.price+"원");
                        RB_rate.setRating(cosmetic.rate_num);

                        if(cosmetic.review!=null) TV_review.setText(cosmetic.review);

                        if(cosmetic.status == 1) using_switch.setChecked(true);
                        else using_switch.setChecked(false);

//                        if(cosmetic.purchase_date != null){
//                            TV_purchase_date.setText(cosmetic.purchase_date.substring(0,10));
//                        }
//
//                        if(cosmetic.expiration_date != null){
//                            TV_expiration_date.setText(cosmetic.expiration_date.substring(0,10));
//                        }

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

                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Review> response) {
                        if (response.size() != 0) {
                            Review review = response.get(0);
                            TV_review_mine.setText(review.review);
                            TV_rating.setText(String.valueOf(review.rate_num));
                            RB_rate.setRating(Float.valueOf(String.valueOf(review.rate_num)));
                            TV_expiration_date.setText(review.expiration_date);
                            TV_purchase_date.setText(review.purchase_date);
                            BT_delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            BT_pencil.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });

                        } else {
                            endOfPage = true;
                            Toast.makeText(activity, "리뷰가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void conn_get_review(String cosmetic_id, final int page_num) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.get_review(cosmetic_id,page_num)
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
                        if (response != null) {
                            for(int i=0;i<response.size();i++)
                                adapter.addData(response.get(i));
                            adapter.notifyDataSetChanged();
                        } else {
                            endOfPage = true;
                            Toast.makeText(activity, "리뷰가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


