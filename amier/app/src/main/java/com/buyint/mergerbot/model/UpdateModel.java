package com.buyint.mergerbot.model;

import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.interfaces.IUpdateModel;
import com.buyint.mergerbot.dto.VersionResponse;

import io.reactivex.Observable;

/*
 * 主要是提供数据源
 * Created by huheming on 2018/5/24.
 */

public class UpdateModel implements IUpdateModel{

    @Override
    public Observable<VersionResponse> getUpdateData() {
        return HttpHelper.getVersion();
    }
}
