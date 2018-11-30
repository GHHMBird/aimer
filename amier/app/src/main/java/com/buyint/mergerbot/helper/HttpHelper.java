package com.buyint.mergerbot.helper;

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
import com.buyint.mergerbot.http.InterfaceAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Created by on 2017/2/22
 */

public class HttpHelper {

    public static Observable<Boolean> addSecretary(AddSecretaryRequest body) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).addSecretary(body);
    }

    public static Observable<ArrayList<MatchRecordListModel>> matchRecordDeepMatch(MatchRecordDeepMatchRequest body) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).matchRecordDeepMatch(body);
    }

    public static Observable<Boolean> removeSecretary(String text) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).removeSecretary(text);
    }

    public static Observable<ArrayList<SecretaryBean>> getSecretaryList() {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getSecretaryList();
    }

    public static Observable<StringResponse> userIconUpdate(MultipartBody.Part body) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).userIconUpdate(body);
    }

    public static Observable<BooleanResponse> verifycodeChangePassword(VerifyCodeChangePasswordRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).verifycodeChangePassword(request);
    }

    public static Observable<BooleanResponse> passwordChangePassword(PasswordChangePasswordRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).passwordChangePassword(request);
    }

    public static Observable<BooleanResponse> updatePrivacySettingArchives(UpdatePrivacySettingRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).updatePrivacySettingArchives(request);
    }

    public static Observable<BooleanResponse> updateAllUserInfo(UpdateUserAllInfoRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).updateAllUserInfo(request);
    }

    public static Observable<BooleanResponse> putUserProjectPerformance(PutUserProjectPerformanceRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).putUserProjectPerformance(request);
    }

    public static Observable<BooleanResponse> putUserBusinessActivity(PutUserBusinessActivityRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).putUserBusinessActivity(request);
    }

    public static Observable<BooleanResponse> putEducationExperience(AddEducationExperienceRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).putEducationExperience(request);
    }

    public static Observable<GetUserProjectPerformanceListResponse> getUserProjectPerformanceList(GetUserProjectPerformanceListRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).getUserProjectPerformanceList(request);
    }

    public static Observable<GetUserBusinessActivityListResponse> getUserBusinessActivityList(GetUserProjectPerformanceListRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).getUserBusinessActivityList(request);
    }

    public static Observable<MatchCompanyMorePageResponse> matchRecordDetailProject(String personId) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).matchRecordDetailProject(personId);
    }

    public static Observable<StringResponse> updateFile(MultipartBody.Part body) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).updateFile(body);
    }

    public static Observable<RegisterVerificationResponse> registerVerification(RegisterRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).registerVerification(request);
    }

    public static Observable<LoginResponse> linkedinRegister(LinkedinRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).linkedinRegister(request);
    }

    public static Observable<AccountVerificationGetMessageResponse> accountVerificationGetMessage(AccountVerificationGetMessageRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).accountVerificationGetMessage(request);
    }

    public static Observable<BooleanResponse> accountVerificationVerifyMessage(AccountVerificationVerifyMessageRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).accountVerificationVerifyMessage(request);
    }

    public static Observable<StringResponse> addEducationExperience(AddEducationExperienceRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).addEducationExperience(request);
    }

    public static Observable<BooleanResponse> setAttachMessage(String string) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).setAttachMessage(string);
    }

    public static Observable<BooleanResponse> updateLanguageSkill(UpdateLanguageSkillRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).updateLanguageSkill(request);
    }

    public static Observable<BooleanResponse> updateFocusArea(ArrayList<CodeNameBean> list) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).updateFocusArea(list);
    }

    public static Observable<StringResponse> postUserProjectPerformance(PutUserProjectPerformanceRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).postUserProjectPerformance(request);
    }

    public static Observable<StringResponse> postUserBusinessActivity(PutUserBusinessActivityRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).postUserBusinessActivity(request);
    }

    public static Observable<BooleanResponse> deleteMatchRecordPeople(String id) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).deleteMatchRecordPeople(id);
    }

    public static Observable<BooleanResponse> deleteNowOrTomorrow() {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).deleteNowOrTomorrow();
    }

    public static Observable<GetPeopleRelationShipModel> getPeopleRelationShip(String source, String target) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getPeopleRelationShip(source, target);
    }

    public static Observable<BooleanResponse> deleteEducationExperience(String id) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).deleteEducationExperience(id);
    }

    public static Observable<BooleanResponse> deleteUserProjectPerformance(String id) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).deleteUserProjectPerformance(id);
    }

    public static Observable<BooleanResponse> deleteUserBusinessActivity(String id) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).deleteUserBusinessActivity(id);
    }

    public static Observable<BooleanResponse> setPassword(String password) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).setPassword(password);
    }

    public static Observable<BooleanResponse> setUserInfo(UserNikeAndIntentionRequest password) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).setUserInfo(password);
    }

    public static Observable<LoginResponse> passwordLogin(PasswordLoginRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).passwordLogin(request);
    }

    public static Observable<SmsResponse> getSms(String type, String phone) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).getSms(type, phone);
    }

    public static Observable<LoginResponse> smsLogin(RegisterRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).smsLogin(request);
    }

    public static Observable<SmsResponse> getRegisterSms(String type, String phone) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).getRegisterSms(type, phone);
    }

    public static Observable<BooleanResponse> userBack(UserBackRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).userBack(request);
    }

    //todo 地理位置四级联动
    public static Observable<CodeNameResponse> getLocation(String code) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getLocation(code);
    }

    public static Observable<GetNameDetailResponse> getNameDetail(String q) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getNameDetail(q);
    }

    public static Observable<UserAndCompanyNameAutoCompleteResponse> userAndCompanyNameAutoComplete(String userName, String companyName) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).userAndCompanyNameAutoComplete(userName, companyName);
    }

    public static Observable<StringResponse> matchRecordLock(MatchRecordLockRequest req) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).matchRecordLock(req);
    }

    public static Observable<BooleanResponse> noticeProject(String pid) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).noticeProject(pid);
    }

    public static Observable<BooleanResponse> notNoticeProject(String pid) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).notNoticeProject(pid);
    }

    public static Observable<BooleanResponse> deleteProject(String pid) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).deleteProject(pid);
    }

    public static Observable<MatchRecordDetailClientResponse> matchRecordDetailClient(String personId) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).matchRecordDetailClient(personId);
    }

    public static Observable<CodeNameResponse> getLocationInput(String place) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getLocationInput(place);
    }

    public static Observable<IndustryListResponse> getIndustryList(String industry) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getIndustryList(industry);
    }

    public static Observable<GetUserProjectPerformanceResponse> getUserProjectPerformance(String id) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).getUserProjectPerformance(id);
    }

    public static Observable<GetUserBusinessActivityResponse> getUserBusinessActivity(String id) {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).getUserBusinessActivity(id);
    }

    public static Observable<MatchCompanyResponse> matchCompany(MatchCompanyRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).matchCompany(request);
    }

    public static Observable<MatchCompanyResponse> reMatchWrongProject(MatchCompanyRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).reMatchWrongProject(request);
    }

    public static Observable<CodeNameResponse> popularIndustry() {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).hotLabel();
    }

    public static Observable<IndustrySuperiorResponse> getIndustrySuperior() {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getIndustrySuperior();
    }

    public static Observable<MatchRecordListResponse> matchRecordList(String type) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).matchRecordList(type);
    }

    public static Observable<MatchRecordListModel> getPeopleInformation(String id) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getPeopleInformation(id);
    }

    public static Observable<MatchRecordListResponse> matchRecordDetailPerson(String personId) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).matchRecordDetailPerson(personId);
    }

    public static Observable<MatchCompanyResponse> getProduct(GetProductRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getProduct(request);
    }

    //todo 一句话 语义抽取
    public static Observable<SemanticExtractionResponse> getSemanticExtraction(String request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getSemanticExtraction(request);
    }

    public static Observable<GetAutoProductResponse> getProductList(String text) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getProductList(text);
    }

    public static Observable<MatchCompanyDetailResponse> getMatchCompanyDetail(String clickId, String ProjectId, String type) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getMatchCompanyDetail(clickId, ProjectId, type);
    }

    public static Observable<MatchCompanyMorePageResponse> matchCompanyMorePage(MatchCompanyMorePageRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).matchCompanyMorePage(request);
    }

    public static Observable<MatchCompanyMorePageResponse> getProductMore(MatchCompanyMorePageRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getProductMore(request);
    }

    public static Observable<QASpeakResponse> qaSpeak(QASpeakRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).qaSpeak(request);
    }

    public static Observable<MatchCompanyMorePageResponse> getMyProjectList(PageRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getMyProjectList(request);
    }

    public static Observable<LoginResponse> accountMerger(AccountMergerRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).accountMerger(request);
    }

    public static Observable<MatchCompanyMorePageResponse> getMyNoticeList(PageRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getMyNoticeList(request);
    }

    public static Observable<MatchCompanyMorePageResponse> getMyMatchList(PageRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getMyMatchList(request);
    }

    public static Observable<VersionResponse> getVersion() {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getVersion();
    }

    public static Observable<GetAttachMessageResponse> getAttachMessage() {
        return new InterfaceAPI(URLHelper.getInstance().NoTokenURL).getAttachMessage();
    }

    public static Observable<String> getQRString() {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getQRString();
    }

    public static Observable<ArrayList<NamePhonePidBean>> getContactsList() {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getContactsList();
    }

    public static Observable<Boolean> sendQRString(String text) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).sendQRString(text);
    }

    public static Observable<HistoryQABean> getHistoryQA(String pid) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getHistoryQA(pid);
    }

    public static Observable<GetFriendListResponse> getFriendList(String text) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getFriendList(text);
    }

    public static Observable<ArrayList<MatchRecordListModel>> searchPeople(String text) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).searchPeople(text);
    }

    public static Observable<ArrayList<MatchRecordListModel>> searchPeople2(String text) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).searchPeople2(text);
    }

    public static Observable<ArrayList<ProjectIdNameBean>> getCompanyList(String text) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getCompanyList(text);
    }

    public static Observable<ArrayList<GetLawyerBean>> getLawyer(String name, String law) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getLawyer(name, law);
    }

    public static Observable<ArrayList<GetLawyerCompanyBean>> getLawyerCompany(String name) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getLawyerCompany(name);
    }

    public static Observable<Boolean> getContactadd(ContactAddRequest contactAddRequest) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getAddContact(contactAddRequest);
    }

    public static Observable<Boolean> putContactput(ContactAddRequest contactAddRequest) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).putAddContact(contactAddRequest);
    }

    public static Observable<ContactAddRequest> getAddressListByPersonId(String personId) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getAddressListByPersonId(personId);
    }

    public static Observable<List<LayerListBean>> getAuthenticationLawyerList(LayerlistRequest body) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getAuthenticationLawyerList(body);
    }

    public static Observable<String> getSendLawyerList(LayerSendRequest body) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getSendLawyerList(body);
    }

    public static Observable<Boolean> changeQATrainAimer(ChangeQATrainAimerRequest request) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).changeQATrainAimer(request);
    }

    public static Observable<Boolean> qaUserBack(QAUserBackAndProjectBackBean bean) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).qaUserBack(bean);
    }

    public static Observable<String> sendMessageToCreateLawyer(SendMessageToCreateLawyerRequest body) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).sendMessageToCreateLawyer(body);
    }

    public static Observable<LoginModel> postUserInfoType3(PostUserInfoType3Request body) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).postUserInfoType3(body);
    }

    public static Observable<Boolean> uploadContact(ArrayList<ContactBean> list) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).uploadContact(list);
    }

    public static Observable<ArrayList<QAHistoryBean>> getHistoryProjectList() {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).getHistoryProjectList();
    }

    public static Observable<Boolean> sendCard(MultipartBody.Part body) {
        return new InterfaceAPI(URLHelper.getInstance().TokenURL).sendCard(body);
    }

}
