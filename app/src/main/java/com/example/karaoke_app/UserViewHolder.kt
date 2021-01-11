package com.example.tunkara

import android.content.Intent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.tunkara.R
import com.example.tunkara.entity.User
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class UserViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val ivUserAvatar: ImageView = v.findViewById(R.id.ivUserAvatar)
    private val tvUserName: TextView = v.findViewById(R.id.S_Name)
    private val tvID :TextView = v.findViewById(R.id.S_ID)
    private val tvurl:TextView = v.findViewById(R.id.S_URL)
    private val rlRoot: RelativeLayout = v.findViewById(R.id.rlRoot)
    // private val rlRoot2 : LinearLayout = v.findViewById(R.id.video)
    //private val youtu : YouTubePlayerView? = v.findViewById(R.id.youtube_player_view)
    val intent = Intent(rlRoot.context, play_video::class.java)


    //val pd :ProgressDialog = ProgressDialog()

    fun bind(user: User) {
        ivUserAvatar.setImageResource(R.drawable.avata)
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
