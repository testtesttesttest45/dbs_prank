package com.example.dbs_prank

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import android.view.inputmethod.InputMethodManager

class MainActivity : ComponentActivity() {
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var investButton: Button
    private lateinit var navButton: Button
    private lateinit var homeButton: Button
    private lateinit var loaderImageView: ImageView
    private lateinit var loaderOverlay: View
    private val handler = Handler()

    private var isMainVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootLayout = findViewById(R.id.root_layout)
        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button3)
        investButton = findViewById(R.id.investButton)
        navButton = findViewById(R.id.navButton)
        homeButton = findViewById(R.id.homeButton)
        loaderImageView = findViewById(R.id.loaderImageView)
        loaderOverlay = findViewById(R.id.loaderOverlay)

        editText.background = null

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                editText.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
            }
        }

        // Show loader for 1 second after MainActivity is launched
        loaderImageView.visibility = View.VISIBLE
        loaderOverlay.visibility = View.VISIBLE
        handler.postDelayed({
            loaderImageView.visibility = View.GONE
            loaderOverlay.visibility = View.GONE
        }, 1500)

        rootLayout.setOnClickListener {
            if (rootLayout.background.constantState != ContextCompat.getDrawable(this, R.drawable.login)?.constantState) {
                loaderImageView.visibility = View.VISIBLE
                loaderOverlay.visibility = View.VISIBLE

                // Hide loader after 1.5 seconds
                handler.postDelayed({
                    loaderImageView.visibility = View.GONE
                    loaderOverlay.visibility = View.GONE
                    rootLayout.setBackgroundResource(R.drawable.login)
                    investButton.visibility = View.GONE
                    homeButton.visibility = View.GONE
                }, 1500)
            }
        }

        button.setOnClickListener {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            handler.postDelayed({
                if (isMainVisible) {
                    rootLayout.setBackgroundResource(R.drawable.main)
                    isMainVisible = false
                    investButton.visibility = View.VISIBLE
                    homeButton.visibility = View.GONE
                } else {
                    rootLayout.setBackgroundResource(R.drawable.invest)
                    isMainVisible = true
                    investButton.visibility = View.GONE
                    homeButton.visibility = View.VISIBLE
                }

                editText.visibility = View.GONE
                button.visibility = View.GONE
                rootLayout.setOnTouchListener { _, _ -> true }
            }, 500)
        }

        // Handle click event for the "Invest" button
        investButton.setOnClickListener {
            // Display "invest.png"
            rootLayout.setBackgroundResource(R.drawable.invest)
            investButton.visibility = View.GONE
            homeButton.visibility = View.VISIBLE

            // Hide the keyboard if it's shown
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            editText.visibility = View.GONE
            button.visibility = View.GONE
            rootLayout.setOnTouchListener { _, _ -> true }
        }

        // Handle click event for the "Home" button
        homeButton.setOnClickListener {
            // Display "main.png"
            rootLayout.setBackgroundResource(R.drawable.main)
            investButton.visibility = View.VISIBLE
            homeButton.visibility = View.GONE
        }

        // Handle click event for the "navButton"
        navButton.setOnClickListener {
            // Display "nav_planner" image
            rootLayout.setBackgroundResource(R.drawable.nav_planner)
            investButton.visibility = View.VISIBLE
            homeButton.visibility = View.VISIBLE
        }
    }
}
