package com.example.earthquake

import MySingleton
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), Eclick {
    private lateinit var adapter : CustomAdapter
    val url : String  = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=0&limit=100"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val rc = findViewById<RecyclerView>(R.id.recyclerView)


        rc.layoutManager = LinearLayoutManager(this)
        fetchdata()
        adapter = CustomAdapter(this,applicationContext)
        rc.adapter = adapter
    }


    fun fetchdata() {
        val jReq = JsonObjectRequest(Request.Method.GET,url,null, {

            val ja = it.getJSONArray("features")
            val ls = ArrayList<EarthData>()

            for (i in 0 until ja.length()){
                val jao = ja.getJSONObject(i)
                val jp = jao.getJSONObject("properties")
                val E : EarthData = EarthData(
                    jp.getString("mag"),
                    jp.getString("place"),
                    jp.getLong("time"),
                    jp.getString("url")
                )
              ls.add(E)
            }
            adapter.updateNews(ls)

        }, {

        })
        MySingleton.getInstance(this).addToRequestQueue(jReq)
    }
    override fun onclick(item: EarthData) {
        super.onclick(item)
        Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show()
        val url = item.getUrl()
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

}