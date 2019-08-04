package com.elmenus.assignment.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elmenus.assignment.constants.AppConstants
import com.elmenus.assignment.main.view.MainActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import java.lang.Thread.sleep

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doAsync {
            sleep(AppConstants.SPLASH_SCREEN_WAIT_IN_MS)
            uiThread {
                startActivity<MainActivity>()
            }
        }
    }
}
