package com.example.swipeproducts.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData

/*
This class will monitor the internet connectivity in the user's phone.
and return it as LiveData , we will observe this.
 */
class NetworkManager(context: Context) : LiveData<Boolean>() {

    override fun onActive() {
        super.onActive()
        checkNetWorkConnectivity()
    }

    override fun onInactive() {
        super.onInactive()
        releaseConnectivityCheck()
    }

    private val connectivityManager =  context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val requestBuilder = NetworkRequest.Builder().apply {
        addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
    }.build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }
        override fun onUnavailable() {
            super.onUnavailable()
            postValue(false)
        }
        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }

    private fun checkNetWorkConnectivity() {
        if ( connectivityManager.activeNetwork == null) postValue(false)
        connectivityManager.registerNetworkCallback(requestBuilder, networkCallback)
    }

    private fun releaseConnectivityCheck() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

}