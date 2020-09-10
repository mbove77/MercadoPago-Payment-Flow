package com.bove.martin.pluspagos.presentation

import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityViewModelTest  {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mpRepository: MercadoPagoRepository

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        mpRepository = mock()
        runBlocking {
            whenever(mpRepository.getPaymentsMethods())
        }
        viewModel = MainActivityViewModel(mpRepository)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun getPaymentsMethods() = runBlocking {
        val response = viewModel.getPaymentsMethods()
        delay(12)
        verify(viewModel.getPaymentsMethods())
    }

    @Test
    fun getCardIssuers() {
    }

    @Test
    fun getInstallmentsOptions() {
    }

    @Test
    fun getUserAmount() {
    }

    @Test
    fun setUserAmount() {
    }

    @Test
    fun getUserPaymentSelection() {
    }

    @Test
    fun setUserPaymentSelection() {
    }

    @Test
    fun getUserCardIssuer() {
    }

    @Test
    fun setUserCardIssuer() {
    }

    @Test
    fun getUserInstallmentSelection() {
    }

    @Test
    fun setUserInstallmentSelection() {
    }

    @Test
    fun clearUserSelections() {
    }

    @Test
    fun getPaimentsMethods() {
    }

    @Test
    fun testGetCardIssuers() {
    }

    @Test
    fun getInstallments() {
    }
}