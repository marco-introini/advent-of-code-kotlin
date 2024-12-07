import java.math.BigInteger
import kotlin.math.pow

enum class Operation {
    ADD, MULTIPLY, CONCATENATE
}

fun main() {

    fun check(total: BigInteger, operands: List<BigInteger>): Boolean {
        val numOperands = operands.size - 1

        fun evaluate(combination: List<Operation>): BigInteger {
            var result = operands.first()
            for (i in 0 until numOperands) {
                when (combination[i]) {
                    Operation.ADD -> result += operands[i + 1]
                    Operation.MULTIPLY -> result *= operands[i + 1]
                    else -> throw Exception("Invalid operation")
                }
            }
            return result
        }

        val combinations = mutableListOf<List<Operation>>()
        for (i in 0 until (1 shl numOperands)) {
            val combination = mutableListOf<Operation>()
            for (j in 0 until numOperands) {
                if ((i shr j) and 1 == 0) {
                    combination.add(Operation.ADD)
                } else {
                    combination.add(Operation.MULTIPLY)
                }
            }
            combinations.add(combination)
        }

        return combinations.any { evaluate(it) == total }
    }

    fun checkWithConcatenation(total: BigInteger, operands: List<BigInteger>): Boolean {
        val numOperands = operands.size - 1

        fun evaluate(combination: List<Operation>): BigInteger {
            var result = operands.first()
            for (i in 0 until numOperands) {
                when (combination[i]) {
                    Operation.ADD -> result += operands[i + 1]
                    Operation.MULTIPLY -> result *= operands[i + 1]
                    Operation.CONCATENATE -> result = BigInteger(result.toString() + operands[i + 1].toString())
                }
            }
            return result
        }

        val combinations = mutableListOf<List<Operation>>()
        for (i in 0 until (3.0).pow(numOperands.toDouble()).toInt()) {
            val combination = mutableListOf<Operation>()
            var temp = i
            for (j in 0 until numOperands) {
                when (temp % 3) {
                    0 -> combination.add(Operation.ADD)
                    1 -> combination.add(Operation.MULTIPLY)
                    2 -> combination.add(Operation.CONCATENATE)
                }
                temp /= 3
            }
            combinations.add(combination)
        }

        return combinations.any { evaluate(it) == total }
    }


    fun part1(input: List<String>): BigInteger {
        var sum = BigInteger.ZERO
        input.forEach {
            val result = it.substringBefore(":").toBigInteger()
            val operands = it.substringAfter(": ")
                .split(" ")
                .map(String::toBigInteger)
            if (check(result, operands)){
                sum += result
            }
        }
        return sum
    }

    fun part2(input: List<String>): BigInteger {
        var sum = BigInteger.ZERO
        input.forEach {
            val result = it.substringBefore(":").toBigInteger()
            val operands = it.substringAfter(": ")
                .split(" ")
                .map(String::toBigInteger)
            if (checkWithConcatenation(result, operands)) {
                sum += result
            }
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput).toInt() == 3749)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
