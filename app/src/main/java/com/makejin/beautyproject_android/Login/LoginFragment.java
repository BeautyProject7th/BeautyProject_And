package com.makejin.beautyproject_android.Login;

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
import android.widget.LinearLayout;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.auth.AuthType;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.makejin.beautyproject_android.DressingTable.DressingTableActivity_;
import com.makejin.beautyproject_android.Model.DressingTable;
import com.makejin.beautyproject_android.Model.User;
import com.makejin.beautyproject_android.ParentFragment;
import com.makejin.beautyproject_android.R;
import com.makejin.beautyproject_android.SkinTrouble.SkinTroubleActivity_;
import com.makejin.beautyproject_android.SkinType.SkinTypeActivity;
import com.makejin.beautyproject_android.SkinType.SkinTypeActivity_;
import com.makejin.beautyproject_android.Utils.Connections.CSConnection;
import com.makejin.beautyproject_android.Utils.Connections.ServiceGenerator;
import com.makejin.beautyproject_android.Utils.Constants.Constants;
import com.makejin.beautyproject_android.Utils.SharedManager.PreferenceManager;
import com.makejin.beautyproject_android.Utils.SharedManager.SharedManager;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;

import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;


/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class LoginFragment extends ParentFragment {
    public static LoginActivity activity;

    public LinearLayout indicator;

    private SessionCallback callback_kakao;      //콜백 선언


    // view

    private String userName;
    private String userId;
    private String profileUrl;


    private SessionCallback mKakaocallback;

    private CallbackManager callbackManager = null;
    private AccessTokenTracker accessTokenTracker = null;
    private com.facebook.login.widget.LoginButton loginButton = null;

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
//                Log.i("result", "makejina1");
//                //LoginManager - 요청된 읽기 또는 게시 권한으로 로그인 절차를 시작합니다.
//                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
//                Log.i("result", "makejina2");
//                LoginManager.getInstance().registerCallback(callbackManager,
//                        new FacebookCallback<LoginResult>() {
//                            @Override
//                            public void onSuccess(LoginResult loginResult) {
//                                Log.i("result", "makejina4");
//                                Log.e("onSuccess", "onSuccess");
//                                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                                    @Override
//                                    public void onCompleted(JSONObject object, GraphResponse response) {
//                                        Log.v("result", object.toString());
//
//                                        Log.i("result", "makejina5");
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
//                Log.i("result", "makejina3");
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

                                    User tempUser = new User();
                                    tempUser.name = name;
                                    tempUser.profile_url = url.toString();
                                    tempUser.social_type = "페이스북";
                                    tempUser.id = userId;
                                    tempUser.push_token = FirebaseInstanceId.getInstance().getToken();
                                    PreferenceManager.getInstance(activity).setPush(true);

                                    //SharedManager.getInstance().setMe(tempUser);

                                    connectTestCall_login(tempUser);

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

        callback_kakao = new SessionCallback();                  // 이 두개의 함수 중요함
        Session.getCurrentSession().addCallback(callback_kakao);


//        Button login_button_kakaotalk = (Button) view.findViewById(R.id.login_button_kakaotalk);
//        login_button_kakaotalk.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                isKakaoLogin();
//            }
//        });

        com.kakao.usermgmt.LoginButton login_button_kakao = (com.kakao.usermgmt.LoginButton) view.findViewById(R.id.login_button_kakao);
        login_button_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isKakaoLogin();
            }
        });


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

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            Log.d("TAG" , "세션 오픈됨");
            // 사용자 정보를 가져옴, 회원가입 미가입시 자동가입 시킴

            KakaorequestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Log.d("TAG" , exception.getMessage());
                Log.i("ZXc","QWeqweqwe");
            }
            Log.i("ZXc","QWeqweqwe2");
        }
    }
    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void KakaorequestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                int ErrorCode = errorResult.getErrorCode();
                int ClientErrorCode = -777;

                if (ErrorCode == ClientErrorCode) {
                    Toast.makeText(getActivity(), "카카오톡 서버의 네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TAG" , "오류로 카카오로그인 실패 ");
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("TAG" , "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                profileUrl = userProfile.getProfileImagePath();
                userId = String.valueOf(userProfile.getId());
                userName = userProfile.getNickname();

                User tempUser = new User();
                tempUser.name = userName;
                tempUser.profile_url = profileUrl;
                tempUser.social_type = "카카오톡";
                tempUser.id = userId;
                tempUser.push_token = FirebaseInstanceId.getInstance().getToken();

                PreferenceManager.getInstance(activity).setPush(true);

                connectTestCall_login(tempUser);

            }

            @Override
            public void onNotSignedUp() {
                // 자동가입이 아닐경우 동의창
            }
        });
    }


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

    private void isKakaoLogin() {
        // 카카오 세션을 오픈한다
        mKakaocallback = new SessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(mKakaocallback);
        com.kakao.auth.Session.getCurrentSession().checkAndImplicitOpen();
        com.kakao.auth.Session.getCurrentSession().open(AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN, getActivity());
    }

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


    void connectTestCall_login(User user) {
        //LoadingUtil.startLoading(indicator);
        CSConnection conn = ServiceGenerator.createService(activity, CSConnection.class);
        conn.user_login(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public final void onCompleted() {
                        Log.i("zxc", "ggggg : ");

                        User me = SharedManager.getInstance().getMe();
                        Log.i("test","skin type : "+me.skin_type);
                        Log.i("test","skin trouble1 : "+me.skin_trouble_1);
                        Log.i("test","skin trouble2 : "+me.skin_trouble_2);
                        Log.i("test","skin trouble3 : "+me.skin_trouble_3);
                        if(me.skin_type == null){
                            startActivity(new Intent(activity, SkinTypeActivity_.class));
                        }
                        else if(me.skin_trouble_1 == null){
                            startActivity(new Intent(activity, SkinTroubleActivity_.class));
                        }
                        else startActivity(new Intent(activity, DressingTableActivity_.class));
                        activity.finish();
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
                            PreferenceManager.getInstance(getActivity()).set_id(response.id);
                            SharedManager.getInstance().setMe(response);
                        } else {
                            Toast.makeText(activity, Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}