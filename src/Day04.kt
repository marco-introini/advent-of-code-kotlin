fun main() {
    fun part1(input: List<String>): Int {
        val word = "XMAS"
        val directions = listOf(
            Pair(-1, 0), Pair(1, 0), // Verticale su, giù
            Pair(0, -1), Pair(0, 1), // Orizzontale sinistra, destra
            Pair(-1, -1), Pair(-1, 1), Pair(1, -1), Pair(1, 1) // Diagonale
        )

        fun withinBounds(x: Int, y: Int): Boolean {
            return x in input.indices && y in input[x].indices
        }

        var count = 0

        for (i in input.indices) {
            for (j in input[i].indices) {
                directions.forEach { (dx, dy) ->
                    var found = true
                    for (k in word.indices) {
                        val newX = i + k * dx
                        val newY = j + k * dy
                        if (!withinBounds(newX, newY) || input[newX][newY] != word[k]) {
                            found = false
                            break
                        }
                    }
                    if (found) {
                        count++
                    }
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {

        fun checkShape(i: Int, j: Int, list: List<String>): Int {
            var numberOfShapes = 0

            if (list[i][j] != 'A') {
                return 0;
            }

            /*
             * M.M
             * .A.
             * S.S
             */
            if (
                (list[i - 1][j - 1] == 'M') &&
                (list[i + 1][j + 1] == 'S') &&
                (list[i - 1][j + 1] == 'M') &&
                (list[i + 1][j - 1] == 'S')
            ) {
                numberOfShapes++;
            }

            /*
             * S.S
             * .A.
             * M.M
             */
            if (
                (list[i - 1][j - 1] == 'S') &&
                (list[i + 1][j + 1] == 'M') &&
                (list[i - 1][j + 1] == 'S') &&
                (list[i + 1][j - 1] == 'M')
            ) {
                numberOfShapes++;
            }

            /*
             * S.M
             * .A.
             * S.M
             */
            if (
                (list[i - 1][j - 1] == 'S') &&
                (list[i + 1][j + 1] == 'M') &&
                (list[i - 1][j + 1] == 'M') &&
                (list[i + 1][j - 1] == 'S')
            ) {
                numberOfShapes++;
            }

            /*
             * M.S
             * .A.
             * M.S
             */
            if (
                (list[i - 1][j - 1] == 'M') &&
                (list[i + 1][j + 1] == 'S') &&
                (list[i - 1][j + 1] == 'S') &&
                (list[i + 1][j - 1] == 'M')
            ) {
                numberOfShapes++;
            }

            return numberOfShapes;
        }

        var count = 0

        // Percorre la griglia evitando i bordi che non possono contenere una X completa
        for (i in 1 until input.size - 1) { // Evita prima e ultima riga
            for (j in 1 until input[i].length - 1) { // Evita prima e ultima colonna
                count += checkShape(i, j, input)
            }
        }
        return count
    }


    check(part1(readInput("Day04_test")) == 18)
    println(part2(readInput("Day04_test")))
    check(part2(readInput("Day04_test")) == 9)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
