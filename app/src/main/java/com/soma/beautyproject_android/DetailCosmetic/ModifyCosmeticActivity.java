package com.soma.beautyproject_android.DetailCosmetic;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.DressingTable;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.Loadings.LoadingUtil;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.format;
import static com.soma.beautyproject_android.R.id.RB_rate;
import static com.soma.beautyproject_android.R.id.TV_product_name;
import static com.soma.beautyproject_android.R.id.TV_review_mine;

/**
 * Created by mijeong on 2017. 5. 7..
 */


@EActivity(R.layout.upload_cosmetic_activity)
public class ModifyCosmeticActivity extends ParentActivity {
    public String status = "0";
    public String cosmetic_id = null;
    private Cosmetic cosmetic = null;
    private String purchase_date = null, expiration_date = null;

    ModifyCosmeticActivity activity;

    @ViewById
    TextView TV_main_category,TV_sub_category,TV_brand,TV_cosmetic;

    @ViewById
    ImageView IV_product;

    @ViewById
    RatingBar RB_rate;

    @ViewById
    EditText ET_review;

    //TODO: Y,M,D 없애려면 picker각자 해야할 듯..?
    @ViewById
    WheelDatePicker purchase_date_picker,expiration_date_picker;

    @ViewById
    Switch using_switch;

