package com.bove.martin.pluspagos.domain.usercase

import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.OperationResult
import com.bove.martin.pluspagos.domain.model.Payment
import com.bove.martin.pluspagos.presentation.utils.UiText
import java.util.stream.Collectors
import javax.inject.Inject

/**
 * Created by Martín Bove on 28/6/2022.
 * E-mail: mbove77@gmail.com
 */

class GetPaymentsMethodsUseCase @Inject constructor(private val mercadoPagoRepository: MercadoPagoRepository) {
    suspend operator fun invoke(userAmount: Double?): OperationResult {
        val response = mercadoPagoRepository.getPaymentsMethods()

        return if (userAmount != null && response.isSuccessful) {
            val tempList = response.body()!!

            val activeItems =
                tempList.stream().filter { payment -> payment.status == "active" }
                    .collect(Collectors.toList())

            val filterList = activeItems.stream()
                .filter { payment: Payment ->
                    (userAmount >= payment.minAllowedAmount && userAmount <= payment.maxAllowedAmount)
                }
                .collect(Collectors.toList())

            OperationResult(true, null, filterList)
        } else {
            OperationResult(false, UiText.StringResource(R.string.payments_methods_error), null)
        }
    }
}
