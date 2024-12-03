import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {

        var safeRows = 0

        mainfor@ for (line in input) {
            val numbers = mutableListOf<Int>()
            val parts = line.split(" ")
            for (part in parts) {
                if (part.isNotBlank()) {
                    val number = part.toInt()
                    numbers.add(number)
                }
            }

            // check if it's safe
            if (!areNumbersMonotonic(numbers)) {
                continue
            }
            for (i in 1 until numbers.size) {
                val difference = abs(numbers[i] - numbers[i - 1])
                if (difference !in 1..3) {
                    continue@mainfor
                }
            }

            safeRows++
        }
        return safeRows
    }



    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
