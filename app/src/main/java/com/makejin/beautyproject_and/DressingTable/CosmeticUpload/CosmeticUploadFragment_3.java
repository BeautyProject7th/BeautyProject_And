package com.makejin.beautyproject_and.DressingTable.CosmeticUpload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_and.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_and.DressingTable.CosmeticInfoRequest.RegistrationRequestActivity_;
import com.makejin.beautyproject_and.Model.Brand;
import com.makejin.beautyproject_and.Model.Cosmetic;
import com.makejin.beautyproject_and.Model.GlobalResponse;
import com.makejin.beautyproject_and.ParentFragment;
import com.makejin.beautyproject_and.R;
import com.makejin.beautyproject_and.Utils.Connections.CSConnection;
import com.makejin.beautyproject_and.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_and.Utils.Constants.Constants;
import com.makejin.beautyproject_and.Utils.Loadings.LoadingUtil;
import com.makejin.beautyproject_and.Utils.SharedManager.SharedManager;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class CosmeticUploadFragment_3 extends ParentFragment {
    public static CosmeticUploadActivity activity;

    private RecyclerView recyclerView;

    public CosmeticUploadAdapter_3 adapter;

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    SwipeRefreshLayout pullToRefresh;

    ImageView IV_brand;

    LinearLayout LL_registration_request;
    TextView TV_main_category, TV_sub_category, TV_brand;
    Button BT_cosmetic_upload;
    Button BT_home;
    public int page_num = 1;
    public boolean endOfPage = false;
    public Brand brand;
    public String main_category;
    public String sub_category;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cosmetic_upload_3, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final CosmeticUploadActivity cosmeticUploadActivity = (CosmeticUploadActivity) getActivity();
        this.activity = cosmeticUploadActivity;

        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        BT_home = (Button) cs_toolbar.findViewById(R.id.BT_home);
        BT_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getActivity(), DressingTableActivity_.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        LL_registration_request = (LinearLayout) view.findViewById(R.id.LL_registration_request);

        IV_brand = (ImageView) view.findViewById(R.id.IV_brand);

        TV_main_category = (TextView) view.findViewById(R.id.TV_main_category);
        TV_sub_category = (TextView) view.findViewById(R.id.TV_sub_category);
        TV_brand = (TextView) view.findViewById(R.id.TV_brand);

        BT_cosmetic_upload = (Button) view.findViewById(R.id.BT_cosmetic_upload);


        brand = (Brand) getArguments().getSerializable("brand");
        main_category = (String) getArguments().getString("main_category");
        sub_category = (String) getArguments().getString("sub_category");


        TV_main_category.setText(main_category);
        TV_sub_category.setText(sub_category);

        TV_brand.setText(brand.name);

        String image_url = Constants.IMAGE_BASE_URL_brand + brand.logo;

        Glide.with(getActivity()).
                load(image_url).
                thumbnail(0.1f).
                into(IV_brand);

        if (recyclerView== null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(activity, 4));
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
                    Toast.makeText(getActivity(), "등록할 화장품을 선택해주세요.", Toast.LENGTH_SHORT).show();
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
                Intent requestintent = new Intent(getActivity(), RegistrationRequestActivity_.class);
                requestintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(requestintent);
            }
        });

        connectTestCall(brand.name, main_category, sub_category, page_num);

        indicator = (LinearLayout) view.findViewById(R.id.indicator);

    }

    @Override
    public void refresh() {

    }

    @Override
    public void reload() {
        refresh();
    }

    void connectTestCall(String brand, String main_category, String sub_category, final int page_num) {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        Log.i("makejin3201", "zxc : " + brand + "  " +  main_category + " " + sub_category);
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
                        //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), "찾고자하는 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void connectTestCall_myCosmetic_post(Cosmetic cosmetic) {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.myCosmetic_post(cosmetic, SharedManager.getInstance().getMe().id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
//                        Intent intent = new Intent(getActivity(), DressingTableActivity_.class);
//                        startActivity(intent);
                        getActivity().setResult(Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_RESULT);
                        getActivity().finish();

                    }
                    @Override
                    public final void onError(Throwable e) {
                        LoadingUtil.stopLoading(indicator);
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "이미 등록한 화장품입니다.", Toast.LENGTH_SHORT).show();
                        BT_cosmetic_upload.setBackgroundResource(R.drawable.btn_save);
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        Log.i("Zxc", "response code : " + response.code);
                        if (response.code == 201) {
                            if(response.message.equals("success"))
                                Toast.makeText(getActivity(), "정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
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



}
