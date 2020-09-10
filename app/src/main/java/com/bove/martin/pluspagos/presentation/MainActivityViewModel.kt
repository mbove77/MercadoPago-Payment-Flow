package com.bove.martin.pluspagos.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bove.martin.pluspagos.data.MemoryRepository
import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.CardIssuer
import com.bove.martin.pluspagos.domain.model.InstallmentOption
import com.bove.martin.pluspagos.domain.model.PayerCost
import com.bove.martin.pluspagos.domain.model.Payment
import com.bove.martin.pluspagos.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.stream.Collectors


class MainActivityViewModel(private val mercadoPagoRepository: MercadoPagoRepository, private val memoryRepository: MemoryRepository): ViewModel() {

    private val _paymentsMethods = MutableLiveData<List<Payment>>()
    val paymentsMethods: LiveData<List<Payment>> get() = _paymentsMethods

    private val _cardIssuers = SingleLiveEvent<List<CardIssuer>>()
    val cardIssuers: LiveData<List<CardIssuer>> get() = _cardIssuers

    private val _installmentsOptions = MutableLiveData<List<InstallmentOption>>()
    val installmentsOptions: LiveData<List<InstallmentOption>> get() = _installmentsOptions

    private val _userAmount = MutableLiveData<String>("")
    val userAmount: LiveData<String> get() = _userAmount

    private val _userPayMethod = MutableLiveData<String>("")
    val userPayMethod: LiveData<String> get() = _userPayMethod

    fun getUserAmount() = memoryRepository.userAmount
    fun setUserAmount(amount: Double) {
        memoryRepository.userAmount = amount
        _userAmount.value = amount.toInt().toString()
    }

    fun getUserPaymentSelection() = memoryRepository.userPaymentSelection
    fun setUserPaymentSelection(payment: Payment) {
        memoryRepository.userPaymentSelection = payment
        _userPayMethod.value = "Medio: " + payment.name
    }

    fun getUserCardIssuer() = memoryRepository.userBankSelection
    fun setUserCardIssuer(cardIssuer: CardIssuer?) {
        memoryRepository.userBankSelection = cardIssuer
    }

    fun getUserInstallmentSelection() = memoryRepository.userInstallmentSelection
    fun setUserInstallmentSelection(payerCost: PayerCost) {
        memoryRepository.userInstallmentSelection = payerCost
    }

    fun clearUserSelections() {
        memoryRepository.userAmount = null
        memoryRepository.userPayMethod = null
        memoryRepository.userPaymentSelection = null
        memoryRepository.userBankSelection = null
        memoryRepository.userInstallmentSelection = null
        _userPayMethod.value = ""
        _userAmount.value = ""
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
                            activeItems.stream().filter {p -> (getUserAmount()!! >= p.minAllowedAmount && getUserAmount()!! <= p.maxAllowedAmount) }
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