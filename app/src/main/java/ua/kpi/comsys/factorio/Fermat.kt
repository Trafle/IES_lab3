package ua.kpi.comsys.ip8311

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import ua.kpi.comsys.factorio.R
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

class Fermat : Fragment () {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fermat_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val input = view.findViewById<TextInputEditText>(R.id.inputField)
        val output = view.findViewById<TextView>(R.id.outputText)
        val calcBut = view.findViewById<Button>(R.id.calcBut)

        calcBut.setOnClickListener { _ ->
            val original = input.text.toString().toLong()

            val timer = Timer("SettingUp", false).schedule(3000) {
                error("took too long")
            }

            val factorsArray = fermatFactorization(original)

            timer.cancel()

            if (factorsArray == null) {
                Toast.makeText(context,"bad argument", Toast.LENGTH_SHORT).show()
                output.text = original.toString()
            } else {
                output.text = factorsArray.joinToString()
            }
        }

    }
}

fun fermatFactorization(n: Long): List<Long>? {
    if (n % 2 == 0L) return listOf(2L, n / 2L)
    if (n <= 0L) return null

    var a = ceil(sqrt(n.toDouble()))
    var b = 0.0

    if (a * a == n.toDouble()) return listOf(a.toLong(), a.toLong())

    while(true) {
        val b1 = a * a - n
        b = floor(sqrt(b1))
        if (b1 == b * b) break else a++
    }

    return listOf((a - b).toLong(), (a + b).toLong())
}