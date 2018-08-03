package com.example.techjini.loginapplicationsimple

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object SimpleAPICall {

    private var apiService: SimpleAPI? = null

    private fun createApiService(context: Context): SimpleAPI {

        val interceptorLog = HttpLoggingInterceptor()

        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptorLog)
        val client = builder.build()


        val retrofit = Retrofit.Builder().baseUrl("http://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()

        return retrofit.create(SimpleAPI::class.java)
    }

    @Synchronized
    fun getSimpleAPI(context: Context): SimpleAPI ?{
        if (apiService == null) {
            apiService = createApiService(context)
        }

        return apiService
    }
}
