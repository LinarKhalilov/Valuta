package android.dev.valuta.data

import android.dev.valuta.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api{

@GET("v6/latest/USD")
suspend fun getRates(): Response<CurrencyResponse>
}