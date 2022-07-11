package com.bove.martin.pluspagos.domain.usercase

import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.InstallmentOption
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
class GetInstallmentsUseCaseTest {
    @RelaxedMockK
    private lateinit var mercadoPagoRepository: MercadoPagoRepository
    private lateinit var getInstallmentsUseCase: GetInstallmentsUseCase
    private val validAmount = 15400.0
    private val invalidAmount = 705400.0
    @RelaxedMockK
    private lateinit var installmentOption: InstallmentOption

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getInstallmentsUseCase = GetInstallmentsUseCase(mercadoPagoRepository)
    }

    @Test
    fun `call with empty id param return error`() = runBlocking {
        // Given

        // When
        val result = getInstallmentsUseCase("", validAmount, "123")

        // Then
        assertFalse(result.operationResult)
    }

    @Test
    fun `call with amount out of range return error`() = runBlocking {
        // Given

        // When
        val result = getInstallmentsUseCase("123", invalidAmount, "123")

        // Then
        assertFalse(result.operationResult)
    }

    @Test
    fun `call with all parameters return valid response`() = runBlocking {
        // Given
        val list = listOf(installmentOption)
        coEvery {
            mercadoPagoRepository.getInstallmentsOptions(any(), any(), any())
        } returns Response.success(200, list)

        // When
        val result = getInstallmentsUseCase("123", validAmount, "123")

        // Then
        coVerify(exactly = 1) { mercadoPagoRepository.getInstallmentsOptions(any(), any(), any()) }
        assertTrue(result.operationResult)
    }

    @Test
    fun `call with null cardIssuer return valid response`() = runBlocking {
        // Given
        val list = listOf(installmentOption)
        coEvery { mercadoPagoRepository.getInstallmentsOptions(any(), any()) } returns Response.success(200, list)

        // When
        val result = getInstallmentsUseCase("123", validAmount, null)

        // Then
        coVerify(exactly = 1) { mercadoPagoRepository.getInstallmentsOptions(any(), any()) }
        assertTrue(result.operationResult)
    }

    @Test
    fun `call with all parameters return invalid response`() = runBlocking {
        // Given
        coEvery {
            mercadoPagoRepository.getInstallmentsOptions(any(), any(), any())
        } returns
            Response.error(400, byteArrayOf().toResponseBody())

        // When
        val result = getInstallmentsUseCase("123", validAmount, "123")

        // Then
        coVerify(exactly = 1) { mercadoPagoRepository.getInstallmentsOptions(any(), any(), any()) }
        assertFalse(result.operationResult)
    }

    @Test
    fun `call with null cardIssuer return invalid response`() = runBlocking {
        // Given
        coEvery { mercadoPagoRepository.getInstallmentsOptions(any(), any()) } returns
            Response.error(400, byteArrayOf().toResponseBody())

        // When
        val result = getInstallmentsUseCase("123", validAmount, null)

        // Then
        coVerify(exactly = 1) { mercadoPagoRepository.getInstallmentsOptions(any(), any()) }
        assertFalse(result.operationResult)
    }
}
