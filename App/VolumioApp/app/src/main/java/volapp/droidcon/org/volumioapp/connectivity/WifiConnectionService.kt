package volapp.droidcon.org.volumioapp.connectivity

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.wifiManager

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
class WifiConnectionService : IntentService("WifiConnectionService"),AnkoLogger {
    private val wifiSsid = "Volumio 2P"
    private val wifiPass = "volumio2"
    private val wifiConf by lazy { WifiConfiguration() }

    override fun onHandleIntent(intent: Intent?) {
        info("The Service startet")
        if (intent != null) {
            val action = intent.action
            if (ACTION_CONNECT == action) {
                wifiConf.SSID = "\"${wifiSsid}\""
                wifiConf.preSharedKey = "\"${wifiPass}\""

                if (wifiManager.connectionInfo.ssid == "Volumio 2P"){
                    debug("Already connected to Volumio 2P")
                    return
                }else{
                    debug { "Not connected, connected to ${wifiManager.connectionInfo.ssid}" }
                }
//              AnkoWifiManager Creates this
//              val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val networkId = wifiManager.addNetwork(wifiConf)
                info{"WifiNetworkAdded - the id is $networkId"}
                var connectionInfo = wifiManager.connectionInfo
                info("Current Connection is ${connectionInfo.ssid} and id is ${connectionInfo.networkId}")
                with(wifiManager){
                    connectionInfo = wifiManager.connectionInfo
                    disconnect()
                    connectionInfo = wifiManager.connectionInfo
                    connectionInfo = wifiManager.connectionInfo
                    enableNetwork(networkId, true)
                    reconnect()
                    info("Current Connection is ${connectionInfo.ssid} after reconnecting")
                }
//                for (i in list) {
//                    info("The networks in the list are - SSID ${i.SSID} Network ID :${i.networkId}")
//                    if (i.SSID != null && i.SSID == wifiConf.SSID) {
//                        with(wifiManager) {
//                            disconnect()
//                            enableNetwork(i.networkId, true)
//                            reconnect()
//                            info("Trying to reconnect with id ${i.networkId}")
//                        }

//                }
            } else if (ACTION_BAZ == action) {
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String, param2: String) {
        // TODO: Handle action Foo
        throw UnsupportedOperationException("Not yet implemented")
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String, param2: String) {
        // TODO: Handle action Baz
        throw UnsupportedOperationException("Not yet implemented")
    }

    companion object {
        // TODO: Rename actions, choose action names that describe tasks that this
        // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
        private val ACTION_CONNECT = "volapp.droidcon.org.volumioapp.connectivity.action.CONNECT"
        private val ACTION_BAZ = "volapp.droidcon.org.volumioapp.connectivity.action.BAZ"

        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.

         * @see IntentService
         */
        // TODO: Customize helper method
        fun startActionConnect(context: Context) {
            val intent = Intent(context, WifiConnectionService::class.java)
            intent.action = ACTION_CONNECT
            context.startService(intent)
        }

        /**
         * Starts this service to perform action Baz with the given parameters. If
         * the service is already performing a task this action will be queued.

         * @see IntentService
         */
        // TODO: Customize helper method
        fun startActionBaz(context: Context, param1: String, param2: String) {
            val intent = Intent(context, WifiConnectionService::class.java)
            intent.action = ACTION_BAZ
            context.startService(intent)
        }
    }
}
