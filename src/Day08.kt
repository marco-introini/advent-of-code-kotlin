

fun main() {

    fun isValid(x: Int, y: Int, input: List<String>): Boolean {
        // Verifica se la posizione (x, y) è entro i limiti della mappa
        return x in input[0].indices && y in input.indices
    }

    fun part1(input: List<String>): Int {
        val antennas = mutableListOf<Triple<Char, Int, Int>>()

        // Identifica tutte le antenne sulla mappa
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char.isLetterOrDigit()) {
                    antennas.add(Triple(char, x, y))
                }
            }
        }

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

        // Restituisce il numero totale di posizioni uniche
        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput).toInt() == 14)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
