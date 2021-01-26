package com.example.karaoke_app

import android.content.Intent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.karaoke_app.R
import com.example.karaoke_app.entity.User
import com.example.karaoke_app.play_video

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class UserViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val ivUserAvatar: ImageView = v.findViewById(R.id.imvlogomusic)
    private val tvUserName: TextView = v.findViewById(R.id.tvtenbh)
    private val tvID :TextView = v.findViewById(R.id.tvID)
    private val tvurl:TextView = v.findViewById(R.id.tvurl)
    private val rlRoot: RelativeLayout = v.findViewById(R.id.Relativebaihat)
    // private val rlRoot2 : LinearLayout = v.findViewById(R.id.video)
    //private val youtu : YouTubePlayerView? = v.findViewById(R.id.youtube_player_view)
    val intent = Intent(rlRoot.context, play_video::class.java)

    //val pd :ProgressDialog = ProgressDialog()

    fun bind(user: User) {
        ivUserAvatar.setImageResource(R.drawable.ic_disc)
        tvUserName.text = user.name
        tvID.text= ""//user.id
        tvurl.text = user.url
        // Sự kiện khi nhấn vào
        clickEvent(user)
    }

    private fun clickEvent(user: User) {

        rlRoot.setOnClickListener {

            val link : String = user.url
            intent.putExtra("URL",link)
            val title : String = user.name
            intent.putExtra("name",title)
            rlRoot.context.startActivity(intent)

            Toast.makeText(
                rlRoot.context,
                "Đã nhấn id ${user.id} có tên ${user.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
