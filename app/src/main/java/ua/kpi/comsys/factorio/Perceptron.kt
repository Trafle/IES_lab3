package ua.kpi.comsys.factorio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlin.system.measureTimeMillis

data class Point(val x: Double, val y: Double)

class Perceptron : Fragment () {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.perceptron_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val threshold: Int = 4
        val points = listOf(Point(0.0, 6.0), Point(1.0, 5.0), Point(3.0, 3.0), Point(2.0, 4.0))
        val speeds = listOf(0.001, 0.01, 0.05, 0.1, 0.2, 0.3)
        val deadlines = listOf(0.5, 1.0, 2.0, 5.0)
        val iterations = listOf(100, 200, 500, 1000)

        val speedSpinner: Spinner = view.findViewById(R.id.speed)
        val deadlineSpinner: Spinner = view.findViewById(R.id.deadline)
        val speedArray = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, speeds).also { speedArray ->
            speedArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            speedSpinner.adapter = speedArray
        }
        val deadlineArray = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, deadlines).also { deadlineArray ->
            speedArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            deadlineSpinner.adapter = deadlineArray
        }

        view.findViewById<Button>(R.id.calc).setOnClickListener { _ ->
            val speed = speedSpinner.selectedItem.toString().toDouble()
            val deadline = deadlineSpinner.selectedItem.toString().toDouble()
            val accuracy = percept(threshold, points, speed, deadline, iterations)
            print(accuracy)
        }
    }
}

fun percept (threshold: Int, points: List<Point>, speed: Double, deadline: Double, iterations: List<Int>): Double {

    var speedIndex: Int = 0
    var accuracy: Double = 0.0

    val timeInMillis = measureTimeMillis {

    }

//    var deadlineIndex = pickTheSmallestLessThanNum(timeInMillis.toDouble() / 1000, deadlines)



    return deadline + speed
}

//fun pickTheSmallestLessThanNum(number: Double, array: List<Double>): Int? {
//    var index: Int = 0
//
//    for ((i, element) in array.withIndex()) {
//        if (element > number && element <= array[index]) index = i
//    }
//
//    return if (array[index] < number) null else index
//}