package ir.matiran.kotlin_sample

import ir.matiran.kotlin_sample.ProfileListInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("https://api.exir.io/v1/trades/")
    fun getCurrencyApi(): Call<ProfileListInfo?>?

    companion object {

        var BASE_URL = "https://api.exir.io/v1/trades/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }

}