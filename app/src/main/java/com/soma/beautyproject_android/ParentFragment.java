package com.soma.beautyproject_android;

import android.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by kksd0900 on 16. 8. 29..
 */
public abstract class ParentFragment extends Fragment {
    public abstract void reload();
    public abstract void refresh();

    public void willBeDisplayed() {
        fadeInAnim(this.getView());
    }

    void fadeInAnim(final View view) {
        final Animation animationFadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        view.startAnimation(animationFadeOut);
    }
}
