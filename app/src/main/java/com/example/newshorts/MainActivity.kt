package com.example.newshorts

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private  lateinit var mAdapter: RecyclerVideosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchData()
        mAdapter = RecyclerVideosAdapter(this)
        recyclerView.adapter = mAdapter

    }

    private fun fetchData(){

        val queue = Volley.newRequestQueue(this)
        val url = "https://pixabay.com/api/videos/?key=30321102-73bc94ca28ec5b4f0857a1eba&q=yellow+flowers&pretty=true"

        // Request a response from the provided URL.

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->

//                Log.d("RES",response.toString())
                val jsonArray = response.getJSONArray("hits")
//                Log.d("RES",jsonArray.toString())
                val arrayOfVideos = ArrayList<String>()
//                Log.d("RES",jsonArray.getJSONObject(0).toString())
                for(i in 0 until jsonArray.length()){
                    val urlToVideo = jsonArray.getJSONObject(i).getJSONObject("videos").getJSONObject("small").getString("url")
                    arrayOfVideos.add(urlToVideo)
                }
                mAdapter.updateVideos(arrayOfVideos)
            },
            { error ->
                Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()
            }
        )

//        queue.add(jsonObjectRequest)
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}