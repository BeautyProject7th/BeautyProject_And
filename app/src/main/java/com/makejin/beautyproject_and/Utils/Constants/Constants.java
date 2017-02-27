package com.makejin.beautyproject_and.Utils.Constants;

/**
 * Created by kksd0900 on 16. 9. 29..
 */
public class Constants {
    public static final String API_BASE_URL = "http://13.112.190.217:8888";
    public static final String IMAGE_BASE_URL_cosmetics = API_BASE_URL+"/cosmetics/images/";
    public static final String IMAGE_BASE_URL_users = API_BASE_URL+"/users/images/";
    public static final String IMAGE_BASE_URL_brand = API_BASE_URL+"/brand/images/";

    public static final String ERROR_MSG = "예상치 못한 에러가 발생했습니다 :(\n문제가 지속될 경우 앱을 재실행해주세요.";

    public static final int ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_REQUEST = 1111;
    public static final int ACTIVITY_CODE_DRESSING_TABLE_FRAGMENT_REFRESH_RESULT = 1112;

    public static final String mockMyFriendText(int index) {
        int idx = index;
        while (mockMyFriends.length <= idx) {
            idx -= mockMyFriends.length;
        }
        return mockMyFriends[idx];
    }
    public static final String[] mockMyFriends = {
            "Jung-Ho Seo, 홍미정", "", "권선복", "원응호", "양수길", "", "김태춘", "김태용",
            "권민준", "Eun Ji Son", "이미정", "", "이선향", "정지윤", "박서준", "이기성", "사고몽킼",
            "여효주", "", "이지민", "윤이나", "박진영", "이현일, 박수진", "", "Eun-ji Park",
            "Hansu Kim", "박리세윤", "김이삭", "안성미", "신한재", "윤현웅", "홍주"};
}
