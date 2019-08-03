package com.elmenus.assignment.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elmenus.assignment.main.view.MainActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import java.lang.Thread.sleep

class SplashActivity : AppCompatActivity() {

    private val delayInMs: Long = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doAsync {
            sleep(delayInMs)
            uiThread {
                startActivity<MainActivity>()
            }
        }
    }
}
