package com.example.simplecalculator

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val numbersAndOperatorsList = listOf<TextView>(
            tvPower, tvDivide,
            tvMultiply, tvSubtract, tvAdd, tvOne, tvTwo, tvThree, tvFour, tvFive,
            tvSix, tvSeven, tvEight, tvNine, tvZero, tvComma
        )

        numbersAndOperatorsList.forEach { btn ->
            btn.setOnClickListener {
                val expression = tvResult.text.toString() + btn.text.toString()
                tvResult.text = expression
            }
        }


        tvPI.setOnClickListener {
            val expression = tvResult.text.toString() + "3.14"
            tvResult.text = expression
        }

        tvC.setOnClickListener { tvResult.text = "" }

        tvDelete.setOnClickListener {
            val currentExpression = tvResult.text.toString()
            tvResult.text = currentExpression.dropLast(1)
        }

        tvEquals.setOnClickListener {
            tvResult.text = evaluate(tvResult.text.toString())
        }
    }

    private fun evaluate(expression: String): String {
        val values: Stack<Double> = Stack()
        val operations: Stack<Char> = Stack()
        val operators = "+-/*^"
        fun isNumber(x: Char) = x in '0'..'9' || x == '.'
        var index = 0
        while (index < expression.length) {
            try {
                when {
                    isNumber(expression[index]) -> {
                        val number = StringBuilder()
                        while (index < expression.length && isNumber(expression[index]))
                            number.append(expression[index++])
                        values.push(number.toString().toDouble())
                        index--
                    }

                    expression[index] in operators -> {
                        while (!operations.empty() && hasPrecedence(
                                expression[index],
                                operations.peek()
                            )
                        )
                            values.push(
                                applyOperation(
                                    operations.pop(),
                                    values.pop(),
                                    values.pop()
                                )
                            )
                        operations.push(expression[index])
                    }
                }
            } catch (e: Exception) {
                return "inv. expression"
            }
            index++
        }

        while (!operations.empty()) {
            values.push(applyOperation(operations.pop(), values.pop(), values.pop()))
        }
        return values.pop().toString()
    }

    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op1 in "*/" && (op2 in "+-"))
            return false
        return true
    }

    private fun applyOperation(op: Char, b: Double, a: Double): Double {
        when (op) {
            '+' -> return a + b
            '-' -> return a - b
            '*' -> return a * b
            '/' -> return if (b != 0.0) a / b else .0
            '^' -> return a.pow(b)
        }
        return 0.0
    }


}