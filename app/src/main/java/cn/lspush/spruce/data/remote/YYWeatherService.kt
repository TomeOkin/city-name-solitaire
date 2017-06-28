package cn.lspush.spruce.data.remote

import cn.lspush.spruce.BuildConfig
import cn.lspush.spruce.data.model.City
import com.google.gson.GsonBuilder
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 *
 */
class YYWeatherService {

    companion object {
        val API_URL: String get() = "http://api.yytianqi.com"

        val gson = GsonBuilder().create()
        val okhttp = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .client(okhttp)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .validateEagerly(BuildConfig.DEBUG)
                .build()
        val apis = retrofit.create(Apis::class.java)
    }

    interface Apis {
        @GET("citylist/id/2")
        fun cities() : Flowable<City>
    }
}