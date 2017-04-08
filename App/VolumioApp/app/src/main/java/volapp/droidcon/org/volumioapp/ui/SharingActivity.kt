package volapp.droidcon.org.volumioapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import volapp.droidcon.org.volumioapp.R
import volapp.droidcon.org.volumioapp.connectivity.WifiConnectionService

class SharingActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sharing)

//        Connects to the Network
        WifiConnectionService.startActionConnect(this)
        if(intent.action == Intent.ACTION_SEND){
            val linkToShare = intent.extras[Intent.EXTRA_TEXT]
            info("Link to share is $linkToShare")
        }
    }

    fun shareTheLink(){
        TODO("Implement the shareLink")
    }
}
