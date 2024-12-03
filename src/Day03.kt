fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        input.forEach {
            val (_, mult) = giveMultiplicationResult(it)
            total += mult
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var stringTotal = input.joinToString("")
        val replaced = removeSegmentsBetweenDontAndDo(stringTotal)
        val (_, mult) = giveMultiplicationResult(replaced)
        return mult
    }

    check(part1(readInput("Day03_test")) == 161)
    check(part2(readInput("Day03_test_parte2")) == 48)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun giveMultiplicationResult(input: String): Pair<Int, Int> {
    val regex = Regex("""mul\((\d+),(\d+)\)""")
    val matches = regex.findAll(input)

    var occurrences = 0
    var mult = 0

    matches.forEach { match ->
        val num1 = match.groupValues[1].toInt()
        val num2 = match.groupValues[2].toInt()
        mult += num1 * num2
        occurrences++
    }

    return Pair(occurrences, mult)
}

fun removeSegmentsBetweenDontAndDo(line: String): String =
    Regex("""(?:^|do\(\))(.*?)(?:${'$'}|don't\(\))""")
        .findAll(line)
        .map { it.groupValues[1] }.toList().toString()

