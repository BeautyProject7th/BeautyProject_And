package com.soma.beautyproject_android.Login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.soma.beautyproject_android.DressingTable.DressingTableActivity_;
import com.soma.beautyproject_android.Model.GlobalResponse;
import com.soma.beautyproject_android.Model.User;
import com.soma.beautyproject_android.ParentFragment;
import com.soma.beautyproject_android.R;
import com.soma.beautyproject_android.Skin.SkinTroubleActivity_;
import com.soma.beautyproject_android.Skin.SkinTypeActivity_;
import com.soma.beautyproject_android.Utils.Connections.CSConnection;
import com.soma.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.soma.beautyproject_android.Utils.Constants.Constants;
import com.soma.beautyproject_android.Utils.SharedManager.PreferenceManager;
import com.soma.beautyproject_android.Utils.SharedManager.SharedManager;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.flurry.sdk.bh.U;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class LoginFragment extends ParentFragment {
    public static LoginActivity activity;

    public LinearLayout indicator;

    //private SessionCallback callback_kakao;      //콜백 선언


    // view

    private String userName;
    private String userId;
    private String profileUrl;


    //private SessionCallback mKakaocallback;

    private CallbackManager callbackManager = null;
    private AccessTokenTracker accessTokenTracker = null;
    private com.facebook.login.widget.LoginButton loginButton = null;

    Button BT_share2;

    private FacebookCallback<LoginResult> callback_facebook = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            Toast.makeText(getActivity().getApplicationContext(), loginResult.getAccessToken().getUserId(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity().getApplicationContext(), "User sign in canceled!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViewSetting(view);
        return view;
    }

    private void initViewSetting(View view) {
        final LoginActivity loginActivity = (LoginActivity) getActivity();
        this.activity = loginActivity;

        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            // App code
                Log.i("eNuri", "Current Token : " + currentAccessToken);
            }
        };


//
//        Button login_button_facebook = (Button) view.findViewById(R.id.login_button_facebook);
//        login_button_facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("result", "somaa1");
//                //LoginManager - 요청된 읽기 또는 게시 권한으로 로그인 절차를 시작합니다.
//                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
//                Log.i("result", "somaa2");
//                LoginManager.getInstance().registerCallback(callbackManager,
//                        new FacebookCallback<LoginResult>() {
//                            @Override
//                            public void onSuccess(LoginResult loginResult) {
//                                Log.i("result", "somaa4");
//                                Log.e("onSuccess", "onSuccess");
//                                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                                    @Override
//                                    public void onCompleted(JSONObject object, GraphResponse response) {
//                                        Log.v("result", object.toString());
//
//                                        Log.i("result", "somaa5");
//                                        try {
//                                            String email = object.getString("email");       // 이메일
//                                            String name = object.getString("name");         // 이름
//                                            //String gender = object.getString("gender");     // 성별
//                                            String userId = object.getString("id");         //id
//                                            //String birthday = object.getString("birthday");         // 이름
//
//                                            URL url = new URL("https://graph.facebook.com/" + userId + "/picture?type=normal");
//
//                                            Log.i("TAG", "페이스북 아이디 -> " + userId);
//                                            Log.i("TAG", "페이스북 이메일 -> " + email);
//                                            Log.i("TAG", "페이스북 이름 -> " + name);
//                                            //Log.i("TAG", "페이스북 성별 -> " + gender);
//                                            Log.i("TAG", "페이스북 프로필 -> " + url);
//                                            //
//                                            // Log.i("TAG", "페이스북 생일 -> " + birthday);
//
//                                            User tempUser = new User();
//                                            tempUser.name = name;
//                                            tempUser.profile_url = url.toString();
//                                            tempUser.social_type = "페이스북";
//                                            tempUser.id = userId;
//
//
//                                            //SharedManager.getInstance().setMe(tempUser);
//
//                                            connectTestCall_login(tempUser);
//
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//                                Bundle parameters = new Bundle();
//                                parameters.putString("fields", "id,name,email,gender,birthday");
//                                graphRequest.setParameters(parameters);
//                                graphRequest.executeAsync();
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                Log.e("onCancel", "onCancel");
//                            }
//
//                            @Override
//                            public void onError(FacebookException exception) {
//                                Log.e("onError", "onError " + exception.getLocalizedMessage());
//                            }
//                        });
//                Log.i("result", "somaa3");
//            }
//
//        });

        LoginButton login_button_facebook = (LoginButton)view.findViewById(R.id.login_button_facebook);
        login_button_facebook.setReadPermissions("public_profile", "user_friends", "email", "user_birthday");
        login_button_facebook.setFragment(this);
        login_button_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("result", object.toString());
                                try {
                                    String email = object.getString("email");       // 이메일
                                    String name = object.getString("name");         // 이름
                                    //String gender = object.getString("gender");     // 성별
                                    String userId = object.getString("id");         //id
                                    //String birthday = object.getString("birthday");         // 이름

                                    URL url = new URL("https://graph.facebook.com/" + userId + "/picture?type=normal");

                                    Log.i("TAG", "페이스북 아이디 -> " + userId);
                                    Log.i("TAG", "페이스북 이메일 -> " + email);
                                    Log.i("TAG", "페이스북 이름 -> " + name);
                                    //Log.i("TAG", "페이스북 성별 -> " + gender);
                                    Log.i("TAG", "페이스북 프로필 -> " + url);
                                    Log.i("TAG", "push token : "+ FirebaseInstanceId.getInstance().getToken());
                                    //
                                    // Log.i("TAG", "페이스북 생일 -> " + birthday);

                                    Map<String, Object> user = new HashMap<String, Object>();
                                    user.put("id",userId);
                                    user.put("push_token",FirebaseInstanceId.getInstance().getToken());
                                    user.put("social_type","페이스북");
                                    user.put("profile_url",url.toString());
                                    user.put("name",name);
                                    PreferenceManager.getInstance(activity).setPush(true);

                                    connectTestCall_login(user);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
                    }
            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr",error.toString());
            }
        });

        accessTokenTracker.startTracking();

