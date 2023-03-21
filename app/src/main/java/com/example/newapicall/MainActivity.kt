package com.example.newapicall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("/comments")
    fun getData(): Call<List<MyDataItem>>
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMyData()

    }

    private fun getMyData() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofit.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?> ,
                response: Response<List<MyDataItem>?>
            ) {
                Log.d("MainActivity" , "The repo work? ${response.isSuccessful} ")
                val responseBody = response.body()
                val myStringBuiler = StringBuilder()
                if (responseBody != null) {
                    for (myData in responseBody) {
                        myStringBuiler.append(myData.id)
                        myStringBuiler.append("\n")
                        myStringBuiler.append(myData.name)
                        myStringBuiler.append("\n")
                    }
                } else {
                    Log.d("MainActivity" , "Error")
                }
                val text = findViewById<TextView>(R.id.textView)
                text.text = myStringBuiler
            }

            override fun onFailure(call: Call<List<MyDataItem>?> , t: Throwable) {
                Log.d("MainActivity" , "ERROR! ${t.message}")
            }

        })
    }
}
