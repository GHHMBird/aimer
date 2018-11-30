package com.buyint.mergerbot.http;

import com.buyint.mergerbot.dto.AccountVerificationGetMessageResponse;
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
import com.buyint.mergerbot.dto.GetUserBusinessActivityListResponse;
import com.buyint.mergerbot.dto.GetUserBusinessActivityResponse;
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListResponse;
import com.buyint.mergerbot.dto.GetUserProjectPerformanceResponse;
import com.buyint.mergerbot.dto.HistoryQABean;
import com.buyint.mergerbot.dto.IndustryListResponse;
import com.buyint.mergerbot.dto.IndustrySuperiorResponse;
import com.buyint.mergerbot.dto.LayerListBean;
import com.buyint.mergerbot.dto.LayerSendRequest;
import com.buyint.mergerbot.dto.LayerlistRequest;
import com.buyint.mergerbot.dto.LoginModel;
import com.buyint.mergerbot.dto.LoginResponse;
import com.buyint.mergerbot.dto.MatchCompanyDetailResponse;
import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse;
import com.buyint.mergerbot.dto.MatchCompanyResponse;
import com.buyint.mergerbot.dto.MatchRecordDeepMatchRequest;
import com.buyint.mergerbot.dto.MatchRecordDetailClientResponse;
import com.buyint.mergerbot.dto.MatchRecordListModel;
import com.buyint.mergerbot.dto.MatchRecordListResponse;
import com.buyint.mergerbot.dto.NamePhonePidBean;
import com.buyint.mergerbot.dto.PasswordLoginRequest;
import com.buyint.mergerbot.dto.PostUserInfoType3Request;
import com.buyint.mergerbot.dto.ProjectIdNameBean;
import com.buyint.mergerbot.dto.QAHistoryBean;
import com.buyint.mergerbot.dto.QASpeakResponse;
import com.buyint.mergerbot.dto.QAUserBackAndProjectBackBean;
import com.buyint.mergerbot.dto.RegisterVerificationResponse;
import com.buyint.mergerbot.dto.ResultBean;
import com.buyint.mergerbot.dto.SecretaryBean;
import com.buyint.mergerbot.dto.SemanticExtractionResponse;
import com.buyint.mergerbot.dto.SendMessageToCreateLawyerRequest;
import com.buyint.mergerbot.dto.SmsResponse;
import com.buyint.mergerbot.dto.StringResponse;
import com.buyint.mergerbot.dto.UserAndCompanyNameAutoCompleteResponse;
import com.buyint.mergerbot.dto.VersionResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InterfaceService {

    String API_GET_VERSIONCODE = "androidapi/appversion";//get检查版本
    String API_DEPTH_MATCH = "androidapi/web/ProjectManager";// post匹配项目的列表  get获取匹配项目的详情
    String API_GET_PAGE_MATCHED = "androidapi/web/ProjectManager/getPageMatchedProjectInfo";//分页加载
    String API_GET_INDUSTRY = "androidapi/web/IndustryManager/getIndustryList";// get获取行业信息
    String API_GET_LOCATION = "androidapi/web/Location";// get获取地理位置四级联动
    String API_GET_LOCATION_INPUT = "androidapi/web/Location/suggest";// get获取地理位置自动补全
    String API_HOT_LABEL = "androidapi/match/chunks/hotLabel";// get热门的行业（登录或者未登录都可以调用）
    String API_SEMANTIC_EXTRACTION = "androidapi/match/Semantic/semanticExtraction";// post语义识别
    String API_GET_PRODUCT = "androidapi/project/recommendproject/RegisterRecommend";// post首页获取推荐产品（登录或者未登录都可以调用）
    String API_GET_PRODUCT_MORE = "androidapi/project/recommendproject/getRegisterPageRecommendProjectResponse";// post首页获取推荐产品（登录或者未登录都可以调用）
    String API_AUTO_PRODUCT = "androidapi/match/product";//产品自动补全
    String API_REGISTER_SEND_SMS = "api/account/userinfo/registerVerificationCode";//注册流程发送验证码
    String API_REGISTER_VERIFICATION_SMS = "api/account/userinfo/registerSimpleUser";//注册流程验证短信
    String API_SET_PASSWORD = "api/account/userinfo/setPassWord";//设置密码
    String API_SET_USER_INFO = "api/account/userinfo/setUserNikeAndIntention";//设置用户昵称和偏好等信息
    String API_PASSWORD_LOGIN = "api/account/userinfo/login";//密码登录
    String API_SMS_LOGIN_SEND = "api/account/userinfo/loginVerificationCode";//短信登录发送短信
    String API_SMS_LOGIN_LOGIN = "api/account/userinfo/verificationCodeLogin";//短信登录验证短信
    String API_Q_A_SPEAK = "androidapi/web/Dialogue";//qa对话系统
    String API_GET_MY_PROJECT = "androidapi/web/ProjectManager/getMyProjectInfo";//我的項目列表
    String API_NOTICE_PRIJECT = "androidapi/project/notice";//点击关注
    String API_NO_NOTICE_PRIJECT = "androidapi/project/cancel";//取消关注
    String API_DELETE_PRIJECT = "androidapi/web/ProjectManager/delMyProject";//删除项目
    String API_NOTICE_PRIJECT_LIST = "androidapi/web/ProjectManager/noticeList";//我关注的项目
    String API_USER_BACK = "androidapi/web/userManager/feedBack";//用户反馈
    String API_LINKEDIN_REGISTER = "api/account/userinfo/linkedInRegisterSimpleUser";//linkedin注册
    String API_INDUSTRY_SUPERIOR = "api/IndustrySuperior/getRecommendIndustrySuperior";//行业高手
    String API_FILE_UPDATE = "androidapi/web/UploadFile";//上传文件
    String API_USER_ICON_UPDATE = "androidapi/web/UserSetting/updateUserAvatars";//用户头像更新
    String API_VERIFY_CODE_CHANGE_PASSWORD = "api/account/UserSetting/updateUserPassword";//验证码修改登录密码
    String API_OLD_PASSWORD_CHANGE_PASSWORD = "api/account/UserSetting/updateUserPasswordByOldPwd";//密码修改登录密码
    String API_ACCOUNT_MERGER = "api/account/UserMerge/MergeUser";//账户合并
    String API_PRIVACY_SETTING = "api/account/UserSetting/updateUserPrivacySetting";//隐私设置接口
    String API_UPDATE_USER_ALL_INFO = "api/account/UserSetting/UpdateUserOverviewMessage";//更新用户的所有信息
    String API_PROJECT_PERFORMANCE = "api/account/UserAchievement";//业绩成就的  增删改查  接口
    String API_PROJECT_PERFORMANCE_LIST = "api/account/UserAchievement/getPageUserAchievement";//业绩成就的 列表  接口
    String API_BUSINESS_ACTIVITY_LIST = "api/account/UserCommercialActivity/getPageUserCommercialActivity";//商务活动的 列表  接口
    String API_BUSINESS_ACTIVITY = "api/account/UserCommercialActivity";//商务活动的  增删改查  接口
    String API_UPDATE_LANGUAGE_SKILL = "api/account/UserAttachMessage/setLanguageCompetence";//语言能力 上传接口
    String API_UPDATE_FOCUS_AREA = "api/account/UserAttachMessage/setIndustryClassification";//聚焦领域 上传接口
    String API_POST_EDUCATION_EXPERIENCE = "api/account/UserAttachMessage/addEducationExperience";//添加教育经历
    String API_UPDATE_EDUCATION_EXPERIENCE = "api/account/UserAttachMessage/updateEducationExperience";//更新教育经历
    String API_DELETE_EDUCATION_EXPERIENCE = "api/account/UserAttachMessage/deleteEducationExperience";//删除教育经历
    String API_SET_ATTACH_MESSAGE = "api/account/UserAttachMessage/setAttachMessage";//设置附加信息
    String API_GET_ATTACH_MESSAGE = "api/account/UserAttachMessage";//获取附加信息
    String API_USERNAME_COMPANY_AUTO_COMPLETE = "androidapi/data/MatchRecord/userBindingSuggest";//匹配录 公司姓名自动补全
    String API_USERNAME_COMPANY_LOCK = "androidapi/data/MatchRecord/userBinding";//匹配录 公司姓名提交并绑定
    String API_MATCH_RECORD_LIST = "androidapi/data/MatchRecord/getUserMatchRecord";//匹配录  一度人脉的列表(反正是首页的列表)
    String API_MATCH_RECORD_DETAIL_PERSON = "androidapi/data/MatchRecord/getFirstLevelUserMatchRecord";//匹配录  人物详情 他的人脉
    String API_MATCH_RECORD_DETAIL_PROJECT = "androidapi/data/MatchRecord/getUserMatchRecordMatchedCompany";//匹配录  人物详情  匹配的项目
    String API_ACCOUNT_VERIFICATION_ONE = "api/account/Authentication/applyAuthentication";//认证流程第一步
    String API_ACCOUNT_VERIFICATION_TWO = "api/account/Authentication/verificationAuthentication";//认证流程第2步
    String API_CONTAC_ADD = "androidapi/account/MatchRecord/addAddressList";//添加联系人
    String API_CONTAC_PUT = "androidapi/account/MatchRecord";//修改联系人
    String API_CONTAC_GETBYID = "androidapi/account/MatchRecord/getAddressListByPersonId";//通过personid 找到对应的好友信息
    String API_MATCH_PRIJECT_LIST = "androidapi/web/ProjectManager/getUserMatchedProjectList";//我的项目  匹配的项目
    String API_MATCH_RECORD_DETAIL_CLIENT = "androidapi/data/MatchRecord/getUserMatchRecordCompany";//匹配录  人物详情  他的客户
    String API_DELETE_NOW_OR_TOMORROW = "androidapi/data/MatchRecord/cleanCurrentUserMatchRecord";//测试的狗接口 给他们删匹配录的
    String API_DELETE_MATCH_RECORD_PEOPLE = "androidapi/data/MatchRecord/deleteUserMatchRecordByPersonId";//删除匹配录的某个人
    String API_CONSESS_LAWYERLIST = "api/account/Authentication/getAuthenticationLawyerList";
    String API_CONSESS_LAWYERLIST_SEND = "api/account/Authentication/saveAuthenticationLawyerApply";//保存申请发送邮件 让Aimer律师帮忙认证
    String API_CREATE_LAWYER_SEND = "api/account/Authentication/inviteAuthenticationLawyerApply";// 自己创建律师帮忙认证
    String API_CONTACT_SCANCARD = "androidapi/web/BusinessCard";
    String API_AUTO_FINISH_NAME = "androidapi/web/Extract/extract";//关系链 自动补全人名
    String API_PEOPLE_RELATIONSHIP = "androidapi/web/Relation/relation";//关系链 查询关系
    String API_PEOPLE_INFORMATION = "androidapi/data/MatchRecord/getUserInfo";//根据personid查询人物信息
    String API_GET_QR_STRING = "androidapi/account/MatchRecord/getScanCode";//获取生成二维码的信息
    String API_SEND_QR_STRING = "androidapi/account/MatchRecord/addFriend";//用二维码加好友
    String API_GET_FRIEND_LIST = "androidapi/account/MatchRecord/scanCode";//用二维码加好友
    String API_SEARCH_PEOPLE = "androidapi/data/MatchRecord/searchUserMatchRecord";//搜索好友
    String API_GET_LAWYER_COMPANY = "api/account/Authentication/getLaw";//律师事务所名字补全
    String API_GET_LAWYER = "api/account/Authentication/getLawyerInfo";//律师 信息补全
    String API_GET_COMPANY_LIST = "androidapi/web/ProjectManager/getProjectName";//注册过程中  自动补全公司名
    String API_POST_USER_INFO = "androidapi/web/UserSetting/registerSetUserCompanyMessage";//注册过程中 上传用户信息
    String API_UP_LOAD_CONTACT = "androidapi/member/ContactList/addContactList";//上传通讯录
    String API_GET_CONTACT_LIST = "androidapi/member/ContactList/getContactList";//获取通讯录
    String API_QA_USER_BACK = "androidapi/user/MatchFeedback/insertMatchFeedbackInfo";//QA对话添加反馈信息   项目添加反馈信息
    String API_PROJECT_WRONG_REMATCH = "androidapi/user/MatchFeedback/updateMatchCondition";//项目配错了  重新匹配
    String API_HISTORY_PROJECT_LIST = "androidapi/user/project/getAllUserProjects";//历史项目列表
    String API_GET_HISTORY_QA = "androidapi/web/Dialogue/getDialogueSentence";//根据项目ID 获取历史对话记录
    String API_TRAIN_AIMER = "androidapi/web/Dialogue/updateDialogueSentence";//根据项目ID 修改对话训练Aimer
    String API_GET_ALL_SECRETARY = "androidapi/authorizeduser/findAuthorizedUsers";//获取所有秘书信息
    String API_ADD_SECRETARY = "androidapi/authorizeduser/addAuthorizedUser";//添加秘书
    String API_REMOVE_SECRETARY = "androidapi/authorizeduser/deleteAuthorizedUser";//删除秘书
    String API_SEARCH_PEOPLE_V2 = "androidapi/data/MatchRecord/searchUserMatchRecordv2";//匹配录根据关键字查询人或者公司
    String API_SEARCH_PEOPLE_V3 = "androidapi/data/MatchRecord/searchUserMatchRecordv3";//匹配录 详细查询

    @DELETE(API_DELETE_NOW_OR_TOMORROW)
    Observable<BooleanResponse> deleteNowOrTomorrow();

    @DELETE(API_DELETE_MATCH_RECORD_PEOPLE)
    Observable<BooleanResponse> deleteMatchRecordPeople(@Query("personId") String personId);

    @DELETE(API_DELETE_EDUCATION_EXPERIENCE + "/{id}")
    Observable<BooleanResponse> deleteEducationExperience(@Path("id") String id);//

    @DELETE(API_PROJECT_PERFORMANCE)
    Observable<BooleanResponse> deleteUserProjectPerformance(@Query("id") String code);//

    @DELETE(API_BUSINESS_ACTIVITY)
    Observable<BooleanResponse> deleteUserBusinessActivity(@Query("id") String code);//

    @GET(API_GET_QR_STRING)
    Observable<ResultBean<String>> getQRString();//

    @GET(API_GET_CONTACT_LIST)
    Observable<ResultBean<ArrayList<NamePhonePidBean>>> getContactsList();//

    @GET(API_GET_ALL_SECRETARY)
    Observable<ResultBean<ArrayList<SecretaryBean>>> getSecretaryList();

    @GET(API_REMOVE_SECRETARY)
    Observable<ResultBean<Boolean>> removeSecretary(@Query("authId") String authId);//

    @GET(API_SEND_QR_STRING)
    Observable<ResultBean<Boolean>> sendQRString(@Query("scanCode") String scanCode);//

    @GET(API_GET_HISTORY_QA)
    Observable<ResultBean<HistoryQABean>> getHistoryQA(@Query("projectId") String projectId);//

    @GET(API_GET_FRIEND_LIST)
    Observable<ResultBean<GetFriendListResponse>> getFriendList(@Query("scanCode") String scanCode);//

    @GET(API_GET_LAWYER_COMPANY)
    Observable<ResultBean<ArrayList<GetLawyerCompanyBean>>> getLawyerCompany(@Query("name") String name);//

    @GET(API_SEARCH_PEOPLE)
    Observable<ResultBean<ArrayList<MatchRecordListModel>>> searchPeople(@Query("searchText") String searchText);//

    @GET(API_SEARCH_PEOPLE_V2)
    Observable<ResultBean<ArrayList<MatchRecordListModel>>> searchPeople2(@Query("searchText") String searchText);//

    @GET(API_GET_COMPANY_LIST)
    Observable<ResultBean<ArrayList<ProjectIdNameBean>>> getCompanyList(@Query("name") String name);//

    @GET(API_GET_VERSIONCODE)
    Observable<VersionResponse> getVersion();//

    @GET(API_GET_ATTACH_MESSAGE)
    Observable<GetAttachMessageResponse> getAttachMessage();//

    @GET(API_HOT_LABEL)
    Observable<CodeNameResponse> hotLabel();//

    @GET(API_INDUSTRY_SUPERIOR)
    Observable<IndustrySuperiorResponse> getIndustrySuperior();//

    @GET(API_MATCH_RECORD_LIST)
    Observable<MatchRecordListResponse> matchRecordList(@Query("searchRelationType") String searchRelationType);//

    @GET(API_PEOPLE_INFORMATION)
    Observable<ResultBean<MatchRecordListModel>> getPeopleInformation(@Query("personId") String personId);//

    @GET(API_GET_LAWYER)
    Observable<ResultBean<ArrayList<GetLawyerBean>>> getLawyer(@Query("name") String name, @Query("law") String law);//

    @GET(API_MATCH_RECORD_DETAIL_PERSON)
    Observable<MatchRecordListResponse> matchRecordDetailPerson(@Query("personId") String personId);

    @GET(API_USERNAME_COMPANY_AUTO_COMPLETE)
    Observable<UserAndCompanyNameAutoCompleteResponse> userAndCompanyNameAutoComplete(@Query("userName") String userName, @Query("company") String company);//

    @GET(API_PEOPLE_RELATIONSHIP)
    Observable<ResultBean<GetPeopleRelationShipModel>> getPeopleRelationShip(@Query("source") String source, @Query("target") String target);//

    @GET(API_GET_LOCATION)
    Observable<CodeNameResponse> getLocation(@Query("code") String code);//

    @GET(API_AUTO_FINISH_NAME)
    Observable<GetNameDetailResponse> getNameDetail(@Query("name") String name);//

    @GET(API_NOTICE_PRIJECT)
    Observable<BooleanResponse> noticeProject(@Query("projectId") String projectId);//

    @GET(API_NO_NOTICE_PRIJECT)
    Observable<BooleanResponse> notNoticeProject(@Query("projectId") String projectId);//

    @GET(API_DELETE_PRIJECT)
    Observable<BooleanResponse> deleteProject(@Query("projectId") String projectId);//

    @GET(API_HISTORY_PROJECT_LIST)
    Observable<ResultBean<ArrayList<QAHistoryBean>>> getHistoryProjectList();//

    @GET(API_DEPTH_MATCH)
    Observable<MatchCompanyDetailResponse> getMatchCompanyDetail(@Query("currentProjectId") String currentProjectId, @Query("projectId") String projectId, @Query("type") String type);//

    @GET(API_GET_LOCATION_INPUT)
    Observable<CodeNameResponse> getLocationInput(@Query("address") String place);//

    @GET(API_MATCH_RECORD_DETAIL_CLIENT)
    Observable<MatchRecordDetailClientResponse> matchRecordDetailClient(@Query("personId") String personId);//

    @GET(API_REGISTER_SEND_SMS)
    Observable<SmsResponse> getRegisterSms(@Query("sendType") String type, @Query("phoneNumber") String phoneNumber);//

    @GET(API_SMS_LOGIN_SEND)
    Observable<SmsResponse> getSms(@Query("sendType") String type, @Query("phoneNumber") String phoneNumber);//

    @GET(API_GET_INDUSTRY)
    Observable<IndustryListResponse> getIndustryList(@Query("title") String title);//

    @GET(API_PROJECT_PERFORMANCE + "/{id}")
    Observable<GetUserProjectPerformanceResponse> getUserProjectPerformance(@Path("id") String id);//

    @GET(API_BUSINESS_ACTIVITY + "/{id}")
    Observable<GetUserBusinessActivityResponse> getUserBusinessActivity(@Path("id") String id);//

    @POST(API_ADD_SECRETARY)
    Observable<ResultBean<Boolean>> addSecretary(@Body AddSecretaryRequest body);//

    @POST(API_SEARCH_PEOPLE_V3)
    Observable<ResultBean<ArrayList<MatchRecordListModel>>> matchRecordDeepMatch(@Body MatchRecordDeepMatchRequest body);//

    @POST(API_SET_ATTACH_MESSAGE)
    Observable<BooleanResponse> setAttachMessage(@Body String string);//

    @POST(API_USERNAME_COMPANY_LOCK)
    Observable<StringResponse> matchRecordLock(@Body RequestBody body);//

    @POST(API_ACCOUNT_VERIFICATION_TWO)
    Observable<BooleanResponse> accountVerificationVerifyMessage(@Body RequestBody body);//

    @POST(API_ACCOUNT_VERIFICATION_ONE)
    Observable<AccountVerificationGetMessageResponse> accountVerificationGetMessage(@Body RequestBody body);//

    @POST(API_POST_USER_INFO)
    Observable<ResultBean<LoginModel>> postUserInfoType3(@Body PostUserInfoType3Request body);//

    @POST(API_LINKEDIN_REGISTER)
    Observable<LoginResponse> linkedinRegister(@Body RequestBody body);//

    @GET(API_MATCH_RECORD_DETAIL_PROJECT)
    Observable<MatchCompanyMorePageResponse> matchRecordDetailProject(@Query("personId") String personId);//

    @POST(API_POST_EDUCATION_EXPERIENCE)
    Observable<StringResponse> addEducationExperience(@Body RequestBody body);//

    @POST(API_UPDATE_LANGUAGE_SKILL)
    Observable<BooleanResponse> updateLanguageSkill(@Body RequestBody body);//

    @POST(API_UPDATE_FOCUS_AREA)
    Observable<BooleanResponse> updateFocusArea(@Body ArrayList<CodeNameBean> list);//

    @POST(API_PROJECT_PERFORMANCE)
    Observable<StringResponse> postUserProjectPerformance(@Body RequestBody body);//

    @POST(API_BUSINESS_ACTIVITY)
    Observable<StringResponse> postUserBusinessActivity(@Body RequestBody body);//

    @POST(API_DEPTH_MATCH)
    Observable<MatchCompanyResponse> matchCompany(@Body RequestBody body);//深度匹配公司信息

    @POST(API_REGISTER_VERIFICATION_SMS)
    Observable<RegisterVerificationResponse> registerVerification(@Body RequestBody body);

    @POST(API_GET_PRODUCT)
    Observable<MatchCompanyResponse> getProduct(@Body RequestBody body);//首页获取推荐产品

    @POST(API_GET_PRODUCT_MORE)
    Observable<MatchCompanyMorePageResponse> getProductMore(@Body RequestBody body);//首页获取推荐产品

    @POST(API_SEMANTIC_EXTRACTION)
    Observable<SemanticExtractionResponse> getSemanticExtraction(@Body RequestBody body);//语义识别

    @POST(API_AUTO_PRODUCT)
    Observable<GetAutoProductResponse> getProductList(@Body RequestBody body);//产品自动补全

    @POST(API_SET_PASSWORD)
    Observable<BooleanResponse> setPassword(@Body RequestBody body);//

    @POST(API_SET_USER_INFO)
    Observable<BooleanResponse> setUserInfo(@Body RequestBody body);//

    @POST(API_PASSWORD_LOGIN)
    Observable<LoginResponse> passwordLogin(@Body PasswordLoginRequest body);//

    @POST(API_SMS_LOGIN_LOGIN)
    Observable<LoginResponse> smsLogin(@Body RequestBody body);//

    @POST(API_GET_PAGE_MATCHED)
    Observable<MatchCompanyMorePageResponse> matchCompanyMorePage(@Body RequestBody body);//

    @POST(API_Q_A_SPEAK)
    Observable<QASpeakResponse> qaSpeak(@Body RequestBody body);//

    @POST(API_GET_MY_PROJECT)
    Observable<MatchCompanyMorePageResponse> getMyProjectList(@Body RequestBody body);//

    @POST(API_NOTICE_PRIJECT_LIST)
    Observable<MatchCompanyMorePageResponse> getMyNoticeList(@Body RequestBody body);//

    @POST(API_MATCH_PRIJECT_LIST)
    Observable<MatchCompanyMorePageResponse> getMyMatchList(@Body RequestBody body);//

    @POST(API_PROJECT_PERFORMANCE_LIST)
    Observable<GetUserProjectPerformanceListResponse> getUserProjectPerformanceList(@Body RequestBody body);//

    @POST(API_BUSINESS_ACTIVITY_LIST)
    Observable<GetUserBusinessActivityListResponse> getUserBusinessActivityList(@Body RequestBody body);//

    @POST(API_USER_BACK)
    Observable<BooleanResponse> userBack(@Body RequestBody body);//

    @POST(API_ACCOUNT_MERGER)
    Observable<LoginResponse> accountMerger(@Body RequestBody body);//

    @Multipart
    @POST(API_FILE_UPDATE)
    Observable<StringResponse> updateFile(@Part MultipartBody.Part body);//

    @Multipart
    @PUT(API_USER_ICON_UPDATE)
    Observable<StringResponse> userIconUpdate(@Part MultipartBody.Part body);//

    @PUT(API_VERIFY_CODE_CHANGE_PASSWORD)
    Observable<BooleanResponse> verifycodeChangePassword(@Body RequestBody body);//

    @PUT(API_UP_LOAD_CONTACT)
    Observable<ResultBean<Boolean>> uploadContact(@Body ArrayList<ContactBean> list);//

    @PUT(API_OLD_PASSWORD_CHANGE_PASSWORD)
    Observable<BooleanResponse> passwordChangePassword(@Body RequestBody body);//

    @PUT(API_PRIVACY_SETTING)
    Observable<BooleanResponse> updatePrivacySettingArchives(@Body RequestBody body);//

    @PUT(API_PROJECT_WRONG_REMATCH)
    Observable<MatchCompanyResponse> reMatchWrongProject(@Body RequestBody body);//

    @PUT(API_UPDATE_USER_ALL_INFO)
    Observable<BooleanResponse> updateAllUserInfo(@Body RequestBody body);//

    @PUT(API_QA_USER_BACK)
    Observable<ResultBean<Boolean>> qaUserBack(@Body QAUserBackAndProjectBackBean bean);

    @PUT(API_PROJECT_PERFORMANCE)
    Observable<BooleanResponse> putUserProjectPerformance(@Body RequestBody body);//

    @PUT(API_BUSINESS_ACTIVITY)
    Observable<BooleanResponse> putUserBusinessActivity(@Body RequestBody body);//

    @PUT(API_TRAIN_AIMER)
    Observable<ResultBean<Boolean>> changeQATrainAimer(@Body ChangeQATrainAimerRequest body);//

    @PUT(API_UPDATE_EDUCATION_EXPERIENCE)
    Observable<BooleanResponse> putEducationExperience(@Body RequestBody body);//

    @POST(API_CONTAC_ADD)
    Observable<ResultBean<Boolean>> addContact(@Body ContactAddRequest body);//

    @PUT(API_CONTAC_PUT)
    Observable<ResultBean<Boolean>> putContact(@Body ContactAddRequest body);//

    @GET(API_CONTAC_GETBYID)
    Observable<ResultBean<ContactAddRequest>> getAddressListByPersonId(@Query("personId") String personId);//

    @POST(API_CONSESS_LAWYERLIST)
    Observable<ResultBean<List<LayerListBean>>> getAuthenticationLawyerList(@Body LayerlistRequest body);//


    @POST(API_CONSESS_LAWYERLIST_SEND)
    Observable<ResultBean<String>> getSendLawyerList(@Body LayerSendRequest body);//

    @POST(API_CREATE_LAWYER_SEND)
    Observable<ResultBean<String>> sendMessageToCreateLawyer(@Body SendMessageToCreateLawyerRequest body);//

    @Multipart
    @POST(API_CONTACT_SCANCARD)
    Observable<ResultBean<Boolean>> sendCard(@Part MultipartBody.Part body);//
}
