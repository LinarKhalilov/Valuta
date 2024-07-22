package android.dev.valuta.data.models


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(
    //@SerializedName("base_code")
    val base_code: String,
    //@SerializedName("documentation")
    val documentation: String,
    //@SerializedName("provider")
    val provider: String,
    //@SerializedName("rates")
    val rates: Map<String,Double>,
    //@SerializedName("result")
    val result: String,
    //@SerializedName("terms_of_use")
    val termsOfUse: String,
    //@SerializedName("time_eol_unix")
    val timeEolUnix: Int,
    //@SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: Int,
    //@SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String,
    //@SerializedName("time_next_update_unix")
    val timeNextUpdateUnix: Int,
    //@SerializedName("time_next_update_utc")
    val timeNextUpdateUtc: String
)