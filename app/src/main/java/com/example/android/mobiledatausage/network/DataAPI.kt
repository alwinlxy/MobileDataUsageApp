package com.example.android.mobiledatausage.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://data.gov.sg"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface RemoteApiService {
    @GET("api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&q=2008+2009+2010+2011+2012+2013+2014+2015+2016+2017+2018")
    suspend fun getData(): Response<String>
}

object DataApi {
    val retrofitService: RemoteApiService by lazy { retrofit.create(RemoteApiService::class.java) }
}