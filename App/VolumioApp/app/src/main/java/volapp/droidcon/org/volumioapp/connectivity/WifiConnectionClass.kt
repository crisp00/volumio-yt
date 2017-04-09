package volapp.droidcon.org.volumioapp.connectivity

import android.net.wifi.WifiConfiguration

/**
 * Created by theshade on 08/04/2017.
 */
class WifiConnectionClass {
    private val wifiSsid = "Volumio 2P"
    private val wifiPass = "volumio2"
    private val wifiConf by lazy { WifiConfiguration() }

    init{
        wifiConf.SSID = "\"${wifiSsid}\""
        wifiConf.preSharedKey = "\"${wifiPass}\""

    }

    fun connect(){
    }
}