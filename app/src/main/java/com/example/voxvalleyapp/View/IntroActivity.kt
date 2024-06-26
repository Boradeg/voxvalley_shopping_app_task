package com.example.voxvalleyapp.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.voxvalleyapp.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

    }

    fun OpenMainActivity(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}