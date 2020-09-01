package com.bove.martin.pluspagos.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.CardIssuer
import com.bove.martin.pluspagos.domain.model.InstallmentOption
import com.bove.martin.pluspagos.domain.model.Issuer
import com.bove.martin.pluspagos.domain.model.Payment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.get
import retrofit2.Response

/**
 * Created by Martín Bove on 28-Aug-20.
 * E-mail: mbove77@gmail.com
 */
class MainActivityViewModel(private val mpRepo: MercadoPagoRepository): ViewModel() {

    private val _paimentsMethods = MutableLiveData<List<Payment>>()
    val paimentsMethods: LiveData<List<Payment>> get() = _paimentsMethods

    private val _cardIssuers = MutableLiveData<List<CardIssuer>>()
    val cardIssuers: LiveData<List<CardIssuer>> get() = _cardIssuers

    private val _installmentsOptions = MutableLiveData<List<InstallmentOption>>()
    val installmentsOptions: LiveData<List<InstallmentOption>> get() = _installmentsOptions


    fun setUserAmount(amount: Double) {
        mpRepo.userAmountSelection = amount
    }

    fun getUserAmount() = mpRepo.userAmountSelection

    fun setUserPaymentSelection(payment : Payment) {
        mpRepo.userPaymentSelection = payment
    }

    fun getUserPaymentSelection() = mpRepo.userPaymentSelection

    fun setUserCardIssuer(cardIssuer: CardIssuer?) {
        mpRepo.userCardIssuerSelection = cardIssuer
    }

    fun gerUserCardIssuer() = mpRepo.userCardIssuerSelection

    fun getPaimentsMethods() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mpRepo.getPaymentsMethods()

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _paimentsMethods.value = response.body()
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