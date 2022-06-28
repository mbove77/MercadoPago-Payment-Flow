package com.bove.martin.pluspagos.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bove.martin.pluspagos.AppConstants
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.*
import com.bove.martin.pluspagos.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val mercadoPagoRepository: MercadoPagoRepository, @ApplicationContext val context: Context): ViewModel() {

    // user selection
    private val _userAmount = MutableLiveData<Double>()
    val userAmount: LiveData<Double> get() = _userAmount

    private val _amountIsValid = MutableLiveData<ValidationResult>()
    val amountIsValid: LiveData<ValidationResult> get() = _amountIsValid

    private val _userPaymentSelection = MutableLiveData<Payment>()
    val userPaymentSelection: LiveData<Payment> get() = _userPaymentSelection

    private val _userBankSelection = MutableLiveData<CardIssuer>()
    val userBankSelection: LiveData<CardIssuer> get() = _userBankSelection

    private val _userInstallmentSelection = MutableLiveData<PayerCost>()
    val userInstallmentSelection: LiveData<PayerCost> get() = _userInstallmentSelection


    // lists
    private val _paymentsMethods = SingleLiveEvent<List<Payment>>()
    val paymentsMethods: LiveData<List<Payment>> get() = _paymentsMethods

    private val _cardIssuers = SingleLiveEvent<List<CardIssuer>>()
    val cardIssuers: LiveData<List<CardIssuer>> get() = _cardIssuers

    private val _installmentsOptions = SingleLiveEvent<List<InstallmentOption>>()
    val installmentsOptions: LiveData<List<InstallmentOption>> get() = _installmentsOptions


    fun setUserAmount(amount: Double) {
        _userAmount.value = amount
    }

    fun setUserPaymentSelection(payment: Payment) {
        _userPaymentSelection.value = payment
    }

    fun setUserCardIssuer(cardIssuer: CardIssuer?) {
        _userBankSelection.value = cardIssuer
    }

    fun setUserInstallmentSelection(payerCost: PayerCost) {
        _userInstallmentSelection.value = payerCost
    }

    fun clearUserSelections() {
        _userAmount.value = null
        _amountIsValid.value = null
        _userPaymentSelection.value = null
        _userBankSelection.value = null
        _userInstallmentSelection.value = null
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
        _amountIsValid.value = validationResult
    }

    fun getPaymentsMethods() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mercadoPagoRepository.getPaymentsMethods()

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    if (!response.body().isNullOrEmpty()) {

                        // Filter the list for remove not active elements and out of price range.
                        val tempList = response.body()!!

                        val activeItems =
                            tempList.stream().filter {p -> p.status == "active" }
                                .collect(Collectors.toList())

                        val inRangeItems =
                            activeItems.stream().filter {p: Payment -> (userAmount.value!! >= p.minAllowedAmount && userAmount.value!! <= p.maxAllowedAmount) }
                                .collect(Collectors.toList())

                        _paymentsMethods.value = inRangeItems
                    }
                }
            } else {
                Log.e("Retrofit", "Error in payments methods request.")
            }
        }
    }

    fun getCardIssuers(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mercadoPagoRepository.getCardIssuers(id)

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _cardIssuers.value = response.body()
                }
            } else {
                Log.e("Retrofit", "Error in card issuers request.")
            }
        }
    }

    fun getInstallments(id: String, amount: Float, issuerId: String?) {
        viewModelScope.launch(Dispatchers.IO) {

            val response= if (issuerId.isNullOrEmpty()) {
                mercadoPagoRepository.getInstallmentsOptions(id, amount)
            } else {
                mercadoPagoRepository.getInstallmentsOptions(id, amount, issuerId)
            }

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _installmentsOptions.value = response.body()
                }
            } else {
                Log.e("Retrofit", "Error in installments options request.")
            }
        }
    }

}