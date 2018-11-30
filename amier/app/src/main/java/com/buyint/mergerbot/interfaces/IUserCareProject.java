package com.buyint.mergerbot.interfaces;

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse;

/**
 * Created by huheming on 2018/6/7
 */

public interface IUserCareProject {

    void getUserCareProjectSuccess(MatchCompanyMorePageResponse response);

    void getUserCareProjectFail(Throwable throwable);

    void getUserCareProjectMoreSuccess(MatchCompanyMorePageResponse matchCompanyMorePageResponse);

    void getUserCareProjectMoreFail(Throwable throwable);

}
