package ua.kpi.comsys.factorio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.absoluteValue

class Genetic : Fragment () {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.genetic_layout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.let { super.onViewCreated(it, savedInstanceState) }

        view.findViewById<Button>(R.id.calc)?.setOnClickListener { _ ->
            val x1 = view.findViewById<EditText>(R.id.x1)?.text.toString().toDouble()
            val x2 = view.findViewById<EditText>(R.id.x2)?.text.toString().toDouble()
            val x3 = view.findViewById<EditText>(R.id.x3)?.text.toString().toDouble()
            val x4 = view.findViewById<EditText>(R.id.x4)?.text.toString().toDouble()
            val y = view.findViewById<EditText>(R.id.y)?.text.toString().toDouble()
            val message = view.findViewById<TextView>(R.id.message)

            val generation = geneMachine(x1, x2, x3, x4, y).result()

            if (generation == null) {
                message?.text = "No result"
            } else {
                message?.text = generation.toString()
            }
        }

    }
}

class geneMachine
constructor(in_x1: Double, in_x2: Double, in_x3: Double, in_x4: Double, in_y: Double) {
    val x1 = in_x1
    val x2 = in_x2
    val x3 = in_x3
    val x4 = in_x4
    val y = in_y
    val zero_population: MutableList<MutableList<Double>> = mutableListOf()
    var population: MutableList<MutableList<Double>> = mutableListOf()
    val fitness_list: MutableList<Double> = mutableListOf()
    val child_popul: MutableList<MutableList<Double>> = mutableListOf()
    var best_popul: MutableList<Double> = mutableListOf()

    fun searchFitness(popul_row: MutableList<Double>): Double {
        val fitness: Double = y -
                popul_row[0] * x1 -
                popul_row[1] * x2 -
                popul_row[2] * x3 -
                popul_row[3] * x4
        return fitness.absoluteValue
    }

    fun zeroPopulationInit() {
        for (i in 0..3) {
            zero_population.add(mutableListOf())
            for (j in 0..3) {
                zero_population[i].add((1..8).random().toDouble())
            }
        }
    }

    fun calculateFitnesOfPopulation() {
        fitness_list.clear()
        if (population.isEmpty()) {
            zero_population.mapTo(population) { it }
        }
        for (i in 0..3) {
            fitness_list.add(searchFitness(this.population[i]))
        }
    }

    fun playRulet() {
        child_popul.clear()
        var rulet = 0.00
        val procent_rulet: MutableList<Double> = mutableListOf()
        val rulet_circle: MutableList<Double> = mutableListOf()
        this.fitness_list.forEach { rulet += 1 / it }
        for (i in 0..3) {
            procent_rulet.add(1 / fitness_list[i] / rulet)
        }

        for (i in 0..3) {
            if (i == 0) {
                rulet_circle.add(procent_rulet[i])
            } else {
                rulet_circle.add(rulet_circle[i - 1] + procent_rulet[i])
            }
        }
        var i = 0
        child_popul.clear()
        while (i < 4) {
            val piu: Double = (1..100).random().toDouble() / 100
            var k = 0
            var this_child = 0
            for (k in 0..3) {
                if (piu >= rulet_circle[k]) {
                    this_child = k
                }
            }
            child_popul.add(population[this_child])
            i++
        }
    }

    fun crossOver() {
        population.clear()
        for (p in 0..3) {
            val c: MutableList<Double> = mutableListOf()
            c.clear()
            for (j in 0..3) {
                if (p % 2 == 0) {
                    if (j < 2) {
                        c.add(child_popul[p][j])
                    } else c.add(child_popul[p + 1][j])
                } else
                    if (j < 2) {
                        c.add(child_popul[p][j])
                    } else c.add(child_popul[p - 1][j])
            }
            population.add(c)
        }
    }

    fun calculateBestFitness(): Boolean {
        this.calculateFitnesOfPopulation()
        fitness_list.forEach { if (it == 0.0) return true }
        return false
    }

    fun lifeCycle(): Boolean {
        var q = 0
        while (!calculateBestFitness()) {
            // the limit of cycles
            if (q < 100) {
                this.calculateFitnesOfPopulation()
                this.playRulet()
                this.crossOver()
                q++
            } else {
                return false
            }
        }
        return true
    }

    fun result(): MutableList<Double>? {
        this.zeroPopulationInit()
        if (this.lifeCycle()) {
            while ((!this.fitness_list.contains(0.0)) &&
                    population[0] == child_popul[0] &&
                    population[1] == child_popul[1] &&
                    population[2] == child_popul[2] &&
                    population[3] == child_popul[3]
            ) {
                zero_population.clear()
                population.clear()
                fitness_list.clear()
                child_popul.clear()
                this.zeroPopulationInit()
                this.lifeCycle()
            }
            for (i in 0..3) {
                if (fitness_list[i] == 0.0) {
                    best_popul = population[i]
                }
            }
            return best_popul
        }
        return null
    }
}