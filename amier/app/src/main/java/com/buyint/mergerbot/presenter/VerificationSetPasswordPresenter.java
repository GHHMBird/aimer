package com.buyint.mergerbot.presenter;

import com.buyint.mergerbot.dto.VerifyCodeChangePasswordRequest;
import com.buyint.mergerbot.interfaces.ILoginGetSms;
import com.buyint.mergerbot.interfaces.ILoginGetSmsModel;
import com.buyint.mergerbot.interfaces.IVerifycodeChangePassword;
import com.buyint.mergerbot.interfaces.IVerifycodeChangePasswordModel;
import com.buyint.mergerbot.model.LoginGetSmsModel;
import com.buyint.mergerbot.model.VerifycodeChangePasswordModel;

import io.reactivex.disposables.Disposable;

/**
 * Created by huheming on 2018/7/3
 */

public class VerificationSetPasswordPresenter {

    private IVerifycodeChangePassword iVerifycodeChangePassword;
    private ILoginGetSms iLoginGetSms;
    private ILoginGetSmsModel loginGetSmsModel;
    private IVerifycodeChangePasswordModel verifycodeChangePasswordModel;
    private Disposable subscribe, subscribe2;

    public VerificationSetPasswordPresenter(ILoginGetSms iLoginGetSms, IVerifycodeChangePassword iVerifycodeChangePassword) {
        this.iLoginGetSms = iLoginGetSms;
        this.iVerifycodeChangePassword = iVerifycodeChangePassword;
    }

    public void loginGetSms(String type, String phone) {
        if (loginGetSmsModel == null) {
            loginGetSmsModel = new LoginGetSmsModel();
        }
        subscribe = loginGetSmsModel.loginGetSms(type, phone).subscribe(smsResponse -> {
            if (iLoginGetSms != null) {
                iLoginGetSms.loginGetMsmSuccess(smsResponse);
            }
        }, throwable -> {
            if (iLoginGetSms != null) {
                iLoginGetSms.loginGetMsmFail(throwable);
            }
        });
    }

    public void verifycodeChangePassword(VerifyCodeChangePasswordRequest request) {
        if (verifycodeChangePasswordModel == null) {
            verifycodeChangePasswordModel = new VerifycodeChangePasswordModel();
        }
        subscribe2 = verifycodeChangePasswordModel.verifycodeChangePassword(request).subscribe(booleanResponse -> {
            if (iVerifycodeChangePassword != null) {
                iVerifycodeChangePassword.verifycodeChangePasswordSuccess(booleanResponse);
            }
        }, throwable -> {
            if (iVerifycodeChangePassword != null) {
                iVerifycodeChangePassword.verifycodeChangePasswordFail(throwable);
            }
        });
    }

    public void destory() {
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
        if (iLoginGetSms != null) {
            iLoginGetSms = null;
        }
        if (subscribe2 != null) {
            subscribe2.dispose();
            subscribe2 = null;
        }
        if (iVerifycodeChangePassword != null) {
            iVerifycodeChangePassword = null;
        }
    }
}
