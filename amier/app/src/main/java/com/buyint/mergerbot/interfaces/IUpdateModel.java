package com.buyint.mergerbot.interfaces;


import com.buyint.mergerbot.dto.VersionResponse;

import io.reactivex.Observable;

/*
 * Created by huheming on 2018/5/24.
 */

public interface IUpdateModel {

    Observable<VersionResponse> getUpdateData();

}
