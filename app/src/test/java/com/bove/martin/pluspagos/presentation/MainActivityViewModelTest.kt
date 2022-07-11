package com.bove.martin.pluspagos.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bove.martin.pluspagos.domain.model.CardIssuer
import com.bove.martin.pluspagos.domain.model.InstallmentOption
import com.bove.martin.pluspagos.domain.model.OperationResult
import com.bove.martin.pluspagos.domain.model.Payment
import com.bove.martin.pluspagos.domain.usercase.GetCardIssuersUseCase
import com.bove.martin.pluspagos.domain.usercase.GetInstallmentsUseCase
import com.bove.martin.pluspagos.domain.usercase.GetPaymentsMethodsUseCase
import com.bove.martin.pluspagos.domain.usercase.ValidateAmountUseCase
import com.bove.martin.pluspagos.presentation.utils.UiText
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {
    private val validAmount = 15400.0
    private val invalidAmount = 705400.0

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var validateAmountUseCase: ValidateAmountUseCase
    @RelaxedMockK
    private lateinit var getPaymentsMethodsUseCase: GetPaymentsMethodsUseCase
    @RelaxedMockK
    private lateinit var getCardIssuersUseCase: GetCardIssuersUseCase
    @RelaxedMockK
    private lateinit var getInstallmentsUseCase: GetInstallmentsUseCase

    @RelaxedMockK
    private lateinit var payment: Payment
    @RelaxedMockK
    private lateinit var cardIssuer: CardIssuer
    @RelaxedMockK
    private lateinit var installmentOption: InstallmentOption

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        validateAmountUseCase = ValidateAmountUseCase()

        mainActivityViewModel = MainActivityViewModel(
            validateAmountUseCase,
            getPaymentsMethodsUseCase,
            getCardIssuersUseCase,
            getInstallmentsUseCase
        )

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `if valid amount validation return true`() {
        // Given

        // When
        mainActivityViewModel.validateAmount(validAmount)

        // Then
        assertTrue(mainActivityViewModel.amountIsValid.value!!.operationResult)
    }

    @Test
    fun `if invalid amount validation return fail`() {
        // Given

        // When
        mainActivityViewModel.validateAmount(invalidAmount)

        // Then
        assertFalse(mainActivityViewModel.amountIsValid.value!!.operationResult)
    }

    @Test
    fun `on clear selection check all values going to null`() {
        // Given

        // When
        mainActivityViewModel.clearUserSelections()

        // Then
        assertNull(mainActivityViewModel.userAmount.value)
        assertNull(mainActivityViewModel.amountIsValid.value)
        assertNull(mainActivityViewModel.userPaymentSelection.value)
        assertNull(mainActivityViewModel.userBankSelection.value)
        assertNull(mainActivityViewModel.userInstallmentSelection.value)
    }

    @Test
    fun `on call getPaymentsMethods returns valid results`() = runTest {
        // Given
        coEvery { getPaymentsMethodsUseCase(any()) } returns OperationResult(true, null, listOf(payment))

        // When
        mainActivityViewModel.getPaymentsMethods()

        // Then
        coVerify(exactly = 1) { getPaymentsMethodsUseCase(any()) }
        assertTrue(mainActivityViewModel.paymentsMethods.value!!.isNotEmpty())
        assertTrue(mainActivityViewModel.paymentsMethods.value!!.size == 1)
    }

    @Test
    fun `on call getPaymentsMethods returns invalid results`() = runTest {
        // Given
        coEvery { getPaymentsMethodsUseCase(any()) } returns OperationResult(false, UiText.DynamicString("Error"), null)

        // When
        mainActivityViewModel.getPaymentsMethods()

        // Then
        coVerify(exactly = 1) { getPaymentsMethodsUseCase(any()) }
        assertNull(mainActivityViewModel.paymentsMethods.value)
        assertTrue(mainActivityViewModel.operationsError.value!!.toString().isNotEmpty())
    }

    @Test
    fun `on call getCardIssuers returns valid results`() = runTest {
        // Given
        coEvery { getCardIssuersUseCase(any()) } returns OperationResult(true, null, listOf(cardIssuer))

        // When
        mainActivityViewModel.getCardIssuers("visa")

        // Then
        coVerify(exactly = 1) { getCardIssuersUseCase(any()) }
        assertTrue(mainActivityViewModel.cardIssuers.value!!.isNotEmpty())
        assertTrue(mainActivityViewModel.cardIssuers.value!!.size == 1)
    }

    @Test
    fun `on call getCardIssuers returns invalid results`() = runTest {
        // Given
        coEvery { getCardIssuersUseCase(any()) } returns OperationResult(false, UiText.DynamicString("Error"), null)

        // When
        mainActivityViewModel.getCardIssuers("visa")

        // Then
        coVerify(exactly = 1) { getCardIssuersUseCase(any()) }
        assertNull(mainActivityViewModel.cardIssuers.value)
        assertTrue(mainActivityViewModel.operationsError.value!!.toString().isNotEmpty())
    }

    @Test
    fun `on call getInstallments with issuerId returns valid results`() = runTest {
        // Given
        coEvery { getInstallmentsUseCase(any(), any(), any()) } returns
            OperationResult(true, null, listOf(installmentOption))

        // When
        mainActivityViewModel.getInstallments("visa", validAmount, "125")

        // Then
        coVerify(exactly = 1) { getInstallmentsUseCase(any(), any(), any()) }
        assertTrue(mainActivityViewModel.installmentsOptions.value!!.isNotEmpty())
        assertTrue(mainActivityViewModel.installmentsOptions.value!!.size == 1)
    }

    @Test
    fun `on call getInstallments returns valid results`() = runTest {
        // Given
        coEvery { getInstallmentsUseCase(any(), any(), null) } returns
            OperationResult(true, null, listOf(installmentOption))

        // When
        mainActivityViewModel.getInstallments("visa", validAmount, null)

        // Then
        coVerify(exactly = 1) { getInstallmentsUseCase(any(), any(), null) }
        assertTrue(mainActivityViewModel.installmentsOptions.value!!.isNotEmpty())
        assertTrue(mainActivityViewModel.installmentsOptions.value!!.size == 1)
    }

    @Test
    fun `on call getInstallments with issuerId returns invalid results`() = runTest {
        // Given
        coEvery { getInstallmentsUseCase(any(), any(), any()) } returns
            OperationResult(false, UiText.DynamicString("Error"), null)

        // When
        mainActivityViewModel.getInstallments("visa", validAmount, "125")

        // Then
        coVerify(exactly = 1) { getInstallmentsUseCase(any(), any(), any()) }
        assertNull(mainActivityViewModel.installmentsOptions.value)
        assertTrue(mainActivityViewModel.operationsError.value!!.toString().isNotEmpty())
    }

    @Test
    fun `on call getInstallments returns invalid results`() = runTest {
        // Given
        coEvery { getInstallmentsUseCase(any(), any(), null) } returns
            OperationResult(false, UiText.DynamicString("Error"), null)

        // When
        mainActivityViewModel.getInstallments("visa", validAmount, null)

        // Then
        coVerify(exactly = 1) { getInstallmentsUseCase(any(), any(), null) }
        assertNull(mainActivityViewModel.installmentsOptions.value)
        assertTrue(mainActivityViewModel.operationsError.value!!.toString().isNotEmpty())
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }
}
