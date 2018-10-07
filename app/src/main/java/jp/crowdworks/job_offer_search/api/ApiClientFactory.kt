package jp.crowdworks.job_offer_search.api

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClientFactory {
    private lateinit var retrofit: Retrofit

    fun initializeWith(context: Context) {
        val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()

        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl("https://crowdworks.jp/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()
    }

    fun createJobOfferSearchApi() = retrofit.create(JobOfferSearchApi::class.java)
}
