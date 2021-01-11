package com.example.karaoke_app.Adapterbaihat

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.karaoke_app.Listen_REC
import com.example.karaoke_app.Interface.CostomItemClickListener
import com.example.karaoke_app.R
import com.example.karaoke_app.model.SongModel
import com.example.karaoke_app.service.PlayMusicService

class SongListAdapter(SongModel:ArrayList<SongModel>,context:Context):RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {
    var mContext = context
    var mSongModel = SongModel
    var allMusicList :ArrayList<String> = ArrayList()

    companion object{
        val MUSICLIST = "musiclist"
        val MUSICITEMPOS = "pos"
    }

    override fun getItemCount(): Int {
        return mSongModel.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_baihat,parent,false)
        return SongListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        var model = mSongModel[position]
        var songName = model.mSongName
        var songDuration = toMandS(model.mSongDuration.toLong())

        holder!!.songTV.text = songName
        holder!!.durationTV.text = songDuration
        holder!!.setCostomItemClickListener(object :CostomItemClickListener{
            override fun onCostomItemClickListen(view: View, pos: Int) {
                for(i in 0 until mSongModel.size){
                    allMusicList.add(mSongModel[i].mSongPath)
                }
                Toast.makeText(mContext,"songTitle: "+songName,Toast.LENGTH_SHORT).show()
                var path = Environment.getExternalStorageDirectory().toString()+"/Sounds/$songName.mp3"
                Log.i("allll",allMusicList.toString())
                var musicDataIntent = Intent(mContext,PlayMusicService::class.java)
                musicDataIntent.putStringArrayListExtra(MUSICLIST,allMusicList)
                musicDataIntent.putExtra(MUSICITEMPOS,pos)
                mContext.startService(musicDataIntent)
                var intent : Intent = Intent(mContext,Listen_REC::class.java)
                intent.putExtra("linkbaihat",path.toString())
                mContext.startActivity(intent)
            }
        })
    }
    fun toMandS(millis:Long):String{
        var duration = String.format("%02d:%02d",
            java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(millis),
            java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(millis)-java.util.concurrent.TimeUnit.MINUTES.toSeconds(
                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(millis)
            ))
        return duration

    }

    class SongListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var songTV:TextView
        var durationTV: TextView
        var mCostomItemClickListener : CostomItemClickListener?= null
        init{
            songTV = itemView.findViewById(R.id.tvtenbh)
            durationTV = itemView.findViewById(R.id.tvID)
            itemView.setOnClickListener(this)
        }
        fun setCostomItemClickListener(costomItemClickListener: CostomItemClickListener){
            this.mCostomItemClickListener = costomItemClickListener
        }

        override fun onClick(view: View?){
            this.mCostomItemClickListener!!.onCostomItemClickListen(view!!,adapterPosition)
        }
    }
}