package com.blackbox.dashmesh.ui.ui.routePlayback

import com.blackbox.dashmesh.ui.data.models.DrivingBehaviourRouteDataModel
import com.blackbox.dashmesh.ui.data.models.RoutePlaybackResponseModel

interface RoutePlaybackView {

    fun getRoutePlaybackResponse(routePlaybackResponseModel: RoutePlaybackResponseModel)

    fun isNetworkConnected(): Boolean

    fun isShowLoading(): Boolean

    fun isHideLoading(): Boolean

    fun showErrorMessage(string: String)
}