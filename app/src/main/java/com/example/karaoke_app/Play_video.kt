package com.example.karaoke_app

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.media.session.PlaybackState
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.karaoke_app.Listen_REC
import com.example.karaoke_app.R
import com.example.karaoke_app.entity.User
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_play_video.*
import kotlinx.android.synthetic.main.fragment_home.*

class play_video : AppCompatActivity() {

    lateinit var mr : MediaRecorder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)

        val intent = intent

        val url:String? = intent.getStringExtra("URL")
        val title:String? = intent.getStringExtra("name")
        val tentep:String = title.toString()
        val youtube : YouTubePlayerView = findViewById((R.id.youtube_player_view))
        lifecycle.addObserver(youtube)

        var path = Environment.getExternalStorageDirectory().toString()+"/Sounds/$tentep.mp3"

        mr = MediaRecorder()
        btn_record.isEnabled = false
        btn_stoprecord.isEnabled = false
        btn_replayrecord.isEnabled = false
        //==========================================================================================
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.RECORD_AUDIO)!=
                PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                    this, arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 111)
        btn_record.isEnabled = true

        //=================================batdaughiam======================================
        btn_record.setOnClickListener {
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mr.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
            mr.setOutputFile(path)
            mr.prepare()
            mr.start()

            btn_stoprecord.isEnabled = true
            btn_record.isEnabled = false
        }

        //======================================dung ghi am ================================
        btn_stoprecord.setOnClickListener {
            mr.stop()
            var duongdan = path.toString()
            btn_record.isEnabled = true
            btn_replayrecord.isEnabled = true
            btn_stoprecord.isEnabled = false
        }
        btn_replayrecord.setOnClickListener {
            //btn_replayrecord.text = path.toString()
            var truyenpath : Intent = Intent(this,Listen_REC::class.java)
            truyenpath.putExtra("linkbaihat",path.toString())
            startActivity(truyenpath)
        }


        youtube.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                val videourl = url.toString()
                youTubePlayer.loadVideo(videourl,0f)
                btn_replay.setOnClickListener{
                    youTubePlayer.loadVideo(videourl,0f)
                }
                //youtube_player_view.enterFullScreen()
                //youtube_player_view.exitFullScreen()
                //youtube_player_view.isFullScreen()
                //youtube_player_view.toggleFullScreen()


            }
        })

    }
    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode ==111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            btn_record.isEnabled = true
    }
    override fun onDestroy() {
        super.onDestroy()
        detroy()
    }
    fun detroy(){
        youtube_player_view.release()
    }

}
