package volapp.droidcon.org.volumioapp.model

/**
 * Created by theshade on 09/04/2017.
 */
interface SongRecieverModel {
    interface OnSongListener{
        fun onNewSongReceived(name:String)
        fun onAllSongsReceived(songs:List<String>)
    }

    fun listenForNewSongs(listener:OnSongListener)
    fun getAllSongsInQueue()
}