import kotlin.math.abs

fun isReportSafe(report: List<Int>): Boolean {
    val windowed = report.windowed(2)
    val allIncreasing = windowed.map { (l, r) -> l < r }.all { it }
    val allDecreasing = windowed.map { (l, r) -> l > r }.all { it }
    val smallDifference = windowed.map { (l, r) -> abs(r - l) }.all {
        it >= 1 && it <= 3
    }
    return (allIncreasing || allDecreasing) && smallDifference
}

fun isReportSafeDampened(report: List<Int>): Boolean {
    val dampenedLists = report.indices.map { report.filterIndexed { i, _ -> i != it } }
    return dampenedLists.count(::isReportSafe) > 0 || isReportSafe(report)
}

fun main() {
    fun part1(input: List<String>): Int {
        var safeRows = 0

        for (line in input) {
            val numbers = mutableListOf<Int>()
            val parts = line.split(" ")
            for (part in parts) {
                if (part.isNotBlank()) {
                    val number = part.toInt()
                    numbers.add(number)
                }
            }

            if (isReportSafe(numbers)) {
                safeRows++
            }
        }
        return safeRows
    }


    fun part2(input: List<String>): Int {
        var safeRows = 0

        for (line in input) {
            val numbers = mutableListOf<Int>()
            val parts = line.split(" ")
            for (part in parts) {
                if (part.isNotBlank()) {
                    val number = part.toInt()
                    numbers.add(number)
                }
            }

            if (isReportSafeDampened(numbers)) {
                safeRows++
            }
        }
        return safeRows
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
