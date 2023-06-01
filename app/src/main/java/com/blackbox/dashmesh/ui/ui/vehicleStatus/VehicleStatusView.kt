package com.blackbox.dashmesh.ui.ui.vehicleStatus

import com.blackbox.dashmesh.ui.data.models.LiveTrackingResponse
import com.blackbox.dashmesh.ui.data.models.VehicleLiveStatusModel

interface VehicleStatusView {

    fun getVehicleStatus(liveTrackingResponse: VehicleLiveStatusModel)

    fun isNetworkConnected():Boolean

    fun isShowLoading():Boolean

    fun isHideLoading():Boolean

    fun showErrorMessage(string: String)
}