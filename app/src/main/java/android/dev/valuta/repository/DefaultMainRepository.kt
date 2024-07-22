package android.dev.valuta.repository

import android.dev.valuta.data.Api
import android.dev.valuta.data.models.CurrencyResponse
import android.dev.valuta.utils.Resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: Api
): MainRepository {

    override suspend fun getRates(base_code: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates()
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error")
        }
    }
}