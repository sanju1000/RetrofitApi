package com.example.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Apiinterface::class.java)

        val retrofiData = retrofitBuilder.getProductData()
        retrofiData.enqueue(object : Callback<MyData?> {

            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {

                val responseBody = response.body()
                val productList = responseBody?.products!!

                val collectDataInSB = java.lang.StringBuilder()
                for(myData in productList){
                    collectDataInSB.append(myData.title + " ")
                }

                val tv = findViewById<TextView>(R.id.textview)
                tv.text = collectDataInSB
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("Main Activity","onFailure:" + t.message)
            }
        })
    }
}