    @Click
    void BT_complete(){
        float rating_num = RB_rate.getRating();
        String review = ET_review.getText().toString();

        Map<String, Object> fields = new HashMap<String, Object>();
        if(rating_num == 0){
            Toast.makeText(activity, "평점을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        fields.put("rate_num",RB_rate.getRating());
        //if(review!=null)
        if(review.equals("")){
            Toast.makeText(activity, "리뷰를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }


        fields.put("review",review);
        //if(purchase_date!=null)
        fields.put("purchase_date",purchase_date);
        //if(expiration_date!=null)
        fields.put("expiration_date",expiration_date);

        fields.put("user_id",SharedManager.getInstance().getMe().id);
        fields.put("cosmetic_id",cosmetic_id);
        fields.put("cosmetic_name",cosmetic.product_name);
        fields.put("status",1);
        connect_cosmetic_update(fields);
    }

    @Click
    void BT_back(){
        activity.finish();
    }

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        cosmetic = (Cosmetic) getIntent().getSerializableExtra("cosmetic");
        cosmetic_id = cosmetic.id;

        String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;

        Glide.with(activity).
                load(image_url).
                thumbnail(0.1f).
                dontTransform().
                diskCacheStrategy(DiskCacheStrategy.SOURCE).
                into(IV_product);
        TV_cosmetic.setText(cosmetic.product_name);
        TV_main_category.setText(cosmetic.main_category);
        TV_sub_category.setText(cosmetic.sub_category);
        TV_brand.setText(cosmetic.brand);
        RB_rate.setRating(0);

        RB_rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                                  boolean fromUser) {
                if(rating<1.0f)
                    ratingBar.setRating(1.0f);
            }
        });
//        if(cosmetic.expiration_date!=null){
//            Toast.makeText(activity,"유통기한 일 : "+cosmetic.expiration_date,Toast.LENGTH_SHORT).show();
//            expiration_date_picker.setSelectedYear(Integer.parseInt(cosmetic.expiration_date.substring(0,4)));
//            expiration_date_picker.setSelectedMonth(Integer.parseInt(cosmetic.expiration_date.substring(5,7)));
//            expiration_date_picker.setSelectedDay(Integer.parseInt(cosmetic.expiration_date.substring(8,10)));
//        }
//        if(cosmetic.purchase_date!=null){
//            purchase_date_picker.setSelectedYear(Integer.parseInt(cosmetic.expiration_date.substring(0,4)));
//            purchase_date_picker.setSelectedMonth(Integer.parseInt(cosmetic.expiration_date.substring(5,7)));
//            purchase_date_picker.setSelectedDay(Integer.parseInt(cosmetic.expiration_date.substring(8,10)));
//        }

        Date now = new Date();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        purchase_date = format.format(now);
        try {
            c.setTime(format.parse(purchase_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.add(Calendar.DATE, 180); // 180 = value(6 month) to value(day)
        Date resultdate = new Date(c.getTimeInMillis());
        expiration_date = format.format(resultdate);

        purchase_date_picker.setVisibleItemCount(3);
        //purchase_date_picker.setItemTextSize(55);
        purchase_date_picker.setTypeface(Typeface.createFromAsset(getAssets(), "NanumSquareOTFRegular.otf"));
        purchase_date_picker.setSelectedItemTextColor(getResources().getColor(R.color.colorPrimary));
        purchase_date_picker.setCurved(true);
        //purchase_date_picker.setItemSpace(70);
        purchase_date_picker.setOnDateSelectedListener(new WheelDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(WheelDatePicker picker, Date date) {
                purchase_date = format.format(date);
            }
        });

        expiration_date_picker.setYear(Integer.valueOf(expiration_date.substring(0,4)));
        expiration_date_picker.setMonth(Integer.valueOf(expiration_date.substring(5,7)));
        expiration_date_picker.setSelectedDay(Integer.valueOf(expiration_date.substring(8,10)));

        Log.i("Zxc", "Integer.valueOf(format.format(now).substring(0,4)) : " + Integer.valueOf(format.format(now).substring(0,4)));
        Log.i("Zxc", "expiration_date_picker.getMonth()+3 : " + expiration_date_picker.getMonth()+3);
        Log.i("Zxc", "Integer.valueOf(format.format(now).substring(8,10)) : " + Integer.valueOf(format.format(now).substring(8,10)));



        expiration_date_picker.setVisibleItemCount(3);
        //expiration_date_picker.setItemTextSize(55);
        expiration_date_picker.setTypeface(Typeface.createFromAsset(getAssets(), "NanumSquareOTFRegular.otf"));
        expiration_date_picker.setSelectedItemTextColor(getResources().getColor(R.color.colorPrimary));
        expiration_date_picker.setCurved(true);
        //expiration_date_picker.setItemSpace(70);
        expiration_date_picker.setOnDateSelectedListener(new WheelDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(WheelDatePicker picker, Date date) {
                expiration_date = format.format(date);
            }
        });


        using_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    status = "1";
                else
                    status = "0";
            }
        });
    }

    void refresh(){
        conn_get_my_cosmetic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    void connect_cosmetic_update(Map<String, Object> cosmetic) {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.myOneCosmetic_post(status, cosmetic)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        activity.finish();
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("cer",e.toString());
                        Toast.makeText(activity, "서버 오류", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        if (response.code == 200 || response.code == 201) {
                            Toast.makeText(activity, "정상적으로 수정되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    void conn_get_my_cosmetic() {
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.my_cosmetic_get(SharedManager.getInstance().getMe().id, cosmetic_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DressingTable>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("cer",e.toString());
                        Toast.makeText(activity, "서버 오류", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<DressingTable> response) {
                        if (response.size() != 0) {
                            DressingTable dt = response.get(0);

                            using_switch.setChecked(dt.status);
                            RB_rate.setRating(Float.valueOf(dt.rate_num+""));

                            purchase_date = dt.purchase_date.substring(0,4)+"-"+purchase_date.substring(5,7)+"-"+dt.purchase_date.substring(8,10);
                            expiration_date = dt.expiration_date.substring(0,4)+"-"+dt.expiration_date.substring(5,7)+"-"+dt.expiration_date.substring(8,10);

                            purchase_date_picker.setYear(Integer.valueOf(dt.purchase_date.substring(0,4)));
                            purchase_date_picker.setMonth(Integer.valueOf(dt.purchase_date.substring(5,7)));
                            purchase_date_picker.setSelectedDay(Integer.valueOf(dt.purchase_date.substring(8,10)));

                            expiration_date_picker.setYear(Integer.valueOf(dt.expiration_date.substring(0,4)));
                            expiration_date_picker.setMonth(Integer.valueOf(dt.expiration_date.substring(5,7)));
                            expiration_date_picker.setSelectedDay(Integer.valueOf(dt.expiration_date.substring(8,10)));

                            ET_review.setText(dt.review);
                        } else {
                            //Toast.makeText(activity, "수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


