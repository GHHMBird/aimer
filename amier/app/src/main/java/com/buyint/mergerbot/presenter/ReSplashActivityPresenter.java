package com.buyint.mergerbot.presenter;

import com.buyint.mergerbot.interfaces.IUpdate;
import com.buyint.mergerbot.interfaces.IUpdateModel;
import com.buyint.mergerbot.model.UpdateModel;
import io.reactivex.disposables.Disposable;

/*
 * Created by huheming on 2018/5/24.
 */

public class ReSplashActivityPresenter {

    private IUpdate iUpdate;
    private IUpdateModel iUpdateModel;
    private Disposable subscribe;

    public ReSplashActivityPresenter(IUpdate iUpdate) {
        this.iUpdate = iUpdate;
    }

    public void getUpdateData() {
        if (iUpdateModel == null) {
            iUpdateModel = new UpdateModel();
        }
        subscribe = iUpdateModel.getUpdateData().subscribe(versionResponse -> {
            if (iUpdate != null) {
                iUpdate.getUpdateDataSuccess(versionResponse);
            }
        }, throwable -> {
            if (iUpdate != null) {
                iUpdate.getUpdateDataFail(throwable);
            }
        });

    }

    public void destory() {
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
        if (iUpdate != null) {
            iUpdate = null;
        }
        if (iUpdateModel != null) {
            iUpdateModel = null;
        }
        String s = new String();
        s = null;
    }
}
