package com.a.luxurycar.common.utils

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.ClipboardManager
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.common.requestresponse.Resource
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

import org.json.JSONObject

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
fun JSONObject.convertJsonToRequestBody() : RequestBody {
    return (RequestBody.create(
        "application/json; charset=utf-8".toMediaTypeOrNull(),
        this.toString()
    ))
}
fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun <A : Activity> Activity.StartActivity(activity: Class<A>) {
    Intent(this, activity).also {
        //it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(it)
    }

}

fun <A : Activity> Fragment.StartActivity(activity: Class<A>) {
    Intent(requireActivity(), activity).also {
        //it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(it)
    }

}

fun TextView.SetTextColor(color: Int) {
    setTextColor(
        ContextCompat.getColor(
            context,
            color
        )
    )
}

fun ImageView.SetIconTint(color: Int) {
    setColorFilter(
        ContextCompat.getColor(
            context,
            color
        ),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enable: Boolean) {
    isEnabled = enable
    alpha = if (enable) 1f else 0.5f
}

fun View.snackBar(message: String, action: (() -> Unit)? = null) {

    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.show()

}

fun Fragment.handleApiErrors(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {

    when {
        failure.isNetworkError -> requireView().snackBar(
            "Please check your network connection.",
            retry
        )
        failure.errorCode == 401 -> {
            /*if(this is LoginFragment) {
                requireView().snackBar("You've entered incorrect email or password.")
            } else {*/
            (this as BaseFragment<*, *, *>).logout()
            //}
        }
        else -> {

            if (failure.errorCode == 400) {
                requireView().snackBar(failure.errorBody?.string().toString(), retry)
            } else {
                val error = failure.errorBody?.string().toString()
                requireView().snackBar(error)
            }


        }


    }
}

fun Activity.handleApiErrors(
    failure: Resource.Failure,
    view: View,
    retry: (() -> Unit)? = null
) {

    when {
        failure.isNetworkError -> {
            //Toast.makeText(this, "network error", Toast.LENGTH_SHORT).show()
            view.snackBar("Please check your network connection.", retry)
        }//snackBar("Please check your network connection.", retry)
        /*failure.errorCode == 401 -> {
                (this as BasicActivity).logout()
        }*/
        failure.errorCode == 404 -> view.snackBar(failure.errorBody?.string().toString(), retry)

        else -> {
            val error = failure.errorBody?.string().toString()
            //requireView().snackBar(error)
        }


    }
}


fun Fragment.copyDataInClipBoard(text: String) {
    val modifiedText = text.replace(",", "").replace("[", "").replace("]", "")
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
        val clipboard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.text = modifiedText
    } else {
        val clipboard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = ClipData.newPlainText("Text Copied", modifiedText)
        clipboard.setPrimaryClip(clip)
    }
}

fun String.asUri(): Uri? {
    return try {
        Uri.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Uri?.openInBrowser(context: Context) {
    this ?: return // Do nothing if uri is null

    val browserIntent = Intent(Intent.ACTION_VIEW, this)
    ContextCompat.startActivity(context, browserIntent, null)
}

fun EditText.showErrorAndSetFocus(message: String) {
    error = message
    requestFocus()
}
fun EditText.getTextInString() : String{
    return text.toString().trim()
}

/*fun JSONObject.convertJsonToRequestBody() : RequestBody {
    return (RequestBody.create(
        "application/json; charset=utf-8".toMediaTypeOrNull(),
        this.toString()
    ))
}*/

fun Fragment.getStringFromResource(resId : Int) : String {
    return requireContext().getString(resId)
}

fun Context.toast(message:String){
    Toast.makeText(this, message , Toast.LENGTH_LONG).show()
}
