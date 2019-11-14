package com.test.hamonschool.utils

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class NetworkTask {

    private var retrofit: Retrofit
    var apiCalls: ApiCalls

    init {

        //create a client and set Timeout
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(10000, TimeUnit.SECONDS)
            .readTimeout(10000, TimeUnit.SECONDS)
            .writeTimeout(10000, TimeUnit.SECONDS)
            .build()


        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        apiCalls = retrofit.create(ApiCalls::class.java)
    }


    /*interfaces*/
    interface ApiCalls {

//        @GET("posts")
//        fun getPosts(@Query("cursor") cursor: String, @Query("charity_id") charity: Int?): Call<PostModel>
    }

}
