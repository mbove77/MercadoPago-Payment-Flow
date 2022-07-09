package com.bove.martin.pluspagos.domain.usercase

import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.CardIssuer
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
class GetCardIssuersUseCaseTest {
    @RelaxedMockK
    private lateinit var mercadoPagoRepository: MercadoPagoRepository
    private lateinit var getCardIssuersUseCase: GetCardIssuersUseCase
    @RelaxedMockK
    private lateinit var cardIssuer: CardIssuer

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCardIssuersUseCase = GetCardIssuersUseCase(mercadoPagoRepository)
    }

    @Test
    fun `call with null id param return error`() = runBlocking {
        // Given

        // When
        val result = getCardIssuersUseCase("")

        // Then
        assertFalse(result.operationResult)
    }

    @Test
    fun `call with valid id return valid response`() = runBlocking{
        // Given
        val list = listOf<CardIssuer>(cardIssuer)
        coEvery { mercadoPagoRepository.getCardIssuers(any()) } returns Response.success(200, list)

        // When
        val result = getCardIssuersUseCase("123")

        // Then
        assertTrue(result.operationResult)
    }

    @Test
    fun `call with valid id return failded response`() = runBlocking {
        // Given
        coEvery { mercadoPagoRepository.getCardIssuers(any()) } returns Response.error(400, byteArrayOf().toResponseBody())

        // When
        val result = getCardIssuersUseCase("123")

        // Then
        assertFalse(result.operationResult)
    }
}
