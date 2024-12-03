fun main() {
    fun part1(input: List<String>): Int {
        val firstNumbers = mutableListOf<Int>()
        val secondNumbers = mutableListOf<Int>()

        for (line in input) {
            val parts = line.split("   ")
            val firstNumber = parts[0].toInt()
            val secondNumber = parts[1].toInt()

            firstNumbers.add(firstNumber)
            secondNumbers.add(secondNumber)
        }

        firstNumbers.sort()
        secondNumbers.sort()

        var total = 0

        for (i in firstNumbers.indices) {
            // Calculate the absolute difference
            val difference = kotlin.math.abs(firstNumbers[i] - secondNumbers[i])
            total += difference
        }

        return total
    }

    fun part2(input: List<String>): Int {
        val firstNumbers = mutableListOf<Int>()
        val secondNumbers = mutableListOf<Int>()

        for (line in input) {
            val parts = line.split("   ")
            val firstNumber = parts[0].toInt()
            val secondNumber = parts[1].toInt()

            firstNumbers.add(firstNumber)
            secondNumbers.add(secondNumber)
        }

        var total = 0

        for (number in firstNumbers) {
            val count = secondNumbers.count { it == number }
            total += (number * count)
        }

        return total
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
