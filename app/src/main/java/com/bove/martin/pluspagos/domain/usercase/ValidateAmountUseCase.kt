package com.bove.martin.pluspagos.domain.usercase

import com.bove.martin.pluspagos.AppConstants
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.domain.model.OperationResult
import com.bove.martin.pluspagos.presentation.utils.UiText
import javax.inject.Inject

/**
 * Created by Martín Bove on 28/6/2022.
 * E-mail: mbove77@gmail.com
 */
class ValidateAmountUseCase @Inject constructor() {
    operator fun invoke(amount: Double?): OperationResult {
        return if (amount == null || amount == 0.0) {
            OperationResult(false, UiText.StringResource(R.string.amount_empty_validation), null)
        } else if (amount > AppConstants.MAX_ALLOW_ENTRY) {
            OperationResult(
                false,
                UiText.StringResource(
                    R.string.amount_max_amount_validation, AppConstants.MAX_ALLOW_ENTRY.toString()
                ),
                null
            )
        } else {
            OperationResult(true, null, null)
        }
    }
}
