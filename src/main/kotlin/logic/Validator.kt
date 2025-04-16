package org.example.logic

class Validator {

    fun validateGymHelperInput(
        calories: String?,
        protein: String?,
    ): Boolean {
        return validateAmount(calories) && validateAmount(protein)
    }

    private fun validateAmount(
        amount: String?
    ): Boolean {
        return amount?.let { amount.isNotBlank() && (amount.toFloatOrNull() != null) && (amount.toFloat() > 0) }
            ?: false
    }
}