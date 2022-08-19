package com.example.earthquake

import android.app.Activity
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor


class CustomAdapter(
    private val listner: Eclick,
    private val context: Context
) : RecyclerView.Adapter<EarthVH>() {
    private fun formatDate(dateObject: Date): String {
        val dateFormat = SimpleDateFormat("LLL dd, yyyy")
        return dateFormat.format(dateObject)
    }

    private fun formatTime(dateObject: Date): String {
        val timeFormat = SimpleDateFormat("h:mm a")
        return timeFormat.format(dateObject)
    }
    fun getMagnitudeColor(i: String): Int {
        val magnitudeColorResourceId: Int
        val magnitudeFloor = floor(i.toDouble()).toInt()
        when (magnitudeFloor) {
            0, 1 -> magnitudeColorResourceId = R.color.magnitude1
            2 -> magnitudeColorResourceId = R.color.magnitude2
            3 -> magnitudeColorResourceId = R.color.magnitude3
            4 -> magnitudeColorResourceId = R.color.magnitude4
            5 -> magnitudeColorResourceId = R.color.magnitude5
            6 -> magnitudeColorResourceId = R.color.magnitude6
            7 -> magnitudeColorResourceId = R.color.magnitude7
            8 -> magnitudeColorResourceId = R.color.magnitude8
            9 -> magnitudeColorResourceId = R.color.magnitude9
            else -> magnitudeColorResourceId = R.color.magnitude10plus
        }
        return ContextCompat.getColor(context , magnitudeColorResourceId)
    }
    private val title: ArrayList<EarthData> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem, parent, false)
        val vh = EarthVH(view)
        view.setOnClickListener {
            listner.onclick(title[vh.adapterPosition])
        }
        return vh

    }

    override fun getItemCount(): Int {
        return title.size
    }

    override fun onBindViewHolder(holder: EarthVH, position: Int) {
        val current = title[position]
        holder.mag.text = current.getMag()
        val magnitudeCircle = holder.mag.background as GradientDrawable
        val magnitudecolor: Int = getMagnitudeColor(current.getMag()!!)
        magnitudeCircle.setColor(magnitudecolor)
        var primarylocation: String
        var locationoffset: String
        val C: String? = current.getCity()
        if (C?.contains("of")!!) {
            var Parts: List<String> = C.split("of")

            holder.place.text = Parts[0] + "of"
            holder.place2.text = Parts[1]
        } else {
            holder.place.text = "Near The"
            holder.place2.text = C
        }
        holder.date.text = formatDate(Date(current.getDate()))
        holder.time.text = formatTime(Date(current.getDate()))
    }

    fun updateNews(upN: ArrayList<EarthData>) {
        title.clear()
        title.addAll(upN)
        notifyDataSetChanged()
    }

}

class EarthVH(view: View) : RecyclerView.ViewHolder(view) {
    val mag = view.findViewById<TextView>(R.id.mag)
    val place = view.findViewById<TextView>(R.id.place)
    val place2 = view.findViewById<TextView>(R.id.place1)
    val date = view.findViewById<TextView>(R.id.date)
    val time = view.findViewById<TextView>(R.id.time)


}

interface Eclick {
    fun onclick(item: EarthData) {

    }
}