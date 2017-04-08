package volapp.droidcon.org.volumioapp.presenter

/**
 * Created by theshade on 09/04/2017.
 */
interface QueuePresenter {
    fun obtainListOfSongs()
    fun playSong()
    fun onSongFinished()
}