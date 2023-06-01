package com.blackbox.dashmesh.ui.utils
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.blackbox.dashmesh.R
class  MarkerOverlayView(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs){
    var textLocation: TextView
    val textDT: TextView
    init {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_marker_info, null)
        textLocation = view.findViewById(R.id.Location)
        textDT = view.findViewById(R.id.DateAndTime)
    }
    fun setLocationAndTime(location:String,Date:String){
        textLocation.text = "Location: "+location
        textDT.text = "Date and Time: "+Date
    }
}
