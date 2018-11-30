package com.buyint.mergerbot.http;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.loginAndRegister.activity.LoginActivity;
import com.buyint.mergerbot.UIs.main.activity.SplashActivity;
import com.buyint.mergerbot.base.AppApplication;
import com.buyint.mergerbot.database.DBUtilsKt;
import com.buyint.mergerbot.dto.LoginResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private Retrofit retrofit;

    public RetrofitClient(String url) {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .client(getHttpClient())
                .build();
    }

    public InterfaceService getInterfaceService() {
        return retrofit.create(InterfaceService.class);
    }

    /*
     * 自定义OkHttpClient 设置超时和LOG
     *
     */
    private OkHttpClient getHttpClient() {
        String token = "";
        LoginResponse response = DBUtilsKt.getLoginResponse(AppApplication.context);
        if (response != null && response.model != null && !TextUtils.isEmpty(response.model.token)) {
            token = response.model.token;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        String finalToken = token;
        Log.d("OkHttp", finalToken);
        return new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("accessToken", finalToken)
                            .addHeader("Authorization", "Bearer " + finalToken)
                            .addHeader("language", AppApplication.language)
                            .build();
                    Response proceed = chain.proceed(request);
                    int code = proceed.code();
                    if (code == 401) {

                        JPushInterface.setAlias(AppApplication.context, 1, "");
                        DBUtilsKt.clearLoginResponse(AppApplication.context);

                        //登录超时
                        MaterialDialog.Builder builder = new MaterialDialog.Builder(AppApplication.context);
                        builder.positiveText(R.string.alright);
                        builder.negativeText(R.string.cancel);
                        builder.positiveColorRes(R.color.colorBlack);
                        builder.negativeColorRes(R.color.colorBlack);
                        builder.content(R.string.login_out);
                        builder.onNegative((dialog, which) -> {
                            dialog.dismiss();
                            AppApplication.context.startActivity(new Intent(AppApplication.context, SplashActivity.class));
                        });
                        builder.onPositive((dialog, which) -> {
                            dialog.dismiss();
                            AppApplication.context.startActivity(new Intent(AppApplication.context, LoginActivity.class));
                        });
                        builder.build().show();
                    }
                    return proceed;
                })
                .retryOnConnectionFailure(true)
                .sslSocketFactory(getSslScoketFactory())
                .hostnameVerifier(getHostnameVerifier())
                .build();
    }

    private HostnameVerifier getHostnameVerifier() {
        return (hostname, session) -> true;
    }


    private SSLSocketFactory getSslScoketFactory() {

        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext.getSocketFactory();
    }

    /*
     * 接口数据转换器，该转换器不做任何转换，直接输出JSON字符串
     */
    private static class RawJsonConverterFactory extends Converter.Factory {

        public static RawJsonConverterFactory create() {
            return new RawJsonConverterFactory();
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return new RawJsonResponseBodyConverter();
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
        }

        @Override
        public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return super.stringConverter(type, annotations, retrofit);
        }
    }


    private static class RawJsonResponseBodyConverter<String> implements Converter<ResponseBody, String> {

        public RawJsonResponseBodyConverter() {

        }

        @Override
        public String convert(ResponseBody value) throws IOException {

            return (String) value.string();
        }
    }
}
