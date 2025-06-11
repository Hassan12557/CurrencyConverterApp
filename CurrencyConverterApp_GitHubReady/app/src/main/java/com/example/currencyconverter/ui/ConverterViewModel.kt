 package com.example.currencyconverter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConverterViewModel(private val repository: CurrencyRepository): ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Idle)
    val state = _state.asStateFlow()

    var amount = MutableStateFlow("1")
    var baseCurrency = MutableStateFlow(repository.getSavedCurrency("base_currency"))
    var targetCurrency = MutableStateFlow(repository.getSavedCurrency("target_currency"))

    fun convert() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            val (rate, success) = repository.convert(baseCurrency.value, targetCurrency.value)

            if (rate != null) {
                val input = amount.value.toDoubleOrNull() ?: 1.0
                val result = input * rate
                _state.value = UiState.Success(result, success)
            } else {
                _state.value = UiState.Error("Conversion failed. Try again.")
            }

            repository.saveCurrency("base_currency", baseCurrency.value)
            repository.saveCurrency("target_currency", targetCurrency.value)
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        data class Success(val result: Double, val online: Boolean) : UiState()
        data class Error(val message: String) : UiState()
    }
}
