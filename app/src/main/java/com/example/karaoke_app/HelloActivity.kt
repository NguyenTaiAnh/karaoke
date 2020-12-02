package com.example.karaoke_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.Exception

class HelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val delay = Thread {
            try {
                Thread.sleep(1500)
            }catch (e: Exception){

            }finally {
                val home = Intent(this, MainActivity::class.java)
                this.startActivity(home)
            }
        }
        delay.start()
    }
}