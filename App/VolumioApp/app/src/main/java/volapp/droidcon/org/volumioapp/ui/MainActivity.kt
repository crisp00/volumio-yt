package volapp.droidcon.org.volumioapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import volapp.droidcon.org.volumioapp.R

import volapp.droidcon.org.volumioapp.connectivity.WifiConnectionService
import volapp.droidcon.org.volumioapp.model.Song
import volapp.droidcon.org.volumioapp.model.SongsAdapter

class MainActivity : AppCompatActivity() {
    var adapter = rwSongList.adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emptyList = emptyList<Song>()
        rwSongList.layoutManager = LinearLayoutManager(this)
        adapter = SongsAdapter(emptyList){
            toast("The ${it.name} was clicked. Its path is ${it.path}")
//            presenter.playSong()
        }
        startConnection()
    }

    private fun startConnection() = WifiConnectionService.startActionConnect(this)

}
