fun main() {

    fun calculateTrailheadScores(map: List<IntArray>): Int {
        val rows = map.size
        val cols = map[0].size

        // Direzioni: su, giù, sinistra, destra
        val directions = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

        // Funzione per verificare se la cella è all'interno della griglia
        fun isInsideGrid(x: Int, y: Int): Boolean {
            return x in 0 until rows && y in 0 until cols
        }

        // Funzione per calcolare il punteggio di un singolo trailhead
        fun calculateTrailheadScore(startX: Int, startY: Int): Int {
            val visited = Array(rows) { BooleanArray(cols) }
            val queue = ArrayDeque<Pair<Int, Int>>()
            val reachableNines = mutableSetOf<Pair<Int, Int>>()

            queue.add(startX to startY)
            visited[startX][startY] = true

            while (queue.isNotEmpty()) {
                val (x, y) = queue.removeFirst()
                val currentHeight = map[x][y]

                for ((dx, dy) in directions) {
                    val nx = x + dx
                    val ny = y + dy

                    if (isInsideGrid(nx, ny) && !visited[nx][ny]) {
                        val nextHeight = map[nx][ny]

                        // Verifica il percorso valido (altezza aumenta di esattamente 1)
                        if (nextHeight == currentHeight + 1) {
                            visited[nx][ny] = true
                            queue.add(nx to ny)

                            // Se la prossima altezza è 9, aggiungi alle raggiungibili
                            if (nextHeight == 9) {
                                reachableNines.add(nx to ny)
                            }
                        }
                    }
                }
            }

            return reachableNines.size
        }

        // Trova tutti i trailheads (celle con altezza 0) e calcola il punteggio totale
        var totalScore = 0
        for (x in 0 until rows) {
            for (y in 0 until cols) {
                if (map[x][y] == 0) {
                    totalScore += calculateTrailheadScore(x, y)
                }
            }
        }

        return totalScore
    }


    fun part1(input: List<String>): Int {
        val topographicMap = input.map { it.map { c -> c - '0' }.toIntArray() }

        val totalScore = calculateTrailheadScores(topographicMap)
        return totalScore
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
