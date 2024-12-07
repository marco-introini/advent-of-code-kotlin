import java.math.BigInteger

enum class Operation {
    ADD, MULTIPLY
}

fun main() {

    data class Command(var operation: Operation, val operand: BigInteger)

    fun calculate(list: List<Command>): BigInteger {
        var result: BigInteger = BigInteger.ZERO;
        list.forEach {
            when (it.operation) {
                Operation.ADD -> result += it.operand
                Operation.MULTIPLY -> result *= it.operand
            }
        }
        return result
    }

    fun check(total: BigInteger, operands: List<BigInteger>): Boolean {
        val numOperands = operands.size - 1
        val operations: MutableList<Command> = mutableListOf()

        operands.forEach {
            operations.add(Command(Operation.ADD, it))
        }
        while (operations.find { it.operation == Operation.ADD } != null) {
            if (calculate(operations) == total) return true
            operations.find { it.operation == Operation.ADD }?.operation = Operation.MULTIPLY
        }
        println(operations.map { it.operand })
        return false
    }


    fun part1(input: List<String>): BigInteger {
        var sum = BigInteger.ZERO
        input.forEach {
            val result = it.substringBefore(":").toBigInteger()
            val operands = it.substringAfter(": ")
                .split(" ")
                .map(String::toBigInteger)
            if (check(result, operands))
                sum += result
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return 0
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
