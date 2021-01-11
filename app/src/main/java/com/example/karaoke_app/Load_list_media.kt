package com.example.tunkara

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tunkara.adapters.SongListAdapter
import com.example.tunkara.model.SongModel
import kotlinx.android.synthetic.main.activity_load_list_media.*

class Load_list_media : AppCompatActivity() {

    var songModelData  : ArrayList<SongModel> = ArrayList()
    var songListAdapter :SongListAdapter? =null

    companion object{
        val PERMISSION_REQUEST_CODE = 12
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_list_media)

        if(ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }else{
            loadData()
        }

    }

    fun loadData(){
        //tao query toi ds bai hat
        var songCursor:Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,null,null,null)


        while(songCursor!=null && songCursor.moveToNext()){
            var songPath = songCursor!!.getString(songCursor!!.getColumnIndex(MediaStore.Audio.Media.DATA))
            var songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            var songDuration = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            songModelData.add(SongModel(songName,songDuration,songPath))
        }
        songListAdapter = SongListAdapter(songModelData,applicationContext)
        var layoutManager = LinearLayoutManager(applicationContext)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = songListAdapter
    }
    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty()&& grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"Đã cấp quyền",Toast.LENGTH_SHORT).show()
                loadData()
            }
        }
    }
}
