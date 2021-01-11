package com.example.karaoke_app.service

import android.app.Service
import android.content.Intent
import android.media.MediaParser
import android.media.MediaPlayer
import android.os.IBinder

import kotlin.collections.ArrayList

class PlayMusicService: Service() {
    //
    //======================================================================
    //var musicDataList : ArrayList<String> = ArrayList()

    //=======================================================================
    //var mp: MediaPlayer? = null


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        currentPos = intent!!.getIntExtra(SongListAdapter.MUSICITEMPOS,0)
//        //=================================================================
//        musicDataList = intent.getStringArrayListExtra(SongListAdapter.MUSICLIST) as ArrayList<String>
//        mp = MediaPlayer()
//        mp!!.setDataSource(musicDataList[currentPos])
//        mp!!.prepare()
//        mp!!.setOnPreparedListener{
//            mp!!.start()
//        }
        return super.onStartCommand(intent, flags, startId)
    }
}