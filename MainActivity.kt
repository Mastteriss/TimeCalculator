package com.example.timecalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var firstOperandET:EditText
    private lateinit var secondOperandET:EditText

    private lateinit var buttonSumBtn:Button
    private lateinit var buttonDifBtn:Button

    private lateinit var resultTv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        firstOperandET = findViewById(R.id.firstOperandET)
        secondOperandET = findViewById(R.id.secondOperandET)
        buttonSumBtn = findViewById(R.id.buttonSumBTN)
        buttonDifBtn = findViewById(R.id.buttonDifBTN)

        resultTv = findViewById(R.id.resultTV)

        buttonSumBtn.setOnClickListener(this)
        buttonDifBtn.setOnClickListener(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClick(v: View) {
        val time1 = firstOperandET.text.toString()
        val time2 = secondOperandET.text.toString()

        if(firstOperandET.text.isEmpty()||secondOperandET.text.isEmpty())
        {
            resultTv.text = "Введите оба времени"
            return
        }
       val totalSeconds1 = convertToSeconds(time1)
        val totalSeconds2 = convertToSeconds(time2)
        val resultInt = if (v.id ==R.id.buttonSumBTN){
            totalSeconds1 + totalSeconds2
        }else{
            totalSeconds1 - totalSeconds2
        }

        resultTv.text = "Результат ${convertToFormattedString(resultInt)}"


    }
    private fun convertToSeconds(time:String):Int {

        var totalSeconds = 0
        val regex = "(\\d+)(h|m|s)".toRegex()

        val matches = regex.findAll(time)
        for (match in matches) {
            val (value, unit) = match.destructured
            when (unit) {
                "h" -> totalSeconds += value.toInt() * 3600
                "m" -> totalSeconds += value.toInt() * 60
                "s" -> totalSeconds += value.toInt()
            }
        }
        return  totalSeconds
    }

    private fun convertToFormattedString(totalSeconds:Int):String{
        val house = totalSeconds / 3600
        val min = (totalSeconds / 3600 ) / 60
        val sec = totalSeconds % 60
        return if (house > 0) {
            "${house}h ${min}m ${sec}s"
        } else {
            "${min}m ${sec}s"
        }
    }


}
