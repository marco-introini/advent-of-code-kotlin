
fun countDistinctPositions(map: List<String>): Int {
    // Direzioni: su, destra, giù, sinistra
    val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))
    var directionIndex = 0 // Inizio guardando su (indice 0)
    var x = 0
    var y = 0
    val visitedPositions = mutableSetOf<Pair<Int, Int>>()

    // Trova la posizione iniziale del guardiano (indicato con "^")
    outer@ for (i in map.indices) {
        for (j in map[i].indices) {
            if (map[i][j] == '^') {
                x = j
                y = i
                break@outer
            }
        }
    }

    // Ciclo fino a che il guardiano non esce dalla mappa
    while (true) {
        // Segna la posizione attuale come visitata
        visitedPositions.add(Pair(x, y))

        // Calcola la prossima posizione
        val nextX = x + directions[directionIndex].first
        val nextY = y + directions[directionIndex].second

        // Controlla se la prossima posizione è valida
        if (nextY in map.indices && nextX in map[nextY].indices && map[nextY][nextX] != '#') {
            // Avanza nella nuova direzione
            x = nextX
            y = nextY
        } else {
            // Ruota a destra (90°)
            directionIndex = (directionIndex + 1) % 4
        }

        // Se il guardiano esce dalla mappa, interrompi il ciclo
        if (x !in map[0].indices || y !in map.indices) {
            break
        }

        // Interrompi se tutte le direzioni sono bloccate (loop infinito evitato)
        val blocked = (0 until 4).all { dir ->
            val testX = x + directions[dir].first
            val testY = y + directions[dir].second
            testY !in map.indices || testX !in map[testY].indices || map[testY][testX] == '#'
        }
        if (blocked) break
    }

    return visitedPositions.size
}




fun main() {
    fun part1(input: List<String>): Int {
        return countDistinctPositions(input)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    //check(part1(readInput("Day05_test")) == 143)
    //check(part2(readInput("Day05_test")) == 123)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
