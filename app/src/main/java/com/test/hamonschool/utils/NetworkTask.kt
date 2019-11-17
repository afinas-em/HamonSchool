package com.test.hamonschool.utils

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.test.hamonschool.models.ClassroomResult
import com.test.hamonschool.models.StudentsResult
import com.test.hamonschool.models.SubjectsResult
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


class NetworkTask {

    private var retrofit: Retrofit
    var apiCalls: ApiCalls


    init {

        val networkInterceptor = Interceptor {

            val original = it.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .build()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            return@Interceptor it.proceed(request)
        };

        //create a client and set Timeout
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(10000, TimeUnit.SECONDS)
            .readTimeout(10000, TimeUnit.SECONDS)
            .writeTimeout(10000, TimeUnit.SECONDS)
            .addInterceptor(networkInterceptor)
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

        @GET("students")
        fun getStudentsList(): Call<StudentsResult>

        @GET("subjects")
        fun getSubjectsList(): Call<SubjectsResult>

        @GET("classrooms")
        fun getClassrooms(): Call<ClassroomResult>
    }

}
