package volapp.droidcon.org.volumioapp.model

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_song.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.image
import volapp.droidcon.org.volumioapp.R

/**
 * Created by theshade on 08/04/2017.
 *
 */

class SongsAdapter(val itemList: List<Song>, val listener: (Song) -> Unit) :
        RecyclerView.Adapter<SongsAdapter.ViewHolder>(),AnkoLogger {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_song,parent,false))


    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(itemList[position],listener)


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(song:Song,listener:(Song) -> Unit){
            with(itemView){
                itemTextId.text = song.name
                if (song.isPlaying){
//                    Set the image to PlayButton
//                    iconSong.image =
                }
                setOnClickListener { listener(song) }
            }


        }
    }

}
