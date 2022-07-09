package com.bove.martin.pluspagos.data

import com.bove.martin.pluspagos.data.retrofit.MercadoPagoServices
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

/**
 * Created by Martín Bove on 31-Aug-20.
 * E-mail: mbove77@gmail.com
 */


class MercadoPagoRepositoryTest {
    private val mockWebServer = MockWebServer()
    private lateinit var mercadoPagoRepository:MercadoPagoRepository
    private val validAmount = 15400.0

    @Before
    fun onBefore() {
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        val api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MercadoPagoServices::class.java)

        mercadoPagoRepository = MercadoPagoRepository(api)
    }

    @Test
    fun `getPaymentsMethods response ok`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse("payment_response.json", 200)

        // When
        val result = mercadoPagoRepository.getPaymentsMethods()

        // Then
        assertTrue(result.isSuccessful)
    }

    @Test
    fun `getPaymentsMethods response error`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse("payment_response.json", 400)

        // When
        val result = mercadoPagoRepository.getPaymentsMethods()

        // Then
        assertFalse(result.isSuccessful)
    }

    @Test
    fun `getCardIssuers response ok`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse("card_issuers_response.json", 200)

        // When
        val result = mercadoPagoRepository.getCardIssuers("visa")

        // Then
        assertTrue(result.isSuccessful)
    }

    @Test
    fun `getCardIssuers response error`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse("card_issuers_response.json", 400)

        // When
        val result = mercadoPagoRepository.getCardIssuers("visa")

        // Then
        assertFalse(result.isSuccessful)
    }

    @Test
    fun `getInstallmentsOptions sin issureId response ok`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse("installments_no_issuer_response.json", 200)

        // When
        val result = mercadoPagoRepository.getInstallmentsOptions("visa", validAmount.toFloat())

        // Then
        assertTrue(result.isSuccessful)
    }

    @Test
    fun `getInstallmentsOptions sin issureId response error`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse("installments_no_issuer_response.json", 400)

        // When
        val result = mercadoPagoRepository.getInstallmentsOptions("visa", validAmount.toFloat())

        // Then
        assertFalse(result.isSuccessful)
    }

    @Test
    fun `getInstallmentsOptions con issureId response ok`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse("installments_response.json", 200)

        // When
        val result = mercadoPagoRepository.getInstallmentsOptions("visa", validAmount.toFloat(), "297")

        // Then
        assertTrue(result.isSuccessful)
    }

    @Test
    fun `getInstallmentsOptions con issureId response error`() = runBlocking {
        // Given
        mockWebServer.enqueueResponse("installments_response.json", 400)

        // When
        val result = mercadoPagoRepository.getInstallmentsOptions("visa", validAmount.toFloat(), "297")

        // Then
        assertFalse(result.isSuccessful)
    }


    internal fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
