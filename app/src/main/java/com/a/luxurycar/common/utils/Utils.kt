package com.a.luxurycar.common.utils

import android.annotation.TargetApi
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.a.luxurycar.R


import java.io.ByteArrayOutputStream
import java.text.*
import java.util.*


class Utils {

    companion object {

        fun updateLocale(context: Context, localeCode: String) {
            //Log.d("set location function: ", "" + localeCode);

            val local = Locale(localeCode.toLowerCase())
            val res = context.resources
            // Change locale settings in the app.
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = local // Locale(localeCode.toLowerCase())

            conf.setLayoutDirection(local)

            res.updateConfiguration(conf, dm)

        }

        fun downloadFile(context: Context, fileUrl: String, fileName: String) {

            val DownloadUrl: String = fileUrl
            val request1 = DownloadManager.Request(Uri.parse(DownloadUrl))
            request1.setDescription("File Downloading...") //appears the same in Notification bar while downloading
            request1.setTitle(fileName)
            request1.setVisibleInDownloadsUi(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request1.allowScanningByMediaScanner()
                request1.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            }

            /*val appDirectoryName = "1Manch Images"
            val imageRoot = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                ), appDirectoryName
            )
            imageRoot.mkdirs()*/
//            request1.setDestinationInExternalFilesDir(
//                context,
//                "/File",
//                "$fileName")

            request1.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                context.getString(R.string.app_name)+"/$fileName")
            val manager1 = context.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager?
            Objects.requireNonNull(manager1)!!.enqueue(request1)
            if (DownloadManager.STATUS_SUCCESSFUL == 8) {
                Toast.makeText(context, "Downloaded/सेव हो गया", Toast.LENGTH_SHORT).show()
                Log.e("success", "STATUS_SUCCESSFUL")
                //DownloadSuccess()
            } else {
                Toast.makeText(context, "Download failure", Toast.LENGTH_SHORT).show()
                Log.e("success", "STATUS_FAILURE")
            }
        }


        fun getLocale(context: Context, localeCode: String): Locale {
            //Log.d("set location function: ", "" + localeCode);

            val local = Locale(localeCode.toLowerCase())
            val res = context.resources
            // Change locale settings in the app.
            val dm = res.displayMetrics
            val conf = res.configuration
            //  conf.locale = local // Locale(localeCode.toLowerCase())

            //  conf.setLayoutDirection(local)

            // res.updateConfiguration(conf, dm)

            return local
        }


        fun getRealPathFromURI(contentURI: String, context: Context): String? {
            val contentUri = Uri.parse(contentURI)
            val cursor = context.contentResolver.query(contentUri, null, null, null, null)
            if (cursor == null) {
                return contentUri.path
            } else {
                cursor.moveToFirst()
                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                return cursor.getString(index)
            }
        }


        fun convertBitmapToBase64(bitmap: Bitmap): String? {
            var encodedImage: String? = null
            try {
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                encodedImage = Base64.encodeToString(byteArray, Base64.NO_WRAP)

            } catch (ex: NullPointerException) {
                ex.printStackTrace()
            }

            return encodedImage
        }

