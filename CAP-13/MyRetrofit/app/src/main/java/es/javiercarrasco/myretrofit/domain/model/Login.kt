package es.javiercarrasco.myretrofit.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    @SerializedName("token") val token: String = "FAIL"
) : Parcelable
