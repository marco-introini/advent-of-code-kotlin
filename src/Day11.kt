data class Stone(var num: Long)

fun divideStringInHalf(input: String): Pair<String, String> {
    val mid = input.length / 2
    val firstHalf = input.substring(0, mid)
    val secondHalf = input.substring(mid)
    return Pair(firstHalf, secondHalf)
}

data class ListOfStones(val stones: MutableList<Stone>) {
    companion object {
        fun fromInput(input: List<String>): ListOfStones {
            val stones = mutableListOf<Stone>()
            var i = 0
            input[0].split(" ").map { it.toInt() }.forEach { stones.add(i, Stone(it.toLong())); i++ }
            return ListOfStones(stones)
        }
    }

    fun blink() {
        var i: Int = 0
        var maxLenght = stones.size
        while (i < maxLenght) {
            if (stones[i].num == 0L) {
                stones[i].num = 1
                //println("0 Blink at $i -> ${stones[i]} became 1")
                i++
                continue
            }
            val current = stones[i].num.toString()
            if (current.count() % 2 == 0) {
                val (first, second) = divideStringInHalf(current)
                stones[i].num = first.toLong()
                val newStone = Stone(second.toLong())
                stones.add(i + 1, newStone)
                //println("SPLIT Blink at $i -> ${stones[i]} became $first and ${stones.getValue(i + 1)} became $second")
                i = i + 2
                maxLenght++
                continue
            }
            stones[i].num *= 2024
            //println("ELSE Blink at $i -> became ${stones[i].num}")
            i++
        }
    }

    override fun toString(): String {
        var ret = ""
        stones.forEach { ret += "${it.num} " }
        return ret
    }


}

fun main() {

    fun part1(input: List<String>): Int {
        val map = ListOfStones.fromInput(input)
        repeat(25) {
            map.blink()
            //println(map)
        }
        return map.stones.count()
    }

    fun part2(input: List<String>): Int {
        val map = ListOfStones.fromInput(input)
        repeat(75) {
            map.blink()
            //println(map)
        }
        return map.stones.count()
    }

    val testInput = readInput("Day11_test")
    val resTest = part1(testInput)
    println(resTest)
    check(resTest == 55312)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
