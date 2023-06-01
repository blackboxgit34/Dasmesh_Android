package com.blackbox.dashmesh.ui.ui.settings

import com.blackbox.dashmesh.ui.data.DataManager
import com.blackbox.dashmesh.ui.data.models.CommonResponseModel
import com.blackbox.dashmesh.ui.data.models.VehicleLiveStatusModel
import com.blackbox.dashmesh.ui.data.network.ApiError
import com.blackbox.dashmesh.ui.data.network.api.ApiHelper
import com.blackbox.dashmesh.ui.ui.vehicleStatus.VehicleStatusView
import com.google.gson.Gson

class SettingsPresenterimpl(
    private val mVehicleStatusView: SettingsView,
    private val mDataManager: DataManager
) : SettingPresenter{
    override fun hitSetWidthApi(BBID:String,ModelId: String) {
        when {
            mVehicleStatusView.isNetworkConnected() -> {
                mVehicleStatusView.isShowLoading()
                mDataManager.apiCallSetHarvestorCutterWidth(BBID,ModelId,object : ApiHelper.ApiListener {
                    override fun onSuccess(commonResponse: Any) {
                        mVehicleStatusView.isHideLoading()
                        val getVehicleStatusResponse = Gson().fromJson(
                            Gson().toJson(commonResponse), CommonResponseModel::class.java
                        )
                        mVehicleStatusView.getSetting(getVehicleStatusResponse)
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