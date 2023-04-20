package edu.quinnipiac.ser210.stockmarketmastery


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * API interface responsible for retrieving data from the API
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
interface ApiInterface {
    // get data from quote endpoint
    @GET("quote")
    @Headers(
        "X-RapidAPI-Key: 0e4746146amsh235878ec1c91e87p1daba7jsn52d8e303af12",
        "X-RapidAPI-Host: twelve-data1.p.rapidapi.com"
    )
    fun stockSearchPrice(
        @Query("symbol") query: String,
    ): Call<QuoteStock>
    // get data from logo endpoint
    @GET("logo")
    @Headers(
        "X-RapidAPI-Key: 0e4746146amsh235878ec1c91e87p1daba7jsn52d8e303af12",
        "X-RapidAPI-Host: twelve-data1.p.rapidapi.com"
    )
    fun stockLogos(
        @Query("symbol") query: String,
    ): Call<LogoStock>

    // get data from the price endpoint
    @GET("price")
    @Headers(
        "X-RapidAPI-Key: 0e4746146amsh235878ec1c91e87p1daba7jsn52d8e303af12",
        "X-RapidAPI-Host: twelve-data1.p.rapidapi.com"
    )
    fun stockRealTimePrice(
        @Query("symbol") query: String,
    ): Call<RealTimePrice>

    companion object{
        // pass in URL for api
        var Base_URL = "https://twelve-data1.p.rapidapi.com/"

        // create function that will build retrofit
        fun create(): ApiInterface {
            // create retrofit
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Base_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}

