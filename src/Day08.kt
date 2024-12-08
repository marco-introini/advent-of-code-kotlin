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
        val antennasByFrequency = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

        // Step 1: Mappa le antenne per frequenza
        for (y in input.indices) {
            for (x in input[y].indices) {
                val char = input[y][x]
                if (char.isLetterOrDigit()) {
                    antennasByFrequency.getOrPut(char) { mutableListOf() }.add(Pair(x, y))
                }
            }
        }

        val antinodes = mutableSetOf<Pair<Int, Int>>()

        // Step 2: Calcola gli antinodi per ogni frequenza
        for (antennas in antennasByFrequency.values) {
            if (antennas.size < 2) continue

            // Aggiungi tutte le posizioni delle antenne come antinodi
            antinodes.addAll(antennas)

            for (i in antennas.indices) {
                for (j in i + 1 until antennas.size) {
                    val (x1, y1) = antennas[i]
                    val (x2, y2) = antennas[j]

                    if (x1 == x2) {
                        // Antenne sulla stessa colonna: aggiungili come antinodi
                        for (y in 0 until input.size) {
                            if (y != y1 && y != y2) {
                                antinodes.add(Pair(x1, y))
                            }
                        }
                    } else if (y1 == y2) {
                        // Antenne sulla stessa riga: aggiungili come antinodi
                        for (x in 0 until input[y1].length) {
                            if (x != x1 && x != x2) {
                                antinodes.add(Pair(x, y1))
                            }
                        }
                    }
                }
            }
        }

        return antinodes.size
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput).toInt() == 14)
    //check(part2(testInput).toInt() == 34)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
