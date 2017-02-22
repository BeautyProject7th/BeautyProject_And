package com.makejin.beautyproject_and.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017. 2. 20..
 */

public class Category {
    public List<String> main_category = new ArrayList<>();
    public List<String> sub_category= new ArrayList<>();

    public Category(List<String> main_category, List<String> sub_category){
        this.main_category = main_category;
        this.sub_category = sub_category;
    }

    public Category(){}
}
