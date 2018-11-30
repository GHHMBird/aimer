package com.buyint.mergerbot.model;

import com.buyint.mergerbot.dto.GetUserProjectPerformanceListRequest;
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListResponse;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.interfaces.IGetUserProjectPerformanceListModel;

import io.reactivex.Observable;

/**
 * Created by huheming on 2018/7/20
 */

public class GetUserProjectPerformanceListModel implements IGetUserProjectPerformanceListModel {

    public Observable<GetUserProjectPerformanceListResponse> getUserProjectPerformanceList(GetUserProjectPerformanceListRequest request) {
        return HttpHelper.getUserProjectPerformanceList(request);
    }
}
