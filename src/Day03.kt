fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val (occurrences, sum) = processPattern(line)
            total += sum
        }
        return total
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    check(part1(readInput("Day03_test")) == 161)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun processPattern(input: String): Pair<Int, Int> {
    val regex = Regex("""mul\((\d+),(\d+)\)""")
    val matches = regex.findAll(input)

    var occurrences = 0
    var sum = 0

    for (match in matches) {
        val num1 = match.groupValues[1].toInt()
        val num2 = match.groupValues[2].toInt()
        sum += num1 * num2
        occurrences++
    }

    return Pair(occurrences, sum)
}
