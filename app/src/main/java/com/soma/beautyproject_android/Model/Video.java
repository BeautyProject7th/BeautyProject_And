package com.soma.beautyproject_android.Model;

import java.io.Serializable;

/**
 * Created by mac on 2017. 2. 20..
 */

public class Video implements Serializable{
    public String id;
    public String video_id;
    public String video_name;
    public String youtuber_name;
    public String upload_date;
    public String description;
    public String thumbnail;
    public int view_cnt;

    public Video(){
        this.id = "id";
        this.video_id = "video_id";
        this.video_name = "video_name";
        this.youtuber_name = "youtuber_name";
        this.upload_date = "upload_date";
        this.description = "description";
        this.thumbnail = "thumbnail";
        this.view_cnt = -1;
    }
}
