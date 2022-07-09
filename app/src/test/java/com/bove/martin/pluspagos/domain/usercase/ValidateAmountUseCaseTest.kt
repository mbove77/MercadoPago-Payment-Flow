package com.bove.martin.pluspagos.domain.usercase

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Created by Martín Bove on 8/7/2022.
 * E-mail: mbove77@gmail.com
 */
class ValidateAmountUseCaseTest {
    private lateinit var validateAmountUseCase: ValidateAmountUseCase
    private val validAmount = 15400.0
    private val invalidAmount = 705400.0

    @Before
    fun setUp() {
        validateAmountUseCase = ValidateAmountUseCase()
    }

    @Test
    fun `when amount is null return error`() {
        // Given

        // When
        val result = validateAmountUseCase(null)

        // Then
        assertFalse(result.operationResult)
    }

    @Test
    fun `when amount is invalid return error`() {
        // Given

        // When
        val result = validateAmountUseCase(invalidAmount)

        // Then
        assertFalse(result.operationResult)
    }

    @Test
    fun `when amount is valid return ok`() {
        // Given

        // When
        val result = validateAmountUseCase(validAmount)

        // Then
        assertTrue(result.operationResult)
    }
}
