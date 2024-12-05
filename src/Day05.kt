fun main() {
    fun part1(input: List<String>): Int {

        val rules = mutableListOf<Pair<Int, Int>>()
        val middleValues = mutableListOf<Int>()

        fun checkIfOk(list: List<Int>): Boolean {
            for ((a, b) in rules) {
                val indexA = list.indexOf(a)
                val indexB = list.indexOf(b)

                if (indexA != -1 && indexB != -1 && indexA >= indexB) {
                    return false
                }
            }
            return true
        }

        // first part of file contains rules. I need to store all rules
        input.forEach {
            if (it.contains("|")) {
                // it's a rule
                val (first, second) = it.split("|").map(String::toInt)
                rules.add(Pair(first, second))
            }
        }

        // next. Check if it's ok
        for (list in input) {
            if (list.isEmpty())
                continue
            if (!list.contains("|")) {
                val numberList = list.split(",").map(String::toInt)
                if (checkIfOk(numberList)) {
                    val middleValue = if (numberList.size % 2 == 0) {
                        numberList[numberList.size / 2 - 1]
                        println("Error: lista pari!")
                        0
                    } else {
                        numberList[numberList.size / 2]
                    }
                    middleValues.add(middleValue)
                }
            }
        }

        return middleValues.sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    check(part1(readInput("Day05_test")) == 143)
    check(part2(readInput("Day05_test")) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
