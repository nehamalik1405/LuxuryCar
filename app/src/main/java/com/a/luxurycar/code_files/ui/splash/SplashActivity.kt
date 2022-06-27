package com.a.luxurycar.code_files.ui.splash

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import com.a.luxurycar.MainActivity
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.auth.AuthActivity
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.ui.seller_deshboard.SellerDeshboardActivity
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.utils.StartActivity
import kotlinx.coroutines.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val userRole = SessionManager.getUserRole()

        getHashKey()
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            if (userRole != null && userRole.equals(Const.KEY_BUYER, true)) {
                StartActivity(HomeActivity::class.java)
            }
            else if (userRole != null && userRole.equals(Const.KEY_SELLER, true)) {
                    StartActivity(SellerDeshboardActivity::class.java)
                }
            else {
                StartActivity(AuthActivity::class.java)
            }
            finish()
        }// 3000 is the delayed time in milliseconds.

    }
    private fun getHashKey() {
// Add code to print out the key hash
        try {
            val info = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }
    override fun onPause() {
        CoroutineScope(Dispatchers.Main).cancel()
        super.onPause()
    }

    }
