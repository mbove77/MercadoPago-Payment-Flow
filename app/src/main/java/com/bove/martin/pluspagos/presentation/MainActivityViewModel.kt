package com.bove.martin.pluspagos.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Created by Martín Bove on 28-Aug-20.
 * E-mail: mbove77@gmail.com
 */
class MainActivityViewModel(private val mpRepo: MercadoPagoRepository): ViewModel() {

    private val _paymentsMethods = MutableLiveData<List<Payment>>()
    val paymentsMethods: LiveData<List<Payment>> get() = _paymentsMethods

    private val _cardIssuers = MutableLiveData<List<CardIssuer>>()
    val cardIssuers: LiveData<List<CardIssuer>> get() = _cardIssuers

    private val _installmentsOptions = MutableLiveData<List<InstallmentOption>>()
    val installmentsOptions: LiveData<List<InstallmentOption>> get() = _installmentsOptions


    fun getUserAmount() = mpRepo.userAmountSelection
    fun setUserAmount(amount: Double) {
        mpRepo.userAmountSelection = amount
    }

    fun getUserPaymentSelection() = mpRepo.userPaymentSelection
    fun setUserPaymentSelection(payment : Payment) {
        mpRepo.userPaymentSelection = payment
    }

    fun getUserCardIssuer() = mpRepo.userCardIssuerSelection
    fun setUserCardIssuer(cardIssuer: CardIssuer?) {
        mpRepo.userCardIssuerSelection = cardIssuer
    }

    fun getUserInstallmentSelection() = mpRepo.userInstallmentSelection
    fun setUserInstallmentSelection(payerCost: PayerCost) {
        mpRepo.userInstallmentSelection = payerCost
    }

    fun clearUserSelections() {
        mpRepo.userAmountSelection = null
        mpRepo.userPaymentSelection = null
        mpRepo.userCardIssuerSelection = null
        mpRepo.userInstallmentSelection = null
    }

    fun getPaimentsMethods() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mpRepo.getPaymentsMethods()

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _paymentsMethods.value = response.body()
                }
            } else {
                Log.e("Retrofit", "Error in payments methods request.")
            }
        }
    }

    fun getCardIssuers(id:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mpRepo.getCardIssuers(id)

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _cardIssuers.value = response.body()
                }
            } else {
                Log.e("Retrofit", "Error in card issuers request.")
            }
        }
    }

    fun getInstallments(id: String, amount:Float, issuerId:String?) {
        viewModelScope.launch(Dispatchers.IO) {
            lateinit var response: Response<List<InstallmentOption>>

            if (issuerId.isNullOrEmpty()) {
                response = mpRepo.getInstallmentsOptions(id, amount)
            } else {
                response = mpRepo.getInstallmentsOptions(id, amount, issuerId)
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