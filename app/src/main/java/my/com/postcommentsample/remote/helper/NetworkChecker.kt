package my.com.postcommentsample.remote.helper

import android.app.Activity
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.content.Context
import android.util.Log

class NetworkChecker{

    companion object{

        private val NETWORK_TAG = "NETWORK_STATUS"

        fun haveInternet(activity: Activity): Boolean{ return checkInternet(activity) }

        @Suppress("DEPRECATION")
        private fun checkInternet(activity: Activity): Boolean{

            val connected: Boolean
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED){
                connected = true
                Log.d(NETWORK_TAG, "Network is available")
            }else{
                connected = false
                Log.d(NETWORK_TAG, "Network is not available")
            }

            return connected

        }

    }

}