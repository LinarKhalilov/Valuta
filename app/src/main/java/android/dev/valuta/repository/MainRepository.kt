package android.dev.valuta.repository

import android.dev.valuta.data.models.CurrencyResponse
import android.dev.valuta.utils.Resource

interface MainRepository {

    suspend fun getRates(base_code: String): Resource<CurrencyResponse>
}