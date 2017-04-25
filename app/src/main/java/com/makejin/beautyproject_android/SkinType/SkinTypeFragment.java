package com.makejin.beautyproject_android.SkinType;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.makejin.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
import com.makejin.beautyproject_android.DressingTable.DressingTableActivity;
import com.makejin.beautyproject_android.DressingTable.DressingTableAdapter;
import com.makejin.beautyproject_android.DressingTable.More.MoreActivity_;
import com.makejin.beautyproject_android.ParentFragment;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.Utils.Constants.Constants;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class SkinTypeFragment extends ParentFragment {
    public static DressingTableActivity activity;


    //index - main_category
    //0-스킨케어
    //1-클렌징
    //2-베이스메이크업
    //3-색조메이크업
    //4-마스크팩
    //5-향수
    //6-기타


    public DressingTableAdapter adapter[] = new DressingTableAdapter[7];

    private RecyclerView recyclerView [] = new RecyclerView[7];

    private int recyclerView_id [] = new int[7];

    public LinearLayout indicator;
    Button BT_cosmetic_upload;

    Button BT_more [] = new Button[7];

    String main_category [] = new String[7];

    ImageView IV_user;

    TextView TV_top_desc, TV_name, TV_id, TV_skin_trouble, TV_skin_type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dressing_table, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final DressingTableActivity dressingTableActivity = (DressingTableActivity) getActivity();
        this.activity = dressingTableActivity;

        BT_cosmetic_upload = (Button) view.findViewById(R.id.BT_cosmetic_upload);
        BT_more[0] = (Button) view.findViewById(R.id.BT_more_skin_care);
        BT_more[1] = (Button) view.findViewById(R.id.BT_more_cleansing);
        BT_more[2] = (Button) view.findViewById(R.id.BT_more_base_makeup);
        BT_more[3] = (Button) view.findViewById(R.id.BT_more_color_makeup);
        BT_more[4] = (Button) view.findViewById(R.id.BT_more_mask_pack);
        BT_more[5] = (Button) view.findViewById(R.id.BT_more_perfume);
        BT_more[6] = (Button) view.findViewById(R.id.BT_more_etc);


        main_category[0] = "스킨케어";
        main_category[1] = "클렌징";
        main_category[2] = "베이스메이크업";
        main_category[3] = "색조메이크업";
        main_category[4] = "마스크팩";
        main_category[5] = "향수";
        main_category[6] = "기타";


        recyclerView_id[0] = R.id.recycler_view_skin_care;
        recyclerView_id[1] = R.id.recycler_view_cleansing;
        recyclerView_id[2] = R.id.recycler_view_base_makeup;
        recyclerView_id[3] = R.id.recycler_view_color_makeup;
        recyclerView_id[4] = R.id.recycler_view_mask_pack;
        recyclerView_id[5] = R.id.recycler_view_perfume;
        recyclerView_id[6] = R.id.recycler_view_etc;


        IV_user = (ImageView) view.findViewById(R.id.IV_user);

        //TV_top_desc = (TextView) view.findViewById(R.id.TV_top_desc);
        TV_name = (TextView) view.findViewById(R.id.TV_name);
        //TV_skin_trouble = (TextView) view.findViewById(R.id.TV_skin_trouble);
        //TV_skin_type = (TextView) view.findViewById(R.id.TV_skin_type);

        String image_url = Constants.IMAGE_BASE_URL_users + SharedManager.getInstance().getMe().profile_url;

        Glide.with(getActivity()).
                load(image_url).
                thumbnail(0.1f).
                into(IV_user);


        TV_top_desc.setText(SharedManager.getInstance().getMe().name + "님이 사용하고 있는 화장품 목록");
        TV_name.setText(SharedManager.getInstance().getMe().name);
        //TV_id.setText(SharedManager.getInstance().getMe().id);
        //TV_skin_trouble.setText(SharedManager.getInstance().getMe().name);
        //TV_skin_type.setText(SharedManager.getInstance().getMe().name);


//        for(int i=0;i<7;i++)
//            connectTestCall(i);

        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        activity.setSupportActionBar(cs_toolbar);
        activity.getSupportActionBar().setTitle("");

//        for(int i=0;i<7;i++){
//            final int temp_i = i;
//            if (recyclerView[temp_i]== null) {
//                recyclerView[temp_i] = (RecyclerView) view.findViewById(recyclerView_id[temp_i]);
//                recyclerView[temp_i].setHasFixedSize(true);
//                recyclerView[temp_i].setLayoutManager(new GridLayoutManager(activity, 4));
//            }
//
//            if (adapter[temp_i] == null) {
//                adapter[temp_i] = new DressingTableAdapter(new DressingTableAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Intent intent = new Intent(activity, DetailCosmeticActivity_.class);
//                        intent.putExtra("cosmetic", adapter[temp_i].mDataset.get(position));
//                        startActivity(intent);
//                        activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
//                    }
//                }, activity, this);
//            }
//            recyclerView[temp_i].setAdapter(adapter[i]);
//
//        }
        indicator = (LinearLayout)view.findViewById(R.id.indicator);

        BT_cosmetic_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CosmeticUploadActivity_1.class);
                startActivityForResult(intent, Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_REQUEST);
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);

            }
        });
        IV_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 사진 바꿀수 있는 페이지
//                Intent intent = new Intent(getActivity(), .class);
//                intent.putExtra("user_id", SharedManager.getInstance().getMe().id);
//                startActivity(intent);
            }
        });

        for(int i=0; i<7; i++){
            final int temp_i = i;
            BT_more[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //이미지 사진 바꿀수 있는 페이지
                    Intent intent = new Intent(getActivity(), MoreActivity_.class);
                    intent.putExtra("main_category_num", temp_i);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void refresh() {
//        for(int i=0;i<7;i++)
//            connectTestCall(i);
    }

    @Override
    public void reload() {
        refresh();
    }

//    void connectTestCall(int main_category_num) {
//        final int temp_main_category_num = main_category_num;
//        LoadingUtil.startLoading(indicator);
//        CSConnection conn = ServiceGenerator.createService(CSConnection.class);
//        conn.myMainCategoryCosmetic(SharedManager.getInstance().getMe().id, main_category[temp_main_category_num])
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<Cosmetic>>() {
//                    @Override
//                    public final void onCompleted() {
//                        LoadingUtil.stopLoading(indicator);
//                    }
//                    @Override
//                    public final void onError(Throwable e) {
//                        e.printStackTrace();
//                        Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public final void onNext(List<Cosmetic> response) {
//                        if (response.size() != 0) {
//                            for (Cosmetic cosmetic : response) {
//                                adapter[temp_main_category_num].addData(cosmetic);
//                            }
//                            adapter[temp_main_category_num].notifyDataSetChanged();
//                        } else {
//                            //Toast.makeText(getActivity(), "데이터 없", Toast.LENGTH_SHORT).show();\
//                        }
//                    }
//                });
//    }
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("makejin", "onActivityResult");
        if (requestCode == Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_REQUEST) {
            if (resultCode == Constants.ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_RESULT) {
                Log.d("makejin", "refresh");
                refresh();
            }
        }
    }
}