fun main() {
    val rules = mutableListOf<Pair<Int, Int>>()

    fun loadRules(input: List<String>) {
        input.forEach {
            if (it.contains("|")) {
                val (first, second) = it.split("|").map(String::toInt)
                rules.add(Pair(first, second))
            }
        }
    }

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

    fun part1(input: List<String>): Int {
        val middleValues = mutableListOf<Int>()

        loadRules(input)

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
        val middleValues = mutableListOf<Int>()

        fun reorderList(list: List<Int>): List<Int> {
            val precedenceMap = mutableMapOf<Int, MutableList<Int>>()

            rules.forEach { (first, second) ->
                precedenceMap.computeIfAbsent(first) { mutableListOf() }.add(second)
            }

            return list.sortedWith { a, b ->
                when {
                    precedenceMap[a]?.contains(b) == true -> -1
                    precedenceMap[b]?.contains(a) == true -> 1
                    else -> 0
                }
            }
        }

        loadRules(input)

        input.forEach { list ->
            if (list.isEmpty()) return@forEach
            if (!list.contains("|")) {
                val numberList = list.split(",").map(String::toInt)
                if (!checkIfOk(numberList)) {
                    val sortedList = reorderList(numberList)
                    val middleValue = if (sortedList.size % 2 == 0) {
                        println("Error: lista pari!")
                        0
                    } else {
                        sortedList[sortedList.size / 2]
                    }
                    middleValues.add(middleValue)
                }
            }
        }

        return middleValues.sum()
    }

    check(part1(readInput("Day05_test")) == 143)
    check(part2(readInput("Day05_test")) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
