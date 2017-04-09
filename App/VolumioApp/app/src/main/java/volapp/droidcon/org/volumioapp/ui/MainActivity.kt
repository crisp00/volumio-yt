package volapp.droidcon.org.volumioapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import volapp.droidcon.org.volumioapp.R

import volapp.droidcon.org.volumioapp.connectivity.WifiConnectionService
import volapp.droidcon.org.volumioapp.model.Song
import volapp.droidcon.org.volumioapp.model.SongsAdapter
import android.widget.EditText
import volapp.droidcon.org.volumioapp.services.VolumioQueue
import android.support.v4.content.LocalBroadcastManager
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.content.Context
import org.jetbrains.anko.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus

class MainActivity : AppCompatActivity(),AnkoLogger {
    lateinit var listAdapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        val emptyList = emptyList<Song>()
        val list: MutableList<Song> = mutableListOf()
        var song = Song( "Song 1", "PATH", false, false)
        list += song
        rwSongList.layoutManager = LinearLayoutManager(this)
        var adapter = rwSongList.adapter
        adapter = SongsAdapter(list){
            toast("The ${it.name} was clicked. Its path is ${it.path}")
//            presenter.playSong()
        }
        */

        val list: MutableList<Song> = mutableListOf()
        var song = Song( "Song 1", "PATH", false, false)
        list += song
        with (rwSongList) {
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            listAdapter = SongsAdapter(list){
                toast("The ${it.name} was clicked. Its path is ${it.path}")
            }
            adapter = listAdapter
        }
        startConnection()

        VolumioQueue.openConnection(this@MainActivity)
    }

    private fun startConnection(){
        info("The SSID: ${wifiManager.connectionInfo.ssid}")
//        If Connected to Volumio 2P already, don't connect.
        if (wifiManager.connectionInfo.ssid == "Volumio 2P"){
            info("Inside the IF statement, connecting to the network.")
            WifiConnectionService.startActionConnect(this)
        }
    }

    override fun onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                IntentFilter("CONNECTION-STATUS-UPDATE"))
    }

    // handler for received Intents for the "my-event" event
    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Extract data included in the Intent
            val message = intent.getStringExtra("status")
            debug("Got message $message")
            //Log.d("receiver", "Got message: " + message)
            when (message) {
                "CONNECTED" -> {
                    info("CONNECTED")
                    VolumioQueue.startGetQueue(this@MainActivity)
                    //VolumioQueue.startGetState(this@MainActivity)
                }
                "PUSH_STATE" -> {
                    info("PUSH_STATE")
                    VolumioQueue.startGetQueue(this@MainActivity)
                }
                "PUSH_QUEUE" -> {
                    info("PUSH_QUEUE")
                    val list: MutableList<Song> = mutableListOf()
                    list.clear()
                    val jsonData = intent.getStringExtra("data")
                    val data: JSONArray = JSONArray(jsonData)
                    var i = 0
                    while (i < data.length()) {
                        info("First element ${data[0]}")
                        val jsonObject = data.getJSONObject(i)
//                        val jsonObject = JSONObject().getJSONObject(data.toString())
                        info("The URI is ${jsonObject["uri"]}")
                        val song = Song(
                                name = "${jsonObject["name"]}",
                                path=jsonObject["uri"].toString()
                        )

                        list.add(song)
                        i++
                    }
//                    var song = Song( "Song 1", "PATH", false, false)
//                    list += song
                    with (rwSongList) {
//                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        listAdapter = SongsAdapter(list){
                            toast("The ${it.name} was clicked. Its path is ${it.path}")
                        }
                        adapter = listAdapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}
