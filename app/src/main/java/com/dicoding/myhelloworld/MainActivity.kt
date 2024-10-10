package com.dicoding.myhelloworld

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.myhelloworld.IntroActivity.Companion.PLAYER_NAME
import com.dicoding.myhelloworld.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    private lateinit var binding: ActivityMainBinding

    companion object {
        val PLAYER_SCORE = "player_score"
        private var PLAYER_NAME: String = "guest"
    }

    var currentScore = 0
    var currentQuestion: Int = 0
    private lateinit var availableQuestion:Array<QuestionBank>


//    val questions = arrayOf(
//        QuestionBank("Krusty Krab is the favorite burger in Bikini Bottom", false),
//        QuestionBank("Spongebob lives in a pineapple under the sea", true),
//        QuestionBank("Karen is Plankton’s wife", true),
//        QuestionBank("Mermaid Man was once a superhero of Bikini Bottom", true),
//        QuestionBank("Squidward has four hands", false),
//    )

//    var questions = availableQuestion

    var wrong: Int = 0;
    var right: Int = 0;
    var attempts: Int = 1;

    fun displayQuestions() {
//        if (currentQuestion > questions.size - 1 && right < 5) {
//            binding.txtQuestion.text = "End of Quiz! You haven't reached the 5 points! Want to Re-Attempt?"
//            binding.btnTrue.isEnabled = false
//            binding.btnTrue.isClickable = false
//            binding.btnFalse.isEnabled = false
//            binding.btnFalse.isClickable = false
//            binding.btnReAttempt.visibility = Button.VISIBLE
//        } else {
//            binding.txtQuestion.text = questions[currentQuestion].question
//        }

        // Week 3
        if (currentQuestion > availableQuestion.size - 1) {
            currentQuestion = 0
            availableQuestion.shuffle()
        }

//      availableQuestion
        if(availableQuestion[currentQuestion].url != "") {
            val builder = Picasso.Builder(this)
            val url = availableQuestion[currentQuestion].url
            builder.listener { picasso, uri, exception -> exception.printStackTrace() }
            builder.build().load(url).into(binding.imageView)
        } else {
            binding.imageView.setImageResource(availableQuestion[currentQuestion].imageId)
        }

        binding.txtQuestion.text = availableQuestion[currentQuestion].question
    }

    fun nextQuestion() {
//        if (wrong >= 3) {
//            Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show()
//            binding.txtQuestion.text = "You lose! Learn more about Bikini Bottom! Want to Re Attempt?"
//            binding.btnTrue.isEnabled = false
//            binding.btnTrue.isClickable = false
//            binding.btnFalse.isEnabled = false
//            binding.btnFalse.isClickable = false
//            binding.btnReAttempt.visibility = Button.VISIBLE
//            return
//        }

//        if (right == 5) {
//            binding.btnTrue.isEnabled = false
//            binding.btnTrue.isClickable = false
//            binding.btnFalse.isEnabled = false
//            binding.btnFalse.isClickable = false
//            binding.txtQuestion.text = "Score: $right / Attempts: $attempts"
//            Toast.makeText(this, "You WIN!!", Toast.LENGTH_SHORT).show()
//            return;
//        }

        // Week 3 - if wrong, redirect to result activity
        if (wrong >= 3) {
            val intent = Intent(this, ResultActivity::class.java)
            // Add Extra data inside intent object
            intent.putExtra(PLAYER_SCORE, currentScore)
            startActivity(intent)  // start Main Activity
        }
        currentQuestion++

//      availableQuestion
        if(currentQuestion >= availableQuestion.size) {
            currentQuestion = 0
            availableQuestion = QuestionData.questions.filter {
                it.isAvailable }
                .toTypedArray()
            availableQuestion.shuffle()
        }

        displayQuestions()
    }

    fun answerCurrentQuestion(answer: Boolean) {
        if (currentQuestion > availableQuestion.size - 1) {
//            Toast.makeText(this, "End of Quiz", Toast.LENGTH_SHORT).show()
//            return
            // Week 3 - reset index to 0
            currentQuestion = 0
        }

        if (availableQuestion[currentQuestion].answer) {
            if (answer) {
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
                right++
                currentScore++
            }
            else {
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
                wrong++
            }
        } else {
            if (answer) {
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
                wrong++
            }
            else {
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
                right++
                currentScore++
            }
        }

//        binding.txtScore.text = "Score: " + right

        // Week 3
        binding.txtScore.text = "Score: " + currentScore
        nextQuestion()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        // Receive data from prev activity using Companion Object
        val playerName = intent.getStringExtra(IntroActivity.PLAYER_NAME)

        // Just in case if the prev activity is not from IntroActivity
        if (playerName != null) {
            PLAYER_NAME = playerName
        }
        binding.txtWelcome.text = "Welcome, $PLAYER_NAME"

        binding.btnReAttempt.visibility = Button.INVISIBLE;

//        availableQuestion.shuffle()

//      availableQuestion
        availableQuestion = QuestionData.questions.filter {
            it.isAvailable }
            .toTypedArray()
        availableQuestion.shuffle()

//      display Questions
        displayQuestions()

        binding.btnTrue.setOnClickListener {
            answerCurrentQuestion(true)
        }

        binding.btnFalse.setOnClickListener {
            answerCurrentQuestion(false)
        }

//        binding.btnReAttempt.setOnClickListener {
//            currentQuestion = 0
//            attempts++
//            questions.shuffle()
//            wrong = 0
//            right = 0
//            binding.btnReAttempt.visibility = Button.INVISIBLE
//            binding.btnTrue.isEnabled = true
//            binding.btnTrue.isClickable = true
//            binding.btnFalse.isEnabled = true
//            binding.btnFalse.isClickable = true
//            binding.txtScore.text = "Score: " + right
//            displayQuestions()
//        }
    }
}