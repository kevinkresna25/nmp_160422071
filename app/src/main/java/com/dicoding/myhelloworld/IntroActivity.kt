package com.dicoding.myhelloworld

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.myhelloworld.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding

    // Special object that's associated with a class. Similar to static members
    // Single Instance have only one companion object
    companion object {
        val PLAYER_NAME = "player_name"
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
        }

    }
}