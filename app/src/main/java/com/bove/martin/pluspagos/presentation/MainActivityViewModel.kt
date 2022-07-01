package com.bove.martin.pluspagos.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bove.martin.pluspagos.domain.model.*
import com.bove.martin.pluspagos.domain.usercase.GetCardIssuersUseCase
import com.bove.martin.pluspagos.domain.usercase.GetInstallmentsUseCase
import com.bove.martin.pluspagos.domain.usercase.GetPaymentsMethodsUseCase
import com.bove.martin.pluspagos.domain.usercase.ValidateAmountUseCase
import com.bove.martin.pluspagos.presentation.utils.SingleLiveEvent
import com.bove.martin.pluspagos.presentation.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val validateAmountUseCase: ValidateAmountUseCase,
    private val getPaymentsMethodsUseCase: GetPaymentsMethodsUseCase,
    private val getCardIssuersUseCase: GetCardIssuersUseCase,
    private val getInstallmentsUseCase: GetInstallmentsUseCase
) : ViewModel() {

    // user selection
    val userAmount = MutableLiveData<Double>()
    val amountIsValid = MutableLiveData<OperationResult>()
    val userPaymentSelection = MutableLiveData<Payment>()
    val userBankSelection = MutableLiveData<CardIssuer>()
    val userInstallmentSelection = MutableLiveData<PayerCost>()

    // lists
    val paymentsMethods = SingleLiveEvent<List<Payment>>()
    val cardIssuers = SingleLiveEvent<List<CardIssuer>>()
    val installmentsOptions = SingleLiveEvent<List<InstallmentOption>>()

    val operationsError = MutableLiveData<UiText>()

    fun setUserAmount(amount: Double) {
        userAmount.postValue(amount)
    }
    fun setUserPaymentSelection(payment: Payment) {
        userPaymentSelection.postValue(payment)
    }
    fun setUserCardIssuer(cardIssuer: CardIssuer?) {
        userBankSelection.postValue(cardIssuer)
    }
    fun setUserInstallmentSelection(payerCost: PayerCost) {
        userInstallmentSelection.postValue(payerCost)
    }

    fun clearUserSelections() {
        userAmount.postValue(null)
        amountIsValid.postValue(null)
        userPaymentSelection.postValue(null)
        userBankSelection.postValue(null)
        userInstallmentSelection.postValue(null)
    }

    fun validateAmount(amount: Double?) {
        amountIsValid.postValue(validateAmountUseCase(amount))
    }

    fun getPaymentsMethods() {
        viewModelScope.launch {
            val response = getPaymentsMethodsUseCase(userAmount.value)

            if (response.operationResult) {
                withContext(Dispatchers.Main) {
                    val paymentList: List<Payment> = (response.resultObject as List<*>).filterIsInstance<Payment>()
                    paymentsMethods.postValue(paymentList)
                }
            } else {
                operationsError.postValue(response.resultMensaje)
            }
        }
    }

    fun getCardIssuers(id: String) {
        viewModelScope.launch {
            val response = getCardIssuersUseCase(id)

            if (response.operationResult) {
                withContext(Dispatchers.Main) {
                    val cardIssuerList = (response.resultObject as List<*>).filterIsInstance<CardIssuer>()
                    cardIssuers.postValue(cardIssuerList)
                }
            } else {
                operationsError.postValue(response.resultMensaje)
            }
        }
    }

    fun getInstallments(id: String, amount: Float, issuerId: String?) {
        viewModelScope.launch {

            val response = getInstallmentsUseCase(id, amount, issuerId)

            if (response.operationResult) {
                withContext(Dispatchers.Main) {
                    val installmentsOptionsList =
                        (response.resultObject as List<*>).filterIsInstance<InstallmentOption>()
                    installmentsOptions.postValue(installmentsOptionsList)
                }
            } else {
                operationsError.postValue(response.resultMensaje)
            }
        }
    }
}
