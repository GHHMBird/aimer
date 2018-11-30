package com.buyint.mergerbot.interfaces;

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse;
import com.buyint.mergerbot.dto.PageRequest;

import io.reactivex.Observable;

/**
 * Created by huheming on 2018/6/7
 */

public interface IUserCareProjectModel {

    Observable<MatchCompanyMorePageResponse> getUserCareProject(PageRequest request);

}
