package com.dicoding.myhelloworld

import android.R
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
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

class EditQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      Handle Spinner
        val items = arrayOf("karen", "mermaid", "mrkrab", "sponge", "squid")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerImage.adapter = adapter

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

//      Read Intent Extra
        val index = intent.getIntExtra("question_index", 0)
        binding.txtNewQuestion.setText(QuestionData.questions[index].question)
        binding.txtURL.setText(QuestionData.questions[index].url)
        binding.checkAvailable.isChecked = QuestionData.questions[index].isAvailable

        if(QuestionData.questions[index].answer) {
            binding.radioTrue.isChecked = true
        } else {
            binding.radioFalse.isChecked = true
        }

//      Update Spinner
        val imageName = resources.getResourceEntryName(QuestionData.questions[index].imageId)
        val spinnerIndex = items.indexOf(imageName)
        binding.spinnerImage.setSelection(spinnerIndex)

//      Load Image
        if(QuestionData.questions[index].url!= "") {
            binding.radioExternal.isChecked = true
            val url = QuestionData.questions[index].url

            val builder = Picasso.Builder(this)
            builder.listener { picasso, uri, exception ->
                exception.printStackTrace()
            }
            builder.build().load(url).into(binding.imgPreview)
        } else {
            binding.radioTemplate.isChecked = true
            binding.imgPreview.setImageResource(QuestionData.questions[index].imageId)
        }

        binding.btnSave.setOnClickListener {
            val radioAnswer = findViewById<RadioButton>(binding.radioAnswer.checkedRadioButtonId)
            val selectedImageName = items[binding.spinnerImage.selectedItemPosition]
            val imgid = this.resources.getIdentifier(selectedImageName, "drawable", this.packageName)

            QuestionData.questions[index].question = binding.txtNewQuestion.text.toString()
            QuestionData.questions[index].imageId = imgid
            QuestionData.questions[index].url = binding.txtURL.text.toString()
            QuestionData.questions[index].isAvailable = binding.checkAvailable.isChecked
            QuestionData.questions[index].answer = radioAnswer.text.toString().lowercase().toBoolean()

            Toast.makeText(this, "Question Updated", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}