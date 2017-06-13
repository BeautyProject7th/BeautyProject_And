package com.soma.beautyproject_android.Search;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soma.beautyproject_android.DressingTable.CosmeticUpload.CosmeticUploadActivity_1;
import com.soma.beautyproject_android.Model.Brand;
import com.soma.beautyproject_android.Model.Cosmetic;
import com.soma.beautyproject_android.Model.Video;
import com.soma.beautyproject_android.Model.Youtuber;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Model.Trouble;
import com.soma.beautyproject_android.Utils.Constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.soma.beautyproject_android.R.id.LL_skin_trouble_9;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class SearchAdapterSearchResult extends RecyclerView.Adapter<SearchAdapterSearchResult.ViewHolder> {
    private static final int TYPE_SEARCH_RESULT_BRAND = 0;
    private static final int TYPE_SEARCH_RESULT_COSMETIC_PERFECT = 1;
    private static final int TYPE_SEARCH_RESULT_COSMETIC = 2;
    private static final int TYPE_SEARCH_RESULT_VIDEO = 3;
//    private static final int TYPE_TAIL_SIMILAR = 4;
//    private static final int TYPE_COMMENT = 5;

    public Context context;
    public SearchFragmentSearchResult fragment;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<Brand> mDataset_brand = new ArrayList<>();
    public ArrayList<Cosmetic> mDataset_cosmetic = new ArrayList<>();
    public ArrayList<Video> mDataset_video = new ArrayList<>();
    public ArrayList<Youtuber> mDataset_youtuber = new ArrayList<>();

    //LinearLayout id 저장하는 거임
    List<Integer> user_skin_trouble_list = new ArrayList<Integer>();

    Map<Integer,int[]> skin_trouble_image = new HashMap<Integer, int[]>();
    Map<Integer,Trouble> skin_trouble_list = new HashMap<Integer, Trouble>();

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public SearchAdapterSearchResult(OnItemClickListener onItemClickListener, Context mContext, SearchFragmentSearchResult mFragment) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        fragment = mFragment;
        clear();
    }

    public void clear() {
        mDataset_brand.clear();
        mDataset_cosmetic.clear();
        mDataset_video.clear();
    }

    //brand
    public void addData_brand(Brand brand) {
        mDataset_brand.add(brand);
    }
    public Brand getItem_brand(int position) {
        return mDataset_brand.get(position);
    }
    public void clear_brand() {
        mDataset_brand.clear();
    }


    //cosmetic
    public void addData_cosmetic(Cosmetic cosmetic) {
        mDataset_cosmetic.add(cosmetic);
    }
    public Cosmetic getItem_cosmetic(int position) {
        return mDataset_cosmetic.get(position);
    }
    public void clear_cosmetic() {
        mDataset_cosmetic.clear();
    }


    //video
    public void addData_video(Video video) {
        mDataset_video.add(video);
    }
    public Video getItem_video(int position) {
        return mDataset_video.get(position);
    }
    public void clear_video() {
        mDataset_video.clear();
    }

    //youtuber
    public void addData_youtuber(Youtuber youtuber, int i) {
        mDataset_youtuber.add(i, youtuber);
    }
    public Youtuber getItem_youtuber(int position) {
        return mDataset_youtuber.get(position);
    }
    public void clear_youtuber() {
        mDataset_youtuber.clear();
    }

    @Override
    public SearchAdapterSearchResult.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SEARCH_RESULT_BRAND) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_result_brand, parent, false);
            return new BrandViewHolder(v);
        } else if (viewType == TYPE_SEARCH_RESULT_COSMETIC_PERFECT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_result_cosmetic_perfect, parent, false);
            return new CosmeticPerfectViewHolder(v);
        } else if (viewType == TYPE_SEARCH_RESULT_COSMETIC) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_result_cosmetic, parent, false);
            return new CosmeticViewHolder(v);
        } else if (viewType == TYPE_SEARCH_RESULT_VIDEO) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_result_video, parent, false);
            //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_result_video, parent, false);
            return new VideoViewHolder(v);
        }
