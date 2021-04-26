package com.donaboyev.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.donaboyev.simplecalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var stateError: Boolean = false
    var lastDot: Boolean = false

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigit(view: View) {
        if (stateError) {
            binding.tvInput.text = (view as Button).text
            stateError = false
        } else {
            binding.tvInput.append((view as Button).text)
        }
        lastNumeric = true
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !stateError && !lastDot) {
            binding.tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !stateError) {
            binding.tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onClear(view: View) {
        binding.tvInput.text = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    fun onEqual(view:View) {
        if (lastNumeric && !stateError) {
            val text = binding.tvInput.text.toString()
            val expression = ExpressionBuilder(text).build()
            try {
                val result = expression.evaluate()
                binding.tvInput.text = result.toString()
                lastDot = true
            } catch (ex: Exception) {
                binding.tvInput.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

}