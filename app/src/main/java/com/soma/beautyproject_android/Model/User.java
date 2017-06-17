package com.soma.beautyproject_android.Model;

import java.io.Serializable;

/**
 * Created by mijeong on 2016. 10. 4..
 */
public class User implements Serializable {
    //*->어느 정도 중요한 정보
    //**->가입시 반드시 입력받아야하는 정보
    public String id;
    public String nickname;
    public String name;
    public String profile_url;
    public String birthyear;
    public String gender;
    public String social_type;
    public String push_token;
    public String skin_type;
    public String skin_trouble_1;
    public String skin_trouble_2;
    public String skin_trouble_3;
    public String push_interval;



    /*
    public User(){
        this.id = "id";
        this.name = "name";
        this.profile_url = "http://img.naver.net/static/www/u/2013/0731/nmms_224940510.gif";
        this.birthday = "birthday";
        this.gender = "gender";
        this.social_type = "social_type";
        this.skin_type = "skin_type";

    }
    */
}
