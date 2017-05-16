package com.makejin.beautyproject_android.Login;

import android.widget.Spinner;

import com.makejin.beautyproject_android.ParentActivity;
import com.makejin.beautyproject_android.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@EActivity(R.layout.activity_join)
public class JoinActivity extends ParentActivity {
    JoinActivity activity;

    @ViewById
    Spinner SP_sex,SP_birth;

    @AfterViews
    void afterBindingView() {
        this.activity = this;

        initSexSpinner();
        initYearSpinner();
    }

    private void initYearSpinner() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = thisYear; i>1920; i--){
            years.add(Integer.toString(i));
        }
        setSpinnerAdapter(years,SP_birth);
    }

    private void initSexSpinner() {
        List<String> sexs = new ArrayList<>();
        sexs.add("여자");
        sexs.add("남자");
        setSpinnerAdapter(sexs,SP_sex);
    }

    private void setSpinnerAdapter(List<String> sexs, Spinner spinner) {
        UserInfoSpinnerAdapter adapter = new UserInfoSpinnerAdapter(this, sexs);
        spinner.setAdapter(adapter);
    }
}
