package com.blackbox.dashmesh.ui.ui.vehicleStatus

import com.google.gson.Gson
import com.blackbox.dashmesh.ui.data.DataManager
import com.blackbox.dashmesh.ui.data.models.VehicleLiveStatusModel
import com.blackbox.dashmesh.ui.data.network.ApiError
import com.blackbox.dashmesh.ui.data.network.api.ApiHelper

class VehcileStatusPresenterImpl(
    private val mVehicleStatusView: VehicleStatusView,
    private val mDataManager: DataManager
) : VehicleStatusPresenter {
    override fun hitLiveStatusApi(
        custid: String,
        beginDate: String,
        endDate: String,
        StatusCode: String,
        sEcho: String,
        iDisplayStart: Int,
        iDisplayLength: Int,
        sSearch: String,
        iSortCol_0: String,
        sSortDir_0: String
    ) {
        when {
            mVehicleStatusView.isNetworkConnected() -> {
                mVehicleStatusView.isShowLoading()
                mDataManager.apiCallToGetLiveTracking(custid,beginDate,endDate,StatusCode,sEcho,iDisplayStart,iDisplayLength,sSearch,iSortCol_0,sSortDir_0, object : ApiHelper.ApiListener {
                    override fun onSuccess(commonResponse: Any) {
                        mVehicleStatusView.isHideLoading()

                        val getVehicleStatusResponse = Gson().fromJson(
                            Gson().toJson(commonResponse), VehicleLiveStatusModel::class.java
                        )
                        mVehicleStatusView.getVehicleStatus(getVehicleStatusResponse)
                    }

                    override fun onError(errorId: Any) {
                        mVehicleStatusView.isHideLoading()
                        mVehicleStatusView.showErrorMessage("API Error. Please try after sometime")
                    }

                    override fun onFailure(apiError: ApiError?, throwable: Throwable?) {
                        mVehicleStatusView.isHideLoading()
                        mVehicleStatusView.showErrorMessage("API Error. Please try after sometime")
                    }

                })
            }
        }
    }
}