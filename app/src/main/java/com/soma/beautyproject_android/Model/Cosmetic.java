package com.soma.beautyproject_android.Model;

import java.io.Serializable;

/**
 * Created by mac on 2017. 2. 20..
 */

public class Cosmetic implements Serializable{
    public String id;
    public String brand;
    public String main_category;
    public String sub_category;
    public String product_name;
    public String price;
    public String link;
    public String img_src;
    public String review;
    public float rate_num;
    public String expiration_date ;
    public String purchase_date;
    public int status = 1;

//    public Cosmetic(){
//        this.id = "";
//        this.product_name = "";
//        this.main_category = "";
//        this.sub_category = "";
//        this.brand = "";
//        this.price = "";
//        this.price = "";
//        this.img_src = "";
//        this.review = "";
//        this.rate_num = 0;
//        this.expiration_date = "0000-00-00";
//
//    }
}
