package android.dev.valuta.repository

import android.dev.valuta.utils.DispatcherProvider
import android.dev.valuta.utils.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String) : CurrencyEvent()
        class Failure(val errorText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String,
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return

        }
        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when (val ratesResponse = repository.getRates(fromCurrency)) {
                is Resource.Error -> _conversion.value =
                    CurrencyEvent.Failure(ratesResponse.message!!)

                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val rateFrom = rates[fromCurrency]
                    val rateTo = rates[toCurrency]
                    rateFrom?.let { from ->
                        rateTo?.let { to ->
                            val fromToUSD = fromAmount / from
                            println("Result: $fromToUSD")
                            val toCurrencyResult = fromToUSD * to
                            val convertedCurrency = round(toCurrencyResult * 100) / 100
                            _conversion.value = CurrencyEvent.Success(
                                "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                            )

                        }
                    } ?: {
                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    }
                }
            }
        }
    }
}