package com.makejin.beautyproject_and.Model;

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
    public String img_src;

    public Cosmetic(){
        this.id = "id";
        this.product_name = "product_name";
        this.main_category = "main_category";
        this.sub_category = "sub_category";
        this.brand = "brand";
        this.img_src = "http://img.naver.net/static/www/u/2013/0731/nmms_224940510.gif";
    }
}
