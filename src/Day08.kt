fun main() {

    fun isValid(x: Int, y: Int, input: List<String>): Boolean {
        // Verifica se la posizione (x, y) è entro i limiti della mappa
        return x in input[0].indices && y in input.indices
    }

    fun findAllAntennas(
        input: List<String>,
        antennas: MutableList<Triple<Char, Int, Int>>
    ) {
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char.isLetterOrDigit()) {
                    antennas.add(Triple(char, x, y))
                }
            }
        }
    }

    fun printMatrix(input: List<String>) {
        for (line in input) {
            println(line)
        }
    }

    fun applyAntinodes(antinodes: MutableSet<Pair<Int, Int>>, input: List<String>): List<CharArray> {
        val output = mutableListOf<CharArray>()
        input.forEach{
            output.add(it.toCharArray())
        }
        antinodes.forEach {
            output[it.second][it.first] = '#'
        }
        return output
    }

    fun part1(input: List<String>): Int {
        val antennas = mutableListOf<Triple<Char, Int, Int>>()

        findAllAntennas(input, antennas)

        val antinodes = mutableSetOf<Pair<Int, Int>>()

        // Calcola gli antinodi per ogni coppia di antenne con la stessa frequenza
        for (i in antennas.indices) {
            for (j in i + 1 until antennas.size) {
                val (char1, x1, y1) = antennas[i]
                val (char2, x2, y2) = antennas[j]

                if (char1 == char2) {
                    val dx = x2 - x1
                    val dy = y2 - y1

                    // Condizione della distanza doppia: 2 * distanza di una parte è uguale all'altra
                    val ax1 = x1 - dx
                    val ay1 = y1 - dy
                    val ax2 = x2 + dx
                    val ay2 = y2 + dy

                    if (isValid(ax1, ay1, input)) {
                        antinodes.add(Pair(ax1, ay1))
                    }
                    if (isValid(ax2, ay2, input)) {
                        antinodes.add(Pair(ax2, ay2))
                    }
                }
            }
        }

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        printMatrix(input)
        println()
        val antennas = mutableListOf<Triple<Char, Int, Int>>()

        findAllAntennas(input, antennas)

        // uso gli insiemi per evitare duplicazioni
        val antinodes = mutableSetOf<Pair<Int, Int>>()

        // Calcola gli antinodi per ogni coppia di antenne con la stessa frequenza
        for (i in antennas.indices) {
            for (j in i + 1 until antennas.size) {
                val (char1, x1, y1) = antennas[i]
                val (char2, x2, y2) = antennas[j]

                // le antenne stesse sono considerate antinodi
                antinodes.add(Pair(x1, y1))
                antinodes.add(Pair(x2, y2))

                if (char1 == char2) {
                    // Qui la distanza può essere anche maggiore di 2 volte
                    // eseguo le operazioni prima in un verso, poi nell'altro
                    var multiplier = 1
                    do {
                        val dx = x2 - x1
                        val dy = y2 - y1
                        val ax = x1 - multiplier * dx
                        val ay = y1 - multiplier * dy
                        multiplier++
                        if (isValid(ax, ay, input)) {
                            antinodes.add(Pair(ax, ay))
                        }
                    } while (
                        isValid(ax, ay, input)
                    )

                    multiplier = 1
                    do {
                        val dx = x2 - x1
                        val dy = y2 - y1
                        val ax = x1 + multiplier * dx
                        val ay = y1 + multiplier * dy
                        multiplier++
                        if (isValid(ax, ay, input)) {
                            antinodes.add(Pair(ax, ay))
                        }
                    } while (
                        isValid(ax, ay, input)
                    )


                }
            }
        }
        printMatrix(applyAntinodes(antinodes, input).map { it.joinToString("") })
        return antinodes.count()

    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput).toInt() == 14)
    println(part2(testInput).toInt())
    check(part2(testInput).toInt() == 34)


    val input = readInput("Day08")
    val part1 = part1(input)
    check(part1 == 413)
    println("Part 1: $part1")
    part2(input).println()
}
