package com.example.dbs_prank

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import android.os.Handler
import android.view.inputmethod.InputMethodManager


@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var editText: EditText
    private lateinit var button: Button
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootLayout = findViewById(R.id.root_layout)
        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button3)

        // Set the initial background of the EditText to transparent
        editText.background = null

        // Change the EditText background color to white when it is tapped (receives focus)
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                editText.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
            }
        }

        rootLayout.setOnClickListener {
            // Post a delayed task to change the background of the root layout
            handler.postDelayed({
                rootLayout.setBackgroundResource(R.drawable.login)
            }, 500)
        }

        button.setOnClickListener {
            // Hide the keyboard
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            handler.postDelayed({
                rootLayout.setBackgroundResource(R.drawable.main)

                editText.visibility = View.GONE
                button.visibility = View.GONE
                rootLayout.setOnTouchListener { _, _ -> true }
            }, 500) // Delay in milliseconds
        }
    }
}
