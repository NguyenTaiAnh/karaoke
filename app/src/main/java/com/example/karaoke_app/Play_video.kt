package com.example.karaoke_app

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.media.session.PlaybackState
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.karaoke_app.Adaptertheloai.Adptertbaihat
import com.example.karaoke_app.Adaptertheloai.Adptertheloai
import com.example.karaoke_app.Listen_REC
import com.example.karaoke_app.R
import com.example.karaoke_app.Fragment.HomeFragment
import com.example.karaoke_app.entity.User
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_play_video.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList

class play_video : AppCompatActivity() {
    val users = ArrayList<User>()
    lateinit var mr : MediaRecorder
    lateinit var adapter: Adptertbaihat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)

        val urlGetDatamusic : String = "https://byyswag.000webhostapp.com/?keyword=top karaoke nhạc trẻ 2020"
        Getdata().execute(urlGetDatamusic)
        adapter = Adptertbaihat(users)
        rcvbaihat1.layoutManager = LinearLayoutManager(video.context)
        rcvbaihat1.setHasFixedSize(true)
        rcvbaihat1.adapter = adapter

        val intent = intent

        val url:String? = intent.getStringExtra("URL")
        val title:String? = intent.getStringExtra("name")
        val tentep:String = title.toString()
        val youtube : YouTubePlayerView = findViewById((R.id.youtube_player_view))
        lifecycle.addObserver(youtube)

        var path = Environment.getExternalStorageDirectory().toString()+"/Sounds/$tentep.mp3"

        mr = MediaRecorder()
//        btn_record.isEnabled = false
//        btn_stoprecord.isEnabled = false
//        btn_replayrecord.isEnabled = false
        //==========================================================================================
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.RECORD_AUDIO)!=
                PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                    this, arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 111)
//        btn_record.isEnabled = true
//
//        //=================================batdaughiam======================================
//        btn_record.setOnClickListener {
//            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
//            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//            mr.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
//            mr.setOutputFile(path)
//            mr.prepare()
//            mr.start()
//
//            btn_stoprecord.isEnabled = true
//            btn_record.isEnabled = false
//        }
//
//        //======================================dung ghi am ================================
//        btn_stoprecord.setOnClickListener {
//            mr.stop()
//            var duongdan = path.toString()
//            btn_record.isEnabled = true
//            btn_replayrecord.isEnabled = true
//            btn_stoprecord.isEnabled = false
//        }
//        btn_replayrecord.setOnClickListener {
//            //btn_replayrecord.text = path.toString()
//            var truyenpath : Intent = Intent(this,Listen_REC::class.java)
//            truyenpath.putExtra("linkbaihat",path.toString())
//            startActivity(truyenpath)
//        }


        youtube.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                val videourl = url.toString()
                youTubePlayer.loadVideo(videourl,0f)
                btn_replay.setOnClickListener{
                    youTubePlayer.loadVideo(videourl,0f)
                }
//                youtube_player_view.enterFullScreen()
//                youtube_player_view.exitFullScreen()
//                youtube_player_view.isFullScreen()
//                youtube_player_view.toggleFullScreen()


            }
        })

    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode ==111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            btn_record.isEnabled = true
    }
    override fun onDestroy() {
        super.onDestroy()
        detroy()
    }
    fun detroy(){
        youtube_player_view.release()
    }

    inner class Getdata : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg p0: String?): String {
            return getContentURL(p0[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var idd: String=""
            var namee: String = ""
            var url_s: String = ""
            var jsonArray: JSONArray = JSONArray(result)
            for (video in 0..jsonArray.length() - 1) {
                var objectVD: JSONObject = jsonArray.getJSONObject(video)
                idd= objectVD.getString("ID")
                namee = objectVD.getString("song")
                url_s = objectVD.getString("songkey")
                users.add(User("",idd,namee,url_s))
                //listview.Name.text = name
                //mangbaihat.add(id+"\n"+name+"")
            }
            adapter.notifyDataSetChanged()
        }
    }
    private fun getContentURL(url: String?) : String{
        var content: StringBuilder = StringBuilder();
        val url: URL = URL(url)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

        var line: String = ""
        try {
            do {
                line = bufferedReader.readLine()
                if(line != null){
                    content.append(line)
                }
            }while (line != null)
            bufferedReader.close()
        }catch (e: Exception){
            Log.d("AAA", e.toString())
        }
        return content.toString()
    }
}
