package com.bove.martin.pluspagos.data

import com.bove.martin.pluspagos.data.retrofit.AuthInterceptor
import com.bove.martin.pluspagos.data.retrofit.MercadoPagoServices
import com.bove.martin.pluspagos.presentation.utils.Constants
import com.google.android.material.snackbar.BaseTransientBottomBar.BehaviorDelegate
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory






/**
 * Created by Martín Bove on 31-Aug-20.
 * E-mail: mbove77@gmail.com
 */

@RunWith(MockitoJUnitRunner::class)
class MercadoPagoRepositoryTest {


    @Before
    @Throws(Exception::class)
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun getPaymentsMethods() = runBlocking<Unit> {

    }



    @Test
    fun getCardIssuers() {
    }

    @Test
    fun getInstallmentsOptions() {
    }

    @Test
    fun getMercadoPagoServices() {
    }
}