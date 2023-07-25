package es.javiercarrasco.myretrofit.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun checkConnection(context: Context): Boolean {
    val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = cm.activeNetwork

    if (networkInfo != null) {
        val activeNetwork = cm.getNetworkCapabilities(networkInfo)
        if (activeNetwork != null)
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
    }
    return false
}

//suspend fun downloadWebPage(url: String): String {
//    return withContext(Dispatchers.IO) {
//        try {
//            val request = Request.Builder() // import okhttp3.Request
//                .url(url)
//                .build()
//            val response = OkHttpClient().newCall(request).execute()
//            response.body!!.string()
//        } catch (e: IOException) {
//            "ERROR: ${e.message}"
//        }
//    }
//}