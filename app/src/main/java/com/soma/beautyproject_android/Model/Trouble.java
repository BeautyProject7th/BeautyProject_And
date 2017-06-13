package com.soma.beautyproject_android.Model;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by mac on 2017. 2. 20..
 */

public class Trouble{
    public TextView TV_trouble;
    public String trouble;
    public ImageView IV_trouble;

    public Trouble(TextView textView, String trouble, ImageView imageView){
        this.TV_trouble = textView;
        this.trouble = trouble;
        this.IV_trouble = imageView;
    }
}
