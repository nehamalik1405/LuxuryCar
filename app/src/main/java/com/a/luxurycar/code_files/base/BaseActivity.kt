package com.a.luxurycar.code_files.base;

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import com.a.luxurycar.common.language.LocaleManager.setLocale

open class BaseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // getViewModelForAccessingDB()

        resetTitles()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(setLocale(base))
    }

    protected fun resetTitles() {
        try {
            val info = packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
            if (info.labelRes != 0) {
                setTitle(info.labelRes)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }


  /*  fun getViewModelForAccessingDB() : UserInfoViewModel {

        if(viewModelUserInfo != null) {
            return viewModelUserInfo!!
        } else {
            viewModelUserInfo = ViewModelProvider(this)[UserInfoViewModel::class.java]
            return viewModelUserInfo!!
        }
    }
*/

}