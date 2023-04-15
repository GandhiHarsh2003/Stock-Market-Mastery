package edu.quinnipiac.ser210.stockmarketmastery


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


interface ApiInterface {

    @GET("quote")
    @Headers(
        "X-RapidAPI-Key: 0e4746146amsh235878ec1c91e87p1daba7jsn52d8e303af12",
        "X-RapidAPI-Host: twelve-data1.p.rapidapi.com"
    )
    fun stockSearchPrice(
        @Query("symbol") query: String,
    ): Call<StockDetailsData>

    @GET("logo")
    @Headers(
        "X-RapidAPI-Key: 0e4746146amsh235878ec1c91e87p1daba7jsn52d8e303af12",
        "X-RapidAPI-Host: twelve-data1.p.rapidapi.com"
    )
    fun stockLogos(
        @Query("symbol") query: String,
    ): Call<StockDetailsData>

    @GET("price")
    @Headers(
        "X-RapidAPI-Key: 0e4746146amsh235878ec1c91e87p1daba7jsn52d8e303af12",
        "X-RapidAPI-Host: twelve-data1.p.rapidapi.com"
    )
    fun stockRealTimePrice(
        @Query("symbol") query: String,
    ): Call<StockDetailsData>

    companion object{
        // pass in URL for api
        var Base_URL = "https://twelve-data1.p.rapidapi.com/"

        // create function that will build retrofit
        fun create(): ApiInterface {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            // Create the OkHttpClient with the logging interceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Base_URL)
                .client(client)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}

