package com.bove.martin.pluspagos.domain

import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.Payment
import java.util.stream.Collectors
import javax.inject.Inject

/**
 * Created by Martín Bove on 28/6/2022.
 * E-mail: mbove77@gmail.com
 */

class GetPaymentsMethodsUseCase @Inject constructor(private val mercadoPagoRepository: MercadoPagoRepository) {
    suspend operator fun invoke(userAmount: Double?): MutableList<Payment>? {
        val response = mercadoPagoRepository.getPaymentsMethods()

        return if (userAmount != null && response.isSuccessful) {
            val tempList = response.body()!!

            val activeItems =
                tempList.stream().filter { payment -> payment.status == "active" }
                    .collect(Collectors.toList())

            activeItems.stream()
                .filter { payment: Payment -> (userAmount >= payment.minAllowedAmount && userAmount <= payment.maxAllowedAmount) }
                .collect(Collectors.toList())
        } else {
            null
        }
    }
}