package com.blackbox.dashmesh.ui.ui.vehicleStatus.vehicleDetails

import com.blackbox.dashmesh.ui.data.models.VehicleDetailResponse

interface VehicleDetailView {

    fun getVehicleDetails(vehicleDetailResponse: VehicleDetailResponse)

    fun isNetworkConnected(): Boolean

    fun isShowLoading(): Boolean

    fun isHideLoading(): Boolean

    fun showErrorMessage(string: String)
}