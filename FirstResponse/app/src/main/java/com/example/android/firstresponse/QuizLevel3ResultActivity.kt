package com.example.android.firstresponse

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

class QuizLevel3ResultActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private lateinit var retryButton: Button
    private lateinit var homeButton: Button

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_level3_result)

        supportActionBar?.hide()

        // Initialize LottieAnimationView
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        lottieAnimationView.apply {
            setAnimation(R.raw.result) // Ensure this matches your animation file in res/raw/
            playAnimation()
            repeatCount = LottieDrawable.INFINITE // Loop animation
        }

        resultTextView = findViewById(R.id.resultTextView)
        retryButton = findViewById(R.id.retryButton)
        homeButton = findViewById(R.id.mainMenuButton)

        // Retrieve the score from the intent
        val score = intent.getIntExtra("score", 0)
        val highestScore = sharedPreferences.getInt("highest_score_level3", 0)

        // Update SharedPreferences if the current score is higher than the highest score
        if (score > highestScore) {
            sharedPreferences.edit().putInt("highest_score_level3", score).apply()
        }

        // Provide feedback based on the score
        val feedbackMessage = when {
            score == 15 -> "Excellent! You got a perfect score!"
            score >= 10 -> "Great job! You have a good grasp of the material."
            score >= 5 -> "Good effort! Keep practicing to improve."
            else -> "Keep trying! You'll get better with more practice."
        }

        // Display the current score, highest score, and feedback message
        resultTextView.text = "Your Score: $score\nHighest Score: $highestScore\n$feedbackMessage"

        retryButton.setOnClickListener {
            finish() // Close QuizLevel3ResultActivity
            startActivity(Intent(this, QuizLevel3Activity::class.java)) // Restart QuizLevel3Activity
        }

        homeButton.setOnClickListener {
            finish() // Close QuizLevel3ResultActivity
            startActivity(Intent(this, QuizPageActivity::class.java)) // Go back to Quiz Page
        }
    }
}
