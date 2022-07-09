package com.bove.martin.pluspagos.domain.usercase

import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.Payment
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Created by Martín Bove on 8/7/2022.
 * E-mail: mbove77@gmail.com
 */
class GetPaymentsMethodsUseCaseTest {
    @RelaxedMockK
    private lateinit var mercadoPagoRepository: MercadoPagoRepository
    private lateinit var getPaymentsMethodsUseCase: GetPaymentsMethodsUseCase
    private val validAmount = 15400.0
    private val invalidAmount = 705400.0
    @RelaxedMockK
    private lateinit var payment: Payment

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getPaymentsMethodsUseCase = GetPaymentsMethodsUseCase(mercadoPagoRepository)
    }

    @Test
    fun `call with null amount return error`() = runBlocking {
        // Given

        // When
        val result = getPaymentsMethodsUseCase(null)

        // Then
        assertFalse(result.operationResult)
    }

    @Test
    fun `call with invalid amount return error`() = runBlocking {
        // Given

        // When
        val result = getPaymentsMethodsUseCase(invalidAmount)

        // Then
        assertFalse(result.operationResult)
    }

    @Test
    fun `call with valid amount return valid result`() = runBlocking {
        // Given
        val list = listOf(payment)
        coEvery { mercadoPagoRepository.getPaymentsMethods() } returns Response.success(200, list)

        // When
        val result = getPaymentsMethodsUseCase(validAmount)

        // Then
        coVerify(exactly = 1) { mercadoPagoRepository.getPaymentsMethods() }
        assertTrue(result.operationResult)
    }

    @Test
    fun `call with valid amount return invalid result`() = runBlocking {
        // Given
        coEvery { mercadoPagoRepository.getPaymentsMethods() } returns
            Response.error(400, byteArrayOf().toResponseBody())

        // When
        val result = getPaymentsMethodsUseCase(validAmount)

        // Then
        coVerify(exactly = 1) { mercadoPagoRepository.getPaymentsMethods() }
        assertFalse(result.operationResult)
    }
}
