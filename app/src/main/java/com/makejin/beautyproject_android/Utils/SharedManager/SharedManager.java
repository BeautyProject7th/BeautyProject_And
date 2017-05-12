package com.makejin.beautyproject_android.Utils.SharedManager;

import com.makejin.beautyproject_android.Model.Category;
import com.makejin.beautyproject_android.Model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kksd0900 on 16. 10. 16..
 */
public class SharedManager {
    private volatile static SharedManager single;
    private User me;
    private User you;
    private Map<String, List<String>> category = new HashMap<String, List<String>>();
    public static SharedManager getInstance() {

        if (single == null) {
            synchronized(SharedManager.class) {
                if (single == null) {
                    single = new SharedManager();
                }
            }
        }
        return single;
    }

    private SharedManager() {

    }

    public boolean setYou(User response) {
        try {
            this.you = response;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public User getYou() {
        return this.you;
    }


    public boolean setMe(User response) {
        try {
            this.me = response;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateMeSkinType(String skintype) {
        try {
            this.me.skin_type = skintype;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateMeSkinTrouble(String t1,String t2,String t3) {
        try {
            this.me.skin_trouble_1 = t1;
            this.me.skin_trouble_2 = t2;
            this.me.skin_trouble_3 = t3;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public User getMe() {
        return this.me;
    }


    public Map<String, List<String>> getCategory() {
        return this.category;
    }

    public boolean setCategory(Map<String, List<String>> category) {
        try {
            this.category = category;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
