package com.buyint.mergerbot.http;

import android.text.TextUtils;

import com.buyint.mergerbot.Utility.LogUtil;
import com.buyint.mergerbot.dto.AccountMergerRequest;
import com.buyint.mergerbot.dto.AccountVerificationGetMessageRequest;
import com.buyint.mergerbot.dto.AccountVerificationGetMessageResponse;
import com.buyint.mergerbot.dto.AccountVerificationVerifyMessageRequest;
import com.buyint.mergerbot.dto.AddEducationExperienceRequest;
import com.buyint.mergerbot.dto.AddSecretaryRequest;
import com.buyint.mergerbot.dto.BooleanResponse;
import com.buyint.mergerbot.dto.ChangeQATrainAimerRequest;
import com.buyint.mergerbot.dto.CodeNameBean;
import com.buyint.mergerbot.dto.CodeNameResponse;
import com.buyint.mergerbot.dto.ContactAddRequest;
import com.buyint.mergerbot.dto.ContactBean;
import com.buyint.mergerbot.dto.GetAttachMessageResponse;
import com.buyint.mergerbot.dto.GetAutoProductResponse;
import com.buyint.mergerbot.dto.GetFriendListResponse;
import com.buyint.mergerbot.dto.GetLawyerBean;
import com.buyint.mergerbot.dto.GetLawyerCompanyBean;
import com.buyint.mergerbot.dto.GetNameDetailResponse;
import com.buyint.mergerbot.dto.GetPeopleRelationShipModel;
import com.buyint.mergerbot.dto.GetProductRequest;
import com.buyint.mergerbot.dto.GetUserBusinessActivityListResponse;
import com.buyint.mergerbot.dto.GetUserBusinessActivityResponse;
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListRequest;
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListResponse;
import com.buyint.mergerbot.dto.GetUserProjectPerformanceResponse;
import com.buyint.mergerbot.dto.HistoryQABean;
import com.buyint.mergerbot.dto.IndustryListResponse;
import com.buyint.mergerbot.dto.IndustrySuperiorResponse;
import com.buyint.mergerbot.dto.LayerListBean;
import com.buyint.mergerbot.dto.LayerSendRequest;
import com.buyint.mergerbot.dto.LayerlistRequest;
import com.buyint.mergerbot.dto.LinkedinRequest;
import com.buyint.mergerbot.dto.LoginModel;
import com.buyint.mergerbot.dto.LoginResponse;
import com.buyint.mergerbot.dto.MatchCompanyDetailResponse;
import com.buyint.mergerbot.dto.MatchCompanyMorePageRequest;
import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse;
import com.buyint.mergerbot.dto.MatchCompanyRequest;
import com.buyint.mergerbot.dto.MatchCompanyResponse;
import com.buyint.mergerbot.dto.MatchRecordDeepMatchRequest;
import com.buyint.mergerbot.dto.MatchRecordDetailClientResponse;
import com.buyint.mergerbot.dto.MatchRecordListModel;
import com.buyint.mergerbot.dto.MatchRecordListResponse;
import com.buyint.mergerbot.dto.MatchRecordLockRequest;
import com.buyint.mergerbot.dto.NamePhonePidBean;
import com.buyint.mergerbot.dto.PageRequest;
import com.buyint.mergerbot.dto.PasswordChangePasswordRequest;
import com.buyint.mergerbot.dto.PasswordLoginRequest;
import com.buyint.mergerbot.dto.PostUserInfoType3Request;
import com.buyint.mergerbot.dto.ProjectIdNameBean;
import com.buyint.mergerbot.dto.PutUserBusinessActivityRequest;
import com.buyint.mergerbot.dto.PutUserProjectPerformanceRequest;
import com.buyint.mergerbot.dto.QAHistoryBean;
import com.buyint.mergerbot.dto.QASpeakRequest;
import com.buyint.mergerbot.dto.QASpeakResponse;
import com.buyint.mergerbot.dto.QAUserBackAndProjectBackBean;
import com.buyint.mergerbot.dto.RegisterRequest;
import com.buyint.mergerbot.dto.RegisterVerificationResponse;
import com.buyint.mergerbot.dto.SecretaryBean;
import com.buyint.mergerbot.dto.SemanticExtractionResponse;
import com.buyint.mergerbot.dto.SendMessageToCreateLawyerRequest;
import com.buyint.mergerbot.dto.SmsResponse;
import com.buyint.mergerbot.dto.StringResponse;
import com.buyint.mergerbot.dto.UpdateLanguageSkillRequest;
import com.buyint.mergerbot.dto.UpdatePrivacySettingRequest;
import com.buyint.mergerbot.dto.UpdateUserAllInfoRequest;
import com.buyint.mergerbot.dto.UserAndCompanyNameAutoCompleteResponse;
import com.buyint.mergerbot.dto.UserBackRequest;
import com.buyint.mergerbot.dto.UserNikeAndIntentionRequest;
import com.buyint.mergerbot.dto.VerifyCodeChangePasswordRequest;
import com.buyint.mergerbot.dto.VersionResponse;
import com.buyint.mergerbot.rx.RequestErrorThrowable;
import com.buyint.mergerbot.rx.RxHttpHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class InterfaceAPI {

    private InterfaceService interfaceService;

    public InterfaceAPI(String url) {
        interfaceService = new RetrofitClient(url).getInterfaceService();
    }

    public Observable<GetPeopleRelationShipModel> getPeopleRelationShip(String source, String target) {
        return interfaceService.getPeopleRelationShip(source, target).map(new ResultFunc<>()).compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<BooleanResponse> deleteNowOrTomorrow() {
        return interfaceService.deleteNowOrTomorrow().flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE, HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> deleteMatchRecordPeople(String id) {
        return interfaceService.deleteMatchRecordPeople(id).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE, HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> deleteEducationExperience(String id) {
        return interfaceService.deleteEducationExperience(id).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE, HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> deleteUserProjectPerformance(String id) {
        return interfaceService.deleteUserProjectPerformance(id).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE, HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> deleteUserBusinessActivity(String id) {
        return interfaceService.deleteUserBusinessActivity(id).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<StringResponse> userIconUpdate(MultipartBody.Part body) {
        return interfaceService.userIconUpdate(body).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<StringResponse> updateFile(MultipartBody.Part body) {
        return interfaceService.updateFile(body).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<VersionResponse> getVersion() {
        return interfaceService.getVersion().flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getQRString() {
        return interfaceService.getQRString().
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<ArrayList<NamePhonePidBean>> getContactsList() {
        return interfaceService.getContactsList().
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<Boolean> sendQRString(String text) {
        return interfaceService.sendQRString(text).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<HistoryQABean> getHistoryQA(String pid) {
        return interfaceService.getHistoryQA(pid).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<GetFriendListResponse> getFriendList(String text) {
        return interfaceService.getFriendList(text).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<ArrayList<MatchRecordListModel>> searchPeople(String text) {
        return interfaceService.searchPeople(text).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<ArrayList<MatchRecordListModel>> searchPeople2(String text) {
        return interfaceService.searchPeople2(text).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<ArrayList<ProjectIdNameBean>> getCompanyList(String text) {
        return interfaceService.getCompanyList(text).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<ArrayList<GetLawyerBean>> getLawyer(String name, String law) {
        return interfaceService.getLawyer(name, law).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<ArrayList<GetLawyerCompanyBean>> getLawyerCompany(String name) {
        return interfaceService.getLawyerCompany(name).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<GetAttachMessageResponse> getAttachMessage() {
        return interfaceService.getAttachMessage().flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SmsResponse> getRegisterSms(String type, String phoneNumber) {
        return interfaceService.getRegisterSms(type, phoneNumber).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SmsResponse> getSms(String type, String phoneNumber) {
        return interfaceService.getSms(type, phoneNumber).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CodeNameResponse> getLocation(String q) {
        return interfaceService.getLocation(q).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GetNameDetailResponse> getNameDetail(String q) {
        return interfaceService.getNameDetail(q).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<StringResponse> matchRecordLock(MatchRecordLockRequest req) {
        return interfaceService.matchRecordLock(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(req)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<UserAndCompanyNameAutoCompleteResponse> userAndCompanyNameAutoComplete(String userName, String companyName) {
        return interfaceService.userAndCompanyNameAutoComplete(userName, companyName).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> userBack(UserBackRequest request) {
        return interfaceService.userBack(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LoginResponse> accountMerger(AccountMergerRequest request) {
        return interfaceService.accountMerger(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> noticeProject(String pid) {
        return interfaceService.noticeProject(pid).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> notNoticeProject(String pid) {
        return interfaceService.notNoticeProject(pid).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> deleteProject(String pid) {
        return interfaceService.deleteProject(pid).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<CodeNameResponse> hotLabel() {
        return interfaceService.hotLabel().flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<IndustrySuperiorResponse> getIndustrySuperior() {
        return interfaceService.getIndustrySuperior().flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchRecordListResponse> matchRecordDetailPerson(String personId) {
        return interfaceService.matchRecordDetailPerson(personId).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchRecordListResponse> matchRecordList(String type) {
        return interfaceService.matchRecordList(type).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchRecordListModel> getPeopleInformation(String id) {
        return interfaceService.getPeopleInformation(id).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<CodeNameResponse> getLocationInput(String place) {
        return interfaceService.getLocationInput(place).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchRecordDetailClientResponse> matchRecordDetailClient(String personId) {
        return interfaceService.matchRecordDetailClient(personId).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchCompanyDetailResponse> getMatchCompanyDetail(String clickId, String ProjectId, String type) {
        return interfaceService.getMatchCompanyDetail(clickId, ProjectId, type).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<IndustryListResponse> getIndustryList(String title) {
        return interfaceService.getIndustryList(title).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GetUserProjectPerformanceResponse> getUserProjectPerformance(String id) {
        return interfaceService.getUserProjectPerformance(id).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GetUserBusinessActivityResponse> getUserBusinessActivity(String id) {
        return interfaceService.getUserBusinessActivity(id).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<RegisterVerificationResponse> registerVerification(RegisterRequest request) {
        return interfaceService.registerVerification(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LoginResponse> linkedinRegister(LinkedinRequest request) {
        return interfaceService.linkedinRegister(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .onErrorResumeNext((ObservableSource<? extends LoginResponse>) throwable -> RxHttpHelper.convertIOEError((Throwable) throwable)).flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<AccountVerificationGetMessageResponse> accountVerificationGetMessage(AccountVerificationGetMessageRequest request) {
        return interfaceService.accountVerificationGetMessage(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> accountVerificationVerifyMessage(AccountVerificationVerifyMessageRequest request) {
        return interfaceService.accountVerificationVerifyMessage(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchCompanyMorePageResponse> matchRecordDetailProject(String personId) {
        return interfaceService.matchRecordDetailProject(personId)
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> setAttachMessage(String string) {
        return interfaceService.setAttachMessage(string).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<StringResponse> addEducationExperience(AddEducationExperienceRequest request) {
        return interfaceService.addEducationExperience(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> updateLanguageSkill(UpdateLanguageSkillRequest request) {
        return interfaceService.updateLanguageSkill(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> updateFocusArea(ArrayList<CodeNameBean> list) {
        return interfaceService.updateFocusArea(list).flatMap(res -> {
            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.errorMsg)) {

                LogUtil.error(res.toString());

                return Observable.just(res);
            } else {
                if (TextUtils.isEmpty(res.errorMsg)) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<StringResponse> postUserProjectPerformance(PutUserProjectPerformanceRequest request) {
        return interfaceService.postUserProjectPerformance(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<StringResponse> postUserBusinessActivity(PutUserBusinessActivityRequest request) {
        return interfaceService.postUserBusinessActivity(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchCompanyResponse> matchCompany(MatchCompanyRequest request) {
        return interfaceService.matchCompany(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchCompanyResponse> reMatchWrongProject(MatchCompanyRequest request) {
        return interfaceService.reMatchWrongProject(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchCompanyResponse> getProduct(GetProductRequest request) {
        return interfaceService.getProduct(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SemanticExtractionResponse> getSemanticExtraction(String request) {
        return interfaceService.getSemanticExtraction(RequestBody.create(MediaType.parse("application/json"), request))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GetAutoProductResponse> getProductList(String request) {
        return interfaceService.getProductList(RequestBody.create(MediaType.parse("application/json"), request))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> setPassword(String password) {
        return interfaceService.setPassword(RequestBody.create(MediaType.parse("application/json"), password))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> setUserInfo(UserNikeAndIntentionRequest request) {
        return interfaceService.setUserInfo(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LoginResponse> passwordLogin(PasswordLoginRequest request) {
        return interfaceService.passwordLogin(/*RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request))*/request)
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<LoginResponse> smsLogin(RegisterRequest request) {
        return interfaceService.smsLogin(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchCompanyMorePageResponse> matchCompanyMorePage(MatchCompanyMorePageRequest request) {
        return interfaceService.matchCompanyMorePage(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchCompanyMorePageResponse> getProductMore(MatchCompanyMorePageRequest request) {
        return interfaceService.getProductMore(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<QASpeakResponse> qaSpeak(QASpeakRequest request) {
        return interfaceService.qaSpeak(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchCompanyMorePageResponse> getMyProjectList(PageRequest request) {
        return interfaceService.getMyProjectList(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchCompanyMorePageResponse> getMyNoticeList(PageRequest request) {
        return interfaceService.getMyNoticeList(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MatchCompanyMorePageResponse> getMyMatchList(PageRequest request) {
        return interfaceService.getMyMatchList(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> verifycodeChangePassword(VerifyCodeChangePasswordRequest request) {
        return interfaceService.verifycodeChangePassword(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> passwordChangePassword(PasswordChangePasswordRequest request) {
        return interfaceService.passwordChangePassword(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> updatePrivacySettingArchives(UpdatePrivacySettingRequest request) {
        return interfaceService.updatePrivacySettingArchives(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> updateAllUserInfo(UpdateUserAllInfoRequest request) {
        return interfaceService.updateAllUserInfo(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> putUserProjectPerformance(PutUserProjectPerformanceRequest request) {
        return interfaceService.putUserProjectPerformance(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> putUserBusinessActivity(PutUserBusinessActivityRequest request) {
        return interfaceService.putUserBusinessActivity(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BooleanResponse> putEducationExperience(AddEducationExperienceRequest request) {
        return interfaceService.putEducationExperience(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GetUserProjectPerformanceListResponse> getUserProjectPerformanceList(GetUserProjectPerformanceListRequest request) {
        return interfaceService.getUserProjectPerformanceList(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());

                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GetUserBusinessActivityListResponse> getUserBusinessActivityList(GetUserProjectPerformanceListRequest request) {
        return interfaceService.getUserBusinessActivityList(RequestBody.create(MediaType.parse("application/json"), RxHttpHelper.convertRequestToJson(request)))
                .flatMap(res -> {
                    if (res == null) {
                        return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                                HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
                    }

                    if (TextUtils.isEmpty(res.errorMsg)) {

                        LogUtil.error(res.toString());
                        return Observable.just(res);
                    } else {
                        if (TextUtils.isEmpty(res.errorMsg)) {
                            return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                        }
                        return Observable.error(new RequestErrorThrowable(res.errorCode, res.errorMsg));
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> getAddContact(ContactAddRequest body) {

        return interfaceService.addContact(body).flatMap(res -> {

            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.getMessage())) {

                LogUtil.error(res.toString());
                return Observable.just(res.getData());
            } else {
                if (TextUtils.isEmpty(res.getMessage())) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.getCode(), res.getMessage()));
            }


        }).compose(RxHelper.INSTANCE.rxSchedulerHelper());


    }

    public Observable<Boolean> putAddContact(ContactAddRequest body) {

        return interfaceService.putContact(body).flatMap(res -> {

            if (res == null) {
                return Observable.error(new RequestErrorThrowable(HttpErrorInfo.CODE_OF_PARSE_REQUEST_FAILURE,
                        HttpErrorInfo.MSG_OF_PARSE_REQUEST_FAILURE));
            }

            if (TextUtils.isEmpty(res.getMessage())) {

                LogUtil.error(res.toString());
                return Observable.just(res.getData());
            } else {
                if (TextUtils.isEmpty(res.getMessage())) {
                    return Observable.error(new RequestErrorThrowable("-1", "系统错误"));
                }
                return Observable.error(new RequestErrorThrowable(res.getCode(), res.getMessage()));
            }


        }).compose(RxHelper.INSTANCE.rxSchedulerHelper());

    }


    public Observable<ContactAddRequest> getAddressListByPersonId(String personId) {

        return interfaceService.getAddressListByPersonId(personId).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());

    }


    public Observable<List<LayerListBean>> getAuthenticationLawyerList(LayerlistRequest body) {
        return interfaceService.getAuthenticationLawyerList(body).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<Boolean> qaUserBack(QAUserBackAndProjectBackBean bean) {
        return interfaceService.qaUserBack(bean).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<String> getSendLawyerList(LayerSendRequest layerSendRequest) {
        return interfaceService.getSendLawyerList(layerSendRequest).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<Boolean> changeQATrainAimer(ChangeQATrainAimerRequest request) {
        return interfaceService.changeQATrainAimer(request).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<String> sendMessageToCreateLawyer(SendMessageToCreateLawyerRequest request) {
        return interfaceService.sendMessageToCreateLawyer(request).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<Boolean> sendCard(MultipartBody.Part body) {
        return interfaceService.sendCard(body).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<LoginModel> postUserInfoType3(PostUserInfoType3Request request) {
        return interfaceService.postUserInfoType3(request).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<Boolean> uploadContact(ArrayList<ContactBean> list) {
        return interfaceService.uploadContact(list).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<ArrayList<QAHistoryBean>> getHistoryProjectList() {
        return interfaceService.getHistoryProjectList().
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<ArrayList<SecretaryBean>> getSecretaryList() {
        return interfaceService.getSecretaryList().
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<Boolean> removeSecretary(String text) {
        return interfaceService.removeSecretary(text).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<Boolean> addSecretary(AddSecretaryRequest request) {
        return interfaceService.addSecretary(request).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }

    public Observable<ArrayList<MatchRecordListModel>> matchRecordDeepMatch(MatchRecordDeepMatchRequest request) {
        return interfaceService.matchRecordDeepMatch(request).
                map(new ResultFunc<>())
                .compose(RxHelper.INSTANCE.rxSchedulerHelper());
    }
}