        public fun isEmptyOrNull(value: String?): Boolean {
            return value == null || value.isEmpty()
        }

        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }


        fun getDeviceId(context: Context): String {

            return Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        fun hideKeyboardIfOpen(activity: Activity) {


            val view = activity.currentFocus
            if (view != null) {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }

        }

        fun getDuration(currentTime: String, time: String): String? {

            var duration: String? = null

            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")

            try {
                val date1 = formatter.parse(currentTime)

                val date2 = formatter.parse(time)

                val diff = date2.time - date1.time
                val diffSeconds = diff / 1000 % 60
                val diffMinutes = truncateData(diff / (60 * 1000) % 60).toLong()
                val diffHours = truncateData(diff / (60 * 60 * 1000)).toLong()
                val diffInDays =
                    truncateData((((date2.time - date1.time) / (1000 * 60 * 60 * 24)) as Int).toLong())

                val weeks = truncateData(getWeeksBetween(date1, date2).toLong())

                val months = truncateData(monthsBetweenDates(date1, date2).toLong())

                if (months > 0) {
                    duration = months.toString() + "Month"
                } else if (weeks > 0) {
                    duration = weeks.toString() + "Week"
                } else if (diffInDays > 0) {
                    duration = diffInDays.toString() + " " + "days"
                } else if (diffHours > 24) {
                    duration = diffHours.toString() + " " + "Hrs"
                } else if (diffHours == 24L && diffMinutes >= 0) {
                    duration = diffMinutes.toString() + " " + "Minutes"
                }

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return duration
        }


        fun getWeeksBetween(a: Date, b: Date): Int {
            var a = a
            var b = b

            if (b.before(a)) {
                return -getWeeksBetween(b, a)
            }
            a = resetTime(a)
            b = resetTime(b)

            val cal = GregorianCalendar()
            cal.time = a
            var weeks = 0
            while (cal.time.before(b)) {
                // add another week
                cal.add(Calendar.WEEK_OF_YEAR, 1)
                weeks++
            }
            return weeks
        }

        fun getWeeks(a: Date, b: Date): Int {
            if (b.before(a)) {
                return -getWeeksBetween(b, a)
            }
            var a1 = resetTime(a)
            var b1 = resetTime(b)

            val cal = GregorianCalendar()
            cal.time = a1
            var weeks = 0
            while (cal.time.before(b1)) {
                // add another week
                cal.add(Calendar.WEEK_OF_YEAR, 1)
                weeks++
            }
            return weeks
        }

        fun resetTime(d: Date): Date {
            val cal = GregorianCalendar()
            cal.time = d
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return cal.time
        }

        fun monthsBetweenDates(startDate: Date, endDate: Date): Int {

            val start = Calendar.getInstance()
            start.time = startDate

            val end = Calendar.getInstance()
            end.time = endDate

            var monthsBetween = 0
            var dateDiff = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH)

            if (dateDiff < 0) {
                val borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH)
                dateDiff =
                    end.get(Calendar.DAY_OF_MONTH) + borrrow - start.get(Calendar.DAY_OF_MONTH)
                monthsBetween--

                if (dateDiff > 0) {
                    monthsBetween++
                }
            } else {
                monthsBetween++
            }
            monthsBetween += end.get(Calendar.MONTH) - start.get(Calendar.MONTH)
            monthsBetween += (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12
            return monthsBetween
        }

        private fun truncateData(value: Long): Int {
            var resultValue = 0
            if (value != 0L) {
                val convertedValue = value.toString()
                val truncatedValue = convertedValue.substring(1, convertedValue.length)

                resultValue = Integer.parseInt(truncatedValue)
            }

            return resultValue

        }


        fun getCurrentDate(): String {
            lateinit var date: String
            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd") as SimpleDateFormat
            var currentDate = Calendar.getInstance().getTime() as Date
            date = simpleDateFormat.format(currentDate)
            return date
        }

        fun getCurrentDateInString(): String {
            var d = Utils.getCurrentDate()
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            var mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)
            mMonth++
            var mDayString = ""
            var mMonthString = ""

            if (mDay < 10) {
                mDayString = "0" + mDay
            } else {
                mDayString = mDay.toString()
            }
            if (mMonth < 10) {
                mMonthString = "0" + mMonth
            } else {
                mMonthString = mMonth.toString()
            }

            var dd = mDayString + "-" + mMonthString + "-" + mYear
            return dd
        }

        fun getDateInStringFormat(mDay: Int, mMonth: Int, mYear: Int): String {

            var mDayString = ""
            var mMonthString = ""

            if (mDay < 10) {
                mDayString = "0" + mDay
            } else {
                mDayString = mDay.toString()
            }
            if (mMonth < 10) {
                mMonthString = "0" + mMonth
            } else {
                mMonthString = mMonth.toString()
            }

            var dd = mDayString + "-" + mMonthString + "-" + mYear
            return dd
        }

        fun convertToDate(date: String): Date {
            var dateFormat = SimpleDateFormat("yyyy-MM-dd");
            var d = dateFormat.parse(date) as Date
            return d
        }

        fun convertDateInDMY(date: String): Date {
            var dateFormat = SimpleDateFormat("dd-MM-yyyy");
            var d = dateFormat.parse(date) as Date
            return d
        }

        fun convertToDate(date: String, dateFormat: String): Date {
            var dateFormat = SimpleDateFormat(dateFormat);
            var d = dateFormat.parse(date) as Date
            return d
        }

        fun changeDateFormat(date: String): String {
            var simpleDateFormat2 = SimpleDateFormat("dd-MM-yyyy");
            var parsedDate = simpleDateFormat2.parse(date);
            simpleDateFormat2 = SimpleDateFormat("yyyy-MM-dd")
            var newFormatttedDate = simpleDateFormat2.format(parsedDate)
            return newFormatttedDate
        }

        fun changeDateFormat(date: String, inputFormat: String, outputFormat: String): String {
            var simpleDateFormat2 = SimpleDateFormat(inputFormat);
            var parsedDate = simpleDateFormat2.parse(date);
            simpleDateFormat2 = SimpleDateFormat(outputFormat)
            var newFormatttedDate = simpleDateFormat2.format(parsedDate)
            return newFormatttedDate
        }

        fun checkIfDateInCurrentMonth(currentDate: Date, inputDate: Date): Boolean {
            var cal1 = Calendar.getInstance();
            var cal2 = Calendar.getInstance();

            cal1.setTime(inputDate);
            cal2.setTime(currentDate);
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                    return true
                }
            }

            return false
        }

        @TargetApi(Build.VERSION_CODES.N)
        public fun getCurrentLocale(context: Context): Locale {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return context.getResources().getConfiguration().getLocales().get(0);
            } else {
                //noinspection deprecation
                return context.resources.configuration.locale;
            }
        }

        fun formatCurrency(value: Double?, context: Context): String {

            val usa = Locale("en", "US")
            var defaultFormat = NumberFormat.getCurrencyInstance(usa);
            return defaultFormat.format(value)
        }

        fun getMimeType(url: String): String? {
            var type: String? = null
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
            return type
        }

        fun parseDate(inputDate: Date): String {

            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val format = formatter.format(inputDate)
            println(format)
            return format

        }

        fun convertMinutesToHour(mintues: String): String {
            var sdf = SimpleDateFormat("mm")

            try {
                val dt = sdf.parse(mintues)
                sdf = SimpleDateFormat("HH:mm")
                println(sdf.format(dt))
                return sdf.format(dt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return ""

        }


        fun setGradientDrawable(startColor: Int, endColor: Int): GradientDrawable {
            val colors = intArrayOf(startColor, endColor)
            val gd = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors
            )

            gd.cornerRadius = 0f

            return gd
        }


        fun getFormatedDateTime(
            dateStr: String?,
            strReadFormat: String?,
            strWriteFormat: String?
        ): String? {
            var formattedDate = dateStr
            val readFormat: DateFormat =
                SimpleDateFormat(strReadFormat, Locale.getDefault())
            val writeFormat: DateFormat =
                SimpleDateFormat(strWriteFormat, Locale.getDefault())
            var date: Date? = null
            try {
                date = readFormat.parse(dateStr)
            } catch (e: ParseException) {
            }
            if (date != null) {
                formattedDate = writeFormat.format(date)
            }
            return formattedDate
        }


        fun percentOf(amount: String, percent: String): String {
            if (amount != null && percent != null && !isEmptyOrNull(amount) && !isEmptyOrNull(
                    percent
                )
            ) {
                var amountDouble = amount.toDouble()
                var percentDouble = percent.toDouble()
                percentDouble = 100 - percentDouble
                percentDouble = percentDouble / 100
                var returnValue = amountDouble * percentDouble
                return roundOffTo2DecPlaces(returnValue)
            }
            return ""
        }

        fun roundOffTo2DecPlaces(value: Double?): String {
            val df = DecimalFormat("0.00")
            // Log.e("double 1", "" + df.format(`val`))
            return df.format(value)
            //return String.format("%.4d", val);
        }

        fun convertTimeIntoSpecificFormat(
            time: String?,
            inputTimeFormat: String?,
            outputTimeFormat: String?
        ): String? {
            try {
                val sdf = SimpleDateFormat(inputTimeFormat, Locale.getDefault())
                val dateObj = sdf.parse(time)
                return SimpleDateFormat(outputTimeFormat, Locale.getDefault()).format(dateObj)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ""
        }

        fun getDaysAgo(date: String?, inputTimeFormat: String?): String? {
            try {
                val sdf = SimpleDateFormat(inputTimeFormat)
                val dateObj = sdf.parse(date)
                val days = (Date().time - dateObj.time) / 86400000

               // Log.e("hours", "" + dateObj.hours)
                //Log.e("min", "" + dateObj.minutes)

                val cal = Calendar.getInstance().time
               // Log.e("cal", "" + cal)
               // Log.e("cal + minutes", "" + cal.minutes)
              //  Log.e("cal + hours", "" + cal.hours)


                var totalMinPost = (dateObj.hours * 60) + dateObj.minutes
                var totalMinCurrent = (cal.hours * 60) + cal.minutes



                if (days == 0L) {

                    var min = totalMinCurrent - totalMinPost
                    var hours = min / 60

                    var hoursStr = ""
                    if (hours < 10) {
                        hoursStr = "0" + hours
                    }else{
                        hoursStr = ""+ hours
                    }
                    var minStr = ""
                    if (min < 10) {
                        minStr = "0" + min
                    }else{
                        minStr = ""+ min
                    }

                    if (hours == 0) {
                        //return "" + minStr + " minutes ago"
                        return "" + minStr + " m ago"

                    } else if (hours == 1) {
                       // return "" + hoursStr + " hour ago"
                        return "" + hoursStr + " h ago"
                    } else {
                        //return "" + hoursStr + " hours ago"
                        return "" + hoursStr + " h ago"
                    }
                    //return "Today"
                } else if (days == 1L) {

                    var hours = dateObj.hours
                    var min = dateObj.minutes
                    var amPm = " am"
                    if (hours > 12) {
                        hours = hours - 12
                        amPm = " pm"
                    } else if (hours == 12) {

                        amPm = " pm"
                    }

                    var hoursStr = ""
                    if (hours < 10) {
                        hoursStr = "0" + hours
                    }else{
                        hoursStr = ""+ hours
                    }
                    var minStr = ""
                    if (min < 10) {
                        minStr = "0" + min
                    }else{
                        minStr = ""+ min
                    }

                   // return "1 d At " + hoursStr + ":" + minStr + amPm
                    return "1 d At "
                } else if (days < 365L){
                    return "$days d ago"
                }else{
                    var year = days/365
                    return "$year y ago"
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ""
        }


        // value -> "2021-05-25T12:20:12.000000Z"  type -> "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"


    }


}