package com.example.karaoke_app

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hello.*
import kotlinx.coroutines.delay
import java.lang.Exception
import java.lang.Thread.sleep
import java.util.Collections.rotate

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        startAnimation()
    }
    private fun startAnimation() {
        val scale: Animation = AnimationUtils.loadAnimation(this,R.anim.scale)

        scale.reset();

        Splash_Image_Logo.setAnimation(scale);

        val mThread: Thread =Thread {
                try {
                    Thread.sleep(1500)
                }catch (e: Exception){

                }finally {
                    val intent: Intent = Intent(this, MainActivity::class.java);
                    startActivity(intent);
                }
        }
        mThread.start();
    }

}

