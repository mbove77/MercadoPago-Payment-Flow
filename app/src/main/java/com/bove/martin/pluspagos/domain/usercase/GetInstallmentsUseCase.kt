package com.bove.martin.pluspagos.domain.usercase

import com.bove.martin.pluspagos.AppConstants
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.OperationResult
import com.bove.martin.pluspagos.presentation.utils.UiText
import javax.inject.Inject

/**
 * Created by Martín Bove on 29/6/2022.
 * E-mail: mbove77@gmail.com
 */
class GetInstallmentsUseCase @Inject constructor(private val mercadoPagoRepository: MercadoPagoRepository) {
    suspend operator fun invoke(id: String, amount: Float, issuerId: String?): OperationResult {

        return if (id.isNotEmpty() && (amount > 0 && amount <= AppConstants.MAX_ALLOW_ENTRY)) {
            val response = if (issuerId.isNullOrEmpty()) {
                mercadoPagoRepository.getInstallmentsOptions(id, amount)
            } else {
                mercadoPagoRepository.getInstallmentsOptions(id, amount, issuerId)
            }

            if (response.isSuccessful) {
                OperationResult(true, null, response.body())
            } else {
                OperationResult(false, UiText.StringResource(R.string.installments_options_error), null)
            }
        } else {
            OperationResult(false, UiText.StringResource(R.string.installments_options_error), null)
        }
    }
}
