package com.bove.martin.pluspagos.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bove.martin.pluspagos.AppConstants
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.GetPaymentsMethodsUseCase
import com.bove.martin.pluspagos.domain.model.*
import com.bove.martin.pluspagos.presentation.utils.SingleLiveEvent
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mercadoPagoRepository: MercadoPagoRepository,
    @ApplicationContext val context: Context,
    private val getPaymentsMethodsUseCase: GetPaymentsMethodsUseCase
    ): ViewModel() {

    // user selection
    val userAmount = MutableLiveData<Double>()
    val amountIsValid = MutableLiveData<ValidationResult>()
    val userPaymentSelection = MutableLiveData<Payment>()
    val userBankSelection = MutableLiveData<CardIssuer>()
    val userInstallmentSelection = MutableLiveData<PayerCost>()

    // lists
    val paymentsMethods = SingleLiveEvent<List<Payment>>()
    val cardIssuers = SingleLiveEvent<List<CardIssuer>>()
    val installmentsOptions = SingleLiveEvent<List<InstallmentOption>>()


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
        val validationResult = ValidationResult(true, null)
        if (amount == null) {
            validationResult.result = false
            validationResult.errorMessage = context.resources.getString(R.string.amount_empty_validation)
        } else if(amount  > AppConstants.MAX_ALLOW_ENTRY) {
            validationResult.result = false
            validationResult.errorMessage = context.resources. getString(R.string.amount_max_amount_validation, AppConstants.MAX_ALLOW_ENTRY.toInt().toString())
        }
        amountIsValid.postValue(validationResult)
    }

    fun getPaymentsMethods() {
        viewModelScope.launch {
            val response = getPaymentsMethodsUseCase(userAmount.value)
            if (response.isNullOrEmpty()) {
                Logger.e("Retrofit", "Error in payments methods request.")
            } else {
                withContext(Dispatchers.Main) {
                    paymentsMethods.postValue(response)
                }
            }
        }
    }

    fun getCardIssuers(id: String) {
        viewModelScope.launch {
            val response = mercadoPagoRepository.getCardIssuers(id)

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    cardIssuers.postValue(response.body())
                }
            } else {
                Log.e("Retrofit", "Error in card issuers request.")
            }
        }
    }

    fun getInstallments(id: String, amount: Float, issuerId: String?) {
        viewModelScope.launch {

            val response= if (issuerId.isNullOrEmpty()) {
                mercadoPagoRepository.getInstallmentsOptions(id, amount)
            } else {
                mercadoPagoRepository.getInstallmentsOptions(id, amount, issuerId)
            }

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    installmentsOptions.postValue( response.body())
                }
            } else {
                Log.e("Retrofit", "Error in installments options request.")
            }
        }
    }

}