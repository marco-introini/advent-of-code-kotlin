import java.math.BigInteger

enum class Operation {
    ADD, MULTIPLY
}

fun main() {

    data class Command(var operation: Operation, val operand: BigInteger)

    fun calculate(list: List<Command>): BigInteger {
        var result: BigInteger = list.first().operand
        var item = 0;
        while (item < list.size-1 ) {
            when (list[item].operation) {
                Operation.ADD -> result += list[item+1].operand
                Operation.MULTIPLY -> result *= list[item+1].operand
            }
            item++
        }

        return result
    }

    fun mustChangeSubsequentOperation(list: List<Command>, current: Int): Boolean {
        list.forEachIndexed { index, command ->
            if (index <= current) {
                if (command.operation == Operation.ADD) return false
            }
        }
        return true
    }

    fun check(total: BigInteger, operands: List<BigInteger>): Boolean {
        val numOperands = operands.size - 1
        val operations: MutableList<Command> = mutableListOf()

        operands.forEach {
            operations.add(Command(Operation.ADD, it))
        }
        var currentIterator = 0
        while (operations.last().operation != Operation.MULTIPLY) {
            if (calculate(operations) == total) {
                print("OK ")
                println(operations.map { it.operand })
                return true
            }
            if (mustChangeSubsequentOperation(operations, currentIterator)) {
                for (i in 0 until currentIterator) {
                    operations[i].operation = Operation.ADD
                }
                currentIterator++
                operations[currentIterator].operation = Operation.MULTIPLY
            }
            else {
                operations.first { it.operation == Operation.ADD  }.operation = Operation.MULTIPLY
            }
        }

        return false
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
