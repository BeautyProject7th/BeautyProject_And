//package com.makejin.beautyproject_and.Login;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.makejin.beautyproject_and.R;
//
//public class LoginTempActivity extends AppCompatActivity {
//
//    private TextView tv_user_id;
//    private TextView tv_user_name;
//    private ImageView iv_user_profile;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_temp);
//        tv_user_id = (TextView) view.findViewById(R.id.tv_user_id);
//        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
//        iv_user_profile = (ImageView) view.findViewById(R.id.iv_user_profile);
//
//        setLayoutText();
//
//    }
//
//    private void setLayoutText(){
//        tv_user_id.setText(userId);
//        tv_user_name.setText(userName);
//
//        Glide.with(this)
//                .load(profileUrl)
//                .thumbnail(0.1f)
//                .into(iv_user_profile);
//    }
//
//}
