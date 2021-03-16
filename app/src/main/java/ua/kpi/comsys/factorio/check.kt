package ua.kpi.comsys.factorio

fun main () {
    print(f(17))
}

fun f(original: Int): MutableList<Int> {
    var changer: Int = original
    val factors = mutableListOf<Int>()

    var i: Int = 2
    while (i <= original / 2) {
        println(i)
        if (changer % i == 0) {
            factors.add(i)
            changer /= i
            if (changer == 1) return factors
            continue
        } else {
            i++
        }
    }

    return if (factors.isEmpty()) mutableListOf(original) else factors
}