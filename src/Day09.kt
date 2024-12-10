import kotlin.collections.containsKey

data class DiskPoint(val fileId: Int)

data class Disk(
    val size: Int,
    val map: MutableMap<Int, DiskPoint>
) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        with(stringBuilder) {
            for (i in 0 until size) {
                append(map[i]?.fileId ?: '.')
            }
        }
        return stringBuilder.toString()
    }

    fun hasNoHoles(): Boolean {
        val total = map.size
        for (i in 0 until total) {
            if (!map.containsKey(i)) {
                return false
            }
        }
        return true
    }

    fun reArrange() {
        for (i in size - 1 downTo 0) {
            if (map.containsKey(i)) {
                if (hasNoHoles()) {
                    return
                }
                for (j in 0 until i) {
                    if (!map.containsKey(j)) {
                        val tempPoint = map[i]
                        if (tempPoint != null) {
                            map.remove(i)
                            map[j] = tempPoint
                            //println("Inserito il punto ${tempPoint} nella posizione ${j}")
                            break
                        } else println("Errore per i ${i} e j ${j}")
                    }
                }
            }
        }
    }

    fun Disk.findFirstEmptySpace(n: Int): Int? {
        var consecutiveEmpty = 0
        var startIndex = -1

        for (i in 0 until size) {
            if (!map.containsKey(i)) {
                if (consecutiveEmpty == 0) {
                    startIndex = i
                }
                consecutiveEmpty++
                if (consecutiveEmpty >= n) {
                    return startIndex
                }
            } else {
                consecutiveEmpty = 0
                startIndex = -1
            }
        }

        // Nessuno spazio trovato
        return null
    }

    fun reArrangeByLength() {
        // Ordina gli entry della mappa in base al fileId in ordine decrescente
        val sortedEntries = map.values.toSet()

        for (entry in sortedEntries) {
            val fileSize = map.count { it.value.fileId == entry.fileId }

            val firstSpace = findFirstEmptySpace(fileSize)

            if (firstSpace != null) {
                val tempPoint = entry
                for (i in firstSpace until firstSpace + fileSize) {
                    map.filter { it.value.fileId == tempPoint.fileId }.forEach { map.remove(it.key) }
                    map[i] = tempPoint
                }
            }
        }
    }

}


fun main() {

    fun generateDisk(input: String): Disk {
        var disk = mutableMapOf<Int, DiskPoint>()
        var dimension = 0
        input.forEachIndexed { index, value ->
            val intValue = Integer.parseInt(value.toString())
            if (index % 2 == 0) {
                repeat(intValue) {
                    disk[dimension] = DiskPoint(index / 2)
                    dimension++
                }
            } else {
                repeat(intValue) {
                    dimension++
                }
            }
        }

        return Disk(dimension, disk)
    }


    fun part1(input: List<String>): Long {
        val disk = generateDisk(input[0])
        disk.reArrange()

        var result = 0L
        disk.map.forEach() { index, it ->
            result += index * it.fileId.toLong()
        }

        return result
    }

    fun part2(input: List<String>): Long {
        val disk = generateDisk(input[0])
        disk.reArrangeByLength()

        var result = 0L
        disk.map.forEach() { index, it ->
            result += index * it.fileId.toLong()
        }

        return result
    }

    val testInput = readInput("Day09_test")
    val disk = generateDisk(testInput[0])
    println("TEST PART1")
    println(disk.toString())
    check(disk.toString() == "00...111...2...333.44.5555.6666.777.888899")
    disk.reArrange()
    println("TEST PART2")
    val disk2 = generateDisk(testInput[0])
    disk2.reArrangeByLength()
    println(disk2.toString())

    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
