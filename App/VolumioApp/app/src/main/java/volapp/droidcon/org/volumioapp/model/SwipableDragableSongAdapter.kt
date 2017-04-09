//package volapp.droidcon.org.volumioapp.model
//
//import android.support.v7.widget.RecyclerView
//import android.view.View
//import android.view.ViewGroup
//import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
//import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemConstants
//import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
//import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter
//import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants
//import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction
//import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableSwipeableItemViewHolder
//import kotlinx.android.synthetic.main.item_song.view.*
//import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants.STATE_FLAG_SWIPING
//import volapp.droidcon.org.volumioapp.R
//
//
///**
// * Created by theshade on 09/04/2017.
// */
//class SwipableDragableSongAdapter(val itemList:MutableList<Song>, val listener: (Song) -> Unit):
//        RecyclerView.Adapter<SwipableDragableSongAdapter.ViewHolder>(),
//        DraggableItemAdapter<SwipableDragableSongAdapter.ViewHolder>,
//        SwipeableItemAdapter<SwipableDragableSongAdapter.ViewHolder> {
//    interface Dragable: DraggableItemConstants
//    interface Swipeable: SwipeableItemConstants
//
//    interface EventListener {
//        fun onItemRemoved(position: Int)
//
//        fun onItemPinned(position: Int)
//
//        fun onItemViewClicked(v: View, pinned: Boolean)
//    }
//
//    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onGetItemDraggableRange(holder: ViewHolder?, position: Int): ItemDraggableRange {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onCheckCanStartDrag(holder: ViewHolder?, position: Int, x: Int, y: Int): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onSetSwipeBackground(holder: ViewHolder?, position: Int, type: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onSwipeItem(holder: ViewHolder?, position: Int, result: Int): SwipeResultAction {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onGetSwipeReactionType(holder: ViewHolder, position: Int, x: Int, y: Int): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getItemCount(): Int = itemList.size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int){
//        holder.bind(itemList[position],listener)
//        val swipeState = holder.swipeStateFlags
//        if (swipeState and Swipeable.STATE_FLAG_IS_UPDATED){
//            var bgResId = 0
//            if (swipeState and Swipeable.STATE_FLAG_IS_ACTIVE !== 0) {
//                bgResId = R.drawable.bg_item_swiping_active_state
//            } else if (swipeState and Swipeable.STATE_FLAG_SWIPING !== 0) {
//                bgResId = R.drawable.bg_item_swiping_state
//            } else {
//                bgResId = R.drawable.bg_item_normal_state
//            }
//
//            holder.mContainer.setBackgroundResource(bgResId)
//        }
//
//
//
//    }
//
//
//    class ViewHolder(itemView: View): AbstractDraggableSwipeableItemViewHolder(itemView) {
//        override fun getSwipeableContainerView(): View? {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        fun  bind(song: Song, listener: (Song) -> Unit): Any {
//            with(itemView){
//                itemTextId.text = song.name
//                if (song.isPlaying){
////                    Set the image to PlayButton
////                    iconSong.image =
//                }
//
//                setOnClickListener { listener(song) }
//            }
//        }
//
//    }
//}