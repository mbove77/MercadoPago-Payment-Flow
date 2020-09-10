package com.bove.martin.pluspagos.data

import com.bove.martin.pluspagos.domain.model.CardIssuer
import com.bove.martin.pluspagos.domain.model.PayerCost
import com.bove.martin.pluspagos.domain.model.Payment

/**
 * Created by Martín Bove on 10-Sep-20.
 * E-mail: mbove77@gmail.com
 */
data class MemoryRepository(var userPaymentSelection:Payment?, var userBankSelection:CardIssuer?, var userInstallmentSelection: PayerCost?, var userAmount:Double?, var userPayMethod:String?)
