package com.dicoding.myhelloworld

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.DatePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.myhelloworld.databinding.ActivityIntroBinding
import java.util.Locale

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding

    // Special object that's associated with a class. Similar to static members
    // Single Instance have only one companion object
    companion object {
        val PLAYER_NAME = "player_name"
    }

//  DatePicker
    private fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val displayFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val displayDate = displayFormat.format(selectedDate.time)
                binding.txtBod.setText(displayDate)

                val storageFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val storedDate = storageFormat.format(selectedDate.time)
                binding.txtBod.tag = storedDate
            },
            year, month, day
        )
//      display the date
        datePickerDialog.show()
    }

    private fun loadMovie() {
        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressWebView.visibility = View.INVISIBLE
            }
        }

        val videoId = "es7W-hGH99M?si=1Pc16JeuBnHMkr8k"
        val youtubeUrl = "https://www.youtube.com/embed/$videoId"

        binding.webView.loadUrl(youtubeUrl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val playerName = binding.txtPlayerName.text.toString()
            // Intent object requires activity destination, which is Main Activity
            val intent = Intent(this, MainActivity::class.java)
            // Add Extra data inside intent object
            intent.putExtra(PLAYER_NAME, playerName)
            startActivity(intent)  // start Main Activity
            finish()
        }

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, QuestionListActivity::class.java)
            startActivity(intent)
        }

//      saat klik txtBod akan triger datepicker
        binding.txtBod.setOnClickListener { showDatePickerDialog() }

//      menampilkan movie
        loadMovie()
    }
}