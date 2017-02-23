package com.makejin.beautyproject_and.DetailCosmetic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_and.Model.Cosmetic;
import com.makejin.beautyproject_and.ParentFragment;
import com.makejin.beautyproject_and.R;
import com.makejin.beautyproject_and.Utils.Constants.Constants;
import com.makejin.beautyproject_and.Utils.SharedManager.SharedManager;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class DetailCosmeticFragment extends ParentFragment {
    public static DetailCosmeticActivity activity;

    private RecyclerView.LayoutManager layoutManager;
    public LinearLayout indicator;
    SwipeRefreshLayout pullToRefresh;

    ImageView IV_product;

    TextView TV_top_desc, TV_product_name, TV_main_category, TV_sub_category, TV_brand;

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

        Cosmetic cosmetic = activity.cosmetic;

        IV_product = (ImageView) view.findViewById(R.id.IV_product);

        TV_top_desc = (TextView) view.findViewById(R.id.TV_top_desc);
        TV_product_name = (TextView) view.findViewById(R.id.TV_product_name);
        TV_main_category = (TextView) view.findViewById(R.id.TV_main_category);
        TV_sub_category = (TextView) view.findViewById(R.id.TV_sub_category);
        TV_brand = (TextView) view.findViewById(R.id.TV_brand);

        String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;

        Glide.with(getActivity()).
                load(image_url).
                thumbnail(0.1f).
                into(IV_product);

        TV_top_desc.setText("\""+cosmetic.product_name + "\"의 상세정보");
        TV_product_name.setText(cosmetic.product_name);
        TV_main_category.setText(cosmetic.main_category);
        TV_sub_category.setText(cosmetic.sub_category);
        TV_brand.setText(cosmetic.brand);


        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

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

    }

    @Override
    public void refresh() {

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
}
