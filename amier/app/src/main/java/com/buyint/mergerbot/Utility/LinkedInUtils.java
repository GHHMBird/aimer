package com.buyint.mergerbot.Utility;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.buyint.mergerbot.dto.CompanyNamePositionBean;
import com.buyint.mergerbot.dto.LinkedinRequest;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.AccessToken;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by huheming on 2018/7/3
 */

public class LinkedInUtils {

    private static String url = "https://api.linkedin.com/v1/people/~:(formatted-name,location,industry,num-connections,id,email-address,summary,positions,picture-url)?";

    public static void linkedinLogin(Context context, LinkedinUtilsListener listener) {
        LISessionManager.getInstance(context.getApplicationContext()).init((Activity) context, Scope.build(Scope.R_BASICPROFILE, Scope.RW_COMPANY_ADMIN, Scope.R_EMAILADDRESS, Scope.W_SHARE), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                //初始化linkedin成功
                //获取用户所有信息
                APIHelper.getInstance(context.getApplicationContext()).getRequest(context, url, new ApiListener() {
                    @Override
                    public void onApiSuccess(ApiResponse apiResponse) {
                        AccessToken accessToken = LISessionManager.getInstance(context.getApplicationContext()).getSession().getAccessToken();
                        LinkedinRequest req = new LinkedinRequest();
                        req.position = new ArrayList<>();
                        try {
                            JSONObject json = apiResponse.getResponseDataAsJson();
                            req.emailAddress = json.optString("emailAddress");
                            req.formattedName = json.optString("formattedName");
                            req.accessToken = accessToken.getValue();
                            req.industry = json.optString("industry");
                            req.linkedInId = json.optString("id");
                            JSONObject location = (JSONObject) json.get("location");
                            req.location = location.optString("name");
                            JSONObject positions = (JSONObject) json.get("positions");
                            JSONArray value = (JSONArray) positions.get("values");
                            for (int i = 0; i < value.length(); i++) {
                                CompanyNamePositionBean bean = new CompanyNamePositionBean();
                                JSONObject valueBean = (JSONObject) value.get(i);
                                JSONObject company = (JSONObject) valueBean.get("company");
                                bean.companyName = company.optString("name");
                                bean.position = valueBean.optString("title");
                                req.position.add(bean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listener.success(req);
                    }

                    @Override
                    public void onApiError(LIApiError LIApiError) {
                        //获取用户信息失败
                        listener.fail();
                    }
                });
            }

            @Override
            public void onAuthError(LIAuthError error) {
                //初始化linkedin失败
                listener.fail();
            }
        }, true, (dialog, which) -> listener.fail(), (dialog, which) -> listener.fail());
    }

    public interface LinkedinUtilsListener {
        void success(LinkedinRequest req);

        void fail();
    }
}
