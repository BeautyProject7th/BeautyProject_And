package com.soma.beautyproject_android.DressingTable.CosmeticUpload;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.soma.beautyproject_android.DressingTable.CosmeticInfoRequest.RegistrationRequestActivity_;
import com.soma.beautyproject_android.DressingTable.DressingTableActivity_;
import com.soma.beautyproject_android.Main.MainActivity;
import com.soma.beautyproject_android.Main.MainActivity_;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Search.SearchActivity;
import com.soma.beautyproject_android.Search.SearchActivity_;
import com.soma.beautyproject_android.Search.SearchFragmentCategory;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CosmeticUploadActivity_3 extends ParentActivity {

    public static CosmeticUploadActivity_3 activity;

    private RecyclerView recyclerView;

    public CosmeticUploadAdapter_3 adapter;

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    SwipeRefreshLayout pullToRefresh;

    ImageView IV_brand;

    LinearLayout LL_registration_request;
    TextView TV_main_category, TV_sub_category, TV_brand;
    Button BT_cosmetic_upload;
    Button BT_back, BT_home;
    Button BT_registration_request;

    public int page_num = 1;
    public boolean endOfPage = false;
    public Brand brand;
    public String main_category;
    public String sub_category;

    String before_intent_page;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_upload_3);
        activity = this;

        Toolbar cs_toolbar = (Toolbar)findViewById(R.id.cs_toolbar);

        BT_back = (Button) cs_toolbar.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        BT_home = (Button) cs_toolbar.findViewById(R.id.BT_home);
        BT_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), DressingTableActivity_.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();
            }
        });

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        LL_registration_request = (LinearLayout) findViewById(R.id.LL_registration_request);
        BT_registration_request = (Button) findViewById(R.id.BT_registration_request);


        IV_brand = (ImageView) findViewById(R.id.IV_brand);

        TV_main_category = (TextView) findViewById(R.id.TV_main_category);
        TV_sub_category = (TextView) findViewById(R.id.TV_sub_category);
        TV_brand = (TextView) findViewById(R.id.TV_brand);

        BT_cosmetic_upload = (Button) findViewById(R.id.BT_cosmetic_upload);

        before_intent_page = (String) getIntent().getStringExtra("before_intent_page");
        brand = (Brand) getIntent().getSerializableExtra("brand");
        main_category = (String) getIntent().getStringExtra("main_category");
        sub_category = (String) getIntent().getStringExtra("sub_category");

        TV_main_category.setText(main_category);
        TV_sub_category.setText(sub_category);

        TV_brand.setText(brand.name);

        String image_url = Constants.IMAGE_BASE_URL_brand + brand.logo;

        Glide.with(getApplicationContext()).
                load(image_url).
                thumbnail(0.1f).
                dontTransform().
                diskCacheStrategy(DiskCacheStrategy.SOURCE).
                into(IV_brand);

        if (recyclerView== null) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            //item 사이 간격
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);

                    outRect.right = 10;
                }
            });
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        }

        if (adapter == null) {
            adapter = new CosmeticUploadAdapter_3(new CosmeticUploadAdapter_3.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                }
            }, activity, this);
        }
        recyclerView.setAdapter(adapter);



        BT_cosmetic_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BT_cosmetic_upload.setBackgroundResource(R.drawable.btn_save_press);
                if(adapter.checkedList.size()==0){
                    Toast.makeText(getApplicationContext(), "등록할 화장품을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    //BT_cosmetic_upload.setBackgroundResource(R.drawable.btn_save);
                }
                for(Cosmetic cosmetic : adapter.checkedList){
                    Log.i("zxc", cosmetic.product_name);
                    connectTestCall_myCosmetic_post(cosmetic);
                }
            }
        });

        LL_registration_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestintent = new Intent(getApplicationContext(), RegistrationRequestActivity_.class);
                requestintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(requestintent);
            }
        });

        connectTestCall(brand.name, main_category, sub_category, page_num);

        indicator = (LinearLayout) findViewById(R.id.indicator);


    }


    public void refresh() {

    }

    void connectTestCall(String brand, String main_category, String sub_category, final int page_num) {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        Log.i("soma3201", "zxc : " + brand + "  " +  main_category + " " + sub_category);
        conn.cosmetic(brand, main_category, sub_category, page_num)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Cosmetic>>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Cosmetic> response) {
                        if (response.size() != 0) {
                            for(int i=0;i<response.size();i++){
                                Log.i("zxc", response.get(i).product_name);
                                adapter.addData(response.get(i));
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            endOfPage = true;
                            Toast.makeText(getApplicationContext(), "찾고자하는 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void connectTestCall_myCosmetic_post(Cosmetic cosmetic) {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.myCosmetic_post(cosmetic, SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
                        Intent intent = new Intent(getApplicationContext(), MainActivity_.class);
                        startActivity(intent);
                        //setResult(Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_RESULT);
                        finish();
                    }
                    @Override
                    public final void onError(Throwable e) {
                        LoadingUtil.stopLoading(indicator);
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "이미 등록한 화장품입니다.", Toast.LENGTH_SHORT).show();
                        BT_cosmetic_upload.setBackgroundResource(R.drawable.btn_save);
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        Log.i("Zxc", "response code : " + response.code);
                        if (response.code == 201) {
                            if(response.message.equals("success"))
                                Toast.makeText(getApplicationContext(), "정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            BT_cosmetic_upload.setBackgroundResource(R.drawable.btn_save);
                        }
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    /// EXIT
    @Override
    public void onBackPressed() {
        Log.i("sad", "before_intent_page : " + before_intent_page);
        if(before_intent_page.equals("1")){
            Intent beforeintent = new Intent(getApplicationContext(), CosmeticUploadActivity_2.class);
            beforeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            beforeintent.putExtra("brand", brand);
            beforeintent.putExtra("main_category", main_category);
            beforeintent.putExtra("sub_category", sub_category);
            startActivity(beforeintent);
            finish();
        }else if(before_intent_page.equals("2")){
            Intent beforeintent = new Intent(getApplicationContext(), SearchActivity_.class);
            beforeintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(beforeintent);
            finish();
        }

    }

}
