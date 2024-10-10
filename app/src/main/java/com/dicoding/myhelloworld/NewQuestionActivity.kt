package com.dicoding.myhelloworld

import android.R
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.myhelloworld.databinding.ActivityNewQuestionBinding
import com.squareup.picasso.Picasso


class NewQuestionActivity : AppCompatActivity() {
    //  declare manual
    private lateinit var binding: ActivityNewQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.labelImageURL.visibility = View.GONE
        binding.txtURL.visibility = View.GONE

//      show/hide code for radioExternal!
        binding.radioTemplate.setOnClickListener {
            with(binding) {
                labelChooseImage.visibility = View.VISIBLE
                spinnerImage.visibility = View.VISIBLE
                labelImageURL.visibility = View.GONE
                txtURL.visibility = View.GONE
            }
        }

//      gada di ppt (manual)
        binding.radioExternal.setOnClickListener {
            with(binding) {
                labelChooseImage.visibility = View.GONE
                spinnerImage.visibility = View.GONE
                labelImageURL.visibility = View.VISIBLE
                txtURL.visibility = View.VISIBLE
            }
        }

//      Handle Spinner
        val items = arrayOf("karen", "mermaid", "mrkrab", "sponge", "squid")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerImage.adapter = adapter

//      Set default gambar
        val imgid = this.resources.getIdentifier("karen", "drawable", this.packageName)
        binding.imgPreview.setImageResource(imgid)

//      untuk mengubah gambar bersar spinner
        binding.spinnerImage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val imgId = applicationContext.resources.getIdentifier(
                    items[position],
                    "drawable",
                    applicationContext.packageName
                )
                binding.imgPreview.setImageResource(imgId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

//      Set gambar berdasar URL menggunakan Picasso Library
        binding.txtURL.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER) {
                val url = binding.txtURL.text.toString()
                val builder = Picasso.Builder(this)
                builder.listener { picasso, uri, exception ->
                    exception.printStackTrace()
                }
                builder.build().load(url).into(binding.imgPreview)
            }
            true
        }

//      button save
        binding.btnSave.setOnClickListener {
            val radioAnswer = findViewById<RadioButton>(binding.radioAnswer.checkedRadioButtonId)
            val selectedImageName = items[binding.spinnerImage.selectedItemPosition]
            val imgId = this.resources.getIdentifier(selectedImageName, "drawable", this.packageName)
//            val newQuestion = QuestionBank(binding.txtNewQuestion.text.toString(), radioAnswer.text.toString().lowercase().toBoolean(), imgId)
//            edit untuk image url
            val newQuestion = QuestionBank(binding.txtNewQuestion.text.toString(),
                radioAnswer.text.toString().lowercase().toBoolean(),
                imgid, binding.txtURL.text.toString(),
                binding.checkAvailable.isChecked)

//          menambahkan objek ke dalam array
            val questionsList = QuestionData.questions.toMutableList()
            questionsList.add(newQuestion)
            QuestionData.questions = questionsList.toTypedArray()

            Toast.makeText(this, "Question Added", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}