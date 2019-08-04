@file:Suppress("DEPRECATION")

package com.elmenus.assignment.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jetbrains.anko.doAsync
import java.net.URL


class ConnectionStateBroadCastReceiver : BroadcastReceiver() {

    companion object {
        private val mConnectionState = MutableLiveData<ConnectionState>()

        val connectionState: LiveData<ConnectionState>
            get() = mConnectionState

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        refreshState(context)
    }

    private fun isNetworkInterfaceAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    private fun isAbleToConnect(url: String, timeoutInMs: Int): Boolean {
        return try {
            doAsync {
                val myUrl = URL(url)
                val connection = myUrl.openConnection()
                connection.connectTimeout = timeoutInMs
                connection.connect()
            }
            true
        } catch (e: Exception) {
            false
        }

    }

    fun refreshState(context: Context?) {
        context?.let {
            if (!isNetworkInterfaceAvailable(context)) {
                mConnectionState.value = ConnectionState.Offline
                return
            }
            if (isAbleToConnect("https://www.google.com", 15000)) {
                mConnectionState.value = ConnectionState.Online
            } else {
                mConnectionState.value = ConnectionState.Offline
            }
        }
    }
}

enum class ConnectionState {
    Offline,
    Online
}