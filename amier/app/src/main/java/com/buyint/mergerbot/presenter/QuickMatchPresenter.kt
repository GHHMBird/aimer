package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.*
import com.buyint.mergerbot.model.*
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/24
 */
class QuickMatchPresenter(var iGetCompanyList: IGetCompanyList?, var iGetLawyer: IGetLawyer?, var iGetLawyerCompany: IGetLawyerCompany?, var iGetLocationInput: IGetLocationInput?, var iGetIndustryList: IGetIndustryList?, var iGetProductList: IGetProductList?, var iUserAndCompanyNameAutoComplete: IUserAndCompanyNameAutoComplete?, var iGetNameDetail: IGetNameDetail?) {

    private var Disposable: Disposable? = null
    private var Disposable2: Disposable? = null
    private var Disposable3: Disposable? = null
    private var Disposable4: Disposable? = null
    private var Disposable5: Disposable? = null
    private var Disposable6: Disposable? = null
    private var Disposable7: Disposable? = null
    private var Disposable8: Disposable? = null
    private var iGetCompanyListModel: IGetCompanyListModel? = null
    private var iGetLocationInputModel: IGetLocationInputModel? = null
    private var iGetIndustryListModel: IGetIndustryListModel? = null
    private var iGetProductListModel: IGetProductListModel? = null
    private var iGetNameDetailModel: IGetNameDetailModel? = null
    private var iGetLawyerCompanyModel: IGetLawyerCompanyModel? = null
    private var iGetLawyerModel: IGetLawyerModel? = null
    private var iUserAndCompanyNameAutoCompleteModel: IUserAndCompanyNameAutoCompleteModel? = null

    fun getCompanyList(text: String) {
        if (iGetCompanyListModel == null) {
            iGetCompanyListModel = GetCompanyListModel()
        }
        Disposable8 = iGetCompanyListModel!!.getCompanyList(text).subscribe({
            if (iGetCompanyList != null) {
                iGetCompanyList!!.getCompanyListSuccess(it)
            }
        }, {
            if (iGetCompanyList != null) {
                iGetCompanyList!!.getCompanyListFail(it)
            }
        })
    }

    fun getLawyer(name: String, law: String) {
        if (iGetLawyerModel == null) {
            iGetLawyerModel = GetLawyerModel()
        }
        Disposable7 = iGetLawyerModel!!.getLawyer(name, law).subscribe({
            if (iGetLawyer != null) {
                iGetLawyer!!.getLawyerSuccess(it)
            }
        }, {
            if (iGetLawyer != null) {
                iGetLawyer!!.getLawyerFail(it)
            }
        })
    }

    fun getLawyerCompany(name: String) {
        if (iGetLawyerCompanyModel == null) {
            iGetLawyerCompanyModel = GetLawyerCompanyModel()
        }
        Disposable6 = iGetLawyerCompanyModel!!.getLawyerCompany(name).subscribe({
            if (iGetLawyerCompany != null) {
                iGetLawyerCompany!!.getLawyerCompanySuccess(it)
            }
        }, {
            if (iGetLawyerCompany != null) {
                iGetLawyerCompany!!.getLawyerCompanyFail(it)
            }
        })
    }

    fun getNameDetail(name: String) {
        if (iGetNameDetailModel == null) {
            iGetNameDetailModel = GetNameDetailModel()
        }
        Disposable5 = iGetNameDetailModel!!.getNameDetail(name).subscribe({
            if (iGetNameDetail != null) {
                iGetNameDetail!!.getNameDetailSuccess(it)
            }
        }, {
            if (iGetNameDetail != null) {
                iGetNameDetail!!.getNameDetailFail(it)
            }
        })
    }

    fun getLocationInput(place: String) {
        if (iGetLocationInputModel == null) {
            iGetLocationInputModel = GetLocationInputModel()
        }
        Disposable = iGetLocationInputModel!!.getLocationInput(place).subscribe({
            if (iGetLocationInput != null) {
                iGetLocationInput!!.getLocationInputSuccess(it)
            }
        }, {
            if (iGetLocationInput != null) {
                iGetLocationInput!!.getLocationInputFail(it)
            }
        })
    }

    fun getIndustryList(industry: String) {
        if (iGetIndustryListModel == null) {
            iGetIndustryListModel = GetIndustryListModel()
        }
        Disposable2 = iGetIndustryListModel!!.getIndustryList(industry).subscribe({
            if (iGetIndustryList != null) {
                iGetIndustryList!!.getIndustryListSuccess(it)
            }
        }, {
            if (iGetIndustryList != null) {
                iGetIndustryList!!.getIndustryListFail(it)
            }
        })
    }

    fun getProductList(product: String) {
        if (iGetProductListModel == null) {
            iGetProductListModel = GetProductListModel()
        }
        Disposable3 = iGetProductListModel!!.getProductList(product).subscribe({
            if (iGetProductList != null) {
                iGetProductList!!.getProductListSuccess(it)
            }
        }, {
            if (iGetProductList != null) {
                iGetProductList!!.getProductListFail(it)
            }
        })
    }

    fun userAndCompanyNameAutoComplete(type: Int, userName: String, companyName: String) {
        if (iUserAndCompanyNameAutoCompleteModel == null) {
            iUserAndCompanyNameAutoCompleteModel = UserAndCompanyNameAutoCompleteModel()
        }
        Disposable4 = iUserAndCompanyNameAutoCompleteModel!!.userAndCompanyNameAutoComplete(userName, companyName).subscribe({
            if (iUserAndCompanyNameAutoComplete != null) {
                if (type == 13) {
                    iUserAndCompanyNameAutoComplete!!.userAndCompanyNameAutoCompleteUserNameSuccess(it)
                } else if (type == 14) {
                    iUserAndCompanyNameAutoComplete!!.userAndCompanyNameAutoCompleteUserCompanyNameSuccess(it)
                }
            }
        }, {
            if (iUserAndCompanyNameAutoComplete != null) {
                iUserAndCompanyNameAutoComplete!!.userAndCompanyNameAutoCompleteUserFail(it)
            }
        })
    }

    fun destory() {
        if (Disposable != null) {
            Disposable!!.dispose()
            Disposable = null
        }
        if (Disposable2 != null) {
            Disposable2!!.dispose()
            Disposable2 = null
        }
        if (Disposable3 != null) {
            Disposable3!!.dispose()
            Disposable3 = null
        }
        if (Disposable4 != null) {
            Disposable4!!.dispose()
            Disposable4 = null
        }
        if (Disposable5 != null) {
            Disposable5!!.dispose()
            Disposable5 = null
        }
        if (Disposable6 != null) {
            Disposable6!!.dispose()
            Disposable6 = null
        }
        if (Disposable7 != null) {
            Disposable7!!.dispose()
            Disposable7 = null
        }
        if (Disposable8 != null) {
            Disposable8!!.dispose()
            Disposable8 = null
        }
        if (iGetNameDetail != null) {
            iGetNameDetail = null
        }
        if (iGetLocationInput != null) {
            iGetLocationInput = null
        }
        if (iGetIndustryList != null) {
            iGetIndustryList = null
        }
        if (iGetProductList != null) {
            iGetProductList = null
        }
        if (iUserAndCompanyNameAutoComplete != null) {
            iUserAndCompanyNameAutoComplete = null
        }
        if (iGetLawyerCompany != null) {
            iGetLawyerCompany = null
        }
        if (iGetLawyer != null) {
            iGetLawyer = null
        }
        if (iGetCompanyList != null) {
            iGetCompanyList = null
        }
    }
}