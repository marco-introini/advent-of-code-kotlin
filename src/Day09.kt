fun main() {

    fun generateDisk(input: String): String {
        var ret = ""
        input.forEachIndexed { index, value ->
            val intValue = Integer.parseInt(value.toString())
            if (index % 2 == 0) {
                for (i in 1..intValue) {
                    ret += (index / 2).toString()
                }
            } else {
                for (i in 1..intValue) {
                    ret += '.'
                }
            }
        }
        return ret
    }

    fun areDotsGrouped(charArray: CharArray): Boolean {
        var foundDotGroup = false

        var index = 0
        while (index < charArray.size) {
            if (charArray[index] == '.') {
                if (!foundDotGroup) {
                    foundDotGroup = true
                }
            } else {
                if (foundDotGroup) {
                    // Se abbiamo trovato un gruppo di `.` e ora troviamo un altro carattere
                    // Questo significa che il gruppo di `.` è terminato
                    // Verifica se un altro gruppo di `.` appare più avanti
                    for (j in index until charArray.size) {
                        if (charArray[j] == '.') {
                            return false
                        }
                    }
                    break
                }
            }
            index++
        }

        return true
    }

    fun reArrange(input: String): String {
        val charArray = input.toCharArray()
        for (i in charArray.size - 1 downTo 0) {
            if (areDotsGrouped(charArray)) {
                return String(charArray)
            }
            if (charArray[i].isDigit()) {
                for (j in charArray.indices) {
                    if (charArray[j] == '.') {
                        charArray[j] = charArray[i]
                        charArray[i] = '.'
                    }
                }
            }
        }
        return String(charArray)
    }

    fun part1(input: List<String>): Long {
        val stringa = generateDisk(input[0])
        val rearranged = reArrange(stringa).toCharArray()

        var result = 0L
        rearranged.forEachIndexed() { index, it ->
            if (it.isDigit()) {
                result += it.toString().toInt() * index
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day09_test")
    check(generateDisk(testInput[0]) == "00...111...2...333.44.5555.6666.777.888899")
    check(part1(testInput) == 1928L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
