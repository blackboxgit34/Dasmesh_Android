package com.blackbox.dashmesh.ui.ui.settings

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request.Method
import com.blackbox.dashmesh.databinding.ActivitySettingsBinding
import com.blackbox.dashmesh.ui.adapters.AllIconsAdapter
import com.blackbox.dashmesh.ui.adapters.CustSpinnerAdapter
import com.blackbox.dashmesh.ui.data.DataManagerImpl
import com.blackbox.dashmesh.ui.data.db.CommonData.getCustIdFromDB
import com.blackbox.dashmesh.ui.data.models.*
import com.blackbox.dashmesh.ui.data.network.RestClient
import com.blackbox.dashmesh.ui.data.network.VolleyApiCaller
import com.blackbox.dashmesh.ui.retofit.Retrofit2
import com.blackbox.dashmesh.ui.retofit.RetrofitResponse
import com.blackbox.dashmesh.ui.utils.CommonUtil
import com.blackbox.dashmesh.ui.utils.Constants
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class SettingsActivity : AppCompatActivity(),SettingsView, RetrofitResponse {
    private lateinit var binding:ActivitySettingsBinding
    var list: ArrayList<AllIconsModel> = ArrayList()
    private lateinit var mPresenter: SettingPresenter
    var vehicleModel = ArrayList<AllVehicleModel>()
    var vehicleList = ArrayList<String>()
    var thresherModels = ArrayList<Result>()
    var thresherModelList = ArrayList<String>()
    var vehicleId = ""
    var vehicleName = ""
    var modelId = ""
    var modelName = ""
    var width = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mPresenter = SettingsPresenterimpl(this, DataManagerImpl(RestClient.getRetrofitBuilderForTrackMaster()))
        getAllModels()
        getAllVehicles()
        binding.toolbar.tvTitle.text = "Setting & Customization"
        binding.toolbar.ivMenu.visibility = View.GONE
        binding.toolbar.ivBack.visibility = View.VISIBLE
        binding.toolbar.ivBell.visibility = View.GONE
        binding.toolbar.ivSort.visibility = View.GONE
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }
        binding.save.setOnClickListener {
            if(modelId!=""&&modelName!=""&&vehicleId!=""&&vehicleName!="") {
                mPresenter.hitSetWidthApi(vehicleId,modelId)
            }
            else{
                if(modelId==""&&modelName==""){
                    Constants.alertDialog(this, "Please select thresher model first")
                }
                else {
                    Constants.alertDialog(this, "Please select vehicle first")
                }
            }
        }
        // on radio button check change
//        if(binding.idRBLight.isChecked) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
//        else if(binding.idRBDark.isChecked) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        }
//        else
//        {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//        }

       // getVehicleIcons()
    }

    private fun getAllModels() {
        val responseModelClass = ThreasherModelsModel::class.java
        VolleyApiCaller.makeApiCallTrackmaster(this,
             Constants.GET_ALL_MODELS,
             Method.GET,
            null,
             responseModelClass,
            true,
            0,
            30000)
        { response, error ->
            if (error != null) {
                // Handle the error here
            }
            else if (response != null) {
                // Use the deserialized response object here
                try {
                    thresherModelList.clear()
                    val data = response
                    for (i in 0 until data.result.size) {
                        val obj = data.result[i]
                        thresherModels.add(Result(obj.CutterWidth, obj.Id, obj.ModelId,obj.ModelName))
                        thresherModelList.add(obj.ModelName)
                    }
                    spinThreshers()
                }
                catch (_:java.lang.Exception){
                }
            }
        }
    }

    override fun onServiceResponse(requestCode: Int, response: Response<ResponseBody>?) {
        when(requestCode){
            Constants.REQ_LOCATION_ON_MAP-> {
                if (response!!.isSuccessful) {
                        try {
                            vehicleList.clear()
                            val data = JSONArray(response.body()!!.string())
                            for (i in 0 until data.length()) {
                                val obj = data.getJSONObject(i)
                                val model = AllVehicleModel()
                                model.bbid = obj.getString("BoxId")
                                model.vehname = obj.getString("VehName")
                                vehicleList.add(obj.getString("VehName"))
                                vehicleModel.add(model)
                                spinVehicles()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
            }
        }
    }

    private fun getAllVehicles() {
            Retrofit2(
                this,
                this,
                Constants.REQ_LOCATION_ON_MAP,
                Constants.LOCATION_ON_MAP + "id=" + getCustIdFromDB()
            ).callServicehitec(true)
    }

//    private fun getVehicleIcons() {
//        Retrofit2(this, this, Constants.REQ_GET_ALL_VEHICLE_ICONS,
//            Constants.GET_ALL_VEHICLE_ICONS.toString())
//            .callService(true)
//    }

    private fun spinThreshers() {
        val adapter = CustSpinnerAdapter.getAdapter(this, thresherModelList)
        binding.spThreshers.setAdapter(adapter) //setting the adapter data into the AutoCompleteTextView

        binding.spThreshers.setOnItemClickListener { parent, view, position, id ->
            val selection = parent.getItemAtPosition(position) as String
            var pos = -1
            for (i in thresherModelList.indices) {
                if (thresherModelList[i] == selection) {
                    pos = i
                    break
                }
            }
            modelId = thresherModels[pos].Id.toString()
            modelName = thresherModels[pos].ModelName
            width = thresherModels[pos].CutterWidth.toString()
        }

        binding.spThreshers.setOnFocusChangeListener { v, hasFocus -> if (hasFocus) binding.spThreshers.showDropDown() }

        binding.spThreshers.setOnTouchListener { v, event ->
            binding.spThreshers.showDropDown()
            false
        }
    }

    private fun spinVehicles() {
        val adapter = CustSpinnerAdapter.getAdapter(this, vehicleList)
        binding.spVehicles.setAdapter(adapter) //setting the adapter data into the AutoCompleteTextView

        binding.spVehicles.setOnItemClickListener { parent, view, position, id ->
            val selection = parent.getItemAtPosition(position) as String
            var pos = -1
            for (i in vehicleList.indices) {
                if (vehicleList[i] == selection) {
                    pos = i
                    break
                }
            }
            vehicleId = vehicleModel[pos].bbid
            vehicleName = vehicleModel[pos].vehname
            Log.e("vehicleId ",vehicleId)
            Log.e("vehicleName ",vehicleName)
        }

        binding.spVehicles.setOnFocusChangeListener { v, hasFocus -> if (hasFocus) binding.spVehicles.showDropDown() }

        binding.spVehicles.setOnTouchListener { v, event ->
            binding.spVehicles.showDropDown()
            false
        }
    }

    override fun getSetting(liveTrackingResponse: CommonResponseModel) {
       if(liveTrackingResponse.result == "Sucess"){
           Toast.makeText(this,"Successfully saved.",Toast.LENGTH_SHORT).show()
           finish()
       }
        else{
           Toast.makeText(this,"Something wrong.",Toast.LENGTH_SHORT).show()
        }
    }

    override fun isNetworkConnected(): Boolean {
        return true
    }

    override fun isShowLoading(): Boolean {
        binding.loader.progressLayout.visibility = View.VISIBLE
        return true
    }

    override fun isHideLoading(): Boolean {
        binding.loader.progressLayout.visibility = View.GONE
        return true
    }

    override fun showErrorMessage(string: String) {
        CommonUtil.alertDialogWithOkOnly(this, "", string)
    }

}