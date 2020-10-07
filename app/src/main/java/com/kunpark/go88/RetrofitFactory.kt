package com.crewsoft.crewcloud.otp.services

import com.crewsoft.crewcloud.otp.BuildConfig
import com.crewsoft.crewcloud.otp.utils.Constants
import com.crewsoft.crewcloud.otp.utils.DazoneApplication
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {

    /**Add Header *{AccessToken}*/
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain
                .request().url
                .newBuilder()
                .build()

        val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

        chain.proceed(newRequest)
    }


    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    //Not loggin the authkey if not Debug
    private val client =
            if (BuildConfig.DEBUG) {
                OkHttpClient().newBuilder()
                        .addInterceptor(authInterceptor)
                        .addInterceptor(loggingInterceptor)
                        .build()
            } else {
                OkHttpClient().newBuilder()
                        .addInterceptor(loggingInterceptor)
                        .addInterceptor(authInterceptor)
                        .build()
            }

    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    private fun getDomain(): String {
        val domain = DazoneApplication.getInstance().sharedPref.getString(Constants.DOMAIN, "")
                ?: ""
        return if (domain.startsWith("http")) domain else "http://$domain"
    }

    val apiService: DazoneService = retrofit(getDomain())
            .create(DazoneService::class.java)

    val apiServiceNonBaseUrl: DazoneService = retrofit("http://mobileupdate.crewcloud.net")
            .create(DazoneService::class.java)
}

