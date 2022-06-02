package com.a.luxurycar.code_files.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import com.a.luxurycar.MainActivity
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.auth.AuthActivity
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.utils.StartActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            if(SessionManager.isUserLoggedIn()) {
                StartActivity(HomeActivity::class.java)
            } else {
                StartActivity(AuthActivity::class.java)
            }
            finish()
        }// 3000 is the delayed time in milliseconds.

    }

    override fun onPause() {
        CoroutineScope(Dispatchers.Main).cancel()
        super.onPause()
    }

    }
