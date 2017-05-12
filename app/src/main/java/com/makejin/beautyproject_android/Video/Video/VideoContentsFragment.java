package com.makejin.beautyproject_android.Video.Video;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.youtube.player.YouTubePlayerView;
import com.makejin.beautyproject_android.DetailCosmetic.DetailCosmeticActivity_;
import com.makejin.beautyproject_android.Model.Cosmetic;
import com.makejin.beautyproject_android.ParentFragment;
import com.makejin.beautyproject_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class VideoContentsFragment extends ParentFragment {
    public static VideoContentsActivity activity;


    //index - main_category
    //0-스킨케어
    //1-클렌징
    //2-베이스메이크업
    //3-색조메이크업
    //4-마스크팩
    //5-향수
    //6-기타


    public LinearLayout indicator;

    public VideoContentsAdapter adapter;
    public RecyclerView recycler_view;

    public Button BT_share;
    public Button BT_share2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_contents, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final VideoContentsActivity videoContentsActivity = (VideoContentsActivity) getActivity();
        this.activity = videoContentsActivity;

        YouTubePlayerView youTubeView = (YouTubePlayerView) view.findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, activity);

        Toolbar cs_toolbar = (Toolbar)view.findViewById(R.id.cs_toolbar);

        BT_share = (Button) view.findViewById(R.id.BT_share);


        BT_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://13.112.190.217:8888/deeplink";

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(shareIntent, 0);
                if (resInfo.isEmpty()) {
                    Log.i("###", "공유할 수 있는 앱 없음");
                    return;
                }

                List<Intent> targetedShareIntents = new ArrayList<>();

                for (ResolveInfo resolveInfo : resInfo) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    targetedShareIntent.setType("text/plain");

                    // 페이스북, 카카오톡, 카카오 스토리만 표시
                    if (packageName.contains("com.facebook.katana") || packageName.contains("com.kakao.talk") || packageName.contains("com.kakao.story")) {
                        ComponentName name = new ComponentName(packageName, resolveInfo.activityInfo.name);
                        targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
                        targetedShareIntent.setComponent(name);
                        targetedShareIntent.setPackage(packageName);
                        targetedShareIntents.add(targetedShareIntent);
                    }
                }

                if (targetedShareIntents.isEmpty()) {
                    Log.i("###", "공유할 수 있는 앱 없음");
                    return;
                }

                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "공유하기");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[targetedShareIntents.size()]));
                startActivity(chooserIntent);
            }
        });

//        activity.setSupportActionBar(cs_toolbar);
//        activity.getSupportActionBar().setTitle("");

        if (recycler_view == null) {
            recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new GridLayoutManager(activity, 2));
        }
        if (adapter == null) {
            adapter = new VideoContentsAdapter(new VideoContentsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetailCosmeticActivity_.class);
                    intent.putExtra("cosmetic", adapter.mDataset.get(position));
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                }
            }, activity, this);
        }
        recycler_view.setAdapter(adapter);
        indicator = (LinearLayout)view.findViewById(R.id.indicator);


        Cosmetic cosmetic = new Cosmetic();

        adapter.addData(cosmetic);
        adapter.addData(cosmetic);
        adapter.addData(cosmetic);
        adapter.addData(cosmetic);
        adapter.addData(cosmetic);
        adapter.addData(cosmetic);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void refresh() {
        //for(int i=0;i<7;i++)
            //connectTestCall();
    }

    @Override
    public void reload() {
        refresh();
    }

//    void connectTestCall(int main_category_num) {
//        final int temp_main_category_num = main_category_num;
//        LoadingUtil.startLoading(indicator);
//        CSConnection conn = ServiceGenerator.createService(CSConnection.class);
//        conn.myMainCategoryCosmetic(SharedManager.getInstance().getMe().id, main_category[temp_main_category_num], page_num)
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

    /*
    void connectTestCall() {
        LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(CSConnection.class);
        conn.myCosmetic(SharedManager.getInstance().getMe().id)
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
                        Log.i("ZXc", "1");
                        Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Cosmetic> response) {
                        if (response.size() != 0) {
                            int temp_main_category_num = 0;
                            Log.i("ZXC", response.size()+"");
                            for(int i=0;i<7;i++){
                                adapter[i].clear();
                                adapter[i].notifyDataSetChanged();
                            }

                            for (Cosmetic cosmetic : response) {
                                switch (cosmetic.main_category){
                                    case "스킨케어":
                                        temp_main_category_num = 0;
                                        break;
                                    case "클렌징":
                                        temp_main_category_num = 1;
                                        break;
                                    case "베이스메이크업":
                                        temp_main_category_num = 2;
                                        break;
                                    case "색조메이크업":
                                        temp_main_category_num = 3;
                                        break;
                                    case "마스크팩":
                                        temp_main_category_num = 4;
                                        break;
                                    case "향수":
                                        temp_main_category_num = 5;
                                        break;
                                    case "기타":
                                        temp_main_category_num = 6;
                                        break;

                                }
                                adapter[temp_main_category_num].addData(cosmetic);
                                adapter[temp_main_category_num].notifyDataSetChanged();
                            }
                        } else {
                            //Toast.makeText(getActivity(), "데이터 없", Toast.LENGTH_SHORT).show();\
                        }
                    }
                });
    }
*/
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

}