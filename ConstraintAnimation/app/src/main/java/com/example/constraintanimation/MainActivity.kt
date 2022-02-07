package com.example.constraintanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import kotlinx.android.synthetic.main.start_frame.*
import android.transition.TransitionManager
import androidx.constraintlayout.widget.ConstraintSet


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_frame)
        button.setOnClickListener {
            val constraint = ConstraintSet()
            val transition = AutoTransition()
            transition.duration = 1000
            constraint.clone(this, R.layout.final_frame)
            TransitionManager.beginDelayedTransition(root, transition)
            constraint.applyTo(root)
        }
    }


}