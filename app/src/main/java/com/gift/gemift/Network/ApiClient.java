package com.gift.gemift.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://gemift-social-dot-gemift.uw.r.appspot.com/api/";
    private static final String BASE_URL_SHARE_FUND = "https://gemift-money-manager-dot-gemift.uw.r.appspot.com/api/";
    private static final String BASE_URL_OTP = "https://2factor.in/API/V1/b5ec251e-1530-11eb-b380-0200cd936042/SMS/";
    private static final String BASE_URL_OTP_ADDON = "https://2factor.in/API/V1/b5ec251e-1530-11eb-b380-0200cd936042/";
    private static final String BASE_URL_IMAGE_UPLOAD = "https://www.eazypurchaseproducts.com/Easypurchase/api/s3ImgUpload/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit_OTP = null;
    private static Retrofit retrofit_SHARE_FUND = null;
    private static Retrofit retrofit_OTP_ADDON = null;
    private static Retrofit retrofit_IMAGE_UPLOAD = null;


    public static Retrofit getApiClient() {

        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(500000, TimeUnit.SECONDS)
//                .writeTimeout(500000, TimeUnit.SECONDS)
//                .readTimeout(500000, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getApiClientShareFund() {

        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(500000, TimeUnit.SECONDS)
//                .writeTimeout(500000, TimeUnit.SECONDS)
//                .readTimeout(500000, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        if (retrofit_SHARE_FUND == null) {
            retrofit_SHARE_FUND = new Retrofit.Builder()
                    .baseUrl(BASE_URL_SHARE_FUND)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit_SHARE_FUND;
    }

    public static Retrofit getApiClientOTP() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(50000, TimeUnit.SECONDS)
//                .writeTimeout(50000, TimeUnit.SECONDS)
//                .readTimeout(50000, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        if (retrofit_OTP == null) {
            retrofit_OTP = new Retrofit.Builder()
                    .baseUrl(BASE_URL_OTP)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit_OTP;
    }


    public static Retrofit getApiClientOTPAddOn() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(50000, TimeUnit.SECONDS)
//                .writeTimeout(50000, TimeUnit.SECONDS)
//                .readTimeout(50000, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        if (retrofit_OTP_ADDON == null) {
            retrofit_OTP_ADDON = new Retrofit.Builder()
                    .baseUrl(BASE_URL_OTP_ADDON)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit_OTP_ADDON;
    }

    public static Retrofit getApiClientImageUpload() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(50000, TimeUnit.SECONDS)
//                .writeTimeout(50000, TimeUnit.SECONDS)
//                .readTimeout(50000, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        if (retrofit_IMAGE_UPLOAD == null) {
            retrofit_IMAGE_UPLOAD = new Retrofit.Builder()
                    .baseUrl(BASE_URL_IMAGE_UPLOAD)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit_IMAGE_UPLOAD;
    }
}
