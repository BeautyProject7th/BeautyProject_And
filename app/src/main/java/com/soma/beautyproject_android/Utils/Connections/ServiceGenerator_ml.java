package com.soma.beautyproject_android.Utils.Connections;



import android.content.Context;

import com.soma.beautyproject_android.Utils.Connections.Intercepter.AddCookiesInterceptor;
import com.soma.beautyproject_android.Utils.Connections.Intercepter.ReceivedCookiesInterceptor;
import com.soma.beautyproject_android.Utils.Constants.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by kksd0900 on 16. 9. 29..
 */
public class ServiceGenerator_ml {
    public static <S> S createService(Context context, Class<S> serviceClass) {

        /**
         * OkHttpClient 객체 생성 과정
         *
         * 1. OkHttpClient 객체 생성
         * 2. 세션 데이터의 획득을 위해 response 데이터 중 헤더 영역의 쿠키 값을 가로채기 위한 RecivedCookiesInterceptor 추가
         * 3. 서버로 데이터를 보내기 전 세션 데이터 삽입을 위해 AddCookiesInterceptor 추가
         * 4. OkHttpClient 빌드
         *
         * 주의) 가로채기 위한 메소드는 addInterceptor이고 삽입하기 위한 메소드는 addNetworkInterceptor
         */
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .addNetworkInterceptor(new AddCookiesInterceptor(context))
                .readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL_ML)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
