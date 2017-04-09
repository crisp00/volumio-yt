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
import android.support.v7.recyclerview.R.attr.layoutManager
import org.jetbrains.anko.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus
import android.support.v7.widget.DividerItemDecoration
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem


class MainActivity : AppCompatActivity(),AnkoLogger {
    lateinit var listAdapter: SongsAdapter
    lateinit var pwd:String

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.become_admin -> {
                showPasswdDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun becomeAdmin(pwd:String) {
        if (pwd == "Volumio2"){
            toast("You are an admin now")
        }else{
            toast("Wrong Password")
        }
    }

    private fun showPasswdDialog(){
        alert {
            customView {
                val pwdText = editText{
                    title("Enter Password:")
                }
                yesButton {
                    pwd = pwdText.text.toString()
                    becomeAdmin(pwd)
                }
            }
        }.show()
    }


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
                        var songIsPlaying = false
                        if (i == 0){
                            songIsPlaying = true
                        }
                        val song = Song(
                                name = "${jsonObject["name"]}",
                                path=jsonObject["uri"].toString(),
                                isPlaying = songIsPlaying
                        )

                        list.add(song)
                        i++
                    }
//                    var song = Song( "Song 1", "PATH", false, false)
//                    list += song
                    with (rwSongList) {
//                        setHasFixedSize(true)
//                        layoutManager = LinearLayoutManager(this@MainActivity)
                        var mLayoutmanager = layoutManager
                        mLayoutmanager = LinearLayoutManager(this@MainActivity)
                        val orientation = mLayoutmanager.orientation
                        val dividerItemDecoration = DividerItemDecoration(rwSongList.context,orientation)
                        addItemDecoration(dividerItemDecoration)
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
