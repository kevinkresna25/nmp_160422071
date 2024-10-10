package com.dicoding.myhelloworld

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.myhelloworld.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var HIGH_SCORE = 0

    companion object {
        val PLAYER_NAME = "player_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentScore = intent.getIntExtra(MainActivity.PLAYER_SCORE, 0)

//      high score SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("SETTING", Context.MODE_PRIVATE)
        HIGH_SCORE = sharedPreferences.getString("HIGH_SCORE", "0").toString().toInt()

        if (currentScore > HIGH_SCORE) {
            HIGH_SCORE = currentScore
            val editor = sharedPreferences.edit()
            editor.putString("HIGH_SCORE", HIGH_SCORE.toString())
            editor.apply()
        }

        binding.txtScore.text = currentScore.toString()
        binding.txtHighScore.text = HIGH_SCORE.toString()

        binding.btnPlayAgain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra(MainActivity.PLAYER_SCORE, currentScore)
            startActivity(intent)
        }


    }
}