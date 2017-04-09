package volapp.droidcon.org.volumioapp.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.json.JSONArray
import volapp.droidcon.org.volumioapp.R
import volapp.droidcon.org.volumioapp.connectivity.WifiConnectionService
import volapp.droidcon.org.volumioapp.model.Song
import volapp.droidcon.org.volumioapp.model.SongsAdapter
import volapp.droidcon.org.volumioapp.services.VolumioQueue

class SharingActivity : AppCompatActivity(), AnkoLogger {

    var linkToShare: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sharing)

//        Connects to the Network
        WifiConnectionService.startActionConnect(this)
        if(intent.action == Intent.ACTION_SEND){
            linkToShare = intent.extras[Intent.EXTRA_TEXT].toString()
            info("Link to share is $linkToShare")
        }
    }

    //fun shareTheLink(){
    //    TODO("Implement the shareLink")
    //}

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
            val message = intent.getStringExtra("status")
            when (message) {
                "CONNECTED" -> {
                    VolumioQueue.startPlayingUrl(this@SharingActivity, linkToShare)
                    this@SharingActivity.finish()
                }
            }
        }
    }

}
