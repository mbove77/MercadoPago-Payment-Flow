package com.bove.martin.pluspagos.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bove.martin.pluspagos.domain.usercase.GetCardIssuersUseCase
import com.bove.martin.pluspagos.domain.usercase.GetInstallmentsUseCase
import com.bove.martin.pluspagos.domain.usercase.GetPaymentsMethodsUseCase
import com.bove.martin.pluspagos.domain.usercase.ValidateAmountUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    @RelaxedMockK
    private lateinit var validateAmountUseCase: ValidateAmountUseCase
    @RelaxedMockK
    private lateinit var getPaymentsMethodsUseCase: GetPaymentsMethodsUseCase
    @RelaxedMockK
    private lateinit var getCardIssuersUseCase: GetCardIssuersUseCase
    @RelaxedMockK
    private lateinit var getInstallmentsUseCase: GetInstallmentsUseCase

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainActivityViewModel = MainActivityViewModel(
            validateAmountUseCase,
            getPaymentsMethodsUseCase,
            getCardIssuersUseCase,
            getInstallmentsUseCase
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }
}
