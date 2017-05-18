package com.soma.beautyproject_android.Video.VideoList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.soma.beautyproject_android.ParentActivity;
import com.soma.beautyproject_android.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_video_list)
public class VideoListActivity extends ParentActivity {

    VideoListActivity activity;

    @ViewById
    Toolbar cs_toolbar;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        Fragment fragment = new VideoListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.activity_video_list, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    void refresh() {

    }

    @UiThread
    void uiThread() {

    }

    void connectTestCall() {

    }

}


