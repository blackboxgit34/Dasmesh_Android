package com.blackbox.dashmesh.ui.ui.vehicleStatus

interface VehicleStatusPresenter {

    fun hitLiveStatusApi(custid:String,beginDate :String,endDate: String,StatusCode:String,sEcho:String,iDisplayStart:Int
                         ,iDisplayLength:Int,sSearch:String,iSortCol_0:String,sSortDir_0:String)
}