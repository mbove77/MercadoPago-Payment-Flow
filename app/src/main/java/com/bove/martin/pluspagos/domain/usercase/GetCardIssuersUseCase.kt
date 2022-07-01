package com.bove.martin.pluspagos.domain.usercase

import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.data.MercadoPagoRepository
import com.bove.martin.pluspagos.domain.model.OperationResult
import com.bove.martin.pluspagos.presentation.utils.UiText
import javax.inject.Inject

/**
 * Created by Martín Bove on 29/6/2022.
 * E-mail: mbove77@gmail.com
 */
class GetCardIssuersUseCase @Inject constructor(private val mercadoPagoRepository: MercadoPagoRepository) {
    suspend operator fun invoke(id: String): OperationResult {
        val response = mercadoPagoRepository.getCardIssuers(id)

        return if (response.isSuccessful) {
            OperationResult(true, null, response.body())
        } else {
            OperationResult(false, UiText.StringResource(R.string.card_issuers_error), null)
        }
    }
}
