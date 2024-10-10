package com.dicoding.myhelloworld

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.myhelloworld.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    companion object {
        val PLAYER_NAME = "player_name"
        private var HIGH_SCORE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentScore = intent.getIntExtra(MainActivity.PLAYER_SCORE, 0)

        if (currentScore > HIGH_SCORE) HIGH_SCORE = currentScore
        binding.txtScore.text = currentScore.toString()
        binding.txtHighScore.text = HIGH_SCORE.toString()

        binding.btnPlayAgain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra(MainActivity.PLAYER_SCORE, currentScore)
            startActivity(intent)
        }


    }
}