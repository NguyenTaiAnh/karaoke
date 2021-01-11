package com.example.karaoke_app

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_listen__rec.*

class Listen_REC : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listen__rec)
        var inten = intent
         var path:String? = inten.getStringExtra("linkbaihat")
        var mp = MediaPlayer()
        mp.setDataSource(path)
        mp.prepare()
        stoppp.setOnClickListener {
           mp.stop()
        }
        //mp.start()
        onnn.setOnClickListener {
            mp.start()
        }
        exit.setOnClickListener {
            finishAffinity()
        }

    }
}
