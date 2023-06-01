package com.blackbox.dashmesh.ui.ui.settings

import com.blackbox.dashmesh.ui.data.models.CommonResponseModel

interface SettingsView {
    fun getSetting(liveTrackingResponse: CommonResponseModel)

    fun isNetworkConnected():Boolean

    fun isShowLoading():Boolean

    fun isHideLoading():Boolean

    fun showErrorMessage(string: String)
}