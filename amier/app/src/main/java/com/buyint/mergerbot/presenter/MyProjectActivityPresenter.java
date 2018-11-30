package com.buyint.mergerbot.presenter;

import com.buyint.mergerbot.dto.PageRequest;
import com.buyint.mergerbot.interfaces.IGetMyMatchList;
import com.buyint.mergerbot.interfaces.IGetMyMatchListModel;
import com.buyint.mergerbot.interfaces.IUserCareProject;
import com.buyint.mergerbot.interfaces.IUserCareProjectModel;
import com.buyint.mergerbot.interfaces.IUserPublishProject;
import com.buyint.mergerbot.interfaces.IUserPublishProjectModel;
import com.buyint.mergerbot.model.GetMyMatchListModel;
import com.buyint.mergerbot.model.UserCareProjectModel;
import com.buyint.mergerbot.model.UserPublishProjectModel;

import io.reactivex.disposables.Disposable;

/**
 * Created by huheming on 2018/6/7
 */

public class MyProjectActivityPresenter {

    private IGetMyMatchList iGetMyMatchList;
    private IUserPublishProject iUserPublishProject;
    private IUserCareProject iUserCareProject;
    private IUserCareProjectModel iUserCareProjectModel;
    private Disposable subscribe;
    private IUserPublishProjectModel iUserPublishProjectModel;
    private IGetMyMatchListModel iGetMyMatchListModel;
    private Disposable subscribe1;

    public MyProjectActivityPresenter(IUserCareProject iUserCareProject, IUserPublishProject iUserPublishProject, IGetMyMatchList iGetMyMatchList) {
        this.iUserCareProject = iUserCareProject;
        this.iUserPublishProject = iUserPublishProject;
        this.iGetMyMatchList = iGetMyMatchList;
    }

    public void getUserCareProject(int pageNum, String projectFilterType) {
        if (iUserCareProjectModel == null) {
            iUserCareProjectModel = new UserCareProjectModel();
        }
        PageRequest request = new PageRequest();
        request.pageNum = pageNum;
        request.projectFilterType = projectFilterType;
        request.pageSize = 10;
        subscribe = iUserCareProjectModel.getUserCareProject(request).subscribe(matchCompanyMorePageResponse -> {
            if (iUserCareProject != null) {
                if (pageNum == 1) {
                    iUserCareProject.getUserCareProjectSuccess(matchCompanyMorePageResponse);
                } else {
                    iUserCareProject.getUserCareProjectMoreSuccess(matchCompanyMorePageResponse);
                }
            }
        }, throwable -> {
            if (iUserCareProject != null) {
                if (pageNum == 1) {
                    iUserCareProject.getUserCareProjectFail(throwable);
                } else {
                    iUserCareProject.getUserCareProjectMoreFail(throwable);
                }
            }
        });
    }

    public void getUserPublishProject(int pageNum, String projectFilterType) {
        if (iUserPublishProjectModel == null) {
            iUserPublishProjectModel = new UserPublishProjectModel();
        }
        PageRequest request = new PageRequest();
        request.pageNum = pageNum;
        request.projectFilterType = projectFilterType;
        request.pageSize = 10;
        subscribe1 = iUserPublishProjectModel.getUserPublishProject(request).subscribe(matchCompanyMorePageResponse -> {
            if (iUserPublishProject != null) {
                if (pageNum == 1) {
                    iUserPublishProject.getUserPublishProjectSuccess(matchCompanyMorePageResponse);
                } else {
                    iUserPublishProject.getUserPublishProjectMoreSuccess(matchCompanyMorePageResponse);
                }
            }
        }, throwable -> {
            if (iUserPublishProject != null) {
                if (pageNum == 1) {
                    iUserPublishProject.getUserPublishProjectFail(throwable);
                } else {
                    iUserPublishProject.getUserPublishProjectMoreFail(throwable);
                }
            }
        });
    }

    public void getMyMatchList(int pageNum, String projectFilterType) {
        if (iGetMyMatchListModel == null) {
            iGetMyMatchListModel = new GetMyMatchListModel();
        }
        PageRequest request = new PageRequest();
        request.pageNum = pageNum;
        request.projectFilterType = projectFilterType;
        request.pageSize = 10;
        subscribe1 = iGetMyMatchListModel.getMyMatchList(request).subscribe(matchCompanyMorePageResponse -> {
            if (iGetMyMatchList != null) {
                if (pageNum == 1) {
                    iGetMyMatchList.getMyMatchListSuccess(matchCompanyMorePageResponse);
                } else {
                    iGetMyMatchList.getMyMatchListMoreSuccess(matchCompanyMorePageResponse);
                }
            }
        }, throwable -> {
            if (iGetMyMatchList != null) {
                if (pageNum == 1) {
                    iGetMyMatchList.getMyMatchListFail(throwable);
                } else {
                    iGetMyMatchList.getMyMatchListMoreFail(throwable);
                }
            }
        });
    }

    public void destory() {
        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
        if (subscribe1 != null) {
            subscribe1.dispose();
            subscribe1 = null;
        }
        if (iUserCareProject != null) {
            iUserCareProject = null;
        }
        if (iUserCareProjectModel != null) {
            iUserCareProjectModel = null;
        }
        if (iUserPublishProject != null) {
            iUserPublishProject = null;
        }
        if (iUserPublishProjectModel != null) {
            iUserPublishProjectModel = null;
        }
    }
}
