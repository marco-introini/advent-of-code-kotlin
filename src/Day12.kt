fun main() {

    fun calculateTotalFencingCost(gardenMap: List<CharArray>): Int {
        val rows = gardenMap.size
        val cols = gardenMap[0].size
        // tengo traccia delle celle visitate
        val visited = Array(rows) { BooleanArray(cols) }

        var totalCost = 0

        // Direzioni per spostarsi nella matrice (su, giù, sinistra, destra)
        val directions = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

        fun isInsideGrid(x: Int, y: Int): Boolean {
            return x in 0 until rows && y in 0 until cols
        }

        fun floodFill(x: Int, y: Int, plantType: Char): Pair<Int, Int> {
            val queue = ArrayDeque<Pair<Int, Int>>()
            queue.add(Pair(x, y))
            visited[x][y] = true

            var area = 0
            var perimeter = 0

            while (queue.isNotEmpty()) {
                val (cx, cy) = queue.removeFirst()
                area++

                for ((dx, dy) in directions) {
                    val nx = cx + dx
                    val ny = cy + dy

                    if (!isInsideGrid(nx, ny) || gardenMap[nx][ny] != plantType) {
                        // Se siamo fuori dalla griglia o incontriamo un altro tipo di pianta, aumentiamo il perimetro
                        perimeter++
                    } else if (!visited[nx][ny]) {
                        // Se è una cella da visitare dello stesso tipo di pianta
                        visited[nx][ny] = true
                        queue.add(Pair(nx, ny))
                    }
                }
            }

            return Pair(area, perimeter)
        }

        for (x in 0 until rows) {
            for (y in 0 until cols) {
                if (!visited[x][y]) {
                    val plantType = gardenMap[x][y]
                    val (area, perimeter) = floodFill(x, y, plantType)
                    totalCost += area * perimeter
                }
            }
        }

        return totalCost
    }

    fun part1(input: List<String>): Int {
        val gardenMap = input.map { it.toCharArray() }

        // Funzione per calcolare il prezzo totale
        val totalPrice = calculateTotalFencingCost(gardenMap)
        return totalPrice
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1930)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
