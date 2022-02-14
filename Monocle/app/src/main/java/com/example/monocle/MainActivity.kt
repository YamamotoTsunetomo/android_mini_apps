package com.example.monocle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.monocle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<EditText>(R.id.etSignInEnterNumber).setOnClickListener {
            val constraint = ConstraintSet()
            val transition = AutoTransition()
            val startView : ViewGroup = findViewById(R.id.root)
            transition.duration = 500

            constraint.clone(this, R.layout.activity_main_keyframe2)
            TransitionManager.beginDelayedTransition(startView, transition)
            constraint.applyTo(startView as ConstraintLayout?)
        }

        findViewById<Button>(R.id.btnGoToProfile).setOnClickListener {
            Intent(this, SecondActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}