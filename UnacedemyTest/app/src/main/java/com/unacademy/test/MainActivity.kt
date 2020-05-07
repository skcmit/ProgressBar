package com.unacademy.test

import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var progressLoader: ProgressLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressLoader = findViewById(R.id.progress_loader)
    }

    override fun onResume() {
        super.onResume()
        val button: Button = findViewById(R.id.button)
        val editText: EditText = findViewById(R.id.edit_text)
        val context = this
        button.setOnClickListener {
            val inputValue = editText.text.toString().toInt()
            if (inputValue > 100 || inputValue < 0) {
                Toast.makeText(context, getString(R.string.error_text), Toast.LENGTH_SHORT).show()
            } else {
                simulateProgress(inputValue)
            }
        }
    }

    private fun simulateProgress(progressPercentage: Int) {
        val animator = ValueAnimator.ofInt(0, progressPercentage)
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            progressLoader.setProgress(progress)
        }
        animator.duration = 2000
        animator.start()
    }
}