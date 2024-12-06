fun main() {
    val rules = mutableListOf<Pair<Int, Int>>()

    fun loadRules(input: List<String>) {
        input.forEach {
            if (it.contains("|")) {
                val (first, second) = it.split("|").map(String::toInt)
                rules.add(Pair(first, second))
            }
        }
    }

    fun checkIfOk(list: List<Int>): Boolean {
        for ((a, b) in rules) {
            val indexA = list.indexOf(a)
            val indexB = list.indexOf(b)

            if (indexA != -1 && indexB != -1 && indexA >= indexB) {
                return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val middleValues = mutableListOf<Int>()

        loadRules(input)

        for (list in input) {
            if (list.isEmpty())
                continue
            if (!list.contains("|")) {
                val numberList = list.split(",").map(String::toInt)
                if (checkIfOk(numberList)) {
                    val middleValue = if (numberList.size % 2 == 0) {
                        numberList[numberList.size / 2 - 1]
                        println("Error: lista pari!")
                        0
                    } else {
                        numberList[numberList.size / 2]
                    }
                    middleValues.add(middleValue)
                }
            }
        }

        return middleValues.sum()
    }

    fun part2(input: List<String>): Int {
        val middleValues = mutableListOf<Int>()

        fun topologicalSort(list: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
            val inDegree = mutableMapOf<Int, Int>()
            val adjacencyList = mutableMapOf<Int, MutableList<Int>>()

            // Inizializza il grado entrante e la lista di adiacenza
            for (element in list) {
                inDegree[element] = 0
                adjacencyList[element] = mutableListOf()
            }

            // Crea il grafo diretto basato sulle regole
            for ((first, second) in rules) {
                adjacencyList[first]?.add(second)
                inDegree[second] = inDegree.getOrDefault(second, 0) + 1
            }

            // Inizializza una coda con elementi con grado entrante zero
            val queue = ArrayDeque<Int>()
            for (element in inDegree.filter { it.value == 0 }.keys) {
                queue.add(element)
            }

            val sortedList = mutableListOf<Int>()

            // Esegui l'ordinamento
            while (queue.isNotEmpty()) {
                val node = queue.removeFirst()
                sortedList.add(node)

                for (neighbor in adjacencyList[node] ?: emptyList()) {
                    inDegree[neighbor] = inDegree[neighbor]!! - 1
                    if (inDegree[neighbor] == 0) {
                        queue.add(neighbor)
                    }
                }
            }

            // Verifica se esiste un ciclo
            if (sortedList.size != list.size) {
                throw IllegalArgumentException("Il grafo contiene un ciclo, quindi non è possibile eseguire un ordinamento topologico.")
            }

            return sortedList
        }

        loadRules(input)

        input.forEach { list ->
            if (list.isEmpty()) return@forEach
            if (!list.contains("|")) {
                var numberList = list.split(",").map(String::toInt)
                if (checkIfOk(numberList.toList())) return@forEach
                var contatore = 0
                while (!checkIfOk(numberList.toList())) {
                    contatore++
                    if (contatore > 1000) {
                        println("Error: contatore superato!")
                    }
                    numberList = topologicalSort(numberList, rules)
                }

                val middleValue = if (numberList.size % 2 == 0) {
                    println("Error: lista pari!")
                    0
                } else {
                    numberList[numberList.size / 2]
                }
                middleValues.add(middleValue)
            }
        }

        return middleValues.sum()
    }

    check(part1(readInput("Day05_test")) == 143)
    check(part2(readInput("Day05_test")) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
