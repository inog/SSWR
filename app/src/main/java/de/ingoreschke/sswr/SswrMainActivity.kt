package de.ingoreschke.sswr

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import de.ingoreschke.sswr.utils.Util
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class SswrMainActivity : ActivityIr() {
    private val dateFormater = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    private var mainIntro:TextView? = null
    private var currentDateDisplay: TextView? = null
    private var etDateDisplay: TextView? = null

    private var etDaysToBirthDisplay: TextView? = null
    private var etDaysUntilNowDisplay: TextView? = null
    private var etWeekPlusDaysDisplay: TextView? = null
    private var etXteWeekDisplay: TextView? = null
    private var etXteMonth: TextView? = null

    private var adView: AdView? = null

    private var btnEtDate: Button? = null
    private var btnTimemachine: Button? = null
    private var btnInfoText: Button? = null
    private var etYear: Int = 0
    private var todayYear: Int = 0
    private var etMonth: Int = 0
    private var todayMonth: Int = 0
    private var etDay: Int = 0
    private var todayDay: Int = 0

    private var pregnancyDate: PregnancyDate? = null

    //the callback received when the user "sets" the date in the dialog
    private val mDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        etYear = year
        etMonth = monthOfYear
        etDay = dayOfMonth
        saveEtDate(year, monthOfYear, dayOfMonth)
        updateDateDisplay()
        calculateSswDate()
    }

    // callback for CurrentDatePicker
    private val mCurDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        //set today
        val c = Calendar.getInstance()
        todayYear = year
        todayMonth = monthOfYear
        todayDay = dayOfMonth
        if (year != c.get(Calendar.YEAR) || monthOfYear != c.get(Calendar.MONTH) || dayOfMonth != c.get(Calendar.DAY_OF_MONTH)) {
            btnEtDate!!.isEnabled = false
            mainIntro!!.setText(R.string.str_intro_timemachinemode)
        } else {
            btnEtDate!!.isEnabled = true
            mainIntro!!.text = ""
        }
        updateCurrentDateDisplay()
        calculateSswDate()
    }

    private val isTimeMachineMode: Boolean
        get() {
            val c = Calendar.getInstance()
            return if (todayYear == c.get(Calendar.YEAR) &&
                    todayMonth == c.get(Calendar.MONTH) &&
                    todayDay == c.get(Calendar.DAY_OF_MONTH)) {
                false
            } else {
                true
            }
        }


    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "Sswr started")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_sswr)
        if (isLiteVersion) {
            //create an ad
            adView = AdView(this)
            adView!!.adUnitId = AD_UNIT_ID_MAIN
            adView!!.adSize = AdSize.SMART_BANNER
            //add Adview to hierachy
            findViewById<LinearLayout>(R.id.linearlayout_wrapper).addView(adView)
            //create an adRequest
            val request = AdRequest.Builder().build()
            //start loading the ad in the background
            adView!!.loadAd(request)
        }

        //set ViewElements
        mainIntro = findViewById(R.id.main_intro) as TextView
        currentDateDisplay = findViewById(R.id.currentDate) as TextView
        etDateDisplay = findViewById(R.id.dateDisplay) as TextView
        etDaysToBirthDisplay = findViewById(R.id.str_daysToBirth) as TextView
        etDaysUntilNowDisplay = findViewById(R.id.str_daysUntilNow) as TextView
        etWeekPlusDaysDisplay = findViewById(R.id.str_weekPlusDays) as TextView
        etXteWeekDisplay = findViewById(R.id.str_xteWeek) as TextView
        etXteMonth = findViewById(R.id.str_xteMonth) as TextView
        btnEtDate = findViewById(R.id.main_btnEtDate) as Button
        btnTimemachine = findViewById(R.id.main_btnTimemachine) as Button
        btnInfoText = findViewById(R.id.main_btnWeekInfo) as Button


        //set today
        val c = Calendar.getInstance()
        todayYear = c.get(Calendar.YEAR)
        todayMonth = c.get(Calendar.MONTH)
        todayDay = c.get(Calendar.DAY_OF_MONTH)

        //Restore stored e.t. (birth) or set default
        val et = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        etYear = et.getInt(KEY_ET_YEAR, 0)
        etMonth = et.getInt(KEY_ET_MONTH, 0)
        etDay = et.getInt(KEY_ET_DAY, 0)
        if (etYear != 0) {
            mainIntro!!.text = "" //delete intro text
            updateDateDisplay()
            calculateSswDate()
            btnTimemachine!!.isEnabled = true
        } else {
            //the first use
            btnEtDate!!.setText(R.string.pickDateFirstUse)
            btnTimemachine!!.isEnabled = false
        }
        updateCurrentDateDisplay()
    }

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            FULL_VERSION_REQUIRED -> return AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.full_version_required)
                    .setPositiveButton(android.R.string.ok) { dialog, whichButton -> Toast.makeText(this@SswrMainActivity, "Replace this toast with an intent to start the android market to buy your full version.", Toast.LENGTH_SHORT).show() }
                    .setNegativeButton(android.R.string.cancel
                    ) { dialog, whichButton -> dialog.dismiss() }.create()

            DATE_DIALOG_ID -> return DatePickerDialog(this, mDateSetListener, todayYear, todayMonth, todayDay) //todayYear, todayMonth, todayDay for das erste mal aufrufen.
            CURRENTDATE_DIALOG_ID -> {
                Log.d(TAG, "auf Button showDialog(CURRENTDATE_DIALOG_ID) gestartet ")
                return DatePickerDialog(this, mCurDateSetListener, todayYear, todayMonth, todayDay)
            }
            PRE_CURRENTDATE_DIALOG_ID -> {
                Log.d(TAG, "auf Button Zeitmaschine geklickt")
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.alertboxTimemachine_title)
                builder.setMessage(R.string.alertboxTimemachine_text)
                builder.setPositiveButton(android.R.string.ok) { dialog, which -> showDialog(CURRENTDATE_DIALOG_ID) }
                builder.setNegativeButton(android.R.string.cancel) { dialog, which -> dialog.cancel() }
                return builder.create()
            }
        }
        return null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_et_date) {
            showDialog(DATE_DIALOG_ID)
            return true
        } else if (id == R.id.menu_et_timemaschine) {
            showDialog(PRE_CURRENTDATE_DIALOG_ID)
            return true
        } else if (id == R.id.about) {
            startActivity(Intent(this, About::class.java))
            return true
        } else if (id == R.id.menu_feedback) {
            val ci = Intent(Intent.ACTION_SEND)
            ci.type = "text/html"
            ci.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_contact_email_sub))
            ci.putExtra(Intent.EXTRA_TEXT, getString(R.string.about_contact_email_text))
            ci.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.about_contact_email)))
            startActivity(Intent.createChooser(ci, getString(R.string.about_contact_email_Intent)))
        } else if (id == R.id.menu_tellAFriend) {
            val title = getString(R.string.tellAFriendSubject)
            var text = ""
            if (pregnancyDate != null) {
                text = getString(R.string.tellAFriendText) + " " + pregnancyDate!!.xteWeek.toString() + getString(R.string.str_xteWeek_suffix) + "."
            }
            text += getString(R.string.tellAFriendText2)
            if (isLiteVersion) {
                text += getString(R.string.tellAFriendLinkLight)
            } else {
                text += getString(R.string.tellAFriendLinkFull)
            }

            val tellAFriendIntent = Intent(Intent.ACTION_SEND)
            tellAFriendIntent.type = "text/plain"
            tellAFriendIntent.putExtra(Intent.EXTRA_SUBJECT, title)
            tellAFriendIntent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(tellAFriendIntent, getString(R.string.tellAFriend_title)))
        } else if (id == R.id.menu_weekinfo) {
            callWeekInfo()
        }
        //More items go here (if any) ...
        return false
    }

    override fun onStop() {
        super.onStop()
        saveEtDate(etYear, etMonth, etDay)
    }

    override fun onDestroy() {
        if (adView != null) {
            adView!!.destroy()
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        if (adView != null) {
            adView!!.resume()
        }
    }

    override fun onPause() {
        if (adView != null) {
            adView!!.pause()
        }
        super.onPause()
    }

    fun onClick(v: View) {
        val id = v.id
        if (id == R.id.main_btnEtDate || id == R.id.main_et_row) {
            showDialog(DATE_DIALOG_ID)
        } else if (id == R.id.main_btnTimemachine) {
            if (isTimeMachineMode) {
                showDialog(CURRENTDATE_DIALOG_ID)
            } else {
                showDialog(PRE_CURRENTDATE_DIALOG_ID)
            }
        } else if (id == R.id.main_btnWeekInfo || id == R.id.main_xteWeek_row) {
            callWeekInfo()
        } else {
        }
    }

    private fun callWeekInfo() {
        if (this.pregnancyDate != null) {
            val weekl = pregnancyDate!!.xteWeek
            val week = Util.safeLongToInt(weekl)
            val i = Intent(this, WeekInfo::class.java)
            i.putExtra("week", week)
            startActivity(i)
        } else {
            Toast.makeText(this, R.string.errorNoDate, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateCurrentDateDisplay() {
        currentDateDisplay!!.text = LocalDate.of(todayYear, todayMonth + 1, todayDay).format(dateFormater)
    }

    private fun updateDateDisplay() {
        etDateDisplay!!.text = LocalDate.of(etYear, etMonth + 1, etDay).format(dateFormater)
    }

    private fun calculateSswDate() {
        val today = LocalDate.of(todayYear, todayMonth + 1,todayDay)
        val birthDate = LocalDate.of(etYear, etMonth +1 , etDay)

        try {
            this.pregnancyDate = PregnancyDate(today, birthDate)
            showResult()
            if (!isTimeMachineMode) {
                updateWidget()
            }
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, e.message)
            var error = ""
            if (e.message == PregnancyDate.DATE1_TOO_SMALL) {
                error = getString(R.string.errorIllegalDate_d1TooSmall)
            } else if (e.message == PregnancyDate.DATE2_TOO_BIG) {
                error = getString(R.string.errorIllegalDate_d2TooBig)
            } else {
                error = e.message!!
            }
            showError(error)
        }

    }

    private fun showResult() {
        etDaysToBirthDisplay!!.text = pregnancyDate!!.daysToBirth.toString()
        etDaysUntilNowDisplay!!.text = pregnancyDate!!.daysUntilNow.toString()
        etWeekPlusDaysDisplay!!.text = pregnancyDate!!.weeksUntilNow.toString() + " + " + pregnancyDate!!.restOfWeekUntilNow.toString()

        var w = pregnancyDate!!.xteWeek.toString()
        w = w + getString(R.string.str_xteWeek_suffix)
        etXteWeekDisplay!!.text = w
        var m = pregnancyDate!!.xteMonth.toString()
        m = m + getString(R.string.str_xteMonth_suffix)
        etXteMonth!!.text = m
    }

    private fun showError(error: String) {


        AlertDialog.Builder(this)
                .setTitle(getString(R.string.errorTitle))
                .setMessage(error)
                .setCancelable(true)
                .setNeutralButton(android.R.string.cancel
                ) { dialog, which -> }
                .show()
    }

    private fun updateWidget() {
        Log.d(TAG, "updateWidget")
        val week = pregnancyDate!!.weeksUntilNow.toString() + " + " + pregnancyDate!!.restOfWeekUntilNow.toString()
        val xteWeek = pregnancyDate!!.xteWeek.toString() + getString(R.string.str_xteWeek_suffix)
        val appWidgetManager = AppWidgetManager.getInstance(this) //this is context
        val views = RemoteViews(this.packageName, R.layout.widget_layout)
        //Perform update on the view
        views.setTextViewText(R.id.widgetTV02, week)
        views.setTextViewText(R.id.widgetTV03, xteWeek)
        appWidgetManager.updateAppWidget(
                ComponentName(this, Widget::class.java!!),
                views
        )
    }


    private fun saveEtDate(year: Int, month: Int, day: Int): Boolean {
        Log.d(TAG, "saveEtDate is called")
        if (year != 0) {
            // We need an Editor object to make preference changes.
            // All objects are from android.context.Context
            val et = getSharedPreferences(PREFS_NAME, 0)
            val editor = et.edit()
            editor.putInt(KEY_ET_YEAR, year)
            editor.putInt(KEY_ET_MONTH, month)
            editor.putInt(KEY_ET_DAY, day)

            Log.d(TAG, "year:$year month:$month day:$day")

            // Commit the edits!
            editor.commit()
            return true
        } else {
            return false
        }
    }

    companion object {
        private val TAG = "SswrMainActivity"
        private val PREFS_NAME = "et_date"
        private val KEY_ET_YEAR = "etYear"
        private val KEY_ET_MONTH = "etMonth"
        private val KEY_ET_DAY = "etDAY"

        private val DATE_DIALOG_ID = 0
        private val PRE_CURRENTDATE_DIALOG_ID = 1
        private val CURRENTDATE_DIALOG_ID = 2
    }
}