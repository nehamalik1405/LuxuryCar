package com.a.luxurycar.common.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.a.luxurycar.R

class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener {
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.edtTextCodeOne && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }
}
class GenericTextWatcher internal constructor(private val currentView: View, private val nextView: View?) :
    TextWatcher {


    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        when (currentView.id) {
            R.id.edtTextCodeOne -> if (text.length == 1) nextView!!.requestFocus()
            R.id.edtTextCodeTwo -> if (text.length == 1) nextView!!.requestFocus()
            R.id.edtTextCodeThree -> if (text.length == 1) nextView!!.requestFocus()

        }
    }
    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {}

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }
}
