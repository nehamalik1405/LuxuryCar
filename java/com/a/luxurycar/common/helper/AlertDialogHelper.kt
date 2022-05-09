package com.a.luxurycar.common.helper

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.a.luxurycar.R

class AlertDialogHelper {

    companion object {

        fun alertCloseApp(context: Context) {

            val alertDialoge = android.app.AlertDialog.Builder(context)

            alertDialoge.setCancelable(false)
            alertDialoge.setMessage(context.getString(R.string.app_exit))

            alertDialoge.setPositiveButton(context.getString(R.string.title_ok)) { dialog, which ->
                (context as Activity).finishAffinity()
                dialog.dismiss()
            }

            alertDialoge.setNegativeButton(context.getString(R.string.title_cancel)) { dialog, which ->
                dialog.dismiss()
            }

            alertDialoge.show()
        }


        fun showMessage(context: Context, msg: String) {
            val alertDialog = AlertDialog.Builder(context)

            // Setting Dialog Message
            alertDialog.setMessage(msg)
            alertDialog.setCancelable(false)
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("OK") { dialog, which ->

                dialog.dismiss()
            }

            // Showing Alert Message
            alertDialog.show()
        }


    }

}