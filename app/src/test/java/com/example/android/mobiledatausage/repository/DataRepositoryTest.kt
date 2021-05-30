package com.example.android.mobiledatausage.repository

import com.example.android.mobiledatausage.network.RemoteApiService
import com.example.android.mobiledatausage.util.DataParser
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class DataRepositoryTest {
    private lateinit var mockServer: MockWebServer
    private lateinit var mockClient: OkHttpClient
    private lateinit var mockApi: RemoteApiService


    @Before
    fun setUp() {

        mockServer = MockWebServer()
        mockClient = OkHttpClient.Builder().build()
        mockApi = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(mockServer.url("/"))
            .client(mockClient)
            .build()
            .create(RemoteApiService::class.java)
    }

    @Test
    fun response_invalid() {
        mockServer.enqueue(
            MockResponse().setResponseCode(409).setBody(response_error)
        )

        val expectedSize = 0

        runBlocking {
            val response = mockApi.getData()
            assertEquals(true, !response.isSuccessful)
        }
    }

    @Test
    fun response_valid_2008() {
        mockServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(response_valid_2008)
        )

        val expectedSize = 1

        runBlocking {
            val response = mockApi.getData()
            if(response.isSuccessful) {
                val actual = DataParser.parseResponse(response.body().toString())
                assertEquals(expectedSize, actual.size)
            }
        }

    }

    @Test
    fun response_valid_2008to2009() {
        mockServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(response_valid_2008to2010)
        )

        val expectedSize = 3

        runBlocking {
            val response = mockApi.getData()
            if(response.isSuccessful) {
                val actual = DataParser.parseResponse(response.body().toString())
                assertEquals(expectedSize, actual.size)
            }
        }

    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

}