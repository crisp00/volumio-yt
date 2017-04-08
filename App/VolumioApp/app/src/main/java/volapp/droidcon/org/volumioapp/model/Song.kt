package volapp.droidcon.org.volumioapp.model

/**
 * Created by theshade on 09/04/2017.
 */
data class Song(val name:String,val path:String,var isPlaying:Boolean = false, var isPaused:Boolean = false)