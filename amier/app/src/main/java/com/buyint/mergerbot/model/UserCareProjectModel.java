package com.buyint.mergerbot.model;

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse;
import com.buyint.mergerbot.dto.PageRequest;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.interfaces.IUserCareProjectModel;

import io.reactivex.Observable;

/*
 * Created by huheming on 2018/6/7.
 */

public class UserCareProjectModel implements IUserCareProjectModel {

    @Override
    public Observable<MatchCompanyMorePageResponse> getUserCareProject(PageRequest request) {
        return HttpHelper.getMyNoticeList(request);
    }

}