//        } else if (viewType == TYPE_TAIL_SIMILAR) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_detail_simiral_tail, parent, false);
//            return new SimiralTailViewHolder(v);
//        } else if (viewType == TYPE_COMMENT) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_detail_comment, parent, false);
//            return new CommentViewHolder(v);
//        }
        View v_temp = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_result_empty, parent, false);
        return new EmptyViewHolder(v_temp);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof BrandViewHolder) {
            BrandViewHolder brandViewHolder = (BrandViewHolder) holder;
            if(mDataset_brand.size() == 0){
                brandViewHolder.cell_search_result_brand.setVisibility(View.GONE);
                return;
            }
            brandViewHolder.cell_search_result_brand.setVisibility(View.VISIBLE);

            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });

            fragment.activity.brand = mDataset_brand.get(0);
            String image_url = Constants.IMAGE_BASE_URL_brand + mDataset_brand.get(0).logo;
            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    into(brandViewHolder.IV_brand);
            brandViewHolder.TV_brand.setText(mDataset_brand.get(0).name);
            brandViewHolder.TV_product_quantity.setText("총 " + "몇" + "개 제품");//:TODO

            brandViewHolder.cell_search_result_brand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment_new = new SearchFragmentSearchResultBrand();
                    FragmentTransaction ft = fragment.getFragmentManager().beginTransaction();
                    ft.replace(R.id.activity_search, fragment_new);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            });

        }else if (holder instanceof CosmeticPerfectViewHolder){
            CosmeticPerfectViewHolder cosmeticPerfectViewHolder = (CosmeticPerfectViewHolder) holder;
            if(mDataset_cosmetic.size() != 1){
                cosmeticPerfectViewHolder.cell_search_result_cosmetic_perfect.setVisibility(View.GONE);
                return;
            }
            cosmeticPerfectViewHolder.cell_search_result_cosmetic_perfect.setVisibility(View.VISIBLE);

            Cosmetic cosmetic_perfect = mDataset_cosmetic.get(0);
            String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic_perfect.img_src;
            Glide.with(context).
                    load(image_url).
                    thumbnail(0.1f).
                    into(cosmeticPerfectViewHolder.IV_cosmetic);
            cosmeticPerfectViewHolder.TV_brand.setText(mDataset_cosmetic.get(0).brand);
            cosmeticPerfectViewHolder.TV_product_name.setText(mDataset_cosmetic.get(0).product_name);
            cosmeticPerfectViewHolder.TV_product_price.setText(mDataset_cosmetic.get(0).price+"원");
            cosmeticPerfectViewHolder.RB_rate.setRating(mDataset_cosmetic.get(0).rate_num);
            cosmeticPerfectViewHolder.TV_rate_num.setText(String.valueOf(mDataset_cosmetic.get(0).rate_num));
            cosmeticPerfectViewHolder.TV_product_review_quantity.setText("(" + "리뷰 개수" +")"); //:TODO
        }else if (holder instanceof CosmeticViewHolder){
            CosmeticViewHolder cosmeticViewHolder = (CosmeticViewHolder) holder;
            if(mDataset_cosmetic.size() == 1){
                cosmeticViewHolder.cell_search_result_cosmetic.setVisibility(View.GONE);
                return;
            }

            if(mDataset_cosmetic.size() == 0){
                //검색결과 없음 페이지 출력
                cosmeticViewHolder.cell_search_result_cosmetic.setVisibility(View.GONE);
                return;
            }

            cosmeticViewHolder.cell_search_result_cosmetic.setVisibility(View.VISIBLE);

            for(int i=0;i<mDataset_cosmetic.size();i++){

                LinearLayout LL_cosmetic = cosmeticViewHolder.LL_cosmetic[i];
                Cosmetic cosmetic = mDataset_cosmetic.get(i);
                String image_url = Constants.IMAGE_BASE_URL_cosmetics + cosmetic.img_src;

                ImageView IV_cosmetic = (ImageView) LL_cosmetic.findViewById(R.id.RR_cosmetic).findViewById(R.id.IV_cosmetic);
                TextView TV_brand = (TextView) LL_cosmetic.findViewById(R.id.TV_cosmetic_brand);
                TextView TV_cosmetic_name = (TextView) LL_cosmetic.findViewById(R.id.TV_cosmetic_name);

                Glide.with(context).
                        load(image_url).
                        thumbnail(0.1f).
                        into(IV_cosmetic);
                TV_brand.setText(mDataset_cosmetic.get(i).brand);
                TV_cosmetic_name.setText(mDataset_cosmetic.get(i).product_name);
            }

            cosmeticViewHolder.BT_cosmetic_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(fragment.getActivity(), CosmeticMoreSearchActivity_.class);
                    intent.putExtra("keyword", fragment.activity.keyword);
                    fragment.startActivity(intent);
                }
            });

        }else if (holder instanceof VideoViewHolder){
            VideoViewHolder videoViewHolder = (VideoViewHolder) holder;

            //videoViewHolder.TV_video_name =

            Video video;
            Youtuber youtuber;

            for(int i=0;i<mDataset_video.size();i++){
                video = mDataset_video.get(i);
                String image_url_youtuber = "";
                int image_url_skin_type = -1;
                int image_url_skin_trouble_1 = -1;
                int image_url_skin_trouble_2 = -1;
                int image_url_skin_trouble_3 = -1;


                Log.i("zxc", "mDataset_youtuber.size() : " + mDataset_youtuber.size());
                Log.i("zxc", "i : " + i);

                if(i < mDataset_youtuber.size()){
                    youtuber = mDataset_youtuber.get(i);
                    image_url_youtuber = youtuber.profile_url;
                    Log.i("zxc", i +" youtuber.skin_type" + youtuber.skin_type);


                    switch(youtuber.skin_type){
                        case "건성":
                            Log.i("zxc", i +" youtuber.skin_type(건성) " + youtuber.skin_type);
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
                }

                //videoViewHolder.TV_video_name.setText(mDataset_video.get(i).video_name);
                //videoViewHolder.TV_view_cnt.setText(video.view_cnt);
                //videoViewHolder.TV_upload_date.setText(video.upload_date);


//                String image_url_skin_type = Constants.IMAGE_BASE_URL_youtuber + y
//                String image_url_skin_trouble_1 = Constants.IMAGE_BASE_URL_youtuber + y
//                String image_url_skin_trouble_2 = Constants.IMAGE_BASE_URL_youtuber + y
//                String image_url_skin_trouble_3 = Constants.IMAGE_BASE_URL_youtuber + y

                String image_url_video = Constants.IMAGE_BASE_URL_video + video.thumbnail;

                Glide.with(context).
                        load(image_url_youtuber).
                        thumbnail(0.1f).
                        into(videoViewHolder.IV_youtuber);
                Glide.with(context).
                        load(image_url_skin_type).
                        thumbnail(0.1f).
                        into(videoViewHolder.IV_skin_type);
                Glide.with(context).
                        load(image_url_skin_trouble_1).
                        thumbnail(0.1f).
                        into(videoViewHolder.IV_skin_trouble_1);
                Glide.with(context).
                        load(image_url_skin_trouble_2).
                        thumbnail(0.1f).
                        into(videoViewHolder.IV_skin_trouble_2);
                Glide.with(context).
                        load(image_url_skin_trouble_3).
                        thumbnail(0.1f).
                        into(videoViewHolder.IV_skin_trouble_3);

                Log.i("ZXc",image_url_video);

                Glide.with(context)
                        .load(image_url_video)
                        .thumbnail(0.1f).
                        into(videoViewHolder.IV_video);

            }

        }
    }


    public class EmptyViewHolder extends ViewHolder {
        public EmptyViewHolder(View v) {
            super(v);
        }
    }

    public class BrandViewHolder extends ViewHolder {
        public TextView TV_brand, TV_product_quantity;
        public ImageView IV_brand;
        public LinearLayout cell_search_result_brand;
        public BrandViewHolder(View v) {
            super(v);
            IV_brand = (ImageView) v.findViewById(R.id.IV_brand);
            TV_brand = (TextView) v.findViewById(R.id.TV_brand);
            TV_product_quantity = (TextView) v.findViewById(R.id.TV_product_quantity);
            cell_search_result_brand = (LinearLayout) v.findViewById(R.id.cell_search_result_brand);
        }
    }

    public class CosmeticPerfectViewHolder extends ViewHolder {
        public ImageView IV_cosmetic;
        public TextView TV_brand, TV_product_name, TV_product_price, TV_rate_num, TV_product_review_quantity;
        public RatingBar RB_rate;
        public LinearLayout cell_search_result_cosmetic_perfect;
        public CosmeticPerfectViewHolder(View v) {
            super(v);
            IV_cosmetic = (ImageView) v.findViewById(R.id.IV_cosmetic);
            TV_brand = (TextView) v.findViewById(R.id.TV_brand);
            TV_product_name = (TextView) v.findViewById(R.id.TV_product_name);
            TV_product_price = (TextView) v.findViewById(R.id.TV_product_price);
            TV_rate_num = (TextView) v.findViewById(R.id.TV_rate_num);
            TV_product_review_quantity = (TextView) v.findViewById(R.id.TV_product_review_quantity);
            RB_rate = (RatingBar) v.findViewById(R.id.RB_rate);
            cell_search_result_cosmetic_perfect = (LinearLayout) v.findViewById(R.id.cell_search_result_cosmetic_perfect);
        }
    }
    public class CosmeticViewHolder extends ViewHolder {
        public Button BT_cosmetic_more;
        public LinearLayout [] LL_cosmetic = new LinearLayout[3];
        public LinearLayout cell_search_result_cosmetic;
        public CosmeticViewHolder(View v) {
            super(v);
            BT_cosmetic_more = (Button) v.findViewById(R.id.BT_cosmetic_more);
            LL_cosmetic[0] = (LinearLayout) v.findViewById(R.id.LL_cosmetic_1);
            LL_cosmetic[1] = (LinearLayout) v.findViewById(R.id.LL_cosmetic_2);
            LL_cosmetic[2] = (LinearLayout) v.findViewById(R.id.LL_cosmetic_3);
            cell_search_result_cosmetic = (LinearLayout) v.findViewById(R.id.cell_search_result_cosmetic);
        }
    }
    public class VideoViewHolder extends ViewHolder {
        public TextView TV_video_name, TV_view_cnt, TV_upload_date, TV_youtuber_name;
        public ImageView IV_youtuber, IV_video, IV_skin_type, IV_skin_trouble_1, IV_skin_trouble_2, IV_skin_trouble_3;

        public VideoViewHolder(View v) {
            super(v);
            TV_video_name = (TextView) v.findViewById(R.id.TV_video_name);
            TV_view_cnt = (TextView) v.findViewById(R.id.LL_video_info).findViewById(R.id.TV_view_cnt);
            TV_upload_date = (TextView) v.findViewById(R.id.TV_upload_date);
            TV_youtuber_name = (TextView) v.findViewById(R.id.TV_youtuber_name);


            IV_youtuber = (ImageView) v.findViewById(R.id.LL_video_total).findViewById(R.id.LL_youtuber_profile).findViewById(R.id.RL_youtuber_image).findViewById(R.id.IV_youtuber);
            IV_video = (ImageView) v.findViewById(R.id.IV_video);
            IV_skin_type = (ImageView) v.findViewById(R.id.IV_skin_type);
            IV_skin_trouble_1 = (ImageView) v.findViewById(R.id.IV_skin_trouble_1);
            IV_skin_trouble_2 = (ImageView) v.findViewById(R.id.IV_skin_trouble_2);
            IV_skin_trouble_3 = (ImageView) v.findViewById(R.id.IV_skin_trouble_3);
//            IV_skin_type = (ImageView) v.findViewById(R.id.LL_video_total).findViewById(R.id.LL_youtuber_profile).findViewById(R.id.LL_youtuber_profile_skin).findViewById(R.id.IV_skin_type);
//            IV_skin_trouble_1 = (ImageView) v.findViewById(R.id.LL_video_total).findViewById(R.id.LL_youtuber_profile).findViewById(R.id.LL_youtuber_profile_skin).findViewById(R.id.IV_skin_trouble_1);
//            IV_skin_trouble_2 = (ImageView) v.findViewById(R.id.LL_video_total).findViewById(R.id.LL_youtuber_profile).findViewById(R.id.LL_youtuber_profile_skin).findViewById(R.id.IV_skin_trouble_2);
//            IV_skin_trouble_3 = (ImageView) v.findViewById(R.id.LL_video_total).findViewById(R.id.LL_youtuber_profile).findViewById(R.id.LL_youtuber_profile_skin).findViewById(R.id.IV_skin_trouble_3);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mDataset_brand.size() != 0)
            return TYPE_SEARCH_RESULT_BRAND;
        else if (position == 1 && mDataset_cosmetic.size() == 1)
            return TYPE_SEARCH_RESULT_COSMETIC_PERFECT;
        else if (position == 2 && mDataset_cosmetic.size() != 1)
            return TYPE_SEARCH_RESULT_COSMETIC;
        else if (position == 3)
            return TYPE_SEARCH_RESULT_VIDEO;
//        else if (position == 4)
//            return TYPE_TAIL_SIMILAR;

        return 999;
    }
    @Override
    public int getItemCount() {
        return 4;
    }


    /*
        ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView;
        }
    }
    public class ItemViewHolder extends ViewHolder {

        public ItemViewHolder(View v) {
            super(v);
        }
    }

}