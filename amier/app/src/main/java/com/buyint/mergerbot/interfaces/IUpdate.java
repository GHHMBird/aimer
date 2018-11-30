package com.buyint.mergerbot.interfaces;

import com.buyint.mergerbot.dto.VersionResponse;

/**
 * Created by huheming on 2018/5/24
 */

public interface IUpdate {

    void getUpdateDataSuccess(VersionResponse versionResponse);

    void getUpdateDataFail(Throwable throwable);
}
