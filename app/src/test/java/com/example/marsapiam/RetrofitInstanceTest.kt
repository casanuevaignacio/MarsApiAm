package com.example.marsapiam



import Model.Remote.MarsApi

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals


class RetrofitInstanceTest {



    private lateinit var mockWebServer: MockWebServer

    private lateinit var retrofit: Retrofit




    @Before
    fun  setUp(){

        mockWebServer = MockWebServer()
        mockWebServer.start()

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

    @After
    fun tearDown(){

        mockWebServer.shutdown()
    }

    @Test

    fun testRetrofitBaseUrl(){

        // Crear la API con la instancia de Retrofit de prueba
        val marApi = retrofit.create(MarsApi::class.java)
        // Verificar que el baseUrl sea el correcto
        val actualBaseUrl = retrofit.baseUrl().toString()
        val expectedBaseUrl = mockWebServer.url("/").toString()


        assertEquals(expectedBaseUrl,actualBaseUrl)

    }














}