//        callback_kakao = new SessionCallback();                  // 이 두개의 함수 중요함
//        Session.getCurrentSession().addCallback(callback_kakao);


//        Button login_button_kakaotalk = (Button) view.findViewById(R.id.login_button_kakaotalk);
//        login_button_kakaotalk.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                isKakaoLogin();
//            }
//        });

//        com.kakao.usermgmt.LoginButton login_button_kakao = (com.kakao.usermgmt.LoginButton) view.findViewById(R.id.login_button_kakao);
//        login_button_kakao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isKakaoLogin();
//            }
//        });


        indicator = (LinearLayout)view.findViewById(R.id.indicator);

        // 헤쉬키를 가져온다
        //getAppKeyHash();

    }

    @Override
    public void refresh() {
    }

    @Override
    public void reload() {
        refresh();
    }

    void connectTestCall_temp() {
        //LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.oneUser_get("4")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public final void onCompleted() {
                        //LoadingUtil.stopLoading(indicator);
                        // 로그인 성공 후 시작
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(activity, DressingTableActivity_.class);
                                startActivity(intent);
                                activity.finish();
                            }
                        }, 1000);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("zxc", "zzz : ");
                        Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(User response) {
                        if (response != null) {
                            SharedManager.getInstance().setMe(response);
                        } else {
                            Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    private class SessionCallback implements ISessionCallback {
//        @Override
//        public void onSessionOpened() {
//            Log.d("TAG" , "세션 오픈됨");
//            // 사용자 정보를 가져옴, 회원가입 미가입시 자동가입 시킴
//
//            KakaorequestMe();
//        }
//
//        @Override
//        public void onSessionOpenFailed(KakaoException exception) {
//            if(exception != null) {
//                Log.d("TAG" , exception.getMessage());
//                Log.i("ZXc","QWeqweqwe");
//            }
//            Log.i("ZXc","QWeqweqwe2");
//        }
//    }
//    /**
//     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
//     */
//    protected void KakaorequestMe() {
//        UserManagement.requestMe(new MeResponseCallback() {
//            @Override
//            public void onFailure(ErrorResult errorResult) {
//                int ErrorCode = errorResult.getErrorCode();
//                int ClientErrorCode = -777;
//
//                if (ErrorCode == ClientErrorCode) {
//                    Toast.makeText(getActivity(), "카카오톡 서버의 네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.d("TAG" , "오류로 카카오로그인 실패 ");
//                }
//            }
//
//            @Override
//            public void onSessionClosed(ErrorResult errorResult) {
//                Log.d("TAG" , "오류로 카카오로그인 실패 ");
//            }
//
//            @Override
//            public void onSuccess(UserProfile userProfile) {
//                profileUrl = userProfile.getProfileImagePath();
//                userId = String.valueOf(userProfile.getId());
//                userName = userProfile.getNickname();
//
//                User tempUser = new User();
//                tempUser.name = userName;
//                tempUser.profile_url = profileUrl;
//                tempUser.social_type = "카카오톡";
//                tempUser.id = userId;
//                tempUser.push_token = FirebaseInstanceId.getInstance().getToken();
//
//                PreferenceManager.getInstance(activity).setPush(true);
//
//                connectTestCall_login(tempUser);
//
//            }
//
//            @Override
//            public void onNotSignedUp() {
//                // 자동가입이 아닐경우 동의창
//            }
//        });
//    }


    private void getAppKeyHash() {
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.i("hash", something);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
    }

//


    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    void connectTestCall_login(final Map<String, Object> user) {
        Log.i("TAG","-------connectTestCall내부------");
        Log.i("TAG", "페이스북 아이디 -> " + user.get("id"));
        Log.i("TAG", "페이스북 이름 -> " + user.get("name"));
        //Log.i("TAG", "페이스북 성별 -> " + gender);
        Log.i("TAG", "페이스북 프로필 -> " + user.get("profile_url"));
        Log.i("TAG", "push token : "+ user.get("push_token"));
        //LoadingUtil.startLoading(indicator);
        final CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.user_login(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public final void onCompleted() {
                        Log.i("session", "성공");

                        if(PreferenceManager.getInstance(activity).getPush().equals(user.get("push_token"))){
                            //토큰값이 동일하다면 바로 메인으로
                            Log.i("session", "바로 메인으로");
                            goMain();
                        }else {
                            Log.i("session", "push 토큰 값 다름 다시 저장");
                            //토큰값이 다르면 토큰 다시 저장
                            Map tempuser = new HashMap();
                            tempuser.put("id", user.get("id"));
                            tempuser.put("push_token", user.get("push_token"));
                            connect_update_token(tempuser);
                        }
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("session", "서버 문제");
                        Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(User response) {
                        if (response != null) {
                            Log.i("session", "값이 있음");
                            Log.i("user","user_id : "+response.id);
                            PreferenceManager.getInstance(getActivity()).set_id(response.id);
                            SharedManager.getInstance().setMe(response);
                        } else {
                            Log.i("session", "값이 없음");
                            Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
/*
    void connectCategoryCall(final User user) {
        //서버 호출 횟수를 줄이기 위해 카테고리 불러오는 작업은 어플을 처음 실행한 경우에만 불러오도록 한다.
        CSConnection conn = ServiceGenerator.createService(activity,CSConnection.class);
        conn.category()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Category>>() {
                    @Override
                    public final void onCompleted() {
                        if(PreferenceManager.getInstance(activity).getPush().equals(user.push_token)){
                            //토큰값이 동일하다면 바로 메인으로
                            goMain();
                        }else {
                            //토큰값이 다르면 다른거
                            Map tempuser = new HashMap();
                            tempuser.put("user_id", SharedManager.getInstance().getMe().id);
                            tempuser.put("push_token", user.push_token);
                            connect_update_skin_type(tempuser);
                        }
                    }
                    @Override
                    public final void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public final void onNext(List<Category> response) {
                        if (response != null) {
                            Map<String, List<String>> categorylist = new HashMap<String, List<String>>();
                            for(int i=0;i<response.size();i++){
                                categorylist.put(response.get(i).main_category, response.get(i).sub_category);
                            }
                            SharedManager.getInstance().setCategory(categorylist);
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    */

    void connect_update_token(final Map user) {
        Log.i("push", "토큰 값 저장하러 들어옴");
        Log.i("push","token : "+user.get("push_token"));
        Log.i("push","id : "+user.get("id"));

        CSConnection conn = ServiceGenerator.createService(getApplicationContext(),CSConnection.class);
        conn.user_updateToken(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GlobalResponse>() {
                    @Override
                    public final void onCompleted() {
                        Log.i("push","token값 재갱신-완료");
                        goMain();
                    }
                    @Override
                    public final void onError(Throwable e) {
                        Log.i("test","토큰 저장하던 도중 오류");
                        e.printStackTrace();
                    }
                    @Override
                    public final void onNext(GlobalResponse response) {
                        Log.i("push","message : "+response.message);
                        Log.i("push","code 값 : "+response.code);
                        if (response.code == 201) {
                            Log.i("push","token값 재갱신-성공");
                            //Toast.makeText(getApplicationContext(), "정상적으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("push","token값 재갱신-실패");
                            //Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void goMain(){
        Log.i("test","다른데로 이동할라구");
        User me = SharedManager.getInstance().getMe();
        Log.i("test","skin type : "+me.skin_type);
        Log.i("test","skin trouble1 f: "+me.skin_trouble_1);
        Log.i("test","skin trouble2 : "+me.skin_trouble_2);
        Log.i("test","skin trouble3 : "+me.skin_trouble_3);
        if(me.skin_type == null){
            Log.i("test","스킨타입갈꺼야");
            Intent intent = new Intent(activity, SkinTypeActivity_.class);
            intent.putExtra("before_login",true);
            startActivity(intent);
        }
        else if(me.skin_trouble_1 == null){
            Log.i("test","스킨트러블갈꺼야");
            Intent intent = new Intent(activity, SkinTroubleActivity_.class);
            intent.putExtra("before_login",true);
            startActivity(intent);
        }
        else
        {
            Log.i("test","메인으로갈꺼야");
            startActivity(new Intent(activity, DressingTableActivity_.class));
        }
        activity.finish();
    }

}