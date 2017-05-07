package com.makejin.beautyproject_android.DetailCosmetic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.bumptech.glide.Glide;
import com.makejin.beautyproject_android.DressingTable.CosmeticInfoRequest.CosmeticReport_;
import com.makejin.beautyproject_android.Model.Cosmetic;
import com.makejin.beautyproject_android.Model.GlobalResponse;
import com.makejin.beautyproject_android.ParentFragment;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Constants.Constants;
import com.makejin.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import java.util.Date;
import java.text.SimpleDateFormat;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class DetailCosmeticFragment extends ParentFragment {
    public static DetailCosmeticActivity activity;

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;

    ImageView IV_product;

    TextView TV_top_desc, TV_product_name, TV_main_category, TV_sub_category, TV_brand, TV_expiration_date, TV_report;
    EditText ET_review;
    RatingBar RB_rate;
    DatePicker DP_expiration_date;
    Button BT_update, BT_back;
    Switch S_status;
    WheelDatePicker wheelDatePicker;

    Cosmetic cosmetic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_cosmetic, container, false);
        initViewSetting(view);


        return view;
    }

    private void initViewSetting(View view) {
        final DetailCosmeticActivity detailCosmeticActivity = (DetailCosmeticActivity) getActivity();
        this.activity = detailCosmeticActivity;

        Toolbar cs_toolbar = (Toolbar) view.findViewById(R.id.cs_toolbar);

        BT_back = (Button) cs_toolbar.findViewById(R.id.BT_back);
        BT_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        ET_review = (EditText) view.findViewById(R.id.ET_review);
        RB_rate = (RatingBar) view.findViewById(R.id.RB_rate);

        IV_product = (ImageView) view.findViewById(R.id.IV_product);

        //TV_top_desc = (TextView) view.findViewById(R.id.TV_top_desc);
        TV_product_name = (TextView) view.findViewById(R.id.TV_product_name);
        TV_main_category = (TextView) view.findViewById(R.id.TV_main_category);
        TV_sub_category = (TextView) view.findViewById(R.id.TV_sub_category);
        TV_brand = (TextView) view.findViewById(R.id.TV_brand);
        TV_expiration_date = (TextView) view.findViewById(R.id.TV_expiration_date);
        TV_report = (TextView) view.findViewById(R.id.TV_report);
        wheelDatePicker = (WheelDatePicker) view.findViewById(R.id.wheel_date_picker);
        wheelDatePicker.setVisibleItemCount(3);
        wheelDatePicker.setCurtainColor(R.color.colorPurpleLight);
        wheelDatePicker.setSelectedItemTextColor(Color.rgb(0,0,0));

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

        indicator = (LinearLayout)view.findViewById(R.id.indicator);

        IV_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 사진 바꿀수 있는 페이지
//                Intent intent = new Intent(getActivity(), .class);
//                intent.putExtra("user_id", SharedManager.getInstance().getMe().id);
//                startActivity(intent);
            }
        });
        /*
        BT_update = (Button) view.findViewById(R.id.BT_update);
        BT_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cosmetic.review = ET_review.getText().toString();
                cosmetic.rate_num = RB_rate.getRating();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String strDate = sdf.format(new Date());
                if(S_status.isChecked())
                    cosmetic.status = 1;
                else
                    cosmetic.status = 0;

                cosmetic.expiration_date = getExpirationDate(wheelDatePicker.getCurrentYear(), wheelDatePicker.getCurrentMonth(), wheelDatePicker.getCurrentDay());

                Log.i("m", "cosmetic.status : " + cosmetic.status);
                connectTestCall_post(cosmetic);
            }
        });
        */

        S_status = (Switch) view.findViewById(R.id.S_status);
    }

    @Override
    public void refresh() {
        connectTestCall_get();
    }

    @Override
    public void reload() {
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    void connectTestCall_get() {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.myOneCosmetic_get(SharedManager.getInstance().getMe().id, activity.cosmetic_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Cosmetic>() {
                    @Override
                    public final void onCompleted() {
                        LoadingUtil.stopLoading(indicator);
                        Log.i("Zxc", "status : " + cosmetic.status);

                        String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;

                        Glide.with(getActivity()).
                                load(image_url).
                                thumbnail(0.1f).
                                into(IV_product);

                        RB_rate.setRating(cosmetic.rate_num);
                        ET_review.setText(cosmetic.review);

                        //TV_top_desc.setText("\""+cosmetic.product_name + "\"의 상세정보");
                        TV_product_name.setText(cosmetic.product_name);
                        TV_main_category.setText(cosmetic.main_category);
                        TV_sub_category.setText(cosmetic.sub_category);
                        TV_brand.setText(cosmetic.brand);


                        if(cosmetic.status == 1)
                            S_status.setChecked(true);
                        else
                            S_status.setChecked(false);

                        if(cosmetic.expiration_date == null){
                            TV_expiration_date.setText("유통기한 : " + "설정해주세요");

                            Toast.makeText(getActivity(), "유통기한을 설정해주세요.", Toast.LENGTH_SHORT).show();
                            long now = System.currentTimeMillis();
                            Date currentDate = new Date(now);

                            SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
                            SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
                            SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");

                            String strCurYear = CurYearFormat.format(currentDate);
                            String strCurMonth = CurMonthFormat.format(currentDate);
                            String strCurDay = CurDayFormat.format(currentDate);

                            wheelDatePicker.setSelectedDay(Integer.parseInt(strCurDay));
                            wheelDatePicker.setSelectedMonth(Integer.parseInt(strCurMonth));
                            wheelDatePicker.setSelectedYear(Integer.parseInt(strCurYear));
                        }else{
                            TV_expiration_date.setText("유통기한 : " + cosmetic.expiration_date.substring(0,10));

                            wheelDatePicker.setSelectedYear(Integer.parseInt(cosmetic.expiration_date.substring(0,4)));
                            wheelDatePicker.setSelectedMonth(Integer.parseInt(cosmetic.expiration_date.substring(5,7)));
                            wheelDatePicker.setSelectedDay(Integer.parseInt(cosmetic.expiration_date.substring(8,10)));
                        }

                        TV_report.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), CosmeticReport_.class);
                                intent.putExtra("cosmetic", cosmetic);
                                startActivity(intent);
                            }
                        });
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
                            Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public String getExpirationDate(int year, int month, int day){
        String tempMonth = "";
        String tempDay = "";
        //month+=1;

        if(month<10)
            tempMonth = "0" + month;
        else
            tempMonth = ""+month;

        if(day<10)
            tempDay = "0" + day;
        else
            tempDay = ""+day;

        return year +"-" + tempMonth + "-" + tempDay;
    }
}
