package com.soma.beautyproject_android.Model;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by mac on 2017. 2. 20..
 */

public class Type{
    public TextView TV_type;
    public TextView TV_explain;
    public String type;
    public ImageView IV_type;

    public Type(TextView tv1, TextView tv2, String type, ImageView imageView){
        this.TV_type = tv1;
        this.TV_explain = tv2;
        this.type = type;
        this.IV_type = imageView;
    }
}
